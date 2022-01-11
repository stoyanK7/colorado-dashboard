import logging

import pandas as pd
import re

from config import aggregate_column_name_config, clean_table_name_config, \
    clean_image_col_name_constants, preprocess_table_name_config, clean_print_cycle_col_name_constants, \
    clean_media_prepare_col_name_constants, preprocess_col_name_constants
from DAL.postgres_database_manager import PostgresDatabaseManager


class PreprocessTasks():
    @staticmethod
    def preprocess_media_category_usage():
        return
        # The columns to mach the database names
        logging.info("Start preprocess for media category usage.")
        # Take the dataframe from the previous step
        df = PreprocessTasks._read_from_db(clean_table_name_config.READ_IMAGE)

        df = df.rename(columns={clean_image_col_name_constants.MACHINEID: preprocess_col_name_constants.MACHINEID})
        df = df.rename(columns={clean_image_col_name_constants.DATE: preprocess_col_name_constants.DATE})
        # Save dataframe into database
        PreprocessTasks._insert_into_db(df, preprocess_table_name_config.PREPROCESS_MEDIA_CATEGORY_USAGE)

    @staticmethod
    def preprocess_sqm_per_print_mode():
        return
        # The columns to mach the database names
        logging.info("Start preprocess for media category usage.")
        # Take the dataframe from the previous step
        df = PreprocessTasks._read_from_db(clean_table_name_config.READ_PRINT_CYCLE)

        df = df.rename(columns={clean_image_col_name_constants.MACHINEID: preprocess_col_name_constants.MACHINEID})
        df = df.rename(columns={clean_image_col_name_constants.DATE: preprocess_col_name_constants.DATE})
        # Save dataframe into database
        PreprocessTasks._insert_into_db(df, preprocess_table_name_config.PREPROCESS_SQM_PER_PRINT_MODE)

    @staticmethod
    def preprocess_ink_usage():
        logging.info("Start preprocess for ink usage.")
        # Take the dataframe from the previous step
        df = PreprocessTasks._read_from_db(clean_table_name_config.READ_IMAGE)
        # Convert the columns to their appropriate unit type
        df = PreprocessTasks._converting_units_to_default_values(df)
        df = df.rename(columns={clean_image_col_name_constants.MACHINEID: preprocess_col_name_constants.MACHINEID})
        df = df.rename(columns={clean_image_col_name_constants.DATE: preprocess_col_name_constants.DATE})
        df = df.rename(columns={
            clean_image_col_name_constants.ACCOUNTED_INK_CYAN: preprocess_col_name_constants.ACCOUNTED_INK_CYAN})
        df = df.rename(columns={
            clean_image_col_name_constants.ACCOUNTED_INK_MAGENTA: preprocess_col_name_constants.ACCOUNTED_INK_MAGENTA})
        df = df.rename(columns={
            clean_image_col_name_constants.ACCOUNTED_INK_YELLOW: preprocess_col_name_constants.ACCOUNTED_INK_YELLOW})
        df = df.rename(columns={
            clean_image_col_name_constants.ACCOUNTED_INK_BLACK: preprocess_col_name_constants.ACCOUNTED_INK_BLACK})
        PreprocessTasks._insert_into_db(df, preprocess_table_name_config.PREPROCESS_INK_USAGE)

    @staticmethod
    def preprocess_media_types_per_machine():
        logging.info("Start preprocess for types per machine.")
        # Take the dataframes from mediaPrepare and PrintCycle table
        df1 = PreprocessTasks._read_from_db(clean_table_name_config.READ_MEDIA_PREPARE)
        df2 = PreprocessTasks._read_from_db(clean_table_name_config.READ_PRINT_CYCLE)

        # Merge the dataframes
        df = PreprocessTasks._merge_two_dataframes(df1, df2, clean_print_cycle_col_name_constants.ENGINE_CYCLE_ID)
        # Make sure to not have unnecessary data
        df = df[[clean_print_cycle_col_name_constants.DATE + "_x",
                 clean_media_prepare_col_name_constants.MEDIA_TYPE_DISPLAY_NAME,
                 clean_print_cycle_col_name_constants.SQUARE_DECIMETER,
                 clean_print_cycle_col_name_constants.MACHINEID + "_x"]]

        # Fix date column from date_x to date
        df = df.rename(
            columns={clean_print_cycle_col_name_constants.DATE + "_x": clean_print_cycle_col_name_constants.DATE})
        df = df.rename(
            columns={
                clean_print_cycle_col_name_constants.MACHINEID + "_x": clean_print_cycle_col_name_constants.MACHINEID})

        # Divide two column by 100
        df = PreprocessTasks._divide_column_by(df,
                                               clean_print_cycle_col_name_constants.SQUARE_DECIMETER,
                                               preprocess_col_name_constants.PREPROCESSED_SQUARE_DECIMETER,
                                               100)
        print(f"After dividing: {df.columns}")
        df = df.rename(
            columns={clean_print_cycle_col_name_constants.MACHINEID: preprocess_col_name_constants.MACHINEID})
        df = df.rename(columns={clean_print_cycle_col_name_constants.DATE: preprocess_col_name_constants.DATE})
        df = df.rename(columns={
            clean_media_prepare_col_name_constants.MEDIA_TYPE_DISPLAY_NAME: preprocess_col_name_constants.MEDIA_TYPE_DISPLAY_NAME})
        print(f"After renaming: {df.columns}")

        # Save dataframe into database
        PreprocessTasks._insert_into_db(df, preprocess_table_name_config.PREPROCESS_MEDIA_TYPES_PER_MACHINE)

    @staticmethod
    def preprocess_top_ten_print_volume():
        # The columns to mach the database names
        logging.info("Start preprocess for media category usage.")
        # Take the dataframe from the previous step
        df = PreprocessTasks._read_from_db(clean_table_name_config.READ_PRINT_CYCLE)

        # Divide two column by 100
        df = PreprocessTasks._divide_column_by(df,
                                               clean_print_cycle_col_name_constants.SQUARE_DECIMETER,
                                               preprocess_col_name_constants.PREPROCESSED_SQUARE_DECIMETER,
                                               100)

        df = df.rename(
            columns={clean_print_cycle_col_name_constants.MACHINEID: preprocess_col_name_constants.MACHINEID})
        df = df.rename(columns={clean_print_cycle_col_name_constants.DATE: preprocess_col_name_constants.DATE})
        # Save dataframe into database
        PreprocessTasks._insert_into_db(df, preprocess_table_name_config.PREPROCESS_TOP_TEN_PRINT_VOLUME)

    @staticmethod
    def _divide_column_by(df, column, column_new_name, number):
        logging.info(f"Preprocess - divide column {column} by {number}.")
        df[column] = df[column] / number
        df = df.rename(columns={column: column_new_name})
        return df

    @staticmethod
    def _merge_two_dataframes(df1, df2, column_for_merging):
        logging.info("Preprocess - merge two dataframes.")
        df = pd.merge(left=df1, right=df2, left_on=column_for_merging, right_on=column_for_merging)
        return df

    @staticmethod
    def _converting_units_to_default_values(df):
        unit_columns = []
        #logging.info(f"\n {df.to_string()}")
        # Gets all column names with units
        for col_name in df.columns:
            if "|unit|" in col_name:
                # Saves each one to a list
                unit_columns.append(col_name)

        # Iterates through all rows and updates them
        for index, row in df.iterrows():
            for unit_col_name in unit_columns:

                # Gets the index of the unit column
                unit_index = df.columns.get_loc(unit_col_name)

                # Gets the index of the data for which is the unit column
                data_index = unit_index - 1

                # Gets the name of the data column
                data_col_name = df.columns[data_index]

                # Gets the values of the columns
                row_data_value = row[data_col_name]
                row_unit_value = row[unit_col_name]

                if row_unit_value == "cl":    # Converts to from one of the units below to milliliters
                    row[data_col_name] = row_data_value * 10
                elif row_unit_value == "dl":
                    row[data_col_name] = row_data_value * 100
                elif row_unit_value == "l":
                    row[data_col_name] = row_data_value * 1000
                elif row_unit_value == "mm":  # Converts to from one of the units below to meters
                    row[data_col_name] = row_data_value / 1000
                elif row_unit_value == "cm":
                    row[data_col_name] = row_data_value / 100
                elif row_unit_value == "dm":
                    row[data_col_name] = row_data_value / 10

        # Drop all unit columns after the conversion
        df.drop(columns=unit_columns)

        #logging.info(f"\n {df.to_string()}")
        return df

    @staticmethod
    def _read_from_db(table_name) -> pd.DataFrame:
        # read from db
        logging.info(f"Preprocess - reading table {table_name} from database.")
        pdm = PostgresDatabaseManager()
        df = pdm.read_table(table_name)
        if df.empty:
            return df
        df = df.set_index(aggregate_column_name_config.ULLID)
        return df

    @staticmethod
    def _insert_into_db(df, table_name):
        # put in db
        logging.info("Preprocess - inserting preprocessed data to database.")
        pdm = PostgresDatabaseManager()
        pdm.insert_into_table(df, table_name)
        logging.info("Preprocess finished!")
