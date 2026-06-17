import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import helpers.Helper as Helper
import java.nio.file.Files as Files
import java.nio.file.Paths as Paths
import java.nio.file.Path as Path
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import java.util.Arrays as Arrays
import file.FileFinder as FileFinder

String topFolder = 'Top_lvl_' + Helper.getRandomFolderName()

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebUI.click(findTestObject('Object Repository/Folders/createFolderIcon'))

WebUI.click(findTestObject('Object Repository/Folders/createFolder'))

WebUI.setText(findTestObject('Object Repository/Folders/inputFolderName'), topFolder)

WebUI.click(findTestObject('Object Repository/Folders/buttonOK'))

WebUI.delay(3)

WebUI.verifyElementPresent(findTestObject('Drag and drop/create_icone'), 5)

WebUI.click(findTestObject('Drag and drop/create_icone'))

WebUI.click(findTestObject('Drag and drop/Create_folder'))

String subFolderName = 'Sub_' + Helper.getRandomFolderName()

WebUI.setText(findTestObject('Folders/inputFolderName'), subFolderName)

WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.delay(3)

WebUI.back()

WebUI.delay(2)

WebUI.verifyElementPresent(findTestObject('Drag and drop/create_icone'), 5)

WebUI.click(findTestObject('Drag and drop/create_icone'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_Document'))

String DocName = 'Doc_' + Helper.getRandomFolderName()

WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), 
    DocName)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebUI.switchToWindowIndex(1)

WebUI.refresh()

WebUI.closeWindowIndex(1)

WebUI.switchToWindowIndex(0)

WebUI.refresh()

TestObject docObject = new TestObject('docObject')
docObject.addProperty(
	'xpath',
	ConditionType.EQUALS,
	"//tr[contains(@data-search-keys, '" + DocName + "')]"
)

TestObject folderObject = new TestObject('folderObject')
folderObject.addProperty(
	'xpath',
	ConditionType.EQUALS,
	"//tr[contains(@data-search-keys, '" + subFolderName + "')]"
)

WebUI.waitForElementPresent(docObject, 20)
WebUI.waitForElementPresent(folderObject, 20)

String sourceXpath = "//tr[contains(@data-search-keys, '" + DocName + "')]"
String targetXpath = "//tr[contains(@data-search-keys, '" + subFolderName + "')]"

Helper.dragAndDropElementToElement(sourceXpath, targetXpath)

WebElement btn = findFolder(subFolderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.delay(3)

def btn1 = findDoc(DocName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

assert DocName != null

WebUI.closeBrowser()



@Keyword
WebElement findFolder(String folderName) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(By.xpath(('//td[2]/span/a[contains(text(),\'' + folderName) + '\')]'))
}

@Keyword
WebElement findDoc(String DocName) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + DocName) + '\')]/td[1]/span'))
}

