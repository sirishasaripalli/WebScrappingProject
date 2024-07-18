package hooks;

//import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;


public class baseClassForDriver {
	public WebDriver driver;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
	
	public WebDriver init_driver(String browser) {
		
		if(browser.equals("chrome")) {
			
			WebDriverManager.chromedriver().setup();
			ChromeOptions chromeoptions=new ChromeOptions();
			chromeoptions.addArguments("--headless");
			tlDriver.set(new ChromeDriver(chromeoptions));
		}
		else if(browser.equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			tlDriver.set(new FirefoxDriver());
		}
		else if(browser.equals("safari")) {	
			tlDriver.set(new SafariDriver());
			
		}
		else if(browser.equals("edge")) {	
			tlDriver.set(new EdgeDriver());
			
		}		else {
			System.out.println("please pass the correct browser value:"+ browser);
		}
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		return getDriver();
		
	}
		public static synchronized WebDriver getDriver() {
			return tlDriver.get();
		}
}


