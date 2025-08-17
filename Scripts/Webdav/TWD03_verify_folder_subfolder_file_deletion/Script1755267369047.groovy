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
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import java.util.List as List;

//create Account and login
WebUI.callTestCase(findTestCase('My Account/Pre_test/Create Account'), [:], FailureHandling.STOP_ON_FAILURE)

// create toplvl folder via web
WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createFolder'))

String folderName = getRandomFolderName()

WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)

WebUI.click(findTestObject('Folders/buttonOK'))

//verify present of toplvl folder in web
WebUI.setText(findTestObject('Folders/inputSearch'), folderName)

WebDriver driver = DriverFactory.getWebDriver()

WebElement folder = driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + folderName) + '\')]/td[1]/span'))

boolean isfolderCreated = folder.isDisplayed()

WebUI.verifyEqual(isfolderCreated, true)

// start webdav connection
String base = 'https://mimas.powerfolder.net/webdav/'

String user = GlobalVariable.userEmail

String pass = GlobalVariable.Pass

// check present of toplvl folder made in web via webdav
boolean web_toplvl_present = CustomKeywords.'utils.WebDav.exists'(base, folderName, user, pass)

assert web_toplvl_present

// create sublvl_folder via webdav
String folderName_webdav = getRandomFolderName()

CustomKeywords.'utils.WebDav.createFolder'(base, folderName + '/' + folderName_webdav, user, pass)

// open toplvl folder

WebUI.click(findTestObject('Folders/Page_Folders - PowerFolder/lang_Folders'))

WebElement btn = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

// verify present of sublvl folder in web
WebDriver webdav_driver = DriverFactory.getWebDriver()

WebElement folder_webdav = webdav_driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + folderName_webdav) + 
        '\')]/td[1]/span'))

boolean is_webdav_folderCreated = folder_webdav.isDisplayed()

WebUI.verifyEqual(is_webdav_folderCreated, true)
	
// create file in subfolder via webdav
String filename = 'TWD03' + getTimestamp() + '.txt'
CustomKeywords.'utils.WebDav.uploadFile'(base, folderName + '/' + folderName_webdav + '/' + filename, user, pass, 2048)

// open subfolder via web
WebElement btn_subfolder = findFolder(folderName_webdav)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn_subfolder))

// verifiy present of file via web
WebUI.setText(findTestObject('Folders/inputSearch'), filename)

WebDriver file_driver = DriverFactory.getWebDriver()

WebElement file = driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + filename) + '\')]/td[1]/span'))

boolean isfileCreated = file.isDisplayed()

WebUI.verifyEqual(isfolderCreated, true)

// delete file via webdav
CustomKeywords.'utils.WebDav.deletePath'(base, folderName + '/' + folderName_webdav + '/' + filename, user, pass)

// verify file is gone in web
WebUI.refresh() //reload page
WebDriver file_del_driver = DriverFactory.getWebDriver()

List<WebElement> items = file_del_driver.findElements(
	By.xpath("//*[@id='files_files_table']/tbody//span[contains(text(), '" + filename + "')]")
)
assert items.isEmpty();

// delete subfolder via webdav
CustomKeywords.'utils.WebDav.deletePath'(base, folderName + '/' + folderName_webdav, user, pass)

// navigate into toplvl folder
WebUI.click(findTestObject('Folders/Page_Folders - PowerFolder/lang_Folders'))
WebElement top_lvl_btn = findFolder(folderName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(top_lvl_btn))

// verify subfolder is gone in web
WebDriver subfolder_del_driver = DriverFactory.getWebDriver()
List<WebElement> toplvl_item = subfolder_del_driver.findElements(
	By.xpath("//*[@id='files_files_table']/tbody//span[contains(text(), '" + folderName_webdav + "')]")
)
assert toplvl_item.isEmpty();

// delete toplvl folder
CustomKeywords.'utils.WebDav.deletePath'(base, folderName, user, pass)

// navigate to root of folders
WebUI.click(findTestObject('Folders/Page_Folders - PowerFolder/lang_Folders'))

// verify toplvl folder is gone in web
WebDriver toplvl_del_driver = DriverFactory.getWebDriver()
List<WebElement> toplvl_folder = toplvl_del_driver.findElements(
	By.xpath("//*[@id='files_files_table']/tbody//span[contains(text(), '" + folderName + "')]")
)
assert toplvl_folder.isEmpty();

// close browser
WebUI.closeBrowser()
	
@Keyword
WebElement findFolder(String folderName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//td[2]/span/a[contains(text(),\'' + folderName) + '\')]'))
}

String getRandomFolderName() {
    String folderName = 'TWD03' + getTimestamp()

    return folderName
}

String getTimestamp() {
    Date todaysDate = new Date()

    String formattedDate = todaysDate.format('ddMMMyyyyhhmmss')

    return formattedDate
}

