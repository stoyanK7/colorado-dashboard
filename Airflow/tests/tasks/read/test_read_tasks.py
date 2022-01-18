import unittest
from unittest.mock import patch
from unittest.mock import MagicMock, Mock

import airflow.models

import tasks.read.file_reader
from DAL.postgres_database_manager import PostgresDatabaseManager
from tasks.read.read_tasks import ReadTasks
import pandas as pd
from config import last_seen_column_name_config, last_seen_table_config, read_table_name_config, \
    read_image_col_name_constants


class TestReadTasks(unittest.TestCase):
    @patch("DAL.postgres_database_manager.PostgresDatabaseManager.read_table")
    def test_get_last_file_and_row(self, mock_pdm: MagicMock):
        mock_pdm.return_value = \
            pd.DataFrame(data={last_seen_column_name_config.LAST_SEEN_IMAGE_ROW_ID: ['8', '3'],
                               last_seen_column_name_config.LAST_SEEN_IMAGE_FILE_PATH: ["SomeOtherFile",
                                                                                        "testFile1.csv"]})

        last_seen_file, last_seen_row = ReadTasks._get_last_file_and_row(
            "testTable",
            last_seen_column_name_config.LAST_SEEN_IMAGE_FILE_PATH,
            last_seen_column_name_config.LAST_SEEN_IMAGE_ROW_ID)
        self.assertEqual(last_seen_file, "testFile1.csv")
        self.assertEqual(last_seen_row, '3')
        mock_pdm.assert_called_once_with("testTable")

    @patch("DAL.postgres_database_manager.PostgresDatabaseManager.read_table")
    def test_get_last_file_and_row_if_empty(self, mock_pdm: MagicMock):
        mock_pdm.return_value = \
            pd.DataFrame(data={last_seen_column_name_config.LAST_SEEN_IMAGE_ROW_ID: [],
                               last_seen_column_name_config.LAST_SEEN_IMAGE_FILE_PATH: []})

        last_seen_file, last_seen_row = ReadTasks._get_last_file_and_row(
            "testTable",
            last_seen_column_name_config.LAST_SEEN_IMAGE_FILE_PATH,
            last_seen_column_name_config.LAST_SEEN_IMAGE_ROW_ID)
        self.assertEqual(last_seen_file, "")
        self.assertEqual(last_seen_row, '')
        mock_pdm.assert_called_once_with("testTable")

    @patch("airflow.models.variable.Variable.get")
    @patch("os.getenv")
    @patch("tasks.read.file_reader.FileReader.get_file_names_starting_from")
    def test_get_file_names(self, mock_file_reader: MagicMock, mock_os: MagicMock, mock_var: MagicMock):
        mock_file_reader.return_value = ["testFile1", "testFile2"]
        mock_os.return_value = "Some/Os/Path/"
        mock_var.return_value = "To/The/Files/"

        files_to_read = ReadTasks._get_file_names("somekey", "testFile1")

        self.assertListEqual(files_to_read, ["testFile1", "testFile2"])
        mock_file_reader.assert_called_once_with("Some/Os/Path/To/The/Files/", "testFile1")
        mock_os.assert_called_once_with("AIRFLOW_HOME")
        mock_var.assert_called_once_with("somekey")

    @patch("airflow.models.variable.Variable.get")
    @patch("os.getenv")
    @patch("tasks.read.file_reader.FileReader.get_file_names_starting_from")
    def test_get_file_names_emtpy(self, mock_file_reader: MagicMock, mock_os: MagicMock, mock_var: MagicMock):
        mock_file_reader.return_value = []
        mock_os.return_value = "Some/Os/Path/"
        mock_var.return_value = "To/The/Files/"

        files_to_read \
            = ReadTasks._get_file_names("somekey", "last_seen_file")

        self.assertListEqual(files_to_read, [])
        mock_file_reader.assert_called_once_with("Some/Os/Path/To/The/Files/", "last_seen_file")
        mock_os.assert_called_once_with("AIRFLOW_HOME")
        mock_var.assert_called_once_with("somekey")

    @patch("airflow.models.variable.Variable.get")
    @patch("os.getenv")
    @patch("tasks.read.file_reader.FileReader.read_pandas_csv_file")
    def test_get_files_to_data_frames_with_empty_last_seen(self, mock_file_reader: MagicMock, mock_os: MagicMock,
                                                           mock_var: MagicMock):
        df1 = pd.DataFrame(data={"ullid": [0, 1, 2, 3], "values": ["value1", "value2", "value3", "value4"]})
        df2 = pd.DataFrame(data={"ullid": [4, 5, 6, 7], "values": ["value5", "value6", "value7", "value8"]})
        mock_file_reader.side_effect = [df1, df2]
        mock_os.return_value = "Some/Os/Path/"

        def var_side_effect(value):
            if (value == directory_variable_key):
                return "To/The/Files/"
            elif (value == "image_col_name_ullid"):
                return "ullid"

        mock_var.side_effect = var_side_effect

        directory_variable_key = "someKey"
        files_to_read = ["file1", "file2"]
        last_seen_file = ""
        last_seen_row = ""

        result_data_frames = ReadTasks._get_files_to_data_frames(directory_variable_key, files_to_read, last_seen_file,
                                                                 last_seen_row)

        expected = pd.concat([df1, df2], ignore_index=True)
        pd.testing.assert_frame_equal(result_data_frames, expected)
        mock_file_reader.assert_has_calls([unittest.mock.call("Some/Os/Path/To/The/Files/file1", ";"),
                                           unittest.mock.call("Some/Os/Path/To/The/Files/file2", ";")])
        mock_os.assert_called_once_with("AIRFLOW_HOME")
        mock_var.assert_has_calls(
            [unittest.mock.call(directory_variable_key), unittest.mock.call("image_col_name_ullid")])

    @patch("airflow.models.variable.Variable.get")
    @patch("os.getenv")
    @patch("tasks.read.file_reader.FileReader.read_pandas_csv_file")
    def test_get_files_to_data_frames_with_last_seen(self, mock_file_reader: MagicMock, mock_os: MagicMock,
                                                     mock_var: MagicMock):
        df1 = pd.DataFrame(data={"ullid": [0, 1, 2, 3], "values": ["value1", "value2", "value3", "value4"]})
        df2 = pd.DataFrame(data={"ullid": [4, 5, 6, 7], "values": ["value5", "value6", "value7", "value8"]})
        mock_file_reader.side_effect = [df1, df2]
        mock_os.return_value = "Some/Os/Path/"

        def var_side_effect(value):
            if (value == directory_variable_key):
                return "To/The/Files/"
            elif (value == "image_col_name_ullid"):
                return "ullid"

        mock_var.side_effect = var_side_effect

        directory_variable_key = "someKey"
        files_to_read = ["file1", "file2"]
        last_seen_file = "file1"
        last_seen_row = "2"

        result_data_frames = ReadTasks._get_files_to_data_frames(directory_variable_key, files_to_read, last_seen_file,
                                                                 last_seen_row)

        expected = pd.DataFrame(
            data={"ullid": [3, 4, 5, 6, 7], "values": ["value4", "value5", "value6", "value7", "value8"]})
        pd.testing.assert_frame_equal(result_data_frames, expected)
        mock_file_reader.assert_has_calls([unittest.mock.call("Some/Os/Path/To/The/Files/file1", ";"),
                                           unittest.mock.call("Some/Os/Path/To/The/Files/file2", ";")])
        mock_os.assert_called_once_with("AIRFLOW_HOME")
        mock_var.assert_has_calls(
            [unittest.mock.call(directory_variable_key), unittest.mock.call("image_col_name_ullid")])

    @patch("DAL.postgres_database_manager.PostgresDatabaseManager.insert_into_table")
    def test_insert_into_db(self, mock_pdm: MagicMock):
        data = "SomeRandomData"
        table_name = "SomeTable"

        ReadTasks._insert_into_db(data, table_name)

        # Assert
        mock_pdm.assert_called_once_with(data, table_name)

    def test_makeXcom(self):
        ti = Mock()
        ReadTasks._make_xcom(ti, "someFile", "someRow")
        # Assert
        ti.xcom_push.assert_has_calls(
            [unittest.mock.call("last_seen_file", "someFile"), unittest.mock.call("last_seen_row", "someRow")])

    @patch("airflow.models.variable.Variable.get")
    @patch("tasks.read.read_tasks.ReadTasks._make_xcom")
    @patch("tasks.read.read_tasks.ReadTasks._insert_into_db")
    @patch("tasks.read.read_tasks.ReadTasks._change_col_names")
    @patch("tasks.read.read_tasks.ReadTasks._get_files_to_data_frames")
    @patch("tasks.read.read_tasks.ReadTasks._get_file_names")
    @patch("tasks.read.read_tasks.ReadTasks._get_last_file_and_row")
    def test_read_image_full(self, mock_get_last_seen: MagicMock, mock_file_names: MagicMock,
                             mock_files_to_df: MagicMock, mock_change_col_names: MagicMock, mock_insert: MagicMock,
                             mock_xcom: MagicMock, mock_var: MagicMock):
        last_seen_file, last_seen_row = "someFile", 4
        files_to_read = ["file1", "file2"]
        variable_key = "image_file_directory"
        data = pd.DataFrame(data={"ullid": [0, 1, 2, 3], "values": ["value1", "value2", "value3", "value4"]})
        mock_get_last_seen.return_value = last_seen_file, last_seen_row
        mock_file_names.return_value = files_to_read
        mock_files_to_df.return_value = data
        mock_var.return_value = "ullid"
        ti = Mock()

        # Act
        ReadTasks.read_image(ti)

        # Assert
        mock_get_last_seen.assert_called_once_with(last_seen_table_config
                                                   .LAST_SEEN_IMAGE_TABLE,
                                                   last_seen_column_name_config.LAST_SEEN_IMAGE_FILE_PATH,
                                                   last_seen_column_name_config.LAST_SEEN_IMAGE_ROW_ID)
        mock_file_names.assert_called_once_with(variable_key, last_seen_file)
        mock_files_to_df.assert_called_once_with(variable_key, files_to_read, last_seen_file, last_seen_row)
        mock_change_col_names.assert_called_once_with(data, read_image_col_name_constants

                                                      )
        mock_insert.assert_called_once_with(data, read_table_name_config
                                            .READ_IMAGE)
        mock_xcom.assert_called_once_with(ti, "file2", 3)

    @patch("tasks.read.read_tasks.ReadTasks._make_xcom")
    @patch("tasks.read.read_tasks.ReadTasks._insert_into_db")
    @patch("tasks.read.read_tasks.ReadTasks._get_files_to_data_frames")
    @patch("tasks.read.read_tasks.ReadTasks._get_file_names")
    @patch("tasks.read.read_tasks.ReadTasks._get_last_file_and_row")
    def test_read_image_no_files(self, mock_get_last_seen: MagicMock, mock_file_names: MagicMock,
                                 mock_files_to_df: MagicMock, mock_insert: MagicMock, mock_xcom: MagicMock):
        last_seen_file, last_seen_row = "someFile", 4
        files_to_read = []
        variable_key = "image_file_directory"
        data = pd.DataFrame(data={"ullid": [0, 1, 2, 3], "values": ["value1", "value2", "value3", "value4"]})
        mock_get_last_seen.return_value = last_seen_file, last_seen_row
        mock_file_names.return_value = files_to_read
        mock_files_to_df.return_value = data
        ti = Mock()

        # Act
        ReadTasks.read_image(ti)

        # Assert
        mock_get_last_seen.assert_called_once_with(last_seen_table_config
                                                   .LAST_SEEN_IMAGE_TABLE,
                                                   last_seen_column_name_config.LAST_SEEN_IMAGE_FILE_PATH,
                                                   last_seen_column_name_config.LAST_SEEN_IMAGE_ROW_ID)
        mock_file_names.assert_called_once_with(variable_key, last_seen_file)
        mock_files_to_df.assert_not_called()
        mock_insert.assert_not_called()
        mock_xcom.assert_not_called()

    @patch("tasks.read.read_tasks.ReadTasks._make_xcom")
    @patch("tasks.read.read_tasks.ReadTasks._insert_into_db")
    @patch("tasks.read.read_tasks.ReadTasks._get_files_to_data_frames")
    @patch("tasks.read.read_tasks.ReadTasks._get_file_names")
    @patch("tasks.read.read_tasks.ReadTasks._get_last_file_and_row")
    def test_read_image_no_new_data(self, mock_get_last_seen: MagicMock, mock_file_names: MagicMock,
                                    mock_files_to_df: MagicMock,
                                    mock_insert: MagicMock, mock_xcom: MagicMock):
        last_seen_file, last_seen_row = "file2", 3
        files_to_read = ["file1", "file2"]
        variable_key = "image_file_directory"
        data = pd.DataFrame(data={"ullid": [], "values": []})
        mock_get_last_seen.return_value = last_seen_file, last_seen_row
        mock_file_names.return_value = files_to_read
        mock_files_to_df.return_value = data
        ti = Mock()

        # Act
        ReadTasks.read_image(ti)

        # Assert
        mock_get_last_seen.assert_called_once_with(last_seen_table_config
                                                   .LAST_SEEN_IMAGE_TABLE,
                                                   last_seen_column_name_config.LAST_SEEN_IMAGE_FILE_PATH,
                                                   last_seen_column_name_config.LAST_SEEN_IMAGE_ROW_ID)
        mock_file_names.assert_called_once_with(variable_key, last_seen_file)
        mock_files_to_df.assert_called_once_with(variable_key, files_to_read, last_seen_file, last_seen_row)
        mock_insert.assert_not_called()
        mock_xcom.assert_not_called()

    def test_read_image_add_unit_columns(self):

        df = pd.DataFrame(data={"ullid": [0, 1, 2, 3],
                                "AccountedInkBlack[cl]#": [3, 3, 3, 3]})

        expected = pd.DataFrame(data={"ullid": [0, 1, 2, 3],
                                      "AccountedInkBlack": [3, 3, 3, 3],
                                      "AccountedInkBlack|unit|": ["cl", "cl", "cl", "cl"]})

        actual = ReadTasks._add_unit_columns(df)

        pd.testing.assert_frame_equal(actual, expected)

    def test_read_image_concat_dataframes(self):
        df1 = pd.DataFrame(data={"ullid": [0, 1, 2, 3],
                                 "AccountedInkBlack[cl]#": [3, 3, 3, 3]})
        df2 = pd.DataFrame(data={"ullid": [4, 5, 6, 7],
                                 "AccountedInkBlack[cl]#": [5, 5, 5, 5]})
        dataframes = [df1, df2]

        expected = pd.DataFrame(data={"ullid": [0, 1, 2, 3, 4, 5, 6, 7],
                                      "AccountedInkBlack": [3, 3, 3, 3, 5, 5, 5, 5],
                                      "AccountedInkBlack|unit|": ["cl", "cl", "cl", "cl", "cl", "cl", "cl", "cl"]})
        actual = ReadTasks._concat_dataframes(dataframes)

        pd.testing.assert_frame_equal(actual, expected)
