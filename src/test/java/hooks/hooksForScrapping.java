package hooks;


import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;




import utilities.configReader;

public class hooksForScrapping extends baseClassForDriver {
	
	private baseClassForDriver bs;

	private configReader configReader;
	public static WebDriver tlDriver;
	Properties prop=new Properties();
	
		

	  @BeforeTest
	  public void setUp() throws IOException {
		  configReader = new configReader();
		  prop = configReader.init_prop();
			String browserName = prop.getProperty("browser");
			System.out.println("Browser value is : "  + browserName);
			bs = new baseClassForDriver();
			//bs.init_driver(browserName);
			System.out.println(prop.getProperty("url"));
			tlDriver = bs.init_driver(browserName);
			tlDriver.get(prop.getProperty("url"));
	  }
 
		
		  @AfterTest 
		  public void quitBrowser()
		  { tlDriver.quit();
		  
		  }
		 


}

