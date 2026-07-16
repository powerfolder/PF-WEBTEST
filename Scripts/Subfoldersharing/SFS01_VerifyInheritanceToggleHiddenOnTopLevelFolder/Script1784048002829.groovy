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

/*
 * Subfolder Sharing spec, section 4.1 / PFC-3543: the "set own permissions" (break inheritance)
 * toggle only makes sense for a subfolder, since a top-level folder has no parent to inherit from.
 * This test verifies the toggle stays hidden when sharing a top-level folder.
 */

// log in as admin
WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

// login lands on the Dashboard - navigate to the Folders section
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

// create a new top-level folder - creation navigates straight into it
String tlfName = 'SFS01_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))
WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Folders/inputFolderName'), tlfName)
WebUI.click(findTestObject('Folders/buttonOK'))

// open the share dialog for the (currently open) top-level folder via the sharing icon
WebUI.click(findTestObject('Links/share_icon_inside_folder'))

// DIAGNOSTIC (PFC-3575): log the real client-side state of the shared folder so we can see
// why the toggle might wrongly think this top-level folder is a subfolder. Remove once the
// root cause is confirmed and fixed.
def cwdState = WebUI.executeJavaScript(
    "var f = Picasso.get('cwd'); return JSON.stringify({isSubFolder: f.getIsSubFolder(), path: f._path, flags: f._flags, name: f.getName(), oid: f._oid});",
    null)
WebUI.comment('DIAGNOSTIC cwd state for top-level folder "' + tlfName + '": ' + cwdState)

// verify the inheritance toggle is not shown for a top-level folder
WebUI.verifyElementNotVisible(findTestObject('Subfoldersharing/share_inheritance_container'))

WebUI.click(findTestObject('Share/close_button_folder_share_mail'))
WebUI.closeBrowser()
