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
// create toplvl folder A
WebUiBuiltInKeywords.callTestCase(findTestCase('File/Pre_test/Create_folder'), [:], FailureHandling.STOP_ON_FAILURE)

String folderNameA = GlobalVariable.folderName
println(folderNameA)

// create toplvl folder B
WebUiBuiltInKeywords.callTestCase(findTestCase('File/Pre_test/Create_folder'), [:], FailureHandling.STOP_ON_FAILURE)
String folderNameB = GlobalVariable.folderName
println(folderNameB)

// create doc in toplvl folder B
WebUiBuiltInKeywords.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

WebUiBuiltInKeywords.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_Document'))

String DocName = 'Doc_num_' + RandomStringUtils.randomNumeric(4)

WebUiBuiltInKeywords.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), 
    DocName)

WebUiBuiltInKeywords.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebUiBuiltInKeywords.delay(15)

WebUiBuiltInKeywords.closeWindowIndex(1)

WebUiBuiltInKeywords.switchToWindowIndex(0)

WebUI.refresh()

WebUiBuiltInKeywords.delay(2)

WebElement btn1 = findDoc(DocName)

// check if doc is created

boolean isDocCreated = btn1.isDisplayed()

WebUiBuiltInKeywords.verifyEqual(isDocCreated, true)

WebUiBuiltInKeywords.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

assert DocName != null

// cut created doc

WebUiBuiltInKeywords.verifyElementClickable(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - Cut/span_Cut'))

WebUiBuiltInKeywords.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - Cut/span_Cut'))

// search for toplvl folder A and paste doc

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebElement btn2 = findFolder(folderNameA)

WebUiBuiltInKeywords.executeJavaScript('arguments[0].click()', Arrays.asList(btn2))

WebUiBuiltInKeywords.click(findTestObject('file_objects/document/span_paste/span_Paste'))

// check if doc is pasted in successfully

WebElement btn3 = findDoc(DocName)

boolean isDocPasted = btn3.isDisplayed()

WebUiBuiltInKeywords.verifyEqual(isDocPasted, true)

// Fermer le navigateur
WebUiBuiltInKeywords.closeBrowser()

@Keyword
WebElement findDoc(String DocName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + DocName) + '\')]/td[1]/span'))
}

@Keyword
WebElement findFolder(String folderName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//a[contains(text(),\'' + folderName) + '\')]'))
}
