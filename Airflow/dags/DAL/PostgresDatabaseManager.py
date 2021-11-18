import sqlalchemy
from airflow.providers.postgres.operators.postgres import PostgresHook
import pandas as pd
from sqlalchemy import text


class PostgresDatabaseManager:
    def __init__(self):
        self.hook = PostgresHook(postgres_conn_id='postgres_default')

    def createTable(self, dataFrame: pd.DataFrame, tableName: str, conn: sqlalchemy.engine.Engine = None):
        if conn == None:
            conn = self.hook.get_sqlalchemy_engine()
        dataFrame.to_sql(tableName, con=conn, if_exists="replace")

    def deleteTable(self, tableName: str):
        statement = text("drop table :table;")
        self.hook.get_sqlalchemy_engine().execute(statement, {'table': tableName})

def execute():
    obj = PostgresDatabaseManager()
    dataFrame = pd.DataFrame(data={"a": [1, 2], "b": [3, 4]})
    obj.createTable(dataFrame,"Test")
    # obj.deleteTable('"Test"')
