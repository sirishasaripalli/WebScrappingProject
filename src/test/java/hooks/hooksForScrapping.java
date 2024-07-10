package hooks;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;



import utilities.configReader;

public class hooksForScrapping extends baseClassForDriver {
	
	private static baseClassForDriver bs;
	
	@BeforeClass
	public static void before() throws Throwable {
		//Get browser Type from config properties file
		System.out.println("I AM HERE");
		configReader.configload();
		String browser = configReader.getBrowserType();
		System.out.println("I AM HERE");
		
		//Initialize driver from base class	
		bs = new baseClassForDriver();
		bs.initDriver(browser);
		
	}
	@AfterMethod
	public static void after() {
		
		bs.closeallDriver();
	}

}
