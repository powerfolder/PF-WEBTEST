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
import java.text.SimpleDateFormat as SimpleDateFormat
import java.util.Calendar as Calendar
import java.util.Date as Date
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement

Organization_name = GlobalVariable.organisationName

println('Organization_name: ' + Organization_name)

WebUI.callTestCase(findTestCase('Organization/Pre_test/Create_Org'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Organization/SelectBranding'))

WebUI.setText(findTestObject('Organization/SetTextIconAndColor'), '#1f1a1a')

WebUI.setText(findTestObject('Organization/SecondaryColor'), '#5f1f1f')

WebUI.setText(findTestObject('Organization/BackgroundColor'), '#972e2e')

WebUI.click(findTestObject('Organization/SaveButton'))

WebUI.refresh()

WebUI.delay(3)

Organization_name = GlobalVariable.organisationName

println('Organization_name: ' + Organization_name)

WebElement btn1 = findORG(Organization_name)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

WebUI.click(findTestObject('Organization/SelectBranding'))

String actualText = WebUI.getAttribute(findTestObject('Organization/SetTextIconAndColor'), 'value')

WebUI.verifyMatch(actualText, '#1f1a1a', false)

WebUI.click(findTestObject('Organization/SaveButton'))

WebUI.delay(2)

WebUI.closeBrowser()

WebElement findORG(String Organization_name) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//a[contains(text(),\'' + Organization_name) + '\')]'))
}

