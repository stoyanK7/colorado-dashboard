# How to set up Apache Airflow

## Installing
1. Open Windows PowerShell with admin rights.

2. copy paste this:

	```
	Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
	```

This is in order to install Chocolatey, a package manager like npm.

3. close and reopen powershell with admin rights.

4. copy paste this:

    ```
	choco install make
    ```
This is to install make, which is needed to launch airflow.

5. repeat 3.

6. cd to the airflow folder of the git.

To start airflow type:
```
make start-airflow
```
To stop airflow type:
```
make stop-airflow
```	
## Using Airflow

1. Open Windows PowerShell with admin rights and cd to the airflow folder of the git.

2. To start airflow type:
```
make start-airflow
```	
3. Go to Localhost:8080

4. Login using the following:

    username: ```admin```
    
    password: ```admin```
    
To stop airflow type:
```
make stop-airflow
```	
## Accessing the database
1. Open the CLI for ```airflow-postgres-1``` by pressing the CLI button in Docker Desktop
2. Log in by typing:
```
psql -d airflow -U airflow
```
## Configuring data source
1. Extract printer data to a accesible folder.
2. Copy the folder path
3. Open docker compose
    a. In Scheduler:
    b. Under Volumes:
    c. Add: - C/path/to/data:/root/airflow/Data
4. After a pipeline fails wait until the cleanup passes then:
    a. Open CLI of airflow-scheduler in docker desktop
    b. cd airflow
    c. rm -r Snapshot
    d. rm -r LastReadData

