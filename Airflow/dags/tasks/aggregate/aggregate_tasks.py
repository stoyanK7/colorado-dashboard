import logging

import pandas as pd
from tabulate import tabulate

from config import aggregate_table_name_config, aggregate_column_name_config, clean_table_name_config, clean_image_col_name_constants
from DAL.postgres_database_manager import PostgresDatabaseManager


class AggregateTasks:

    @staticmethod
    def aggregate_media_category_usage():
        # Take the dataframe from the previous step
        df = AggregateTasks.__read_from_db(clean_table_name_config.READ_IMAGE)
        if df.empty:
            logging.info("No new data was found, skipping step.")
            return
        # Multiply ImageLength and ImageWidth into Area column
        df = AggregateTasks._aggregate_two_columns(df,
                                                   cleaning_column_name_config.IMAGE_WIDTH,
                                                   cleaning_column_name_config.IMAGE_LENGTH,
                                                   aggregate_column_name_config.IMAGE_AREA,
                                                   True)

        # Group
        df = AggregateTasks._group_by_two_columns_and_sum_third(df,
                                                                cleaning_column_name_config.DATE,
                                                                cleaning_column_name_config.MEDIA_TYPE,
                                                                aggregate_column_name_config.IMAGE_AREA)
        # Save into a database
        AggregateTasks._insert_into_db(df, aggregate_table_name_config.AGGREGATE_IMAGE)

    @staticmethod
    def aggregate_sqm_per_print_mode():
        pass

    @staticmethod
    def aggregate_ink_usage():
        pass

    @staticmethod
    def aggregate_top_ten_print_volume():
        pass

    @staticmethod
    def aggregate_media_types_per_machine():
        pass


    @staticmethod
    def __read_from_db(table_name) -> pd.DataFrame:
        # read from db
        logging.info("Reading the cleaned data from database.")
        pdm = PostgresDatabaseManager()
        df = pdm.read_table(table_name)
        if df.empty:
            return df
        df = df.set_index(aggregate_column_name_config.ULLID)
        return df

    @staticmethod
    def _aggregate_two_columns(df, c1, c2, new_column, to_delete):
        # aggregate two columns
        logging.info(f"Aggregation of {c1} and {c2} into {new_column}.")
        df[new_column] = df[c1] * df[c2]
        if to_delete:
            logging.info(f"Columns: {c1} and {c2} are dropped")
            df = df.drop(columns=[c1, c2])
        return df

    @staticmethod
    def _group_by_two_columns_and_sum_third(df, c1, c2, col_to_sum):
        # group two columns and sum the third
        logging.info(f"Grouping {c1} and {c2} and summing {col_to_sum}.")
        df = pd.to_numeric(df.groupby([c1, c2])[col_to_sum].sum(), errors='coerce')
        return df

    @staticmethod
    def _insert_into_db(df, table_name):
        # put in db
        logging.info("Inserting aggregated data to database.")
        pdm = PostgresDatabaseManager()
        pdm.insert_into_table(df, table_name)
