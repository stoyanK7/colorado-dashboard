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
        df = LoadTasks._read_from_db_postgresql(aggregate_table_name_config.AGGREGATE_MEDIA_CATEGORY_USAGE)
        if df.empty:
            logging.info("No new data was found, skipping step.")
            return

        api_table_name = Variable.get("api_media_category_usage_table_name")
        date_col = Variable.get("api_date_col_name")
        start_date = df[DATE].min()
        end_date = df[DATE].max()
        machine_id = preprocess_col_name_constants.MACHINEID
        media_category = preprocess_col_name_constants.MEDIA_TYPE
        api_df = LoadTasks._read_existing_data_from_api(api_table_name, date_col, start_date, end_date)

        LoadTasks._set_date_type(api_df, df, date_col)

        LoadTasks._delete_existing_data_from_api(api_table_name, date_col, start_date, end_date)

        final_df = LoadTasks._merge_existing_with_new_data(df, api_df, [date_col, machine_id, media_category])

        LoadTasks._send_data_to_api(api_table_name, final_df)

    @staticmethod
    def load_sqm_per_print_mode():
        df = LoadTasks._read_from_db_postgresql(aggregate_table_name_config.AGGREGATE_SQM_PER_PRINT_MODE)
        if df.empty:
            logging.info("No new data was found, skipping step.")
            return

        api_table_name = Variable.get("api_square_meters_per_print_mode_table_name")
        date_col = Variable.get("api_date_col_name")
        start_date = df[DATE].min()
        end_date = df[DATE].max()
        machine_id = preprocess_col_name_constants.MACHINEID
        print_mode = preprocess_col_name_constants.PRINT_MODE
        api_df = LoadTasks._read_existing_data_from_api(api_table_name, date_col, start_date, end_date)

        LoadTasks._set_date_type(api_df, df, date_col)

        LoadTasks._delete_existing_data_from_api(api_table_name, date_col, start_date, end_date)

        final_df = LoadTasks._merge_existing_with_new_data(df, api_df, [date_col, machine_id, print_mode])

        LoadTasks._send_data_to_api(api_table_name, final_df)

    @staticmethod
    def load_ink_usage():
        df = LoadTasks._read_from_db_postgresql(aggregate_table_name_config.AGGREGATE_INK_USAGE)
        if df.empty:
            logging.info("No new data was found, skipping step.")
            return

        api_table_name = Variable.get("api_ink_usage_table_name")
        date_col = Variable.get("api_date_col_name")
        start_date = df[DATE].min()
        end_date = df[DATE].max()
        machine_id = preprocess_col_name_constants.MACHINEID
        api_df = LoadTasks._read_existing_data_from_api(api_table_name, date_col, start_date, end_date)

        LoadTasks._set_date_type(api_df, df, date_col)

        LoadTasks._delete_existing_data_from_api(api_table_name, date_col, start_date, end_date)

        final_df = LoadTasks._merge_existing_with_new_data(df, api_df, [date_col, machine_id])

        LoadTasks._send_data_to_api(api_table_name, final_df)


    @staticmethod
    def load_top_ten_print_volume():
        df = LoadTasks._read_from_db_postgresql(aggregate_table_name_config.AGGREGATE_TOP_TEN_PRINT_VOLUME)
        if df.empty:
            logging.info("No new data was found, skipping step.")
            return

        api_table_name = Variable.get("api_top_ten_print_volume_table_name")
        date_col = Variable.get("api_date_col_name")
        start_date = df[DATE].min()
        end_date = df[DATE].max()
        machine_id = preprocess_col_name_constants.MACHINEID
        api_df = LoadTasks._read_existing_data_from_api(api_table_name, date_col, start_date, end_date)

        LoadTasks._set_date_type(api_df, df, date_col)

        LoadTasks._delete_existing_data_from_api(api_table_name, date_col, start_date, end_date)

        final_df = LoadTasks._merge_existing_with_new_data(df, api_df, [date_col, machine_id])

        LoadTasks._send_data_to_api(api_table_name, final_df)

    @staticmethod
    def load_media_types_per_machine():
        df = LoadTasks._read_from_db_postgresql(aggregate_table_name_config.AGGREGATE_MEDIA_TYPES_PER_MACHINE)
        if df.empty:
            logging.info("No new data was found, skipping step.")
            return

        api_table_name = Variable.get("api_media_types_per_machine_table_name")
        date_col = Variable.get("api_date_col_name")
        start_date = df[DATE].min()
        end_date = df[DATE].max()
        machine_id = preprocess_col_name_constants.MACHINEID
        media_type = preprocess_col_name_constants.MEDIA_TYPE_DISPLAY_NAME
        api_df = LoadTasks._read_existing_data_from_api(api_table_name, date_col, start_date, end_date)

        LoadTasks._set_date_type(api_df, df, date_col)

        LoadTasks._delete_existing_data_from_api(api_table_name, date_col, start_date, end_date)

        final_df = LoadTasks._merge_existing_with_new_data(df, api_df, [date_col, machine_id, media_type])

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
            result = result.drop(["id"], axis=1)
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
    def _merge_existing_with_new_data(pipeline_data: pd.DataFrame, api_data: pd.DataFrame, cols):
        logging.info("Merging existing data with new data")
        concat_df = pd.concat([pipeline_data, api_data]).reset_index(drop=True)
        grouped = concat_df.groupby(cols, as_index=False).sum().reset_index(drop=True)
        return grouped

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

    @staticmethod
    def _set_date_type(api_df, df, date_col):
        api_df[date_col] = pd.to_datetime(api_df[date_col])
        df[date_col] = pd.to_datetime(df[date_col])