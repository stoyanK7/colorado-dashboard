import logging

import pandas as pd
import os
from airflow.models.variable import Variable

# from tasks.read.FileReader import FileReader
# from DAL.PostgresDatabaseManager import PostgresDatabaseManager
# from config import ReadTableNameConfig, LastSeenTableConfig, LastSeenColumnNameConfig
from tasks.read.FileReader import FileReader
from DAL.PostgresDatabaseManager import PostgresDatabaseManager
from config import ReadTableNameConfig, LastSeenTableConfig, LastSeenColumnNameConfig


class ReadTasks():

    @staticmethod
    def ReadImage(ti):

        lastSeenFile, lastSeenRow = ReadTasks.__getLastFileAndRow(LastSeenTableConfig.LAST_SEEN_IMAGE_TABLE,
                                      LastSeenColumnNameConfig.LAST_SEEN_IMAGE_FILE_PATH,
                                      LastSeenColumnNameConfig.LAST_SEEN_IMAGE_ROW_ID)

        filesToRead = ReadTasks.__getFileNames("image_file_directory", lastSeenFile)

        if len(filesToRead) <= 0:
            logging.info("No files were found, terminating reading step successfully.")
            return

        data = ReadTasks.__getFilesToDataFrames("image_file_directory", filesToRead, lastSeenFile, lastSeenRow)

        if data.empty:
            logging.info("No new data was found, terminating reading step successfully.")
            return

        ReadTasks.__insertIntoDb(data, ReadTableNameConfig.READIMAGE)

        ReadTasks.__makeXcom(ti, filesToRead[len(filesToRead) - 1], data["ullid"].iloc[-1])

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
    def __getLastFileAndRow(tableName, filePathColName, rowColName):
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
    def __getFileNames(directoryVariableKey, lastSeenFile):
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
    def __getFilesToDataFrames(directoryVariableKey, filesToRead, lastSeenFile, lastSeenRow) -> pd.DataFrame:
        # get files
        logging.info("Getting data from files.")
        dataFrames = []
        fileReader = FileReader()
        directory = os.getenv("AIRFLOW_HOME") + Variable.get(directoryVariableKey)

        for file in filesToRead:
            logging.info(f"Getting data from file {file}.")
            df = fileReader.readPandasCsvFile(directory + file, ";")
            df = df.set_index(df["ullid"])
            df = df.sort_index('index')
            if file == lastSeenFile:
                df = df[df['ullid'] > int(lastSeenRow)]
                logging.info(
                    "Removed rows " + lastSeenRow + " and before from file " + file + " because they were seen before.")
            dataFrames.append(df)
        resultDataFrames = pd.concat(dataFrames, ignore_index=True)
        return resultDataFrames

    @staticmethod
    def __insertIntoDb(data, tableName):
        # put in db
        logging.info("Inserting read data to database.")
        pdm = PostgresDatabaseManager()
        pdm.insertIntoTable(data, tableName)

    @staticmethod
    def __makeXcom(ti, lastSeenFile, lastSeenRow):
        # send xcom about last read file and row
        logging.info("Sending xcom about last read information.")
        ti.xcom_push("lastSeenFile", str(lastSeenFile))
        ti.xcom_push("lastSeenRow", str(lastSeenRow))