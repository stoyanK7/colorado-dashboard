import unittest
import pandas as pd
from tabulate import tabulate
import os
import subprocess

from airflow.models import Connection
from airflow.settings import Session
from cryptography.fernet import Fernet
from sqlalchemy import create_engine, text

from tasks.clean.cleanTasks import CleanTasks
from config import CleaningColumnNameConfig


class PostgresDatabaseManagerTests(unittest.TestCase):

    def test_remove_duplicates_image(self):
        actual = pd.DataFrame({CleaningColumnNameConfig.ULLID: [1, 2, 2, 3, 4, 5], CleaningColumnNameConfig.MEDIA_TYPE: ['a', 'b', 'b', 'c', 'd', 'e']})
        expected = pd.DataFrame({CleaningColumnNameConfig.ULLID: [1, 2, 3, 4, 5], CleaningColumnNameConfig.MEDIA_TYPE: ['a', 'b', 'c', 'd', 'e']})
        obj = CleanTasks()

        actual = obj.remove_duplicates(actual)
        print(actual)
        print('------')
        print(expected)

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))


    def test_check_negative_image(self):
        actual = pd.DataFrame({CleaningColumnNameConfig.ULLID: [1, -2, 3, 4, 5, 6, 7, 8],
                               CleaningColumnNameConfig.ACCOUNTED_INK_BLACK: [1, 2, -3, 4, 5, 6, 7, 8],
                               CleaningColumnNameConfig.ACCOUNTED_INK_CYAN: [1, 2, 3, -4, 5, 6, 7, 8],
                               CleaningColumnNameConfig.ACCOUNTED_INK_MAGENTA: [1, 2, 3, 4, -5, 6, 7, 8],
                               CleaningColumnNameConfig.ACCOUNTED_INK_YELLOW: [1, 2, 3, 4, 5, -6, 7, 8],
                               CleaningColumnNameConfig.DATE: ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'],
                               CleaningColumnNameConfig.IMAGE_LENGTH: [1, 2, 3, 4, 5, 6, -7, 8],
                               CleaningColumnNameConfig.IMAGE_WIDTH: [1, 2, 3, 4, 5, 6, 7, -8],
                               CleaningColumnNameConfig.MEDIA_TYPE: ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h']})

        expected = pd.DataFrame({CleaningColumnNameConfig.ULLID: [1],
                                 CleaningColumnNameConfig.ACCOUNTED_INK_BLACK: [1],
                                 CleaningColumnNameConfig.ACCOUNTED_INK_CYAN: [1],
                                 CleaningColumnNameConfig.ACCOUNTED_INK_MAGENTA: [1],
                                 CleaningColumnNameConfig.ACCOUNTED_INK_YELLOW: [1],
                                 CleaningColumnNameConfig.DATE: ['a'],
                                 CleaningColumnNameConfig.IMAGE_LENGTH: [1],
                                 CleaningColumnNameConfig.IMAGE_WIDTH: [1],
                                 CleaningColumnNameConfig.MEDIA_TYPE: ['a']})
        obj = CleanTasks()

        actual = obj.check_negative_image(actual)
        print(actual)

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))


    def test_remove_null(self):
        actual = pd.DataFrame({CleaningColumnNameConfig.ULLID: [1, -2],
                               CleaningColumnNameConfig.ACCOUNTED_INK_BLACK: [1, 2],
                               CleaningColumnNameConfig.ACCOUNTED_INK_CYAN: [1, 2],
                               CleaningColumnNameConfig.ACCOUNTED_INK_MAGENTA: [1, 2],
                               CleaningColumnNameConfig.ACCOUNTED_INK_YELLOW: [1, 2],
                               CleaningColumnNameConfig.DATE: ['a', 'b'],
                               CleaningColumnNameConfig.IMAGE_LENGTH: [1, 2],
                               CleaningColumnNameConfig.IMAGE_WIDTH: [1, 2],
                               CleaningColumnNameConfig.MEDIA_TYPE: ['a', '']})
        expected = pd.DataFrame({CleaningColumnNameConfig.ULLID: [1],
                                 CleaningColumnNameConfig.ACCOUNTED_INK_BLACK: [1],
                                 CleaningColumnNameConfig.ACCOUNTED_INK_CYAN: [1],
                                 CleaningColumnNameConfig.ACCOUNTED_INK_MAGENTA: [1],
                                 CleaningColumnNameConfig.ACCOUNTED_INK_YELLOW: [1],
                                 CleaningColumnNameConfig.DATE: ['a'],
                                 CleaningColumnNameConfig.IMAGE_LENGTH: [1],
                                 CleaningColumnNameConfig.IMAGE_WIDTH: [1],
                                 CleaningColumnNameConfig.MEDIA_TYPE: ['a']})
        obj = CleanTasks()

        actual = obj.remove_row_null(actual)
        print(tabulate(actual, headers='keys', tablefmt='psql'))
        print('------')
        print(tabulate(expected, headers='keys', tablefmt='psql'))

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))

    def test_check_type_image(self):
        actual = pd.DataFrame({CleaningColumnNameConfig.ULLID: [1, 'a'],
                               CleaningColumnNameConfig.ACCOUNTED_INK_BLACK: [1, 2],
                               CleaningColumnNameConfig.ACCOUNTED_INK_CYAN: [1, 2],
                               CleaningColumnNameConfig.ACCOUNTED_INK_MAGENTA: [1, 2],
                               CleaningColumnNameConfig.ACCOUNTED_INK_YELLOW: [1, 2],
                               CleaningColumnNameConfig.DATE: ['21/03/2001', 'b'],
                               CleaningColumnNameConfig.IMAGE_LENGTH: [1, 2],
                               CleaningColumnNameConfig.IMAGE_WIDTH: [1, 2],
                               CleaningColumnNameConfig.MEDIA_TYPE: ['Film', 1]})


        expected = pd.DataFrame({CleaningColumnNameConfig.ULLID: [1, float("NaN")],
                                 CleaningColumnNameConfig.ACCOUNTED_INK_BLACK: [1, 2],
                                 CleaningColumnNameConfig.ACCOUNTED_INK_CYAN: [1, 2],
                                 CleaningColumnNameConfig.ACCOUNTED_INK_MAGENTA: [1, 2],
                                 CleaningColumnNameConfig.ACCOUNTED_INK_YELLOW: [1, 13840000],
                                 CleaningColumnNameConfig.DATE: ['21/03/2001', float("NaN")],
                                 CleaningColumnNameConfig.IMAGE_LENGTH: [1, 2],
                                 CleaningColumnNameConfig.IMAGE_WIDTH: [1, 2],
                                 CleaningColumnNameConfig.MEDIA_TYPE: ['Film', float("NaN")]})

        obj = CleanTasks()

        actual = obj.check_type_image(actual)

        print(tabulate(actual, headers='keys', tablefmt='psql'))
        print('------')
        print(tabulate(expected, headers='keys', tablefmt='psql'))

        #pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))

    def test_remove_invalid_media_type(self):
        actual = pd.DataFrame({CleaningColumnNameConfig.ULLID: [1, 2],
                               CleaningColumnNameConfig.ACCOUNTED_INK_BLACK: [1, 2],
                               CleaningColumnNameConfig.ACCOUNTED_INK_CYAN: [1, 2],
                               CleaningColumnNameConfig.ACCOUNTED_INK_MAGENTA: [1, 2],
                               CleaningColumnNameConfig.ACCOUNTED_INK_YELLOW: [1, 2],
                               CleaningColumnNameConfig.DATE: ['21/03/2001', '21/03/2001'],
                               CleaningColumnNameConfig.IMAGE_LENGTH: [1, 2],
                               CleaningColumnNameConfig.IMAGE_WIDTH: [1, 2],
                               CleaningColumnNameConfig.MEDIA_TYPE: ['Film', 'film1']})
        expected = pd.DataFrame({CleaningColumnNameConfig.ULLID: [1],
                                 CleaningColumnNameConfig.ACCOUNTED_INK_BLACK: [1],
                                 CleaningColumnNameConfig.ACCOUNTED_INK_CYAN: [1],
                                 CleaningColumnNameConfig.ACCOUNTED_INK_MAGENTA: [1],
                                 CleaningColumnNameConfig.ACCOUNTED_INK_YELLOW: [1],
                                 CleaningColumnNameConfig.DATE: ['21/03/2001'],
                                 CleaningColumnNameConfig.IMAGE_LENGTH: [1],
                                 CleaningColumnNameConfig.IMAGE_WIDTH: [1],
                                 CleaningColumnNameConfig.MEDIA_TYPE: ['Film']})
        obj = CleanTasks()

        actual = obj.RemoveInvalid_media_type(actual)
        print(tabulate(actual, headers='keys', tablefmt='psql'))
        print('------')
        print(tabulate(expected, headers='keys', tablefmt='psql'))

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))


    def test_make_data_frame_image(self):
        actual = pd.DataFrame({CleaningColumnNameConfig.ULLID: [1],
                               CleaningColumnNameConfig.ACCOUNTED_INK_BLACK: [1],
                               CleaningColumnNameConfig.ACCOUNTED_INK_CYAN: [1],
                               CleaningColumnNameConfig.ACCOUNTED_INK_MAGENTA: [1],
                               CleaningColumnNameConfig.ACCOUNTED_INK_YELLOW: [1],
                               CleaningColumnNameConfig.DATE: ['21/03/2001'],
                               CleaningColumnNameConfig.IMAGE_LENGTH: [1],
                               CleaningColumnNameConfig.IMAGE_WIDTH: [1],
                               CleaningColumnNameConfig.MEDIA_TYPE: ['Film1'],
                               CleaningColumnNameConfig.DATE: ['21/03/2001'],
                               CleaningColumnNameConfig.IMAGE_LENGTH: [1],
                               CleaningColumnNameConfig.IMAGE_WIDTH: [1],
                               CleaningColumnNameConfig.MEDIA_TYPE: ['Film1'],
                               })
        expected = pd.DataFrame({CleaningColumnNameConfig.ULLID: [1],
                                 CleaningColumnNameConfig.ACCOUNTED_INK_BLACK: [1],
                                 CleaningColumnNameConfig.ACCOUNTED_INK_CYAN: [1],
                                 CleaningColumnNameConfig.ACCOUNTED_INK_MAGENTA: [1],
                                 CleaningColumnNameConfig.ACCOUNTED_INK_YELLOW: [1],
                                 CleaningColumnNameConfig.DATE: ['21/03/2001'],
                                 CleaningColumnNameConfig.IMAGE_LENGTH: [1],
                                 CleaningColumnNameConfig.IMAGE_WIDTH: [1],
                                 CleaningColumnNameConfig.MEDIA_TYPE: ['Film1']})



        obj = CleanTasks()

        actual = obj.make_data_frame_image(actual)
        print(tabulate(actual, headers='keys', tablefmt='psql'))
        print('------')
        print(tabulate(expected, headers='keys', tablefmt='psql'))

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))

