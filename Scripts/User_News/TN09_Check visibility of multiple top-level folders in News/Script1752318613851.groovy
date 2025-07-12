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

WebUI.callTestCase(findTestCase('User_News/Pre_test/Create_user'), [:], FailureHandling.STOP_ON_FAILURE)

println(GlobalVariable.userEmail)

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

WebUI.setText(findTestObject('Login/inputEmail'), GlobalVariable.userEmail)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(3)

GlobalVariable.folderName = getRandomFolderName()

String folderName1 = GlobalVariable.folderName

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Folders/createFolderIcon'))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Folders/createFolder'))

WebUiBuiltInKeywords.setText(findTestObject('Object Repository/Folders/inputFolderName'), folderName1)

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Folders/buttonOK'))

WebUI.delay(2)

WebElement btn = findFolder(folderName1)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Folders/createFolderIcon'))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Folders/createFolder'))


String folderName2 = getRandomFolderName()

WebUiBuiltInKeywords.setText(findTestObject('Object Repository/Folders/inputFolderName'), folderName2)

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Folders/buttonOK'))

WebElement btn1 = findFolder(folderName2)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

String folderName3 = getRandomFolderName()

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Folders/createFolderIcon'))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Folders/createFolder'))

WebUiBuiltInKeywords.setText(findTestObject('Object Repository/Folders/inputFolderName'), folderName3)

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Folders/buttonOK'))

WebUI.delay(2)

WebElement btn2 = findFolder(folderName3)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn2))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_Document'))

GlobalVariable.Document = ('Doc_num_' + RandomStringUtils.randomNumeric(4))

WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'),
	GlobalVariable.Document)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebUI.switchToWindowIndex(1)

WebUI.closeWindowIndex(1)

WebUI.switchToWindowIndex(0)

WebUI.refresh()

WebUI.delay(2)

WebUI.click(findTestObject('News_User/Page_News - PowerFolder/News'))

WebUI.mouseOver(findTestObject('News_User/Page_News - PowerFolder/file_wpath'))

String tooltipText = WebUI.getAttribute(findTestObject('News_User/Page_News - PowerFolder/file_wpath'), 'data-original-title')

println('Tooltip content: ' + tooltipText)

String RealPath = tooltipText.split(' ')[0]
String expectedPath = folderName2 + "/" + folderName3 + "/" + GlobalVariable.Document +".docx"

println('Real path: ' + RealPath)
println('expected Path: ' + expectedPath)


assert RealPath == expectedPath : '❌ does not match.'

WebUI.delay(3)

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