package _login_page
import common_keywords.Common_Keywords as common

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

public class loginAction {

	public static def signIn_span() {
		return findTestObject('Object Repository/TC_0_LOGIN/Sign up page/sign_in span')
	}

	public static def email_txtfield() {
		return findTestObject('Object Repository/TC_0_LOGIN/Login Page/Email txtField')
	}

	public static def password_txtfield() {
		return findTestObject('Object Repository/TC_0_LOGIN/Login Page/Password txtField')
	}

	public static def signIn_button() {
		return findTestObject('Object Repository/TC_0_LOGIN/Login Page/sign_in button')
	}


	public static def signIn(String Email, String Password) {
		WebUI.click(signIn_span())
		WebUI.setText(email_txtfield(), Email)
		WebUI.setText(password_txtfield(), Password)
		WebUI.click(signIn_button())
		WebUI.comment("logged in successfully")
	}
}
