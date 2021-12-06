
from datetime import datetime, timedelta
from textwrap import dedent
from pipelineParts import logTest
from pipelineParts import dataReader
from pipelineParts import dataAggregator
from DAL import postgres_database_manager


# The DAG object; we'll need this to instantiate a DAG
from airflow import DAG

# Operators; we need this to operate!
from airflow.operators.bash import BashOperator
from airflow.operators.python_operator import PythonOperator
from airflow.operators.postgres_operator import PostgresOperator
# These args will get passed on to each operator
# You can override them on a per-task basis during operator initialization
default_args = {
    'owner': 'airflow',
    'depends_on_past': False,
    'email': ['airflow@example.com'],
    'email_on_failure': False,
    'email_on_retry': False,
    'retries': 1,
    'retry_delay': timedelta(minutes=5),
}

with DAG(
    'testDag',
    default_args=default_args,
    description='A test dag',
    schedule_interval=timedelta(days=1),
    start_date=datetime(2021, 1, 1),
    catchup=False,
    tags=['test'],
) as dag:

    t1 = PythonOperator(
        task_id='Log1',
        python_callable=logTest.logTest
    )

    t3 = PythonOperator(
        task_id='readData',
        python_callable=dataReader.dataReader
    )

    t4 = PythonOperator(
        task_id="aggregateData",
        python_callable=dataAggregator.dataAggregator
    )

    t3 >> t4

