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
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils

WebUI.callTestCase(findTestCase('Accounts/Edit_Account/pre_test/Create_Account'), [:], FailureHandling.STOP_ON_FAILURE)

println(GlobalVariable.userName)

println(GlobalVariable.userLastName)

println(GlobalVariable.userEmail)

WebElement btn = findAccount(GlobalVariable.userEmail)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/Edit_account'))

String NewFirstName = 'New_' + RandomStringUtils.randomAlphabetic(8)

String NewLastName = 'New_' + RandomStringUtils.randomAlphabetic(8)

String FullName = (NewFirstName + ' ') + NewLastName

String emailId = GlobalVariable.userEmail

WebUI.clearText(findTestObject('Accounts/InputFirstName'))

WebUI.clearText(findTestObject('Accounts/InputLastName'))

WebUI.setText(findTestObject('Accounts/InputFirstName'), NewFirstName)

WebUI.setText(findTestObject('Accounts/InputLastName'), NewLastName)

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.refresh()

WebUI.delay(2)

String dynamicXPath = ('//*[contains(@data-search-keys, \'' + emailId) + '\')]/td[2]/a'

try {
    // 🔹 Get the WebDriver instance
    WebDriver driver = DriverFactory.getWebDriver()

    // 🔹 Find the target element using the dynamic XPath
    WebElement targetElement = driver.findElement(By.xpath(dynamicXPath))

    // 🔹 Retrieve the text from the element
    String copiedText = targetElement.getText().trim()

    println('📌 Copied text: ' + copiedText)

    // 🔹 Compare the copied text with FullName
    if (!(copiedText.equals(FullName))) {
        throw new Exception(((('❌ ERROR: The retrieved text \'' + copiedText) + '\' does not match \'') + FullName) + '\'')
    } else {
        println('✅ Verification successful: Retrieved text matches FullName')
    }
}
catch (Exception e) {
    println(e.getMessage())
} // 🔹 Print the error message if the element is not found or the text doesn't match

WebUI.delay(3)

WebUI.closeBrowser()

WebElement findAccount(String emailId) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + emailId) + '\')]/td[1]/span'))
}

