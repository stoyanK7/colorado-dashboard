from airflow.hooks.postgres_hook import PostgresHook
import pandas as pd
import os
def dataAggregator():
    data = getData()
    aggregatedData = aggregate(data)
    createTable()
    saveData(aggregatedData)

def getData():
    hook = PostgresHook(postgres_conn_id="postgres_default")
    data = hook.get_pandas_df(sql="SELECT * FROM datareadertable;")
    return data

def readData(path, delimiter):
    return pd.read_csv(path, delimiter=delimiter)

def aggregate(data):
    data["area"] = data['imagelength'] * data['imagewidth']
    data["datetime"] = pd.to_datetime(data["datetime"], unit='ms')
    data = data[["area", 'mediatype', "datetime", "ullid"]]
    data.index = data["ullid"]
    data['day'] = data['datetime'].dt.date
    data = data.groupby(['day', 'mediatype'])['area'].sum()
    return data.reset_index()

def createTable():
    hook = PostgresHook(postgres_conn_id="postgres_default")
    hook.run("CREATE TABLE IF NOT EXISTS dataaggregatortable ("
                 "dateDay date, "
                "mediatype varchar(500), "
                 "area decimal not null"
                 ");")

def saveData(data):
    hook = PostgresHook(postgres_conn_id="postgres_default")
    hook.insert_rows(table="dataaggregatortable", rows=list(data.itertuples(index=False, name=None)))