from ...DAL import PostgresDatabaseManager
from ...config import ReadTableNameConfig, CleaningColumnNameConfig, CleanTableNameConfig
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

        # Check absurd value?

        # Check negative value.
        df = self.CheckNegativeImage(df)

        # Create table and store
        obj.createTable(df, CleanTableNameConfig.READIMAGE)

    def MakeDataFrameImage(self, df):
        df = df[[CleaningColumnNameConfig.ULLID,
                 CleaningColumnNameConfig.ACCOUNTEDINKBLACK,
                 CleaningColumnNameConfig.ACCOUNTEDINKCYAN,
                 CleaningColumnNameConfig.ACCOUNTEDINKMAGENTA,
                 CleaningColumnNameConfig.ACCOUNTEDINKYELLOW,
                 CleaningColumnNameConfig.DATE,
                 CleaningColumnNameConfig.IMAGELENGTH,
                 CleaningColumnNameConfig.IMAGEWIDTH,
                 CleaningColumnNameConfig.MEDIATYPE]]
        df = df.set_index(CleaningColumnNameConfig.ULLID)
        return df

    def RemoveDuplicatesImage(self, df):
        df = df[~df.index.duplicated(keep='first')]
        return df


    def CheckNegativeImage(self, df):
        df = df[df.ullid > 0]
        df = df[CleaningColumnNameConfig.ACCOUNTEDINKBLACK > 0]
        df = df[CleaningColumnNameConfig.ACCOUNTEDINKCYAN > 0]
        df = df[CleaningColumnNameConfig.ACCOUNTEDINKMAGENTA > 0]
        df = df[CleaningColumnNameConfig.ACCOUNTEDINKYELLOW > 0]
        df = df[CleaningColumnNameConfig.IMAGEWIDTH > 0]
        df = df[CleaningColumnNameConfig.IMAGELENGTH > 0]
        return df

    def CheckTypeImage(self, df):

        # remove all rows with value null
        df = df.dropna()

        # remove row with invalid ullid type
        df = df[CleaningColumnNameConfig.ULLID].map(type) != str

        # remove row with invalid Accountedink*
        df = df[CleaningColumnNameConfig.ACCOUNTEDINKCYAN].map(type) != str
        df = df[CleaningColumnNameConfig.ACCOUNTEDINKBLACK].map(type) != str
        df = df[CleaningColumnNameConfig.ACCOUNTEDINKYELLOW].map(type) != str
        df = df[CleaningColumnNameConfig.ACCOUNTEDINKMAGENTA].map(type) != str

        # remove row with invalid date
        df = pd.to_datetime(df[CleaningColumnNameConfig.DATE], format='%d-%b-%Y', errors='coerce').notna()

        # remove row with invalid image length and width.
        df = df[CleaningColumnNameConfig.IMAGELENGTH].map(type) != str
        df = df[CleaningColumnNameConfig.IMAGEWIDTH].map(type) != str


        # remove row with invalid mediaType
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
        obj.createTable(df, CleanTableNameConfig.READMEDIAPREPARE)

    def CleanPrintCycle(self):
        return PostgresDatabaseManager.readTable(ReadTableNameConfig.READPRINTCYCLE)