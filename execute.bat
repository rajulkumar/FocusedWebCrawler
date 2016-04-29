:set jdk bin path of the system here
set path=%PATH%;C:\Program Files\Java\jdk1.8.0_05\bin
set CLASSPATH=%CLASSPATH%;./src/com/crawler;./src

:set seed url here
set seed=https://en.wikipedia.org/wiki/Hugh_of_Saint-Cher
:set key phrase here
set keyPhrase=index

javac ./src/com/crawler/HTTPConnect.java
javac ./src/com/crawler/ParseData.java
javac ./src/com/crawler/DocParseHandler.java
javac ./src/com/crawler/DocCrawler.java

java com/crawler/DocCrawler %seed% %keyPhrase%
