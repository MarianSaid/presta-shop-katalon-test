package _cart_TC
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.util.concurrent.ConcurrentHashMap.KeySetView

import org.openqa.selenium.WebElement

import com.github.javafaker.Faker
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.Keys as Keys

//import common_keywords.Common_Keywords as Keys
import common_keywords.Common_Keywords as common



public class Manage_cart {
	
	
	private static String savedTitle
	private static String savedPrice
	
	private static def Selector(int rand) {
		return findTestObject('Object Repository/TC_0_ADD_TO_CART/product article', [('index') : rand])
	}
	
	public static List<Integer> listItemsIndex() {
		String baseSelector = 'div.products.row article'
		TestObject itemCard = new TestObject('item card')
		itemCard.addProperty('css', ConditionType.EQUALS, baseSelector)
		List<WebElement> products = WebUiCommonHelper.findWebElements(itemCard, 10)
		List<Integer> index=[]
		for (WebElement element : products) {
			String productId= element.getAttribute('data-id-product')
			if (productId != null && productId.isInteger()) {
				index.add(Integer.parseInt(productId))
			}
		}
		return index
	}
	
	private static def compareProductInfo(TestObject product, int index) {
		String titleBeforeNavigation = getProductTitle(product, findTestObject('Object Repository/TC_0_ADD_TO_CART/product_title pre', [('index') : index] ))
		String priceBeforeNavigation = getProductPrice(product, findTestObject('Object Repository/TC_0_ADD_TO_CART/product_price pre', [('index') : index] ))
		
		if (titleBeforeNavigation == null || priceBeforeNavigation == null) {
			WebUI.comment("Failed to fetch product title or price before navigating.")
			return
		}
		
		WebUI.comment("Title before navigation: " + titleBeforeNavigation)
		WebUI.comment("Price before navigation: " + priceBeforeNavigation)
		
		WebUI.click(product)
//		common.swithToIframe()
		
//		WebUI.waitForPageLoad(2)
		
		String titleAfterNavigation = getProductTitle(product, findTestObject('Object Repository/TC_0_ADD_TO_CART/product_title post'))
		String priceAfterNavigation = getProductPrice(product, findTestObject('Object Repository/TC_0_ADD_TO_CART/product_price post'))
		
		if (titleAfterNavigation == null || priceAfterNavigation == null) {
			WebUI.comment("Failed to fetch product title or price after navigating.")
			return
		}
		
		WebUI.comment("Title after navigation: " + titleAfterNavigation)
		WebUI.comment("Price after navigation: " + priceAfterNavigation)
		
		if (titleBeforeNavigation.equalsIgnoreCase(titleAfterNavigation)) {
			WebUI.comment("Product title matches.")
		} else {
			WebUI.comment("Product title does not match!")
		}
	
		if (priceBeforeNavigation.equals(priceAfterNavigation)) {
			WebUI.comment("Product price matches.")
		} else {
			WebUI.comment("Product price does not match!")
		}
	}
	
	
	private static String getProductTitle(TestObject productSelector, String selector) {
		TestObject titleObject = new TestObject('product title')
		titleObject.addProperty('css', ConditionType.EQUALS, productSelector.getSelectorCollection().get(ConditionType.EQUALS).get('css') + " h3 a")
	
		String title = null
		try {
			WebUI.waitForElementVisible(titleObject, 10)
			title = WebUI.getText(titleObject)
		} catch (Exception e) {
			WebUI.comment("Error fetching product title: " + e.getMessage())
		}
		
		return title
	}
	
	private static String getProductPrice(TestObject productSelector, String selector) {
		TestObject priceObject = new TestObject('product price')
		priceObject.addProperty('css', ConditionType.EQUALS, productSelector.getSelectorCollection().get(ConditionType.EQUALS).get('css') + " span.price")
	
		String price = null
		try {
			WebUI.waitForElementVisible(priceObject, 10)
			price = WebUI.getText(priceObject)
		} catch (Exception e) {
			WebUI.comment("Error fetching product price: " + e.getMessage())
		}
		
		return price
	}
	
