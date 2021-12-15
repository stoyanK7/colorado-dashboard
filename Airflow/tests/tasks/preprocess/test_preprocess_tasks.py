import unittest

from unittest.mock import patch
from unittest.mock import MagicMock, Mock

import numpy
from tabulate import tabulate

from tasks.preprocess.preprocess_tasks import PreprocessTasks
import pandas as pd
from config import clean_table_name_config, clean_image_col_name_constants, aggregate_column_name_config, \
    aggregate_table_name_config, clean_print_cycle_col_name_constants, preprocess_col_name_constants


class TestAggregateTasks(unittest.TestCase):

    def test_divide_column_by(self):
        data = pd.DataFrame(data={clean_print_cycle_col_name_constants.SQUARE_DECIMETER: ["3", "2", "1"]})
        expected = pd.DataFrame(data={preprocess_col_name_constants.PREPROCESSED_SQUARE_DECIMETER: ["0.03", "0.02", "0.01"]})

        new_dtypes1 = {clean_print_cycle_col_name_constants.SQUARE_DECIMETER: numpy.float64}
        new_dtypes2 = {preprocess_col_name_constants.PREPROCESSED_SQUARE_DECIMETER: numpy.float64}
        expected = expected.astype(new_dtypes2)
        data = data.astype(new_dtypes1)

        actual = PreprocessTasks._divide_column_by(data, clean_print_cycle_col_name_constants.SQUARE_DECIMETER, preprocess_col_name_constants.PREPROCESSED_SQUARE_DECIMETER, 100)

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))

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

    def test_divide_four_columns_by(self):
        data = pd.DataFrame(data={clean_image_col_name_constants.DATE: ["04/02/2021", "05/02/2021",
                                                                        "06/02/2021", "07/02/2021"],
                                  clean_image_col_name_constants.MEDIA_TYPE: ["Paper", "Board", "Paper", "Board"],
                                  clean_image_col_name_constants.MACHINEID: ["1111", "2222", "3333", "3333"],
                                  aggregate_column_name_config.IMAGE_AREA: ["175822", "738546", "738566", "58483"]})

        expected = pd.DataFrame(data={clean_image_col_name_constants.DATE: ["04/02/2021", "05/02/2021",
                                                                            "06/02/2021", "07/02/2021"],
                                      clean_image_col_name_constants.MEDIA_TYPE: ["Paper", "Board", "Paper", "Board"],
                                      clean_image_col_name_constants.MACHINEID: ["1.111", "2.222", "3.333", "3.333"],
                                      aggregate_column_name_config.IMAGE_AREA: ["175.822", "738.546", "738.566",
                                                                                "58.483"]})
        new_dtypes = {aggregate_column_name_config.IMAGE_AREA: numpy.float64}
        data = expected.astype(new_dtypes)
        expected = expected.astype(new_dtypes)

        actual = PreprocessTasks._divide_four_columns_by(data, clean_image_col_name_constants.ACCOUNTED_INK_BLACK,
                                                         clean_image_col_name_constants.ACCOUNTED_INK_CYAN,
                                                         clean_image_col_name_constants.ACCOUNTED_INK_MAGENTA,
                                                         clean_image_col_name_constants.ACCOUNTED_INK_YELLOW,
                                                         1000)

        # print(tabulate(actual, headers='keys', tablefmt='psql'))

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))
