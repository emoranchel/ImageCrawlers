@echo off
if [%1]==[] goto usage
mvn versions:set -DartifactId=* -DgroupId=* -DoldVersion=* -DgenerateBackupPoms=false -DnewVersion=%1
goto :eof
:usage
@echo Usage setVersion <Version>
exit /B 1