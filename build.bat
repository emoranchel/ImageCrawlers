cd ImageCrawlerCore
call mvn install
cd ..
cd ImageCrawlerSwingView
call mvn install
cd ..
cd ImageCrawlerFxView
call mvn install
cd ..
cd konachanator
call mvn install
cd ..
cd sankakunator
call mvn install
cd ..
cd konachanatorSwingLauncher
call mvn package assembly:single
cd ..
cd sankakunatorSwingLauncher
call mvn package assembly:single
cd ..
cd konachanatorFxLauncher
call mvn package assembly:single
cd ..
cd sankakunatorFxLauncher
call mvn package assembly:single
cd ..
copy konachanatorSwingLauncher\target\*.zip . /Y
copy sankakunatorSwingLauncher\target\*.zip . /Y
rem copy konachanatorFxLauncher\target\*.zip . /Y
rem copy sankakunatorFxLauncher\target\*.zip . /Y