	public static void randomProductSelection() {
		
			WebUI.waitForPageLoad(2) 
			
			List<Integer> indexList = listItemsIndex()
			if (indexList.isEmpty()) {
				WebUI.comment("No products found")
				return
			}		
			Random rand = new Random()
			int randomIndex = indexList.get(rand.nextInt(indexList.size()))
			
			TestObject selector= Selector(randomIndex)
			
			WebUI.scrollToElement(selector, 2)
			WebUI.comment("Clicked on product with data-id-product=" + randomIndex)
			
			compareProductInfo(selector, randomIndex)	
		}
		
			
	private static String getProductTitle(TestObject dynamicSelector, TestObject titleSelector) {
		   String title = null;
 		    
		    try {
		        WebUI.waitForElementVisible(titleSelector, 10);
		        // Fetch the title text
		        title = WebUI.getText(titleSelector);
		    } catch (Exception e) {
		        WebUI.comment("Error fetching product title: " + e.getMessage());
		    }
		    
		    return title;
		}

		
	private static String getProductPrice(TestObject dynamicSelector, TestObject priceSelector) {
			String price = null
			
			try {
		        WebUI.waitForElementVisible(priceSelector, 10);
		        // Fetch the title text
		        price = WebUI.getText(priceSelector);
		    } catch (Exception e) {
		        WebUI.comment("Error fetching product price: " + e.getMessage());
		    }		
			return price
		}
		
		private static def addToCart_button() {
//			common.switchToDefaultFrame()
//			common.swithToIframe()
//			WebUI.waitForElementVisible(findTestObject('Object Repository/TC_0_ADD_TO_CART/addProduct toast'), 1)
			return findTestObject('Object Repository/TC_0_ADD_TO_CART/add_to_cart button')
		}
		
		
		public static def specifyQuantity() {
		    // Define the TestObject for stock and quantity input
		    TestObject stockElement = findTestObject('Object Repository/TC_0_ADD_TO_CART/product_quantity')
		    TestObject availabilitySpan = findTestObject('Object Repository/TC_0_ADD_TO_CART/product_availability')
		    TestObject quantityInputBox = findTestObject('Object Repository/TC_0_ADD_TO_CART/quantity_txtfield')
		    
		    // Get the stock value
		    String stockValue = WebUI.getAttribute(stockElement, "data-stock")
		    if (stockValue == null || stockValue.isEmpty()) {
		        WebUI.comment("Stock value is missing.")
		        return
		    }
		
		    int maxStock = Integer.parseInt(stockValue)
		    Random rand = new Random()
		    int randomQuantity = rand.nextInt(maxStock - 200) + 1
		    
		    // Define the maximum wait time and interval
		    int maxWaitTime = 10
		    int waitInterval = 1
		    
		    // Clear and set the initial quantity
		    WebUI.clearText(quantityInputBox)
				WebUI.sendKeys(quantityInputBox, Keys.chord(Keys.BACK_SPACE))
//			WebUI.sendKeys(quantityInputBox, KeySetView.)

		    WebUI.setText(quantityInputBox, Integer.toString(randomQuantity))
		    WebUI.comment("Initial random quantity " + randomQuantity + " set in the input box.")
		    
		    // Wait until the availability span disappears or timeout
		    int waitedTime = 0
		    while (WebUI.verifyElementVisible(availabilitySpan, FailureHandling.OPTIONAL) && waitedTime < maxWaitTime) {
		        WebUI.comment("Product availability span is still visible. Adjusting quantity...")
		        
		        // Adjust the quantity
		        randomQuantity = rand.nextInt(maxStock - 200) + 1
		        
		        // Clear and set the new quantity
		        WebUI.clearText(quantityInputBox)
		        WebUI.setText(quantityInputBox, Integer.toString(randomQuantity))
		        
		        WebUI.comment("Adjusted quantity to " + randomQuantity + " in the input box.")
		        
		        // Wait before rechecking
		        WebUI.delay(waitInterval)
		        waitedTime += waitInterval
		    }
		    
		    // Final quantity report
		    if (waitedTime >= maxWaitTime) {
		        WebUI.comment("Product availability span is still visible after the timeout. Final quantity: " + randomQuantity)
		    } else {
		        WebUI.comment("Product availability span is now invisible. Final quantity: " + randomQuantity)
		    }
			
		}

	
	public static void verifyText(String expectedText) {
		TestObject modalTitleElement = findTestObject('Object Repository/TC_0_ADD_TO_CART/added_to_cart text')
		
		// Get the text from the modal title element
		String actualText = WebUI.getText(modalTitleElement)
		
		if (actualText.contains(expectedText)) {
			WebUI.comment("The text '${expectedText}' is present in the modal title.")
		} else {
			WebUI.comment("The text '${expectedText}' is not present in the modal title. Actual text: '${actualText}'")
		}
	}
	
	 static def proceedToCheckout_button() {
		return findTestObject('Object Repository/TC_0_ADD_TO_CART/proceed_to_checkout button')
	}

