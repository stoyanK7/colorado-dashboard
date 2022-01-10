import logging

import pandas

from DAL.postgres_database_manager import PostgresDatabaseManager
from config import read_table_name_config, clean_table_name_config, \
    clean_image_col_name_constants, clean_media_prepare_col_name_constants, clean_print_cycle_col_name_constants, \
    clean_image_data_types, clean_media_prepare_data_types, clean_print_cycle_data_types
import pandas as pd

class CleanTasks:
    @staticmethod
    def clean_image():
        # Read Image table from Db
        df = CleanTasks._read_from_db(read_table_name_config.READ_PRINT_CYCLE)
        if df.empty:
            logging.info("No new data was found, skipping step.")
            return

        # Make dataframe using pandas
        cols = [getattr(clean_image_col_name_constants, name)
                for name in dir(clean_image_col_name_constants) if not name.startswith('_')]
        cols = cols[-1:] + cols[:-1]
        df = CleanTasks.make_data_frame(df, cols)

        # check if ullid is same then drop
        df = CleanTasks.remove_duplicates(df)

        # check data type.
        df = CleanTasks.check_data_type(df, clean_image_data_types.data_types)

        # check if some row values are empty
        df = CleanTasks.remove_row_null(df)

        # Check negative value.
        df = CleanTasks.check_negative_values(df, clean_image_data_types.data_types)

        # Check if mediaType is valid
        #df = self.remove_invalid_media_type(df)


        # Create table and store
        CleanTasks._insert_into_db(df, clean_table_name_config.READ_IMAGE)

    @staticmethod
    def clean_media_prepare():
        # Read media prepare from Db
        df = CleanTasks._read_from_db(read_table_name_config.READ_PRINT_CYCLE)
        if df.empty:
            logging.info("No new data was found, skipping step.")
            return

        # Make dataframe using pandas
        cols = [getattr(clean_media_prepare_col_name_constants, name)
                for name in dir(clean_media_prepare_col_name_constants) if not name.startswith('_')]
        cols = cols[-1:] + cols[:-1]
        df = CleanTasks.make_data_frame(df, cols)

        # check if ullid is same then drop
        df = CleanTasks.remove_duplicates(df)

        # check data type.
        df = CleanTasks.check_data_type(df, clean_media_prepare_data_types.data_types)

        # check if some row values are empty
        df = CleanTasks.remove_row_null(df)

        # Check negative value.
        df = CleanTasks.check_negative_values(df, clean_media_prepare_data_types.data_types)


        # Create table and store
        CleanTasks._insert_into_db(df, clean_table_name_config.READ_MEDIA_PREPARE)

    @staticmethod
    def clean_print_cycle():
        # read print cycle from Db
        df = CleanTasks._read_from_db(read_table_name_config.READ_PRINT_CYCLE)
        if df.empty:
            logging.info("No new data was found, skipping step.")
            return

        # Make dataframe using pandas
        cols = [getattr(clean_print_cycle_col_name_constants, name)
                for name in dir(clean_print_cycle_col_name_constants) if not name.startswith('_')]
        cols = cols[-1:] + cols[:-1]
        df = CleanTasks.make_data_frame(df, cols)

        # check if ullid is same then drop
        df = CleanTasks.remove_duplicates(df)

        # check data type.
        df = CleanTasks.check_data_type(df, clean_print_cycle_data_types.data_types)

        # check if some row values are empty
        df = CleanTasks.remove_row_null(df)

        # Check negative value.
        df = CleanTasks.check_negative_values(df, clean_print_cycle_data_types.data_types)

        # Create table and store
        CleanTasks._insert_into_db(df, clean_table_name_config.READ_PRINT_CYCLE)

    @staticmethod
    def _read_from_db(table_name):
        # put in db
        logging.info("Reading data from the database.")
        pdm = PostgresDatabaseManager()
        df = pdm.read_table(table_name)
        return df

    @staticmethod
    def make_data_frame(df, cols):
        logging.info("Making the dataframe with the right columns.")
        df = df[df.columns.intersection(cols)]
        return df

    @staticmethod
    def remove_duplicates(df):
        logging.info("Removing all the rows with duplicate ullids.")
        df = df.drop_duplicates(subset=["ullid"])
        return df

    @staticmethod
    def _insert_into_db(df, table_name):
        # put in db
        logging.info("Inserting read data to database.")
        pdm = PostgresDatabaseManager()
        pdm.insert_into_table(df, table_name)

    @staticmethod
    def remove_row_null(df):
        logging.info("Removing all rows with empty or NaN value.")
        nan_value = float("NaN")
        df.replace('', nan_value, inplace=True)
        df.dropna(inplace=True)
        return df

    @staticmethod
    def check_data_type(df, data_types):
        logging.info("Making value NaN for all the columns with invalid datatype.")
        for column in df:
            data_type = data_types.get(column)
            if data_type == "integer":
                df[column] = pd.to_numeric(
                    df[column], errors='coerce')
            elif data_type == "datetime":
                df[column] = pd.to_datetime(
                    df[column], errors='coerce').dt.strftime('%Y-%m-%d')
            elif data_type == "string":
                df[column] = \
                    df[column].mask(pd.to_numeric(
                        df[column], errors='coerce').notna())
        return df

    @staticmethod
    def check_negative_values(df, data_types):
        logging.info("Removing all rows with negative values.")
        for column in df:
            data_type = data_types.get(column)
            if data_type == "integer":
                df = df[(df[column] > 0)]
        return df

    @staticmethod
    def remove_invalid_media_type(df):
        logging.info("Removing all rows with invalid mediatype.")
        array = ['Canvas', 'Film', 'Monomeric vinyl',
                 'Textile', 'Unknown papertype', 'Polymeric & cast vinyl',
                 'Light paper < 120gsm', 'Heavy paper > 200gsm',
                 'Heavy banner > 400gsm', 'Thick film > 200 um']
        df = df.loc[df[clean_image_col_name_constants.MEDIA_TYPE].isin(array)]
        return df


pdm = PostgresDatabaseManager