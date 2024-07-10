package hooks;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import utilities.configReader;

public class hooksForScrapping extends baseClassForDriver {
	
	private baseClassForDriver bs;
	private WebDriver driver;
	private configReader configReader;
	Properties prop;
	
	@BeforeClass 
	public void getProperty() {
	  configReader = new configReader(); 
	  prop = configReader.init_prop(); 
	  }
	  
	  @BeforeClass 
	  public void launchBrowser() { 
		  String browserName = prop.getProperty("browser"); 
		  bs = new baseClassForDriver(); 
		  driver = bs.init_driver(browserName); 
		  }
	 
//	@AfterClass
//	public void quitBrowser() {
//		driver.quit();
//	}

}
