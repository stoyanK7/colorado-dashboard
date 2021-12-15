import logging
import os
import shutil

import pandas as pd
from airflow.models import Variable

from config import aggregate_table_name_config, \
    clean_table_name_config, \
    last_seen_table_config, \
    read_table_name_config, \
    last_seen_column_name_config, preprocess_table_name_config

from DAL.postgres_database_manager import PostgresDatabaseManager


class CleanupTasks:

    @staticmethod
    def cleanup(ti):
        # Clean the data from the databases
        CleanupTasks._cleanup_tables(aggregate_table_name_config)
        CleanupTasks._cleanup_tables(clean_table_name_config)
        # CleanupTasks._cleanup_tables(last_seen_table_config)
        CleanupTasks._cleanup_tables(read_table_name_config)
        CleanupTasks._cleanup_tables(preprocess_table_name_config)

        CleanupTasks._cleanup_snapshot()
        # # save from X com into the database
        # CleanupTasks._xcom_to_db(ti,
        #                          last_seen_column_name_config.LAST_SEEN_IMAGE_FILE_PATH,
        #                          last_seen_column_name_config.LAST_SEEN_IMAGE_ROW_ID,
        #                          last_seen_table_config.LAST_SEEN_IMAGE_TABLE)

    @staticmethod
    def _cleanup_tables(table_name_config):
        pdm = PostgresDatabaseManager()

        # take all table_names from module table_name_config and clean them up
        for name in table_name_config.__dict__:
            if not name.startswith('_'):
                # print(getattr(table_name_config, name)) for testing
                table_name = getattr(table_name_config, name)

                logging.info(f"Cleaning the table {table_name}.")

                pdm.delete_table(table_name)

    @staticmethod
    def _xcom_to_db(ti, c1_name, c2_name, table_name):
        logging.info(f"Saving last seen file and last seen row in table {table_name}.")
        try:
            last_seen_file = ti.xcom_pull(task_ids='readImage', key='last_seen_file')  # to change task_id
            last_seen_row = ti.xcom_pull(task_ids='readImage', key='last_seen_row')  # to change task_id
        except:
            logging.error("Cleanup - There was a problem with reading the xcom.")
            return

        if not last_seen_file or not last_seen_row:
            logging.error("Cleanup - There was a problem with reading the xcom.")
            return

        CleanupTasks._cleanup_tables(last_seen_table_config)

        pdm = PostgresDatabaseManager()

        df = pd.DataFrame({c1_name: [last_seen_file], c2_name: [last_seen_row]})
        pdm.insert_into_table(df, table_name)

    @staticmethod
    def _cleanup_snapshot():
        logging.info("Removing last read files")
        if (os.path.exists(Variable.get("last_read_files_directory"))):
            shutil.rmtree(Variable.get("last_read_files_directory"))
        logging.info("Moving snapshot to last read files")
        os.rename(Variable.get("snapshot_directory"), Variable.get("last_read_files_directory"))
        pass
