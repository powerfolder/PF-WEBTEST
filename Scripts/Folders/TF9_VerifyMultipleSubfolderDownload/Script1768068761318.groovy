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

// create second sublvlfolder

WebUI.verifyElementClickable(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

String folderName2 = "TF8_subfolder2_" + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))

WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'),
	folderName2)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

TestObject dynamicObject2 = new TestObject()

dynamicObject2.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//span[text()='" + folderName2 + "']"
)

boolean exists2 = WebUI.verifyElementPresent(dynamicObject2, 10, FailureHandling.OPTIONAL)

// move into toplvl folder

WebUI.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebElement btn4 = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn4))

// mark sublvl folder1

TestObject dynamicFolder = new TestObject('dynamicFolder')
dynamicFolder.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//*[contains(@data-search-keys, '" + folderName1 + "')]/td[1]/span"
)

WebUI.waitForElementVisible(dynamicFolder, 10)

WebUI.waitForElementClickable(dynamicFolder, 10)

WebUI.click(dynamicFolder)

// select all elements of the toplvl folder

WebUI.click(findTestObject('Folders/Page_Folders - PowerFolder/select_all_checkbox'))

// Download the subfolders
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

// extract items of chromes download-page
JavascriptExecutor js = (JavascriptExecutor) driver
List<Map<String, String>> downloadedFiles = []
int retryCount = 0
boolean filesRetrieved = false

while (retryCount < 5 && !filesRetrieved) {
    try {
        downloadedFiles = (List<Map<String, String>>) js.executeScript("""
            let items = document
              .querySelector('downloads-manager')
              .shadowRoot
              .querySelector('#downloadsList')
              .querySelectorAll('downloads-item');

            let results = [];
            items.forEach(item => {
                let root = item.shadowRoot;
                let fileLink = root.querySelector('#file-link');
                if (fileLink) {
                    results.push({
                        name: fileLink.textContent.trim(),
                        href: fileLink.href
                    });
                }
            });
            return results;
        """);

        filesRetrieved = (downloadedFiles != null && downloadedFiles.size() > 0)
    } catch (Exception e) {
        WebUI.delay(2)
        retryCount++
    }
}

if (!filesRetrieved) {
    throw new RuntimeException('No downloaded files found after retries.')
}

// debug
/*downloadedFiles.each { file ->
	println "Downloaded File: ${file.name}"
	println "Source URL: ${file.href}"
}*/

// Verify the downloaded subfolders
WebUI.delay(5)
String expectedSubfolder = folderName1 + '.zip'
boolean found = downloadedFiles.any { it.name == expectedSubfolder }
WebUI.verifyEqual(found, true)

String expectedSubfolder2 = folderName2 + '.zip'
boolean found2 = downloadedFiles.any { it.name == expectedSubfolder2 }
WebUI.verifyEqual(found, true)

// Close the browser
WebUI.closeBrowser()

@Keyword
WebElement findFolder(String folderName) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(By.xpath(('//td[2]/span/a[contains(text(),\'' + folderName) + '\')]'))
}
