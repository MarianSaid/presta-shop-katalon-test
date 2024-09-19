package _Navigation_To_Website

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable


public class WebsiteNavigation {

	public static def navigateToWebsite(String URL) {
		WebUI.openBrowser('')
		WebUI.navigateToUrl(URL)
		WebUI.maximizeWindow()
		//		WebUI.waitForPageLoad(10)
	}
}
