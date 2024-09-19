import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import _Navigation_To_Website.WebsiteNavigation
import _Sign_Up_Page.RegisterAction
import _add_to_wishlist_TC.Fetch_webElements_Action as FetchElements
import _add_to_wishlist_TC.ManageAddingItems as ManageItems
import _cart_TC.Manage_cart as ManageCart
import common_keywords.Common_Keywords as common
import internal.GlobalVariable

final Map<String, String> testData = [
    "firstName": common.generateFakeName(),
    "lastName": common.generateFakeName(),
    "email": common.generateFakeEmail()
]

String firstName= testData['firstName']
String lastName=testData['lastName']
String email=testData['email']
WebUI.comment("Test data - First Name: ${testData['firstName']}")
WebUI.comment("Test data - Last Name: ${testData['lastName']}")
WebUI.comment("Test data - Email: ${testData['email']}")



//TC_0_LOGIN
WebsiteNavigation.navigateToWebsite(GlobalVariable.URL) //done
RegisterAction.navigateToSignUp(firstName, lastName, email) //done 
RegisterAction.verifyRegister(firstName, lastName) // done


//TC_0_ADD_TO_WISHLISH
FetchElements.randomFavoriteButtonClick() //done
ManageItems.createWishList()//done
ManageItems.selectRandomWishlist()//done

//TC_0_ADD_TO_CART
ManageCart.randomProductSelection()
ManageCart.specifyQuantity()
ManageCart.addToCart()
ManageCart.proceedCheckout()
ManageCart.proceedCheckout2()
