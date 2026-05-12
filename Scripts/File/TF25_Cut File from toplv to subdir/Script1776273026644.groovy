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

// create toplvl folder A
String folderName_1 = getRandomFolderName()

String folderName_2 = getRandomFolderName()

while (folderName_2 == folderName_1) {
    folderName_2 = getRandomFolderName()
}

WebUiBuiltInKeywords.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Folders/createFolderIcon'))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Folders/createFolder'))

// crete toplevel dir
WebUiBuiltInKeywords.setText(findTestObject('Object Repository/Folders/inputFolderName'), folderName_1)

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Folders/buttonOK'))

// create doc in toplvl folder A
WebUiBuiltInKeywords.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

WebUiBuiltInKeywords.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_Document'))

String docName = 'Doc_num_' + RandomStringUtils.randomNumeric(4)

WebUiBuiltInKeywords.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), 
    docName)

WebUiBuiltInKeywords.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebUiBuiltInKeywords.delay(10)

WebUiBuiltInKeywords.closeWindowIndex(1)

WebUiBuiltInKeywords.switchToWindowIndex(0)

WebUI.refresh()

// crete subdir
WebUiBuiltInKeywords.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))

WebUiBuiltInKeywords.setText(findTestObject('Object Repository/Folders/inputFolderName'), folderName_2)

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Folders/buttonOK'))

WebUI.click(findTestObject('Object Repository/Folders/Page_Folders - PowerFolder/lang_Folders'))

WebUI.delay(2)

WebDriver driver = DriverFactory.getWebDriver()

WebElement folder = driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + folderName_1) + '\')]/td[1]/span'))

boolean isFolderCreated = folder.isDisplayed()

WebUiBuiltInKeywords.verifyEqual(isFolderCreated, true)

WebElement btn = findFolder(folderName_1)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.delay(2)

WebElement btn_1 = findDoc(docName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn_1))

WebUiBuiltInKeywords.verifyElementClickable(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - Cut/span_Cut'))

WebUiBuiltInKeywords.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - Cut/span_Cut'))

// search for sub dir A and paste doc
WebElement btn_3 = findFolder(folderName_2)

WebUiBuiltInKeywords.executeJavaScript('arguments[0].click()', Arrays.asList(btn_3))

WebUiBuiltInKeywords.click(findTestObject('file_objects/document/span_paste/span_Paste'))

def btn_4 = findDoc(docName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn_4))

assert docName != null

WebUI.click(findTestObject('Folders/Page_Folders - PowerFolder/Return_to_toplvl'))

WebUI.delay(2)

boolean docStillPresentInTopLevel = isDocPresent(docName)

WebUI.verifyEqual(docStillPresentInTopLevel, false)

WebUI.closeBrowser()

@Keyword
WebElement findFolder(String folderName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//td[2]/span/a[contains(text(),\'' + folderName) + '\')]'))
}

String getTimestamp() {
    Date todaysDate = new Date()

    String formattedDate = todaysDate.format('dd_MMM_yyyy_hh_mm_ss')

    return formattedDate
}

String getRandomFileName() {
    String fileName = 'File_' + getTimestamp()

    return fileName
}

String getRandomFolderName() {
    String folderName = 'Folder_' + getTimestamp()

    return folderName
}

@Keyword
WebElement findDoc(String DocName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + DocName) + '\')]/td[1]/span'))
}

boolean isDocPresent(String docName) {
    WebDriver driver = DriverFactory.getWebDriver()

    List<WebElement> docs = driver.findElements(By.xpath(('//*[contains(@data-search-keys, \'' + docName) + '\')]/td[1]/span'))

    return !(docs.isEmpty())
}

