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
 * Scenario "Group A - sharing and revoking": share a subfolder with a GROUP (read/write).
 *
 * Precondition: a top folder with a subfolder SubA; a group exists, the second account is a
 * member of it and has no permission on the top folder.
 * Steps: as admin, share the ROW of SubA (from within the top folder's listing - the same entry
 * point as SFS20, not the "current folder" icon) and add the group with READ_WRITE; verify the
 * group appears in SubA's member list; log in as the second account and open SubA.
 * Expected: access is granted through the group membership alone (the account itself was never
 * given a permission); SubA's content is listed and writable.
 */

// create a group with one member account (also logs in as admin); sets
// GlobalVariable.userName (member email) and GlobalVariable.GroupName
WebUI.callTestCase(findTestCase('Groups/Pre_test/add member'), [:], FailureHandling.STOP_ON_FAILURE)
String memberEmail = GlobalVariable.userName
String groupName = GlobalVariable.GroupName

// navigate to the Folders section
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

// create the top folder - not shared with anyone - creation navigates straight into it
String tlfName = 'SFS21_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))
WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Folders/inputFolderName'), tlfName)
WebUI.click(findTestObject('Folders/buttonOK'))

// create SubA inside the top folder - creation navigates straight into SubA
String subAName = 'SFS21_SubA_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), subAName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

// go back to the top-level folder list, then open the top folder (viewing its content, where
// SubA now appears as a row)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/lang_Home'))
WebElement tlfRow = findFolder(tlfName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(tlfRow))

// use the row-level Share icon on SubA (not the "current folder" icon)
WebElement subAShareButton = findShareButton(subAName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(subAShareButton))

// set the permission to READ_WRITE before inviting the group
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/folder_share_permission_dropdown_toggle'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/folder_share_permission_r_w'))

WebUI.setText(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), groupName)
// wait for the autocomplete dropdown to load - a group name is not a valid email, so
// pressing Enter directly (as with an individual account's email) does not add it; the
// matching suggestion must be selected from the dropdown first, then submitted via Add
WebUI.waitForElementVisible(findTestObject('Share/Page_Folders - PowerFolder/tag'), 10)
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/tag'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/buttonAddEmail'))

// the group appears in SubA's member list straight away - no pending invitation to accept
WebUI.verifyElementText(findTestObject('Share/Page_Folders - PowerFolder/td_Group'), groupName)
WebUI.click(findTestObject('Share/close_button_folder_share_mail'))

// log out admin, log in as the second account (the group's member) - it never received a
// permission of its own, only the group did
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))
WebUI.setText(findTestObject('Login/inputEmail'), memberEmail)
WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)
WebUI.click(findTestObject('Login/loginSubmit'))
WebUI.delay(3)
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

// SubA is visible immediately (access via group membership alone); the top folder is not
TestObject subAPresent = new TestObject()
subAPresent.addProperty('xpath', ConditionType.EQUALS, "//a[contains(concat(' ',normalize-space(@class),' '),' pica-name ') and normalize-space(text())='" + subAName + "']")
WebUI.verifyElementPresent(subAPresent, 10)

TestObject tlfPresent = new TestObject()
tlfPresent.addProperty('xpath', ConditionType.EQUALS, "//a[contains(concat(' ',normalize-space(@class),' '),' pica-name ') and normalize-space(text())='" + tlfName + "']")
WebUI.verifyElementNotPresent(tlfPresent, 10)

// open SubA - its content is listed
WebElement memberSubARow = findFolder(subAName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(memberSubARow))
WebUI.verifyElementPresent(findTestObject('Folders/createFolderIcon'), 10)

// SubA's content is writable - actually create a folder to prove it, not just check the button
WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.verifyElementPresent(findTestObject('Folders/Page_Folders - PowerFolder/Upload files'), 5)
WebUI.verifyElementClickable(findTestObject('Folders/Page_Folders - PowerFolder/Upload files'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))
String createdFolderName = 'SFS21_written_' + RandomStringUtils.randomAlphanumeric(6)
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

WebElement findShareButton(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//*[contains(@data-search-keys, '" + name + "')]/td[7]/a/span"))
}
