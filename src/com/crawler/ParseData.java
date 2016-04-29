package com.crawler;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class ParseData {

	
	

	
	static TreeSet<String> urlParsed=new TreeSet<String>();
	static HashSet<String> urlToParseSet=new LinkedHashSet<String>(1000);
	static TreeSet<String> urlWithKeyPhrase=new TreeSet<String>();
	
	static List<String> urlToParse=new LinkedList<String>();
	static List<Integer> depth=new LinkedList<Integer>();
	static List<String> tempUrl=new ArrayList<String>(25);
	static List<Integer> tempDepth=new ArrayList<Integer>(25);
	
	static boolean gotKeyPhrase=false;
	static boolean keyPharse=false;
	
	
}
