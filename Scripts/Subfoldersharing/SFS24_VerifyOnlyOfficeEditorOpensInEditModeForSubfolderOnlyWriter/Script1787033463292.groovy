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
import org.apache.poi.xwpf.usermodel.XWPFDocument

/*
 * Scenario "Group B - access of a subfolder-only user": open a document in the online editor
 * with write access.
 *
 * Regression: the editor evaluated the TOP FOLDER permission and opened read-only for a user who
 * only has a permission on the subfolder - the same class of bug as the SFS22 upload regression
 * and the SFS23 nested-directory write check, this time in the OnlyOffice editor's own permission
 * check rather than the storage/upload precheck.
 *
 * Precondition: SubA shared with the second account as READ_WRITE, containing an editable
 * document (created here as a Word document, analogous to "Hello.docx").
 * Steps: log in as the second account, open SubA, open the document in the online editor, type
 * some text and let it save.
 * Expected: the editor opens in edit mode (not read-only); the change is saved and visible after
 * reopening.
 *
 * Edit-mode proof follows the established house pattern from TL47/TL48 (check read-only / check
 * read-write in OnlyOffice): OnlyOffice silently ignores keystrokes sent to a read-only document,
 * so typing text and then reading it back from the editor body is a reliable way to distinguish
 * edit mode from read-only, without depending on any particular read-only banner/indicator.
 */

// create the account that will only ever have READ_WRITE access to SubA (also logs in as admin)
WebUI.callTestCase(findTestCase('Accounts/Edit_Account/pre_test/Create_Account'), [:], FailureHandling.STOP_ON_FAILURE)
String memberEmail = GlobalVariable.userEmail

// admin login/account creation lands elsewhere in the admin UI - navigate to Folders
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

// create the top folder - not shared with anyone - creation navigates straight into it
String tlfName = 'SFS24_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))
WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Folders/inputFolderName'), tlfName)
WebUI.click(findTestObject('Folders/buttonOK'))

// create SubA inside the top folder - creation navigates straight into SubA
String subAName = 'SFS24_SubA_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), subAName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

// create an editable Word document inside SubA (still inside it, right after creation)
String docName = 'Hello_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_Document'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), docName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebUI.switchToWindowIndex(1)
WebUI.verifyElementNotPresent(findTestObject('file_objects/document/Page_Open - PowerFolder/span_Unable to create document'), 5)
WebUI.delay(15)
WebUI.switchToWindowIndex(0)
WebUI.refresh()

// share the (currently open) SubA directly with the account, at READ_WRITE
WebUI.click(findTestObject('Links/share_icon_inside_folder'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/folder_share_permission_dropdown_toggle'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/folder_share_permission_r_w'))
WebUI.setText(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), memberEmail)
WebUI.sendKeys(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), Keys.chord(Keys.ENTER))
WebUI.click(findTestObject('Share/close_button_folder_share_mail'))

// log out admin, log in as the second account
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))
WebUI.setText(findTestObject('Login/inputEmail'), memberEmail)
WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)
WebUI.click(findTestObject('Login/loginSubmit'))
WebUI.delay(3)
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

// accept the invitation to SubA
WebElement invitationRow = findRow(subAName)
WebUI.verifyEqual(invitationRow.isDisplayed(), true)
WebElement invitationLink = findFolder(subAName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(invitationLink))
WebUI.verifyElementClickable(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))

// open SubA, then open the document in the online editor
WebElement subARow = findFolder(subAName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(subARow))

WebElement docRow = findDoc(docName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(docRow))

WebUI.switchToWindowIndex(1)
WebUI.verifyElementVisible(findTestObject('ONLY OFFICE/iframe_editor'))
WebUI.switchToFrame(findTestObject('ONLY OFFICE/iframe_editor'), 5)

// type some text - editor_body.getText() only returns OnlyOffice's own toolbar/chrome (the
// document body itself is canvas-rendered, not exposed as accessible DOM text to Selenium), so
// edit-mode and persistence are proven afterwards by downloading the file and reading its real
// content instead of trying to read the canvas back through the DOM
String typedText = 'SFS24_' + RandomStringUtils.randomAlphanumeric(8)
WebUI.sendKeys(findTestObject('ONLY OFFICE/editor_body'), typedText)
WebUI.delay(2)
WebUI.switchToDefaultContent()

// close the editor tab - this disconnects the co-editing session, which is what actually starts
// the OnlyOffice Document Server's save countdown (per OnlyOfficeBackendServlet's own comment, the
// "ready to save" callback fires ~10s AFTER the document is closed for editing, not 10s after
// typing) - so the wait has to happen AFTER closeWindowIndex, not before it, or the download below
// races the save and reads back the still-unsaved original content
WebUI.switchToWindowIndex(0)
WebUI.closeWindowIndex(1)
WebUI.switchToWindowIndex(0)
WebUI.delay(15)
WebUI.refresh()

// download the document and read its real content - this proves the edit actually reached and
// persisted on the server; a read-only editor would never have let the edit through at all
WebElement docRowForDownload = findDoc(docName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(docRowForDownload))
WebUI.verifyElementClickable(findTestObject('file_objects/document/Download/Page_Folders - PowerFolder/span_download'))
WebUI.click(findTestObject('file_objects/document/Download/Page_Folders - PowerFolder/span_download'))

String downloadPath = System.getProperty('user.home') + '/Downloads/'
File downloadedFile = new File(downloadPath, docName + '.docx')
long deadline = System.currentTimeMillis() + (2 * 60 * 1000)
while (!downloadedFile.exists() && System.currentTimeMillis() < deadline) {
    Thread.sleep(2000)
}
WebUI.verifyEqual(downloadedFile.exists(), true)

XWPFDocument downloadedDoc = new XWPFDocument(new FileInputStream(downloadedFile))
String downloadedText = downloadedDoc.getParagraphs().collect { it.getText() }.join('\n')
downloadedDoc.close()
downloadedFile.delete()

WebUI.verifyMatch(downloadedText, '.*' + typedText + '.*', true)

WebUI.closeBrowser()

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
