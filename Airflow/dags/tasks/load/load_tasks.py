import logging

import pymysql
import pandas as pd

from config import aggregate_table_name_config, aggregate_column_name_config
from DAL.postgres_database_manager import PostgresDatabaseManager
from tabulate import tabulate

from config.aggregate_column_name_config import DATE
from config.aggregate_table_name_config import AGGREGATE_MEDIA_CATEGORY_USAGE, AGGREGATE_INK_USAGE
from config.preprocess_col_name_constants import MACHINEID


class LoadTasks:


    @staticmethod
    def load_media_category_usage():
        return
        # load data from airflow db
        pdm = PostgresDatabaseManager()
        df = pdm.read_table(AGGREGATE_INK_USAGE)
        api_table_name = "ink_usage"
        date_col = "date"
        start_date = df[DATE].min()
        end_date = df[DATE].max()
        machineid = MACHINEID
        params = [start_date, end_date]

        connection = LoadTasks.connect_to_api_database()

        sql = f"SELECT * FROM {api_table_name} WHERE {date_col} BETWEEN %s AND %s"
        api_df = pd.read_sql(sql, connection, params=params)
        concat_df = pd.concat(df, api_df)
        sum_df = concat_df.groupby([date_col, machineid], as_index=False).sum()
        sum_df.to_sql(api_table_name, connection, if_exists="replace")

        #
        # put data in api database

        pass







    @staticmethod
    def connect_to_api_database():
        return pymysql.connect(host='host.docker.internal', user='canon', password='canon', db='canon')


        # df = LoadTasks._read_from_db_postgresql(aggregate_table_name_config.AGGREGATE_IMAGE)
        # if df.empty:
        #     logging.info("No new data was found, skipping step.")
        #     return
        #
        #
        # first_date_df = LoadTasks._get_first_value_from_df(df, aggregate_column_name_config.DATE)
        # first_area_df = LoadTasks._get_first_value_from_df(df, aggregate_column_name_config.IMAGE_AREA)
        #
        # last_date_api = LoadTasks._get_last_value_from_api("media_category_usage", aggregate_column_name_config.DATE)
        # last_area_api = LoadTasks._get_last_value_from_api("media_category_usage", "printed_square_meters")
        #
        #
        #
        #
        # if last_area_api != "" and last_date_api != "":
        #     if LoadTasks._check_adding_area(last_date_api, first_date_df):
        #         new_area = LoadTasks._adding_area(last_area_api, first_area_df)
        #         LoadTasks._update_area('media_category_usage', new_area, last_date_api)
        #         df = df[df[aggregate_column_name_config.DATE] != pd.to_datetime(last_date_api.strftime('%Y-%m-%d'), format='%Y-%m-%d')]
        #         logging.info(tabulate(df, headers='keys', tablefmt='psql'))
        #         logging.info(new_area)
        # logging.info(df)
        # LoadTasks._add_data_to_api(df)
        # logging.info(last_date_api)
        # logging.info(first_date_df)
        # logging.info(last_area_api)
        # logging.info(first_area_df)






    @staticmethod
    def _read_from_db_postgresql(table_name) -> pd.DataFrame:
        logging.info("Reading the data from database.")
        pdm = PostgresDatabaseManager()
        df = pdm.read_table(table_name)
        return df


    @staticmethod
    def _get_last_value_from_api(table_name, column_name):
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
    def _get_first_value_from_df(df, column_name):
        return df[column_name].loc[0]


    @staticmethod
    def _check_adding_area(last_date_api, last_date_df):
        last_date_api = last_date_api.strftime('%Y-%m-%d')
        logging.info(last_date_api)
        logging.info(last_date_df)
        logging.info("Checking is equal")
        if last_date_api == last_date_df:
            return True
        else:
            return False


    @staticmethod
    def _adding_area(area_api, area_df):
        return area_api + area_df




    @staticmethod
    def _update_area(table_name, new_area, date):
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
    def _add_data_to_api(df):
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
    def load_sqm_per_print_mode():
        pass

    @staticmethod
    def load_ink_usage():
        pdm = PostgresDatabaseManager()
        df = pdm.read_table(AGGREGATE_INK_USAGE)
        api_table_name = "ink_usage"
        date_col = "date"
        start_date = df[DATE].min()
        end_date = df[DATE].max()
        machineid = MACHINEID
        params = [start_date, end_date]

        connection = LoadTasks.connect_to_api_database()

        sql = f"SELECT * FROM {api_table_name} WHERE {date_col} BETWEEN {start_date} AND {end_date}"
        api_df = pd.read_sql(sql, connection)
        print(f"pipeline df: {df.head(100)}")
        print(f"api response: {api_df.head(100)}")
        concat_df = pd.concat([df, api_df])
        sum_df = concat_df.groupby([date_col, machineid], as_index=False).sum()
        print(f"summed df: {sum_df.head(100)}")
        sum_df.to_sql(api_table_name, connection, if_exists="replace")


    @staticmethod
    def load_top_ten_print_volume():
        pass

    @staticmethod
    def load_media_types_per_machine():
        pass