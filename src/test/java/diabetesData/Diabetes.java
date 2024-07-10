package diabetesData;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;


import utilities.configReader;
import hooks.baseClassForDriver;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class Diabetes {
		
	public static WebDriver tlDriver;
	private static baseClassForDriver bs;
	Properties prop=new Properties();
	configReader cRead;
	
		
	//@BeforeMethod
		//@Parameters("browserName")
		@Test
		public void setUp() throws IOException {
			cRead = new configReader();
			prop = cRead.init_prop();
			String browserName = prop.getProperty("browser");
			System.out.println("Browser value is : "  + browserName);
			bs = new baseClassForDriver();
			//bs.init_driver(browserName);
			System.out.println(prop.getProperty("url"));
			tlDriver = bs.init_driver(browserName);
			tlDriver.get(prop.getProperty("url"));
			tlDriver.findElement(By.linkText("Recipe A To Z")).click();
			
			
				}
		
		

}


