import logging

import pandas as pd

from config import AggregateTableNameConfig, \
    CleanTableNameConfig, \
    LastSeenTableConfig, \
    ReadTableNameConfig, \
    LastSeenColumnNameConfig

from DAL.PostgresDatabaseManager import PostgresDatabaseManager


class CleanupTasks:

    @staticmethod
    def cleanup(ti):
        # Clean the data from the databases
        CleanupTasks._cleanup_tables(AggregateTableNameConfig)
        CleanupTasks._cleanup_tables(CleanTableNameConfig)
        # CleanupTasks._cleanup_tables(LastSeenTableConfig)
        CleanupTasks._cleanup_tables(ReadTableNameConfig)

        # save from X com into the database
        CleanupTasks._xcom_to_db(ti,
                                 LastSeenColumnNameConfig.LAST_SEEN_IMAGE_FILE_PATH,
                                 LastSeenColumnNameConfig.LAST_SEEN_IMAGE_ROW_ID,
                                 LastSeenTableConfig.LAST_SEEN_IMAGE_TABLE)

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
            last_seen_file = ti.xcom_pull(task_ids='readImage', key='lastSeenFile')  # to change task_id
            last_seen_row = ti.xcom_pull(task_ids='readImage', key='lastSeenRow')  # to change task_id
        except:
            logging.error("Cleanup - There was a problem with reading the xcom.")
            return

        if not last_seen_file or not last_seen_row:
            logging.error("Cleanup - There was a problem with reading the xcom.")
            return

        CleanupTasks._cleanup_tables(LastSeenTableConfig)

        pdm = PostgresDatabaseManager()

        df = pd.DataFrame({c1_name: [last_seen_file], c2_name: [last_seen_row]})
        pdm.insert_into_table(df, table_name)
