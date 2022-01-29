import logging

# from tabulate import tabulate
import pandas as pd

from config import aggregate_table_name_config, \
    aggregate_column_name_config, clean_image_col_name_constants, \
    preprocess_table_name_config, preprocess_col_name_constants
from DAL.postgres_database_manager import PostgresDatabaseManager


class AggregateTasks:

    @staticmethod
    def aggregate_media_category_usage():
        # Take the dataframe from the previous step
        df = AggregateTasks._read_from_db(preprocess_table_name_config.PREPROCESS_MEDIA_CATEGORY_USAGE)
        if df.empty:
            logging.info("No new data was found, skipping step.")
            return
        # Multiply ImageLength and ImageWidth into Area column
        df = AggregateTasks._aggregate_two_columns(df,
                                                   clean_image_col_name_constants.IMAGE_WIDTH,
                                                   clean_image_col_name_constants.IMAGE_LENGTH,
                                                   aggregate_column_name_config.IMAGE_AREA,
                                                   True)

        # Group
        df = AggregateTasks._group_by_three_columns_and_sum_third(df,
                                                                  preprocess_col_name_constants.DATE,
                                                                  preprocess_col_name_constants.MEDIA_TYPE,
                                                                  preprocess_col_name_constants.MACHINEID,
                                                                  aggregate_column_name_config.IMAGE_AREA)
        # Save into a database
        AggregateTasks._insert_into_db(df, aggregate_table_name_config.AGGREGATE_MEDIA_CATEGORY_USAGE)

    @staticmethod
    def aggregate_sqm_per_print_mode():
        # Take the dataframe from the previous step
        df = AggregateTasks._read_from_db(preprocess_table_name_config.PREPROCESS_SQM_PER_PRINT_MODE)
        if df.empty:
            logging.info("No new data was found, skipping step.")
            return

        # Group
        df = AggregateTasks._group_by_three_columns_and_sum_third(df,
                                                                  preprocess_col_name_constants.DATE,
                                                                  preprocess_col_name_constants.MACHINEID,
                                                                  preprocess_col_name_constants.PRINT_MODE,
                                                                  preprocess_col_name_constants.SQUARE_DECIMETER)
        # Save into a database
        AggregateTasks._insert_into_db(df, aggregate_table_name_config.AGGREGATE_SQM_PER_PRINT_MODE)

    @staticmethod
    def aggregate_ink_usage():
        # Take the dataframe from the previous step
        df = AggregateTasks._read_from_db(preprocess_table_name_config.PREPROCESS_INK_USAGE)
        if df.empty:
            logging.info("No new data was found, skipping step.")
            return

        # Group
        df = AggregateTasks._group_by_two_columns_and_sum(df,
                                                          preprocess_col_name_constants.DATE,
                                                          preprocess_col_name_constants.MACHINEID)
        # Save into a database
        AggregateTasks._insert_into_db(df, aggregate_table_name_config.AGGREGATE_INK_USAGE)

    @staticmethod
    def aggregate_top_ten_print_volume():
        # Take the dataframe from the previous step
        df = AggregateTasks._read_from_db(preprocess_table_name_config.PREPROCESS_TOP_TEN_PRINT_VOLUME)
        if df.empty:
            logging.info("No new data was found, skipping step.")
            return

        # Group
        df = AggregateTasks._group_by_two_columns_and_sum_third(df,
                                                                preprocess_col_name_constants.DATE,
                                                                preprocess_col_name_constants.MACHINEID,
                                                                preprocess_col_name_constants.SQUARE_DECIMETER)
        # Save into a database
        AggregateTasks._insert_into_db(df, aggregate_table_name_config.AGGREGATE_TOP_TEN_PRINT_VOLUME)

    @staticmethod
    def aggregate_media_types_per_machine():
        # Take the dataframe from the previous step
        df = AggregateTasks._read_from_db(preprocess_table_name_config.PREPROCESS_MEDIA_TYPES_PER_MACHINE)
        if df.empty:
            logging.info("No new data was found, skipping step.")
            return

        # Group
        df = AggregateTasks._group_by_three_columns_and_sum_third(df,
                                                                  preprocess_col_name_constants.DATE,
                                                                  preprocess_col_name_constants.MACHINEID,
                                                                  preprocess_col_name_constants.MEDIA_TYPE_DISPLAY_NAME,
                                                                  preprocess_col_name_constants.SQUARE_DECIMETER)
        # Save into a database
        AggregateTasks._insert_into_db(df, aggregate_table_name_config.AGGREGATE_MEDIA_TYPES_PER_MACHINE)

    @staticmethod
    def _read_from_db(table_name) -> pd.DataFrame:
        # read from db
        logging.info("Reading the cleaned data from database.")
        pdm = PostgresDatabaseManager()
        df = pdm.read_table(table_name)
        if df.empty:
            return df
        return df

    @staticmethod
    def _aggregate_two_columns(df, c1, c2, new_column, to_delete):  # tested
        # aggregate two columns
        logging.info(f"Aggregation of {c1} and {c2} into {new_column}.")
        df[new_column] = df[c1] * df[c2]
        if to_delete:
            logging.info(f"Columns: {c1} and {c2} are dropped")
            df = df.drop(columns=[c1, c2])
        return df

    @staticmethod
    def _group_by_three_columns_and_sum_third(df, c1, c2, c3, col_to_sum):  # tested
        # group three columns and sum the third
        logging.info(f"Grouping {c1}, {c2}, {c3} and summing {col_to_sum}.")
        df = df.groupby([c1, c2, c3], as_index=False)[col_to_sum].sum()
        return df

    @staticmethod
    def _group_by_two_columns_and_sum_third(df, c1, c2, col_to_sum):  # tested
        # group two columns and sum the third
        logging.info(f"Grouping {c1}, {c2} and summing {col_to_sum}.")
        df = df.groupby([c1, c2], as_index=False)[col_to_sum].sum()
        return df

    @staticmethod
    def _group_by_two_columns_and_sum(df, c1, c2):  # tested
        # group two columns and sum
        logging.info(f"Grouping {c1}, {c2}.")
        df = df.groupby([c1, c2], as_index=False).sum()
        return df

    @staticmethod
    def _insert_into_db(df, table_name):
        # put in db
        logging.info("Inserting aggregated data to database.")
        # print(tabulate(df, headers='keys', tablefmt='psql'))
        pdm = PostgresDatabaseManager()
        pdm.insert_into_table(df, table_name)
