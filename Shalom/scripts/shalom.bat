@echo off

REM  Copyright (c) 2013, Yatta Tech and/or its affiliates. All rights reserved.
REM  YATTATECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.

if "%OS%"=="Windows_NT" @setlocal
if "%OS%"=="WINNT" @setlocal

if "%JAVA_HOME%" == "" goto noJavaHome
if not exist "%JAVA_HOME%\bin\java.exe" goto noJavaHome
goto callShalomSeminary

:noJavaHome
echo No java JDK/JRE installed, please install it and try again.
goto end

:callShalomSeminary
echo Starting shalom seminary application.
java -jar Shalom.jar
goto end

:end

