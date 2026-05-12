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


WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Folders/createFolderIcon'))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Folders/createFolder'))

// crete toplevel dir
WebUiBuiltInKeywords.setText(findTestObject('Object Repository/Folders/inputFolderName'), folderName_2)

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Folders/buttonOK'))

// create doc in toplvl folder B
WebUiBuiltInKeywords.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

WebUiBuiltInKeywords.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_Document'))

String DocName = 'Doc_num_' + RandomStringUtils.randomNumeric(4)

WebUiBuiltInKeywords.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), 
    DocName)

WebUiBuiltInKeywords.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebUiBuiltInKeywords.delay(15)

WebUiBuiltInKeywords.closeWindowIndex(1)

WebUiBuiltInKeywords.switchToWindowIndex(0)

WebUI.refresh()

WebUiBuiltInKeywords.delay(2)

WebElement btn1 = findDoc(DocName)

// check if doc is created

boolean isDocCreated = btn1.isDisplayed()

WebUiBuiltInKeywords.verifyEqual(isDocCreated, true)

WebUiBuiltInKeywords.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

assert DocName != null

// cut created doc

WebUiBuiltInKeywords.verifyElementClickable(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - Cut/span_Cut'))

WebUiBuiltInKeywords.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - Cut/span_Cut'))

// search for toplvl folder A and paste doc

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebElement btn2 = findFolder(folderName_1)

WebUiBuiltInKeywords.executeJavaScript('arguments[0].click()', Arrays.asList(btn2))

WebUiBuiltInKeywords.click(findTestObject('file_objects/document/span_paste/span_Paste'))


// check if doc is pasted in successfully

WebElement btn3 = findDoc(DocName)

boolean isDocPasted = btn3.isDisplayed()

WebUiBuiltInKeywords.verifyEqual(isDocPasted, true)

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebElement btn4 = findFolder(folderName_2)

WebUiBuiltInKeywords.executeJavaScript('arguments[0].click()', Arrays.asList(btn4))


WebUI.delay(2)

boolean docStillPresentInTopLevel = isDocPresent(DocName)

WebUI.verifyEqual(docStillPresentInTopLevel, false)

WebUI.closeBrowser()


@Keyword
WebElement findDoc(String DocName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + DocName) + '\')]/td[1]/span'))
}

@Keyword
WebElement findFolder(String folderName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//a[contains(text(),\'' + folderName) + '\')]'))
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

boolean isDocPresent(String docName) {
	WebDriver driver = DriverFactory.getWebDriver()

	List<WebElement> docs = driver.findElements(By.xpath(('//*[contains(@data-search-keys, \'' + docName) + '\')]/td[1]/span'))

	return !(docs.isEmpty())
}
