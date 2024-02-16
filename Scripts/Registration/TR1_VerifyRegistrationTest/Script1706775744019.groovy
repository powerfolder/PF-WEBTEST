import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement


WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl(GlobalVariable.URL)
WebUI.verifyEqual(WebUI.getWindowTitle(), 'Login - PowerFolder')
WebUI.verifyEqual(WebUI.getAttribute(findTestObject('Login/poweredBy'), 'href'), 'https://www.powerfolder.com/')
WebUI.verifyEqual(WebUI.getAttribute(findTestObject('Login/documentationLink'), 'href'), 'https://wiki.powerfolder.com/')
WebUI.verifyElementClickable(findTestObject('Login/registerNewAccountLink'))
WebUI.click(findTestObject('Registration/ClickOnRegisterNewAccount'))
String emailId = generateRandomEmail()
String firstName = generateRandomString(4)+'Test'
String lastName = generateRandomString(4)+'Test'
WebUI.setText(findTestObject('Registration/inputEmail'), emailId)
WebUI.setText(findTestObject('Registration/EnterFirstName'), firstName)
WebUI.setText(findTestObject('Registration/EnterLastName'), lastName)
WebUI.setText(findTestObject('Registration/EnterPassword'), "Alexa@1311900")
WebUI.click(findTestObject('Registration/AcceptTermsAndConditions'))
WebUI.click(findTestObject('Registration/RegisterButton'))
WebUI.verifyEqual(WebUI.getText(findTestObject('Registration/VerifyRegistrationMsg')), 'Registration successful. You have received an e-mail with an activation link.')
WebUI.delay(3)
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl("https://yopmail.com/")
WebUI.setText(findTestObject('YopMail/YopMailSearchMailBar'), emailId)
WebUI.click(findTestObject('YopMail/ArrowNexrButton'))
WebUI.switchToFrame(findTestObject('YopMail/IFrame'),10)


WebUI.delay(3)
WebUI.click(findTestObject('YopMail/ActivateAccount'))
WebUI.delay(3)
String newWindowHandle = WebUI.switchToWindowTitle('Login - PowerFolder')
WebUI.switchToWindowTitle("Login - PowerFolder")
WebUI.setText(findTestObject('Registration/EnterLoginPassword'), "Alexa@1311900")

WebUI.delay(3)
WebUI.click(findTestObject('Registration/LoginButton'))
WebUI.delay(3)

assert WebUI.getWindowTitle().equals('Folders - PowerFolder')



String generateRandomEmail() {
	return generateRandomString(8) + "@yopmail.com"
}



String generateRandomString(int length) {
	String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
	StringBuilder randomString = new StringBuilder()
	Random random = new Random()

	for (int i = 0; i < length; i++) {
		randomString.append(characters.charAt(random.nextInt(characters.length())))
	}

	return randomString.toString()
}
