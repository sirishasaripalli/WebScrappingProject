package RecepieeScrapingLCHF;

    import java.io.IOException;
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.SQLException;
	import java.util.ArrayList;
	import java.util.Arrays;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;
	import java.util.concurrent.TimeUnit;
	import java.util.concurrent.atomic.AtomicBoolean;

	import org.openqa.selenium.By;
	import org.openqa.selenium.WebElement;
	import org.testng.annotations.Test;

	import hooks.baseClassForDriver;
	import hooks.hooksForScrapping;
import utilities.ReadElliminationList;
import utilities.WriteExcel;

	public class LCHF extends hooksForScrapping {
		private ArrayList<String> eliminateList;
		@Test
		public void extractRecipe() throws InterruptedException, IOException {

			eliminateList = ReadElliminationList.readColumnFromExcel("/Users/parikshit/Downloads/LCHFEllimanator.xlsx",0);

					
			
			int rowCounter = 1;
			// run in a loop for all recipe in a page
 		List<String> pageBeginsWithList = Arrays.asList(new String[] { "0-9" });

		for (int k = 0; k <1 ; k++) {
		tlDriver.navigate().to("https://www.tarladalal.com/recipes-for-low-carb-veg-indian-recipes-757");
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
				
					tlDriver.navigate().to("https://www.tarladalal.com/recipes-for-low-carb-veg-indian-recipes-757"
							+ "?pageindex=" + j);
						List<WebElement> recipeCardElements = tlDriver
								.findElements(By.xpath("//article[@class='rcc_recipecard']"));
						System.out.println(recipeCardElements.size() + "is the sizeee");
						System.out.println(recipeCardElements);
						List<String> recipeUrls = new ArrayList<>();
						Map<String, String> recipeIdUrls = new HashMap<>();

						
						// Looping through all recipes Web elements and generating a navigation URL
						recipeCardElements.stream().forEach(recipeCardElement -> {
							recipeUrls.add("https://www.tarladalal.com/" + recipeCardElement
									.findElement(By.xpath("//span[@class='rcc_recipename']/a")).getDomAttribute("href"));
//							// example: recipeIdUrls.put("id","url");

							recipeIdUrls.put(recipeCardElement.getDomAttribute("id").replace("rcp", ""),
									"https://www.tarladalal.com/"
							+ recipeCardElement.findElement(By.tagName("a")).getDomAttribute("href").replace("calories-for-", "").concat("r").replace("rr", "r"));
							});
						System.out.println(recipeUrls);
						System.out.println(recipeIdUrls.size() + "is the size");
						System.out.println(recipeIdUrls);
						for (Map.Entry<String, String> recipeIdUrlEntry : recipeIdUrls.entrySet()) {
							System.out.println("Inside this");
							String recipeUrl = recipeIdUrlEntry.getValue();
							String recipeId = recipeIdUrlEntry.getKey();
							System.out.println(recipeUrl + "is the recipe URL");
							tlDriver.navigate().to(recipeUrl);
							tlDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

							if (isEliminated(eliminateList)) {
								//Exclude using the 
								// tlDriver.navigate().to("//div/a[text()= 'Recipe A To Z']");
							} else {
								
								WriteExcel writeOutput = new WriteExcel();
								// Recipe id
								try {
									writeOutput.setCellData("LCHF", rowCounter, 0, recipeId);
								} catch (Exception e) {

								}

								// Recipe Name
								try {
									WebElement recipeTitle = tlDriver
											.findElement(By.xpath("//span[@id= 'ctl00_cntrightpanel_lblRecipeName']"));
									System.out.print(recipeTitle.getText());
									writeOutput.setCellData("LCHF", rowCounter, 1, recipeTitle.getText());
									String recipeTitleDB = recipeTitle.getText();
									
								} catch (Exception e) {

								}
								try {
									WebElement recipeCategory = tlDriver.findElement(By.xpath(
											"//span[@itemprop= 'description']/*[contains (text(), 'breakfast') or contains (text(), 'lunch') or contains (text(), 'dinner') or contains (text(), ' Snacks')]"));
									System.out.print(recipeCategory.getText());
									writeOutput.setCellData("LCHF", rowCounter, 2, recipeCategory.getText());

								} catch (Exception e) {

								}
								try {
									WebElement foodCategory = tlDriver
											.findElement(By.xpath("//span[@itemprop= 'name'][contains (text(), 'Veg Recipes')  or contains (text(), 'Vegetarian') ]"));
									System.out.print(foodCategory.getText());
									writeOutput.setCellData("LCHF", rowCounter, 3, foodCategory.getText());

								} catch (Exception e) {

								}

								try {
									WebElement nameOfIngredients = tlDriver.findElement(By.xpath("//div[@id= 'rcpinglist']"));
									System.out.print(nameOfIngredients.getText());
									writeOutput.setCellData("LCHF", rowCounter, 4, nameOfIngredients.getText());

								} catch (Exception e) {

								}

								try {
									WebElement preparationTime = tlDriver
											.findElement(By.xpath("//p/time[@itemprop= 'prepTime']"));
									System.out.print(preparationTime.getText());
									writeOutput.setCellData("LCHF", rowCounter, 5, preparationTime.getText());

								} catch (Exception e) {

								}

								try {
									WebElement cookTime = tlDriver.findElement(By.xpath("//p/time[@itemprop= 'cookTime']"));
									System.out.print(cookTime.getText());
									writeOutput.setCellData("LCHF", rowCounter, 6, cookTime.getText());

								} catch (Exception e) {

								}
	//Tag, Number of servings, Cusinie Category, Recipe Description

								try {
									WebElement tagsTags = tlDriver
											.findElement(By.xpath("//div[@class= 'tags']"));
									System.out.print(tagsTags.getText());
									writeOutput.setCellData("LCHF", rowCounter, 7, tagsTags.getText());

								} catch (Exception e) {

								}
								try {
									WebElement numberOfServings = tlDriver
											.findElement(By.xpath("//span[@id= 'ctl00_cntrightpanel_lblServes']"));
									System.out.print(numberOfServings.getText());
									writeOutput.setCellData("LCHF", rowCounter, 8, numberOfServings.getText());

								} catch (Exception e) {

								}
								try {
									WebElement cusineCategory = tlDriver
											.findElement(By.xpath("//div[@class= 'breadcrumb']/span[7]"));
									System.out.print(cusineCategory.getText());
									writeOutput.setCellData("LCHF", rowCounter, 9, cusineCategory.getText());

								} catch (Exception e) {

								}
								try {
									WebElement recipeDescription = tlDriver
											.findElement(By.xpath("//span[@id='ctl00_cntrightpanel_lblrecipeNameH2']"));
									System.out.print(recipeDescription.getText());
									writeOutput.setCellData("LCHF", rowCounter, 10, recipeDescription.getText());

								} catch (Exception e) {

								}
								
								try {
									WebElement prepMethod = tlDriver
											.findElement(By.xpath("//div[@id= 'ctl00_cntrightpanel_pnlRcpMethod']"));
									System.out.print(prepMethod.getText());
									writeOutput.setCellData("LCHF", rowCounter, 11, prepMethod.getText());

								} catch (Exception e) {

								}
								try {
									WebElement nutrients = tlDriver.findElement(By.xpath("//table[@id= 'rcpnutrients']"));
									System.out.print(nutrients.getText());
									writeOutput.setCellData("LCHF", rowCounter, 12, nutrients.getText());

								} catch (Exception e) {

								}
								try {
									System.out.print(recipeUrl);
									writeOutput.setCellData("LCHF", rowCounter, 13, recipeUrl);
								} catch (Exception e) {

								}

								//Insert data to the table
//								String sqlInsert = "INSERT INTO public.lfv(recipe_id, recipe_name, recipe_category, food_category, ingredients, preparation_time, cooking_time, tag, no_of_servings, cuisine_category, recipe_description, preparation_method, nutrient_values, recipe_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
//								
//								  try {
//						        		Connection conn = tlDriverManager.getConnection(url, user, password);
//						        		System.out.println("DB connected");
//						                PreparedStatement stmt = conn.prepareStatement(sqlInsert);
//						                // Set parameters for the insert statement
//						                // Execute the insert statement
//						                int rowsInserted = stmt.executeUpdate();
//						                System.out.println("Rows inserted: " + rowsInserted);
	//
//						            } catch (SQLException e) {
//						                e.printStackTrace();
//						            }

								rowCounter++;
								System.out.println("rowCounter" + rowCounter);
							}

						}
				
					}

			}
			}
		
	}
		private boolean isEliminated(List<String> eliminateList) {
			AtomicBoolean isEliminatorPresent = new AtomicBoolean(false);

			eliminateList.parallelStream().forEach(eliminator -> {
				try {

					WebElement methodWebElement = tlDriver.findElement(By.xpath("//div[@id='recipe_small_steps']"));
					String method = methodWebElement.getText();
					if (null != method && null != eliminator && method.toLowerCase().contains(eliminator.toLowerCase())) {
						System.out.println("Eliminated due to: " + eliminator);
						isEliminatorPresent.set(true);
					}
				} catch (Exception e) {
					System.out.print("No Such Element " + e.getLocalizedMessage());
				}
			});
			return isEliminatorPresent.get();
		}

}
