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

// Reuses the existing Upload-form fixture (Test Cases/Upload form/Pre_Test) that
// logs in and creates a folder, then opens the admin-side "Create upload form" dialog.
WebUI.callTestCase(findTestCase('Upload form/Pre_Test/Creat_Folder'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementClickable(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Create upload form'))

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Create upload form'))

WebUI.delay(3)

WebUI.setText(findTestObject('1Upload_Form/Page_Error - PowerFolder/Page_Folders - PowerFolder/input_Create_uploadform_heading'), 'TAR32_Accessibility_Test')

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/change_description'))
WebUI.sendKeys(findTestObject('1Upload_Form/Page_Folders - PowerFolder/change_description'), 'Accessibility test - upload form creation dialog')

// Scans the "Create upload form" dialog (heading/description/expiry fields) while it is open.
CustomKeywords.'accessibility.AccessibilityKeywords.checkAccessibility'()

WebUI.scrollToElement(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Save'), 1)

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Save'))

WebUI.closeBrowser()
