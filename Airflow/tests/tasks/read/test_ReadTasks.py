import unittest
from unittest.mock import patch
from unittest.mock import MagicMock, Mock

import airflow.models

import tasks.read.FileReader
from DAL.PostgresDatabaseManager import PostgresDatabaseManager
from tasks.read.ReadTasks import ReadTasks
import pandas as pd
from config import LastSeenColumnNameConfig, LastSeenTableConfig, ReadTableNameConfig, ReadImageColNameConstants


class TestReadTasks(unittest.TestCase):
    @patch("DAL.PostgresDatabaseManager.PostgresDatabaseManager.readTable")
    def test_getLastFileAndRow(self, mockPdm: MagicMock):
        mockPdm.return_value = \
            pd.DataFrame(data={LastSeenColumnNameConfig.LAST_SEEN_IMAGE_ROW_ID: ['8', '3'],
                               LastSeenColumnNameConfig.LAST_SEEN_IMAGE_FILE_PATH: ["SomeOtherFile", "testFile1.csv"]})

        lastSeenFile, lastSeenRow = ReadTasks._getLastFileAndRow(
            "testTable",
            LastSeenColumnNameConfig.LAST_SEEN_IMAGE_FILE_PATH,
            LastSeenColumnNameConfig.LAST_SEEN_IMAGE_ROW_ID)
        self.assertEqual(lastSeenFile, "testFile1.csv")
        self.assertEqual(lastSeenRow, '3')
        mockPdm.assert_called_once_with("testTable")

    @patch("DAL.PostgresDatabaseManager.PostgresDatabaseManager.readTable")
    def test_getLastFileAndRowIfEmpty(self, mockPdm: MagicMock):
        mockPdm.return_value = \
            pd.DataFrame(data={LastSeenColumnNameConfig.LAST_SEEN_IMAGE_ROW_ID: [],
                               LastSeenColumnNameConfig.LAST_SEEN_IMAGE_FILE_PATH: []})

        lastSeenFile, lastSeenRow = ReadTasks._getLastFileAndRow(
            "testTable",
            LastSeenColumnNameConfig.LAST_SEEN_IMAGE_FILE_PATH,
            LastSeenColumnNameConfig.LAST_SEEN_IMAGE_ROW_ID)
        self.assertEqual(lastSeenFile, "")
        self.assertEqual(lastSeenRow, '')
        mockPdm.assert_called_once_with("testTable")

    @patch("airflow.models.variable.Variable.get")
    @patch("os.getenv")
    @patch("tasks.read.FileReader.FileReader.getFileNamesStartingFrom")
    def test_getFileNames(self, mockFileReader: MagicMock, mockOs: MagicMock, mockVar: MagicMock):
        mockFileReader.return_value = ["testFile1", "testFile2"]
        mockOs.return_value = "Some/Os/Path/"
        mockVar.return_value = "To/The/Files/"

        filesToRead = ReadTasks._getFileNames("somekey", "testFile1")

        self.assertListEqual(filesToRead, ["testFile1", "testFile2"])
        mockFileReader.assert_called_once_with("Some/Os/Path/To/The/Files/", "testFile1")
        mockOs.assert_called_once_with("AIRFLOW_HOME")
        mockVar.assert_called_once_with("somekey")

    @patch("airflow.models.variable.Variable.get")
    @patch("os.getenv")
    @patch("tasks.read.FileReader.FileReader.getFileNamesStartingFrom")
    def test_getFileNamesEmtpy(self, mockFileReader: MagicMock, mockOs: MagicMock, mockVar: MagicMock):
        mockFileReader.return_value = []
        mockOs.return_value = "Some/Os/Path/"
        mockVar.return_value = "To/The/Files/"

        filesToRead = ReadTasks._getFileNames("somekey", "lastSeenFile")

        self.assertListEqual(filesToRead, [])
        mockFileReader.assert_called_once_with("Some/Os/Path/To/The/Files/", "lastSeenFile")
        mockOs.assert_called_once_with("AIRFLOW_HOME")
        mockVar.assert_called_once_with("somekey")

    @patch("airflow.models.variable.Variable.get")
    @patch("os.getenv")
    @patch("tasks.read.FileReader.FileReader.readPandasCsvFile")
    def test_getFilesToDataFramesWithEmptyLastSeen(self, mockFileReader: MagicMock, mockOs: MagicMock, mockVar: MagicMock):
        df1 = pd.DataFrame(data={"ullid": [0, 1, 2, 3], "values": ["value1", "value2", "value3", "value4"]})
        df2 = pd.DataFrame(data={"ullid": [4, 5, 6, 7], "values": ["value5", "value6", "value7", "value8"]})
        mockFileReader.side_effect = [df1, df2]
        mockOs.return_value = "Some/Os/Path/"

        def varSideEffect(value):
            if (value == directoryVariableKey):
                return "To/The/Files/"
            elif (value == "image_col_name_ullid"):
                return "ullid"

        mockVar.side_effect = varSideEffect

        directoryVariableKey = "someKey"
        filesToRead = ["file1", "file2"]
        lastSeenFile = ""
        lastSeenRow = ""

        resultDataFrames = ReadTasks._getFilesToDataFrames(directoryVariableKey, filesToRead, lastSeenFile, lastSeenRow)

        expected = pd.concat([df1, df2], ignore_index=True)
        pd.testing.assert_frame_equal(resultDataFrames, expected)
        mockFileReader.assert_has_calls([unittest.mock.call("Some/Os/Path/To/The/Files/file1", ";"),
                                         unittest.mock.call("Some/Os/Path/To/The/Files/file2", ";")])
        mockOs.assert_called_once_with("AIRFLOW_HOME")
        mockVar.assert_has_calls([unittest.mock.call(directoryVariableKey), unittest.mock.call("image_col_name_ullid")])

    @patch("airflow.models.variable.Variable.get")
    @patch("os.getenv")
    @patch("tasks.read.FileReader.FileReader.readPandasCsvFile")
    def test_getFilesToDataFramesWithLastSeen(self, mockFileReader: MagicMock, mockOs: MagicMock, mockVar: MagicMock):
        df1 = pd.DataFrame(data={"ullid":[0, 1, 2, 3], "values": ["value1", "value2", "value3", "value4"]})
        df2 = pd.DataFrame(data={"ullid": [4, 5, 6, 7], "values": ["value5", "value6", "value7", "value8"]})
        mockFileReader.side_effect = [df1, df2]
        mockOs.return_value = "Some/Os/Path/"
        def varSideEffect(value):
            if (value == directoryVariableKey):
                return "To/The/Files/"
            elif (value == "image_col_name_ullid"):
                return "ullid"
        mockVar.side_effect = varSideEffect

        directoryVariableKey = "someKey"
        filesToRead=["file1", "file2"]
        lastSeenFile = "file1"
        lastSeenRow = "2"

        resultDataFrames = ReadTasks._getFilesToDataFrames(directoryVariableKey, filesToRead, lastSeenFile, lastSeenRow)

        expected = pd.DataFrame(data={"ullid":[3, 4, 5, 6, 7], "values":["value4", "value5", "value6", "value7", "value8"]})
        pd.testing.assert_frame_equal(resultDataFrames, expected)
        mockFileReader.assert_has_calls([unittest.mock.call("Some/Os/Path/To/The/Files/file1", ";"), unittest.mock.call("Some/Os/Path/To/The/Files/file2", ";")])
        mockOs.assert_called_once_with("AIRFLOW_HOME")
        mockVar.assert_has_calls([unittest.mock.call(directoryVariableKey), unittest.mock.call("image_col_name_ullid")])

    @patch("DAL.PostgresDatabaseManager.PostgresDatabaseManager.insertIntoTable")
    def test_insertIntoDb(self, mockPdm: MagicMock):
        data = "SomeRandomData"
        tableName="SomeTable"

        ReadTasks._insertIntoDb(data, tableName)

        # Assert
        mockPdm.assert_called_once_with(data, tableName)

    def test_makeXcom(self):
        ti = Mock()
        ReadTasks._makeXcom(ti, "someFile", "someRow")
        # Assert
        ti.xcom_push.assert_has_calls([unittest.mock.call("lastSeenFile", "someFile"), unittest.mock.call("lastSeenRow", "someRow")])


    @patch("airflow.models.variable.Variable.get")
    @patch("tasks.read.ReadTasks.ReadTasks._makeXcom")
    @patch("tasks.read.ReadTasks.ReadTasks._insertIntoDb")
    @patch("tasks.read.ReadTasks.ReadTasks._changeColNames")
    @patch("tasks.read.ReadTasks.ReadTasks._getFilesToDataFrames")
    @patch("tasks.read.ReadTasks.ReadTasks._getFileNames")
    @patch("tasks.read.ReadTasks.ReadTasks._getLastFileAndRow")
    def test_readImageFull(self, mockGetLastSeen: MagicMock, mockFileNames: MagicMock, mockFilesToDf: MagicMock, mockChangeColNames: MagicMock, mockInsert: MagicMock, mockXcom: MagicMock, mockVar: MagicMock):
        lastSeenFile, lastSeenRow = "someFile", 4
        filesToRead = ["file1", "file2"]
        variableKey = "image_file_directory"
        data = pd.DataFrame(data={"ullid":[0, 1, 2, 3], "values": ["value1", "value2", "value3", "value4"]})
        mockGetLastSeen.return_value = lastSeenFile, lastSeenRow
        mockFileNames.return_value = filesToRead
        mockFilesToDf.return_value = data
        mockVar.return_value = "ullid"
        ti = Mock()

        # Act
        ReadTasks.ReadImage(ti)

        # Assert
        mockGetLastSeen.assert_called_once_with(LastSeenTableConfig.LAST_SEEN_IMAGE_TABLE,
                                      LastSeenColumnNameConfig.LAST_SEEN_IMAGE_FILE_PATH,
                                      LastSeenColumnNameConfig.LAST_SEEN_IMAGE_ROW_ID)
        mockFileNames.assert_called_once_with(variableKey, lastSeenFile)
        mockFilesToDf.assert_called_once_with(variableKey, filesToRead, lastSeenFile, lastSeenRow)
        mockChangeColNames.assert_called_once_with(data, ReadImageColNameConstants)
        mockInsert.assert_called_once_with(data, ReadTableNameConfig.READIMAGE)
        mockXcom.assert_called_once_with(ti, "file2", 3)

    @patch("tasks.read.ReadTasks.ReadTasks._makeXcom")
    @patch("tasks.read.ReadTasks.ReadTasks._insertIntoDb")
    @patch("tasks.read.ReadTasks.ReadTasks._getFilesToDataFrames")
    @patch("tasks.read.ReadTasks.ReadTasks._getFileNames")
    @patch("tasks.read.ReadTasks.ReadTasks._getLastFileAndRow")
    def test_readImageNoFiles(self, mockGetLastSeen: MagicMock, mockFileNames: MagicMock, mockFilesToDf: MagicMock, mockInsert: MagicMock, mockXcom: MagicMock):
        lastSeenFile, lastSeenRow = "someFile", 4
        filesToRead = []
        variableKey = "image_file_directory"
        data = pd.DataFrame(data={"ullid":[0, 1, 2, 3], "values": ["value1", "value2", "value3", "value4"]})
        mockGetLastSeen.return_value = lastSeenFile, lastSeenRow
        mockFileNames.return_value = filesToRead
        mockFilesToDf.return_value = data
        ti = Mock()

        # Act
        ReadTasks.ReadImage(ti)

        # Assert
        mockGetLastSeen.assert_called_once_with(LastSeenTableConfig.LAST_SEEN_IMAGE_TABLE,
                                      LastSeenColumnNameConfig.LAST_SEEN_IMAGE_FILE_PATH,
                                      LastSeenColumnNameConfig.LAST_SEEN_IMAGE_ROW_ID)
        mockFileNames.assert_called_once_with(variableKey, lastSeenFile)
        mockFilesToDf.assert_not_called()
        mockInsert.assert_not_called()
        mockXcom.assert_not_called()

    @patch("tasks.read.ReadTasks.ReadTasks._makeXcom")
    @patch("tasks.read.ReadTasks.ReadTasks._insertIntoDb")
    @patch("tasks.read.ReadTasks.ReadTasks._getFilesToDataFrames")
    @patch("tasks.read.ReadTasks.ReadTasks._getFileNames")
    @patch("tasks.read.ReadTasks.ReadTasks._getLastFileAndRow")
    def test_readImageNoNewData(self, mockGetLastSeen: MagicMock, mockFileNames: MagicMock, mockFilesToDf: MagicMock,
                           mockInsert: MagicMock, mockXcom: MagicMock):
        lastSeenFile, lastSeenRow = "file2", 3
        filesToRead = ["file1", "file2"]
        variableKey = "image_file_directory"
        data = pd.DataFrame(data={"ullid": [], "values": []})
        mockGetLastSeen.return_value = lastSeenFile, lastSeenRow
        mockFileNames.return_value = filesToRead
        mockFilesToDf.return_value = data
        ti = Mock()

        # Act
        ReadTasks.ReadImage(ti)

        # Assert
        mockGetLastSeen.assert_called_once_with(LastSeenTableConfig.LAST_SEEN_IMAGE_TABLE,
                                                LastSeenColumnNameConfig.LAST_SEEN_IMAGE_FILE_PATH,
                                                LastSeenColumnNameConfig.LAST_SEEN_IMAGE_ROW_ID)
        mockFileNames.assert_called_once_with(variableKey, lastSeenFile)
        mockFilesToDf.assert_called_once_with(variableKey, filesToRead, lastSeenFile, lastSeenRow)
        mockInsert.assert_not_called()
        mockXcom.assert_not_called()