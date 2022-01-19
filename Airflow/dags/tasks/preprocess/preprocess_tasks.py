import logging

import pandas as pd
import numpy as np
import re
from datetime import datetime
import pytz

from config import clean_table_name_config, \
    clean_image_col_name_constants, preprocess_table_name_config, clean_print_cycle_col_name_constants, \
    clean_media_prepare_col_name_constants, preprocess_col_name_constants
from DAL.postgres_database_manager import PostgresDatabaseManager


class PreprocessTasks():
    @staticmethod
    def preprocess_media_category_usage():
        logging.info("Start preprocess for media category usage.")
        # Take the dataframe from the previous step
        df = PreprocessTasks._read_from_db(clean_table_name_config.READ_IMAGE)

        # Skip if no new data
        if df.empty:
            logging.info("No new data was found, skipping step.")
            return

        # Columns to be removed from the table
        columns_to_drop = [clean_image_col_name_constants.LOCAL_TIME,
                           clean_image_col_name_constants.ACCOUNTED_INK_BLACK,
                           clean_image_col_name_constants.ACCOUNTED_INK_YELLOW,
                           clean_image_col_name_constants.ACCOUNTED_INK_CYAN,
                           clean_image_col_name_constants.ACCOUNTED_INK_MAGENTA]

        # Convert the columns to their appropriate unit type
        df = PreprocessTasks._converting_units_to_default_values(df, columns_to_drop)

        # Converts dates to utc
        df = PreprocessTasks._convert_date_to_utc(df)

        # Rename the columns to mach the database names
        df = df.rename(columns={clean_image_col_name_constants.MACHINEID: preprocess_col_name_constants.MACHINEID})
        df = df.rename(columns={clean_image_col_name_constants.DATE: preprocess_col_name_constants.DATE})
        df = df.rename(columns={clean_image_col_name_constants.MEDIA_TYPE: preprocess_col_name_constants.MEDIA_TYPE})
        # Save dataframe into database
        PreprocessTasks._insert_into_db(df, preprocess_table_name_config.PREPROCESS_MEDIA_CATEGORY_USAGE)

    @staticmethod
    def preprocess_sqm_per_print_mode():
        logging.info("Start preprocess for sqm per print mode.")
        # Take the dataframe from the previous step
        df = PreprocessTasks._read_from_db(clean_table_name_config.READ_PRINT_CYCLE)

        # Skip if no new data
        if df.empty:
            logging.info("No new data was found, skipping step.")
            return

        # Columns to be removed from the table
        columns_to_drop = [clean_print_cycle_col_name_constants.ENGINE_CYCLE_ID,
                           clean_image_col_name_constants.LOCAL_TIME]

        # Convert the columns to their appropriate unit type
        df = PreprocessTasks._converting_units_to_default_values(df, columns_to_drop)

        # Converts dates to utc
        df = PreprocessTasks._convert_date_to_utc(df)

        # Rename the print modes
        df = PreprocessTasks._rename_print_modes(df)

        # Rename the columns to mach the database names
        df = df.rename(columns={clean_image_col_name_constants.MACHINEID: preprocess_col_name_constants.MACHINEID})
        df = df.rename(columns={clean_image_col_name_constants.DATE: preprocess_col_name_constants.DATE})
        df = df.rename(columns={clean_print_cycle_col_name_constants.PRINT_MODE: preprocess_col_name_constants.PRINT_MODE})
        df = df.rename(columns={clean_print_cycle_col_name_constants.SQUARE_DECIMETER: preprocess_col_name_constants.SQUARE_DECIMETER})

        # Save dataframe into database
        PreprocessTasks._insert_into_db(df, preprocess_table_name_config.PREPROCESS_SQM_PER_PRINT_MODE)

    @staticmethod
    def preprocess_ink_usage():
        logging.info("Start preprocess for ink usage.")
        # Take the dataframe from the previous step
        df = PreprocessTasks._read_from_db(clean_table_name_config.READ_IMAGE)

        # Skip if no new data
        if df.empty:
            logging.info("No new data was found, skipping step.")
            return

        # Columns to be removed from the table
        columns_to_drop = [clean_image_col_name_constants.IMAGE_LENGTH, clean_image_col_name_constants.IMAGE_WIDTH,
                           clean_image_col_name_constants.MEDIA_TYPE, clean_image_col_name_constants.LOCAL_TIME]

        # Convert the columns to their appropriate unit type
        df = PreprocessTasks._converting_units_to_default_values(df, columns_to_drop)

        # Converts dates to utc
        df = PreprocessTasks._convert_date_to_utc(df)

        # Rename the columns to mach the database names
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

        # Skip if no new data
        if df1.empty and df2.empty:
            logging.info("No new data was found, skipping step.")
            return

        # Merge the dataframes
        df = PreprocessTasks._merge_two_dataframes(df1, df2, clean_print_cycle_col_name_constants.ENGINE_CYCLE_ID)

        # Make sure to not have unnecessary data
        df = df[[clean_print_cycle_col_name_constants.DATE + "_x",
                 clean_media_prepare_col_name_constants.MEDIA_TYPE_DISPLAY_NAME,
                 clean_print_cycle_col_name_constants.SQUARE_DECIMETER,
                 clean_print_cycle_col_name_constants.SQUARE_DECIMETER_UNIT,
                 clean_print_cycle_col_name_constants.MACHINEID + "_x"]]

        # Fix date column from date_x to date
        df = df.rename(
            columns={clean_print_cycle_col_name_constants.DATE + "_x": clean_print_cycle_col_name_constants.DATE})
        df = df.rename(
            columns={
                clean_print_cycle_col_name_constants.MACHINEID + "_x": clean_print_cycle_col_name_constants.MACHINEID})

        # Columns to be removed from the table
        columns_to_drop = []

        # Convert the columns to their appropriate unit type
        df = PreprocessTasks._converting_units_to_default_values(df, columns_to_drop)

        # Converts dates to utc
        df = PreprocessTasks._convert_date_to_utc(df)

        # Rename the columns to mach the database names
        df = df.rename(
            columns={clean_print_cycle_col_name_constants.MACHINEID: preprocess_col_name_constants.MACHINEID})
        df = df.rename(columns={clean_print_cycle_col_name_constants.DATE: preprocess_col_name_constants.DATE})
        df = df.rename(columns={
            clean_media_prepare_col_name_constants.MEDIA_TYPE_DISPLAY_NAME: preprocess_col_name_constants.MEDIA_TYPE_DISPLAY_NAME})
        df = df.rename(columns={
            clean_print_cycle_col_name_constants.SQUARE_DECIMETER: preprocess_col_name_constants.SQUARE_DECIMETER})

        # Save dataframe into database
        PreprocessTasks._insert_into_db(df, preprocess_table_name_config.PREPROCESS_MEDIA_TYPES_PER_MACHINE)

    @staticmethod
    def preprocess_top_ten_print_volume():
        logging.info("Start preprocess for media category usage.")
        # Take the dataframe from the previous step
        df = PreprocessTasks._read_from_db(clean_table_name_config.READ_PRINT_CYCLE)

        # Skip if no new data
        if df.empty:
            logging.info("No new data was found, skipping step.")
            return

        # Columns to be removed from the table
        columns_to_drop = [clean_print_cycle_col_name_constants.LOCAL_TIME,
                           clean_print_cycle_col_name_constants.ENGINE_CYCLE_ID,
                           clean_print_cycle_col_name_constants.PRINT_MODE]

        # Convert the columns to their appropriate unit type
        df = PreprocessTasks._converting_units_to_default_values(df, columns_to_drop)

        # Converts dates to utc
        df = PreprocessTasks._convert_date_to_utc(df)

        # Rename the columns to mach the database names
        df = df.rename(
            columns={clean_print_cycle_col_name_constants.MACHINEID: preprocess_col_name_constants.MACHINEID})
        df = df.rename(columns={clean_print_cycle_col_name_constants.DATE: preprocess_col_name_constants.DATE})
        df = df.rename(columns={
            clean_print_cycle_col_name_constants.SQUARE_DECIMETER: preprocess_col_name_constants.SQUARE_DECIMETER})

        # Save dataframe into database
        PreprocessTasks._insert_into_db(df, preprocess_table_name_config.PREPROCESS_TOP_TEN_PRINT_VOLUME)

    @staticmethod
    def _merge_two_dataframes(df1, df2, column_for_merging):
        logging.info("Preprocess - merge two dataframes.")
        df = pd.merge(left=df1, right=df2, left_on=column_for_merging, right_on=column_for_merging)
        return df

    @staticmethod
    def _rename_print_modes(df):
        logging.info("Preprocess - renaming print modes.")
        print_mode = clean_print_cycle_col_name_constants.PRINT_MODE
        if print_mode in df.columns:
            names = "1_pass" + " 1_pass_highDensity" + " 2_pass" + " 4_pass" + " 8_pass" + " 8_pass_highDensity" + " 16_pass"
            for index, row in df.iterrows():
                if str(row[print_mode]) not in names:
                    df.at[index, print_mode] = "Other"

            df.loc[df[print_mode] == "1_pass", print_mode] = "Max speed"
            df.loc[df[print_mode] == "1_pass_highDensity", print_mode] = "High speed"
            df.loc[df[print_mode] == "2_pass", print_mode] = "Production"
            df.loc[df[print_mode] == "4_pass", print_mode] = "High Quality"
            df.loc[df[print_mode] == "8_pass", print_mode] = "Specialty"
            df.loc[df[print_mode] == "8_pass_highDensity", print_mode] = "Backlit"
            df.loc[df[print_mode] == "16_pass", print_mode] = "Reliance"
        return df

    @staticmethod
    def _converting_units_to_default_values(df, columns_to_drop):
        logging.info("Starting process of unit conversion and needless column removal")
        unit_columns = []
        # Gets all column names with units
        for col_name in df.columns:
            if "|unit|" in col_name:
                # Saves each one to a list
                unit_columns.append(col_name)
            elif "machineid" in col_name:
                df[col_name] = df[col_name].astype(str)

        # Iterates through all rows and updates them
        for index, row in df.iterrows():
            for unit_col_name in unit_columns:

                # Gets the name of the column for the unit
                data_col_name = re.search("(.+)\|.+\|(.+)?", unit_col_name).group(1)

                if data_col_name != clean_image_col_name_constants.LOCAL_TIME:
                    # Set the type of value columns to float
                    df[data_col_name] = df[data_col_name].astype(float)

                # Gets the values of the columns
                row_data_value = row[data_col_name]
                row_unit_value = row[unit_col_name]
                if row_unit_value == "ml":  # Converts to from one of the units below to milliliters
                    df.at[index, data_col_name] = row_data_value / 1000
                elif row_unit_value == "cl":
                    df.at[index, data_col_name] = row_data_value / 100
                elif row_unit_value == "dl":
                    df.at[index, data_col_name] = row_data_value / 10
                elif row_unit_value == "dal":
                    df.at[index, data_col_name] = row_data_value * 10
                elif row_unit_value == "hl":
                    df.at[index, data_col_name] = row_data_value * 100
                elif row_unit_value == "kl":
                    df.at[index, data_col_name] = row_data_value * 1000
                elif row_unit_value == "mm":  # Converts to from one of the units below to meters
                    df.at[index, data_col_name] = row_data_value / 1000
                elif row_unit_value == "cm":
                    df.at[index, data_col_name] = row_data_value / 100
                elif row_unit_value == "dm":
                    df.at[index, data_col_name] = row_data_value / 10
                elif row_unit_value == "dam":
                    df.at[index, data_col_name] = row_data_value * 10
                elif row_unit_value == "hm":
                    df.at[index, data_col_name] = row_data_value * 100
                elif row_unit_value == "km":
                    df.at[index, data_col_name] = row_data_value * 1000
                elif row_unit_value == "mm2":  # Converts to from one of the units below to square meters
                    df.at[index, data_col_name] = row_data_value / 1000000
                elif row_unit_value == "cm2":
                    df.at[index, data_col_name] = row_data_value / 10000
                elif row_unit_value == "dm2":
                    df.at[index, data_col_name] = row_data_value / 100
                elif row_unit_value == "dam2":
                    df.at[index, data_col_name] = row_data_value * 100
                elif row_unit_value == "hm2":
                    df.at[index, data_col_name] = row_data_value * 10000
                elif row_unit_value == "km2":
                    df.at[index, data_col_name] = row_data_value * 1000000
                elif row_unit_value == "mm3":  # Converts to from one of the units below to cubic meters
                    df.at[index, data_col_name] = row_data_value / 1000000000
                elif row_unit_value == "cm3":
                    df.at[index, data_col_name] = row_data_value / 1000000
                elif row_unit_value == "dm3":
                    df.at[index, data_col_name] = row_data_value / 1000
                elif row_unit_value == "dam3":
                    df.at[index, data_col_name] = row_data_value * 1000
                elif row_unit_value == "hm3":
                    df.at[index, data_col_name] = row_data_value * 1000000
                elif row_unit_value == "km3":
                    df.at[index, data_col_name] = row_data_value * 1000000000

        # Combines the unit columns with the unnecessary ones
        unnecessary_columns = np.concatenate((unit_columns, columns_to_drop), axis=0)

        # Drop all unit and needless columns after the conversion
        df = df.drop(columns=unnecessary_columns)

        logging.info("Successful process of unit conversion and needless column removal")
        return df

    @staticmethod
    def _convert_date_to_utc(df):
        logging.info("Start of date generalization to UTC")
        date_format = "%Y-%m-%d"
        for index, row in df.iterrows():
            date_col = clean_image_col_name_constants.DATE
            date_value = row[clean_image_col_name_constants.DATE]
            # Create an object in local timezone
            local_dt = datetime.strptime(date_value, date_format)

            # Convert local datetime to UTC time-zone datetime
            dt_utc = local_dt.astimezone(pytz.UTC)

            # Converts the UTC date to a string
            dt_utc_str = dt_utc.strftime(date_format)

            # Inserts the utc date back in the dataframe
            df.at[index, date_col] = dt_utc_str
        logging.info("End of date generalization to UTC")

        return df

    @staticmethod
    def _read_from_db(table_name) -> pd.DataFrame:
        # read from db
        logging.info(f"Preprocess - reading table {table_name} from database.")
        pdm = PostgresDatabaseManager()
        df = pdm.read_table(table_name)
        return df

    @staticmethod
    def _insert_into_db(df, table_name):
        # put in db
        logging.info("Preprocess - inserting preprocessed data to database.")
        pdm = PostgresDatabaseManager()
        pdm.insert_into_table(df, table_name)
        logging.info("Preprocess finished!")
