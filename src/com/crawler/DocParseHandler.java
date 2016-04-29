package com.crawler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.sun.webkit.dom.AttrImpl;

public class DocParseHandler extends DefaultHandler   {
	
	int depth=0;
	String keyPhrase=null;
	
	public DocParseHandler(int depth) {
		this.depth=depth;
	}
	
	public DocParseHandler(int depth,String keyPhrse) {
		this.depth=depth;
		this.keyPhrase=keyPhrse;
	}
	
	public void startElement (String uri, String localName,
            String qName, Attributes attributes)
            			throws SAXException
	{
		
		getReferenceLinks(attributes);
		
	}
	
	
	
	 public void characters (char ch[], int start, int length)
		        throws SAXException
	 {
		        String chars=new String(ch,start,length);
		        if(ParseData.keyPharse)
		        {
		        if(null!=chars && !"".equalsIgnoreCase(chars.trim()))
		        {
		        	if(!chars.contains("<") && !chars.contains(">") && !chars.contains("//>") && !chars.contains("<//") && !chars.contains("http") && !chars.contains("/index."))
		        	{
		        		if(chars.toLowerCase().contains(keyPhrase))
				        {
				        	System.out.println("got KP:::"+chars);
				        	ParseData.gotKeyPhrase=true;
				        }
		        	}
		        	
		        	
		        
		        }
		        }
	 }	
	 
	 private void getReferenceLinks(Attributes attributes)
	 {

			String attribute="";
			for(int i=0;i<attributes.getLength();i++)
			{
				if ("href".equalsIgnoreCase(attributes.getQName(i)))
				{
					attribute=attributes.getValue(i).trim();
					 
					if (attribute.startsWith("/wiki/")
							&& !(attribute.contains(":")) 
								&& !(attribute.contains("Main_Page")) 
									&& !attribute.contains("#"))
					{
						
						if(!updateLinksToList(attribute))
							break;
					}
					else if(attribute.startsWith("https://en.wikipedia.org/wiki/") 
							&& !(attribute.replace("https://en.wikipedia.org/wiki/", "")).contains(":")
								&& !(attribute.contains("Main_Page"))
									&& !attribute.contains("#"))
					{
						if(!updateLinksToList(attribute))
							break;
						
					}
					//System.out.println("hrefs::"+attribute);
				}
			}
		
	 }
	 
	 private boolean updateLinksToList(String attribute)
	 {
		   if((ParseData.keyPharse?ParseData.urlWithKeyPhrase.size():ParseData.urlParsed.size())<1000)
			{
					if(this.depth<5)
			 		{
			 			if(!ParseData.urlParsed.contains(attribute) && !ParseData.urlToParseSet.contains(attribute) && !ParseData.tempUrl.contains(attribute))
			 			{
//						skip=true;
//					
//					if(!skip)
//						ParseData.getUrlToParse(depth+1).add(attribute);
						//System.out.println("href added::"+attribute);
			 				ParseData.tempUrl.add(attribute);
			 				ParseData.tempDepth.add(depth+1);
			 			}
			 		}
			}
			else
			{
				//ParseData.clearUrlToParse();
				ParseData.urlToParse.clear();
				ParseData.depth.clear();
				ParseData.tempUrl.clear();
				ParseData.tempDepth.clear();

				return false;
			}
		 return true;
	 }
}
