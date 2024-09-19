package _add_to_wishlist_TC

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.WebElement

import com.github.javafaker.Faker
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import common_keywords.Common_Keywords as common

public class ManageAddingItems {
	
	static def newWishList_dialog() {
		return findTestObject('Object Repository/TC_0_ADD_TO_WISHLIST/create_new_wishlist dialog')
	}
	
	static def newWishList_button() {
		return findTestObject('Object Repository/TC_0_ADD_TO_WISHLIST/create_new_wishlist button')
	}

	static def wishlistName_input() {
		return findTestObject('Object Repository/TC_0_ADD_TO_WISHLIST/wishlist_name input')
	}
	
	static def addWishlist_button() {
		return findTestObject('Object Repository/TC_0_ADD_TO_WISHLIST/add_the_wishlist button')
	}
	
	static def addItem_toast() {
		return findTestObject('Object Repository/TC_0_ADD_TO_WISHLIST/add_item toast')
	}
	
	static def addProduct_toast() {
		return findTestObject('Object Repository/TC_0_ADD_TO_CART/addProduct toast')
	}
	
	static def allProducts_link(){
		return findTestObject('Object Repository/TC_0_ADD_TO_WISHLIST/all_products link')
	}
		
	public static int countItems() {
		TestObject wishList = new TestObject('wishlist')
		wishList.addProperty('css', ConditionType.EQUALS, 'ul.wishlist-list li p')
		List<WebElement> itemElements = WebUiCommonHelper.findWebElements(wishList, 10)
		WebUI.comment("wishlist number= " + itemElements.size())
		return itemElements.size()
	}
		
	public static List<String> listItemsString() {
		common.switchToDefaultFrame()
		common.swithToIframe()
		String baseSelector = 'ul.wishlist-list li p'
		TestObject wishLists = new TestObject('wishlist')
		wishLists.addProperty('css', ConditionType.EQUALS, baseSelector)
		List<WebElement> wishlists = WebUiCommonHelper.findWebElements(wishLists, 10)
		List<String> wishlistNames = []
		for (WebElement element : wishlists) {
			String wishListText = element.getText()
			if (wishListText != null && !wishListText.isEmpty()) {
				wishlistNames.add(wishListText)
			}
		}
		return wishlistNames
	}
	
	private static def verifyAddingWishlist() {
		WebUI.verifyElementPresent(findTestObject('Object Repository/TC_0_ADD_TO_WISHLIST/addWishList toast'), 2)
	}
	
	public static def createWishList() {
//		common.swithToIframe()
		WebUI.verifyElementPresent(newWishList_dialog(), 2)
		
		Faker faker = new Faker()
		Random rand = new Random()
		
//		int numberOfWishlists = rand.nextInt(5) + 1
		int numberOfWishlists= 2
		WebUI.comment("random number is: "+ numberOfWishlists)
		List<String> newWishlists = []
		
//		common.switchToDefaultFrame()
		for (int i = 0; i < numberOfWishlists; i++) {
			WebUI.delay(1.5)
			common.switchToDefaultFrame()
			common.swithToIframe()
			
			WebUI.click(newWishList_button())
			
			String newName = faker.commerce().productName()
			WebUI.comment("The new wishlist is: " + newName)
			newWishlists.add(newName)	
			WebUI.setText(wishlistName_input(), newName)
			WebUI.click(addWishlist_button())
			verifyAddingWishlist()
		}
		
		List<String> existingWishlists = listItemsString()
		List<String> allWishlists = newWishlists + existingWishlists
		
		if (allWishlists.isEmpty()) {
			WebUI.comment("No wishlists available")
			return
		}
		
		WebUI.comment("All wishlists: " + allWishlists.toString())
		
		String randomWishlist = allWishlists.get(rand.nextInt(allWishlists.size()))
		WebUI.comment("The selected wishlist: " + randomWishlist)
		
		String script = """
        var wishlistName = '" + randomWishlist + "';
        var wishlistElement = [...document.querySelectorAll('ul.wishlist-list li.wishlist-list-item p')] 
            .find(el => el.textContent.trim() === wishlistName);
        if (wishlistElement) {
            wishlistElement.click();
        } else {
            console.log('Wishlist item not found: ' + wishlistName);
        }
    """
		/// the "..." to turn the nodelist into array
		WebUI.executeJavaScript(script, null)
//		common.switchToDefaultFrame()
//		WebUI.comment("Clicked on wishlist: " + randomWishlist)
	}
	
