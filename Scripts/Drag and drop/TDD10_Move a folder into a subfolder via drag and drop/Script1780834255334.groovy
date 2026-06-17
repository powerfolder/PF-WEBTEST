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

String subFolderName_1 = 'Sub_1_' + Helper.getRandomFolderName()

WebUI.setText(findTestObject('Folders/inputFolderName'), subFolderName_1)

WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.delay(3)

WebUI.back()

WebUI.delay(2)

WebUI.click(findTestObject('Drag and drop/create_icone'))

WebUI.click(findTestObject('Drag and drop/Create_folder'))

String subFolderName_2 = 'Sub_2_' + Helper.getRandomFolderName()

WebUI.setText(findTestObject('Folders/inputFolderName'), subFolderName_2)

WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.delay(3)

WebUI.back()

TestObject subFolderName_2_Object = new TestObject('subFolderName_2_Object')
subFolderName_2_Object.addProperty(
	'xpath',
	ConditionType.EQUALS,
	"//tr[contains(@data-search-keys, '" + subFolderName_2 + "')]"
)

TestObject subFolderName_1_Object = new TestObject('subFolderName_1_Object')
subFolderName_1_Object.addProperty(
	'xpath',
	ConditionType.EQUALS,
	"//tr[contains(@data-search-keys, '" + subFolderName_1 + "')]"
)

WebUI.waitForElementPresent(subFolderName_2_Object, 20)
WebUI.waitForElementPresent(subFolderName_1_Object, 20)

String sourceXpath = "//tr[contains(@data-search-keys, '" + subFolderName_2 + "')]"
String targetXpath = "//tr[contains(@data-search-keys, '" + subFolderName_1 + "')]"

Helper.dragAndDropElementToElement(sourceXpath, targetXpath)

WebElement btn = findFolder(subFolderName_1)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.delay(3)

def btn1 = findDoc(subFolderName_2)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

assert subFolderName_2 != null

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

