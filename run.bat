@echo off
REM Kompilasi dan jalankan Sistem Kasir Toko Buket
cd /d "%~dp0"
if not exist out mkdir out
javac -d out src\*.java
if errorlevel 1 (
    echo Kompilasi gagal. Pastikan JDK sudah terpasang.
    pause
    exit /b 1
)
java -cp out Main
pause
