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

println(GlobalVariable.userEmail)

WebElement btn = findAccount(GlobalVariable.userEmail)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/Edit_account'))

WebUI.clearText(findTestObject('Accounts/InputUserOrEmail'))

String NewEmail = generateRandomEmail().toLowerCase()

WebUI.setText(findTestObject('Accounts/InputUserOrEmail'), NewEmail)

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.refresh()

// Wait for the page to reload
WebUI.delay(3)

// Re-fetch the list of accounts
WebDriver driver = DriverFactory.getWebDriver()

// Check if the element with the old email still exists
List<WebElement> elements = driver.findElements(By.xpath(('//*[contains(@data-search-keys, \'' + GlobalVariable.userEmail) + 
        '\')]/td[1]/span'))

// Assertion to ensure the old email is no longer present
assert elements.size() == 0 : 'The old email is still present in the list, test failed.'

// Update GlobalVariable.userEmail to reflect the new email
GlobalVariable.userEmail = NewEmail

WebElement btn1 = findAccount(GlobalVariable.userEmail)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/Edit_account'))

WebUI.verifyElementAttributeValue(findTestObject('Accounts/InputUserOrEmail'), 'value', GlobalVariable.userEmail, 5)

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.closeBrowser()

WebElement findAccount(String emailId) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + emailId) + '\')]/td[1]/span'))
}

String generateRandomEmail() {
    return ('New' + RandomStringUtils.randomNumeric(4)) + '@email.com'
}

