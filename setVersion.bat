@echo off
if [%1]==[] goto usage
mvn versions:set -DnewVersion=%1 -DartifactId=* -DgroupId=* -DoldVersion=* -DgenerateBackupPoms=false
goto :eof
:usage
@echo Usage setVersion <Version>
exit /B 1