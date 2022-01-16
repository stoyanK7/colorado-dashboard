import logging

import pymysql
import pandas as pd
from airflow.models import Variable
from sqlalchemy import create_engine, text

from config import aggregate_table_name_config, aggregate_column_name_config, preprocess_col_name_constants
from DAL.postgres_database_manager import PostgresDatabaseManager
from tabulate import tabulate

from config.aggregate_column_name_config import DATE
from config.aggregate_table_name_config import AGGREGATE_MEDIA_CATEGORY_USAGE, AGGREGATE_INK_USAGE
from config.preprocess_col_name_constants import MACHINEID


class LoadTasks:


    @staticmethod
    def load_media_category_usage():
        pass

    @staticmethod
    def load_sqm_per_print_mode():
        pass

    @staticmethod
    def load_ink_usage():
        df = LoadTasks._read_from_db_postgresql(aggregate_table_name_config.AGGREGATE_INK_USAGE)

        api_table_name = Variable.get("api_ink_usage_table_name")
        date_col = Variable.get("api_date_col_name")
        start_date = df[DATE].min()
        end_date = df[DATE].max()
        machine_id = preprocess_col_name_constants.MACHINEID
        api_df = LoadTasks._read_existing_data_from_api(api_table_name, date_col, start_date, end_date)

        LoadTasks._delete_existing_data_from_api(api_table_name, date_col, start_date, end_date)

        final_df = LoadTasks._merge_existing_with_new_data(df, api_df, date_col, machine_id)

        LoadTasks._send_data_to_api(api_table_name, final_df)


    @staticmethod
    def load_top_ten_print_volume():
        pass
        df = LoadTasks._read_from_db_postgresql(aggregate_table_name_config.AGGREGATE_TOP_TEN_PRINT_VOLUME)

        api_table_name = Variable.get("api_top_ten_print_volume_table_name")
        date_col = Variable.get("api_date_col_name")
        start_date = df[DATE].min()
        end_date = df[DATE].max()
        machine_id = preprocess_col_name_constants.MACHINEID
        api_df = LoadTasks._read_existing_data_from_api(api_table_name, date_col, start_date, end_date)

        LoadTasks._delete_existing_data_from_api(api_table_name, date_col, start_date, end_date)

        final_df = LoadTasks._merge_existing_with_new_data(df, api_df, date_col, machine_id)

        LoadTasks._send_data_to_api(api_table_name, final_df)

    @staticmethod
    def load_media_types_per_machine():
        pass
        df = LoadTasks._read_from_db_postgresql(aggregate_table_name_config.AGGREGATE_MEDIA_TYPES_PER_MACHINE)

        api_table_name = Variable.get("api_media_types_per_machine_table_name")
        date_col = Variable.get("api_date_col_name")
        start_date = df[DATE].min()
        end_date = df[DATE].max()
        machine_id = preprocess_col_name_constants.MACHINEID
        api_df = LoadTasks._read_existing_data_from_api(api_table_name, date_col, start_date, end_date)

        LoadTasks._delete_existing_data_from_api(api_table_name, date_col, start_date, end_date)

        final_df = LoadTasks._merge_existing_with_new_data(df, api_df, date_col, machine_id)

        LoadTasks._send_data_to_api(api_table_name, final_df)

    @staticmethod
    def _read_from_db_postgresql(table_name) -> pd.DataFrame:
        logging.info("Reading the data from internal database.")
        pdm = PostgresDatabaseManager()
        df = pdm.read_table(table_name)
        return df

    @staticmethod
    def _read_existing_data_from_api(table_name, date_col_name, start_date, end_date):
        logging.info(f"Reading existing data in api database from table {table_name} between {start_date} and {end_date}")
        result = None
        connection = LoadTasks._connect_to_api_database()
        try:
            sql = f"SELECT * FROM {table_name} WHERE {date_col_name} BETWEEN '{start_date}' AND '{end_date}'"
            result = pd.read_sql(sql, connection)
        finally:
            connection.close()
        return result


    @staticmethod
    def _delete_existing_data_from_api(table_name, date_col_name, start_date, end_date):
        logging.info(f"Deleting existing data in api database from table {table_name} between {start_date} and {end_date}")
        connection = LoadTasks._connect_to_api_database()
        try:
            connection.execute(
                text(f"DELETE FROM {table_name} WHERE {date_col_name} BETWEEN '{start_date}' AND '{end_date}'"))
        finally:
            connection.close()

    @staticmethod
    def _merge_existing_with_new_data(existing_data, new_data, date_col, printer_id_col):
        logging.info("Merging existing data with new data")
        concat_df = pd.concat([existing_data, new_data])
        return concat_df.groupby([date_col, printer_id_col]).sum().reset_index()

    @staticmethod
    def _send_data_to_api(table_name, df):
        logging.info(f"Pushing new data to api table {table_name}")
        connection = LoadTasks._connect_to_api_database()
        try:
            df.to_sql(table_name, connection, if_exists="append", index=False)
        finally:
            connection.close()


    @staticmethod
    def _connect_to_api_database():
        logging.info("Opening connection to api database")
        engine = create_engine("mysql+pymysql://canon:canon@host.docker.internal:3306/canon", pool_recycle=3600)
        return engine.connect()