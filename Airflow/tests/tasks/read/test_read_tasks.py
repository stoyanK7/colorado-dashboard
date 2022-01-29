import unittest
from unittest.mock import patch
from unittest.mock import MagicMock, Mock

import airflow.models

import tasks.read.file_reader
from DAL.postgres_database_manager import PostgresDatabaseManager
from tasks.read.file_manager import FileManager
from tasks.read.read_tasks import ReadTasks
import pandas as pd
from config import last_seen_column_name_config, last_seen_table_config, read_table_name_config, \
    read_image_col_name_constants



class TestReadTasks(unittest.TestCase):
    # Use this example for function 1
    @patch("DAL.postgres_database_manager.PostgresDatabaseManager.insert_into_table")
    def test_insert_into_db(self, mock_pdm: MagicMock):
        data = pd.DataFrame(data={"ullid": [0, 1, 2, 3],
                                "AccountedInkBlack[cl]#": [3, 3, 3, 3]})
        table_name = "SomeTable"

        ReadTasks._insert_into_db(data, table_name)

        # Assert
        mock_pdm.assert_called_once_with(data, table_name)

    @patch("tasks.read.file_manager.FileManager.copy_files_to_dir")
    def test_make_snapshot(self, mock_pdm: MagicMock):
        rsync_directory = "some_directory"
        snapshot_directory = "snapshot_dir"
        file_directory_extension = ".csv"

        ReadTasks._make_snapshot(rsync_directory, snapshot_directory, file_directory_extension)

        # Assert
        mock_pdm.assert_called_once_with(rsync_directory, snapshot_directory, file_directory_extension)

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

