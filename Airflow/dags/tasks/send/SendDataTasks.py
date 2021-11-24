import logging

from DAL.PostgresDatabaseManager import PostgresDatabaseManager
from config import AggregateTableNameConfig, AggregateColumnNameConfig
import pandas as pd


import mysql.connector

class SendDataTasks():
    def __init__(self):
        connection = mysql.connector.connect(host='localhost',
                                     user='root',
                                     password='admin',
                                     db='canon')
        self.cursor = connection.cursor()

    def SendDataMediaTypeSquareMeterGraph(self):

        df = SendDataTasks.__read_from_db(AggregateTableNameConfig.AGGREGATEIMAGE)

    @staticmethod
    def __read_from_db_postgresql(table_name) -> pd.DataFrame:
        # read from db
        logging.info("Reading the data from database.")
        pdm = PostgresDatabaseManager()
        df = pdm.readTable(table_name)
        return df

    @staticmethod
    def __read_from_db_api(table_name):

        statement = """SELECT date FROM {table} ORDER BY ullid DESC LIMIT 1 ;""".format(table=table_name)
        return

    @staticmethod
    def __check_adding_area(df, date):
        date_first_row = df[AggregateColumnNameConfig.DATE].loc[0]
        if (date == date_first_row):


    @staticmethod
    def __adding_area(area_api, area_postgresql):
        return area_api + area_postgresql

    @staticmethod
    def __update_area(new_area, table_name):
        statement = """WITH imagetable AS 
                        ( SELECT TOP 1 * FROM {table} BY ullid DESC) 
                        UPDATE {table} SET area={area};""".format(table=table_name, area=new_area)

    ULLID = "ullid"
    DATE = "date"
    IMAGEAREA = "imagearea"
    MEDIATYPE = "mediatype"


    @staticmethod
    def __add_data_to_api():
        statement = """INSERT INTO {table} (ullid, date, imagearea, mediatype)
                       VALUES ({}})""".format(table=table_name, area=new_area)
