package _add_to_wishlist_TC
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import common_keywords.Common_Keywords 
import org.openqa.selenium.WebElement

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import common_keywords.Common_Keywords as common
public class Fetch_webElements_Action {
	
	private static def buttonSelector(int rand) {
		return findTestObject('Object Repository/TC_0_ADD_TO_WISHLIST/add_to_wishlist button', [('index') : rand])
	}

	public static int countItems() {
		TestObject itemCard = new TestObject('item card')
		itemCard.addProperty('css', ConditionType.EQUALS, 'div.products.row div.js-product.product.col-xs-12.col-sm-6.col-lg-4.col-xl-3')
		List<WebElement> itemElements = WebUiCommonHelper.findWebElements(itemCard, 10)
		WebUI.comment("list size equals: " + itemElements.size())
		return itemElements.size()
	}
	
	public static List<Integer> listItemsIndex() {
		common.switchToDefaultFrame()
		common.swithToIframe()
		
		String baseSelector = 'div.products.row article'
		TestObject itemCard = new TestObject('item card')
//		TestObject itemCard= findTestObject("")
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
	
	
	public static void randomFavoriteButtonClick() {
			WebUI.waitForPageLoad(2) 
			
			List<Integer> indexList = listItemsIndex()
			if (indexList.isEmpty()) {
				WebUI.comment("No products found")
				return
			}	
			Random rand = new Random()
			int randomIndex = indexList.get(rand.nextInt(indexList.size()))
			WebUI.comment("random index is:" + randomIndex.toInteger())
			WebUI.delay(3)
			TestObject selector= buttonSelector(randomIndex)
			WebUI.scrollToElement(selector, 2)
			WebUI.click(selector)
			WebUI.comment("Clicked wishlist button for product with data-id-product=" + randomIndex)
//			common.swithToIframe()
		}	

	} 