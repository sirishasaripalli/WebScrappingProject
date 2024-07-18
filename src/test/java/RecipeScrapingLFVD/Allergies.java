package RecipeScrapingLFVD;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import hooks.hooksForScrapping;
import utilities.WriteExcel;
import utilities.writeToDatabase;

public class Allergies extends hooksForScrapping {
	
	@Test
	public void extractRecipe() throws InterruptedException, IOException {
		List<String> allergies = Arrays.asList(new String[] { "Milk","Soy","Egg","Sesame","Peanuts","walnut","almond","hazelnut","pecan","cashew","pistachio","Shellfish","Seafood"});
		List<String> eliminators = Arrays.asList(new String[] { "Fish","Sausage","ham","salami","bacon","milk","cheese","yogurt","butter","Ice cream","egg","prawn","Oil","olive oil",
				"coconut oil","soybean oil","corn oil","safflower oil","sunflower oil","rapeseed oil","peanut oil","cottonseed oil","canola oil","mustard oil","cereals","tinned vegetable",
				"bread","maida","atta","sooji","poha","cornflake","cornflour","pasta","White rice","pastry","cakes","biscuit","soy","soy milk","white miso paste","soysauce","soy curls",
				"edamame","soy yogurt","soy nut","tofu","pies","Chip","cracker","potatoe","sugar","jaggery","glucose","fructose","corn syrup","cane solid","aspartame","cane solid","maltose","dextrose",
				"sorbitol","mannitol","xylitol","maltodextrin","molasses","brown rice syrup","Splenda","nutra sweet","stevia","barley malt","pork","Meat","poultry" });
		
		int rowCounter = 1;
		// run in a loop for all recipe in a page

		tlDriver.navigate().to("https://www.tarladalal.com/recipes-for-lebanese-vegetarian-lebanese-recipes-115");
			int lastPage = 0;
			try {
				lastPage = Integer
						.parseInt(tlDriver.findElement(By.xpath("//div/a[@class= 'respglink'][last()]")).getText());
				System.out.println(lastPage + "is the last page");
			} catch (Exception e) {
				// do nothing or log exception
			}
			if (0 != lastPage) {
				for (int j = 1; j <= lastPage; j++) {
				
					tlDriver.navigate().to("https://www.tarladalal.com/recipes-for-lebanese-vegetarian-lebanese-recipes-115"
							+ "?pageindex=" + j);
					System.out.println("I am in page: " + j);
					List<WebElement> recipeCardElements = tlDriver
							.findElements(By.xpath("//article[@class='rcc_recipecard']"));
					//System.out.println(recipeCardElements.size() + "is the sizeee");
					//System.out.println(recipeCardElements);
					List<String> recipeUrls = new ArrayList<>();
					Map<String, String> recipeIdUrls = new HashMap<>();
					
					// Looping through all recipes Web elements and generating a navigation URL
					recipeCardElements.stream().forEach(recipeCardElement -> {
						recipeUrls.add("https://www.tarladalal.com/" + recipeCardElement
								.findElement(By.xpath("//span[@class='rcc_recipename']/a")).getDomAttribute("href"));
//						// example: recipeIdUrls.put("id","url");

						recipeIdUrls.put(recipeCardElement.getDomAttribute("id").replace("rcp", ""),
								"https://www.tarladalal.com/"
						+ recipeCardElement.findElement(By.tagName("a")).getDomAttribute("href").replace("calories-for-", "").concat("r").replace("rr", "r"));
						});
					//System.out.println(recipeUrls);
					System.out.println(recipeIdUrls.size() + "is the size");
					//System.out.println(recipeIdUrls);
					for (Map.Entry<String, String> recipeIdUrlEntry : recipeIdUrls.entrySet()) {
						//System.out.println("Inside this");
						String recipeUrl = recipeIdUrlEntry.getValue();
						String recipeId = recipeIdUrlEntry.getKey();
						//System.out.println(recipeUrl + "is the recipe URL");
						
						if (recipeUrl.contains("Add_Photo"))
						{
							System.out.println("Skipped URL: " + recipeUrl);
						}
						else
						{
						try {
						tlDriver.navigate().to(recipeUrl);
						}
						catch (Exception e)
						{
				            // Handle generic exceptions
				            if (e.getMessage().contains("Server Error in '/' Application")) {
				                System.out.println("Server Error in '/' Application occurred.");
				                e.printStackTrace();
				                writeToDatabase.insertData(1, "allergies_lebenese");
				                
				            } else {
				                System.out.println("An unexpected error occurred: " + e.getMessage());
				                e.printStackTrace();
				            }
						}
						tlDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

						if (isEliminated(eliminators, allergies)) {
							
							System.out.println(recipeUrl + " is eliminated");
						} else {
							System.out.println(recipeUrl + " is processed");
							WriteExcel writeOutput = new WriteExcel();
							// Recipe id
							try {
								writeOutput.setCellData("AllergiesData", rowCounter, 0, recipeId);
							} catch (Exception e) {

							}

							// Recipe Name
							try {
								WebElement recipeTitle = tlDriver
										.findElement(By.xpath("//span[@id= 'ctl00_cntrightpanel_lblRecipeName']"));
								//System.out.print(recipeTitle.getText());
								writeOutput.setCellData("AllergiesData", rowCounter, 1, recipeTitle.getText());

								
							} catch (Exception e) {

							}
							try {
								WebElement recipeCategory = tlDriver.findElement(By.xpath(
										"//span[@itemprop= 'description']/*[contains (text(), 'Breakfast') or contains (text(), 'lunch') or contains (text(), 'drink') or contains (text(), 'snack') or contains (text(), 'dinner') or contains (text(), 'dish')]"));
								//System.out.print(recipeCategory.getText());
								writeOutput.setCellData("AllergiesData", rowCounter, 2, recipeCategory.getText());

							} catch (Exception e) {

							}
							try {
								WebElement foodCategory = tlDriver
										.findElement(By.xpath("//div[@class= 'breadcrumb']/span[5]"));
								//System.out.print(foodCategory.getText());
								writeOutput.setCellData("AllergiesData", rowCounter, 3, foodCategory.getText());

							} catch (Exception e) {

							}

							try {
								WebElement nameOfIngredients = tlDriver.findElement(By.xpath("//div[@id= 'rcpinglist']"));
								//System.out.print(nameOfIngredients.getText());
								writeOutput.setCellData("AllergiesData", rowCounter, 4, nameOfIngredients.getText());

							} catch (Exception e) {

							}

							try {
								WebElement preparationTime = tlDriver
										.findElement(By.xpath("//p/time[@itemprop= 'prepTime']"));
								//System.out.print(preparationTime.getText());
								writeOutput.setCellData("AllergiesData", rowCounter, 5, preparationTime.getText());

							} catch (Exception e) {

							}

							try {
								WebElement cookTime = tlDriver.findElement(By.xpath("//p/time[@itemprop= 'cookTime']"));
								//System.out.print(cookTime.getText());
								writeOutput.setCellData("AllergiesData", rowCounter, 6, cookTime.getText());

							} catch (Exception e) {

							}
//Tag, Number of servings, Cusinie Category, Recipe Description

							try {
								WebElement tagsTags = tlDriver
										.findElement(By.xpath("//div[@class= 'tags']"));
								//System.out.print(tagsTags.getText());
								writeOutput.setCellData("AllergiesData", rowCounter, 7, tagsTags.getText());

							} catch (Exception e) {

							}
							try {
								WebElement numberOfServings = tlDriver
										.findElement(By.xpath("//span[@id= 'ctl00_cntrightpanel_lblServes']"));
								//System.out.print(numberOfServings.getText());
								writeOutput.setCellData("AllergiesData", rowCounter, 8, numberOfServings.getText());

							} catch (Exception e) {

							}
							try {
								WebElement cusineCategory = tlDriver
										.findElement(By.xpath("//div[@class= 'breadcrumb']/span[5]"));
								//System.out.print(cusineCategory.getText());
								writeOutput.setCellData("AllergiesData", rowCounter, 9, cusineCategory.getText());

							} catch (Exception e) {

							}
							try {
								WebElement recipeDescription = tlDriver
										.findElement(By.xpath("//span[@id='ctl00_cntrightpanel_lblrecipeNameH2']"));
								//System.out.print(recipeDescription.getText());
								writeOutput.setCellData("AllergiesData", rowCounter, 10, recipeDescription.getText());

							} catch (Exception e) {

							}
							
							try {
								WebElement prepMethod = tlDriver
										.findElement(By.xpath("//div[@id= 'ctl00_cntrightpanel_pnlRcpMethod']"));
								//System.out.print(prepMethod.getText());
								writeOutput.setCellData("AllergiesData", rowCounter, 11, prepMethod.getText());

							} catch (Exception e) {

							}
							try {
								WebElement nutrients = tlDriver.findElement(By.xpath("//table[@id= 'rcpnutrients']"));
								//System.out.print(nutrients.getText());
								writeOutput.setCellData("AllergiesData", rowCounter, 12, nutrients.getText());

							} catch (Exception e) {

							}
							try {
								//System.out.print(recipeUrl);
								writeOutput.setCellData("AllergiesData", rowCounter, 13, recipeUrl);
							} catch (Exception e) {

							}
							rowCounter++;
						}

					}//Ending IF for check of skip url
					}
				}
				writeToDatabase.insertData(1, "allergies_lebenese");
			}
//		}
	}

