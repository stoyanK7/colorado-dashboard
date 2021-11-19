import sqlalchemy
from airflow.providers.postgres.operators.postgres import PostgresHook
import pandas as pd
from sqlalchemy import text


class PostgresDatabaseManager:
    def __init__(self):
        self.hook = PostgresHook(postgres_conn_id='postgres_default')

    def createTable(self, dataFrame: pd.DataFrame, tableName: str, conn: sqlalchemy.engine.Engine = None):
        tableName = tableName.lower()
        if conn == None:
            conn = self.hook.get_sqlalchemy_engine()
        dataFrame.to_sql(tableName, con=conn, if_exists="replace")

    def deleteTable(self, tableName: str):
        tableName = tableName.lower();
        statement = """drop table if exists {table};""".format(table=tableName)
        self.hook.run(statement)