package org.apache.solr.handler.dataimport.scheduler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class SolrDataImportProperties {
	
	private static Properties props = new Properties();
	
	static {
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("dataimport.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getProperty(String key) {
		return props.getProperty(key);
	}

    public static boolean SYNC_ENABLED(){
    	String syncEnabled = props.getProperty("syncEnabled");
    	if (syncEnabled == null || syncEnabled.isEmpty()){
    		return false;
    	}
    	return syncEnabled.equals("true");
    }
    
    public static String[] SYNC_CORCES(){
    	String syncCores = props.getProperty("syncCores");
    	if (syncCores == null || syncCores.trim().isEmpty()){
    		return null;
    	}
    	return syncCores.trim().split(",");
    }
    
    public static String SERVER(){
    	String server = props.getProperty("server");
    	if (server == null || server.isEmpty()){
    		server = "localhost";
    	}
    	return server;
    }
    
    public static String PORT(){
    	String port = props.getProperty("port");
    	if (port == null || port.isEmpty()){
    		port = "8080";
    	}
    	return port;
    }
    
    public static String WEBAPP(){
    	String webapp = props.getProperty("webapp");
    	if (webapp == null || webapp.isEmpty()){
    		webapp = "solr";
    	}
    	return webapp;
    }
    
    public static int DELTA_INTERVAL(){
    	String deltaInterval = props.getProperty("deltaInterval");
    	if (deltaInterval == null || deltaInterval.isEmpty()){
    		return 100;
    	}
    	try {
			return Integer.parseInt(deltaInterval);
		} catch (NumberFormatException e) {
			return 100;
		}
    }
    
    public static String DELTA_PARAMS(){
    	String deltaParams = props.getProperty("deltaParams");
    	if (deltaParams == null || deltaParams.isEmpty())
    		deltaParams = "/dataimport?command=delta-import&clean=false&commit=true";
    	return deltaParams;
    }
    
    public static int FULL_INTERVAL(){
    	String fullInterval = props.getProperty("fullInterval");
    	if (fullInterval == null || fullInterval.isEmpty()){
    		return 0;
    	}
    	try {
			return Integer.parseInt(fullInterval);
		} catch (NumberFormatException e) {
			return 0;
		}
    }
    
    public static String FULL_PARAMS(){
    	String fullParams = props.getProperty("fullParams");
    	if (fullParams == null || fullParams.isEmpty())
    		fullParams = "/dataimport?command=full-import&clean=true&commit=true";
    	return fullParams;
    }
    
//    // ≤‚ ‘≤Œ ˝
//    public static void main(String[] args) {
//		System.out.println(  SolrDataImportProperties.FULL_PARAMS()   );
//	}

}