import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberBuiltinKeywords
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGBuiltinKeywords
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as WindowsBuiltinKeywords
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import java.util.Arrays as Arrays
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('file_objects/document/Page_Federation - PowerFolder/lang_Preferences'))

WebUI.click(findTestObject('file_objects/document/Page_Preferences - PowerFolder/a_Configure'))

WebUI.click(findTestObject('file_objects/document/Page_Preferences - PowerFolder/lang_Open files in web URL'))

WebUI.setText(findTestObject('file_objects/document/Page_Preferences - PowerFolder/input_ONLYOFFICE'), 'https://oo.powerfolder.xxx')

WebUI.click(findTestObject('file_objects/document/Page_Preferences - PowerFolder/button_Save'))

WebUI.callTestCase(findTestCase('File/Pre_test/crete a file without login'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.delay(3)

WebUI.switchToWindowIndex('1', FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementPresent(findTestObject('file_objects/document/Page_Open - PowerFolder/span_Unable to create document'), 
    3)

WebUI.closeWindowIndex('1')

WebUI.switchToWindowIndex('0', FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('file_objects/document/Page_Federation - PowerFolder/lang_Preferences'))

WebUI.click(findTestObject('file_objects/document/Page_Preferences - PowerFolder/a_Configure'))

WebUI.click(findTestObject('file_objects/document/Page_Preferences - PowerFolder/lang_Open files in web URL'))

WebUI.setText(findTestObject('file_objects/document/Page_Preferences - PowerFolder/input_ONLYOFFICE'), 'https://oo.powerfolder.com')

WebUI.click(findTestObject('file_objects/document/Page_Preferences - PowerFolder/button_Save'))

WebUI.callTestCase(findTestCase('File/Pre_test/crete a file without login'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.refresh()

WebUI.switchToWindowIndex('0', FailureHandling.STOP_ON_FAILURE)

WebUI.delay(3)

String DocName = GlobalVariable.Document

def btn = findDoc(DocName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

assert DocName != null

WebUI.closeBrowser()

@Keyword
WebElement findDoc(String DocName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + DocName) + '\')]/td[1]/span'))
}

