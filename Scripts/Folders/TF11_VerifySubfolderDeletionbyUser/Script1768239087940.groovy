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
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By

// create User Account

WebUI.callTestCase(findTestCase('My Account/Pre_test/Create Account'), [:], FailureHandling.STOP_ON_FAILURE)

// create toplvl folder
WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createFolder'))

String folderName = "TF11_" + RandomStringUtils.randomAlphanumeric(6)

WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)

WebUI.click(findTestObject('Folders/buttonOK'))

// create sublvl folder

WebUI.verifyElementClickable(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

String folderName1 = "TF11_subfolder_" + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))

WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'),folderName1)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

TestObject dynamicObject = new TestObject()

dynamicObject.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//span[text()='" + folderName1 + "']"
)

boolean exists = WebUI.verifyElementPresent(dynamicObject, 10, FailureHandling.OPTIONAL)

// go to folderslist
WebUI.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

// move into toplvl folder
WebElement btn3 = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn3))

// mark sublvl folder 

WebUI.setText(findTestObject('Folders/inputSearch'), folderName1)

TestObject dynamicFolder = new TestObject('dynamicFolder')
dynamicFolder.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//*[contains(@data-search-keys, '" + folderName1 + "')]/td[1]/span"
)

WebUI.waitForElementVisible(dynamicFolder, 10)

WebUI.waitForElementClickable(dynamicFolder, 10)

WebUI.click(dynamicFolder)

// delete subfolder

WebUI.click(findTestObject('Folders/buttonDelete'))

String deleteAlertText = WebUI.getText(findTestObject('lang/getDeleteAlertText'))

String expectedText = ('Do you really want to delete ' + folderName1) + '?'

WebUI.click(findTestObject('Folders/yesButton_Delete'))

WebUI.verifyEqual(deleteAlertText, expectedText)

WebUI.delay(3)

boolean notPresent = WebUI.waitForElementNotPresent(dynamicFolder, 10)

WebUI.verifyEqual(notPresent, true)

WebUI.closeBrowser()


WebElement findFolder(String folderName) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(By.xpath(('//td[2]/span/a[contains(text(),\'' + folderName) + '\')]'))
}

