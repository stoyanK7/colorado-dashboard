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
    # To figure out / test if it access right files
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

    # Use this example for function 1
    @patch("DAL.postgres_database_manager.PostgresDatabaseManager.insert_into_table")
    def test_insert_into_db(self, mock_pdm: MagicMock):
        data = pd.DataFrame(data={"ullid": [0, 1, 2, 3],
                                "AccountedInkBlack[cl]#": [3, 3, 3, 3]})
        table_name = "SomeTable"

        ReadTasks._insert_into_db(data, table_name)

        # Assert
        mock_pdm.assert_called_once_with(data, table_name)

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
