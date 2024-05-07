import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberBuiltinKeywords
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
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
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.testobject.TestObject as TestObject

WebUiBuiltInKeywords.callTestCase(findTestCase('File/Pre_test/Create_folder'), [:], FailureHandling.STOP_ON_FAILURE)

WebUiBuiltInKeywords.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

WebUiBuiltInKeywords.verifyElementClickable(findTestObject('file_objects/text file/Page_Folders - PowerFolder/Create Text File'))

WebUiBuiltInKeywords.click(findTestObject('file_objects/text file/Page_Folders - PowerFolder/Create Text File'))

String characters = 'äöüß'

String textFileName = (('text_file' + RandomStringUtils.randomNumeric(2)) + '_') + RandomStringUtils.random(2, characters)

WebUiBuiltInKeywords.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), 
    textFileName)

WebUiBuiltInKeywords.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebUI.switchToWindowIndex('1')

WebUI.verifyElementNotPresent(findTestObject('file_objects/document/Page_Open - PowerFolder/span_Unable to create document'), 
    3)

WebUI.refresh()

WebUiBuiltInKeywords.delay(15)

WebUiBuiltInKeywords.switchToWindowUrl('https://lab.powerfolder.net:8666/folderstable')

WebUiBuiltInKeywords.refresh()

WebUiBuiltInKeywords.delay(2)

def btn = findDoc(textFileName)

WebUiBuiltInKeywords.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

assert textFileName != null

WebUiBuiltInKeywords.delay(2)

WebUiBuiltInKeywords.closeBrowser()

@Keyword
WebElement findDoc(String textFileName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + textFileName) + '\')]/td[1]/span'))
}

