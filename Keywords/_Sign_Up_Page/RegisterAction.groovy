package _Sign_Up_Page

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.WebElement

import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import common_keywords.Common_Keywords as common
import groovy.util.logging.Commons

public class RegisterAction {
	
	public static def SignIn_span() {
		return findTestObject('Object Repository/TC_0_LOGIN/Sign up page/sign_in span')
	}
	
	public static def Login_section() {
		return findTestObject('Object Repository/TC_0_LOGIN/Sign up page/login section')
	}

	public static def signup_link() {
		return findTestObject('Object Repository/TC_0_LOGIN/Sign up page/sign_up link')
	}
	
	public static def signup_section() {
		return findTestObject('Object Repository/TC_0_LOGIN/Sign up page/signUp section')
	}
	
	public static def firstName_textfield() {
		return findTestObject('Object Repository/TC_0_LOGIN/Sign up page/FirstName txtField')
	}
	
	public static def lastName_textfield() {
		return findTestObject('Object Repository/TC_0_LOGIN/Sign up page/LastName txtFields')
	}
	
	public static def email_textfield() {
		return findTestObject('Object Repository/TC_0_LOGIN/Sign up page/Email txtField')
	}
	
	public static def password_txtfield() {
		return findTestObject('Object Repository/TC_0_LOGIN/Sign up page/Password txtField')
	}
	
	public static def terms_checkbox() {
		return findTestObject('Object Repository/TC_0_LOGIN/Sign up page/terms of conditions checkbox')
	}
	
	public static def privacyPolicy_checkbox() {
		return findTestObject('Object Repository/TC_0_LOGIN/Sign up page/privacy policy checkbox')
	}
	
	public static def save_button() {
		return findTestObject('Object Repository/TC_0_LOGIN/Sign up page/Save button')
	}
	
	public static def DesktopUserInfo_div() {
		return findTestObject('Object Repository/TC_0_LOGIN/Sign up page/Desktop_user_info div')
	}
	
	static def chooseIndex(int num) {
		Random random = new Random()
		return random.nextInt(num)
	}
	
	private static def listItems() {
		String baseSelector = 'div[class="col-md-6 js-input-column form-control-valign"] label'
		TestObject baseObject = new TestObject('base labels')
		baseObject.addProperty('css', ConditionType.EQUALS, baseSelector)
		List<WebElement> labels = WebUiCommonHelper.findWebElements(baseObject, 10)
		List<String> indexList = []
		for (WebElement label : labels) {
			String forAttribute = label.getAttribute('for')
			if (forAttribute != null && forAttribute.contains('field-id_gender-')) {
				String index = forAttribute.split('-').last()
				indexList.add(index)
			}
		}
		
		return indexList
	}
	
	private static def listAndChooseGender() {
//		common.switchToDefaultFrame()
		common.swithToIframe()
		TestObject genderSelector = findTestObject('Object Repository/TC_0_LOGIN/Sign up page/gender radio button')
		List<WebElement> labelElements = WebUiCommonHelper.findWebElements(genderSelector, 10)
		
		List<String> labelTextList = []
		
		for (WebElement label : labelElements) {
			String labelText = label.getText().trim() // Get the text of the label
			labelTextList.add(labelText) // Add the label text to the list
		}
		WebUI.comment("Available gender options: " + labelTextList.toString())
		
		if (labelTextList.isEmpty()) {
			WebUI.comment("No gender options available")
			return
		}
		
		Random random = new Random()
		String randomLabelText = labelTextList.get(random.nextInt(labelTextList.size()))
		
		WebUI.comment("Randomly selected gender: " + randomLabelText)
		
		for (WebElement label : labelElements) {
			if (label.getText().trim().equals(randomLabelText)) {
				label.click()
				WebUI.comment("Clicked on gender option: " + randomLabelText)
				break
			}
		}
		common.switchToDefaultFrame()
		common.swithToIframe()
	}
	

	
	public static def navigateToSignUp(String firstName, String lastName, String Email) {
//		WebUI.waitForElementVisible(SignIn_span(), 5)
		WebUI.click(SignIn_span())		
		WebUI.verifyElementPresent(Login_section(), 2)
		WebUI.click(signup_link())
		WebUI.verifyElementPresent(signup_section(),2)
		listAndChooseGender()
		
		WebUI.setText(firstName_textfield(), firstName)
		WebUI.setText(lastName_textfield(), lastName)
		WebUI.setText(email_textfield(), Email)
		WebUI.setText(password_txtfield(), GlobalVariable.Password)
		WebUI.delay(2)
		WebUI.click(terms_checkbox())
		WebUI.click(privacyPolicy_checkbox())
		WebUI.click(save_button())
//		//////////convert to map
	}
	
	 

	
	public static def verifyRegister(String firstName, String lastName) {
//		common.swithToIframe()
		WebUI.comment("started verifyRegister ")
		String expectedUsername = firstName +" "+lastName
		String actualUsername = WebUI.getText(DesktopUserInfo_div()) //////////////////
		WebUI.verifyMatch(actualUsername, expectedUsername, false)
		WebUI.comment("Dynamic username verification completed")
	}
}
