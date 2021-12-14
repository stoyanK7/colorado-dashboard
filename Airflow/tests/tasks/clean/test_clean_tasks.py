import unittest
import pandas as pd
from tabulate import tabulate
import os
import subprocess

from airflow.models import Connection
from airflow.settings import Session
from cryptography.fernet import Fernet
from sqlalchemy import create_engine, text

from tasks.clean.clean_tasks import CleanTasks
from config import read_image_column_name_config
from config import read_table_name_config, clean_table_name_config, \
    clean_image_col_name_constants, clean_media_prepare_col_name_constants, clean_print_cycle_col_name_constants, \
    clean_image_data_types, clean_media_prepare_data_types, clean_print_cycle_data_types


class PostgresDatabaseManagerTests(unittest.TestCase):

    def test_remove_duplicates_image(self):
        actual = pd.DataFrame({clean_image_col_name_constants.ULLID: [1, 2, 2, 3, 4, 5], clean_image_col_name_constants.MEDIA_TYPE: ['a', 'b', 'b', 'c', 'd', 'e']})
        expected = pd.DataFrame({clean_image_col_name_constants.ULLID: [1, 2, 3, 4, 5], clean_image_col_name_constants.MEDIA_TYPE: ['a', 'b', 'c', 'd', 'e']})
        obj = CleanTasks()

        actual = obj.remove_duplicates(actual)
        print(actual)
        print('------')
        print(expected)

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))


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

    def test_remove_invalid_media_type(self):
        actual = pd.DataFrame({clean_image_col_name_constants.ULLID: [1, 2],
                               clean_image_col_name_constants.ACCOUNTED_INK_BLACK: [1, 2],
                               clean_image_col_name_constants.ACCOUNTED_INK_CYAN: [1, 2],
                               clean_image_col_name_constants.ACCOUNTED_INK_MAGENTA: [1, 2],
                               clean_image_col_name_constants.ACCOUNTED_INK_YELLOW: [1, 2],
                               clean_image_col_name_constants.DATE: ['21/03/2001', '21/03/2001'],
                               clean_image_col_name_constants.IMAGE_LENGTH: [1, 2],
                               clean_image_col_name_constants.IMAGE_WIDTH: [1, 2],
                               clean_image_col_name_constants.MEDIA_TYPE: ['Film', 'film1']})
        expected = pd.DataFrame({clean_image_col_name_constants.ULLID: [1],
                                 clean_image_col_name_constants.ACCOUNTED_INK_BLACK: [1],
                                 clean_image_col_name_constants.ACCOUNTED_INK_CYAN: [1],
                                 clean_image_col_name_constants.ACCOUNTED_INK_MAGENTA: [1],
                                 clean_image_col_name_constants.ACCOUNTED_INK_YELLOW: [1],
                                 clean_image_col_name_constants.DATE: ['21/03/2001'],
                                 clean_image_col_name_constants.IMAGE_LENGTH: [1],
                                 clean_image_col_name_constants.IMAGE_WIDTH: [1],
                                 clean_image_col_name_constants.MEDIA_TYPE: ['Film']})
        obj = CleanTasks()

        actual = obj.remove_invalid_media_type(actual)
        print(tabulate(actual, headers='keys', tablefmt='psql'))
        print('------')
        print(tabulate(expected, headers='keys', tablefmt='psql'))

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))


    def test_make_data_frame_image(self):
        actual = pd.DataFrame({read_column_name_config.ULLID: [1],
                               read_image_column_name_config.ACCOUNTED_INK_BLACK: [1],
                               read_image_column_name_config.ACCOUNTED_INK_CYAN: [1],
                               read_column_name_config.ACCOUNTED_INK_MAGENTA: [1],
                               read_image_column_name_config.ACCOUNTED_INK_YELLOW: [1],
                               read_column_name_config.DATE: ['21/03/2001'],
                               read_column_name_config.IMAGE_LENGTH: [1],
                               read_image_column_name_config.IMAGE_WIDTH: [1],
                               read_column_name_config.MEDIA_TYPE: ['Film1'],
                               read_image_column_name_config.DATE: ['21/03/2001'],
                               read_column_name_config.IMAGE_LENGTH: [1],
                               read_column_name_config.IMAGE_WIDTH: [1],
                               read_column_name_config.MEDIA_TYPE: ['Film1'],
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
        cols = [getattr(clean_image_col_name_constants, name)
                for name in dir(clean_image_col_name_constants) if not name.startswith('_')]
        actual = obj.make_data_frame(actual, cols)
        print(tabulate(actual, headers='keys', tablefmt='psql'))
        print('------')
        print(tabulate(expected, headers='keys', tablefmt='psql'))

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))

    def test_list_from_config(self):
        config = clean_image_col_name_constants
        cols = [getattr(clean_image_col_name_constants, name)
                for name in dir(clean_image_col_name_constants) if not name.startswith('_')]

        print(cols[-1])


