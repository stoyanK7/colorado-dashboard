from datetime import timedelta, datetime

from airflow import DAG
from airflow.operators.python_operator import PythonOperator

from tasks.read.ReadTasks import ReadTasks
from tasks.clean.cleanTasks import CleanTasks
from tasks.aggregate.AgregateTasks import AggregateTasks

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
    'pipeline',
    default_args=default_args,
    description='pipeline',
    schedule_interval=timedelta(days=1),
    start_date=datetime(2021, 1, 1),
    catchup=False,
    tags=['pipeline'],
) as dag:
    readImage = PythonOperator(
        task_id='readImage',
        python_callable=ReadTasks.ReadImage
    )
    cleanImage = PythonOperator(
        task_id='cleanImage',
        python_callable=CleanTasks.CleanImage
    )
    aggregateImage = PythonOperator(
        task_id='aggregateImage',
        python_callable=AggregateTasks.aggregate_image
    )
    # readMediaPrepare = PythonOperator(
    #     task_id='readMediaPrepare',
    #     python_callable=ReadTasks.ReadTasks.ReadMediaPrepare
    # )
    # readPrintCycle = PythonOperator(
    #     task_id='readPrintCycle',
    #     python_callable=ReadTasks.ReadTasks.ReadPrintCycle
    # )

    readImage >> cleanImage >> aggregateImage

    # readImage