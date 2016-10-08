call mvn install
cd ..
cd ImageCrawlerLauncher
call mvn assembly:single
cd ..
copy ImageCrawlerLauncher\target\*.zip . /Y