	private boolean isEliminated(List<String> eliminators, List<String> allergies) {
		AtomicBoolean isEliminatorPresent = new AtomicBoolean(false);

		eliminators.parallelStream().forEach(eliminator -> {
			try {

				WebElement methodWebElement = tlDriver.findElement(By.xpath("//div[@id= 'rcpinglist']"));
				//WebElement methodWebElement = tlDriver.findElement(By.xpath("//div[@id='recipe_small_steps']"));
				String method = methodWebElement.getText();
				if (null != method && null != eliminator && method.toLowerCase().contains(eliminator.toLowerCase())) {
					System.out.println("Eliminated due to eliminator: " + eliminator);
					isEliminatorPresent.set(true);
				}
			} catch (Exception e) {
				System.out.print("No Such Element " + e.getLocalizedMessage());
			}
		});
		allergies.parallelStream().forEach(allergie -> {
			try {

				WebElement methodWebElement = tlDriver.findElement(By.xpath("//div[@id= 'rcpinglist']"));
				//WebElement methodWebElement = tlDriver.findElement(By.xpath("//div[@id='recipe_small_steps']"));
				String method = methodWebElement.getText();
				if (null != method && null != allergie && method.toLowerCase().contains(allergie.toLowerCase())) {
					System.out.println("Eliminated due to allergy: " + allergie);
					isEliminatorPresent.set(true);
				}
			} catch (Exception e) {
				System.out.print("No Such Element " + e.getLocalizedMessage());
			}
		});
		return isEliminatorPresent.get();
	}
}


