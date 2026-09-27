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


WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('LeftNavigationIcons/folders'))

String tlfName = 'SFS05_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))
WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Folders/inputFolderName'), tlfName)
WebUI.click(findTestObject('Folders/buttonOK'))

String subFolderName = 'SFS05_Sub_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), subFolderName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebUI.click(findTestObject('Links/share_icon_inside_folder'))
WebUI.verifyElementVisible(findTestObject('Subfoldersharing/share_inheritance_container'))
WebUI.delay(2)

WebElement inheritanceToggleEl = WebUI.findWebElement(findTestObject('Subfoldersharing/share_inheritance_toggle'), 5)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(inheritanceToggleEl))

WebUI.waitForElementVisible(findTestObject('Subfoldersharing/inheritance_dialog_title'), 5)
WebUI.verifyElementText(findTestObject('Subfoldersharing/inheritance_dialog_title'), 'Own access rights for "' + subFolderName + '"?')
WebUI.verifyElementText(findTestObject('Subfoldersharing/inheritance_dialog_body'), 'This folder will no longer take over access rights from its parent folder. The currently effective permissions are copied as its own, so nobody loses access immediately. You can adjust them afterwards.')

WebUI.click(findTestObject('Subfoldersharing/confirmation_dialog_cancel'))
WebUI.verifyElementChecked(findTestObject('Subfoldersharing/share_inheritance_toggle'), 5)

WebUI.click(findTestObject('Share/close_button_folder_share_mail'))
WebUI.closeBrowser()
