package hooks;

import java.time.Duration;

import org.openqa.selenium.WebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;


public class baseClassForDriver {
static WebDriver driver; 
	
	public WebDriver initDriver(String browser) { 
		
		if (browser.equalsIgnoreCase("firefox")) {
			driver =WebDriverManager.firefoxdriver().create();
			

		} else if (browser.equalsIgnoreCase("chrome")) {
			driver=WebDriverManager.chromedriver().create();
		

		} else if (browser.equalsIgnoreCase("safari")) {
			driver = WebDriverManager.safaridriver().create();
			

		} else if (browser.equalsIgnoreCase("edge")) {
			driver=WebDriverManager.edgedriver().create();
			
	
		}
		// Set Page load timeout
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.manage().window().maximize();
		

		return driver;
	}

	public static WebDriver getdriver() {
		return driver;
	}

	public void closeallDriver() {
		driver.close();
	}
}


