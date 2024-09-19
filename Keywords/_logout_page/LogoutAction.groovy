package _logout_page

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class LogoutAction {
	
	private static def signIn_button() {
		return findTestObject('Object Repository/TC_0_LOGIN/Logout Page/sign_out button')
	}
	
	private static def userName_div() {
		return findTestObject('Object Repository/TC_0_LOGIN/Logout Page/username div')
	}

	public static def sign_out() {
		WebUI.click(signIn_button())
		WebUI.verifyElementNotPresent(userName_div(), 0)
//		WebUI.verifyElementPresent(findTestObject('Object Repository/TC_0_LOGIN/Sign up page/login section'), 0)
		WebUI.comment("the user now is signed out")
	}
}
