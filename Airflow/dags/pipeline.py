from datetime import timedelta, datetime

from airflow import DAG
from airflow.operators.python_operator import PythonOperator

from tasks.cleanup.error_collect import ErrorCollect
from tasks.load.load_tasks import LoadTasks
from tasks.preprocess.preprocess_tasks import PreprocessTasks
from tasks.read.read_tasks import ReadTasks
from tasks.clean.clean_tasks import CleanTasks
from tasks.aggregate.aggregate_tasks import AggregateTasks
from tasks.cleanup.cleanup import CleanupTasks

default_args = {
    'owner': 'airflow',
    'depends_on_past': False,
    'email': ['airflow@example.com'],
    'email_on_failure': False,
    'email_on_retry': False,
    'retries': 0,
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
    # READ
    readImage = PythonOperator(
        task_id='readImage',
        python_callable=ReadTasks.read_image
    )
    readMediaPrepare = PythonOperator(
        task_id='readMediaPrepare',
        python_callable=ReadTasks.read_media_prepare
    )
    readPrintCycle = PythonOperator(
        task_id='readPrintCycle',
        python_callable=ReadTasks.read_print_cycle
    )

    # CLEAN
    cleanImage = PythonOperator(
        task_id='cleanImage',
        python_callable=CleanTasks.clean_image
    )
    cleanMediaPrepare = PythonOperator(
        task_id='cleanMediaPrepare',
        python_callable=CleanTasks.clean_media_prepare
    )
    cleanPrintCycle = PythonOperator(
        task_id='cleanPrintCycle',
        python_callable=CleanTasks.clean_print_cycle
    )

    # PREPROCESS
    preprocessMediaCategoryUsage = PythonOperator(
        task_id='preprocessMediaCategoryUsage',
        python_callable=PreprocessTasks.preprocess_media_category_usage
    )
    preprocessSqmPerPrintMode = PythonOperator(
        task_id='preprocessSqmPerPrintMode',
        python_callable=PreprocessTasks.preprocess_sqm_per_print_mode
    )
    preprocessInkUsage = PythonOperator(
        task_id='preprocessInkUsage',
        python_callable=PreprocessTasks.preprocess_ink_usage
    )
    preprocessTopTenPrintVolume = PythonOperator(
        task_id='preprocessTopTenPrintVolume',
        python_callable=PreprocessTasks.preprocess_top_ten_print_volume
    )
    preprocessMediaTypesPerMachine = PythonOperator(
        task_id='preprocessMediaTypesPerMachine',
        python_callable=PreprocessTasks.preprocess_media_types_per_machine
    )

    # AGGREGATE
    aggregateMediaCategoryUsage = PythonOperator(
        task_id='aggregateMediaCategoryUsage',
        python_callable=AggregateTasks.aggregate_media_category_usage
    )
    aggregateSqmPerPrintMode = PythonOperator(
        task_id='aggregateSqmPerPrintMode',
        python_callable=AggregateTasks.aggregate_sqm_per_print_mode
    )
    aggregateInkUsage = PythonOperator(
        task_id='aggregateInkUsage',
        python_callable=AggregateTasks.aggregate_ink_usage
    )
    aggregateTopTenPrintVolume = PythonOperator(
        task_id='aggregateTopTenPrintVolume',
        python_callable=AggregateTasks.aggregate_top_ten_print_volume
    )
    aggregateMediaTypesPerMachine = PythonOperator(
        task_id='aggregateMediaTypesPerMachine',
        python_callable=AggregateTasks.aggregate_media_types_per_machine
    )

    #LOAD
    loadMediaCategoryUsage = PythonOperator(
        task_id='loadMediaCategoryUsage',
        python_callable=LoadTasks.load_media_category_usage
    )
    loadSqmPerPrintMode = PythonOperator(
        task_id='loadSqmPerPrintMode',
        python_callable=LoadTasks.load_sqm_per_print_mode
    )
    loadInkUsage = PythonOperator(
        task_id='loadInkUsage',
        python_callable=LoadTasks.load_ink_usage
    )
    loadTopTenPrintVolume = PythonOperator(
        task_id='loadTopTenPrintVolume',
        python_callable=LoadTasks.load_top_ten_print_volume
    )
    loadMediaTypesPerMachine = PythonOperator(
        task_id='loadMediaTypesPerMachine',
        python_callable=LoadTasks.load_media_types_per_machine
    )

    #CLEANUP
    cleanUp = PythonOperator(
        task_id='cleanUp',
        trigger_rule="all_done",
        python_callable=CleanupTasks.cleanup
    )
    error_collect = PythonOperator(
        task_id='errorCollect',
        trigger_rule="all_done",
        python_callable=ErrorCollect.collect_errors
    )

    # cleaning
    readImage >> cleanImage
    readMediaPrepare >> cleanMediaPrepare
    readPrintCycle >> cleanPrintCycle
    # preprocess
    cleanImage >> preprocessMediaCategoryUsage
    cleanPrintCycle >> preprocessSqmPerPrintMode
    cleanImage >> preprocessInkUsage
    cleanPrintCycle >> preprocessTopTenPrintVolume
    cleanMediaPrepare >> preprocessMediaTypesPerMachine
    cleanPrintCycle >> preprocessMediaTypesPerMachine
    # aggregate
    preprocessMediaCategoryUsage >> aggregateMediaCategoryUsage
    preprocessSqmPerPrintMode >> aggregateSqmPerPrintMode
    preprocessInkUsage >> aggregateInkUsage
    preprocessTopTenPrintVolume >> aggregateTopTenPrintVolume
    preprocessMediaTypesPerMachine >> aggregateMediaTypesPerMachine
    # load
    aggregateMediaCategoryUsage >> loadMediaCategoryUsage
    aggregateInkUsage >> loadInkUsage
    aggregateMediaCategoryUsage >> loadInkUsage
    aggregateInkUsage >> loadMediaCategoryUsage
    aggregateSqmPerPrintMode >> loadSqmPerPrintMode
    aggregateTopTenPrintVolume >> loadTopTenPrintVolume
    aggregateMediaTypesPerMachine >> loadMediaTypesPerMachine
    aggregateSqmPerPrintMode >> loadTopTenPrintVolume
    aggregateTopTenPrintVolume >> loadMediaTypesPerMachine
    aggregateMediaTypesPerMachine >> loadSqmPerPrintMode
    aggregateSqmPerPrintMode >> loadMediaTypesPerMachine
    aggregateTopTenPrintVolume >> loadSqmPerPrintMode
    aggregateMediaTypesPerMachine >> loadTopTenPrintVolume

    # cleanup
    loadMediaCategoryUsage >> cleanUp
    loadSqmPerPrintMode >> cleanUp
    loadInkUsage >> cleanUp
    loadTopTenPrintVolume >> cleanUp
    loadMediaTypesPerMachine >> cleanUp

    cleanUp >> error_collect

    # readImage