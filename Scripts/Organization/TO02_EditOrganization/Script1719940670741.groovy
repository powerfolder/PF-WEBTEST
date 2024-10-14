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
import java.text.SimpleDateFormat as SimpleDateFormat
import java.util.Calendar as Calendar
import java.util.Date as Date
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils

WebUI.callTestCase(findTestCase('Organization/Pre_test/Create_Org'), [:], FailureHandling.STOP_ON_FAILURE)

GlobalVariable.organisationName = ('Organisation_' + RandomStringUtils.randomNumeric(4))

Organization_rename = ('Rename_Org_' + RandomStringUtils.randomNumeric(4))

WebUI.setText(findTestObject('Organization/InputName'), Organization_rename)

WebUI.setText(findTestObject('Organization/EnterNotes'), 'We are able to edate our informations')

WebUI.click(findTestObject('Organization/SaveButton'))

WebUI.delay(2)

WebElement btn1 = findORG(Organization_rename)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

WebUI.delay(2)

String actualText = WebUI.getAttribute(findTestObject('Organization/InputName'), 'value') 

WebUI.verifyMatch(actualText, Organization_rename, false) 


WebUI.click(findTestObject('Organization/Page_Organizations - PowerFolder/button_Cancel_organization'))

WebUI.closeBrowser()

WebElement findORG(String Organization_name) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//a[contains(text(),\'' + Organization_name) + '\')]'))
}

