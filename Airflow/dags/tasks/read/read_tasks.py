import logging

import pandas as pd
import re

import os
from airflow.models.variable import Variable

# from tasks.read.FileReader import FileReader
# from DAL.PostgresDatabaseManager import PostgresDatabaseManager
# from config import read_table_name_config, last_seen_table_config, last_seen_column_name_config
import config.read_image_column_name_config
from tasks.read.file_manager import FileManager
from tasks.read.file_reader import FileReader
from DAL.postgres_database_manager import PostgresDatabaseManager
from config import read_table_name_config, last_seen_table_config, last_seen_column_name_config, \
    read_image_col_name_constants, read_media_prepare_schema_col_name_constants, \
    read_media_prepare_db_col_name_constants, read_print_cycle_db_col_name_constants, \
    read_print_cycle_schema_col_name_constants


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
        # Get all data files in a list
        dataframes = ReadTasks._get_files_to_data_frames(new_files, changed_files, image_file_directory_extension, config.read_image_column_name_config.MACHINEID)

        # fix dataframes columns and concatenation them
        data = ReadTasks._concat_dataframes(dataframes)

        # Change col names
        ReadTasks._change_col_names(data, read_image_col_name_constants)

        # Insert to database
        ReadTasks._insert_into_db(data, read_table_name_config.READ_IMAGE)

    @staticmethod
    def read_media_prepare():
        rsync_directory = Variable.get("rsync_file_directory")
        snapshot_directory = Variable.get("snapshot_directory")
        last_seen_directory = Variable.get("last_read_files_directory")
        media_prepare_file_directory_extension = Variable.get("media_prepare_file_directory_extension")

        # Copy Rsync files to snapshot
        ReadTasks._make_snapshot(rsync_directory, snapshot_directory, media_prepare_file_directory_extension)

        # Get new files and different files
        new_files, changed_files = ReadTasks._get_new_and_changed_files(snapshot_directory, last_seen_directory,
                                                                        media_prepare_file_directory_extension)

        # Check for no new files
        if not ReadTasks._any_new_changed_files(new_files, changed_files):
            logging.info("No new or changed files were found, terminating reading step successfully.")
            return

        # Get file data (add machine ids)
        # Get all data files in a list
        dataframes = ReadTasks._get_files_to_data_frames(new_files, changed_files, media_prepare_file_directory_extension, read_media_prepare_db_col_name_constants.MACHINEID)

        # fix dataframes columns and concatenation them
        data = ReadTasks._concat_dataframes(dataframes)

        # Change col names
        ReadTasks._change_col_names(data, read_media_prepare_schema_col_name_constants)

        # Insert to database
        ReadTasks._insert_into_db(data, read_table_name_config.READ_MEDIA_PREPARE)

    @staticmethod
    def read_print_cycle():
        rsync_directory = Variable.get("rsync_file_directory")
        snapshot_directory = Variable.get("snapshot_directory")
        last_seen_directory = Variable.get("last_read_files_directory")
        print_cycle_file_directory_extension = Variable.get("print_cycle_file_directory_extension")

        # Copy Rsync files to snapshot
        ReadTasks._make_snapshot(rsync_directory, snapshot_directory, print_cycle_file_directory_extension)

        # Get new files and different files
        new_files, changed_files = ReadTasks._get_new_and_changed_files(snapshot_directory, last_seen_directory,
                                                                        print_cycle_file_directory_extension)

        # Check for no new files
        if not ReadTasks._any_new_changed_files(new_files, changed_files):
            logging.info("No new or changed files were found, terminating reading step successfully.")
            return

        # Get file data (add machine ids)
        # Get all data files in a list
        dataframes = ReadTasks._get_files_to_data_frames(new_files, changed_files, print_cycle_file_directory_extension,
                                                   read_print_cycle_db_col_name_constants.MACHINEID)

        # fix dataframes columns and concatenation them
        data = ReadTasks._concat_dataframes(dataframes)

        # Change col names
        ReadTasks._change_col_names(data, read_print_cycle_schema_col_name_constants)

        # Insert to database
        ReadTasks._insert_into_db(data, read_table_name_config.READ_PRINT_CYCLE)

    @staticmethod
    def _add_unit_columns(data):
        for col_name in data.columns:
            if "[" in col_name and "]" in col_name:

                # Get the unit
                unit = re.search(".+\[(.+)\](.+)?", col_name).group(1)

                # Get the name without the unit
                name_without_unit = re.search("(.+)\[.+\](.+)?", col_name).group(1)

                # Create a column name from the unit column based on the ink column
                new_col_name = re.search("(\D+)\[", col_name).group(1) + "|unit|"

                # Get the index of the future unit column
                index_no = data.columns.get_loc(col_name)
                new_col_index = index_no + 1

                # Removes units from the column's name
                data = data.rename(columns={col_name: name_without_unit})
                # Check if column exists
                if new_col_name not in data.columns:
                    # Insert the new column at the appropriate index and sets the value
                    data.insert(new_col_index, new_col_name, unit)

        return data

    @staticmethod
    def _concat_dataframes(dataframe_list):
        dataframes = []
        logging.info("Starting dataframe concatenation")
        for dataframe in dataframe_list:

            # Adding unit columns to the dataframe
            modified_df = ReadTasks._add_unit_columns(dataframe)

            # Appends the modified df to the list
            dataframes.append(modified_df)
        logging.info("Passed dataframe concatenation")
        return pd.concat(dataframes, ignore_index=True)

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
        new_files, changed_files = file_reader.get_different_files(updated_dir, reference_dir, extension_to_folder)
        new_changed_files_count = 0
        for machine_id in new_files:
            new_changed_files_count += len(new_files[machine_id])
        for machine_id in changed_files:
            new_changed_files_count += len(changed_files[machine_id])
        logging.info(f"Found {new_changed_files_count} new or changed files")
        return new_files, changed_files

    @staticmethod
    def _any_new_changed_files(new_files, changed_files):
        logging.info("Checking if there are any new or changed in files")
        any_new = False
        for machine_id in new_files:
            if len(new_files[machine_id]) > 0:
                any_new = True
                break
        for machine_id in changed_files:
            if len(changed_files[machine_id]) > 0:
                any_new = True
                break
        return any_new

    @staticmethod
    def _get_files_to_data_frames(new_files, changed_files, identifier_column_name, machine_id_column_name):
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
        return dataframes

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
        logging.info("Renaming column names that include a % because of sql injection")
        data.columns = data.columns.str.replace('%', 'percent')
        logging.info("Inserting read data to database.")
        pdm = PostgresDatabaseManager()
        pdm.insert_into_table(data, table_name)