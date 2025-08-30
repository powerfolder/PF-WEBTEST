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

// get token from API
String token = CustomKeywords.'utils.WebDav.getToken'(GlobalVariable.userEmail, GlobalVariable.Pass)

// start webdav connection
String base = GlobalVariable.WebdavURL

String user = GlobalVariable.userEmail

String pass = token

// create folder via webdav
String folderName_webdav = getRandomFolderName()

CustomKeywords.'utils.WebDav.createFolder'(base, folderName_webdav, user, pass)

WebUI.click(findTestObject('Folders/Page_Folders - PowerFolder/lang_Folders'))

// check present of toplvl folder made via webdav in web
WebUI.setText(findTestObject('Folders/inputSearch'), folderName_webdav)

WebDriver webdav_driver = DriverFactory.getWebDriver()

WebElement folder_webdav = webdav_driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + folderName_webdav) + 
        '\')]/td[1]/span'))

boolean is_webdav_folderCreated = folder_webdav.isDisplayed()

WebUI.verifyEqual(is_webdav_folderCreated, true)

// rename toplvl folder via webdav
String renamed_toplvlfolder_webdav = 'renamed_' + folderName_webdav

CustomKeywords.'utils.WebDav.renameOrMove'(base, folderName_webdav, renamed_toplvlfolder_webdav, user, pass, true)

// verify rename of toplvl folder via web
WebUI.refresh()

WebUI.setText(findTestObject('Folders/inputSearch'), renamed_toplvlfolder_webdav)

WebDriver renamed_toplvlfolder_driver = DriverFactory.getWebDriver()

WebElement renamed_toplvlfolder = renamed_toplvlfolder_driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + renamed_toplvlfolder_webdav) + '\')]/td[1]/span'))

boolean istoplvlfolderrenamed = renamed_toplvlfolder.isDisplayed()

WebUI.verifyEqual(istoplvlfolderrenamed, true)

// create file in renamed folder via webdav
String filename = 'TWD09' + getTimestamp() + '.txt'
CustomKeywords.'utils.WebDav.uploadFile'(base, renamed_toplvlfolder_webdav + '/' + filename, user, pass, 4096)

// rename file in folder via webdav
String filename_renamed = 'renamed_' + filename
CustomKeywords.'utils.WebDav.renameOrMove'(base, renamed_toplvlfolder_webdav + '/' + filename, renamed_toplvlfolder_webdav + '/' + filename_renamed, user, pass, true)

// check present renamed file in web
WebElement btn_subfolder = findFolder(renamed_toplvlfolder_webdav)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn_subfolder))
WebUI.refresh()

WebUI.setText(findTestObject('Folders/inputSearch'), filename_renamed)

WebDriver file_driver = DriverFactory.getWebDriver()

WebElement file = file_driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + filename_renamed) + '\')]/td[1]/span'))

boolean isfileCreated = file.isDisplayed()

WebUI.verifyEqual(isfileCreated, true)

WebUI.closeBrowser()

WebElement findFolder(String folderName) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(By.xpath(('//td[2]/span/a[contains(text(),\'' + folderName) + '\')]'))
}

String getRandomFolderName() {
    String folderName = 'TWD09' + getTimestamp()
    return folderName
}

String getTimestamp() {
    Date todaysDate = new Date()

    String formattedDate = todaysDate.format('ddMMMyyyyhhmmss')

    return formattedDate
}

