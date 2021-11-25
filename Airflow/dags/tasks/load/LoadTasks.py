import logging

import pymysql
import pandas as pd

from config import AggregateTableNameConfig, AggregateColumnNameConfig
from DAL.PostgresDatabaseManager import PostgresDatabaseManager
from tabulate import tabulate

class LoadTasks:


    @staticmethod
    def LoadMediaCategoryUsage():
        df = LoadTasks.__read_from_db_postgresql(AggregateTableNameConfig.AGGREGATEIMAGE)
        if df.empty:
            logging.info("No new data was found, skipping step.")
            return


        first_date_df = LoadTasks.__get_first_value_from_df(df, AggregateColumnNameConfig.DATE)
        first_area_df = LoadTasks.__get_first_value_from_df(df, AggregateColumnNameConfig.IMAGEAREA)

        last_date_api = LoadTasks.__get_last_value_from_api("media_category_usage", AggregateColumnNameConfig.DATE)
        last_area_api = LoadTasks.__get_last_value_from_api("media_category_usage", "printed_square_meters")




        if last_area_api != "" and last_date_api != "":
            if LoadTasks.__check_adding_area(last_date_api, first_date_df):
                new_area = LoadTasks.__adding_area(last_area_api, first_area_df)
                LoadTasks.__update_area('media_category_usage', new_area, last_date_api)
                df = df[df[AggregateColumnNameConfig.DATE] != pd.to_datetime(last_date_api.strftime('%Y-%m-%d'), format='%Y-%m-%d')]
                logging.info(tabulate(df, headers='keys', tablefmt='psql'))
                logging.info(new_area)
        logging.info(df)
        LoadTasks.__add_data_to_api(df)
        logging.info(last_date_api)
        logging.info(first_date_df)
        logging.info(last_area_api)
        logging.info(first_area_df)






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
            return ""
        except TypeError as error:
            logging.info(error)
            return ""
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
        logging.info("Checking is equal")
        if last_date_api == last_date_df:
            return True
        else:
            return False


    @staticmethod
    def __adding_area(area_api, area_df):
        return area_api + area_df




    @staticmethod
    def __update_area(table_name, new_area, date):
        try:
            logging.info("Making connection with MySql database")
            logging.info("Adding data to the  MySql database")
            logging.info(date.strftime('%Y-%m-%d'))
            date = date.strftime('%Y-%m-%d')
            connection = pymysql.connect(host='host.docker.internal', user='canon', password='canon', db='canon')
            cursor = connection.cursor()

            sql_read_query = """UPDATE {table} SET printed_square_meters={area} WHERE date ='{date}';"""\
                                .format(table=table_name, area=new_area,date=date)
            logging.info(sql_read_query)
            cursor.execute(sql_read_query)
            connection.commit()
        except pymysql.Error as e:
            logging.info(e)
            return
        finally:
            logging.info("close the database connection using close() method.")
            connection.close()





    @staticmethod
    def __add_data_to_api(df):
        try:
            logging.info("Adding data to the  MySql database")
            connection = pymysql.connect(host='host.docker.internal', user='canon', password='canon', db='canon')
            cursor = connection.cursor()
            cols = "`,`".join([str(i) for i in df.columns.tolist()])

            for i, row in df.iterrows():
                # sql = "INSERT INTO `media_category_usage` " \
                #       "(machine_id, `" +cols + "`)" \
                #       " VALUES (0, " + "%s," * (len(row) - 1) + "%s)"
                sql = "INSERT INTO `media_category_usage` " \
                        "(machine_id, date, media_category, printed_square_meters)" \
                        " VALUES (0," + "%s,"*(len(row)-1) + "%s)"
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