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
 * Subfolder Sharing spec, section 3.1 "Vererbung wiederherstellen": unchecking the toggle on an
 * interrupted subfolder must ask for confirmation (title "Restore inheritance"), then remove the
 * snapshotted explicit permissions and let the parent's permissions apply again - a member who is
 * still on the parent folder keeps access, now shown as inherited again.
 *
 * Note: a still-pending invitation is not resolved as a permission holder at all (renderInvitation
 * in share.js has no inherited marker, and is a different list than confirmed members). So the
 * member must accept the top-level folder invitation BEFORE the subfolder is created, otherwise
 * neither the interrupt-snapshot nor the "(inherited)" tag behave as expected.
 */

// create the member who stays on the parent folder for the whole test
WebUI.callTestCase(findTestCase('Accounts/Edit_Account/pre_test/Create_Account'), [:], FailureHandling.STOP_ON_FAILURE)
String memberEmail = GlobalVariable.userEmail
// the share dialog lists members by their display name (first + last name), not by email,
// once the account has a name set - which Create_Account always does
String memberDisplayName = GlobalVariable.userName + ' ' + GlobalVariable.userLastName

// admin login/account creation lands elsewhere in the admin UI - navigate to Folders
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

// create a top-level folder - creation navigates straight into it
String tlfName = 'SFS09_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))
WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Folders/inputFolderName'), tlfName)
WebUI.click(findTestObject('Folders/buttonOK'))

// share the (currently open) top-level folder with the member
WebUI.click(findTestObject('Links/share_icon_inside_folder'))
WebUI.setText(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), memberEmail)
WebUI.sendKeys(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), Keys.chord(Keys.ENTER))
WebUI.click(findTestObject('Share/close_button_folder_share_mail'))

// log out admin, log in as the member and accept the invitation BEFORE the subfolder exists,
// so the member is a confirmed permission holder for the interrupt/restore flow below
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))
WebUI.setText(findTestObject('Login/inputEmail'), memberEmail)
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

// log out the member, log back in as admin
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))
WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

// re-enter the top-level folder and create a subfolder inside it - creation navigates
// straight into the new subfolder
WebElement tlfRow = findFolder(tlfName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(tlfRow))

String subFolderName = 'SFS09_Sub_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), subFolderName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

// interrupt inheritance on the (currently open) subfolder and confirm
WebUI.click(findTestObject('Links/share_icon_inside_folder'))
// use a native JS click - a plain Selenium click on this checkbox does not
// reliably fire the jQuery 'change' handler that drives the confirmation dialog
WebElement inheritanceToggleEl = WebUI.findWebElement(findTestObject('Subfoldersharing/share_inheritance_toggle'), 5)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(inheritanceToggleEl))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/confirme_handover'))
WebUI.verifyElementText(findTestObject('notifications_toastmessage'), 'Own permissions have been set.')
WebUI.verifyElementText(findTestObject('Share/Page_Folders - PowerFolder/td_usermailcom'), memberDisplayName)

// restore inheritance - uncheck the toggle
// use a native JS click - a plain Selenium click on this checkbox does not
// reliably fire the jQuery 'change' handler that drives the confirmation dialog
WebElement restoreToggleEl = WebUI.findWebElement(findTestObject('Subfoldersharing/share_inheritance_toggle'), 5)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(restoreToggleEl))

// verify the restore-confirmation dialog text - wait for the modal's fade-in to finish first,
// otherwise Selenium's getText() can return "" for an element that is present but not yet visible
WebUI.waitForElementVisible(findTestObject('Subfoldersharing/confirmation_dialog_title'), 5)
WebUI.verifyElementText(findTestObject('Subfoldersharing/confirmation_dialog_title'), 'Restore inheritance')
String bodyText = WebUI.getText(findTestObject('Subfoldersharing/confirmation_dialog_body'))
WebUI.verifyMatch(bodyText, '.*' + subFolderName + '.*archived.*', true)

// confirm the restore
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/confirme_handover'))

// verify the success notification, the toggle state, and that the member is inherited again
WebUI.verifyElementText(findTestObject('notifications_toastmessage'), 'Inheritance has been restored.')
WebUI.verifyElementNotChecked(findTestObject('Subfoldersharing/share_inheritance_toggle'), 5)
WebUI.verifyElementText(findTestObject('Share/Page_Folders - PowerFolder/td_usermailcom'), memberDisplayName + ' (inherited)')

WebUI.click(findTestObject('Share/close_button_folder_share_mail'))
WebUI.closeBrowser()

WebElement findFolder(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//td[2]/span/a[contains(text(),'" + name + "')]"))
}

WebElement findRow(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//*[contains(@data-search-keys, '" + name + "')]/td[1]/span"))
}
