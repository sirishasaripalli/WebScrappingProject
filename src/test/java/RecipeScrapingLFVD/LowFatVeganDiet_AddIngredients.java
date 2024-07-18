package RecipeScrapingLFVD;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import org.openqa.selenium.*;
import org.testng.annotations.Test;
import hooks.hooksForScrapping;
import utilities.WriteExcel;

public class LowFatVeganDiet_AddIngredients extends hooksForScrapping {

	@Test
	public void extractRecipe() throws InterruptedException, IOException {
		List<String> eliminators = Arrays.asList(new String[] { "Fish", "Sausage", "ham", "salami", "bacon", "milk",
				"cheese", "yogurt", "butter", "Ice cream", "egg", "prawn", "Oil", "olive oil", "coconut oil",
				"soybean oil", "corn oil", "safflower oil", "sunflower oil", "rapeseed oil", "peanut oil",
				"cottonseed oil", "canola oil", "mustard oil", "cereals", "tinned vegetable", "bread", "maida", "atta",
				"sooji", "poha", "cornflake", "cornflour", "pasta", "White rice", "pastry", "cakes", "biscuit", "soy",
				"soy milk", "white miso paste", "soysauce", "soy curls", "edamame", "soy yogurt", "soy nut", "tofu",
				"pies", "Chip", "cracker", "potatoe", "sugar", "jaggery", "glucose", "fructose", "corn syrup",
				"cane solid", "aspartame", "cane solid", "maltose", "dextrose", "sorbitol", "mannitol", "xylitol",
				"maltodextrin", "molasses", "brown rice syrup", "Splenda", "nutra sweet", "stevia", "barley malt",
				"pork", "Meat", "poultry" });

		List<String> ToADDIngredients = Arrays.asList("Lettuce", "kale", "chard", "arugula", "spinach", "cabbage",
				"pumpkin", "sweet potatoes", "purple potatoes", "yams", "turnip", "parsnip", "karela", "bittergourd",
				"beet", "carrot", "cucumber", "red onion", "white onion", "broccoli", "cauliflower", "carrot", "celery",
				"artichoke", "bell pepper", "mushroom", "tomato", "sweet and hot pepper", "banana", "mango", "papaya",
				"plantain", "apple", "orange", "pineapple", "pear", "tangerine", "all berry varieties",
				"all melon varieties", "peach", "plum", "nectarine", "Avocado", "Amaranth", "Rajgira",
				"Ramdana Barnyard", "Sanwa", "Samvat ke chawal buckwheat", "kuttu finger millet", "Ragi",
				"Nachni foxtail millet", "kangni", "kakum kodu", "kodon", "little millet", "moraiyo", "kutki", "shavan",
				"sama pearl millet", "bajra", "broom corn millet", "chena sorghum", "jowar", "Lentil", "Pulse",
				"Moong dhal", "masoor dhal", "toor dhal", "urd dhal", "lobia", "rajma", "matar", "all forms of chana",
				"almond", "cashew", "pistachio", "brazil nut", "walnut", "pine nut", "hazelnut", "macadamia nut",
				"pecan", "peanut", "hemp seed", "sun flower seed", "sesame seed", "chia seed", "flax seed");

		
		int rowCounter = 1;

		tlDriver.navigate().to("https://www.tarladalal.com/recipes-for-vegan-recipes-vegan-diet-1010");
		int lastPage = 0;
		try {
			lastPage = Integer
					.parseInt(tlDriver.findElement(By.xpath("//div/a[@class= 'respglink'][last()]")).getText());
			System.out.println(lastPage + "is the last page");
		} catch (Exception e) {
			// do nothing or log exception
		}
		if (0 != lastPage) {
			for (int j = 2; j <= lastPage; j++) {

				tlDriver.navigate()
						.to("https://www.tarladalal.com/recipes-for-vegan-recipes-vegan-diet-1010" + "?pageindex=" + j);
				System.out.println("I am in page: " + j);
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
					recipeIdUrls.put(recipeCardElement.getDomAttribute("id").replace("rcp", ""),
							"https://www.tarladalal.com/"
									+ recipeCardElement.findElement(By.tagName("a")).getDomAttribute("href")
											.replace("calories-for-", "").concat("r").replace("rr", "r"));
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

					if (!isEliminated(eliminators) && IsaddIngredients(ToADDIngredients)) {

						WriteExcel writeOutput = new WriteExcel();
						// Recipe id
						try {
							writeOutput.setCellData("LFV", rowCounter, 0, recipeId);
							WebElement recipeTitle = tlDriver
									.findElement(By.xpath("//span[@id= 'ctl00_cntrightpanel_lblRecipeName']"));
							System.out.print(recipeTitle.getText());
							writeOutput.setCellData("LFV", rowCounter, 1, recipeTitle.getText());
							// String recipeTitleDB = recipeTitle.getText();

							WebElement recipeCategory = tlDriver.findElement(By.xpath(
									"//span[@itemprop= 'description']/*[contains (text(), 'Breakfast') or contains (text(), 'lunch') or contains (text(), 'drink') or contains (text(), 'soup') or contains (text(), 'dinner') or contains (text(), 'snack')]"));
							System.out.print(recipeCategory.getText());
							writeOutput.setCellData("LFV", rowCounter, 2, recipeCategory.getText());
							WebElement foodCategory = tlDriver
									.findElement(By.xpath("//div[@class= 'breadcrumb']/span[5]"));
							System.out.print(foodCategory.getText());
							writeOutput.setCellData("LFV", rowCounter, 3, foodCategory.getText());
							WebElement nameOfIngredients = tlDriver.findElement(By.xpath("//div[@id= 'rcpinglist']"));
							System.out.print(nameOfIngredients.getText());
							writeOutput.setCellData("LFV", rowCounter, 4, nameOfIngredients.getText());

							WebElement preparationTime = tlDriver
									.findElement(By.xpath("//p/time[@itemprop= 'prepTime']"));
							System.out.print(preparationTime.getText());
							writeOutput.setCellData("LFV", rowCounter, 5, preparationTime.getText());

							WebElement cookTime = tlDriver.findElement(By.xpath("//p/time[@itemprop= 'cookTime']"));
							System.out.print(cookTime.getText());
							writeOutput.setCellData("LFV", rowCounter, 6, cookTime.getText());

							WebElement tagsTags = tlDriver.findElement(By.xpath("//div[@class= 'tags']"));
							System.out.print(tagsTags.getText());
							writeOutput.setCellData("LFV", rowCounter, 7, tagsTags.getText());

							WebElement numberOfServings = tlDriver
									.findElement(By.xpath("//span[@id= 'ctl00_cntrightpanel_lblServes']"));
							System.out.print(numberOfServings.getText());
							writeOutput.setCellData("LFV", rowCounter, 8, numberOfServings.getText());

							System.out.print("In cusineCategory");
							WebElement cusineCategory = tlDriver
									.findElement(By.xpath("//div[@class = 'breadcrumb']/span[5]"));

							System.out.print("cusineCategory:" + cusineCategory.getText());
							writeOutput.setCellData("LFV", rowCounter, 9, cusineCategory.getText());
							WebElement recipeDescription = tlDriver
									.findElement(By.xpath("//span[@id='ctl00_cntrightpanel_lblrecipeNameH2']"));
							System.out.print(recipeDescription.getText());
							writeOutput.setCellData("LFV", rowCounter, 10, recipeDescription.getText());
							WebElement prepMethod = tlDriver
									.findElement(By.xpath("//div[@id= 'ctl00_cntrightpanel_pnlRcpMethod']"));
							System.out.print(prepMethod.getText());
							writeOutput.setCellData("LFV", rowCounter, 11, prepMethod.getText());

							WebElement nutrients = tlDriver.findElement(By.xpath("//table[@id= 'rcpnutrients']"));
							System.out.print(nutrients.getText());
							writeOutput.setCellData("LFV", rowCounter, 12, nutrients.getText());
							System.out.print(recipeUrl);
							writeOutput.setCellData("LFV", rowCounter, 13, recipeUrl);
						} catch (Exception e) {

						}

						rowCounter++;
						System.out.println("rowCounter" + rowCounter);
					}

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

	private boolean IsaddIngredients(List<String> ToADDIngredients) {
		AtomicBoolean isPresent = new AtomicBoolean(false);

		ToADDIngredients.parallelStream().forEach(ToADDIngredient -> {
			try {

				WebElement methodWebElement = tlDriver.findElement(By.xpath("//div[@id='recipe_small_steps']"));
				String method = methodWebElement.getText();
				if (null != method && null != ToADDIngredient
						&& method.toLowerCase().contains(ToADDIngredient.toLowerCase())) {
					System.out.println("This recipe has listed Ingredient: " + ToADDIngredient);
					isPresent.set(true);
				}
			} catch (Exception e) {
				System.out.print("No Such Element " + e.getLocalizedMessage());
			}
		});
		return isPresent.get();
	}

}
