cd ImageCrawlerCore
call mvn install
cd ..
cd ImageCrawlerSwingView
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
cd sankakunatorSwingLaunch
call mvn package assembly:single
cd ..
copy konachanatorSwingLauncher\target\*.zip . /Y
copy sankakunatorSwingLaunch\target\*.zip . /Y