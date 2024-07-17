package allergy;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import org.openqa.selenium.*;
import org.testng.annotations.Test;
import hooks.hooksForScrapping;
import utilities.WriteExcel;

public class AllergyDinnerData extends hooksForScrapping {

	@Test
	public void extractRecipe() throws InterruptedException, IOException {
		List<String> eliminators = Arrays.asList(new String[] { "Milk", "Soy", "Egg", "Sesame", "Peanuts", "walnut",
				"almond", "hazelnut", "pecan", "cashew", "pistachio", "Shellfish", "Seafood" });

		int rowCounter = 1;

		tlDriver.navigate().to("https://www.tarladalal.com/recipes-for-dinner-menus-1116");
		int lastPage = 0;
		try {
			lastPage = Integer
					.parseInt(tlDriver.findElement(By.xpath("//div/a[@class= 'respglink'][last()]")).getText());
			System.out.println(lastPage + "is the last page");
		} catch (Exception e) {

		}
		if (0 != lastPage) {
			for (int j = 2; j <= lastPage; j++) {

				tlDriver.navigate().to("https://www.tarladalal.com/recipes-for-dinner-menus-1116" + "?pageindex=" + j);
				System.out.println("I am in page: " + j);
				List<WebElement> recipeCardElements = tlDriver
						.findElements(By.xpath("//article[@class='rcc_recipecard']"));
				System.out.println(recipeCardElements);
				List<String> recipeUrls = new ArrayList<>();
				Map<String, String> recipeIdUrls = new HashMap<>();

				// Looping through all recipes Web elements and generating a navigation URL
				recipeCardElements.stream().forEach(recipeCardElement -> {
					recipeUrls.add("https://www.tarladalal.com/" + recipeCardElement
							.findElement(By.xpath("//span[@class='rcc_recipename']/a")).getDomAttribute("href"));

					recipeIdUrls.put(recipeCardElement.getDomAttribute("id").replace("rcp", ""),
							"https://www.tarladalal.com/"
									+ recipeCardElement.findElement(By.tagName("a")).getDomAttribute("href")
											.replace("calories-for-", "").concat("r").replace("rr", "r"));
				});
				System.out.println(recipeUrls);
				System.out.println(recipeIdUrls);
				for (Map.Entry<String, String> recipeIdUrlEntry : recipeIdUrls.entrySet()) {
					String recipeUrl = recipeIdUrlEntry.getValue();
					String recipeId = recipeIdUrlEntry.getKey();
					System.out.println(recipeUrl + "is the recipe URL");
					tlDriver.navigate().to(recipeUrl);

					if (isEliminated(eliminators)) {

					} else {
						WriteExcel writeOutput = new WriteExcel();

						try {
							writeOutput.setCellAllergyData("AllergiesData", rowCounter, 0, recipeId);
							WebElement recipeTitle = tlDriver
									.findElement(By.xpath("//span[@id= 'ctl00_cntrightpanel_lblRecipeName']"));
							System.out.print(recipeTitle.getText());
							writeOutput.setCellAllergyData("AllergiesData", rowCounter, 1, recipeTitle.getText());

							WebElement recipeCategory = tlDriver.findElement(By.xpath(
									"//span[@itemprop= 'description']/*[contains (text(), 'Breakfast') or contains (text(), 'lunch') or contains (text(), 'drink') or contains (text(), 'snack') or contains (text(), 'dinner') or contains (text(), 'dish')]"));
							System.out.print(recipeCategory.getText());
							writeOutput.setCellAllergyData("AllergiesData", rowCounter, 2, recipeCategory.getText());

							WebElement foodCategory = tlDriver
									.findElement(By.xpath("//div[@class= 'breadcrumb']/span[5]"));
							System.out.print(foodCategory.getText());
							writeOutput.setCellAllergyData("AllergiesData", rowCounter, 3, foodCategory.getText());

							WebElement nameOfIngredients = tlDriver.findElement(By.xpath("//div[@id= 'rcpinglist']"));
							System.out.print(nameOfIngredients.getText());
							writeOutput.setCellAllergyData("AllergiesData", rowCounter, 4, nameOfIngredients.getText());

							WebElement preparationTime = tlDriver
									.findElement(By.xpath("//p/time[@itemprop= 'prepTime']"));
							System.out.print(preparationTime.getText());
							writeOutput.setCellAllergyData("AllergiesData", rowCounter, 5, preparationTime.getText());

							WebElement cookTime = tlDriver.findElement(By.xpath("//p/time[@itemprop= 'cookTime']"));
							System.out.print(cookTime.getText());
							writeOutput.setCellAllergyData("AllergiesData", rowCounter, 6, cookTime.getText());

							WebElement tagsTags = tlDriver.findElement(By.xpath("//div[@class= 'tags']"));
							System.out.print(tagsTags.getText());
							writeOutput.setCellAllergyData("AllergiesData", rowCounter, 7, tagsTags.getText());

							WebElement numberOfServings = tlDriver
									.findElement(By.xpath("//span[@id= 'ctl00_cntrightpanel_lblServes']"));
							System.out.print(numberOfServings.getText());
							writeOutput.setCellAllergyData("AllergiesData", rowCounter, 8, numberOfServings.getText());

							WebElement cusineCategory = tlDriver
									.findElement(By.xpath("//div[@class= 'breadcrumb']/span[5]"));
							System.out.print(cusineCategory.getText());
							writeOutput.setCellAllergyData("AllergiesData", rowCounter, 9, cusineCategory.getText());

							WebElement recipeDescription = tlDriver
									.findElement(By.xpath("//span[@id='ctl00_cntrightpanel_lblrecipeNameH2']"));
							System.out.print(recipeDescription.getText());
							writeOutput.setCellAllergyData("AllergiesData", rowCounter, 10,
									recipeDescription.getText());

							WebElement prepMethod = tlDriver
									.findElement(By.xpath("//div[@id= 'ctl00_cntrightpanel_pnlRcpMethod']"));
							System.out.print(prepMethod.getText());
							writeOutput.setCellAllergyData("AllergiesData", rowCounter, 11, prepMethod.getText());

							WebElement nutrients = tlDriver.findElement(By.xpath("//table[@id= 'rcpnutrients']"));
							System.out.print(nutrients.getText());
							writeOutput.setCellAllergyData("AllergiesData", rowCounter, 12, nutrients.getText());

							System.out.print(recipeUrl);
							writeOutput.setCellAllergyData("AllergiesData", rowCounter, 13, recipeUrl);

						} catch (Exception e) {

						}

					}

					rowCounter++;
					System.out.println("rowCounter" + rowCounter);
				}
			}
		}
	}

	private boolean isEliminated(List<String> eliminators) {
		AtomicBoolean isEliminatorPresent = new AtomicBoolean(false);

		eliminators.parallelStream().forEach(eliminator -> {
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
