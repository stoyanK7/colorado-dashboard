from ...DAL import PostgresDatabaseManager
from ...config import ReadTableNameConfig, CleaningColumnNameConfig
from airflow.models import Variable

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
        self.RemoveDuplicates(df)


        # check integer or string. Check absurd value. Check negative value.
        self.CheckType(df)

        # Create table

        # Store table
        pass


    def RemoveDuplicates(self,df):
        df.drop_duplicates()
        df.drop_duplicates(subset=[CleaningColumnNameConfig.ULLID])

    def CheckType(self, df):
        if ()


    def CleanMediaPrepare(self):
        return PostgresDatabaseManager.readTable(ReadTableNameConfig.READMEDIAPREPARE)

    def CleanPrintCycle(self):
        return PostgresDatabaseManager.readTable(ReadTableNameConfig.READPRINTCYCLE)