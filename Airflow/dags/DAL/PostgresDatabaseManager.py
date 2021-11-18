import sqlalchemy
from airflow.providers.postgres.operators.postgres import PostgresHook
import pandas as pd


class PostgresDatabaseManager:
    def __init__(self):
        self.hook = PostgresHook(postgres_conn_id='postgres_default')

    def createTable(self, dataFrame: pd.DataFrame, tableName: str, conn: sqlalchemy.engine.Engine):
        if conn == None:
            conn = self.hook.get_sqlalchemy_engine()
        dataFrame.to_sql(tableName, con=conn, if_exists="replace")
