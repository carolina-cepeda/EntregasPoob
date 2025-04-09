@echo off
cd /d %~dp0

echo Compilando archivos...
javac -cp ".;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" -d bin src/domain/*.java src/presentation/*.java

echo Ejecutando GUI...
java -cp bin presentation.Plan15GUI

pause