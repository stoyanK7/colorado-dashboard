import logging

import pymysql
import pandas as pd

from config import AggregateTableNameConfig, AggregateColumnNameConfig
from DAL.PostgresDatabaseManager import PostgresDatabaseManager


class LoadTasks:


    @staticmethod
    def LoadMediaCategoryUsage():
        df = LoadTasks.__read_from_db_postgresql(AggregateTableNameConfig.AGGREGATEIMAGE)
        LoadTasks.__add_data_to_api(df)

        first_date_df = LoadTasks.__get_first_value_from_df(df, AggregateColumnNameConfig.DATE)
        first_area_df = LoadTasks.__get_first_value_from_df(df, AggregateColumnNameConfig.IMAGEAREA)

        last_date_api = LoadTasks.__get_last_value_from_api("media_category_usage", AggregateColumnNameConfig.DATE)
        last_area_api = LoadTasks.__get_last_value_from_api("media_category_usage", "printed_square_meters")



        b_check_adding_area = LoadTasks.__check_adding_area(last_date_api, first_date_df)
        new_area = 0
        if (b_check_adding_area):
            new_area = LoadTasks.__adding_area(last_area_api, first_area_df)
        logging.info(last_area_api)
        logging.info(first_area_df)
        logging.info(new_area)





    @staticmethod
    def __read_from_db_postgresql(table_name) -> pd.DataFrame:
        logging.info("Reading the data from database.")
        pdm = PostgresDatabaseManager()
        df = pdm.readTable(table_name)
        return df


    @staticmethod
    def __get_last_value_from_api(table_name, column_name):
        try:
            logging.info("Reading data to the  MySql database")
            connection = pymysql.connect(host='host.docker.internal', user='canon', password='canon', db='canon')
            cursor = connection.cursor()
            sql = """SELECT {column} FROM {table} ORDER BY date DESC LIMIT 1;"""\
                .format(table=table_name, column=column_name)

            cursor.execute(sql)
            return cursor.fetchone()[0]
        except pymysql.Error as error:
            logging.info(error)
            return
        finally:
            logging.info("close the database connection using close() method.")
            connection.close()


    @staticmethod
    def __get_first_value_from_df(df, column_name):
        return df[column_name].loc[0]


    @staticmethod
    def __check_adding_area(last_date_api, last_date_df):
        last_date_api = last_date_api.strftime('%Y-%m-%d')
        logging.info(last_date_api)
        logging.info(last_date_df)
        logging.info("Checking is null")
        if last_date_api == "":
            return False

        logging.info("Checking is equal")
        if last_date_api == last_date_df:
            return True
        else:
            return False


    @staticmethod
    def __adding_area(area_api, area_df):
        return area_api + area_df




    @staticmethod
    def __update_area(table_name, new_area):
        try:
            logging.info("Making connection with MySql database")
            logging.info("Adding data to the  MySql database")
            connection = pymysql.connect(host='host.docker.internal', user='canon', password='canon', db='canon')
            cursor = connection.cursor()

            sql_read_query = """WITH imagetable AS
                                ( SELECT TOP 1 * FROM {table} BY date DESC)
                                UPDATE {table} SET printed_square_meters={area};"""\
                                .format(table=table_name, area=new_area)
            cursor = connection.cursor(prepared=True)
            cursor.execute(sql_read_query)
            return cursor.fetchall()
        except pymysql.Error as e:
            logging.info(e)
            return
        finally:
            if connection.is_connected():
                cursor.close()
                connection.close()
                logging.info("MySql connection is closed")





    @staticmethod
    def __add_data_to_api(df):
        try:
            logging.info("Adding data to the  MySql database")
            connection = pymysql.connect(host='host.docker.internal', user='canon', password='canon', db='canon')
            cursor = connection.cursor()
            cols = "`,`".join([str(i) for i in df.columns.tolist()])

            for i, row in df.iterrows():
                sql = "INSERT INTO `media_category_usage` " \
                      "(machine_id, `" +cols + "`)" \
                      " VALUES (0, " + "%s," * (len(row) - 1) + "%s)"
                # sql = "INSERT INTO `media_category_usage` " \
                #         "(machine_id, date, media_category, printed_square_meters)" \
                #         " VALUES (0," + "%s,"*(len(row)-1) + "%s)"
                cursor.execute(sql, tuple(row))
                connection.commit()
        except pymysql.Error as e:
            print(e)

        finally:
            logging.info("close the database connection using close() method.")
            connection.close()



    @staticmethod
    def LoadSqmPerPrintMode():
        pass

    @staticmethod
    def LoadInkUsage():
        pass

    @staticmethod
    def LoadTopTenPrintVolume():
        pass

    @staticmethod
    def LoadMediaTypesPerMachine():
        pass