import logging

import pandas as pd

from config import aggregate_column_name_config, clean_table_name_config, \
    clean_image_col_name_constants, preprocess_table_name_config, clean_print_cycle_col_name_constants, \
    clean_media_prepare_col_name_constants, preprocess_col_name_constants
from DAL.postgres_database_manager import PostgresDatabaseManager


class PreprocessTasks():
    @staticmethod
    def preprocess_media_category_usage():
        return
        # The columns to mach the database names
        logging.info("Start preprocess for media category usage.")
        # Take the dataframe from the previous step
        df = PreprocessTasks._read_from_db(clean_table_name_config.READ_IMAGE)

        df = df.rename(columns={clean_image_col_name_constants.MACHINEID: preprocess_col_name_constants.MACHINEID})
        df = df.rename(columns={clean_image_col_name_constants.DATE: preprocess_col_name_constants.DATE})
        # Save dataframe into database
        PreprocessTasks._insert_into_db(df, preprocess_table_name_config.PREPROCESS_MEDIA_CATEGORY_USAGE)

    @staticmethod
    def preprocess_sqm_per_print_mode():
        return
        # The columns to mach the database names
        logging.info("Start preprocess for media category usage.")
        # Take the dataframe from the previous step
        df = PreprocessTasks._read_from_db(clean_table_name_config.READ_PRINT_CYCLE)

        df = df.rename(columns={clean_image_col_name_constants.MACHINEID: preprocess_col_name_constants.MACHINEID})
        df = df.rename(columns={clean_image_col_name_constants.DATE: preprocess_col_name_constants.DATE})
        # Save dataframe into database
        PreprocessTasks._insert_into_db(df, preprocess_table_name_config.PREPROCESS_SQM_PER_PRINT_MODE)

    @staticmethod
    def preprocess_ink_usage():
        logging.info("Start preprocess for ink usage.")
        # Take the dataframe from the previous step
        df = PreprocessTasks._read_from_db(clean_table_name_config.READ_IMAGE)
        # Divide four columns by 1000 to convert from ml to L
        df = PreprocessTasks._divide_four_columns_by(df, clean_image_col_name_constants.ACCOUNTED_INK_BLACK,
                                                     clean_image_col_name_constants.ACCOUNTED_INK_CYAN,
                                                     clean_image_col_name_constants.ACCOUNTED_INK_MAGENTA,
                                                     clean_image_col_name_constants.ACCOUNTED_INK_YELLOW,
                                                     1000)
        df = df.rename(columns={clean_image_col_name_constants.MACHINEID: preprocess_col_name_constants.MACHINEID})
        df = df.rename(columns={clean_image_col_name_constants.DATE: preprocess_col_name_constants.DATE})
        df = df.rename(columns={clean_image_col_name_constants.ACCOUNTED_INK_CYAN: preprocess_col_name_constants.ACCOUNTED_INK_CYAN})
        df = df.rename(columns={clean_image_col_name_constants.ACCOUNTED_INK_MAGENTA: preprocess_col_name_constants.ACCOUNTED_INK_MAGENTA})
        df = df.rename(columns={clean_image_col_name_constants.ACCOUNTED_INK_YELLOW: preprocess_col_name_constants.ACCOUNTED_INK_YELLOW})
        df = df.rename(columns={clean_image_col_name_constants.ACCOUNTED_INK_BLACK: preprocess_col_name_constants.ACCOUNTED_INK_BLACK})

        PreprocessTasks._insert_into_db(df, preprocess_table_name_config.PREPROCESS_INK_USAGE)


    @staticmethod
    def preprocess_media_types_per_machine():
        logging.info("Start preprocess for types per machine.")
        # Take the dataframes from mediaPrepare and PrintCycle table
        df1 = PreprocessTasks._read_from_db(clean_table_name_config.READ_MEDIA_PREPARE)
        df2 = PreprocessTasks._read_from_db(clean_table_name_config.READ_PRINT_CYCLE)

        # Merge the dataframes
        df = PreprocessTasks._merge_two_dataframes(df1, df2, clean_print_cycle_col_name_constants.ENGINE_CYCLE_ID)
        # Make sure to not have unnecessary data
        df = df[[clean_print_cycle_col_name_constants.DATE + "_x",
                 clean_media_prepare_col_name_constants.MEDIA_TYPE_DISPLAY_NAME,
                 clean_print_cycle_col_name_constants.SQUARE_DECIMETER,
                 clean_print_cycle_col_name_constants.MACHINEID + "_x"]]


        # Fix date column from date_x to date
        df = df.rename(
            columns={clean_print_cycle_col_name_constants.DATE + "_x": clean_print_cycle_col_name_constants.DATE})
        df = df.rename(
            columns={clean_print_cycle_col_name_constants.MACHINEID + "_x": clean_print_cycle_col_name_constants.MACHINEID})

        # Divide two column by 100
        df = PreprocessTasks._divide_column_by(df,
                                               clean_print_cycle_col_name_constants.SQUARE_DECIMETER,
                                               preprocess_col_name_constants.PREPROCESSED_SQUARE_DECIMETER,
                                               100)
        print(f"After dividing: {df.columns}")
        df = df.rename(columns={clean_print_cycle_col_name_constants.MACHINEID: preprocess_col_name_constants.MACHINEID})
        df = df.rename(columns={clean_print_cycle_col_name_constants.DATE: preprocess_col_name_constants.DATE})
        df = df.rename(columns={clean_media_prepare_col_name_constants.MEDIA_TYPE_DISPLAY_NAME: preprocess_col_name_constants.MEDIA_TYPE_DISPLAY_NAME})
        print(f"After renaming: {df.columns}")

        # Save dataframe into database
        PreprocessTasks._insert_into_db(df, preprocess_table_name_config.PREPROCESS_MEDIA_TYPES_PER_MACHINE)

    @staticmethod
    def preprocess_top_ten_print_volume():
        # The columns to mach the database names
        logging.info("Start preprocess for media category usage.")
        # Take the dataframe from the previous step
        df = PreprocessTasks._read_from_db(clean_table_name_config.READ_PRINT_CYCLE)

        # Divide two column by 100
        df = PreprocessTasks._divide_column_by(df,
                                               clean_print_cycle_col_name_constants.SQUARE_DECIMETER,
                                               preprocess_col_name_constants.PREPROCESSED_SQUARE_DECIMETER,
                                               100)

        df = df.rename(
            columns={clean_print_cycle_col_name_constants.MACHINEID: preprocess_col_name_constants.MACHINEID})
        df = df.rename(columns={clean_print_cycle_col_name_constants.DATE: preprocess_col_name_constants.DATE})
        # Save dataframe into database
        PreprocessTasks._insert_into_db(df, preprocess_table_name_config.PREPROCESS_TOP_TEN_PRINT_VOLUME)

    @staticmethod
    def _divide_column_by(df, column, column_new_name, number):
        logging.info(f"Preprocess - divide column {column} by {number}.")
        df[column] = df[column] / number
        df = df.rename(columns={column: column_new_name})
        return df

    @staticmethod
    def _merge_two_dataframes(df1, df2, column_for_merging):
        logging.info("Preprocess - merge two dataframes.")
        df = pd.merge(left=df1, right=df2, left_on=column_for_merging, right_on=column_for_merging)
        return df

    @staticmethod
    def _divide_four_columns_by(df, c1, c2, c3, c4, number):
        logging.info(f"Preprocess -dividing: {c1}, {c2}, {c3},{c4} by {number}")
        df[c1] = df[c1] / number
        df[c2] = df[c2] / number
        df[c3] = df[c3] / number
        df[c4] = df[c4] / number
        return df

    @staticmethod
    def _read_from_db(table_name) -> pd.DataFrame:
        # read from db
        logging.info(f"Preprocess - reading table {table_name} from database.")
        pdm = PostgresDatabaseManager()
        df = pdm.read_table(table_name)
        if df.empty:
            return df
        df = df.set_index(aggregate_column_name_config.ULLID)
        return df

    @staticmethod
    def _insert_into_db(df, table_name):
        # put in db
        logging.info("Preprocess - inserting preprocessed data to database.")
        pdm = PostgresDatabaseManager()
        pdm.insert_into_table(df, table_name)
        logging.info("Preprocess finished!")
