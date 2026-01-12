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
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import com.kms.katalon.core.testobject.ConditionType

// Login and go to folders menu
WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.OPTIONAL)

// Vérifie si le titre de la page correspond à "Folders - PowerFolder"
assert WebUI.getWindowTitle().equals('Folders - PowerFolder')

// create toplvl folder
WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createFolder'))

String folderName = "TF8_" + RandomStringUtils.randomAlphanumeric(6)

WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)

WebUI.click(findTestObject('Folders/buttonOK'))

// create sublvl folder

WebUI.verifyElementClickable(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

String folderName1 = "TF8_subfolder_" + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))

WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'),
	folderName1)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

TestObject dynamicObject = new TestObject()

dynamicObject.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//span[text()='" + folderName1 + "']"
)

boolean exists = WebUI.verifyElementPresent(dynamicObject, 10, FailureHandling.OPTIONAL)

// go back to folderslist
WebUI.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

// move into toplvl folder

WebElement btn3 = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn3))

// mark sublvl folder

TestObject dynamicFolder = new TestObject('dynamicFolder')
dynamicFolder.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//*[contains(@data-search-keys, '" + folderName1 + "')]/td[1]/span"
)

WebUI.waitForElementVisible(dynamicFolder, 10)

WebUI.waitForElementClickable(dynamicFolder, 10)

WebUI.click(dynamicFolder)

// Download the folder
WebUI.click(findTestObject('Folders/downloadLink'))

// Save the parent window handle
WebDriver driver = DriverFactory.getWebDriver()
String parentWindow = driver.getWindowHandle()

// Open a new tab to check the downloads
JavascriptExecutor jsExecutor = (JavascriptExecutor) driver
jsExecutor.executeScript('window.open()')

// Switch to the new tab
Set<String> allWindowHandles = driver.getWindowHandles()
for (String winHandle : allWindowHandles) {
	if (!winHandle.equals(parentWindow)) {
		driver.switchTo().window(winHandle)
	}
}

// Navigate to the downloads page
WebUI.delay(5)
driver.get('chrome://downloads')

// Retry mechanism for fetching the file name
JavascriptExecutor downloadWindowExecutor = (JavascriptExecutor) driver
String fileName = ''
int retryCount = 0
boolean isFileNameRetrieved = false

while (retryCount < 5 && !isFileNameRetrieved) {
	try {
		fileName = downloadWindowExecutor.executeScript('return document.querySelector("downloads-manager").shadowRoot.querySelector("#downloadsList downloads-item").shadowRoot.querySelector("div#content #file-link").textContent').toString()
		isFileNameRetrieved = (fileName != null && !fileName.isEmpty())
	} catch (Exception e) {
		WebUI.delay(2)  // Wait for 2 seconds before retrying
		retryCount++
	}
}

if (!isFileNameRetrieved) {
	throw new RuntimeException('Failed to retrieve the downloaded file name after retries.')
}

String downloadSourceLink = downloadWindowExecutor.executeScript('return document.querySelector("downloads-manager").shadowRoot.querySelector("#downloadsList downloads-item").shadowRoot.querySelector("div#content #file-link").href').toString()

System.out.println('Downloaded File Name: ' + fileName)

// Verify the downloaded file name
WebUI.delay(5)
WebUI.verifyEqual(fileName, folderName1 + '.zip')

// Close the browser
WebUI.closeBrowser()



@Keyword
WebElement findFolder(String folderName) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(By.xpath(('//td[2]/span/a[contains(text(),\'' + folderName) + '\')]'))
}
