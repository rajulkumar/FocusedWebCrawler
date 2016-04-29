#Set the jdk bin path of the system at <...> 
export PATH=$PATH:<Path of jdk bin on the system>
export CLASSPATH=$CLASSPATH:./src/com/crawler:./src:.

#Set the seed URL here
seed=https://en.wikipedia.org/wiki/Hugh_of_Saint-Cher
#Set the key phrase here
keyPhrase=index

javac ./src/com/crawler/HTTPConnect.java
javac ./src/com/crawler/ParseData.java
javac ./src/com/crawler/DocParseHandler.java
javac ./src/com/crawler/DocCrawler.java

java com/crawler/DocCrawler $seed $keyPhrase
