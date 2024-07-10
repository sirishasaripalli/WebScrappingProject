package diabetesData;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import hooks.baseClassForDriver;
import utilities.configReader;

public class Diabetes {

	public static WebDriver driver=baseClassForDriver.getdriver();
	//system.out.println("I am Driver");
	
	static String URL=configReader.getApplicationUrl();
		
	private static baseClassForDriver bs;
	
	
	@Test
	public void test() {
		System.out.println("ssss");
		for (int i = 1; i <= 2; ++i) {
			//url = "https://www.tarladalal.com/RecipeAtoZ.aspx?pageindex=";
			URL = URL + i;
			System.out.print(URL);
			System.out.print("\n");
	}
		

	}
}


