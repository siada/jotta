@echo off
cd src
:c
cls
echo Compiling JSON lib
javac -d ../bin -cp . org\json\*.java
echo Compiling Jotta
javac -d ../bin -cp .;../data/last.fm-bindings.jar;../bin/org/json com\pie\jotta\*.java
pause
goto c