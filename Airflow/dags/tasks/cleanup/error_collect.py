import logging
from contextlib import closing

import pandas as pd
from airflow import models, settings
from airflow.models import Variable
from airflow.utils.state import State
from sqlalchemy import create_engine

from DAL.api_database_manager import ApiDatabaseManager
from DAL.postgres_database_manager import PostgresDatabaseManager


class ErrorCollect:
    @staticmethod
    def collect_errors(**context):
        pdm = PostgresDatabaseManager()
        logging.info("Reading errors from the database")
        df = pdm.read_sql(f"""SELECT dag_id, task_id, execution_date, try_number FROM task_instance WHERE dag_id='{context["dag"].dag_id}' AND execution_date='{context["execution_date"]}' AND state='{State.FAILED}'""")
        logging.info(f"{len(df)} errors were found")

        # If no errors, set df to empty row with date and passed to true
        if df.empty:
            logging.info("No errors found, creating empty that passed")
            df = pd.DataFrame(data={"date_time": [context["execution_date"]], "passed": [True]})
        else:
            # generate log file path
            logging.info("Generating log file path")
            df["location"] = df["dag_id"].astype(str) + "/" + df["task_id"].astype(str) + "/" + df["execution_date"].astype(str).replace(" ", "T", regex=True) + "/" + df["try_number"].astype(str) + ".log"

            # read log files and put them into df
            logging.info("Reading log files")
            df["log"] = ""
            for index, row in df.iterrows():
                df["log"][index] = open("airflow/logs/" + df["location"][index], "r").read()
            df["passed"] = False
            # get affected graphs
            logging.info("Getting affected graphs")
            df["affected_graphs"] = df.apply(ErrorCollect.get_affected_graphs, axis=1)

            # rename columns to match database
            logging.info("Renaming columns")
            df = df.rename(columns={"execution_date": "date_time"})
            df = df.rename(columns={"task_id": "step"})
            df = df.drop(["try_number", "dag_id"], axis=1)

        # push to db
        logging.info("Sending to api database")
        dm = ApiDatabaseManager()
        dm.send_df(Variable.get("api_error_table_name"), df)

    @staticmethod
    def get_affected_graphs(row):
        inkusage_media_category = "Ink usage, Media categories usage"
        sqm_media_type_top = "Square meters per print mode, Used media types per machine, Top machines with most print volume"
        task_id = row["task_id"]
        if "Image" in task_id:
            return inkusage_media_category
        elif "MediaPrepare" in task_id:
            return sqm_media_type_top
        elif "PrintCycle" in task_id:
            return sqm_media_type_top
        elif "InkUsage" in task_id:
            return inkusage_media_category
        elif "MediaCategoryUsage" in task_id:
            return inkusage_media_category
        elif "MediaTypesPerMachine" in task_id:
            return sqm_media_type_top
        elif "SqmPerPrintMode" in task_id:
            return sqm_media_type_top
        elif "TopTenPrintVolume" in task_id:
            return sqm_media_type_top
        else:
            return "other"

    @staticmethod
    def _connect_to_api_database():
        logging.info("Opening connection to api database")
        engine = create_engine("mysql+pymysql://canon:canon@host.docker.internal:3306/canon", pool_recycle=3600)
        return engine.connect()

