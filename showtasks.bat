call runcrud.bat
if "%ERRORLEVEL%" == "0" goto runwebbrowser
echo.
goto fail

:runwebbrowser
start firefox.exe http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVEL%" == "0" goto end
goto fail

:fail
echo.
echo Houston! We have a problem!

:end
echo.
echo The task has been completed.
