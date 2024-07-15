package hooks;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;


import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import utilities.configReader;

public class hooksForScrapping extends baseClassForDriver {
	
	private baseClassForDriver bs;

	private configReader configReader;
	public static WebDriver tlDriver;
	Properties prop=new Properties();
	
	private final String url = "jdbc:postgresql://localhost:5432/COVID";
	private final String user = "postgres";
	private final String password = "postgres";
	
	

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
	  @BeforeTest
	  public void connect() {
		  System.out.println("database connection");
		  try(Connection connection = DriverManager.getConnection(url, user, password);){
			  if(connection !=null) {
				  System.out.println("connected to postgres server successfully");
			  }else {
				  System.out.println("failed to connect to the postgres server");
				  
			  } 
		  }
			  catch(SQLException e) {
			  e.printStackTrace();
		  }
	  }
	 
		
		  @AfterTest 
		  public void quitBrowser()
		  { tlDriver.quit();
		  
		  }
		 


}

