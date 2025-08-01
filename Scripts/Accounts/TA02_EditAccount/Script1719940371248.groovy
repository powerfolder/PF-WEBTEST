import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

String firstName = generateRandomString(8)
String lastName = generateRandomString(8)
String emailId = generateRandomEmail().toLowerCase()
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

WebUI.click(findTestObject('Accounts/SaveButton'))
WebUI.delay(4)

WebUI.setText(findTestObject('Accounts/inputAccountSearch'), firstName)
WebUI.sendKeys(findTestObject('Accounts/inputAccountSearch'), Keys.chord(Keys.ENTER))

WebUI.delay(2)

WebDriver driver = DriverFactory.getWebDriver()

try {
    WebElement clickOnAccount = driver.findElement(By.partialLinkText(firstName))
    WebUI.delay(2)
    clickOnAccount.click()
} catch (Exception e) {
    WebElement clickOnAccount = driver.findElement(By.xpath("//a[@class='pica-name' and @data-original-title ='$emailId']"))
    JavascriptExecutor executor = driver as JavascriptExecutor
    executor.executeScript('arguments[0].click()', clickOnAccount)
} 

WebUI.click(findTestObject('Accounts/editAccount'), FailureHandling.OPTIONAL)

WebUI.verifyEqual(WebUI.getText(findTestObject('Accounts/VerifyEditPage')), 'Edit Account', FailureHandling.CONTINUE_ON_FAILURE)

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.delay(10)

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
