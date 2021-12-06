import unittest
import pandas as pd
import os
import subprocess

from airflow.models import Connection
from airflow.settings import Session
from cryptography.fernet import Fernet
from sqlalchemy import create_engine, text

from dags.DAL.postgres_database_manager import PostgresDatabaseManager

class PostgresDatabaseManagerTests(unittest.TestCase):

    def testCreateTable(self):
        dataFrame = pd.DataFrame(data={"a":[1, 2], "b":[3, 4]})
        name = "testdataframe"
        dbm = PostgresDatabaseManager()
        engine = create_engine("sqlite:////repo/unittests.db")

        dbm.insert_into_table(tableName=name, dataFrame=dataFrame, conn=engine)

        with engine.connect() as connection:
            result = connection.execute(text("select * from testdataframe;"))
            df = pd.DataFrame(data=result.fetchall())
            df.columns = result.keys()
            self.assertEqual(df["a"][0], dataFrame["a"][0])
            self.assertEqual(df["a"][1], dataFrame["a"][1])
            self.assertEqual(df["b"][0], dataFrame["b"][0])
            self.assertEqual(df["b"][1], dataFrame["b"][1])

        engine.dispose()

