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
cd ImageCrawlerLauncher
call mvn install assembly:single
cd ..
copy ImageCrawlerLauncher\target\*.zip . /Y
