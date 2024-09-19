package common_keywords
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.WebElement

import com.github.javafaker.Faker
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI




public class Common_Keywords {

	public static def swithToIframe() {
		WebUI.switchToFrame(findTestObject("Object Repository/Iframe_live"), 10)
	}

	public static void switchToDefaultFrame() {
		WebUI.switchToDefaultContent()
		WebUI.comment("Switched back to the default frame")
	}


	public static int countElementsByCss(String cssSelector, String elementName) {
		TestObject testObject = new TestObject(elementName)
		testObject.addProperty('css', ConditionType.EQUALS, cssSelector)
		List<WebElement> elements = WebUiCommonHelper.findWebElements(testObject, 10)

		WebUI.comment(elementName + " count = " + elements.size())
		return elements.size()
	}


	public static String generateFakeEmail() {
		Faker faker=new Faker()
		String fakeEmail = faker.internet().emailAddress()
		WebUI.comment("Generated Fake Email: " + fakeEmail)
		return fakeEmail
	}


	public static String generateFakeName() {
		Faker faker = new Faker()
		String name = faker.name().fullName()
		WebUI.comment("Generated Fake Name: " + name)

		return name
	}

	public static <T> T selectRandomElement(List<T> list) {
		if (list == null || list.isEmpty()) {
			throw new IllegalArgumentException("The list is empty or null.");
		}
		Random random = new Random();
		int randomIndex = random.nextInt(list.size());
		return list.get(randomIndex);
	}
}