	public static def verifyAddingItem() {
		WebUI.verifyElementPresent(addProduct_toast(), 2)
		WebUI.comment("the item added successfuly and the toast message appeared")
	}
	
	public static void selectRandomWishlist() {
		List<String> wishlists = listItemsString()
		if (wishlists.isEmpty()) {
			WebUI.comment("There is no wishlist")
			return
		}
		WebUI.comment("Available wishlists: " + wishlists.toString())
		Random rand = new Random()
		String randomWishlist = wishlists.get(rand.nextInt(wishlists.size()))
		WebUI.comment("Randomly selected wishlist: " + randomWishlist)
		
		TestObject wishlistItem = findTestObject("Object Repository/TC_0_ADD_TO_WISHLIST/wishlist_list item")
		List<WebElement> wishlistElements = WebUiCommonHelper.findWebElements(wishlistItem, 10)
		for (WebElement element : wishlistElements) {
			if (element.getText().equals(randomWishlist)) {
				element.click()
				WebUI.comment("Clicked on wishlist: " + randomWishlist)
				break
			}
		}
//		verifyAddingItem()
	}
	
	
		
	public static def navigateAllProducts() {
		TestObject allProductsLink = 
		WebUI.click(allProducts_link())
	}		
	public static List<String> listPages() {
    String baseSelector = 'ul.page-list.clearfix.text-sm-center a[rel="nofollow"]'
    
    TestObject pageLink = new TestObject('page link')
    pageLink.addProperty('css', ConditionType.EQUALS, baseSelector)
    
    List<WebElement> pageElements = WebUiCommonHelper.findWebElements(pageLink, 10)
    
    List<String> pageUrls = []
    
    for (WebElement element : pageElements) {
        String pageUrl = element.getAttribute('href')
        
        if (pageUrl != null && !pageUrl.isEmpty()) {
            pageUrls.add(pageUrl)
        }
    }   
    return pageUrls
}

	public static void navigateToRandomPage() {
		
		List<String> pageUrls = listPages()		
		if (pageUrls.isEmpty()) {
			WebUI.comment("No pages available to choose from.")
			return
		}
				
		Random rand = new Random()		
		String randomPageUrl = pageUrls.get(rand.nextInt(pageUrls.size()))		
		WebUI.comment("Randomly selected page URL: " + randomPageUrl)	
		TestObject pageLink = new TestObject('page link')
		pageLink.addProperty('css', ConditionType.EQUALS, "ul.page-list.clearfix.text-sm-center a[rel='nofollow'][href='" + randomPageUrl + "']")
		WebUI.waitForElementPresent(pageLink, 10)
		WebUI.waitForElementVisible(pageLink, 10)
		WebUI.delay(4)
		WebUI.click(pageLink)
	}

	public static void clickRandomPageLink() {
		String baseSelector = 'ul.page-list.clearfix.text-sm-center a[rel="nofollow"]'
		
		TestObject pageLink = new TestObject('page link')
		pageLink.addProperty('css', ConditionType.EQUALS, baseSelector)
		
		List<WebElement> pageElements = WebUiCommonHelper.findWebElements(pageLink, 10)
		
		// Map to hold page text and corresponding WebElements
		Map<String, WebElement> pageTextToElementMap = new HashMap<>()
		
		for (WebElement element : pageElements) {
			String pageText = element.getText().trim()
			
			if (pageText != null && !pageText.isEmpty()) {
				pageTextToElementMap.put(pageText, element)
			}
		}
		
		if (pageTextToElementMap.isEmpty()) {
			WebUI.comment("No pages found")
			return
		}
		
		// Get a random key from the map
		List<String> pageTexts = new ArrayList<>(pageTextToElementMap.keySet())
		Random rand = new Random()
		String randomPageText = pageTexts.get(rand.nextInt(pageTexts.size()))
		
		WebUI.comment("Randomly selected page with text: " + randomPageText)
		
		WebElement randomPageElement = pageTextToElementMap.get(randomPageText)
		
		// Click the randomly selected page element
		WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(randomPageElement))
		common.switchToDefaultFrame()
		common.swithToIframe()
	}
		
	}

