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
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement

//create Account and login
WebUI.callTestCase(findTestCase('My Account/Pre_test/Create Account'), [:], FailureHandling.STOP_ON_FAILURE)

// start webdav connection
String base = GlobalVariable.WebdavURL

String user = GlobalVariable.userEmail

String pass = GlobalVariable.Pass

// create folder via webdav
String folderName_webdav = getRandomFolderName()

CustomKeywords.'utils.WebDav.createFolder'(base, folderName_webdav, user, pass)

WebUI.click(findTestObject('Folders/Page_Folders - PowerFolder/lang_Folders'))

// check present of toplvl folder made via webdav in web
WebElement folder_webdav = waitForRow(folderName_webdav)

boolean is_webdav_folderCreated = folder_webdav.isDisplayed()

WebUI.verifyEqual(is_webdav_folderCreated, true)

// rename toplvl folder via webdav
String renamed_toplvlfolder_webdav = 'renamed_' + folderName_webdav

CustomKeywords.'utils.WebDav.renameOrMove'(base, folderName_webdav, renamed_toplvlfolder_webdav, user, pass, true)

// verify rename of toplvl folder via web
WebElement renamed_toplvlfolder = waitForRow(renamed_toplvlfolder_webdav)

boolean istoplvlfolderrenamed = renamed_toplvlfolder.isDisplayed()

WebUI.verifyEqual(istoplvlfolderrenamed, true)

// create file in renamed folder via webdav
String filename = 'TWD04' + getTimestamp() + '.txt'
CustomKeywords.'utils.WebDav.uploadFile'(base, renamed_toplvlfolder_webdav + '/' + filename, user, pass, 4096)

// rename file in folder via webdav
String filename_renamed = 'renamed_' + filename
CustomKeywords.'utils.WebDav.renameOrMove'(base, renamed_toplvlfolder_webdav + '/' + filename, renamed_toplvlfolder_webdav + '/' + filename_renamed, user, pass, true)

// check present renamed file in web
WebElement btn_subfolder = waitForFolderLink(renamed_toplvlfolder_webdav)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn_subfolder))

WebElement file = waitForRow(filename_renamed)

boolean isfileCreated = file.isDisplayed()

WebUI.verifyEqual(isfileCreated, true)

WebUI.closeBrowser()

WebElement waitForRow(String searchKey, int maxAttempts = 6, int waitSeconds = 5) {
	return waitForElementPresent("//*[contains(@data-search-keys, '" + searchKey + "')]/td[1]/span", maxAttempts, waitSeconds)
}

WebElement waitForFolderLink(String folderName, int maxAttempts = 6, int waitSeconds = 5) {
	return waitForElementPresent("//td[2]/span/a[contains(text(),'" + folderName + "')]", maxAttempts, waitSeconds)
}

WebElement waitForElementPresent(String xpath, int maxAttempts, int waitSeconds) {
	WebDriver driver = DriverFactory.getWebDriver()

	for (int attempt = 1; attempt <= maxAttempts; attempt++) {
		List<WebElement> found = driver.findElements(By.xpath(xpath))

		if (!found.isEmpty() && found[0].isDisplayed()) {
			return found[0]
		}

		if (attempt < maxAttempts) {
			WebUI.delay(waitSeconds)
			WebUI.refresh()
		}
	}

	throw new Exception("Element not found after " + maxAttempts + " attempts (with reloads): " + xpath)
}

String getRandomFolderName() {
    String folderName = 'TWD04' + getTimestamp()
    return folderName
}

String getTimestamp() {
    Date todaysDate = new Date()

    String formattedDate = todaysDate.format('ddMMMyyyyhhmmss')

    return formattedDate
}

