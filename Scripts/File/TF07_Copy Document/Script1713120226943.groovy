import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
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

WebUI.callTestCase(findTestCase('File/Pre_test/Create_Doc'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementClickable(findTestObject('file_objects/document/copy/Page_Folders - PowerFolder/span_copy'))

WebUI.click(findTestObject('file_objects/document/copy/Page_Folders - PowerFolder/span_copy'))

String characters = 'äöüß'

String CopyName = ((('Copy_' + RandomStringUtils.randomNumeric(2)) + '_') + RandomStringUtils.random(2, characters)) + '.docx'

WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), 
    CopyName)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebUI.refresh()

WebUI.delay(3)

def btn = findDoc(CopyName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

assert CopyName != null

WebUI.closeBrowser()

WebElement findDoc(String DocName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + DocName) + '\')]/td[1]/span'))
}

