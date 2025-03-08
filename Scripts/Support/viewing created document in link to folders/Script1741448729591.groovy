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
import javax.swing.filechooser.FileSystemView as FileSystemView
import org.apache.poi.xwpf.usermodel.XWPFDocument as XWPFDocument
import java.io.FileOutputStream as FileOutputStream
import java.nio.file.FileSystems as FileSystems
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.apache.commons.io.FileUtils as FileUtils
import org.apache.commons.io.IOUtils as IOUtils
import java.io.ByteArrayInputStream as ByteArrayInputStream
import java.net.URL as URL
import java.nio.file.Files as Files
import java.nio.file.Paths as Paths
import java.nio.file.StandardCopyOption as StandardCopyOption
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.StringSelection as StringSelection
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import java.awt.FileDialog as FileDialog
import javax.swing.JFrame as JFrame
import java.io.IOException as IOException
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import java.awt.datatransfer.DataFlavor as DataFlavor
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper

WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.OPTIONAL)

String folderName_1 = getRandomFolderName()

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createFolder'))

WebUI.verifyEqual(WebUI.getText(findTestObject('lang/getCreateText')), 'Create', FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyEqual(WebUI.getText(findTestObject('lang/getFolderNameLabelText')), 'Create a new Folder', FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.setText(findTestObject('Folders/inputFolderName'), folderName_1)

WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.refresh()

WebUI.delay(2)

WebDriver driver = DriverFactory.getWebDriver()

WebElement folder = driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + folderName_1) + '\')]/td[1]/span'))

boolean isFolderCreated = folder.isDisplayed()

WebUiBuiltInKeywords.verifyEqual(isFolderCreated, true)

WebElement btn = ClickFolder(folderName_1)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createFolder'))

String folderName_2 = getRandomFolderName()

WebUI.setText(findTestObject('Folders/inputFolderName'), folderName_2)

WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.delay(2)

WebElement btn1 = ClickFolder(folderName_2)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_Document'))

String DocName = 'Doc_num_' + RandomStringUtils.randomNumeric(4)

WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), 
    DocName)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebUI.switchToWindowIndex('1')

WebUI.refresh()

WebUI.delay(5)

WebUI.switchToWindowIndex(0)

WebUI.refresh()

WebUI.click(findTestObject('Folders/Page_Folders - PowerFolder/click_on_sub'))

WebElement btn2 = findShareButton(folderName_2)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn2))

WebUI.click(findTestObject('Links/buttonCreateLink'))

WebUI.waitForElementClickable(findTestObject('Links/buttonCreateLink'), 30, FailureHandling.CONTINUE_ON_FAILURE)

WebElement buttonCreateLink = WebUiCommonHelper.findWebElement(findTestObject('Links/buttonCreateLink'), 30)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(buttonCreateLink))

WebUI.click(findTestObject('SettingsPopUp/buttonSave'))

WebUI.delay(3)

WebUI.click(findTestObject('Page_Folders - PowerFolder/icon-copy'))

WebUI.click(findTestObject('Links/Page_Folders - PowerFolder/share_button_Close'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

WebUI.switchToWindowIndex(1)

WebUI.refresh()

String my_clipboard = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor)

WebUI.navigateToUrl(my_clipboard)

WebUI.delay(2)

WebUI.click(findTestObject('Links/Page_Link - PowerFolder/click_Doc'))

WebUI.switchToWindowIndex(2)

WebUI.delay(2)

WebDriver driver_2 = DriverFactory.getWebDriver()

String currentUrl = driver_2.getCurrentUrl()

String expectedDocName = DocName + '.docx'

if (currentUrl.contains(expectedDocName)) {
    WebUI.comment('✅ L\'URL contient bien le fichier attendu: ' + expectedDocName)
} else {
    WebUI.comment('❌ L\'URL ne contient pas le fichier attendu! URL actuelle: ' + currentUrl)

    WebUI.takeScreenshot()
}

WebUI.verifyMatch(currentUrl, ('.*' + expectedDocName) + '.*', true, FailureHandling.CONTINUE_ON_FAILURE)

WebUI.closeBrowser()

String getRandomFolderName() {
    String folderName = 'FD_Test_' + getTimestamp()

    return folderName
}

String getTimestamp() {
    Date todaysDate = new Date()

    String formattedDate = todaysDate.format('ddMMMyyyyhhmmss')

    return formattedDate
}

WebElement SelectFolder(String folderName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + folderName) + '\')]/td[1]/span'))
}

WebElement ClickFolder(String folderName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//td[2]/span/a[contains(text(),\'' + folderName) + '\')]'))
}

WebElement findShareButton(String fileName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + fileName) + '\')]/td[7]/a/span'))
}

