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

public class Allergies_Indian_veg extends hooksForScrapping {
	
	@Test
	public void extractRecipe() throws InterruptedException, IOException {
		List<String> allergies = Arrays.asList(new String[] { "Milk","Soy","Egg","Sesame","Peanuts","walnut","almond","hazelnut","pecan","cashew","pistachio","Shellfish","Seafood",});

		List<String> avoidedRecipes = Arrays.asList(new String[] {"microwave", "ready meal", "fried", "cracker"});
		int rowCounter = 1;

		tlDriver.navigate().to("https://www.tarladalal.com/recipes-for-indian-veg-recipes-2");
			int lastPage = 0;
			try {
				lastPage = Integer
						.parseInt(tlDriver.findElement(By.xpath("//div/a[@class= 'respglink'][last()]")).getText());
				System.out.println(lastPage + "is the last page");
			} catch (Exception e) {
				// do nothing or log exception
			}
			if (0 != lastPage) {
				for (int j = 1; j <= 3; j++) {
				
					tlDriver.navigate().to("https://www.tarladalal.com/recipes-for-indian-veg-recipes-2"
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
						//System.out.println(recipeUrl + " is the recipe URL");
						tlDriver.navigate().to(recipeUrl);
						tlDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

						if (isEliminated(allergies, avoidedRecipes)) {
							//Exclude using the 
							// tlDriver.navigate().to("//div/a[text()= 'Recipe A To Z']");
							System.out.println(recipeUrl + " is eliminated");
						} else {
							System.out.println(recipeUrl + " is processed");
							WriteExcel writeOutput = new WriteExcel();
							// Recipe id
							try {
								writeOutput.setCellData("AllergiesVeg", rowCounter, 0, recipeId);
							} catch (Exception e) {

							}

							// Recipe Name
							try {
								WebElement recipeTitle = tlDriver
										.findElement(By.xpath("//span[@id= 'ctl00_cntrightpanel_lblRecipeName']"));
								//System.out.print(recipeTitle.getText());
								writeOutput.setCellData("AllergiesVeg", rowCounter, 1, recipeTitle.getText());
								
								
							} catch (Exception e) {

							}
							try {
								WebElement recipeCategory = tlDriver.findElement(By.xpath(
										"//span[@itemprop= 'description']/*[contains (text(), 'Breakfast') or contains (text(), 'lunch') or contains (text(), 'drink') or contains (text(), 'yoghurt') or contains (text(), 'dinner') or contains (text(), 'dish')]"));
								//System.out.print(recipeCategory.getText());
								writeOutput.setCellData("AllergiesVeg", rowCounter, 2, recipeCategory.getText());

							} catch (Exception e) {

							}
							try {
								WebElement foodCategory = tlDriver
										.findElement(By.xpath("//div[@class= 'breadcrumb']/span[5]"));
								//System.out.print(foodCategory.getText());
								writeOutput.setCellData("AllergiesVeg", rowCounter, 3, foodCategory.getText());

							} catch (Exception e) {

							}

							try {
								WebElement nameOfIngredients = tlDriver.findElement(By.xpath("//div[@id= 'rcpinglist']"));
								//System.out.print(nameOfIngredients.getText());
								writeOutput.setCellData("AllergiesVeg", rowCounter, 4, nameOfIngredients.getText());

							} catch (Exception e) {

							}

							try {
								WebElement preparationTime = tlDriver
										.findElement(By.xpath("//p/time[@itemprop= 'prepTime']"));
								//System.out.print(preparationTime.getText());
								writeOutput.setCellData("AllergiesVeg", rowCounter, 5, preparationTime.getText());

							} catch (Exception e) {

							}

							try {
								WebElement cookTime = tlDriver.findElement(By.xpath("//p/time[@itemprop= 'cookTime']"));
								//System.out.print(cookTime.getText());
								writeOutput.setCellData("AllergiesVeg", rowCounter, 6, cookTime.getText());

							} catch (Exception e) {

							}
//Tag, Number of servings, Cusinie Category, Recipe Description

							try {
								WebElement tagsTags = tlDriver
										.findElement(By.xpath("//div[@class= 'tags']"));
								//System.out.print(tagsTags.getText());
								writeOutput.setCellData("AllergiesVeg", rowCounter, 7, tagsTags.getText());

							} catch (Exception e) {

							}
							try {
								WebElement numberOfServings = tlDriver
										.findElement(By.xpath("//span[@id= 'ctl00_cntrightpanel_lblServes']"));
								//System.out.print(numberOfServings.getText());
								writeOutput.setCellData("AllergiesVeg", rowCounter, 8, numberOfServings.getText());

							} catch (Exception e) {

							}
							try {
								WebElement cusineCategory = tlDriver
										//.findElement(By.xpath("//div[@class= 'tags']/a[last()]"));
										.findElement(By.xpath("//div[@class= 'breadcrumb']/span[7]"));
								//System.out.print(cusineCategory.getText());
								writeOutput.setCellData("AllergiesVeg", rowCounter, 9, cusineCategory.getText());

							} catch (Exception e) {

							}
							try {
								WebElement recipeDescription = tlDriver
										.findElement(By.xpath("//span[@id='ctl00_cntrightpanel_lblrecipeNameH2']"));
								//System.out.print(recipeDescription.getText());
								writeOutput.setCellData("AllergiesVeg", rowCounter, 10, recipeDescription.getText());

							} catch (Exception e) {

							}
							
							try {
								WebElement prepMethod = tlDriver
										.findElement(By.xpath("//div[@id= 'ctl00_cntrightpanel_pnlRcpMethod']"));
								//System.out.print(prepMethod.getText());
								writeOutput.setCellData("AllergiesVeg", rowCounter, 11, prepMethod.getText());

							} catch (Exception e) {

							}
							try {
								WebElement nutrients = tlDriver.findElement(By.xpath("//table[@id= 'rcpnutrients']"));
								//System.out.print(nutrients.getText());
								writeOutput.setCellData("AllergiesVeg", rowCounter, 12, nutrients.getText());

							} catch (Exception e) {

							}
							try {
								//System.out.print(recipeUrl);
								writeOutput.setCellData("AllergiesVeg", rowCounter, 13, recipeUrl);
							} catch (Exception e) {

							}
							rowCounter++;
							//System.out.println("rowCounter" + rowCounter);
						}

					}
				}
				writeToDatabase.insertData(2, "allergies_indian_veg");
			}
//		}
	}

	private boolean isEliminated(List<String> allergies, List<String> avoidedRecipes) {
		AtomicBoolean isEliminatorPresent = new AtomicBoolean(false);

		
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
		avoidedRecipes.parallelStream().forEach(avoidedRecipe -> {
			try {

				//WebElement methodWebElement = tlDriver.findElement(By.xpath("//div[@id= 'rcpinglist']"));
				WebElement methodWebElement = tlDriver.findElement(By.xpath("//div[@id='recipe_small_steps']"));
				String method = methodWebElement.getText();
				if (null != method && null != avoidedRecipe && method.toLowerCase().contains(avoidedRecipe.toLowerCase())) {
					System.out.println("Eliminated due to avoidedRecipes: " + avoidedRecipe);
					isEliminatorPresent.set(true);
				}
			} catch (Exception e) {
				System.out.print("No Such Element " + e.getLocalizedMessage());
			}
		});
			return isEliminatorPresent.get();
	}
}
