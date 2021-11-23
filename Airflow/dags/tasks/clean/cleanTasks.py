import logging

from DAL import PostgresDatabaseManager
from config import ReadTableNameConfig, CleaningColumnNameConfig, CleanTableNameConfig
import pandas as pd

class CleanTasks():

    def CleanImage(self):
        obj = PostgresDatabaseManager()

        # Read Image table from Db
        df = obj.readTable(ReadTableNameConfig.READIMAGE)

        # Make dataframe using pandas
        df = self.MakeDataFrameImage(df)

        # check if ullid is same then drop
        df = self.RemoveDuplicatesImage(df)

        # check integer or string.
        df = self.CheckTypeImage(df)

        # check if some row values are empty
        df = self.RemoveRowNull(df)

        # Check absurd value?

        # Check negative value.
        df = self.CheckNegativeImage(df)

        # Check if mediatype is valid
        #df = self.RemoveInvalidMediaType(df)

        # Create table and store
        obj.insertIntoTable(df, CleanTableNameConfig.READIMAGE)

    def MakeDataFrameImage(self, df):
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

    def RemoveDuplicates(self, df):
        logging.info("Removing all the rows with duplicate ullids.")
        df = df.drop_duplicates(subset=[CleaningColumnNameConfig.ULLID])
        return df

    def CheckTypeImage(self, df):
        logging.info("Making value NaN for all the columns with invalid datatype.")
        df[CleaningColumnNameConfig.ULLID] = df[CleaningColumnNameConfig.ULLID].apply(pd.to_numeric)
        df[CleaningColumnNameConfig.ACCOUNTEDINKBLACK] = df[CleaningColumnNameConfig.ACCOUNTEDINKBLACK].apply(pd.to_numeric)
        df[CleaningColumnNameConfig.ACCOUNTEDINKCYAN] = df[CleaningColumnNameConfig.ACCOUNTEDINKCYAN].apply(pd.to_numeric)
        df[CleaningColumnNameConfig.ACCOUNTEDINKYELLOW] = df[CleaningColumnNameConfig.ACCOUNTEDINKYELLOW].apply(pd.to_numeric)
        df[CleaningColumnNameConfig.ACCOUNTEDINKMAGENTA] = df[CleaningColumnNameConfig.ACCOUNTEDINKMAGENTA].apply(pd.to_numeric)
        df[CleaningColumnNameConfig.IMAGELENGTH] = df[CleaningColumnNameConfig.IMAGELENGTH].apply(pd.to_numeric)
        df[CleaningColumnNameConfig.IMAGEWIDTH] = df[CleaningColumnNameConfig.IMAGEWIDTH].apply(pd.to_numeric)
        df[CleaningColumnNameConfig.DATE] = pd.to_datetime(df[CleaningColumnNameConfig.DATE], errors='coerce').dt.strftime('%d/%m/%Y')
        df[CleaningColumnNameConfig.MEDIATYPE] = df[CleaningColumnNameConfig.MEDIATYPE].mask(pd.to_numeric(df[CleaningColumnNameConfig.MEDIATYPE], errors='coerce').notna())
        return df

    def RemoveRowNull(self, df):
        logging.info("Removing all rows with empty or NaN value.")
        nan_value = float("NaN")
        df.replace('', nan_value, inplace=True)
        df.dropna(inplace=True)
        return df

    def CheckNegativeImage(self, df):
        logging.info("Removing all rows with negative values.")
        df = df[(df[CleaningColumnNameConfig.ULLID] > 0)]
        df = df[(df[CleaningColumnNameConfig.ACCOUNTEDINKBLACK] > 0)]
        df = df[(df[CleaningColumnNameConfig.ACCOUNTEDINKCYAN] > 0)]
        df = df[(df[CleaningColumnNameConfig.ACCOUNTEDINKMAGENTA] > 0)]
        df = df[(df[CleaningColumnNameConfig.ACCOUNTEDINKYELLOW] > 0)]
        df = df[(df[CleaningColumnNameConfig.IMAGEWIDTH] > 0)]
        df = df[(df[CleaningColumnNameConfig.IMAGELENGTH] > 0)]
        return df


    def RemoveInvalidMediaType(self,df):
        logging.info("Removing all rows with invalid mediatype.")
        array = ['Canvas', 'Film', 'Monomeric vinyl',
                 'Textile', 'Unknown papertype', 'Polymeric & cast vinyl',
                 'Light paper < 120gsm', 'Heavy paper > 200gsm',
                 'Heavy banner > 400gsm', 'Thick film > 200 um']
        df = df.loc[df[CleaningColumnNameConfig.MEDIATYPE].isin(array)]
        return df


    def CleanMediaPrepare(self):
        obj = PostgresDatabaseManager()

        # Read Image table from Db
        df = obj.readTable(ReadTableNameConfig.READMEDIAPREPARE)

        # Make dataframe using pandas
        df = self.MakeDataFrameMediaPrepare(df)

        # check if ullid is same then drop
        df = self.RemoveDuplicatesMediaPrepare(df)

        # check integer or string.
        df = self.CheckTypeMediaPrepare(df)

        # Check absurd value?

        # Check negative value.
        df = self.CheckNegativeMediaPrepare(df)

        # Create table and store
        obj.insertIntoTable(df, CleanTableNameConfig.READMEDIAPREPARE)

    def CleanPrintCycle(self):
        return PostgresDatabaseManager.readTable(ReadTableNameConfig.READPRINTCYCLE)