	public static def addToCart() {
			WebUI.click(addToCart_button())
			verifyText("Product successfully added to your shopping cart")
		}
		
		public static void verifyTotalPrice() {
		    TestObject cartItemsList = findTestObject('Object Repository/TC_0_ADD_TO_CART/cartItems list')
		    TestObject totalPriceElement = findTestObject('Object Repository/TC_0_ADD_TO_CART/totalPrice div')
		    
		    // Get all cart items
		    List<WebElement> cartItems = WebUI.findWebElements(cartItemsList, 10)
		    
		    double calculatedTotalPrice = 0.0
		
		    // Iterate over each cart item to calculate total price
		    for (WebElement item : cartItems) {
		        TestObject quantityInputBox = findTestObject('Object Repository/TC_0_ADD_TO_CART/quantityInputBox txtfield')
		        TestObject priceElement = findTestObject('Object Repository/TC_0_ADD_TO_CART/priceElement div')
		        
		        String quantityValue = WebUI.getAttribute(quantityInputBox, "value")
		        int quantity = Integer.parseInt(quantityValue.trim())
		        
		        String priceText = WebUI.getText(priceElement).replaceAll("[^\\d.,]", "").replace(',', '.')
		        double price = Double.parseDouble(priceText)
		        
		        calculatedTotalPrice += quantity * price
		    }
		    
		    String totalPriceText = WebUI.getText(totalPriceElement).replaceAll("[^\\d.,]", "").replace(',', '.')
		    double actualTotalPrice = Double.parseDouble(totalPriceText)
		    
		    if (Math.abs(calculatedTotalPrice - actualTotalPrice) < 0.01) { // Allowing a small margin of error
		        WebUI.comment("The total price is correctly calculated: " + calculatedTotalPrice)
		    } else {
		        WebUI.comment("The total price is incorrect. Expected: " + calculatedTotalPrice + ", Actual: " + actualTotalPrice)
		    }
		}
		
			public static def proceedCheckout() {
//				common.swithToIframe()
		
//			common.swithToIframe()
			WebUI.comment("proceedCheckout() 1")
//			WebUI.click(proceedToCheckout_button())
			WebUI.navigateToUrl('dependent-cent.demo.prestashop.com/en/cart?action=show')
			WebUI.comment("proceedCheckout() 2")
			
			verifyTotalPrice()
			
		}
		
		
		public static String generateRandomAddress(Faker faker) {
			String streetAddress = faker.address().streetAddress();
			String city = faker.address().city();
			String state = faker.address().state();
			String zipCode = faker.address().zipCode();
			String country = faker.address().country();
			
			// Format the address into a single string
			return String.format("%s, %s, %s %s, %s",
								 streetAddress, city, state, zipCode, country);
		}
		
		public static String generateRandomCityName(Faker faker) {
			// Generate a random city name using Faker
			return faker.address().city();
		}
		
		public static def proceedCheckout2() {
			WebUI.click(findTestObject(''))
			Faker faker=new Faker()
			String fakeEmail= generateRandomAddress(faker)
			String city= generateRandomCityName(faker)
			WebUI.setText('Object Repository/TC_0_ADD_TO_CART/addres inpput', fakeEmail)
			WebUI.setText('Object Repository/TC_0_ADD_TO_CART/city input', city)
			selectRandomOptionFromDropdown()
			String zipCode= generateZipCode()
			WebUI.setText(findTestObject('Object Repository/TC_0_ADD_TO_CART/zipCode input'), zipCode)
			
			
			
		}
		
		public static String generateZipCode() {
			Faker faker = new Faker();
			
			String zipCode = faker.address().zipCode();
			
			return zipCode;
		}
		
		public static void selectRandomOptionFromDropdown() {
			TestObject dropdown = findTestObject('Object Repository/TC_0_ADD_TO_CART/dropdpwn list')
		
			try {
				int optionCount = WebUI.getNumberOfTotalOption(dropdown)
				
				if (optionCount > 1) { // More than 1 option (excluding the default disabled option)
					Random random = new Random()
					int randomIndex = random.nextInt(optionCount - 1) + 1 // Exclude the default option
					WebUI.selectOptionByIndex(dropdown, randomIndex)
					
					WebUI.comment("Selected option at index: " + randomIndex)
				} else {
					WebUI.comment("Not enough options in the dropdown to select randomly.")
				}
			} catch (StepFailedException e) {
				WebUI.comment("Failed to select a random option: " + e.getMessage())
			}
		}
}
