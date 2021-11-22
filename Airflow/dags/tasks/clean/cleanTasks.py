from ...DAL import PostgresDatabaseManager
from ...config import ReadTableNameConfig, CleaningColumnNameConfig, CleanTableNameConfig
from airflow.models import Variable
import pandas as pd

class CleanTasks():

    def CleanImage(self):
        obj = PostgresDatabaseManager()

        # Read Image table from Db
        df = obj.readTable(ReadTableNameConfig.READIMAGE)

        # Make dataframe using pandas
        df = df[[CleaningColumnNameConfig.ULLID,
                 CleaningColumnNameConfig.ACCOUNTEDINKBLACK,
                 CleaningColumnNameConfig.ACCOUNTEDINKCYAN,
                 CleaningColumnNameConfig.ACCOUNTEDINKMAGENTA,
                 CleaningColumnNameConfig.ACCOUNTEDINKYELLOW,
                 CleaningColumnNameConfig.DATE,
                 CleaningColumnNameConfig.IMAGELENGTH,
                 CleaningColumnNameConfig.IMAGEWIDTH,
                 CleaningColumnNameConfig.MEDIATYPE,
                 CleaningColumnNameConfig.TIME]]

        # check if ullid is same then drop
        df = self.RemoveDuplicates(df)


        # check integer or string. Check absurd value. Check negative value.
        df = self.CheckType(df)

        # Create table and store
        obj.createTable(CleanTableNameConfig.READIMAGE)
        pass


    def RemoveDuplicates(self,df):
        df.drop_duplicates()
        df.drop_duplicates(subset=[CleaningColumnNameConfig.ULLID])
        return df

    def CheckType(self, df):

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

        # remove row with length and width 0
        df = df[CleaningColumnNameConfig.IMAGELENGTH != 0.000000]
        df = df[CleaningColumnNameConfig.IMAGEWIDTH != 0.000000]

        # remove row with invalid mediaType
        array = ['canvas', 'film', 'monomeric vinyl',
                 'textile', 'unknown papertype', 'polymeric & cast vinyl',
                 'light paper < 120gsm', 'heavy paper > 200gsm',
                 'heavy banner > 400gsm', 'thick film > 200 um']
        df = df.loc[df[CleaningColumnNameConfig.MEDIATYPE].tolower().isin(array)]

        # remove row with invalid time

        return df



    def CleanMediaPrepare(self):
        return PostgresDatabaseManager.readTable(ReadTableNameConfig.READMEDIAPREPARE)

    def CleanPrintCycle(self):
        return PostgresDatabaseManager.readTable(ReadTableNameConfig.READPRINTCYCLE)