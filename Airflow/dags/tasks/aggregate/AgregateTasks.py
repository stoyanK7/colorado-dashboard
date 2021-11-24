import logging

import pandas as pd

from config import AggregateTableNameConfig, AggregateColumnNameConfig, CleanTableNameConfig, CleaningColumnNameConfig
from DAL.PostgresDatabaseManager import PostgresDatabaseManager


class AggregateTasks:

    @staticmethod
    def AggregateMediaCategoryUsage():
        # Take the dataframe from the previous step
        df = AggregateTasks.__read_from_db(CleanTableNameConfig.READIMAGE)
        if df.empty:
            logging.info("No new data was found, skipping step.")
            return
        # Multiply ImageLength and ImageWidth into Area column
        df = AggregateTasks.__aggregate_two_columns(df,
                                                    CleaningColumnNameConfig.IMAGEWIDTH,
                                                    CleaningColumnNameConfig.IMAGELENGTH,
                                                    AggregateColumnNameConfig.IMAGEAREA,
                                                    True)

        # Group
        df = AggregateTasks.__group_by_two_columns_and_sum_third(df,
                                                                 CleaningColumnNameConfig.DATE,
                                                                 CleaningColumnNameConfig.MEDIATYPE,
                                                                 AggregateColumnNameConfig.IMAGEAREA)
        # Save into a database
        AggregateTasks.__insert_into_db(df, AggregateTableNameConfig.AGGREGATEIMAGE)

    @staticmethod
    def AggregateSqmPerPrintMode():
        pass

    @staticmethod
    def AggregateInkUsage():
        pass

    @staticmethod
    def AggregateTopTenPrintVolume():
        pass

    @staticmethod
    def AggregateMediaTypesPerMachine():
        pass


    @staticmethod
    def __read_from_db(table_name) -> pd.DataFrame:
        # read from db
        logging.info("Reading the cleaned data from database.")
        pdm = PostgresDatabaseManager()
        df = pdm.readTable(table_name)
        if df.empty:
            return df
        df = df.set_index(AggregateColumnNameConfig.ULLID)
        return df

    @staticmethod
    def __aggregate_two_columns(df, c1, c2, new_column, to_delete):
        # aggregate two columns
        logging.info(f"Aggregation of {c1} and {c2} into {new_column}.")
        df[new_column] = df[c1] * df[c2]
        if to_delete:
            logging.info(f"Columns: {c1} and {c2} are dropped")
            df = df.drop(columns=[c1, c2])
        return df

    @staticmethod
    def __group_by_two_columns_and_sum_third(df, c1, c2, col_to_sum):
        # group two columns and sum the third
        logging.info(f"Grouping {c1} and {c2} and summing {col_to_sum}.")
        df = pd.to_numeric(df.groupby([c1, c2])[col_to_sum].sum(), errors='coerce')
        return df

    @staticmethod
    def __insert_into_db(df, table_name):
        # put in db
        logging.info("Inserting aggregated data to database.")
        pdm = PostgresDatabaseManager()
        pdm.insertIntoTable(df, table_name)
