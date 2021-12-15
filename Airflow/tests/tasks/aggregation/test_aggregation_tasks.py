import unittest
from unittest.mock import patch
from unittest.mock import MagicMock, Mock

import numpy
from tabulate import tabulate

from tasks.aggregate.aggregate_tasks import AggregateTasks
import pandas as pd
from config import clean_table_name_config, clean_image_col_name_constants, aggregate_column_name_config, \
    aggregate_table_name_config


class TestAggregateTasks(unittest.TestCase):

    def test_group_by_three_columns_and_sum_third(self):
        data = pd.DataFrame(data={clean_image_col_name_constants.DATE: ["04/02/2021", "04/02/2021", "05/02/2021",
                                                                        "05/02/2021"],
                                  clean_image_col_name_constants.MEDIA_TYPE: ["Paper", "Paper", "Board", "Paper"],
                                  aggregate_column_name_config.IMAGE_AREA: ["0.0490007", "0.126821",
                                                                            "0.0738546", "0.058483"],
                                  clean_image_col_name_constants.MACHINEID: ["1111111", "1111111", "2222222", "3333333"]

                                  })
        new_dtypes = {aggregate_column_name_config.IMAGE_AREA: numpy.float64}
        data = data.astype(new_dtypes)
        expected = pd.DataFrame(data={clean_image_col_name_constants.DATE: ["04/02/2021", "05/02/2021",
                                                                            "05/02/2021"],
                                      clean_image_col_name_constants.MEDIA_TYPE: ["Paper", "Board", "Paper"],
                                clean_image_col_name_constants.MACHINEID: ["1111111", "2222222", "3333333"],
                                      aggregate_column_name_config.IMAGE_AREA: ["0.175822", "0.0738546",
                                                                                "0.058483"]
                                      })
        new_dtypes = {aggregate_column_name_config.IMAGE_AREA: numpy.float64}
        expected = expected.astype(new_dtypes)

        actual = AggregateTasks._group_by_three_columns_and_sum_third(data,
                                                                      clean_image_col_name_constants.DATE,
                                                                      clean_image_col_name_constants.MEDIA_TYPE,
                                                                      clean_image_col_name_constants.MACHINEID,
                                                                      aggregate_column_name_config.IMAGE_AREA)

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))

    def test_aggregate_two_columns(self):
        data = pd.DataFrame(data={"ullid": [0, 1, 2, 3],
                                  clean_image_col_name_constants.IMAGE_WIDTH: ["0.166116", "0.166432", "0.166152",
                                                                               "0.166124"],
                                  clean_image_col_name_constants.IMAGE_LENGTH: ["0.294979", "0.762", "0.4445",
                                                                                "0.352044"],
                                  clean_image_col_name_constants.DATE: ["04/02/2021", "04/02/2021", "05/02/2021",
                                                                        "05/02/2021"],
                                  clean_image_col_name_constants.MEDIA_TYPE: ["Paper", "Board", "Board", "Paper"]
                                  })
        new_dtypes = {clean_image_col_name_constants.IMAGE_WIDTH: numpy.float64,
                      clean_image_col_name_constants.IMAGE_LENGTH: numpy.float64}
        data = data.astype(new_dtypes)

        expected = pd.DataFrame(data={"ullid": [0, 1, 2, 3],
                                      clean_image_col_name_constants.DATE: ["04/02/2021", "04/02/2021", "05/02/2021",
                                                                            "05/02/2021"],
                                      clean_image_col_name_constants.MEDIA_TYPE: ["Paper", "Board", "Board", "Paper"],
                                      aggregate_column_name_config.IMAGE_AREA: ["0.0490007", "0.126821",
                                                                                "0.0738546", "0.058483"]
                                      })
        new_dtypes = {aggregate_column_name_config.IMAGE_AREA: numpy.float64}
        expected = expected.astype(new_dtypes)

        # Act
        actual = AggregateTasks._aggregate_two_columns(data,
                                                       clean_image_col_name_constants.IMAGE_WIDTH,
                                                       clean_image_col_name_constants.IMAGE_LENGTH,
                                                       aggregate_column_name_config.IMAGE_AREA,
                                                       True)

        # Assert
        pd.testing.assert_frame_equal(expected.reset_index(drop=True), actual.reset_index(drop=True))

    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._read_from_db")
    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._aggregate_two_columns")
    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._group_by_three_columns_and_sum_third")
    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._insert_into_db")
    def test_aggregate_media_category_usage_full(self,
                                                 mock_df_to_db: MagicMock,
                                                 mock_grouping: MagicMock,
                                                 mock_aggregate_two_columns: MagicMock,
                                                 mock_read_from_db: MagicMock):
        data = pd.DataFrame(data={"ullid": [0, 1, 2, 3],
                                  clean_image_col_name_constants.IMAGE_WIDTH: ["0.166116", "0.166432", "0.166152",
                                                                               "0.166124"],
                                  clean_image_col_name_constants.IMAGE_LENGTH: ["0.294979", "0.762", "0.4445",
                                                                                "0.352044"],
                                  clean_image_col_name_constants.DATE: ["04/02/2021", "04/02/2021", "05/02/2021",
                                                                        "05/02/2021"],
                                  clean_image_col_name_constants.MEDIA_TYPE: ["Paper", "Board", "Board", "Paper"]
                                  })
        mock_read_from_db.return_value = data
        mock_aggregate_two_columns.return_value = data
        mock_grouping.return_value = data

        # Act
        AggregateTasks.aggregate_media_category_usage()

        # Assert
        mock_read_from_db.assert_called_once_with(clean_table_name_config.READ_IMAGE)

        mock_aggregate_two_columns.assert_called_once_with(data,
                                                           clean_image_col_name_constants.IMAGE_WIDTH,
                                                           clean_image_col_name_constants.IMAGE_LENGTH,
                                                           aggregate_column_name_config.IMAGE_AREA,
                                                           True)

        mock_grouping.assert_called_once_with(data,
                                              clean_image_col_name_constants.DATE,
                                              clean_image_col_name_constants.MEDIA_TYPE,
                                              clean_image_col_name_constants.MACHINEID,
                                              aggregate_column_name_config.IMAGE_AREA)

        mock_df_to_db.assert_called_once_with(data, aggregate_table_name_config.AGGREGATE_IMAGE)

    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._read_from_db")
    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._aggregate_two_columns")
    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._group_by_three_columns_and_sum_third")
    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._insert_into_db")
    def test_aggregate_media_category_usage_no_data(self,
                                                    mock_df_to_db: MagicMock,
                                                    mock_grouping: MagicMock,
                                                    mock_aggregate_two_columns: MagicMock,
                                                    mock_read_from_db: MagicMock):
        mock_read_from_db.return_value = pd.DataFrame()

        # Act
        AggregateTasks.aggregate_media_category_usage()

        # Assert

        mock_read_from_db.assert_called_once_with(clean_table_name_config.READ_IMAGE)

        mock_aggregate_two_columns.assert_not_called()
        mock_grouping.assert_not_called()
        mock_df_to_db.assert_not_called()
