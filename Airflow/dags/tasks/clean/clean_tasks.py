import logging

from DAL.postgres_database_manager import PostgresDatabaseManager
from config import read_table_name_config, cleaning_column_name_config, clean_table_name_config
import pandas as pd
from tabulate import tabulate

class CleanTasks():

    @staticmethod
    def clean_image():
        pdm = PostgresDatabaseManager()

        # Read Image table from Db
        df = pdm.read_table(read_table_name_config.READ_IMAGE)
        if (df.empty):
            logging.info("No new data was found, skipping step.")
            return

        logging.info(tabulate(df, headers='keys', tablefmt='psql'))

        # Make dataframe using pandas
        df = CleanTasks.make_data_frame_image(df)
        logging.info(tabulate(df, headers='keys', tablefmt='psql'))

        # check if ullid is same then drop
        df = CleanTasks.remove_duplicates(df)
        logging.info(tabulate(df, headers='keys', tablefmt='psql'))

        # check integer or string.
        df = CleanTasks.check_type_image(df)
        logging.info(tabulate(df, headers='keys', tablefmt='psql'))

        # check if some row values are empty
        df = CleanTasks.remove_row_null(df)
        logging.info(tabulate(df, headers='keys', tablefmt='psql'))

        # Check absurd value?

        # Check negative value.
        df = CleanTasks.check_negative_image(df)
        logging.info(tabulate(df, headers='keys', tablefmt='psql'))

        # Check if mediatype is valid
        #df = self.RemoveInvalid_media_type(df)

        # Create table and store
        CleanTasks._insert_into_db(df, clean_table_name_config
.READ_IMAGE)

    @staticmethod
    def _read_from_db(tableName):
        # put in db
        logging.info("Reading data from the database.")
        pdm = PostgresDatabaseManager()
        pdm.read_table(tableName)

    @staticmethod
    def _insert_into_db(data, tableName):
        # put in db
        logging.info("Inserting read data to database.")
        pdm = PostgresDatabaseManager()
        pdm.insert_into_table(data, tableName)

    @staticmethod
    def make_data_frame_image(df):
        logging.info("Making the dataframe with the right columns.")
        df = df[[cleaning_column_name_config.ULLID,
                 cleaning_column_name_config.ACCOUNTED_INK_BLACK,
                 cleaning_column_name_config.ACCOUNTED_INK_CYAN,
                 cleaning_column_name_config.ACCOUNTED_INK_MAGENTA,
                 cleaning_column_name_config.ACCOUNTED_INK_YELLOW,
                 cleaning_column_name_config.DATE,
                 cleaning_column_name_config.IMAGE_LENGTH,
                 cleaning_column_name_config.IMAGE_WIDTH,
                 cleaning_column_name_config.MEDIA_TYPE]]
        return df

    @staticmethod
    def remove_duplicates(df):
        logging.info("Removing all the rows with duplicate ullids.")
        df = df.drop_duplicates(subset=[cleaning_column_name_config.ULLID])
        return df

    @staticmethod
    def check_type_image(df):
        logging.info("Making value NaN for all the columns with invalid datatype.")
        df[cleaning_column_name_config.ULLID] = pd.to_numeric(df[cleaning_column_name_config.ULLID], errors='coerce')
        df[cleaning_column_name_config.ACCOUNTED_INK_BLACK] = pd.to_numeric(df[cleaning_column_name_config.ACCOUNTED_INK_BLACK], errors='coerce')
        df[cleaning_column_name_config.ACCOUNTED_INK_CYAN] = pd.to_numeric(df[cleaning_column_name_config.ACCOUNTED_INK_CYAN], errors='coerce')
        df[cleaning_column_name_config.ACCOUNTED_INK_YELLOW] = pd.to_numeric(df[cleaning_column_name_config.ACCOUNTED_INK_YELLOW], errors='coerce')
        df[cleaning_column_name_config.ACCOUNTED_INK_MAGENTA] = pd.to_numeric(df[cleaning_column_name_config.ACCOUNTED_INK_MAGENTA], errors='coerce')
        df[cleaning_column_name_config.IMAGE_LENGTH] = pd.to_numeric(df[cleaning_column_name_config.IMAGE_LENGTH], errors='coerce')
        df[cleaning_column_name_config.IMAGE_WIDTH] = pd.to_numeric(df[cleaning_column_name_config.IMAGE_WIDTH], errors='coerce')
        df[cleaning_column_name_config.DATE] = pd.to_datetime(df[cleaning_column_name_config.DATE], errors='coerce').dt.strftime('%Y-%m-%d')
        df[cleaning_column_name_config.MEDIA_TYPE] = df[cleaning_column_name_config.MEDIA_TYPE].mask(pd.to_numeric(df[cleaning_column_name_config.MEDIA_TYPE], errors='coerce').notna())
        return df

    @staticmethod
    def remove_row_null(df):
        logging.info("Removing all rows with empty or NaN value.")
        nan_value = float("NaN")
        df.replace('', nan_value, inplace=True)
        df.dropna(inplace=True)
        return df

    @staticmethod
    def check_negative_image(df):
        logging.info("Removing all rows with negative values.")
        df = df[(df[cleaning_column_name_config.ULLID] > 0)]
        df = df[(df[cleaning_column_name_config.ACCOUNTED_INK_BLACK] > 0)]
        df = df[(df[cleaning_column_name_config.ACCOUNTED_INK_CYAN] > 0)]
        df = df[(df[cleaning_column_name_config.ACCOUNTED_INK_MAGENTA] > 0)]
        df = df[(df[cleaning_column_name_config.ACCOUNTED_INK_YELLOW] > 0)]
        df = df[(df[cleaning_column_name_config.IMAGE_WIDTH] > 0)]
        df = df[(df[cleaning_column_name_config.IMAGE_LENGTH] > 0)]
        return df

    @staticmethod
    def RemoveInvalid_media_type(df):
        logging.info("Removing all rows with invalid mediatype.")
        array = ['Canvas', 'Film', 'Monomeric vinyl',
                 'Textile', 'Unknown papertype', 'Polymeric & cast vinyl',
                 'Light paper < 120gsm', 'Heavy paper > 200gsm',
                 'Heavy banner > 400gsm', 'Thick film > 200 um']
        df = df.loc[df[cleaning_column_name_config.MEDIA_TYPE].isin(array)]
        return df
    @staticmethod
    def clean_media_prepare():
        pass

    @staticmethod
    def clean_print_cycle():
        pass