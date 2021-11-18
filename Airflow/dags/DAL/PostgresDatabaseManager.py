from airflow.providers.postgres.operators.postgres import PostgresHook
import pandas as pd


class PostgresDatabaseManager:
    def __init__(self):
        self.hook = PostgresHook(postgres_conn_id='postgres_default')

    def createTable(self, dataFrame: pd.DataFrame, tableName: str):
        tableName = tableName.lower()
        dataFrame.to_sql(tableName, con=self.hook.get_sqlalchemy_engine(), if_exists="replace")

    def deleteTable(self, tableName: str):
        tableName = tableName.lower();
        statement = """drop table {table};""".format(table=tableName)
        self.hook.run(statement)