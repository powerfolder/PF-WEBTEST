import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import org.openqa.selenium.By as By
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import java.util.Random as Random

String firstName = generateRandomString(8)
String lastName = generateRandomString(8)
String emailId = generateRandomEmail()
emailId = emailId.toLowerCase()
String phone = generateRandomPhoneNumber()

WebUiBuiltInKeywords.callTestCase(TestCaseFactory.findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.OPTIONAL)

WebUiBuiltInKeywords.click(ObjectRepository.findTestObject('LeftNavigationIcons/account'))
WebUiBuiltInKeywords.waitForPageLoad(10)

WebUiBuiltInKeywords.click(ObjectRepository.findTestObject('Accounts/CreateButton'))
WebUiBuiltInKeywords.waitForElementVisible(ObjectRepository.findTestObject('Accounts/ClickCreateAccount'), 10)
WebUiBuiltInKeywords.click(ObjectRepository.findTestObject('Accounts/ClickCreateAccount'))

WebUiBuiltInKeywords.setText(ObjectRepository.findTestObject('Accounts/InputUserOrEmail'), emailId)
WebUiBuiltInKeywords.setText(ObjectRepository.findTestObject('Accounts/InputPassword'), 'Alexa@131190')
WebUiBuiltInKeywords.setText(ObjectRepository.findTestObject('Accounts/InputFirstName'), firstName)
WebUiBuiltInKeywords.setText(ObjectRepository.findTestObject('Accounts/InputLastName'), lastName)
WebUiBuiltInKeywords.setText(ObjectRepository.findTestObject('Accounts/InputPhoneNo'), phone)
WebUiBuiltInKeywords.setText(ObjectRepository.findTestObject('Accounts/InputQuota'), '5')
WebUiBuiltInKeywords.click(ObjectRepository.findTestObject('Accounts/SaveButton'))

WebUiBuiltInKeywords.delay(2)
WebUiBuiltInKeywords.setText(ObjectRepository.findTestObject('Accounts/inputAccountSearch'), firstName)
WebUiBuiltInKeywords.sendKeys(ObjectRepository.findTestObject('Accounts/inputAccountSearch'), Keys.chord(Keys.ENTER))

WebDriver driver = DriverFactory.getWebDriver()

try {
	WebElement ClickOnAccount = driver.findElement(By.partialLinkText(firstName))
	WebUiBuiltInKeywords.delay(1)
	ClickOnAccount.click()
} catch (Exception e) {
	WebElement ClickOnAccount = driver.findElement(By.xpath("//a[@class='pica-name' and @data-original-title ='${emailId}']"))
	WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(ClickOnAccount))
}

WebUiBuiltInKeywords.verifyEqual(WebUiBuiltInKeywords.getText(ObjectRepository.findTestObject('Accounts/VerifyEditPage')), 'Edit Account', FailureHandling.CONTINUE_ON_FAILURE)

WebUiBuiltInKeywords.click(ObjectRepository.findTestObject('Accounts/AddFolder'))
WebUiBuiltInKeywords.setText(ObjectRepository.findTestObject('Accounts/AddFolderByNameInput'), firstName)
WebUiBuiltInKeywords.delay(2)
WebUiBuiltInKeywords.click(ObjectRepository.findTestObject('Accounts/SelectCreateNew'))
WebUiBuiltInKeywords.delay(1)
WebUiBuiltInKeywords.click(ObjectRepository.findTestObject('Accounts/SelectFolder'))
WebUiBuiltInKeywords.click(ObjectRepository.findTestObject('Accounts/SaveButton'))
WebUiBuiltInKeywords.delay(2)

String actualText = WebUiBuiltInKeywords.getText(ObjectRepository.findTestObject('Accounts/VerifyAlertMsg')).trim().toLowerCase()
String expectedText = 'account updated'

WebUiBuiltInKeywords.verifyEqual(actualText.contains(expectedText), true, FailureHandling.CONTINUE_ON_FAILURE)

WebUiBuiltInKeywords.setText(ObjectRepository.findTestObject('Accounts/inputAccountSearch'), firstName)
WebUiBuiltInKeywords.waitForElementClickable(ObjectRepository.findTestObject('Accounts/checkAccount'), 10, FailureHandling.OPTIONAL)
WebUiBuiltInKeywords.click(ObjectRepository.findTestObject('Accounts/checkAccount'))
WebUiBuiltInKeywords.click(ObjectRepository.findTestObject('Accounts/DeleteButton'))
WebUiBuiltInKeywords.delay(2)

String actualDeletedText = WebUiBuiltInKeywords.getText(ObjectRepository.findTestObject('Accounts/VerifyDeleteMsg')).trim()
String expectedDeletedText = "Do you really want to delete ${emailId}?"

WebUiBuiltInKeywords.verifyEqual(actualDeletedText, expectedDeletedText, FailureHandling.STOP_ON_FAILURE)

WebUiBuiltInKeywords.click(ObjectRepository.findTestObject('Accounts/YesButton'))
WebUiBuiltInKeywords.delay(2)
WebUiBuiltInKeywords.closeBrowser()

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
