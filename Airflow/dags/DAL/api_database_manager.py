import pandas as pd
from airflow.models import Variable
from sqlalchemy import create_engine


class ApiDatabaseManager:

    def get_connection(self):
        engine = create_engine(
            f"mysql+pymysql://{Variable.get('api_username')}:{Variable.get('api_password')}@{Variable.get('api_connection_address')}:{Variable.get('api_db_port')}/{Variable.get('api_db_name')}",
            pool_recycle=3600)
        return engine.connect()

    def send_df(self, table_name, df, if_exists="append"):
        connection = self.get_connection()
        try:
            df.to_sql(table_name, connection, if_exists=if_exists, index=False)
        finally:
            connection.close()

    def sql_query(self, sql):
        connection = self.get_connection()
        try:
            connection.execute(sql)
        finally:
            connection.close()

    def read_pd(self, sql):
        connection = self.get_connection()
        try:
            result = pd.read_sql(sql, connection)
        finally:
            connection.close()
        return result
