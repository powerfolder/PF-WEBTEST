import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.openqa.selenium.By as By
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

String firstName = generateRandomString(8)

String lastName = generateRandomString(8)

String emailId = generateRandomEmail()

Emailid = emailId.toLowerCase()

String phone = generateRandomPhoneNumber()

WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.OPTIONAL)

WebUI.click(findTestObject('LeftNavigationIcons/account'))

WebUI.click(findTestObject('Accounts/CreateButton'))

WebUI.click(findTestObject('Accounts/ClickCreateAccount'))

WebUI.setText(findTestObject('Accounts/InputUserOrEmail'), emailId)

WebUI.setText(findTestObject('Accounts/InputPassword'), 'Alexa@131190')

WebUI.setText(findTestObject('Accounts/InputFirstName'), firstName)

WebUI.setText(findTestObject('Accounts/InputLastName'), lastName)

WebUI.setText(findTestObject('Accounts/InputPhoneNo'), phone)

WebUI.setText(findTestObject('Accounts/InputQuota'), '5')

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.delay(7)

WebUI.setText(findTestObject('Accounts/inputAccountSearch'), firstName)

WebUI.sendKeys(findTestObject('Accounts/inputAccountSearch'), Keys.chord(Keys.ENTER))

WebDriver driver = DriverFactory.getWebDriver()

try {
    WebElement ClickOnAccount = driver.findElement(By.partialLinkText(firstName))

    WebUI.delay(2)

    ClickOnAccount.click()
}
catch (Exception e) {
    WebElement ClickOnAccount = driver.findElement(By.xpath("//a[@class='pica-name' and @data-original-title ='$emailId']"))

    JavascriptExecutor executor = ((driver) as JavascriptExecutor)

    executor.executeScript('arguments[0].click()', ClickOnAccount)
} 

WebUI.verifyEqual(WebUI.getText(findTestObject('Accounts/VerifyEditPage')), 'Edit Account', FailureHandling.CONTINUE_ON_FAILURE)

WebUI.click(findTestObject('Accounts/ActiveButton'))

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.delay(3)

WebUI.waitForElementClickable(findTestObject('Accounts/checkAccount'), 30, FailureHandling.OPTIONAL)

WebUI.click(findTestObject('Accounts/checkAccount'))

WebUI.click(findTestObject('Accounts/DeleteButton'))

WebUI.delay(4)

String actualText = WebUI.getText(findTestObject('Accounts/VerifyDeleteMsg'))

String expectedText = "Do you really want to delete $emailId?"

WebUI.verifyEqual(actualText, expectedText, FailureHandling.STOP_ON_FAILURE)

WebUI.delay(3)

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

