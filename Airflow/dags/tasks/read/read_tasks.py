import logging

import pandas as pd
import os
from airflow.models.variable import Variable

# from tasks.read.FileReader import FileReader
# from DAL.PostgresDatabaseManager import PostgresDatabaseManager
# from config import read_table_name_config, last_seen_table_config, last_seen_column_name_config
from tasks.read.file_reader import FileReader
from DAL.postgres_database_manager import PostgresDatabaseManager
from config import read_table_name_config, last_seen_table_config, last_seen_column_name_config, read_image_col_name_constants


class ReadTasks():

    @staticmethod
    def read_image(ti):

        last_seen_file, last_seen_row = ReadTasks._get_last_file_and_row(last_seen_table_config.LAST_SEEN_IMAGE_TABLE,
                                                                     last_seen_column_name_config.LAST_SEEN_IMAGE_FILE_PATH,
                                                                     last_seen_column_name_config.LAST_SEEN_IMAGE_ROW_ID)

        files_to_read = ReadTasks._get_file_names("image_file_directory", last_seen_file)

        if len(files_to_read) <= 0:
            logging.info("No files were found, terminating reading step successfully.")
            return

        data = ReadTasks._get_files_to_data_frames("image_file_directory", files_to_read, last_seen_file, last_seen_row)

        if data.empty:
            logging.info("No new data was found, terminating reading step successfully.")
            return

        ReadTasks._change_col_names(data, read_image_col_name_constants)

        ReadTasks._insert_into_db(data, read_table_name_config.READ_IMAGE)

        ReadTasks._make_xcom(ti, files_to_read[len(files_to_read) - 1], data[Variable.get("image_col_name_ullid")].iloc[-1])

        # lastReadData = {last_seen_column_name_config.LAST_SEEN_IMAGE_FILE_PATH:[files_to_read[len(files_to_read) - 1]], last_seen_column_name_config.LAST_SEEN_IMAGE_ROW_ID:[data["ullid"].iloc[-1]]}
        # lastSeenDf = pd.DataFrame(data=lastReadData)
        # pdm=PostgresDatabaseManager()
        # pdm.insertIntoTable(lastSeenDf, last_seen_table_config.LAST_SEEN_IMAGE_TABLE)
    @staticmethod
    def read_media_prepare():
        # do stuff, remove pass
        pass
    @staticmethod
    def read_print_cycle():
        # do stuff, remove pass
        pass

    @staticmethod
    def _get_last_file_and_row(table_name, file_path_col_name, row_col_name):
        # Read last seen table & row
        logging.info("Getting last seen file and row.")
        pdm = PostgresDatabaseManager()
        last_seen_table = ""
        try:
            last_seen_table = pdm.read_table(table_name)
        except:
            last_seen_table = pd.DataFrame()
        last_seen_file = ""
        last_seen_row = ""
        if not last_seen_table.empty:
            last_seen_file = last_seen_table[file_path_col_name].iloc[-1]
            last_seen_row = last_seen_table[row_col_name].iloc[-1]
            logging.info("Found last seen file: " + last_seen_file + " at row with ullid " + last_seen_row + ".")
        else:
            logging.info("Table " + table_name + " was empty, reading all instead.")
        return last_seen_file, last_seen_row

    @staticmethod
    def _get_file_names(directory_variable_key, last_seen_file):
        # Get filenames
        logging.info("Getting filenames from directory.")
        file_reader = FileReader()
        directory = os.getenv("AIRFLOW_HOME") + Variable.get(directory_variable_key)
        logging.info(
            f"Reading files from {directory} {f'starting at file {last_seen_file}' if last_seen_file != '' else ''}")
        files_to_read = file_reader.get_file_names_starting_from(directory, last_seen_file)
        logging.info(f'Found {len(files_to_read)} files.')
        return files_to_read

    @staticmethod
    def _get_files_to_data_frames(directory_variable_key, files_to_read, last_seen_file, last_seen_row) -> pd.DataFrame:
        # get files
        logging.info("Getting data from files.")
        data_frames = []
        file_reader = FileReader()
        directory = os.getenv("AIRFLOW_HOME") + Variable.get(directory_variable_key)

        for file in files_to_read:
            logging.info(f"Getting data from file {file}.")
            df = file_reader.read_pandas_csv_file(directory + file, ";")
            df = df.set_index(df[Variable.get("image_col_name_ullid")])
            df = df.sort_index('index')
            if file == last_seen_file:
                df = df[df[Variable.get("image_col_name_ullid")] > int(last_seen_row)]
                logging.info(
                    "Removed rows " + last_seen_row + " and before from file " + file + " because they were seen before.")
            data_frames.append(df)
        result_data_frames = pd.concat(data_frames, ignore_index=True)
        return result_data_frames

    @staticmethod
    def _change_col_names(data, constants_file):
        # change the column names
        logging.info("Renaming columns for internal use.")
        rename_scheme = {}
        for pair in constants_file.__dict__:
            if not pair.startswith('_'):
                if (pair.startswith("p")):
                    pair = getattr(constants_file, pair)
                    rename_scheme[Variable.get(pair[0])] = pair[1]
        logging.info("Old column names:")
        logging.info(data.columns)
        data.rename(columns=rename_scheme, inplace=True)
        logging.info("New column names:")
        logging.info(data.columns)
        return data



    @staticmethod
    def _insert_into_db(data, table_name):
        # put in db
        logging.info("Inserting read data to database.")
        pdm = PostgresDatabaseManager()
        pdm.insert_into_table(data, table_name)

    @staticmethod
    def _make_xcom(ti, last_seen_file, last_seen_row):
        # send xcom about last read file and row
        logging.info("Sending xcom about last read information.")
        ti.xcom_push("last_seen_file", str(last_seen_file))
        ti.xcom_push("last_seen_row", str(last_seen_row))