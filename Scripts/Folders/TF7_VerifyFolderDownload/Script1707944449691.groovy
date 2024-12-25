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
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.DataFlavor as DataFlavor
import java.nio.file.Path as Path
import java.nio.file.Files as Files
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import org.openqa.selenium.chrome.ChromeDriver as ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions as ChromeOptions
import java.text.SimpleDateFormat as SimpleDateFormat

// Pre-test case call
WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.OPTIONAL)

// Generate a random folder name
String folderName = getRandomFolderName()

// Create folder
WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))

// Verify folder creation interface
WebUI.verifyEqual(WebUI.getText(findTestObject('lang/getCreateText')), 'Create', FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyEqual(WebUI.getText(findTestObject('lang/getFolderNameLabelText')), 'Create a new Folder', FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)

// Set folder name and create the folder
WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)
WebUI.click(findTestObject('Folders/buttonOK'))

// Search for the created folder
WebUI.setText(findTestObject('Folders/inputSearch'), folderName)

// Get the WebDriver instance
WebDriver driver = DriverFactory.getWebDriver()

// Find the folder element using the correct XPath with string concatenation
WebElement folderNameElement = driver.findElement(By.xpath("//td/span/a[text()='" + folderName + "']/ancestor::tr/td[1]/span"))

// Delay to ensure the folder is present
WebUI.delay(5)

// Click on the folder element
folderNameElement.click()

// Download the folder
WebUI.click(findTestObject('Folders/downloadLink'))

// Save the parent window handle
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
WebUI.verifyEqual(fileName, folderName + '.zip')

// Close the browser
WebUI.closeBrowser()

// Function to generate a random folder name
String getRandomFolderName() {
	return 'FDTF7' + getTimestamp()
}

// Function to get a formatted timestamp
String getTimestamp() {
	Date todaysDate = new Date()
	String formattedDate = new SimpleDateFormat('dd_MMM_yyyy_hh_mm_ss').format(todaysDate)
	return formattedDate
}
