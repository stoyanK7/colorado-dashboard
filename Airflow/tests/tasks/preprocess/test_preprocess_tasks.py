import unittest

from unittest.mock import patch
from unittest.mock import MagicMock, Mock

import numpy
from tabulate import tabulate

from tasks.preprocess.preprocess_tasks import PreprocessTasks
import pandas as pd
from config import clean_table_name_config, clean_image_col_name_constants, aggregate_column_name_config, \
    aggregate_table_name_config, clean_print_cycle_col_name_constants, preprocess_col_name_constants


class TestPreprocessTasks(unittest.TestCase):
    def test_converting_units_to_default_values(self):
        df = pd.DataFrame(data={"ullid": [0, 1, 2, 3],
                                "AccountedInkBlack": [3, 3, 3, 3],
                                "AccountedInkBlack|unit|": ["cl", "cl", "cl", "cl"]})

        expected = pd.DataFrame(data={"ullid": [0, 1, 2, 3],
                                "AccountedInkBlack": [0.03, 0.03, 0.03, 0.03]})

        actual = PreprocessTasks._converting_units_to_default_values(df, [])

        pd.testing.assert_frame_equal(actual, expected)

    def test_removing_unnecessary_columns(self):
        df = pd.DataFrame(data={"ullid": [0, 1, 2, 3],
                                "AccountedInkBlack": [3, 3, 3, 3],
                                "AccountedInkBlack|unit|": ["cl", "cl", "cl", "cl"],
                                "SmtTest": ["value", "value", "value", "value"]})

        expected = pd.DataFrame(data={"ullid": [0, 1, 2, 3],
                                "AccountedInkBlack": [0.03, 0.03, 0.03, 0.03]})

        columns_to_remove = ["SmtTest"]
        actual = PreprocessTasks._converting_units_to_default_values(df, columns_to_remove)

        pd.testing.assert_frame_equal(actual, expected)

    def test_convert_date_to_utc(self):
        df = pd.DataFrame(data={"ullid": [0],
                                "date": ["2022-01-17"]})

        expected = pd.DataFrame(data={"ullid": [0],
                                "date": ["2022-01-17"]})

        actual = PreprocessTasks._convert_date_to_utc(df)

        pd.testing.assert_frame_equal(actual, expected)

    def test_merge_two_dataframes(self):
        df1 = pd.DataFrame(data={clean_image_col_name_constants.DATE: ["04/02/2021", "05/02/2021",
                                                                       "06/02/2021"],
                                 clean_image_col_name_constants.MEDIA_TYPE: ["Paper", "Board", "Paper"],
                                 clean_image_col_name_constants.MACHINEID: ["1111111", "2222222", "3333333"],
                                 aggregate_column_name_config.IMAGE_AREA: ["0.175822", "0.0738546",
                                                                           "0.058483"]})

        df2 = pd.DataFrame(data={clean_image_col_name_constants.DATE: ["04/02/2021", "05/02/2021",
                                                                       "06/02/2021"],
                                 clean_print_cycle_col_name_constants.SQUARE_DECIMETER: ["3", "2", "1"]})

        expected = pd.DataFrame(data={clean_image_col_name_constants.DATE: ["04/02/2021", "05/02/2021",
                                                                            "06/02/2021"],
                                      clean_image_col_name_constants.MEDIA_TYPE: ["Paper", "Board", "Paper"],
                                      clean_image_col_name_constants.MACHINEID: ["1111111", "2222222", "3333333"],
                                      aggregate_column_name_config.IMAGE_AREA: ["0.175822", "0.0738546",
                                                                                "0.058483"],
                                      clean_print_cycle_col_name_constants.SQUARE_DECIMETER: ["3", "2", "1"]})

        actual = PreprocessTasks._merge_two_dataframes(df1, df2, clean_image_col_name_constants.DATE)
        # print(tabulate(actual, headers='keys', tablefmt='psql'))

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))
