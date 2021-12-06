from datetime import timedelta, datetime

from airflow import DAG
from airflow.operators.python_operator import PythonOperator

from tasks.load.LoadTasks import LoadTasks
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
    # READ
    readImage = PythonOperator(
        task_id='readImage',
        python_callable=ReadTasks.read_image
    )
    readMediaPrepare = PythonOperator(
        task_id='readMediaPrepare_TODO',
        python_callable=ReadTasks.read_media_prepare
    )
    readPrintCycle = PythonOperator(
        task_id='readPrintCycle_TODO',
        python_callable=ReadTasks.read_print_cycle
    )

    # CLEAN
    cleanImage = PythonOperator(
        task_id='cleanImage',
        python_callable=CleanTasks.clean_image
    )
    cleanMediaPrepare = PythonOperator(
        task_id='cleanMediaPrepare_TODO',
        python_callable=CleanTasks.clean_media_prepare
    )
    cleanPrintCycle = PythonOperator(
        task_id='cleanPrintCycle_TODO',
        python_callable=CleanTasks.clean_print_cycle
    )

    # PREPROCESS
    preprocessMediaCategoryUsage = PythonOperator(
        task_id='preprocessMediaCategoryUsage_TODO',
        python_callable=PreprocessTasks.preprocess_media_category_usage
    )
    preprocessSqmPerPrintMode = PythonOperator(
        task_id='preprocessSqmPerPrintMode_TODO',
        python_callable=PreprocessTasks.preprocess_sqm_per_print_mode
    )
    preprocessInkUsage = PythonOperator(
        task_id='preprocessInkUsage_TODO',
        python_callable=PreprocessTasks.preprocess_ink_usage
    )
    preprocessTopTenPrintVolume = PythonOperator(
        task_id='preprocessTopTenPrintVolume_TODO',
        python_callable=PreprocessTasks.preprocess_top_ten_print_volume
    )
    preprocessMediaTypesPerMachine = PythonOperator(
        task_id='preprocessMediaTypesPerMachine_TODO',
        python_callable=PreprocessTasks.preprocess_media_types_per_machine
    )

    # AGGREGATE
    aggregateMediaCategoryUsage = PythonOperator(
        task_id='aggregateMediaCategoryUsage',
        python_callable=AggregateTasks.aggregate_media_category_usage
    )
    aggregateSqmPerPrintMode = PythonOperator(
        task_id='aggregateSqmPerPrintMode_TODO',
        python_callable=AggregateTasks.aggregate_sqm_per_print_mode
    )
    aggregateInkUsage = PythonOperator(
        task_id='aggregateInkUsage_TODO',
        python_callable=AggregateTasks.aggregate_ink_usage
    )
    aggregateTopTenPrintVolume = PythonOperator(
        task_id='aggregateTopTenPrintVolume_TODO',
        python_callable=AggregateTasks.aggregate_top_ten_print_volume
    )
    aggregateMediaTypesPerMachine = PythonOperator(
        task_id='aggregateMediaTypesPerMachine_TODO',
        python_callable=AggregateTasks.aggregate_media_types_per_machine
    )

    #LOAD
    loadMediaCategoryUsage = PythonOperator(
        task_id='loadMediaCategoryUsage_TODO',
        python_callable=LoadTasks.load_media_category_usage
    )
    loadSqmPerPrintMode = PythonOperator(
        task_id='loadSqmPerPrintMode_TODO',
        python_callable=LoadTasks.load_sqm_per_print_mode
    )
    loadInkUsage = PythonOperator(
        task_id='loadInkUsage_TODO',
        python_callable=LoadTasks.load_ink_usage
    )
    loadTopTenPrintVolume = PythonOperator(
        task_id='loadTopTenPrintVolume_TODO',
        python_callable=LoadTasks.load_top_ten_print_volume
    )
    loadMediaTypesPerMachine = PythonOperator(
        task_id='loadMediaTypesPerMachine_TODO',
        python_callable=LoadTasks.load_media_types_per_machine
    )

    #CLEANUP
    cleanUp = PythonOperator(
        task_id='cleanUp',
        python_callable=CleanupTasks.cleanup
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
    aggregateSqmPerPrintMode >> loadSqmPerPrintMode
    aggregateInkUsage >> loadInkUsage
    aggregateTopTenPrintVolume >> loadTopTenPrintVolume
    aggregateMediaTypesPerMachine >> loadMediaTypesPerMachine

    # cleanup
    loadMediaCategoryUsage >> cleanUp
    loadSqmPerPrintMode >> cleanUp
    loadInkUsage >> cleanUp
    loadTopTenPrintVolume >> cleanUp
    loadMediaTypesPerMachine >> cleanUp

    # readImage