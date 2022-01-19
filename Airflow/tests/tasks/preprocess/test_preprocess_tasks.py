import unittest

from unittest.mock import patch
from unittest.mock import MagicMock

from tasks.preprocess.preprocess_tasks import PreprocessTasks
import pandas as pd
from config import clean_table_name_config, clean_image_col_name_constants, aggregate_column_name_config, \
    clean_media_prepare_col_name_constants, clean_print_cycle_col_name_constants


class TestPreprocessTasks(unittest.TestCase):

    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._read_from_db")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._converting_units_to_default_values")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._convert_date_to_utc")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._insert_into_db")
    def test_preprocess_media_category_usage_no_data(self,
                                                     mock_df_to_db: MagicMock,
                                                     mock_convert_date_to_utc: MagicMock,
                                                     mock_units_to_default_values: MagicMock,
                                                     mock_read_from_db: MagicMock):
        mock_read_from_db.return_value = pd.DataFrame()

        # Act
        PreprocessTasks.preprocess_media_category_usage()

        # Assert

        mock_read_from_db.assert_called_once_with(clean_table_name_config.READ_IMAGE)

        mock_units_to_default_values.assert_not_called()
        mock_convert_date_to_utc.assert_not_called()
        mock_df_to_db.assert_not_called()

    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._read_from_db")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._converting_units_to_default_values")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._convert_date_to_utc")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._insert_into_db")
    def test_preprocess_media_category_usage_full(self,
                                                  mock_df_to_db: MagicMock,
                                                  mock_convert_date_to_utc: MagicMock,
                                                  mock_units_to_default_values: MagicMock,
                                                  mock_read_from_db: MagicMock):
        data = pd.DataFrame(data={"ullid": [0, 1, 2, 3],
                                  clean_image_col_name_constants.MACHINEID: ["166116", "166432", "166152",
                                                                             "166124"],
                                  clean_image_col_name_constants.DATE: ["04/02/2021", "04/02/2021", "05/02/2021",
                                                                        "05/02/2021"],
                                  clean_image_col_name_constants.MEDIA_TYPE: ["Paper", "Board", "Board", "Paper"]
                                  })

        mock_read_from_db.return_value = data
        mock_units_to_default_values.return_value = data
        mock_convert_date_to_utc.return_value = data
        columns_to_drop = [clean_image_col_name_constants.LOCAL_TIME,
                           clean_image_col_name_constants.ACCOUNTED_INK_BLACK,
                           clean_image_col_name_constants.ACCOUNTED_INK_YELLOW,
                           clean_image_col_name_constants.ACCOUNTED_INK_CYAN,
                           clean_image_col_name_constants.ACCOUNTED_INK_MAGENTA]

        # Act
        PreprocessTasks.preprocess_media_category_usage()

        # Assert
        mock_read_from_db.assert_called_once_with(clean_table_name_config.READ_IMAGE)

        mock_units_to_default_values.assert_called_once_with(data, columns_to_drop)

        mock_convert_date_to_utc.assert_called_once_with(data)

        mock_df_to_db.assert_called_once()

    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._read_from_db")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._converting_units_to_default_values")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._convert_date_to_utc")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._insert_into_db")
    def test_preprocess_sqm_per_print_mode_no_data(self,
                                                   mock_df_to_db: MagicMock,
                                                   mock_convert_date_to_utc: MagicMock,
                                                   mock_units_to_default_values: MagicMock,
                                                   mock_read_from_db: MagicMock):
        mock_read_from_db.return_value = pd.DataFrame()

        # Act
        PreprocessTasks.preprocess_sqm_per_print_mode()

        # Assert

        mock_read_from_db.assert_called_once_with(clean_table_name_config.READ_PRINT_CYCLE)

        mock_units_to_default_values.assert_not_called()
        mock_convert_date_to_utc.assert_not_called()
        mock_df_to_db.assert_not_called()

    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._read_from_db")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._converting_units_to_default_values")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._convert_date_to_utc")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._insert_into_db")
    def test_preprocess_sqm_per_print_mode_full(self,
                                                mock_df_to_db: MagicMock,
                                                mock_convert_date_to_utc: MagicMock,
                                                mock_units_to_default_values: MagicMock,
                                                mock_read_from_db: MagicMock):
        data = pd.DataFrame(data={"ullid": [0, 1, 2, 3],
                                  clean_image_col_name_constants.MACHINEID: ["166116", "166432", "166152",
                                                                             "166124"],
                                  clean_image_col_name_constants.DATE: ["04/02/2021", "04/02/2021", "05/02/2021",
                                                                        "05/02/2021"],
                                  clean_image_col_name_constants.MEDIA_TYPE: ["Paper", "Board", "Board", "Paper"]
                                  })

        mock_read_from_db.return_value = data
        mock_units_to_default_values.return_value = data
        mock_convert_date_to_utc.return_value = data
        columns_to_drop = [clean_print_cycle_col_name_constants.ENGINE_CYCLE_ID,
                           clean_image_col_name_constants.LOCAL_TIME]

        # Act
        PreprocessTasks.preprocess_sqm_per_print_mode()

        # Assert
        mock_read_from_db.assert_called_once_with(clean_table_name_config.READ_PRINT_CYCLE)

        mock_units_to_default_values.assert_called_once_with(data, columns_to_drop)

        mock_convert_date_to_utc.assert_called_once_with(data)

        mock_df_to_db.assert_called_once()

    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._read_from_db")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._converting_units_to_default_values")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._convert_date_to_utc")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._insert_into_db")
    def test_preprocess_ink_usage_no_data(self,
                                          mock_df_to_db: MagicMock,
                                          mock_convert_date_to_utc: MagicMock,
                                          mock_units_to_default_values: MagicMock,
                                          mock_read_from_db: MagicMock):
        mock_read_from_db.return_value = pd.DataFrame()

        # Act
        PreprocessTasks.preprocess_ink_usage()

        # Assert

        mock_read_from_db.assert_called_once_with(clean_table_name_config.READ_IMAGE)

        mock_units_to_default_values.assert_not_called()
        mock_convert_date_to_utc.assert_not_called()
        mock_df_to_db.assert_not_called()

    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._read_from_db")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._converting_units_to_default_values")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._convert_date_to_utc")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._insert_into_db")
    def test_preprocess_ink_usage_full(self,
                                       mock_df_to_db: MagicMock,
                                       mock_convert_date_to_utc: MagicMock,
                                       mock_units_to_default_values: MagicMock,
                                       mock_read_from_db: MagicMock):
        data = pd.DataFrame(data={"ullid": [0, 1, 2, 3],
                                  clean_image_col_name_constants.MACHINEID: ["166116", "166432", "166152",
                                                                             "166124"],
                                  clean_image_col_name_constants.DATE: ["04/02/2021", "04/02/2021", "05/02/2021",
                                                                        "05/02/2021"],
                                  clean_image_col_name_constants.MEDIA_TYPE: ["Paper", "Board", "Board", "Paper"]
                                  })

        mock_read_from_db.return_value = data
        mock_units_to_default_values.return_value = data
        mock_convert_date_to_utc.return_value = data
        columns_to_drop = [clean_image_col_name_constants.IMAGE_LENGTH, clean_image_col_name_constants.IMAGE_WIDTH,
                           clean_image_col_name_constants.MEDIA_TYPE, clean_image_col_name_constants.LOCAL_TIME]

        # Act
        PreprocessTasks.preprocess_ink_usage()

        # Assert
        mock_read_from_db.assert_called_once_with(clean_table_name_config.READ_IMAGE)

        mock_units_to_default_values.assert_called_once_with(data, columns_to_drop)

        mock_convert_date_to_utc.assert_called_once_with(data)

        mock_df_to_db.assert_called_once()

    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._read_from_db")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._merge_two_dataframes")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._converting_units_to_default_values")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._convert_date_to_utc")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._insert_into_db")
    def test_preprocess_media_types_per_machine_no_data(self,
                                                        mock_df_to_db: MagicMock,
                                                        mock_convert_date_to_utc: MagicMock,
                                                        mock_units_to_default_values: MagicMock,
                                                        mock_merge_two_dataframes: MagicMock,
                                                        mock_read_from_db: MagicMock):
        mock_read_from_db.return_value = pd.DataFrame()

        # Act
        PreprocessTasks.preprocess_media_types_per_machine()

        # Assert

        mock_read_from_db.assert_any_call(clean_table_name_config.READ_MEDIA_PREPARE)
        mock_read_from_db.assert_any_call(clean_table_name_config.READ_PRINT_CYCLE)

        mock_merge_two_dataframes.assert_not_called()
        mock_units_to_default_values.assert_not_called()
        mock_convert_date_to_utc.assert_not_called()
        mock_df_to_db.assert_not_called()

    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._read_from_db")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._merge_two_dataframes")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._converting_units_to_default_values")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._convert_date_to_utc")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._insert_into_db")
    def test_preprocess_media_types_per_machine_full(self,
                                                     mock_df_to_db: MagicMock,
                                                     mock_convert_date_to_utc: MagicMock,
                                                     mock_units_to_default_values: MagicMock,
                                                     mock_merge_two_dataframes: MagicMock,
                                                     mock_read_from_db: MagicMock):
        data = pd.DataFrame(data={"ullid": [0, 1, 2, 3],
                                  clean_print_cycle_col_name_constants.DATE + "_x": ["166116", "166432", "166152",
                                                                                     "166124"],
                                  clean_media_prepare_col_name_constants.MEDIA_TYPE_DISPLAY_NAME: ["04/02/2021",
                                                                                                   "04/02/2021",
                                                                                                   "05/02/2021",
                                                                                                   "05/02/2021"],
                                  clean_print_cycle_col_name_constants.SQUARE_DECIMETER: ["166116", "166432", "166152",
                                                                                          "166124"],
                                  clean_print_cycle_col_name_constants.SQUARE_DECIMETER_UNIT: ["166116", "166432",
                                                                                               "166152",
                                                                                               "166124"],
                                  clean_print_cycle_col_name_constants.MACHINEID + "_x": ["166116", "166432", "166152",
                                                                                          "166124"]
                                  })

        mock_read_from_db.return_value = data
        mock_units_to_default_values.return_value = data
        mock_convert_date_to_utc.return_value = data
        mock_merge_two_dataframes.return_value = data

        # Act
        PreprocessTasks.preprocess_media_types_per_machine()

        # Assert
        mock_read_from_db.assert_any_call(clean_table_name_config.READ_MEDIA_PREPARE)
        mock_read_from_db.assert_any_call(clean_table_name_config.READ_PRINT_CYCLE)

        mock_merge_two_dataframes.assert_called_once_with(data, data,
                                                          clean_print_cycle_col_name_constants.ENGINE_CYCLE_ID)

        mock_units_to_default_values.assert_called_once()

        mock_convert_date_to_utc.assert_called_once()

        mock_df_to_db.assert_called_once()

    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._read_from_db")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._converting_units_to_default_values")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._convert_date_to_utc")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._insert_into_db")
    def test_preprocess_top_ten_print_volume_no_data(self,
                                                     mock_df_to_db: MagicMock,
                                                     mock_convert_date_to_utc: MagicMock,
                                                     mock_units_to_default_values: MagicMock,
                                                     mock_read_from_db: MagicMock):
        mock_read_from_db.return_value = pd.DataFrame()

        # Act
        PreprocessTasks.preprocess_top_ten_print_volume()

        # Assert

        mock_read_from_db.assert_called_once_with(clean_table_name_config.READ_PRINT_CYCLE)

        mock_units_to_default_values.assert_not_called()
        mock_convert_date_to_utc.assert_not_called()
        mock_df_to_db.assert_not_called()

    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._read_from_db")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._converting_units_to_default_values")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._convert_date_to_utc")
    @patch("tasks.preprocess.preprocess_tasks.PreprocessTasks._insert_into_db")
    def test_preprocess_top_ten_print_volume_full(self,
                                                  mock_df_to_db: MagicMock,
                                                  mock_convert_date_to_utc: MagicMock,
                                                  mock_units_to_default_values: MagicMock,
                                                  mock_read_from_db: MagicMock):
        data = pd.DataFrame(data={"ullid": [0, 1, 2, 3],
                                  clean_image_col_name_constants.MACHINEID: ["166116", "166432", "166152",
                                                                             "166124"],
                                  clean_image_col_name_constants.DATE: ["04/02/2021", "04/02/2021", "05/02/2021",
                                                                        "05/02/2021"],
                                  clean_image_col_name_constants.MEDIA_TYPE: ["Paper", "Board", "Board", "Paper"]
                                  })

        mock_read_from_db.return_value = data
        mock_units_to_default_values.return_value = data
        mock_convert_date_to_utc.return_value = data
        columns_to_drop = [clean_print_cycle_col_name_constants.LOCAL_TIME,
                           clean_print_cycle_col_name_constants.ENGINE_CYCLE_ID,
                           clean_print_cycle_col_name_constants.PRINT_MODE]

        # Act
        PreprocessTasks.preprocess_top_ten_print_volume()

        # Assert
        mock_read_from_db.assert_called_once_with(clean_table_name_config.READ_PRINT_CYCLE)

        mock_units_to_default_values.assert_called_once_with(data, columns_to_drop)

        mock_convert_date_to_utc.assert_called_once_with(data)

        mock_df_to_db.assert_called_once()

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
                                      "date": ["2022-01-16"]})

        actual = PreprocessTasks._convert_date_to_utc(df)

        pd.testing.assert_frame_equal(actual, expected)

    def test_rename_print_modes(self):
        print_mode = clean_print_cycle_col_name_constants.PRINT_MODE
        data = pd.DataFrame(data={print_mode: ["1_pass",
                                               "1_pass_highDensity",
                                               "2_pass",
                                               "4_pass",
                                               "8_pass",
                                               "8_pass_highDensity",
                                               "16_pass",
                                               "9_pass"]})
        expected = pd.DataFrame(data={print_mode: ["Max speed",
                                                   "High speed",
                                                   "Production",
                                                   "High Quality",
                                                   "Specialty",
                                                   "Backlit",
                                                   "Reliance",
                                                   "Other"]})

        actual = PreprocessTasks._rename_print_modes(data)

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

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))
