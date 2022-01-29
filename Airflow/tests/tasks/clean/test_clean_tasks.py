import unittest
import pandas as pd
from tabulate import tabulate


from tasks.clean.clean_tasks import CleanTasks
from config import clean_image_col_name_constants, clean_image_data_types, clean_media_prepare_col_name_constants, \
    clean_print_cycle_col_name_constants


class PostgresDatabaseManagerTests(unittest.TestCase):

    def test_make_data_frame_image(self):
        actual = pd.DataFrame({clean_image_col_name_constants.ULLID: [1],
                               clean_image_col_name_constants.ACCOUNTED_INK_BLACK: [1],
                               clean_image_col_name_constants.ACCOUNTED_INK_CYAN: [1],
                               clean_image_col_name_constants.ACCOUNTED_INK_MAGENTA: [1],
                               clean_image_col_name_constants.ACCOUNTED_INK_YELLOW: [1],
                               clean_image_col_name_constants.DATE: ['21/03/2001'],
                               clean_image_col_name_constants.IMAGE_LENGTH: [1],
                               clean_image_col_name_constants.IMAGE_WIDTH: [1],
                               clean_image_col_name_constants.MEDIA_TYPE: ['Film1'],
                               clean_image_col_name_constants.DATE: ['21/03/2001'],
                               clean_image_col_name_constants.IMAGE_LENGTH: [1],
                               clean_image_col_name_constants.IMAGE_WIDTH: [1],
                               clean_image_col_name_constants.MEDIA_TYPE: ['Film1'],
                               })
        expected = pd.DataFrame({clean_image_col_name_constants.ULLID: [1],
                                 clean_image_col_name_constants.ACCOUNTED_INK_BLACK: [1],
                                 clean_image_col_name_constants.ACCOUNTED_INK_CYAN: [1],
                                 clean_image_col_name_constants.ACCOUNTED_INK_MAGENTA: [1],
                                 clean_image_col_name_constants.ACCOUNTED_INK_YELLOW: [1],
                                 clean_image_col_name_constants.DATE: ['21/03/2001'],
                                 clean_image_col_name_constants.IMAGE_LENGTH: [1],
                                 clean_image_col_name_constants.IMAGE_WIDTH: [1],
                                 clean_image_col_name_constants.MEDIA_TYPE: ['Film1']})
        obj = CleanTasks()
        actual = obj.make_data_frame(actual, clean_image_col_name_constants)
        print(tabulate(actual, headers='keys', tablefmt='psql'))
        print('------')
        print(tabulate(expected, headers='keys', tablefmt='psql'))

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))

    def test_remove_duplicates_image(self):
        actual = pd.DataFrame({clean_image_col_name_constants.ULLID: [1, 2, 2, 3, 4, 5],
                               clean_image_col_name_constants.MEDIA_TYPE: ['a', 'b', 'b', 'c', 'd', 'e']})
        expected = pd.DataFrame({clean_image_col_name_constants.ULLID: [1, 2, 3, 4, 5],
                                 clean_image_col_name_constants.MEDIA_TYPE: ['a', 'b', 'c', 'd', 'e']})
        obj = CleanTasks()

        actual = obj.remove_duplicates(actual)
        print(actual)
        print('------')
        print(expected)

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))

    def test_remove_null(self):
        actual = pd.DataFrame({clean_image_col_name_constants.ULLID: [1, -2],
                               clean_image_col_name_constants.ACCOUNTED_INK_BLACK: [1, 2],
                               clean_image_col_name_constants.ACCOUNTED_INK_CYAN: [1, 2],
                               clean_image_col_name_constants.ACCOUNTED_INK_MAGENTA: [1, 2],
                               clean_image_col_name_constants.ACCOUNTED_INK_YELLOW: [1, 2],
                               clean_image_col_name_constants.DATE: ['a', 'b'],
                               clean_image_col_name_constants.IMAGE_LENGTH: [1, 2],
                               clean_image_col_name_constants.IMAGE_WIDTH: [1, 2],
                               clean_image_col_name_constants.MEDIA_TYPE: ['a', '']})
        expected = pd.DataFrame({clean_image_col_name_constants.ULLID: [1],
                                 clean_image_col_name_constants.ACCOUNTED_INK_BLACK: [1],
                                 clean_image_col_name_constants.ACCOUNTED_INK_CYAN: [1],
                                 clean_image_col_name_constants.ACCOUNTED_INK_MAGENTA: [1],
                                 clean_image_col_name_constants.ACCOUNTED_INK_YELLOW: [1],
                                 clean_image_col_name_constants.DATE: ['a'],
                                 clean_image_col_name_constants.IMAGE_LENGTH: [1],
                                 clean_image_col_name_constants.IMAGE_WIDTH: [1],
                                 clean_image_col_name_constants.MEDIA_TYPE: ['a']})
        obj = CleanTasks()

        actual = obj.remove_row_null(actual)
        print(tabulate(actual, headers='keys', tablefmt='psql'))
        print('------')
        print(tabulate(expected, headers='keys', tablefmt='psql'))

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))

    def test_check_data_type(self):
        actual = pd.DataFrame({clean_image_col_name_constants.ULLID: [1, 'a'],
                               clean_image_col_name_constants.ACCOUNTED_INK_BLACK: [1, 2],
                               clean_image_col_name_constants.ACCOUNTED_INK_CYAN: [1, 2],
                               clean_image_col_name_constants.ACCOUNTED_INK_MAGENTA: [1, 2],
                               clean_image_col_name_constants.ACCOUNTED_INK_YELLOW: [1, 2],
                               clean_image_col_name_constants.DATE: ['21/03/2001', 'b'],
                               clean_image_col_name_constants.IMAGE_LENGTH: [1, 2],
                               clean_image_col_name_constants.IMAGE_WIDTH: [1, 2],
                               clean_image_col_name_constants.MEDIA_TYPE: ['Film', 1]})

        expected = pd.DataFrame({clean_image_col_name_constants.ULLID: [1, float("NaN")],
                                 clean_image_col_name_constants.ACCOUNTED_INK_BLACK: [1, 2],
                                 clean_image_col_name_constants.ACCOUNTED_INK_CYAN: [1, 2],
                                 clean_image_col_name_constants.ACCOUNTED_INK_MAGENTA: [1, 2],
                                 clean_image_col_name_constants.ACCOUNTED_INK_YELLOW: [1, 2],
                                 clean_image_col_name_constants.DATE: ['2001-03-21', float("NaN")],
                                 clean_image_col_name_constants.IMAGE_LENGTH: [1, 2],
                                 clean_image_col_name_constants.IMAGE_WIDTH: [1, 2],
                                 clean_image_col_name_constants.MEDIA_TYPE: ['Film', float("NaN")]})

        obj = CleanTasks()

        actual = obj.check_data_type(actual, clean_image_data_types.data_types)

        print(tabulate(actual, headers='keys', tablefmt='psql'))
        print('------')
        print(tabulate(expected, headers='keys', tablefmt='psql'))

        pd.testing.assert_frame_equal(actual, expected)

    def test_check_negative_values(self):
        actual = pd.DataFrame({clean_image_col_name_constants.ULLID: [1, -2, 3, 4, 5, 6, 7, 8],
                               clean_image_col_name_constants.ACCOUNTED_INK_BLACK: [1, 2, -3, 4, 5, 6, 7, 8],
                               clean_image_col_name_constants.ACCOUNTED_INK_CYAN: [1, 2, 3, -4, 5, 6, 7, 8],
                               clean_image_col_name_constants.ACCOUNTED_INK_MAGENTA: [1, 2, 3, 4, -5, 6, 7, 8],
                               clean_image_col_name_constants.ACCOUNTED_INK_YELLOW: [1, 2, 3, 4, 5, -6, 7, 8],
                               clean_image_col_name_constants.DATE: ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'],
                               clean_image_col_name_constants.IMAGE_LENGTH: [1, 2, 3, 4, 5, 6, -7, 8],
                               clean_image_col_name_constants.IMAGE_WIDTH: [1, 2, 3, 4, 5, 6, 7, -8],
                               clean_image_col_name_constants.MEDIA_TYPE: ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h']})

        expected = pd.DataFrame({clean_image_col_name_constants.ULLID: [1],
                                 clean_image_col_name_constants.ACCOUNTED_INK_BLACK: [1],
                                 clean_image_col_name_constants.ACCOUNTED_INK_CYAN: [1],
                                 clean_image_col_name_constants.ACCOUNTED_INK_MAGENTA: [1],
                                 clean_image_col_name_constants.ACCOUNTED_INK_YELLOW: [1],
                                 clean_image_col_name_constants.DATE: ['a'],
                                 clean_image_col_name_constants.IMAGE_LENGTH: [1],
                                 clean_image_col_name_constants.IMAGE_WIDTH: [1],
                                 clean_image_col_name_constants.MEDIA_TYPE: ['a']})
        obj = CleanTasks()

        actual = obj.check_negative_values(actual, clean_image_data_types.data_types)
        print(actual)

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))

    def test_remove_invalid_units_image(self):
        actual = pd.DataFrame({clean_image_col_name_constants.ACCOUNTED_INK_BLACK_UNIT: ['ml', 'nl', 'dm3', 'dm3'],
                               clean_image_col_name_constants.ACCOUNTED_INK_CYAN_UNIT: ['ml', 'nl', 'dm3', 'dm3'],
                               clean_image_col_name_constants.ACCOUNTED_INK_MAGENTA_UNIT: ['ml', 'nl', 'dm3', 'dm3'],
                               clean_image_col_name_constants.ACCOUNTED_INK_YELLOW_UNIT: ['ml', 'nl', 'dm3', 'dm3'],
                               clean_image_col_name_constants.IMAGE_LENGTH_UNIT: ['mm', 'mm', 'ml', 'cm'],
                               clean_image_col_name_constants.IMAGE_WIDTH_UNIT: ['cm', 'cm', 'ml', 'cm'],
                               clean_image_col_name_constants.LOCAL_TIME_UNIT: ['us', 'us', 'us', 'oe']})
        expected = pd.DataFrame({clean_image_col_name_constants.ACCOUNTED_INK_BLACK_UNIT: ['ml'],
                                 clean_image_col_name_constants.ACCOUNTED_INK_CYAN_UNIT: ['ml'],
                                 clean_image_col_name_constants.ACCOUNTED_INK_MAGENTA_UNIT: ['ml'],
                                 clean_image_col_name_constants.ACCOUNTED_INK_YELLOW_UNIT: ['ml'],
                                 clean_image_col_name_constants.IMAGE_LENGTH_UNIT: ['mm'],
                                 clean_image_col_name_constants.IMAGE_WIDTH_UNIT: ['cm'],
                                 clean_image_col_name_constants.LOCAL_TIME_UNIT: ['us']})
        obj = CleanTasks()
        actual = obj.remove_invalid_units_image(actual)
        print(tabulate(actual, headers='keys', tablefmt='psql'))
        print('------')
        print(tabulate(expected, headers='keys', tablefmt='psql'))

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))

    def test_remove_invalid_units_media_prepare(self):
        actual = pd.DataFrame({clean_media_prepare_col_name_constants.LOCAL_TIME_UNIT: ['utc', 'oe', 'us']})
        expected = pd.DataFrame({clean_media_prepare_col_name_constants.LOCAL_TIME_UNIT: ['utc', 'us']})
        obj = CleanTasks()
        actual = obj.remove_invalid_units_media_prepare(actual)
        print(tabulate(actual, headers='keys', tablefmt='psql'))
        print('------')
        print(tabulate(expected, headers='keys', tablefmt='psql'))

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))

    def test_remove_invalid_units_print_cycle(self):
        actual = pd.DataFrame({clean_print_cycle_col_name_constants.SQUARE_DECIMETER_UNIT: ['mm2', 'cm2', 'm2', 'ht2'],
                               clean_print_cycle_col_name_constants.LOCAL_TIME_UNIT: ['utc', 'oe', 'us', 'us']})
        expected = pd.DataFrame({clean_print_cycle_col_name_constants.SQUARE_DECIMETER_UNIT: ['mm2', 'm2'],
                                 clean_print_cycle_col_name_constants.LOCAL_TIME_UNIT: ['utc', 'us']})
        obj = CleanTasks()
        actual = obj.remove_invalid_units_print_cycle(actual)
        print(tabulate(actual, headers='keys', tablefmt='psql'))
        print('------')
        print(tabulate(expected, headers='keys', tablefmt='psql'))

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))

    def test_list_from_config(self):
        cols = [getattr(clean_image_col_name_constants, name)
                for name in dir(clean_image_col_name_constants) if not name.startswith('_')]
        print(cols[-1])
