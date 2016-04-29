package com.crawler;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.xml.parsers.SAXParserFactory;

public class DocCrawler {

	
	public static void main(String arg[]) throws Exception
	{
		//String seedUrl="https://en.wikipedia.org/wiki/Hugh_of_Saint-Cher";
		//new DocCrawler().startCrawl(seedUrl, "concordance");
		if(arg.length<2)
		{
			new DocCrawler().startCrawl(arg[0], "");
		}
		else
		{
			new DocCrawler().startCrawl(arg[0], arg[1]);
		}
		
		System.out.println("*******Crawl Ends:::*********");
		
	}
	
	public static void crawlDocForLinks(InputStream docStream,int depth,String keyPhrase) throws Exception
	{
		SAXParserFactory.newInstance().newSAXParser().parse(docStream, new DocParseHandler(depth,keyPhrase));
	}

	
	public void startCrawl(String seedUrl, String keyPhrase) throws Exception
	{
		String webDoc =HTTPConnect.getDocOnHTTPsConnection(seedUrl);
	
		long start=0l,end=0l,cBegin=0l,cEnd=0l;
		long respBegin=0l,respEnd=0l,totalResp=0l,totalCrawl=0l;
		String urlToBeParsed="",urlFromList="";
		if(null!=keyPhrase && !"".equalsIgnoreCase(keyPhrase.trim()))
		{
			ParseData.keyPharse=true;
		}
		
		System.out.println("Start::"+(start=System.nanoTime()));
		ParseData.urlParsed.add(seedUrl.trim());
		
		
		crawlDocForLinks(new ByteArrayInputStream(webDoc.getBytes("UTF-8")),1,keyPhrase);
		ParseData.urlToParse.addAll(ParseData.tempUrl);
		ParseData.depth.addAll(ParseData.tempDepth);
		
		if(ParseData.gotKeyPhrase && ParseData.urlWithKeyPhrase.size()<1000)
		{
			ParseData.urlWithKeyPhrase.add(seedUrl);
			ParseData.gotKeyPhrase=false;
		}

		for(String doc:ParseData.urlParsed)
		{
			
			System.out.println("URLs:::"+doc);
			
		}
		
		System.out.println("urlToParse:::"+ParseData.urlToParse.size());
		while (!ParseData.urlToParse.isEmpty())
		{
			Thread.sleep(1000);
			urlToBeParsed="";
			urlFromList="";

			System.out.println("urltobepar::"+(urlFromList=ParseData.urlToParse.get(0)));
			if(urlFromList.startsWith("/wiki/") && !urlFromList.contains("https://en.wikipedia.org"))
			{
				System.out.println("add url");
				urlToBeParsed="https://en.wikipedia.org"+urlFromList;
			}
			else
			{
				urlToBeParsed=urlFromList;
			}
			
//			System.out.println("Current url being parsed:::"+urlToBeParsed+"::"+ParseData.depth.get(0));
			System.out.println("Request sent::"+(respBegin=System.nanoTime()));
			webDoc=HTTPConnect.getDocOnHTTPsConnection(urlToBeParsed);
			System.out.println("Response received::"+(respEnd=System.nanoTime()));
			System.out.println("Fetch time::"+(respEnd-respBegin)/1000);
			totalResp=totalResp+((respEnd-respBegin)/1000);
			ParseData.urlParsed.add(urlFromList.trim());
			
			
			try{
				System.out.println("Crawl begins::"+(cBegin=System.nanoTime()));
				crawlDocForLinks(new ByteArrayInputStream(webDoc.getBytes("UTF-8")),ParseData.depth.get(0),keyPhrase);
				if(ParseData.keyPharse )
				{
					if(ParseData.gotKeyPhrase)
					{
						System.out.println("Adding temp urls::"+ParseData.tempUrl.size());
						ParseData.urlWithKeyPhrase.add(urlToBeParsed);
						ParseData.urlToParse.addAll(ParseData.tempUrl);
						ParseData.depth.addAll(ParseData.tempDepth);
						ParseData.urlToParseSet.addAll(ParseData.tempUrl);
						ParseData.gotKeyPhrase=false;
					}
				}
				else
				{
					System.out.println("Adding to temp without keyphrase::"+ParseData.tempUrl.size());
					ParseData.urlToParse.addAll(ParseData.tempUrl);
					ParseData.depth.addAll(ParseData.tempDepth);
					ParseData.urlToParseSet.addAll(ParseData.tempUrl);
				}
				
				ParseData.tempDepth.clear();
				ParseData.tempUrl.clear();
				
				System.out.println("Temp cleared::");
				
			}
			catch(Exception e)
			{
				System.out.println("Exception occoured");
				
				ParseData.urlParsed.remove(urlFromList.trim());
				ParseData.tempDepth.clear();
				ParseData.tempUrl.clear();
				ParseData.gotKeyPhrase=false;
				System.out.println("Exception data cleared::");
			}
			if(!ParseData.urlToParse.isEmpty())
			{
				ParseData.urlToParseSet.remove(urlFromList);
				ParseData.urlToParse.remove(0);
				ParseData.depth.remove(0);
			}
			System.out.println("Crawl ends::"+(cEnd=System.nanoTime()));
			System.out.println("Crawl time::"+(cEnd-cBegin)/1000+":::"+webDoc.length());
			totalCrawl=totalCrawl+((cEnd-cBegin)/1000);
			System.out.println("********************************************");
			System.out.println("CurrentLinks:::"+ParseData.urlToParse.size());
			System.out.println("URLs parsed::"+ParseData.urlParsed.size());
			System.out.println("URLs with key::"+ParseData.urlWithKeyPhrase.size());
		}
		System.out.println("End::"+(end=System.nanoTime()));

		System.out.println("********************************************");
		
		StringBuffer urlParseBuffer=new StringBuffer();
		for(String doc:ParseData.urlParsed)
		{
			
			System.out.println("URLs:::"+doc);
			urlParseBuffer.append(doc);
			urlParseBuffer.append(System.getProperty("line.separator"));
			
		}
		//System.out.println("buffer::"+urlParseBuffer);
		writetoFile("URLsParsed", urlParseBuffer);
		System.out.println("********************************************");
		
		StringBuffer urlWithKey=new StringBuffer();
		for(String doc:ParseData.urlWithKeyPhrase)
		{
			
			System.out.println("URLs with key:::"+doc);
			urlWithKey.append(doc);
			urlWithKey.append(System.getProperty("line.separator"));
			
		}
		if(ParseData.keyPharse)
			writetoFile("URLsWithKey", urlWithKey);
		
		System.out.println("*************************************************");
		System.out.println("total response::"+totalResp);
		System.out.println("total crawl::"+totalCrawl);
		System.out.println("total time:"+(end-start)/1000);
		
		
	}
	
	private static void writetoFile(String fileName,StringBuffer data) throws Exception
	{
		FileOutputStream fos=null;
		BufferedWriter buffWriter=null;
		try
		{
		String fileNameWithTime=fileName+"_"+new SimpleDateFormat("MM-dd-yy_HH-mm-SS").format(new Date())+".txt";	
		new File(fileNameWithTime).createNewFile();
//		fos=new FileOutputStream(new File(fileNameWithTime));
		buffWriter=new BufferedWriter(new FileWriter(fileNameWithTime));
		buffWriter.write(data.toString());
		}
		finally
		{
			//fos.close();
			buffWriter.flush();
			buffWriter.close();
		}
	
	}
}
