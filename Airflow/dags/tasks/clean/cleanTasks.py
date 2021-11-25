import logging

from DAL.PostgresDatabaseManager import PostgresDatabaseManager
from config import ReadTableNameConfig, CleaningColumnNameConfig, CleanTableNameConfig
import pandas as pd
from tabulate import tabulate

class CleanTasks():

    @staticmethod
    def CleanImage():
        pdm = PostgresDatabaseManager()

        # Read Image table from Db
        df = pdm.readTable(ReadTableNameConfig.READIMAGE)
        if (df.empty):
            logging.info("No new data was found, skipping step.")
            return

        logging.info(tabulate(df, headers='keys', tablefmt='psql'))

        # Make dataframe using pandas
        df = CleanTasks.MakeDataFrameImage(df)
        logging.info(tabulate(df, headers='keys', tablefmt='psql'))

        # check if ullid is same then drop
        df = CleanTasks.RemoveDuplicates(df)
        logging.info(tabulate(df, headers='keys', tablefmt='psql'))

        # check integer or string.
        df = CleanTasks.CheckTypeImage(df)
        logging.info(tabulate(df, headers='keys', tablefmt='psql'))

        # check if some row values are empty
        df = CleanTasks.RemoveRowNull(df)
        logging.info(tabulate(df, headers='keys', tablefmt='psql'))

        # Check absurd value?

        # Check negative value.
        df = CleanTasks.CheckNegativeImage(df)
        logging.info(tabulate(df, headers='keys', tablefmt='psql'))

        # Check if mediatype is valid
        #df = self.RemoveInvalidMediaType(df)

        # Create table and store
        CleanTasks._insertIntoDb(df, CleanTableNameConfig.READIMAGE)

    @staticmethod
    def _readFromDb(tableName):
        # put in db
        logging.info("Reading data from the database.")
        pdm = PostgresDatabaseManager()
        pdm.readTable(tableName)

    @staticmethod
    def _insertIntoDb(data, tableName):
        # put in db
        logging.info("Inserting read data to database.")
        pdm = PostgresDatabaseManager()
        pdm.insertIntoTable(data, tableName)

    @staticmethod
    def MakeDataFrameImage(df):
        logging.info("Making the dataframe with the right columns.")
        df = df[[CleaningColumnNameConfig.ULLID,
                 CleaningColumnNameConfig.ACCOUNTEDINKBLACK,
                 CleaningColumnNameConfig.ACCOUNTEDINKCYAN,
                 CleaningColumnNameConfig.ACCOUNTEDINKMAGENTA,
                 CleaningColumnNameConfig.ACCOUNTEDINKYELLOW,
                 CleaningColumnNameConfig.DATE,
                 CleaningColumnNameConfig.IMAGELENGTH,
                 CleaningColumnNameConfig.IMAGEWIDTH,
                 CleaningColumnNameConfig.MEDIATYPE]]
        return df

    @staticmethod
    def RemoveDuplicates(df):
        logging.info("Removing all the rows with duplicate ullids.")
        df = df.drop_duplicates(subset=[CleaningColumnNameConfig.ULLID])
        return df

    @staticmethod
    def CheckTypeImage(df):
        logging.info("Making value NaN for all the columns with invalid datatype.")
        df[CleaningColumnNameConfig.ULLID] = pd.to_numeric(df[CleaningColumnNameConfig.ULLID], errors='coerce')
        df[CleaningColumnNameConfig.ACCOUNTEDINKBLACK] = pd.to_numeric(df[CleaningColumnNameConfig.ACCOUNTEDINKBLACK], errors='coerce')
        df[CleaningColumnNameConfig.ACCOUNTEDINKCYAN] = pd.to_numeric(df[CleaningColumnNameConfig.ACCOUNTEDINKCYAN], errors='coerce')
        df[CleaningColumnNameConfig.ACCOUNTEDINKYELLOW] = pd.to_numeric(df[CleaningColumnNameConfig.ACCOUNTEDINKYELLOW], errors='coerce')
        df[CleaningColumnNameConfig.ACCOUNTEDINKMAGENTA] = pd.to_numeric(df[CleaningColumnNameConfig.ACCOUNTEDINKMAGENTA], errors='coerce')
        df[CleaningColumnNameConfig.IMAGELENGTH] = pd.to_numeric(df[CleaningColumnNameConfig.IMAGELENGTH], errors='coerce')
        df[CleaningColumnNameConfig.IMAGEWIDTH] = pd.to_numeric(df[CleaningColumnNameConfig.IMAGEWIDTH], errors='coerce')
        df[CleaningColumnNameConfig.DATE] = pd.to_datetime(df[CleaningColumnNameConfig.DATE], errors='coerce').dt.strftime('%Y-%m-%d')
        df[CleaningColumnNameConfig.MEDIATYPE] = df[CleaningColumnNameConfig.MEDIATYPE].mask(pd.to_numeric(df[CleaningColumnNameConfig.MEDIATYPE], errors='coerce').notna())
        return df

    @staticmethod
    def RemoveRowNull(df):
        logging.info("Removing all rows with empty or NaN value.")
        nan_value = float("NaN")
        df.replace('', nan_value, inplace=True)
        df.dropna(inplace=True)
        return df

    @staticmethod
    def CheckNegativeImage(df):
        logging.info("Removing all rows with negative values.")
        df = df[(df[CleaningColumnNameConfig.ULLID] > 0)]
        df = df[(df[CleaningColumnNameConfig.ACCOUNTEDINKBLACK] > 0)]
        df = df[(df[CleaningColumnNameConfig.ACCOUNTEDINKCYAN] > 0)]
        df = df[(df[CleaningColumnNameConfig.ACCOUNTEDINKMAGENTA] > 0)]
        df = df[(df[CleaningColumnNameConfig.ACCOUNTEDINKYELLOW] > 0)]
        df = df[(df[CleaningColumnNameConfig.IMAGEWIDTH] > 0)]
        df = df[(df[CleaningColumnNameConfig.IMAGELENGTH] > 0)]
        return df

    @staticmethod
    def RemoveInvalidMediaType(df):
        logging.info("Removing all rows with invalid mediatype.")
        array = ['Canvas', 'Film', 'Monomeric vinyl',
                 'Textile', 'Unknown papertype', 'Polymeric & cast vinyl',
                 'Light paper < 120gsm', 'Heavy paper > 200gsm',
                 'Heavy banner > 400gsm', 'Thick film > 200 um']
        df = df.loc[df[CleaningColumnNameConfig.MEDIATYPE].isin(array)]
        return df
    @staticmethod
    def CleanMediaPrepare():
        pass

    @staticmethod
    def CleanPrintCycle():
        pass