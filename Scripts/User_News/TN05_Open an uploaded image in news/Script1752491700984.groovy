import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.annotation.Keyword as Keyword
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
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import java.util.Arrays as Arrays
import java.util.Date as Date
import java.util.Random as Random
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import java.util.Date
import java.util.Random
import java.util.Set

// === Test Case Execution ===

WebUI.callTestCase(findTestCase('User_News/Pre_test/Create_user'), [:], FailureHandling.STOP_ON_FAILURE)

println(GlobalVariable.userEmail)

// Login

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

WebUI.setText(findTestObject('Login/inputEmail'), GlobalVariable.userEmail)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(3)

// Create Folder

GlobalVariable.folderName = getRandomFolderName()

WebUI.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebUI.click(findTestObject('Object Repository/Folders/createFolderIcon'))

WebUI.click(findTestObject('Object Repository/Folders/createFolder'))

WebUI.setText(findTestObject('Object Repository/Folders/inputFolderName'), GlobalVariable.folderName)

WebUI.click(findTestObject('Object Repository/Folders/buttonOK'))

WebUI.delay(2)

WebElement btn = findFolder(GlobalVariable.folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

// Upload image

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

WebUI.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/Upload file'))

String projDir = RunConfiguration.getProjectDir()

String fname = projDir + '/Images/user.png'

WebUI.uploadFile(findTestObject('Accounts/AddFileButton'), fname)

WebUI.click(findTestObject('News_User/Page_Folders - PowerFolder/button_Close_upload_file'))


// check News tab
WebUI.click(findTestObject('News_User/Page_News - PowerFolder/News'))

WebUI.mouseOver(findTestObject('News_User/Page_News - PowerFolder/file_wpath'))

WebUI.click(findTestObject('News_User/Page_News - PowerFolder/file_wpath'))

WebUI.delay(3)

// check doandload 

WebDriver driver = DriverFactory.getWebDriver()

String parentWindow = driver.getWindowHandle()

JavascriptExecutor jsExecutor = (JavascriptExecutor) driver

jsExecutor.executeScript('window.open()')

///////// downlad browser ////////

Set<String> allWindowHandles = driver.getWindowHandles()
for (String winHandle : allWindowHandles) {
	if (!winHandle.equals(parentWindow)) {
		driver.switchTo().window(winHandle)
		break
	}
}

WebUI.delay(2)

driver.get('chrome://downloads')


// read donladed image

JavascriptExecutor downloadWindowExecutor = (JavascriptExecutor) driver
String fileName = ''
int retryCount = 0
boolean isFileNameRetrieved = false

while (retryCount < 5 && !isFileNameRetrieved) {
	try {
		fileName = downloadWindowExecutor.executeScript(
			'return document.querySelector("downloads-manager").shadowRoot.querySelector("#downloadsList downloads-item").shadowRoot.querySelector("#file-link").textContent'
		).toString()
		isFileNameRetrieved = (fileName != null && !fileName.isEmpty())
	} catch (Exception e) {
		WebUI.delay(2)
		retryCount++
	}
}

if (!isFileNameRetrieved) {
	throw new RuntimeException('Failed to retrieve the downloaded file name after retries.')
}

System.out.println('Downloaded File Name: ' + fileName)

// check if downloaded image is user.png (or user (1).png, etc.)

WebUI.delay(2)

WebUI.comment("Nom du fichier téléchargé : " + fileName)

if (!fileName.toLowerCase().matches('user( \\(\\d+\\))?\\.png')) {
	WebUI.comment("❌ Le fichier téléchargé ne correspond pas au format 'user.png' ou 'user (n).png'")
	WebUI.verifyMatch(fileName, 'user( \\(\\d+\\))?\\.png', true)
} else {
	WebUI.comment("✅ Fichier PNG détecté correctement : " + fileName)
}

WebUI.closeBrowser()


// === Fonctions utilitaires ===
WebElement findDoc(String docName) {
	WebDriver driver = DriverFactory.getWebDriver()
	return driver.findElement(By.xpath("//*[contains(@data-search-keys, '" + docName + "')]/td[1]/span"))
}

WebElement findFolder(String folderName) {
	WebDriver driver = DriverFactory.getWebDriver()
	return driver.findElement(By.xpath("//td[2]/span/a[contains(text(),'" + folderName + "')]"))
}

String getTimestamp() {
	return new Date().format('dd_MMM_yyyy_hh_mm_ss')
}

String getRandomFileName() {
	return 'File_' + getTimestamp()
}

String getRandomFolderName() {
	return 'Folder_' + getTimestamp()
}

String generateRandomString(int length) {
	String characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'
	StringBuilder randomString = new StringBuilder()
	Random random = new Random()

	for (int i = 0; i < length; i++) {
		randomString.append(characters.charAt(random.nextInt(characters.length())))
	}

	return randomString.toString().toLowerCase()
}

String generateRandomEmail() {
	return generateRandomString(8) + '@yoemail.com'
}

String generateRandomPhoneNumber() {
	Random random = new Random()
	return String.format('(%03d) %03d-%04d', random.nextInt(1000), random.nextInt(1000), random.nextInt(10000))
}
