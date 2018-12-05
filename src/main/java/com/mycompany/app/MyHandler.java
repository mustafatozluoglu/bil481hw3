package com.mycompany.app;


import org.eclipse.jetty.server.handler.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class MyHandler extends DefaultHandler {

	int id;
	String firstname;
	String lastname;
	
	boolean readId;
	boolean readFirstname;
	boolean readLastname;
	
	public String result() {
		return id + "\n" + firstname + "\n" + lastname;
	}
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
		if(qName.equals("ENTITY")) {
			readId = true;
			id = Integer.parseInt(attributes.getValue("Id"));
		}
		else if(qName.equalsIgnoreCase("firstname")) {
			readFirstname = true;
		}
		else if(qName.equalsIgnoreCase("lastname")) {
			readLastname = true;		
		}
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException{
		if(qName.equals("ENTITY")) {
			
		}			
	}

	public void characters(char ch[], int start, int length) throws SAXException {
		String data = new String(ch, start, length);
		
		if(readFirstname) {
			firstname = data;
			readFirstname = false;
		}
		else if(readLastname) {
			lastname = data;
			readLastname = false;
		}
		else if(readId) {
			readId = false;
		}
		
	}
}