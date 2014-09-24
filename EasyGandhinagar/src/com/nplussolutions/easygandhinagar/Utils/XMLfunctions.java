package com.nplussolutions.easygandhinagar.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class XMLfunctions
{
	public final static Document XMLfromString(String xml)
	{
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try
        {
        	DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
	        is.setCharacterStream(new StringReader(xml));
	        doc = db.parse(is); 
	        
		} catch (Exception e)
		{
			System.out.println("XML parse error: " + e.getMessage());
			return null;
		}
        return doc;
	}
	public static StringBuilder inputStreamToString(InputStream is) 
	{
		String line = "";
		StringBuilder total = new StringBuilder();
   
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        try 
        {
        	while ((line = rd.readLine()) != null) 
        	{ 
        		total.append(line); 
        	}
        } 
        catch (IOException e) 
        {
        	e.printStackTrace();
        }
        return total;
	}
}