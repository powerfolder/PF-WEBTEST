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
import org.openqa.selenium.WebElement

/*
 * Subfolder Sharing spec, section 3.1 / 4.4 / decision #1: checking the inheritance toggle must
 * ask for confirmation before anything changes (title "Set own permissions", body naming the
 * subfolder). Cancelling must leave the toggle unchecked and inheritance untouched.
 *
 * Known issue (confirmed 2026-07-16): on the current build, no 'change' handler is bound to
 * #share_inheritance_toggle at all (verified via jQuery._data(el, 'events') returning {}), so
 * checking the box neither reverts nor opens the confirmation dialog - the checkbox just behaves
 * like a plain, unmanaged HTML checkbox. This is an application bug (PFC-3543/3575 area), not a
 * test issue; this test is expected to stay red until it is fixed.
 */

// log in as admin
WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

// login lands on the Dashboard - navigate to the Folders section
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

// create a top-level folder - creation navigates straight into it
String tlfName = 'SFS05_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))
WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Folders/inputFolderName'), tlfName)
WebUI.click(findTestObject('Folders/buttonOK'))

// create a subfolder inside it - creation navigates straight into the new subfolder
String subFolderName = 'SFS05_Sub_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), subFolderName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

// open the share dialog for the (currently open) subfolder and check the inheritance toggle
WebUI.click(findTestObject('Links/share_icon_inside_folder'))
WebUI.verifyElementVisible(findTestObject('Subfoldersharing/share_inheritance_container'))

WebElement inheritanceToggleEl = WebUI.findWebElement(findTestObject('Subfoldersharing/share_inheritance_toggle'), 5)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(inheritanceToggleEl))

// verify the confirmation dialog text - wait for the modal's fade-in to finish first, otherwise
// Selenium's getText() can return "" for an element that is present but not yet visible
WebUI.waitForElementVisible(findTestObject('Subfoldersharing/confirmation_dialog_title'), 5)
WebUI.verifyElementText(findTestObject('Subfoldersharing/confirmation_dialog_title'), 'Set own permissions')
String bodyText = WebUI.getText(findTestObject('Subfoldersharing/confirmation_dialog_body'))
WebUI.verifyMatch(bodyText, '.*' + subFolderName + '.*adopted as explicit ones.*', true)

// cancel the dialog - inheritance must stay untouched
WebUI.click(findTestObject('Subfoldersharing/confirmation_dialog_cancel'))
WebUI.verifyElementNotChecked(findTestObject('Subfoldersharing/share_inheritance_toggle'), 5)

WebUI.click(findTestObject('Share/close_button_folder_share_mail'))
WebUI.closeBrowser()
