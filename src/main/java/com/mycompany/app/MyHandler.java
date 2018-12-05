package com.mycompany.app;



import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class MyHandler extends DefaultHandler {

	private ArrayList<Entry> list;
	private Entry entry;
	private String sb = null;
	
	String id;
	String firstname;
	String lastname;
	
	boolean readId;
	boolean readFirstname;
	boolean readLastname;
	
	public String result() {
		return id + "\n" + firstname + "\n" + lastname;
	}
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
		if(qName.equals("ENTITY")) {
			readId = true;
			id = attributes.getValue("Id");
			
			entry = new Entry();
			entry.setId(id);
		}
		else if(qName.equalsIgnoreCase("FIRSTNAME")) {
			readFirstname = true;
		}
		else if(qName.equalsIgnoreCase("LASTNAME")) {
			readLastname = true;		
		}
		sb = new String();
	}
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException{
	
		
		if(readFirstname) {
			entry.setFirstname(sb);
			readFirstname = false;
		}
		if(readLastname) {
			entry.setLastname(sb);
			readLastname = false;
		}
		if(qName.equals("ENTITY")) {
			list.add(entry);
		}
	}
	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		sb += new String(ch, start, length);
			
	}
	public ArrayList<Entry> getList() {
		return list;
	}
}