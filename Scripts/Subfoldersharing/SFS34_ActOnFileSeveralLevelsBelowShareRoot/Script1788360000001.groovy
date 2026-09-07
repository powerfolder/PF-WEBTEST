import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import org.apache.commons.lang3.RandomStringUtils
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import com.kms.katalon.core.webui.driver.DriverFactory

WebUI.callTestCase(findTestCase('Accounts/Edit_Account/pre_test/Create_Account'), [:], FailureHandling.STOP_ON_FAILURE)
String memberEmail = GlobalVariable.userEmail

WebUI.click(findTestObject('LeftNavigationIcons/folders'))

String tlfName = 'SFS34_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))
WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Folders/inputFolderName'), tlfName)
WebUI.click(findTestObject('Folders/buttonOK'))

String subAName = 'SFS34_SubA_' + RandomStringUtils.randomAlphanumeric(6)
createNestedFolder(subAName)

String aName = 'a'
createNestedFolder(aName)
String bName = 'b'
createNestedFolder(bName)
String cName = 'c'
createNestedFolder(cName)

String docName = 'SFS34_file_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_Document'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), docName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebUI.switchToWindowIndex(1)
WebUI.verifyElementNotPresent(findTestObject('file_objects/document/Page_Open - PowerFolder/span_Unable to create document'), 5)
WebUI.delay(15)
WebUI.closeWindowIndex(1)
WebUI.switchToWindowIndex(0)
WebUI.refresh()
WebUI.delay(3)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/lang_Home'))
WebUI.delay(2)
WebElement tlfRowAdmin = findFolder(tlfName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(tlfRowAdmin))
WebElement subARowAdmin = findFolder(subAName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(subARowAdmin))

WebUI.click(findTestObject('Links/share_icon_inside_folder'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/folder_share_permission_dropdown_toggle'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/folder_share_permission_r_w'))
WebUI.setText(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), memberEmail)
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/buttonAddEmail'))
WebUI.click(findTestObject('Share/close_button_folder_share_mail'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))
WebUI.setText(findTestObject('Login/inputEmail'), memberEmail)
WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)
WebUI.click(findTestObject('Login/loginSubmit'))
WebUI.delay(3)
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

WebElement invitationRow = findRow(subAName)
WebUI.verifyEqual(invitationRow.isDisplayed(), true)
WebElement invitationLink = findFolder(subAName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(invitationLink))
WebUI.verifyElementClickable(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))

WebElement subARow = findFolder(subAName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(subARow))
WebElement aRow = findFolder(aName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(aRow))
WebElement bRow = findFolder(bName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(bRow))
WebElement cRow = findFolder(cName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(cRow))

TestObject docPresent = new TestObject()
docPresent.addProperty('xpath', ConditionType.EQUALS, "//*[contains(@data-search-keys, '" + docName + "')]/td[1]/span")
WebUI.verifyElementPresent(docPresent, 10)

String uploadedFileName = 'sfs34_upload_' + RandomStringUtils.randomAlphanumeric(6) + '.txt'
File localFile = File.createTempFile('sfs34_', '.txt')
localFile.text = 'SFS34 upload several levels below the share root'
File renamedLocalFile = new File(localFile.getParentFile(), uploadedFileName)
localFile.renameTo(renamedLocalFile)

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.verifyElementPresent(findTestObject('Folders/Page_Folders - PowerFolder/Upload files'), 5)
WebUI.click(findTestObject('Folders/Page_Folders - PowerFolder/Upload files'))

TestObject uploadInput = new TestObject('uploadInput')
uploadInput.addProperty('xpath', ConditionType.EQUALS, "//input[@id='upload_input_files']")
WebUI.waitForElementPresent(uploadInput, 10)
WebUI.uploadFile(uploadInput, renamedLocalFile.getAbsolutePath())

TestObject successMsg = new TestObject('successMsg')
successMsg.addProperty('xpath', ConditionType.EQUALS, "//*[contains(text(),'Successfully uploaded')]")
WebUI.waitForElementVisible(successMsg, 15)

TestObject closeUploadBtn = new TestObject('closeUploadBtn')
closeUploadBtn.addProperty('xpath', ConditionType.EQUALS, "//button[@id='upload_stop_button']")
WebUI.waitForElementClickable(closeUploadBtn, 10)
WebUI.click(closeUploadBtn)
renamedLocalFile.delete()

TestObject uploadedFilePresent = new TestObject()
uploadedFilePresent.addProperty('xpath', ConditionType.EQUALS, "//*[contains(@data-search-keys, '" + uploadedFileName + "')]/td[1]/span")
WebUI.verifyElementPresent(uploadedFilePresent, 10)

String renamedDocName = 'SFS34_renamed_' + RandomStringUtils.randomAlphanumeric(6)

WebElement docRowToSelect = findRow(docName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(docRowToSelect))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/span_Rename'))
WebUI.waitForElementVisible(findTestObject('Folders/inputFolderName'), 5)
WebUI.setText(findTestObject('Folders/inputFolderName'), renamedDocName)
WebUI.click(findTestObject('Folders/buttonOK'))
WebUI.delay(2)

TestObject renamedDocPresent = new TestObject()
renamedDocPresent.addProperty('xpath', ConditionType.EQUALS, "//*[contains(@data-search-keys, '" + renamedDocName + "')]/td[1]/span")
WebUI.verifyElementPresent(renamedDocPresent, 10)
WebUI.verifyElementNotPresent(docPresent, 5)

String editMarker = 'SFS34_EDIT_' + RandomStringUtils.randomAlphanumeric(8)

WebElement renamedDocLink = findFolder(renamedDocName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(renamedDocLink))

WebUI.switchToWindowIndex(1)
WebUI.verifyElementVisible(findTestObject('ONLY OFFICE/iframe_editor'))
WebUI.switchToFrame(findTestObject('ONLY OFFICE/iframe_editor'), 5)
WebUI.sendKeys(findTestObject('ONLY OFFICE/editor_body'), editMarker)
WebUI.delay(2)
WebUI.switchToDefaultContent()
WebUI.delay(5)
WebUI.closeWindowIndex(1)
WebUI.switchToWindowIndex(0)
WebUI.refresh()

TestObject dangerNotification = new TestObject()
dangerNotification.addProperty('xpath', ConditionType.EQUALS, "//div[contains(concat(' ',normalize-space(@class),' '),' alert-danger ')]")
WebUI.verifyElementNotPresent(dangerNotification, 5)

def suspiciousConsoleEntries = []
try {
    WebDriver driver = DriverFactory.getWebDriver()
    def logs = driver.manage().logs().get('browser').getAll()
    suspiciousConsoleEntries = logs.findAll { entry ->
        String msg = entry.getMessage().toLowerCase()
        msg.contains('403') || msg.contains('permission denied') || msg.contains('forbidden')
    }
} catch (Exception e) {
    WebUI.comment('Browser console log retrieval not available in this environment - skipping console check: ' + e.getMessage())
}
WebUI.verifyEqual(suspiciousConsoleEntries.isEmpty(), true)

WebUI.closeBrowser()

def createNestedFolder(String name) {
    WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
    WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))
    WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), name)
    WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))
}

WebElement findFolder(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//td[2]/span/a[contains(text(),'" + name + "')]"))
}

WebElement findRow(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//*[contains(@data-search-keys, '" + name + "')]/td[1]/span"))
}

WebElement findDoc(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//*[contains(@data-search-keys, '" + name + "')]/td[1]/span"))
}
