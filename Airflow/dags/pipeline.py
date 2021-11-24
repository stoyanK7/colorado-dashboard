from datetime import timedelta, datetime

from airflow import DAG
from airflow.operators.python_operator import PythonOperator

from tasks.preprocess.PreprocessTasks import PreprocessTasks
from tasks.read.ReadTasks import ReadTasks
from tasks.clean.cleanTasks import CleanTasks
from tasks.aggregate.AgregateTasks import AggregateTasks
from tasks.cleanup.Cleanup import CleanupTasks

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
    readMediaPrepare = PythonOperator(
        task_id='readMediaPrepare_TODO',
        python_callable=ReadTasks.ReadMediaPrepare
    )
    readPrintCycle = PythonOperator(
        task_id='readPrintCycle_TODO',
        python_callable=ReadTasks.ReadPrintCycle
    )

    cleanImage = PythonOperator(
        task_id='cleanImage',
        python_callable=CleanTasks.CleanImage
    )
    cleanMediaPrepare = PythonOperator(
        task_id='cleanMediaPrepare_TODO',
        python_callable=CleanTasks.CleanMediaPrepare
    )
    cleanPrintCycle = PythonOperator(
        task_id='cleanPrintCycle_TODO',
        python_callable=CleanTasks.CleanPrintCycle
    )

    preprocessMediaCategoryUsage = PythonOperator(
        task_id='preprocessMediaCategoryUsage_TODO',
        python_callable=PreprocessTasks.PreprocessMediaCategoryUsage
    )
    preprocessSqmPerPrintMode = PythonOperator(
        task_id='preprocessSqmPerPrintMode_TODO',
        python_callable=PreprocessTasks.PreprocessSqmPerPrintMode
    )
    preprocessInkUsage = PythonOperator(
        task_id='preprocessInkUsage_TODO',
        python_callable=PreprocessTasks.PreprocessInkUsage
    )
    preprocessTopTenPrintVolume = PythonOperator(
        task_id='preprocessTopTenPrintVolume_TODO',
        python_callable=PreprocessTasks.PreprocessTopTenPrintVolume
    )
    preprocessMediaTypesPerMachine = PythonOperator(
        task_id='preprocessMediaTypesPerMachine_TODO',
        python_callable=PreprocessTasks.PreprocessMediaTypesPerMachine
    )

    aggregateMediaCategoryUsage = PythonOperator(
        task_id='aggregateMediaCategoryUsage',
        python_callable=AggregateTasks.AggregateMediaCategoryUsage
    )
    aggregateSqmPerPrintMode = PythonOperator(
        task_id='aggregateSqmPerPrintMode_TODO',
        python_callable=AggregateTasks.AggregateSqmPerPrintMode
    )
    aggregateInkUsage = PythonOperator(
        task_id='aggregateInkUsage_TODO',
        python_callable=AggregateTasks.AggregateInkUsage
    )
    aggregateTopTenPrintVolume = PythonOperator(
        task_id='aggregateTopTenPrintVolume_TODO',
        python_callable=AggregateTasks.AggregateTopTenPrintVolume
    )
    aggregateMediaTypesPerMachine = PythonOperator(
        task_id='aggregateMediaTypesPerMachine_TODO',
        python_callable=AggregateTasks.AggregateMediaTypesPerMachine
    )

    cleanUp = PythonOperator(
        task_id='cleanUp',
        python_callable=CleanupTasks.cleanup
    )

    readImage >> cleanImage >> preprocessMediaCategoryUsage >> aggregateMediaCategoryUsage
    readMediaPrepare >> cleanMediaPrepare
    readPrintCycle >> cleanPrintCycle
    cleanPrintCycle >> preprocessSqmPerPrintMode >> aggregateSqmPerPrintMode
    cleanImage >> preprocessInkUsage >> aggregateInkUsage
    cleanPrintCycle >> preprocessTopTenPrintVolume >> aggregateTopTenPrintVolume
    cleanMediaPrepare >> preprocessMediaTypesPerMachine >> aggregateMediaTypesPerMachine
    cleanPrintCycle >> preprocessMediaTypesPerMachine

    aggregateMediaCategoryUsage >> cleanUp
    aggregateSqmPerPrintMode >> cleanUp
    aggregateInkUsage >> cleanUp
    aggregateTopTenPrintVolume >> cleanUp
    aggregateMediaTypesPerMachine >> cleanUp

    # readImage