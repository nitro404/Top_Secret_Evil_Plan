@ECHO OFF
CLS

SET MASTER_PLANNER_DIR=MasterPlanner

ECHO.
ECHO Compiling Master Planner...
IF NOT EXIST "%MASTER_PLANNER_DIR%/bin" MKDIR "%MASTER_PLANNER_DIR%/bin"
IF EXIST "%MASTER_PLANNER_DIR%\bin\*.class" DEL "%MASTER_PLANNER_DIR%\bin\*.class"
CD "%MASTER_PLANNER_DIR%"
javac -cp lib/*.jar src/*.java -d bin
ECHO Done Compiling!
ECHO.

PAUSE
