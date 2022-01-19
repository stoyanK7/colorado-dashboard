import unittest
from unittest.mock import patch
from unittest.mock import MagicMock, Mock

import numpy

from tasks.aggregate.aggregate_tasks import AggregateTasks
import pandas as pd
from config import clean_table_name_config, clean_image_col_name_constants, aggregate_column_name_config, \
    aggregate_table_name_config, preprocess_table_name_config, preprocess_col_name_constants


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

        # Act
        actual = AggregateTasks._group_by_three_columns_and_sum_third(data,
                                                                      clean_image_col_name_constants.DATE,
                                                                      clean_image_col_name_constants.MEDIA_TYPE,
                                                                      clean_image_col_name_constants.MACHINEID,
                                                                      aggregate_column_name_config.IMAGE_AREA)

        # Assert
        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))

    def test_group_by_two_columns_and_sum_third(self):
        data = pd.DataFrame(data={clean_image_col_name_constants.DATE: ["04/02/2021", "04/02/2021", "05/02/2021",
                                                                        "05/02/2021"],
                                  clean_image_col_name_constants.MACHINEID: ["111", "111", "222", "333"],
                                  aggregate_column_name_config.IMAGE_AREA: ["0.2", "0.3",
                                                                            "0.7", "0.78"]
                                  })
        new_dtypes = {aggregate_column_name_config.IMAGE_AREA: numpy.float64}
        data = data.astype(new_dtypes)
        expected = pd.DataFrame(data={clean_image_col_name_constants.DATE: ["04/02/2021", "05/02/2021",
                                                                            "05/02/2021"],
                                      clean_image_col_name_constants.MACHINEID: ["111", "222", "333"],
                                      aggregate_column_name_config.IMAGE_AREA: ["0.5", "0.7",
                                                                                "0.78"]
                                      })
        expected = expected.astype(new_dtypes)

        # Act
        actual = AggregateTasks._group_by_two_columns_and_sum_third(data,
                                                                    clean_image_col_name_constants.DATE,
                                                                    clean_image_col_name_constants.MACHINEID,
                                                                    aggregate_column_name_config.IMAGE_AREA)

        # Assert
        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))

    def test_group_by_two_columns_and_sum(self):
        data = pd.DataFrame(data={clean_image_col_name_constants.DATE: ["04/02/2021", "04/02/2021", "05/02/2021",
                                                                        "05/02/2021"],
                                  clean_image_col_name_constants.MACHINEID: ["111", "111", "222", "333"],
                                  clean_image_col_name_constants.ACCOUNTED_INK_MAGENTA: ["0.4", "0.3",
                                                                                         "0.7", "0.78"],
                                  clean_image_col_name_constants.ACCOUNTED_INK_BLACK: ["0.3", "0.7",
                                                                                       "0.6", "0.78"],
                                  clean_image_col_name_constants.ACCOUNTED_INK_YELLOW: ["0.5", "0.3",
                                                                                        "0.2", "0.78"]
                                  })
        new_dtypes = {clean_image_col_name_constants.ACCOUNTED_INK_MAGENTA: numpy.float64,
                      clean_image_col_name_constants.ACCOUNTED_INK_BLACK: numpy.float64,
                      clean_image_col_name_constants.ACCOUNTED_INK_YELLOW: numpy.float64}
        data = data.astype(new_dtypes)
        expected = pd.DataFrame(data={clean_image_col_name_constants.DATE: ["04/02/2021", "05/02/2021",
                                                                            "05/02/2021"],
                                      clean_image_col_name_constants.MACHINEID: ["111", "222", "333"],
                                      clean_image_col_name_constants.ACCOUNTED_INK_MAGENTA: ["0.7", "0.7", "0.78"],
                                      clean_image_col_name_constants.ACCOUNTED_INK_BLACK: ["1.0", "0.6", "0.78"],
                                      clean_image_col_name_constants.ACCOUNTED_INK_YELLOW: ["0.8", "0.2", "0.78"]
                                      })
        expected = expected.astype(new_dtypes)

        # Act
        actual = AggregateTasks._group_by_two_columns_and_sum(data,
                                                              clean_image_col_name_constants.DATE,
                                                              clean_image_col_name_constants.MACHINEID)

        # Assert
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
        mock_read_from_db.assert_called_once_with(preprocess_table_name_config.PREPROCESS_MEDIA_CATEGORY_USAGE)

        mock_aggregate_two_columns.assert_called_once_with(data,
                                                           clean_image_col_name_constants.IMAGE_WIDTH,
                                                           clean_image_col_name_constants.IMAGE_LENGTH,
                                                           aggregate_column_name_config.IMAGE_AREA,
                                                           True)

        mock_grouping.assert_called_once_with(data,
                                              preprocess_col_name_constants.DATE,
                                              preprocess_col_name_constants.MEDIA_TYPE,
                                              preprocess_col_name_constants.MACHINEID,
                                              aggregate_column_name_config.IMAGE_AREA)

        mock_df_to_db.assert_called_once_with(data, aggregate_table_name_config.AGGREGATE_MEDIA_CATEGORY_USAGE)

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

        mock_read_from_db.assert_called_once_with(preprocess_table_name_config.PREPROCESS_MEDIA_CATEGORY_USAGE)

        mock_aggregate_two_columns.assert_not_called()
        mock_grouping.assert_not_called()
        mock_df_to_db.assert_not_called()

    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._read_from_db")
    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._group_by_three_columns_and_sum_third")
    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._insert_into_db")
    def test_aggregate_sqm_per_print_mode_usage_full(self,
                                                     mock_df_to_db: MagicMock,
                                                     mock_grouping: MagicMock,
                                                     mock_read_from_db: MagicMock):
        data = pd.DataFrame(data={"ullid": [0, 1, 2, 3],
                                  preprocess_col_name_constants.MACHINEID: ["111", "222", "111", "333"],
                                  preprocess_col_name_constants.PRINT_MODE: ["2_pass", "2_pass", "4_pass",
                                                                             "4_pass"],
                                  clean_image_col_name_constants.DATE: ["04/02/2021", "04/02/2021", "05/02/2021",
                                                                        "05/02/2021"],
                                  preprocess_col_name_constants.SQUARE_DECIMETER: ["0.2", "0.32", "0.55", "0.1"]
                                  })
        mock_read_from_db.return_value = data
        mock_grouping.return_value = data

        # Act
        AggregateTasks.aggregate_sqm_per_print_mode()

        # Assert
        mock_read_from_db.assert_called_once_with(preprocess_table_name_config.PREPROCESS_SQM_PER_PRINT_MODE)

        mock_grouping.assert_called_once_with(data,
                                              preprocess_col_name_constants.DATE,
                                              preprocess_col_name_constants.MACHINEID,
                                              preprocess_col_name_constants.PRINT_MODE,
                                              preprocess_col_name_constants.SQUARE_DECIMETER)

        mock_df_to_db.assert_called_once_with(data, aggregate_table_name_config.AGGREGATE_SQM_PER_PRINT_MODE)

    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._read_from_db")
    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._group_by_three_columns_and_sum_third")
    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._insert_into_db")
    def test_aggregate_sqm_per_print_mode_no_data(self,
                                                  mock_df_to_db: MagicMock,
                                                  mock_grouping: MagicMock,
                                                  mock_read_from_db: MagicMock):
        mock_read_from_db.return_value = pd.DataFrame()

        # Act
        AggregateTasks.aggregate_sqm_per_print_mode()

        # Assert

        mock_read_from_db.assert_called_once_with(preprocess_table_name_config.PREPROCESS_SQM_PER_PRINT_MODE)

        mock_grouping.assert_not_called()
        mock_df_to_db.assert_not_called()

    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._read_from_db")
    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._group_by_two_columns_and_sum")
    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._insert_into_db")
    def test_aggregate_ink_usage_usage_full(self,
                                            mock_df_to_db: MagicMock,
                                            mock_grouping: MagicMock,
                                            mock_read_from_db: MagicMock):
        data = pd.DataFrame(data={"ullid": [0, 1, 2, 3],
                                  preprocess_col_name_constants.MACHINEID: ["111", "222", "111", "333"],
                                  preprocess_col_name_constants.PRINT_MODE: ["2_pass", "2_pass", "4_pass",
                                                                             "4_pass"],
                                  clean_image_col_name_constants.DATE: ["04/02/2021", "04/02/2021", "05/02/2021",
                                                                        "05/02/2021"],
                                  preprocess_col_name_constants.SQUARE_DECIMETER: ["0.2", "0.32", "0.55", "0.1"]
                                  })
        mock_read_from_db.return_value = data
        mock_grouping.return_value = data

        # Act
        AggregateTasks.aggregate_ink_usage()

        # Assert
        mock_read_from_db.assert_called_once_with(preprocess_table_name_config.PREPROCESS_INK_USAGE)

        mock_grouping.assert_called_once_with(data,
                                              preprocess_col_name_constants.DATE,
                                              preprocess_col_name_constants.MACHINEID)

        mock_df_to_db.assert_called_once_with(data, aggregate_table_name_config.AGGREGATE_INK_USAGE)

    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._read_from_db")
    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._group_by_two_columns_and_sum")
    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._insert_into_db")
    def test_aggregate_ink_usage_no_data(self,
                                         mock_df_to_db: MagicMock,
                                         mock_grouping: MagicMock,
                                         mock_read_from_db: MagicMock):
        mock_read_from_db.return_value = pd.DataFrame()

        # Act
        AggregateTasks.aggregate_ink_usage()

        # Assert

        mock_read_from_db.assert_called_once_with(preprocess_table_name_config.PREPROCESS_INK_USAGE)

        mock_grouping.assert_not_called()
        mock_df_to_db.assert_not_called()

    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._read_from_db")
    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._group_by_two_columns_and_sum_third")
    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._insert_into_db")
    def test_aggregate_top_ten_print_volume_full(self,
                                                 mock_df_to_db: MagicMock,
                                                 mock_grouping: MagicMock,
                                                 mock_read_from_db: MagicMock):
        data = pd.DataFrame(data={"ullid": [0, 1, 2, 3],
                                  preprocess_col_name_constants.MACHINEID: ["111", "222", "111", "333"],
                                  preprocess_col_name_constants.PRINT_MODE: ["2_pass", "2_pass", "4_pass",
                                                                             "4_pass"],
                                  clean_image_col_name_constants.DATE: ["04/02/2021", "04/02/2021", "05/02/2021",
                                                                        "05/02/2021"],
                                  preprocess_col_name_constants.SQUARE_DECIMETER: ["0.2", "0.32", "0.55", "0.1"]
                                  })
        mock_read_from_db.return_value = data
        mock_grouping.return_value = data

        # Act
        AggregateTasks.aggregate_top_ten_print_volume()

        # Assert
        mock_read_from_db.assert_called_once_with(preprocess_table_name_config.PREPROCESS_TOP_TEN_PRINT_VOLUME)

        mock_grouping.assert_called_once_with(data,
                                              preprocess_col_name_constants.DATE,
                                              preprocess_col_name_constants.MACHINEID,
                                              preprocess_col_name_constants.SQUARE_DECIMETER)

        mock_df_to_db.assert_called_once_with(data, aggregate_table_name_config.AGGREGATE_TOP_TEN_PRINT_VOLUME)

    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._read_from_db")
    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._group_by_two_columns_and_sum_third")
    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._insert_into_db")
    def test_aggregate_top_ten_print_volume_no_data(self,
                                                    mock_df_to_db: MagicMock,
                                                    mock_grouping: MagicMock,
                                                    mock_read_from_db: MagicMock):
        mock_read_from_db.return_value = pd.DataFrame()

        # Act
        AggregateTasks.aggregate_top_ten_print_volume()

        # Assert

        mock_read_from_db.assert_called_once_with(preprocess_table_name_config.PREPROCESS_TOP_TEN_PRINT_VOLUME)

        mock_grouping.assert_not_called()
        mock_df_to_db.assert_not_called()

    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._read_from_db")
    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._group_by_three_columns_and_sum_third")
    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._insert_into_db")
    def test_aggregate_media_types_per_machine_full(self,
                                                    mock_df_to_db: MagicMock,
                                                    mock_grouping: MagicMock,
                                                    mock_read_from_db: MagicMock):
        data = pd.DataFrame(data={"ullid": [0, 1, 2, 3],
                                  preprocess_col_name_constants.MACHINEID: ["111", "222", "111", "333"],
                                  preprocess_col_name_constants.PRINT_MODE: ["2_pass", "2_pass", "4_pass",
                                                                             "4_pass"],
                                  clean_image_col_name_constants.DATE: ["04/02/2021", "04/02/2021", "05/02/2021",
                                                                        "05/02/2021"],
                                  preprocess_col_name_constants.SQUARE_DECIMETER: ["0.2", "0.32", "0.55", "0.1"]
                                  })
        mock_read_from_db.return_value = data
        mock_grouping.return_value = data

        # Act
        AggregateTasks.aggregate_media_types_per_machine()

        # Assert
        mock_read_from_db.assert_called_once_with(preprocess_table_name_config.PREPROCESS_MEDIA_TYPES_PER_MACHINE)

        mock_grouping.assert_called_once_with(data,
                                              preprocess_col_name_constants.DATE,
                                              preprocess_col_name_constants.MACHINEID,
                                              preprocess_col_name_constants.MEDIA_TYPE_DISPLAY_NAME,
                                              preprocess_col_name_constants.SQUARE_DECIMETER)

        mock_df_to_db.assert_called_once_with(data, aggregate_table_name_config.AGGREGATE_MEDIA_TYPES_PER_MACHINE)

    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._read_from_db")
    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._group_by_three_columns_and_sum_third")
    @patch("tasks.aggregate.aggregate_tasks.AggregateTasks._insert_into_db")
    def test_aggregate_media_types_per_machine_no_data(self,
                                                       mock_df_to_db: MagicMock,
                                                       mock_grouping: MagicMock,
                                                       mock_read_from_db: MagicMock):
        mock_read_from_db.return_value = pd.DataFrame()

        # Act
        AggregateTasks.aggregate_media_types_per_machine()

        # Assert

        mock_read_from_db.assert_called_once_with(preprocess_table_name_config.PREPROCESS_MEDIA_TYPES_PER_MACHINE)

        mock_grouping.assert_not_called()
        mock_df_to_db.assert_not_called()
