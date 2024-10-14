import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

String firstName = generateRandomString(8)
String lastName = generateRandomString(8)
String emailId = generateRandomEmail()
String Emailid = emailId.toLowerCase()
String phone = generateRandomPhoneNumber()

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.OPTIONAL)

WebUI.click(findTestObject('LeftNavigationIcons/account'))
WebUI.click(findTestObject('Accounts/CreateButton'))
WebUI.click(findTestObject('Accounts/ClickCreateAccount'))

WebUI.setText(findTestObject('Accounts/InputUserOrEmail'), emailId)
WebUI.setText(findTestObject('Accounts/InputPassword'), 'Alexa@131190')
WebUI.setText(findTestObject('Accounts/InputFirstName'), firstName)
WebUI.setText(findTestObject('Accounts/InputLastName'), lastName)
WebUI.setText(findTestObject('Accounts/InputPhoneNo'), phone)
WebUI.setText(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/account_storage_overwiew'), '5')

// Add wait for element to be clickable
WebUI.waitForElementClickable(findTestObject('Accounts/GrantAccessNowButton'), 30, FailureHandling.STOP_ON_FAILURE)

// Debugging: Check if the element exists before clicking
if (WebUI.verifyElementPresent(findTestObject('Accounts/GrantAccessNowButton'), 30, FailureHandling.OPTIONAL)) {
	WebUI.click(findTestObject('Accounts/GrantAccessNowButton'))
} else {
	WebUI.comment('GrantAccessNowButton not found')
}

WebUI.click(findTestObject('Accounts/SaveButton'))
WebUI.setText(findTestObject('Accounts/inputAccountSearch'), firstName)
WebUI.sendKeys(findTestObject('Accounts/inputAccountSearch'), Keys.chord(Keys.ENTER))

WebUI.delay(4)
WebUI.waitForElementClickable(findTestObject('Accounts/checkAccount'), 30, FailureHandling.OPTIONAL)
WebUI.click(findTestObject('Accounts/checkAccount'))
WebUI.click(findTestObject('Accounts/DeleteButton'))

WebUI.delay(4)
String actualText = WebUI.getText(findTestObject('Accounts/VerifyDeleteMsg'))
String expectedText = "Do you really want to delete $emailId?"

WebUI.verifyEqual(actualText, expectedText, FailureHandling.STOP_ON_FAILURE)
WebUI.click(findTestObject('Accounts/YesButton'))
WebUI.closeBrowser()

String generateRandomString(int length) {
	String characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'
	StringBuilder randomString = new StringBuilder()
	Random random = new Random()

	for (int i = 0; i < length; i++) {
		randomString.append(characters.charAt(random.nextInt(characters.length())))
	}
	
	return randomString.toString().toLowerCase()
}

String generateRandomEmail() {
	return generateRandomString(8) + '@yopmail.com'
}

String generateRandomPhoneNumber() {
	Random random = new Random()
	return String.format('(%03d) %03d-%04d', random.nextInt(1000), random.nextInt(1000), random.nextInt(10000))
}
