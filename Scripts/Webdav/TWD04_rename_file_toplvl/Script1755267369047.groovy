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
String base = 'https://mimas.powerfolder.net/webdav/'

String user = GlobalVariable.userEmail

String pass = GlobalVariable.Pass

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

// create file in renamed folder
CustomKeywords.'utils.WebDav.uploadFile'(base, (frenamed_toplvlfolder_webdav + '/' + filename), user, pass, 8192)

//WebUI.closeBrowser()

String getRandomFolderName() {
    String folderName = 'TWD04' + getTimestamp()
    return folderName
}

String getTimestamp() {
    Date todaysDate = new Date()

    String formattedDate = todaysDate.format('ddMMMyyyyhhmmss')

    return formattedDate
}

