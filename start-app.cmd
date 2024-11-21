@echo off
echo This file Starts H2 Database in Server Modes which is separate from the Spring Boot Application.
echo.
echo Once the H2 Database Server is started, Run this application in Intellij or any other IDE.
echo.
echo.

REM Ensure the target directory is clean
REM call mvnw clean

REM Starts the H2 database in server mode in a separate window using the start command.
REM start java -cp "h2.jar" org.h2.tools.Server -tcp -tcpAllowOthers -webAllowOthers -ifNotExists -tcpPort 9092 -baseDir "path\to\database\dir"

REM Starts the H2 database in server mode in the same window
REM Run this command to start h2 sever before running spring application
java -cp "h2.jar" org.h2.tools.Server -tcp -tcpAllowOthers -webAllowOthers -ifNotExists

REM Run Flyway migrations
REM call mvnw flyway:migrate

REM Package and start the Spring Boot application
REM call mvnw spring-boot:run
