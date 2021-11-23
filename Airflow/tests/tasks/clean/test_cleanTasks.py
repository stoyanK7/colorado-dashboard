import unittest
import pandas as pd
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
        actual = pd.DataFrame({'ullid': ['1', '2', '2', '3', '4', '5'], 'style': ['a', 'b', 'b', 'c', 'd', 'e']})
        expected = pd.DataFrame({'ullid': ['1', '2', '3', '4', '5'], 'style': ['a', 'b', 'c', 'd', 'e']})
        actual = actual.set_index('ullid')
        expected = expected.set_index('ullid')
        obj = CleanTasks()
        actual = obj.RemoveDuplicatesImage(actual)
        pd.testing.assert_frame_equal(actual, expected)

    def testCheckNegativeImage(self):
        actual = pd.DataFrame({'ullid': ['1', '2', '2', '3', '4', '5'], 'style': ['a', 'b', 'b', '', 'd', 'e']})

        actual = actual.set_index('ullid')
        actual = actual['style'].replace('', actual.nan, inplace=True)
        print(actual)
