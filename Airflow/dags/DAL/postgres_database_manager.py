import sqlalchemy
from airflow.providers.postgres.operators.postgres import PostgresHook
import pandas as pd
from sqlalchemy import text


class PostgresDatabaseManager:
    def __init__(self):
        self.hook = PostgresHook(postgres_conn_id='postgres_default')

    def insert_into_table(self, data_frame: pd.DataFrame, table_name: str, conn: sqlalchemy.engine.Engine = None, if_exists ="append"):
        table_name = table_name.lower()
        if conn == None:
            conn = self.hook.get_sqlalchemy_engine()
        data_frame.to_sql(table_name, con=conn, if_exists=if_exists)

    def delete_table(self, table_name: str):
        table_name = table_name.lower();
        statement = """drop table if exists {table};""".format(table=table_name)
        self.hook.run(statement)

    def read_table(self, table_name: str) -> pd.DataFrame:
        try:
            return pd.read_sql_table(table_name, con=self.hook.get_sqlalchemy_engine())
        except:
            return pd.DataFrame({})