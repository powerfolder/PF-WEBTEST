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
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import java.util.Arrays as Arrays

WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.OPTIONAL)

assert WebUI.getWindowTitle().equals('Folders - PowerFolder')

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createFolder'))

String topLvlFolderName = 'Top_lvl' + getTimestamp()

WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.setText(findTestObject('Folders/inputFolderName'), topLvlFolderName)

WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/lang_Home'))

WebUI.refresh()

WebUI.delay(3)

def btn = findFolder(topLvlFolderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.click(findTestObject('ManagePopup/button_Manage'))

WebUI.clearText(findTestObject('Folders/version/input version'))

WebUI.setText(findTestObject('Folders/version/input version'), '3')

WebUI.click(findTestObject('Folders/version/Save buton'))

def btn1 = clickElement(topLvlFolderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_Document'))

String DocName = 'Doc_num_' + RandomStringUtils.randomNumeric(4)

WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), 
    DocName)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebUI.switchToWindowIndex(1)

WebUI.verifyElementNotPresent(findTestObject('file_objects/document/Page_Open - PowerFolder/span_Unable to create document'), 
    3)

WebUI.closeWindowIndex(1)

WebUI.switchToWindowIndex(0)

WebUI.refresh()

WebUI.delay(3)

editFromList(DocName, '1st Modification')

editFromList(DocName, ' 2nd Modification ')

editFromList(DocName, ' 3rd Modification ')

WebUI.delay(10)

WebUI.refresh()

def btn2 = findFolder(DocName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn2))

WebUI.click(findTestObject('Recycle bin/Page_Folders - PowerFolder/a_Restore'))

WebUI.waitForElementVisible(findTestObject('Recycle bin/Page_Folders - PowerFolder/restore_popup'), 10)

List<WebElement> restoreButtons = DriverFactory.getWebDriver().findElements(By.xpath("//div[@id='pica_restore_versions']//tbody/tr//a[.//span[contains(concat(' ',normalize-space(@class),' '),' glyphicons-restart ')]]"))

WebUI.verifyEqual(restoreButtons.size(), 3)

WebUI.closeBrowser()

def editFromList(String docName, String textToAdd) {
    def btnDoc = clickElement(docName)

    WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btnDoc))

    editDocument(textToAdd)
}

def editDocument(String textToAdd) {
    WebUI.switchToWindowIndex(1)

    WebUI.delay(10)

    WebUI.waitForElementVisible(findTestObject('ONLY OFFICE/iframe_editor'), 20)

    WebUI.switchToFrame(findTestObject('ONLY OFFICE/iframe_editor'), 10)

    WebUI.executeJavaScript('document.body.focus();', null)

    WebUI.sendKeys(findTestObject('ONLY OFFICE/editor_body'), textToAdd)

    WebUI.delay(2)

    WebUI.sendKeys(findTestObject('ONLY OFFICE/editor_body'), Keys.chord(Keys.CONTROL, 's'))

    WebUI.delay(1)

    WebUI.switchToDefaultContent()

    WebUI.closeWindowIndex(1)

    WebUI.switchToWindowIndex(0)

    WebUI.refresh()

    WebUI.delay(10)
}

String getTimestamp() {
    Date todaysDate = new Date()

    return todaysDate.format('_dd_MM_yyyy_hh_mm_ss')
}

WebElement findFolder(String folderName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath("//*[contains(@data-search-keys, '$folderName')]/td[1]/span"))
}

WebElement clickElement(String variable) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath("//*[contains(@data-search-keys, '$variable')]/td[2]/span/a"))
}

