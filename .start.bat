@REM Batch script to open API in IntelliJ, Frontend in VS Code & run React
start cmd /k idea ./API &
cd ./Frontend &
start cmd /k npm run start &
code . 