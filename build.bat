call mvn install
cd ImageCrawlerLauncher
call mvn assembly:single
cd ..
copy ImageCrawlerLauncher\target\*.zip . /Y
