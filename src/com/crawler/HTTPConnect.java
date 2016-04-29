package com.crawler;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import java.util.Date;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.ws.WebEndpoint;

import org.xml.sax.InputSource;

import sun.net.www.protocol.https.HttpsURLConnectionImpl;;

public class HTTPConnect {
	public static String getDocOnHTTPsConnection(String urlToConnect) throws Exception
	{
		BufferedReader br=null;
		InputStreamReader ir=null;
		InputStream is=null;
		HttpsURLConnectionImpl conn=null;
		
		String line=null;
		String webDoc="";
		
		try
		{
		URL url=new URL(urlToConnect);
		conn=(HttpsURLConnectionImpl)url.openConnection();
		
		System.out.println("Conn response code::"+conn.getResponseCode());
		is=conn.getInputStream();
		ir=new InputStreamReader(is);
		br=new BufferedReader(ir);
		while((line=br.readLine())!=null)
		{
			webDoc+=line;
		}
		}
		finally
		{
			br.close();
			ir.close();
			is.close();
			conn.disconnect();
		}
		webDoc="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+webDoc;
		return webDoc;
	}
	
	public static void main(String args[]) throws Exception
	{
		//System.out.println(getDocOnHTTPsConnection("https://en.wikipedia.org/wiki/Secular_clergy"));
		crawlDocForLinks(new ByteArrayInputStream(getDocOnHTTPsConnection("https://en.wikipedia.org/wiki/Secular_clergy").getBytes("UTF-8")), 1, "");
		System.out.println("End:::");
	}
	
	public static void crawlDocForLinks(InputStream docStream,int depth,String keyPhrase) throws Exception
	{
		SAXParserFactory.newInstance().newSAXParser().parse(docStream, new DocParseHandler(depth,keyPhrase));
	}
}
