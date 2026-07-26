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
 * Subfolder Sharing spec, section 3.1 "Vererbung wiederherstellen", warning #6 in 4.4: a user who
 * was only granted an explicit permission on an interrupted subfolder (and never had access to the
 * parent top-level folder) must lose access entirely once inheritance is restored - the explicit
 * permission is archived and removed, and the parent's permissions do not cover this user.
 *
 * Note: a still-pending invitation is not resolved as a confirmed permission holder (renderInvitation
 * in share.js has no inherited marker and is a different list than confirmed members). So memberA
 * must accept the top-level folder invitation BEFORE the subfolder is created, otherwise neither the
 * interrupt-snapshot nor the "(inherited)" tag behave as expected.
 */

// create memberA - stays on the parent folder for the whole test (the "keeps access" baseline)
WebUI.callTestCase(findTestCase('Accounts/Edit_Account/pre_test/Create_Account'), [:], FailureHandling.STOP_ON_FAILURE)
String memberAEmail = GlobalVariable.userEmail
// the share dialog lists members by their display name (first + last name), not by email,
// once the account has a name set - which Create_Account always does
String memberADisplayName = GlobalVariable.userName + ' ' + GlobalVariable.userLastName

// create memberB inline - will only ever get an explicit permission on the subfolder
// (still on the Accounts page after Create_Account, so we can create it right away)
String memberBEmail = ('sfs10b_' + RandomStringUtils.randomNumeric(6)) + '@qa-automated-webtest.com'
String memberBLastName = RandomStringUtils.randomAlphabetic(6)
String memberBDisplayName = 'SFS10B ' + memberBLastName
WebUI.click(findTestObject('Accounts/CreateButton'))
WebUI.click(findTestObject('Accounts/ClickCreateAccount'))
WebUI.setText(findTestObject('Accounts/InputUserOrEmail'), memberBEmail)
WebUI.setText(findTestObject('Accounts/InputPassword'), GlobalVariable.Pass)
WebUI.setText(findTestObject('Accounts/InputFirstName'), 'SFS10B')
WebUI.setText(findTestObject('Accounts/InputLastName'), memberBLastName)
WebUI.setText(findTestObject('Accounts/InputPhoneNo'), '(030) 1234567')
WebUI.setText(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/account_storage_overwiew'), '5')
WebUI.click(findTestObject('Accounts/SaveButton'))
WebUI.delay(2)

// navigate to the Folders section
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

// create a top-level folder - creation navigates straight into it
String tlfName = 'SFS10_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))
WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Folders/inputFolderName'), tlfName)
WebUI.click(findTestObject('Folders/buttonOK'))

// share the (currently open) top-level folder with memberA only
WebUI.click(findTestObject('Links/share_icon_inside_folder'))
WebUI.setText(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), memberAEmail)
WebUI.sendKeys(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), Keys.chord(Keys.ENTER))
WebUI.click(findTestObject('Share/close_button_folder_share_mail'))

// log out admin, log in as memberA and accept the invitation BEFORE the subfolder exists
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))
WebUI.setText(findTestObject('Login/inputEmail'), memberAEmail)
WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)
WebUI.click(findTestObject('Login/loginSubmit'))
WebUI.delay(3)
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

WebElement invitationRow = findRow(tlfName)
WebUI.verifyEqual(invitationRow.isDisplayed(), true)
// click the folder name link (not the invitation icon) to open the accept dialog
WebElement invitationLink = findFolder(tlfName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(invitationLink))
WebUI.verifyElementClickable(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))

// log out memberA, log back in as admin
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))
WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

// re-enter the top-level folder and create a subfolder inside it - creation navigates
// straight into the new subfolder
WebElement tlfRow = findFolder(tlfName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(tlfRow))

String subFolderName = 'SFS10_Sub_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), subFolderName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

// interrupt inheritance on the (currently open) subfolder (memberA is snapshotted as explicit)
WebUI.click(findTestObject('Links/share_icon_inside_folder'))
// use a native JS click - a plain Selenium click on this checkbox does not
// reliably fire the jQuery 'change' handler that drives the confirmation dialog
WebElement inheritanceToggleEl = WebUI.findWebElement(findTestObject('Subfoldersharing/share_inheritance_toggle'), 5)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(inheritanceToggleEl))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/confirme_handover'))
WebUI.verifyElementText(findTestObject('notifications_toastmessage'), 'Own permissions have been set.')

// while interrupted, invite memberB directly on the subfolder (explicit-only, never on the parent)
WebUI.setText(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), memberBEmail)
WebUI.sendKeys(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), Keys.chord(Keys.ENTER))

TestObject memberBRow = new TestObject()
memberBRow.addProperty('xpath', ConditionType.EQUALS, "//table[@id='share_table']//td[contains(text(),'" + memberBDisplayName + "')]")
WebUI.verifyElementPresent(memberBRow, 10)

// restore inheritance - uncheck the toggle and confirm
// use a native JS click - a plain Selenium click on this checkbox does not
// reliably fire the jQuery 'change' handler that drives the confirmation dialog
WebElement restoreToggleEl = WebUI.findWebElement(findTestObject('Subfoldersharing/share_inheritance_toggle'), 5)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(restoreToggleEl))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/confirme_handover'))
WebUI.verifyElementText(findTestObject('notifications_toastmessage'), 'Inheritance has been restored.')

// memberB's explicit-only permission was archived/removed - memberA is inherited again
WebUI.verifyElementNotPresent(memberBRow, 10)
WebUI.verifyElementText(findTestObject('Share/Page_Folders - PowerFolder/td_usermailcom'), memberADisplayName + ' (inherited)')
WebUI.click(findTestObject('Share/close_button_folder_share_mail'))

// log out admin, log in as memberB and verify neither the subfolder nor the parent are reachable
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))
WebUI.setText(findTestObject('Login/inputEmail'), memberBEmail)
WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)
WebUI.click(findTestObject('Login/loginSubmit'))
WebUI.delay(3)

// login lands on the Dashboard - navigate to the Folders section
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

TestObject tlfPresent = new TestObject()
tlfPresent.addProperty('xpath', ConditionType.EQUALS, "//a[contains(concat(' ',normalize-space(@class),' '),' pica-name ') and normalize-space(text())='" + tlfName + "']")
WebUI.verifyElementNotPresent(tlfPresent, 10)

TestObject subFolderPresent = new TestObject()
subFolderPresent.addProperty('xpath', ConditionType.EQUALS, "//a[contains(concat(' ',normalize-space(@class),' '),' pica-name ') and normalize-space(text())='" + subFolderName + "']")
WebUI.verifyElementNotPresent(subFolderPresent, 10)

WebUI.closeBrowser()

WebElement findFolder(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//td[2]/span/a[contains(text(),'" + name + "')]"))
}

WebElement findRow(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//*[contains(@data-search-keys, '" + name + "')]/td[1]/span"))
}
