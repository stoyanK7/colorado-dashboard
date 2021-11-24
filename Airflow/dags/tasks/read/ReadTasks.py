import logging

import pandas as pd
import os
from airflow.models.variable import Variable

# from tasks.read.FileReader import FileReader
# from DAL.PostgresDatabaseManager import PostgresDatabaseManager
# from config import ReadTableNameConfig, LastSeenTableConfig, LastSeenColumnNameConfig
from tasks.read.FileReader import FileReader
from DAL.PostgresDatabaseManager import PostgresDatabaseManager
from config import ReadTableNameConfig, LastSeenTableConfig, LastSeenColumnNameConfig, ReadImageColNameConstants


class ReadTasks():

    @staticmethod
    def ReadImage(ti):

        lastSeenFile, lastSeenRow = ReadTasks._getLastFileAndRow(LastSeenTableConfig.LAST_SEEN_IMAGE_TABLE,
                                      LastSeenColumnNameConfig.LAST_SEEN_IMAGE_FILE_PATH,
                                      LastSeenColumnNameConfig.LAST_SEEN_IMAGE_ROW_ID)

        filesToRead = ReadTasks._getFileNames("image_file_directory", lastSeenFile)

        if len(filesToRead) <= 0:
            logging.info("No files were found, terminating reading step successfully.")
            return

        data = ReadTasks._getFilesToDataFrames("image_file_directory", filesToRead, lastSeenFile, lastSeenRow)

        if data.empty:
            logging.info("No new data was found, terminating reading step successfully.")
            return

        ReadTasks._changeColNames(data, ReadImageColNameConstants)

        if data.empty:
            logging.error("There are no new files to read")

        ReadTasks._insertIntoDb(data, ReadTableNameConfig.READIMAGE)

        ReadTasks._makeXcom(ti, filesToRead[len(filesToRead) - 1], data[Variable.get("image_col_name_ullid")].iloc[-1])

        # lastReadData = {LastSeenColumnNameConfig.LAST_SEEN_IMAGE_FILE_PATH:[filesToRead[len(filesToRead) - 1]], LastSeenColumnNameConfig.LAST_SEEN_IMAGE_ROW_ID:[data["ullid"].iloc[-1]]}
        # lastSeenDf = pd.DataFrame(data=lastReadData)
        # pdm=PostgresDatabaseManager()
        # pdm.insertIntoTable(lastSeenDf, LastSeenTableConfig.LAST_SEEN_IMAGE_TABLE)
    def ReadMediaPrepare(self):
        # do stuff, remove pass
        pass
    def ReadPrintCycle(self):
        # do stuff, remove pass
        pass

    @staticmethod
    def _getLastFileAndRow(tableName, filePathColName, rowColName):
        # Read last seen table & row
        logging.info("Getting last seen file and row.")
        pdm = PostgresDatabaseManager()
        lastSeenTable = ""
        try:
            lastSeenTable = pdm.readTable(tableName)
        except:
            lastSeenTable = pd.DataFrame()
        lastSeenFile = ""
        lastSeenRow = ""
        if not lastSeenTable.empty:
            lastSeenFile = lastSeenTable[filePathColName].iloc[-1]
            lastSeenRow = lastSeenTable[rowColName].iloc[-1]
            logging.info("Found last seen file: " + lastSeenFile + " at row with ullid " + lastSeenRow + ".")
        else:
            logging.info("Table " + tableName + " was empty, reading all instead.")
        return lastSeenFile, lastSeenRow

    @staticmethod
    def _getFileNames(directoryVariableKey, lastSeenFile):
        # Get filenames
        logging.info("Getting filenames from directory.")
        fileReader = FileReader()
        directory = os.getenv("AIRFLOW_HOME") + Variable.get(directoryVariableKey)
        logging.info(
            f"Reading files from {directory} {f'starting at file {lastSeenFile}' if lastSeenFile != '' else ''}")
        filesToRead = fileReader.getFileNamesStartingFrom(directory, lastSeenFile)
        logging.info(f'Found {len(filesToRead)} files.')
        return filesToRead

    @staticmethod
    def _getFilesToDataFrames(directoryVariableKey, filesToRead, lastSeenFile, lastSeenRow) -> pd.DataFrame:
        # get files
        logging.info("Getting data from files.")
        dataFrames = []
        fileReader = FileReader()
        directory = os.getenv("AIRFLOW_HOME") + Variable.get(directoryVariableKey)

        for file in filesToRead:
            logging.info(f"Getting data from file {file}.")
            df = fileReader.readPandasCsvFile(directory + file, ";")
            df = df.set_index(df[Variable.get("image_col_name_ullid")])
            df = df.sort_index('index')
            if file == lastSeenFile:
                df = df[df[Variable.get("image_col_name_ullid")] > int(lastSeenRow)]
                logging.info(
                    "Removed rows " + lastSeenRow + " and before from file " + file + " because they were seen before.")
            dataFrames.append(df)
        resultDataFrames = pd.concat(dataFrames, ignore_index=True)
        return resultDataFrames

    @staticmethod
    def _changeColNames(data, constantsFile):
        # change the column names
        logging.info("Renaming columns for internal use.")
        renameScheme = {}
        for pair in constantsFile.__dict__:
            if not pair.startswith('_'):
                if (pair.startswith("p")):
                    pair = getattr(constantsFile, pair)
                    renameScheme[Variable.get(pair[0])] = pair[1]
        logging.info("Old column names:")
        logging.info(data.columns)
        data.rename(columns=renameScheme, inplace=True)
        logging.info("New column names:")
        logging.info(data.columns)
        return data



    @staticmethod
    def _insertIntoDb(data, tableName):
        # put in db
        logging.info("Inserting read data to database.")
        pdm = PostgresDatabaseManager()
        pdm.insertIntoTable(data, tableName)

    @staticmethod
    def _makeXcom(ti, lastSeenFile, lastSeenRow):
        # send xcom about last read file and row
        logging.info("Sending xcom about last read information.")
        ti.xcom_push("lastSeenFile", str(lastSeenFile))
        ti.xcom_push("lastSeenRow", str(lastSeenRow))