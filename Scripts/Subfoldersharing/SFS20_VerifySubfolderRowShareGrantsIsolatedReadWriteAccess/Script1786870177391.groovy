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

/*
 * Scenario "Group A - sharing and revoking" (sharing half):
 *
 * Precondition: a top folder with a subfolder SubA containing one file; a second account exists
 * with no permission on the top folder.
 * Steps: as admin, open the top folder, use the row-level Share icon on SubA (not the
 * "current folder" share icon - this exercises the OTHER share entry point, the one used for a
 * row inside a listing rather than for the folder you're currently browsing), invite the second
 * account with READ_WRITE, log out, log in as the second account, open the folder list.
 * Expected: the second account reaches SubA and sees its content; no other part of the top
 * folder is visible to them (the top folder itself is never shared, so it - and anything else in
 * it - stays completely invisible); write actions (create/upload) are offered inside SubA.
 */

// create the account that will only ever get READ_WRITE access to SubA (also logs in as admin)
WebUI.callTestCase(findTestCase('Accounts/Edit_Account/pre_test/Create_Account'), [:], FailureHandling.STOP_ON_FAILURE)
String memberEmail = GlobalVariable.userEmail

// admin login/account creation lands elsewhere in the admin UI - navigate to Folders
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

// create the top folder - creation navigates straight into it
String tlfName = 'SFS20_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))
WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Folders/inputFolderName'), tlfName)
WebUI.click(findTestObject('Folders/buttonOK'))

// create SubA inside the top folder - creation navigates straight into SubA
String subAName = 'SFS20_SubA_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), subAName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

// upload one file into SubA (still inside it, right after creation) - satisfies the precondition
String fileName = 'sfs20_file_' + RandomStringUtils.randomAlphanumeric(6) + '.txt'
File localFile = File.createTempFile('sfs20_', '.txt')
localFile.text = 'SFS20 precondition file'
File renamedFile = new File(localFile.getParentFile(), fileName)
localFile.renameTo(renamedFile)

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.verifyElementPresent(findTestObject('Folders/Page_Folders - PowerFolder/Upload files'), 5)
WebUI.click(findTestObject('Folders/Page_Folders - PowerFolder/Upload files'))

TestObject uploadInput = new TestObject('uploadInput')
uploadInput.addProperty('xpath', ConditionType.EQUALS, "//input[@id='upload_input_files']")
WebUI.waitForElementPresent(uploadInput, 10)
WebUI.uploadFile(uploadInput, renamedFile.getAbsolutePath())

TestObject successMsg = new TestObject('successMsg')
successMsg.addProperty('xpath', ConditionType.EQUALS, "//*[contains(text(),'Successfully uploaded')]")
WebUI.waitForElementVisible(successMsg, 15)

TestObject closeUploadBtn = new TestObject('closeUploadBtn')
closeUploadBtn.addProperty('xpath', ConditionType.EQUALS, "//button[@id='upload_stop_button']")
WebUI.waitForElementClickable(closeUploadBtn, 10)
WebUI.click(closeUploadBtn)
renamedFile.delete()

// go back to the top-level folder list, then open the top folder (viewing its content, where
// SubA now appears as a row) - this is the "open the top folder" step
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/lang_Home'))
WebElement tlfRow = findFolder(tlfName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(tlfRow))

// use the row-level Share icon on SubA (not the "current folder" icon - SubA is not open, just
// listed as a row inside the top folder we are currently viewing)
WebElement subAShareButton = findShareButton(subAName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(subAShareButton))

// set the permission to READ_WRITE before inviting
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

// login lands on the Dashboard - open the folder list
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

// accept the invitation to SubA
WebElement invitationRow = findRow(subAName)
WebUI.verifyEqual(invitationRow.isDisplayed(), true)
WebElement invitationLink = findFolder(subAName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(invitationLink))
WebUI.verifyElementClickable(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))

// the top folder itself (and therefore anything else inside it) stays completely invisible
TestObject tlfPresent = new TestObject()
tlfPresent.addProperty('xpath', ConditionType.EQUALS, "//a[contains(concat(' ',normalize-space(@class),' '),' pica-name ') and normalize-space(text())='" + tlfName + "']")
WebUI.verifyElementNotPresent(tlfPresent, 10)

// the second account reaches SubA and sees its content (the one file)
WebElement memberSubARow = findFolder(subAName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(memberSubARow))

TestObject filePresent = new TestObject()
filePresent.addProperty('xpath', ConditionType.EQUALS, "//*[contains(@data-search-keys, '" + fileName + "')]/td[1]/span")
WebUI.verifyElementPresent(filePresent, 10)

// write actions (create/upload) are offered inside SubA, proving READ_WRITE (not just READ) -
// actually create a folder to prove the write access really works end-to-end, not just that the
// buttons happen to be enabled
WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.verifyElementPresent(findTestObject('Folders/Page_Folders - PowerFolder/Upload files'), 5)
WebUI.verifyElementClickable(findTestObject('Folders/Page_Folders - PowerFolder/Upload files'))
WebUI.verifyElementPresent(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'), 5)
WebUI.verifyElementClickable(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))
String createdFolderName = 'SFS20_written_' + RandomStringUtils.randomAlphanumeric(6)
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), createdFolderName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

// creation navigates straight into the new folder - go back out and re-enter SubA to see it
// listed as a row, proving it was really created on the server, not just a UI-only success
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/lang_Home'))
WebElement memberSubARowAgain = findFolder(subAName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(memberSubARowAgain))

TestObject createdFolderPresent = new TestObject()
createdFolderPresent.addProperty('xpath', ConditionType.EQUALS, "//a[contains(concat(' ',normalize-space(@class),' '),' pica-name ') and normalize-space(text())='" + createdFolderName + "']")
WebUI.verifyElementPresent(createdFolderPresent, 10)

WebUI.closeBrowser()

WebElement findFolder(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//td[2]/span/a[contains(text(),'" + name + "')]"))
}

WebElement findRow(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//*[contains(@data-search-keys, '" + name + "')]/td[1]/span"))
}

WebElement findShareButton(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//*[contains(@data-search-keys, '" + name + "')]/td[7]/a/span"))
}
