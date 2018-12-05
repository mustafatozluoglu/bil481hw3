package com.mycompany.app;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

public class App {
    
    public static void main(String[] args) {
        port(getHerokuAssignedPort());

        get("/", (req, res) -> "Hello, World");

        post("/compute", (req, res) -> {
            
        	MyHandler myHandler = new MyHandler();

            String firstname = req.queryParams("input1").replaceAll("\\s", "");
            
            String lastname = req.queryParams("input2").replaceAll("\\s", "");

            String result;
            
           result = App.search(firstname, lastname);
            
            Map map = new HashMap();
            map.put("result", result);
            return new ModelAndView(map, "compute.mustache");
        }, new MustacheTemplateEngine());

        get("/compute", (rq, rs) -> {
            Map map = new HashMap();
            map.put("result", "not computed yet!");
            return new ModelAndView(map, "compute.mustache");
        }, new MustacheTemplateEngine());
    }
    
    public static String search(String firstname, String lastname) throws ParserConfigurationException, SAXException, IOException {
    	String result = "";
    	
    	File xmlFile = new File("EEAS.xml");
    
    	SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
    
    	SAXParser saxParser = saxParserFactory.newSAXParser();
    	MyHandler myHandler = new MyHandler();
    	saxParser.parse(xmlFile, myHandler);
    	
    	ArrayList<Entry> list = myHandler.getList();
    	
    	for(Entry entry : list) {
    			result += entry;    		
    	}
    	
    	return result;   	
    
    }
    

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; // return default port if heroku-port isn't set (i.e. on localhost)
    }
}
