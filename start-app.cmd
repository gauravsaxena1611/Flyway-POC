@echo off
REM Ensure the target directory is clean
call mvnw clean

REM Start H2 database in server mode
start java -cp "h2.jar" org.h2.tools.Server -tcp -tcpAllowOthers -webAllowOthers -ifNotExists -tcpPort 9092 -baseDir "path\to\database\dir"
REM Run below command in terminal to start h2 sever before running spring application
java -cp "h2.jar" org.h2.tools.Server -tcp -tcpAllowOthers -webAllowOthers -ifNotExists

REM Run Flyway migrations
REM call mvnw flyway:migrate

REM Package and start the Spring Boot application
call mvnw spring-boot:run
