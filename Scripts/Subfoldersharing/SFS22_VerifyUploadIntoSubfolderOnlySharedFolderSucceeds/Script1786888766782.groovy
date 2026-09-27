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
 * Scenario "Group B - access of a subfolder-only user": upload a file into the shared subfolder.
 *
 * Regression: uploading into a subfolder that a user only has access to via a direct share on the
 * SUBFOLDER itself (no permission on the top folder) was previously denied with HTTP 403, because
 * the storage precheck evaluated the TOP FOLDER's permission instead of the subfolder's own one.
 *
 * Precondition: SubA shared with the second account as READ_WRITE; no permission on the top folder.
 * Steps: log in as the second account, open SubA, upload a file.
 * Expected: the upload succeeds and the file is listed afterwards; no permission error appears,
 * neither in the upload dialog nor in the browser console.
 */

// create the account that will only ever have READ_WRITE access to SubA (also logs in as admin)
WebUI.callTestCase(findTestCase('Accounts/Edit_Account/pre_test/Create_Account'), [:], FailureHandling.STOP_ON_FAILURE)
String memberEmail = GlobalVariable.userEmail

// admin login/account creation lands elsewhere in the admin UI - navigate to Folders
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

// create the top folder - not shared with anyone - creation navigates straight into it
String tlfName = 'SFS22_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))
WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Folders/inputFolderName'), tlfName)
WebUI.click(findTestObject('Folders/buttonOK'))

// create SubA inside the top folder - creation navigates straight into SubA
String subAName = 'SFS22_SubA_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), subAName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

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

// open SubA
WebElement subARow = findFolder(subAName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(subARow))

// upload a file into SubA
String fileName = 'sfs22_file_' + RandomStringUtils.randomAlphanumeric(6) + '.txt'
File localFile = File.createTempFile('sfs22_', '.txt')
localFile.text = 'SFS22 upload regression check'
File renamedFile = new File(localFile.getParentFile(), fileName)
localFile.renameTo(renamedFile)

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.verifyElementPresent(findTestObject('Folders/Page_Folders - PowerFolder/Upload files'), 5)
WebUI.click(findTestObject('Folders/Page_Folders - PowerFolder/Upload files'))

TestObject uploadInput = new TestObject('uploadInput')
uploadInput.addProperty('xpath', ConditionType.EQUALS, "//input[@id='upload_input_files']")
WebUI.waitForElementPresent(uploadInput, 10)
WebUI.uploadFile(uploadInput, renamedFile.getAbsolutePath())

// the upload succeeds - no permission error appears in the upload dialog
TestObject successMsg = new TestObject('successMsg')
successMsg.addProperty('xpath', ConditionType.EQUALS, "//*[contains(text(),'Successfully uploaded')]")
WebUI.waitForElementVisible(successMsg, 15)

TestObject dangerNotification = new TestObject('dangerNotification')
dangerNotification.addProperty('xpath', ConditionType.EQUALS, "//div[contains(concat(' ',normalize-space(@class),' '),' alert-danger ')]")
WebUI.verifyElementNotPresent(dangerNotification, 5)

TestObject closeUploadBtn = new TestObject('closeUploadBtn')
closeUploadBtn.addProperty('xpath', ConditionType.EQUALS, "//button[@id='upload_stop_button']")
WebUI.waitForElementClickable(closeUploadBtn, 10)
WebUI.click(closeUploadBtn)
renamedFile.delete()

// no permission error appeared in the browser console either (best-effort - some environments
// don't expose browser logs to Selenium, in which case this list is simply empty and the check
// passes trivially; a genuine 403/permission error found in the logs still fails the test)
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

// the file is listed afterwards
TestObject filePresent = new TestObject()
filePresent.addProperty('xpath', ConditionType.EQUALS, "//*[contains(@data-search-keys, '" + fileName + "')]/td[1]/span")
WebUI.verifyElementPresent(filePresent, 10)

WebUI.closeBrowser()

WebElement findFolder(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//td[2]/span/a[contains(text(),'" + name + "')]"))
}

WebElement findRow(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//*[contains(@data-search-keys, '" + name + "')]/td[1]/span"))
}
