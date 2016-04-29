Focused Crawler
---------------
This is a web crawler that looks for a keyphrase in a web page statring from a seed URL and in it's child if the phrase is found in the page. This goes till getting 1000 unique relevant URLs with keyphrase or docs crawled upto a depth of 5.
If no key phrase is present then it crawls 1000 unique URLs starting from the seed upto a depth of 5.

Build
-----
Developed in Java using jdk1.8.0_05.
Compiled for jre7 and jre8.
No third party/external libraries used.
Works for https URL only.

Compile and Run
----------------
For windows:
1. Open execute.bat
2. Set the path of jdk bin on this system at the path variable.
3. Set the seed URL at the seed variable.
4. Set the optional key phrase at keyPharse variable.
5. Save and execute execute.bat
6. Response file is generated here as URLsParsed_<timestamp> and URLsWithKey_<timestamp>.

For linux/unix*:
1. Open execute.sh
2. Set the path of jdk bin on this system at the path variable.
3. Set the seed URL at the seed variable.
4. Set the optional key phrase at keyPharse variable.
5. Save and execute execute.sh
6. Response file is generated here as URLsParsed_<timestamp> and URLsWithKey_<timestamp>.

* not tested for linux/unix 

Testing
--------
1000 links crawled without keyphrase:(file: ./ListOfURLs/URLsParsed_no_key.txt)
total response::1025580501 = ~17
total crawl::42579151 = ~0.7
total time:2138447217 = ~35

1000 relevant links found with kp 'index':(file: ./ListOfURLs/URLsWithKey_index_1000.txt & ./ListOfURLs/URLsParsed_index_2403.txt)
total response::1006204631 = ~17
total crawl::13594487 = ~0.2
total time::3477230125 = ~57
URLs parsed/crawled::2403
URLs with key::1000

1000 links crawled with keyphrase 'index':(file: ./ListOfURLs/URLsWithKey_index_468.txt & ./ListOfURLs/URLsParsed_index_1000.txt)
total response::901108646 = ~15
total crawl::48760756 = ~ 0.8
total time:2009623860 = ~33
URLs parsed/crawled::1000
URLs with key::468

*times in microseconds and corresponding minutes
**response is the time taken to send a request and get a response from the web
***crawl is timetaken to parse the page after the response

References
-----------
1.JavaSE Documentation: https://docs.oracle.com/javase/7/docs/
2.Jenkov.com tutorials: http://tutorials.jenkov.com/
