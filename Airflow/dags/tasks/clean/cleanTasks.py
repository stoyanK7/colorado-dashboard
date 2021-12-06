import logging

from DAL.PostgresDatabaseManager import PostgresDatabaseManager
from config import ReadTableNameConfig, CleaningColumnNameConfig, CleanTableNameConfig
import pandas as pd
from tabulate import tabulate

class CleanTasks():

    @staticmethod
    def clean_image():
        pdm = PostgresDatabaseManager()

        # Read Image table from Db
        df = pdm.read_table(ReadTableNameConfig.READ_IMAGE)
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
        CleanTasks._insert_into_db(df, CleanTableNameConfig.READ_IMAGE)

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
        df = df[[CleaningColumnNameConfig.ULLID,
                 CleaningColumnNameConfig.ACCOUNTED_INK_BLACK,
                 CleaningColumnNameConfig.ACCOUNTED_INK_CYAN,
                 CleaningColumnNameConfig.ACCOUNTED_INK_MAGENTA,
                 CleaningColumnNameConfig.ACCOUNTED_INK_YELLOW,
                 CleaningColumnNameConfig.DATE,
                 CleaningColumnNameConfig.IMAGE_LENGTH,
                 CleaningColumnNameConfig.IMAGE_WIDTH,
                 CleaningColumnNameConfig.MEDIA_TYPE]]
        return df

    @staticmethod
    def remove_duplicates(df):
        logging.info("Removing all the rows with duplicate ullids.")
        df = df.drop_duplicates(subset=[CleaningColumnNameConfig.ULLID])
        return df

    @staticmethod
    def check_type_image(df):
        logging.info("Making value NaN for all the columns with invalid datatype.")
        df[CleaningColumnNameConfig.ULLID] = pd.to_numeric(df[CleaningColumnNameConfig.ULLID], errors='coerce')
        df[CleaningColumnNameConfig.ACCOUNTED_INK_BLACK] = pd.to_numeric(df[CleaningColumnNameConfig.ACCOUNTED_INK_BLACK], errors='coerce')
        df[CleaningColumnNameConfig.ACCOUNTED_INK_CYAN] = pd.to_numeric(df[CleaningColumnNameConfig.ACCOUNTED_INK_CYAN], errors='coerce')
        df[CleaningColumnNameConfig.ACCOUNTED_INK_YELLOW] = pd.to_numeric(df[CleaningColumnNameConfig.ACCOUNTED_INK_YELLOW], errors='coerce')
        df[CleaningColumnNameConfig.ACCOUNTED_INK_MAGENTA] = pd.to_numeric(df[CleaningColumnNameConfig.ACCOUNTED_INK_MAGENTA], errors='coerce')
        df[CleaningColumnNameConfig.IMAGE_LENGTH] = pd.to_numeric(df[CleaningColumnNameConfig.IMAGE_LENGTH], errors='coerce')
        df[CleaningColumnNameConfig.IMAGE_WIDTH] = pd.to_numeric(df[CleaningColumnNameConfig.IMAGE_WIDTH], errors='coerce')
        df[CleaningColumnNameConfig.DATE] = pd.to_datetime(df[CleaningColumnNameConfig.DATE], errors='coerce').dt.strftime('%Y-%m-%d')
        df[CleaningColumnNameConfig.MEDIA_TYPE] = df[CleaningColumnNameConfig.MEDIA_TYPE].mask(pd.to_numeric(df[CleaningColumnNameConfig.MEDIA_TYPE], errors='coerce').notna())
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
        df = df[(df[CleaningColumnNameConfig.ULLID] > 0)]
        df = df[(df[CleaningColumnNameConfig.ACCOUNTED_INK_BLACK] > 0)]
        df = df[(df[CleaningColumnNameConfig.ACCOUNTED_INK_CYAN] > 0)]
        df = df[(df[CleaningColumnNameConfig.ACCOUNTED_INK_MAGENTA] > 0)]
        df = df[(df[CleaningColumnNameConfig.ACCOUNTED_INK_YELLOW] > 0)]
        df = df[(df[CleaningColumnNameConfig.IMAGE_WIDTH] > 0)]
        df = df[(df[CleaningColumnNameConfig.IMAGE_LENGTH] > 0)]
        return df

    @staticmethod
    def RemoveInvalid_media_type(df):
        logging.info("Removing all rows with invalid mediatype.")
        array = ['Canvas', 'Film', 'Monomeric vinyl',
                 'Textile', 'Unknown papertype', 'Polymeric & cast vinyl',
                 'Light paper < 120gsm', 'Heavy paper > 200gsm',
                 'Heavy banner > 400gsm', 'Thick film > 200 um']
        df = df.loc[df[CleaningColumnNameConfig.MEDIA_TYPE].isin(array)]
        return df
    @staticmethod
    def clean_media_prepare():
        pass

    @staticmethod
    def clean_print_cycle():
        pass