package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class configReader {
	public static Properties prop;
	public static String proppath= "./src/test/resources/config/config.properties";
	
	public static void configload()throws Throwable {
	
	try {
		FileInputStream ip= new FileInputStream(proppath);
		prop=new Properties();
		try {
			prop.load(ip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		throw new RuntimeException("config.properties not found at " + proppath);
	}
	
	}
	
	public static String getBrowserType() {
		String browser = prop.getProperty("browser");
		if (browser != null)
			return browser;
		else
			throw new RuntimeException("browser not specified in the config.properties file.");
	}
	
	public static String getApplicationUrl() {
		System.out.println("My prop" + prop);
		String url = prop.getProperty("url");
		if (url != null)
			return url;
		else
			throw new RuntimeException("url not specified in the config.properties file.");
	}
}
