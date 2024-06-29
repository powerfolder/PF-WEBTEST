import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.By as By
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import java.util.Random as Random

String firstName = generateRandomString(8)
String lastName = generateRandomString(8)
String emailId = generateRandomEmail().toLowerCase()
String phone = generateRandomPhoneNumber()

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [:], FailureHandling.OPTIONAL)

WebUI.click(ObjectRepository.findTestObject('LeftNavigationIcons/account'))
WebUI.waitForPageLoad(10)
WebUI.click(ObjectRepository.findTestObject('Accounts/CreateButton'))
WebUI.waitForElementVisible(ObjectRepository.findTestObject('Accounts/ClickCreateAccount'), 10)
WebUI.click(ObjectRepository.findTestObject('Accounts/ClickCreateAccount'))

WebUI.setText(ObjectRepository.findTestObject('Accounts/InputUserOrEmail'), emailId)
WebUI.setText(ObjectRepository.findTestObject('Accounts/InputPassword'), 'Alexa@131190')
WebUI.setText(ObjectRepository.findTestObject('Accounts/InputFirstName'), firstName)
WebUI.setText(ObjectRepository.findTestObject('Accounts/InputLastName'), lastName)
WebUI.setText(ObjectRepository.findTestObject('Accounts/InputPhoneNo'), phone)
WebUI.setText(ObjectRepository.findTestObject('Accounts/InputQuota'), '5')
WebUI.click(ObjectRepository.findTestObject('Accounts/SaveButton'))

WebUI.delay(2)
WebUI.setText(ObjectRepository.findTestObject('Accounts/inputAccountSearch'), firstName)
WebUI.sendKeys(ObjectRepository.findTestObject('Accounts/inputAccountSearch'), Keys.chord(Keys.ENTER))

WebUI.delay(5)

WebDriver driver = DriverFactory.getWebDriver()

try {
	WebElement ClickOnAccount = driver.findElement(By.partialLinkText(firstName))
	WebUI.delay(1)
	ClickOnAccount.click()
} catch (Exception e) {
	WebElement ClickOnAccount = driver.findElement(By.xpath("//a[@class='pica-name' and @data-original-title ='${emailId}']"))
	WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(ClickOnAccount))
}


WebUI.verifyEqual(WebUI.getText(ObjectRepository.findTestObject('Accounts/VerifyEditPage')), 'Edit Account', FailureHandling.CONTINUE_ON_FAILURE)

WebUI.click(ObjectRepository.findTestObject('Accounts/AddFolder'))
WebUI.setText(ObjectRepository.findTestObject('Accounts/AddFolderByNameInput'), firstName)
WebUI.delay(2)
WebUI.click(ObjectRepository.findTestObject('Accounts/SelectCreateNew'))
WebUI.delay(1)
WebUI.click(ObjectRepository.findTestObject('Accounts/SelectFolder'))
WebUI.click(ObjectRepository.findTestObject('Accounts/SaveButton'))
WebUI.delay(2)

String actualText = WebUI.getText(ObjectRepository.findTestObject('Accounts/VerifyAlertMsg')).trim().toLowerCase()
String expectedText = 'account updated'
WebUI.verifyEqual(actualText.contains(expectedText), true, FailureHandling.CONTINUE_ON_FAILURE)

WebUI.setText(ObjectRepository.findTestObject('Accounts/inputAccountSearch'), firstName)
WebUI.waitForElementClickable(ObjectRepository.findTestObject('Accounts/checkAccount'), 10, FailureHandling.OPTIONAL)
WebUI.scrollToElement(ObjectRepository.findTestObject('Accounts/checkAccount'), 10) // Scroll to the element
WebUI.click(ObjectRepository.findTestObject('Accounts/checkAccount'))
WebUI.click(ObjectRepository.findTestObject('Accounts/DeleteButton'))
WebUI.delay(2)

String actualDeletedText = WebUI.getText(ObjectRepository.findTestObject('Accounts/VerifyDeleteMsg')).trim()
String expectedDeletedText = "Do you really want to delete ${emailId}?"
WebUI.verifyEqual(actualDeletedText, expectedDeletedText, FailureHandling.STOP_ON_FAILURE)

WebUI.click(ObjectRepository.findTestObject('Accounts/YesButton'))
WebUI.delay(2)
WebUI.closeBrowser()

String generateRandomString(int length) {
	String characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'
	Random random = new Random()
	StringBuilder randomString = new StringBuilder(length)

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
