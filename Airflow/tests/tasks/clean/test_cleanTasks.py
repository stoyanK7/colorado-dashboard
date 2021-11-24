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

    def testRemoveDuplicatesImage(self):
        actual = pd.DataFrame({CleaningColumnNameConfig.ULLID: [1, 2, 2, 3, 4, 5], CleaningColumnNameConfig.MEDIATYPE: ['a', 'b', 'b', 'c', 'd', 'e']})
        expected = pd.DataFrame({CleaningColumnNameConfig.ULLID: [1, 2, 3, 4, 5], CleaningColumnNameConfig.MEDIATYPE: ['a', 'b', 'c', 'd', 'e']})
        obj = CleanTasks()

        actual = obj.RemoveDuplicates(actual)
        print(actual)
        print('------')
        print(expected)

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))


    def testCheckNegativeImage(self):
        actual = pd.DataFrame({CleaningColumnNameConfig.ULLID: [1, -2, 3, 4, 5, 6, 7, 8],
                               CleaningColumnNameConfig.ACCOUNTEDINKBLACK: [1, 2, -3, 4, 5, 6, 7, 8],
                               CleaningColumnNameConfig.ACCOUNTEDINKCYAN: [1, 2, 3, -4, 5, 6, 7, 8],
                               CleaningColumnNameConfig.ACCOUNTEDINKMAGENTA: [1, 2, 3, 4, -5, 6, 7, 8],
                               CleaningColumnNameConfig.ACCOUNTEDINKYELLOW: [1, 2, 3, 4, 5, -6, 7, 8],
                               CleaningColumnNameConfig.DATE: ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'],
                               CleaningColumnNameConfig.IMAGELENGTH: [1, 2, 3, 4, 5, 6, -7, 8],
                               CleaningColumnNameConfig.IMAGEWIDTH: [1, 2, 3, 4, 5, 6, 7, -8],
                               CleaningColumnNameConfig.MEDIATYPE: ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h']})

        expected = pd.DataFrame({CleaningColumnNameConfig.ULLID: [1],
                               CleaningColumnNameConfig.ACCOUNTEDINKBLACK: [1],
                               CleaningColumnNameConfig.ACCOUNTEDINKCYAN: [1],
                               CleaningColumnNameConfig.ACCOUNTEDINKMAGENTA: [1],
                               CleaningColumnNameConfig.ACCOUNTEDINKYELLOW: [1],
                               CleaningColumnNameConfig.DATE: ['a'],
                               CleaningColumnNameConfig.IMAGELENGTH: [1],
                               CleaningColumnNameConfig.IMAGEWIDTH: [1],
                               CleaningColumnNameConfig.MEDIATYPE: ['a']})
        obj = CleanTasks()

        actual = obj.CheckNegativeImage(actual)
        print(actual)

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))


    def testRemoveNull(self):
        actual = pd.DataFrame({CleaningColumnNameConfig.ULLID: [1, -2],
                               CleaningColumnNameConfig.ACCOUNTEDINKBLACK: [1, 2],
                               CleaningColumnNameConfig.ACCOUNTEDINKCYAN: [1, 2],
                               CleaningColumnNameConfig.ACCOUNTEDINKMAGENTA: [1, 2],
                               CleaningColumnNameConfig.ACCOUNTEDINKYELLOW: [1, 2],
                               CleaningColumnNameConfig.DATE: ['a', 'b'],
                               CleaningColumnNameConfig.IMAGELENGTH: [1, 2],
                               CleaningColumnNameConfig.IMAGEWIDTH: [1, 2],
                               CleaningColumnNameConfig.MEDIATYPE: ['a', '']})
        expected = pd.DataFrame({CleaningColumnNameConfig.ULLID: [1],
                                 CleaningColumnNameConfig.ACCOUNTEDINKBLACK: [1],
                                 CleaningColumnNameConfig.ACCOUNTEDINKCYAN: [1],
                                 CleaningColumnNameConfig.ACCOUNTEDINKMAGENTA: [1],
                                 CleaningColumnNameConfig.ACCOUNTEDINKYELLOW: [1],
                                 CleaningColumnNameConfig.DATE: ['a'],
                                 CleaningColumnNameConfig.IMAGELENGTH: [1],
                                 CleaningColumnNameConfig.IMAGEWIDTH: [1],
                                 CleaningColumnNameConfig.MEDIATYPE: ['a']})
        obj = CleanTasks()

        actual = obj.RemoveRowNull(actual)
        print(tabulate(actual, headers='keys', tablefmt='psql'))
        print('------')
        print(tabulate(expected, headers='keys', tablefmt='psql'))

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))

    def testCheckTypeImage(self):
        actual = pd.DataFrame({CleaningColumnNameConfig.ULLID: [1, 'a'],
                               CleaningColumnNameConfig.ACCOUNTEDINKBLACK: [1, 2],
                               CleaningColumnNameConfig.ACCOUNTEDINKCYAN: [1, 2],
                               CleaningColumnNameConfig.ACCOUNTEDINKMAGENTA: [1, 2],
                               CleaningColumnNameConfig.ACCOUNTEDINKYELLOW: [1, 2],
                               CleaningColumnNameConfig.DATE: ['21/03/2001', 'b'],
                               CleaningColumnNameConfig.IMAGELENGTH: [1, 2],
                               CleaningColumnNameConfig.IMAGEWIDTH: [1, 2],
                               CleaningColumnNameConfig.MEDIATYPE: ['Film', 1]})


        expected = pd.DataFrame({CleaningColumnNameConfig.ULLID: [1, float("NaN")],
                                 CleaningColumnNameConfig.ACCOUNTEDINKBLACK: [1, 2],
                                 CleaningColumnNameConfig.ACCOUNTEDINKCYAN: [1, 2],
                                 CleaningColumnNameConfig.ACCOUNTEDINKMAGENTA: [1, 2],
                                 CleaningColumnNameConfig.ACCOUNTEDINKYELLOW: [1, 13840000],
                                 CleaningColumnNameConfig.DATE: ['21/03/2001', float("NaN")],
                                 CleaningColumnNameConfig.IMAGELENGTH: [1, 2],
                                 CleaningColumnNameConfig.IMAGEWIDTH: [1, 2],
                                 CleaningColumnNameConfig.MEDIATYPE: ['Film', float("NaN")]})

        obj = CleanTasks()

        actual = obj.CheckTypeImage(actual)

        print(tabulate(actual, headers='keys', tablefmt='psql'))
        print('------')
        print(tabulate(expected, headers='keys', tablefmt='psql'))

        #pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))

    def testRemoveInvalidMediaType(self):
        actual = pd.DataFrame({CleaningColumnNameConfig.ULLID: [1, 2],
                                 CleaningColumnNameConfig.ACCOUNTEDINKBLACK: [1, 2],
                                 CleaningColumnNameConfig.ACCOUNTEDINKCYAN: [1, 2],
                                 CleaningColumnNameConfig.ACCOUNTEDINKMAGENTA: [1, 2],
                                 CleaningColumnNameConfig.ACCOUNTEDINKYELLOW: [1, 2],
                                 CleaningColumnNameConfig.DATE: ['21/03/2001', '21/03/2001'],
                                 CleaningColumnNameConfig.IMAGELENGTH: [1, 2],
                                 CleaningColumnNameConfig.IMAGEWIDTH: [1, 2],
                                 CleaningColumnNameConfig.MEDIATYPE: ['Film', 'film1']})
        expected = pd.DataFrame({CleaningColumnNameConfig.ULLID: [1],
                               CleaningColumnNameConfig.ACCOUNTEDINKBLACK: [1],
                               CleaningColumnNameConfig.ACCOUNTEDINKCYAN: [1],
                               CleaningColumnNameConfig.ACCOUNTEDINKMAGENTA: [1],
                               CleaningColumnNameConfig.ACCOUNTEDINKYELLOW: [1],
                               CleaningColumnNameConfig.DATE: ['21/03/2001'],
                               CleaningColumnNameConfig.IMAGELENGTH: [1],
                               CleaningColumnNameConfig.IMAGEWIDTH: [1],
                               CleaningColumnNameConfig.MEDIATYPE: ['Film']})
        obj = CleanTasks()

        actual = obj.RemoveInvalidMediaType(actual)
        print(tabulate(actual, headers='keys', tablefmt='psql'))
        print('------')
        print(tabulate(expected, headers='keys', tablefmt='psql'))

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))


    def testMakeDataFrameImage(self):
        actual = pd.DataFrame({CleaningColumnNameConfig.ULLID: [1],
                               CleaningColumnNameConfig.ACCOUNTEDINKBLACK: [1],
                               CleaningColumnNameConfig.ACCOUNTEDINKCYAN: [1],
                               CleaningColumnNameConfig.ACCOUNTEDINKMAGENTA: [1],
                               CleaningColumnNameConfig.ACCOUNTEDINKYELLOW: [1],
                               CleaningColumnNameConfig.DATE: ['21/03/2001'],
                               CleaningColumnNameConfig.IMAGELENGTH: [1],
                               CleaningColumnNameConfig.IMAGEWIDTH: [1],
                               CleaningColumnNameConfig.MEDIATYPE: ['Film1'],
                               CleaningColumnNameConfig.DATE: ['21/03/2001'],
                               CleaningColumnNameConfig.IMAGELENGTH: [1],
                               CleaningColumnNameConfig.IMAGEWIDTH: [1],
                               CleaningColumnNameConfig.MEDIATYPE: ['Film1'],
                               })
        expected = pd.DataFrame({CleaningColumnNameConfig.ULLID: [1],
                               CleaningColumnNameConfig.ACCOUNTEDINKBLACK: [1],
                               CleaningColumnNameConfig.ACCOUNTEDINKCYAN: [1],
                               CleaningColumnNameConfig.ACCOUNTEDINKMAGENTA: [1],
                               CleaningColumnNameConfig.ACCOUNTEDINKYELLOW: [1],
                               CleaningColumnNameConfig.DATE: ['21/03/2001'],
                               CleaningColumnNameConfig.IMAGELENGTH: [1],
                               CleaningColumnNameConfig.IMAGEWIDTH: [1],
                               CleaningColumnNameConfig.MEDIATYPE: ['Film1']})



        obj = CleanTasks()

        actual = obj.MakeDataFrameImage(actual)
        print(tabulate(actual, headers='keys', tablefmt='psql'))
        print('------')
        print(tabulate(expected, headers='keys', tablefmt='psql'))

        pd.testing.assert_frame_equal(actual.reset_index(drop=True), expected.reset_index(drop=True))

