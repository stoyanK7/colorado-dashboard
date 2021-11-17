import pandas as pd
from airflow.hooks.postgres_hook import PostgresHook
import os

def readData(path, delimiter):
    return pd.read_csv(path, delimiter=delimiter)

def createTable():
    hook = PostgresHook(postgres_conn_id="postgres_default")
    hook.run("CREATE TABLE IF NOT EXISTS datareadertable ("
                 "ullid int not null, "
                "mediatype varchar(500), "
                 "datetime bigint not null, "
                 "imageLength decimal, "
                 "imageWidth decimal"
                 ");")


def saveToDatabase(data):
    hook = PostgresHook(postgres_conn_id="postgres_default")
    hook.insert_rows(table="datareadertable", rows=list(data.itertuples(index=False, name=None)))


def dataReader():
    createTable()
    data = readData(os.getenv("AIRFLOW_HOME") + "/dags/data/Image_20210204_123009_00000000000022687026.csv", ";")
    saveToDatabase(data[['ullid', 'MediaType$','LocalTime[us]', 'ImageLength[m]#', 'ImageWidth[m]#']])
    return True

if __name__ == '__main__':
    dataReader()