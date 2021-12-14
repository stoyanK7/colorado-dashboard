import logging

import pandas as pd
import os
from airflow.models.variable import Variable

# from tasks.read.FileReader import FileReader
# from DAL.PostgresDatabaseManager import PostgresDatabaseManager
# from config import read_table_name_config, last_seen_table_config, last_seen_column_name_config
from config.read_column_name_config import MACHINEID
from tasks.read.file_manager import FileManager
from tasks.read.file_reader import FileReader
from DAL.postgres_database_manager import PostgresDatabaseManager
from config import read_table_name_config, last_seen_table_config, last_seen_column_name_config, read_image_col_name_constants


class ReadTasks():

    @staticmethod
    def read_image(ti):
        rsync_directory = Variable.get("rsync_file_directory")
        snapshot_directory = Variable.get("snapshot_directory")
        last_seen_directory = Variable.get("last_read_files_directory")
        image_file_directory_extension = Variable.get("image_file_directory_extension")

        # Copy Rsync files to snapshot
        ReadTasks._make_snapshot(rsync_directory, snapshot_directory, image_file_directory_extension)

        # Get new files and different files
        new_files, changed_files = ReadTasks._get_new_and_changed_files(snapshot_directory, last_seen_directory, image_file_directory_extension)

        # Check for no new files
        if not ReadTasks._any_new_changed_files(new_files, changed_files):
            logging.info("No new or changed files were found, terminating reading step successfully.")
            return

        # Get file data (add machine ids)
        data = ReadTasks._get_files_to_data_frames(new_files, changed_files, image_file_directory_extension, MACHINEID)

        # Change col names
        ReadTasks._change_col_names(data, read_image_col_name_constants)

        # Insert to database
        ReadTasks._insert_into_db(data, read_table_name_config.READ_IMAGE)
        pass

        # ReadTasks._make_xcom(ti, files_to_read[len(files_to_read) - 1], data[Variable.get("image_col_name_ullid")].iloc[-1])
    @staticmethod
    def read_media_prepare():
        # do stuff, remove pass
        pass
    @staticmethod
    def read_print_cycle():
        # do stuff, remove pass
        pass

    @staticmethod
    def _make_snapshot(source_dir, target_dir, extension_to_folder):
        logging.info(f"Making a snapshot by copying files from {source_dir} to {target_dir} focussing on the files at machine_id{extension_to_folder}")
        file_manager = FileManager()
        file_manager.copy_files_to_dir(source_dir, target_dir, extension_to_folder)
        logging.info("Making snapshot finished")

    @staticmethod
    def _get_new_and_changed_files(updated_dir, reference_dir, extension_to_folder):
        logging.info(f"Getting the new and changed files by comparing the files in {updated_dir} to the files in {reference_dir} at the subdirectory machine_id{extension_to_folder}")
        file_reader = FileReader()
        return file_reader.get_different_files(updated_dir, reference_dir, extension_to_folder)

    @staticmethod
    def _any_new_changed_files(new_files, changed_files):
        logging.info("Checking if there are any new or changed in files")
        any_new = False
        for machine_id in new_files:
            if len(machine_id) > 0:
                any_new = True
                break
        for machine_id in changed_files:
            if len(machine_id) > 0:
                any_new = True
                break
        return any_new

    @staticmethod
    def _get_files_to_data_frames(new_files, changed_files, identifier_column_name, machine_id_column_name) -> pd.DataFrame:
        # get files
        file_reader = FileReader()
        dataframes = []
        logging.info("Getting data from new files")
        for machine_id in new_files:
            for new_file in new_files[machine_id]:
                # logging.info(f"Getting data from file {new_file}.")
                df = file_reader.read_pandas_csv_file(new_file, ";")
                # logging.info(f"Changing index to {identifier_column_name}")
                # logging.info(f"Adding machine id column with value {int(machine_id)}")
                df[machine_id_column_name] = int(machine_id)
                dataframes.append(df)
        logging.info("Getting data from changed files")
        for machine_id in changed_files:
            for changed_file in changed_files[machine_id]:
                logging.info(f"Getting new data from {changed_file}.")
                old_file = changed_file["old"]
                new_file = changed_file["new"]
                # logging.info(f"Getting data from file {old_file}.")
                df_old = file_reader.read_pandas_csv_file(old_file, ";")
                # logging.info(f"Getting data from file {new_file}.")
                df_new = file_reader.read_pandas_csv_file(new_file, ";")
                # logging.info("Substracting dataframes")
                # logging.info(f"Changing index to {identifier_column_name}")
                df_all = pd.concat([df_new,df_old]).drop_duplicates(keep=False, subset='ullid',ignore_index=True)
                # logging.info(f"Number of added columns: {len(df_all.index)}")
                # logging.info(f"Adding machine id column with value {int(machine_id)}")
                df_all[machine_id_column_name] = int(machine_id)
                dataframes.append(df_all)
        return pd.concat(dataframes, ignore_index=True)

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