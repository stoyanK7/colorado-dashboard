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
from config import cleaning_column_name_config


class PostgresDatabaseManagerTests(unittest.TestCase):

    def test_remove_duplicates_image(self):
        actual = pd.DataFrame({cleaning_column_name_config.ULLID: [1, 2, 2, 3, 4, 5], cleaning_column_name_config.MEDIA_TYPE: ['a', 'b', 'b', 'c', 'd', 'e']})
        expected = pd.DataFrame({cleaning_column_name_config.ULLID: [1, 2, 3, 4, 5], cleaning_column_name_config.MEDIA_TYPE: ['a', 'b', 'c', 'd', 'e']})
        obj = CleanTasks()

        actual = obj.remove_duplicates(actual)
        print(actual)
        print('------')
        print(expected)

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))


    def test_check_negative_image(self):
        actual = pd.DataFrame({cleaning_column_name_config.ULLID: [1, -2, 3, 4, 5, 6, 7, 8],
                               cleaning_column_name_config.ACCOUNTED_INK_BLACK: [1, 2, -3, 4, 5, 6, 7, 8],
                               cleaning_column_name_config.ACCOUNTED_INK_CYAN: [1, 2, 3, -4, 5, 6, 7, 8],
                               cleaning_column_name_config.ACCOUNTED_INK_MAGENTA: [1, 2, 3, 4, -5, 6, 7, 8],
                               cleaning_column_name_config.ACCOUNTED_INK_YELLOW: [1, 2, 3, 4, 5, -6, 7, 8],
                               cleaning_column_name_config.DATE: ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'],
                               cleaning_column_name_config.IMAGE_LENGTH: [1, 2, 3, 4, 5, 6, -7, 8],
                               cleaning_column_name_config.IMAGE_WIDTH: [1, 2, 3, 4, 5, 6, 7, -8],
                               cleaning_column_name_config.MEDIA_TYPE: ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h']})

        expected = pd.DataFrame({cleaning_column_name_config.ULLID: [1],
                                 cleaning_column_name_config.ACCOUNTED_INK_BLACK: [1],
                                 cleaning_column_name_config.ACCOUNTED_INK_CYAN: [1],
                                 cleaning_column_name_config.ACCOUNTED_INK_MAGENTA: [1],
                                 cleaning_column_name_config.ACCOUNTED_INK_YELLOW: [1],
                                 cleaning_column_name_config.DATE: ['a'],
                                 cleaning_column_name_config.IMAGE_LENGTH: [1],
                                 cleaning_column_name_config.IMAGE_WIDTH: [1],
                                 cleaning_column_name_config.MEDIA_TYPE: ['a']})
        obj = CleanTasks()

        actual = obj.check_negative_image(actual)
        print(actual)

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))


    def test_remove_null(self):
        actual = pd.DataFrame({cleaning_column_name_config.ULLID: [1, -2],
                               cleaning_column_name_config.ACCOUNTED_INK_BLACK: [1, 2],
                               cleaning_column_name_config.ACCOUNTED_INK_CYAN: [1, 2],
                               cleaning_column_name_config.ACCOUNTED_INK_MAGENTA: [1, 2],
                               cleaning_column_name_config.ACCOUNTED_INK_YELLOW: [1, 2],
                               cleaning_column_name_config.DATE: ['a', 'b'],
                               cleaning_column_name_config.IMAGE_LENGTH: [1, 2],
                               cleaning_column_name_config.IMAGE_WIDTH: [1, 2],
                               cleaning_column_name_config.MEDIA_TYPE: ['a', '']})
        expected = pd.DataFrame({cleaning_column_name_config.ULLID: [1],
                                 cleaning_column_name_config.ACCOUNTED_INK_BLACK: [1],
                                 cleaning_column_name_config.ACCOUNTED_INK_CYAN: [1],
                                 cleaning_column_name_config.ACCOUNTED_INK_MAGENTA: [1],
                                 cleaning_column_name_config.ACCOUNTED_INK_YELLOW: [1],
                                 cleaning_column_name_config.DATE: ['a'],
                                 cleaning_column_name_config.IMAGE_LENGTH: [1],
                                 cleaning_column_name_config.IMAGE_WIDTH: [1],
                                 cleaning_column_name_config.MEDIA_TYPE: ['a']})
        obj = CleanTasks()

        actual = obj.remove_row_null(actual)
        print(tabulate(actual, headers='keys', tablefmt='psql'))
        print('------')
        print(tabulate(expected, headers='keys', tablefmt='psql'))

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))

    def test_check_type_image(self):
        actual = pd.DataFrame({cleaning_column_name_config.ULLID: [1, 'a'],
                               cleaning_column_name_config.ACCOUNTED_INK_BLACK: [1, 2],
                               cleaning_column_name_config.ACCOUNTED_INK_CYAN: [1, 2],
                               cleaning_column_name_config.ACCOUNTED_INK_MAGENTA: [1, 2],
                               cleaning_column_name_config.ACCOUNTED_INK_YELLOW: [1, 2],
                               cleaning_column_name_config.DATE: ['21/03/2001', 'b'],
                               cleaning_column_name_config.IMAGE_LENGTH: [1, 2],
                               cleaning_column_name_config.IMAGE_WIDTH: [1, 2],
                               cleaning_column_name_config.MEDIA_TYPE: ['Film', 1]})


        expected = pd.DataFrame({cleaning_column_name_config.ULLID: [1, float("NaN")],
                                 cleaning_column_name_config.ACCOUNTED_INK_BLACK: [1, 2],
                                 cleaning_column_name_config.ACCOUNTED_INK_CYAN: [1, 2],
                                 cleaning_column_name_config.ACCOUNTED_INK_MAGENTA: [1, 2],
                                 cleaning_column_name_config.ACCOUNTED_INK_YELLOW: [1, 13840000],
                                 cleaning_column_name_config.DATE: ['21/03/2001', float("NaN")],
                                 cleaning_column_name_config.IMAGE_LENGTH: [1, 2],
                                 cleaning_column_name_config.IMAGE_WIDTH: [1, 2],
                                 cleaning_column_name_config.MEDIA_TYPE: ['Film', float("NaN")]})

        obj = CleanTasks()

        actual = obj.check_type_image(actual)

        print(tabulate(actual, headers='keys', tablefmt='psql'))
        print('------')
        print(tabulate(expected, headers='keys', tablefmt='psql'))

        #pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))

    def test_remove_invalid_media_type(self):
        actual = pd.DataFrame({cleaning_column_name_config.ULLID: [1, 2],
                               cleaning_column_name_config.ACCOUNTED_INK_BLACK: [1, 2],
                               cleaning_column_name_config.ACCOUNTED_INK_CYAN: [1, 2],
                               cleaning_column_name_config.ACCOUNTED_INK_MAGENTA: [1, 2],
                               cleaning_column_name_config.ACCOUNTED_INK_YELLOW: [1, 2],
                               cleaning_column_name_config.DATE: ['21/03/2001', '21/03/2001'],
                               cleaning_column_name_config.IMAGE_LENGTH: [1, 2],
                               cleaning_column_name_config.IMAGE_WIDTH: [1, 2],
                               cleaning_column_name_config.MEDIA_TYPE: ['Film', 'film1']})
        expected = pd.DataFrame({cleaning_column_name_config.ULLID: [1],
                                 cleaning_column_name_config.ACCOUNTED_INK_BLACK: [1],
                                 cleaning_column_name_config.ACCOUNTED_INK_CYAN: [1],
                                 cleaning_column_name_config.ACCOUNTED_INK_MAGENTA: [1],
                                 cleaning_column_name_config.ACCOUNTED_INK_YELLOW: [1],
                                 cleaning_column_name_config.DATE: ['21/03/2001'],
                                 cleaning_column_name_config.IMAGE_LENGTH: [1],
                                 cleaning_column_name_config.IMAGE_WIDTH: [1],
                                 cleaning_column_name_config.MEDIA_TYPE: ['Film']})
        obj = CleanTasks()

        actual = obj.RemoveInvalid_media_type(actual)
        print(tabulate(actual, headers='keys', tablefmt='psql'))
        print('------')
        print(tabulate(expected, headers='keys', tablefmt='psql'))

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))


    def test_make_data_frame_image(self):
        actual = pd.DataFrame({cleaning_column_name_config.ULLID: [1],
                               cleaning_column_name_config.ACCOUNTED_INK_BLACK: [1],
                               cleaning_column_name_config.ACCOUNTED_INK_CYAN: [1],
                               cleaning_column_name_config.ACCOUNTED_INK_MAGENTA: [1],
                               cleaning_column_name_config.ACCOUNTED_INK_YELLOW: [1],
                               cleaning_column_name_config.DATE: ['21/03/2001'],
                               cleaning_column_name_config.IMAGE_LENGTH: [1],
                               cleaning_column_name_config.IMAGE_WIDTH: [1],
                               cleaning_column_name_config.MEDIA_TYPE: ['Film1'],
                               cleaning_column_name_config.DATE: ['21/03/2001'],
                               cleaning_column_name_config.IMAGE_LENGTH: [1],
                               cleaning_column_name_config.IMAGE_WIDTH: [1],
                               cleaning_column_name_config.MEDIA_TYPE: ['Film1'],
                               })
        expected = pd.DataFrame({cleaning_column_name_config.ULLID: [1],
                                 cleaning_column_name_config.ACCOUNTED_INK_BLACK: [1],
                                 cleaning_column_name_config.ACCOUNTED_INK_CYAN: [1],
                                 cleaning_column_name_config.ACCOUNTED_INK_MAGENTA: [1],
                                 cleaning_column_name_config.ACCOUNTED_INK_YELLOW: [1],
                                 cleaning_column_name_config.DATE: ['21/03/2001'],
                                 cleaning_column_name_config.IMAGE_LENGTH: [1],
                                 cleaning_column_name_config.IMAGE_WIDTH: [1],
                                 cleaning_column_name_config.MEDIA_TYPE: ['Film1']})



        obj = CleanTasks()

        actual = obj.make_data_frame_image(actual)
        print(tabulate(actual, headers='keys', tablefmt='psql'))
        print('------')
        print(tabulate(expected, headers='keys', tablefmt='psql'))

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))

