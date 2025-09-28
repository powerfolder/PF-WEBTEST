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

WebUI.callTestCase(findTestCase('File/Pre_test/Create_folder'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_Document'))

String DocName = 'Doc_num_' + RandomStringUtils.randomNumeric(4)

WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), 
    DocName)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebUI.switchToWindowIndex(1)

// Attendre que le titre de la page contienne le nom du document
WebUI.waitForPageLoad(10)

// Récupérer l’URL de la fenêtre active
String currentUrl = WebUI.getUrl()

// Vérifier que l’URL contient bien le nom du doc
WebUI.verifyMatch(currentUrl.contains(DocName + '.docx').toString(), 'true', false)

WebUI.closeWindowIndex(1)

WebUI.switchToWindowIndex(0)

WebUI.refresh()

WebUI.delay(3)

def btn = findDoc(DocName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

assert DocName != null

WebUI.refresh()

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))

String folderName = getRandomFolderName()

WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), 
    folderName)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebElement btn1 = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_Document'))

String DocName_1 = 'Doc_num_' + RandomStringUtils.randomNumeric(4)

WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), 
    DocName_1)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebUI.switchToWindowIndex(1)

WebUI.waitForPageLoad(10)

// Récupérer l’URL de la fenêtre active
String currentUrl_1 = WebUI.getUrl()

// Vérifier que l’URL contient bien le nom du doc
WebUI.verifyMatch(currentUrl_1.contains(DocName_1 + '.docx').toString(), 'true', false)

WebUI.closeWindowIndex(1)

WebUI.switchToWindowIndex(0)

WebUI.refresh()

WebUI.delay(3)

def btn2 = findDoc(DocName_1)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn2))

assert DocName_1 != null

WebUI.refresh()

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))

String folderName_1 = getRandomFolderName()

WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), 
    folderName_1)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebElement btn3 = findFolder(folderName_1)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn3))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_Document'))

String DocName_2 = 'Doc_num_' + RandomStringUtils.randomNumeric(4)

WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), 
    DocName_2)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebUI.switchToWindowIndex(1)

WebUI.waitForPageLoad(10)

// Récupérer l’URL de la fenêtre active
String currentUrl_2 = WebUI.getUrl()

// Vérifier que l’URL contient bien le nom du doc
WebUI.verifyMatch(currentUrl_2.contains(DocName_2 + '.docx').toString(), 'true', false)

WebUI.closeWindowIndex(1)

WebUI.switchToWindowIndex(0)

WebUI.refresh()

WebUI.delay(3)

def btn4 = findDoc(DocName_2)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn4))

assert DocName_2 != null

WebUI.refresh()

WebUI.closeBrowser()

WebElement findDoc(String DocName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + DocName) + '\')]/td[1]/span'))
}

String getRandomFolderName() {
    String folderName = 'Folder_' + getTimestamp()

    return folderName
}

String getTimestamp() {
    Date todaysDate = new Date()

    String formattedDate = todaysDate.format('dd_MMM_yyyy_hh_mm_ss')

    return formattedDate
}

WebElement findFolder(String folderName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//td[2]/span/a[contains(text(),\'' + folderName) + '\')]'))
}

