import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint

// Follows the same navigation as Test Cases/Organization/TO01_CreateNewOrganization up
// to opening the "Create Organization" dialog, but scans it while open and then
// cancels instead of filling it in - no organization is created by this test.
WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable'): ''], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Organization/SelectOrganization'))

WebUI.click(findTestObject('Organization/DropDownToggle'))

WebUI.click(findTestObject('Organization/CreateOrganization'))

WebUI.delay(3)

// Scans the "Create Organization" dialog while it is open.
CustomKeywords.'accessibility.AccessibilityKeywords.checkAccessibility'()

WebUI.closeBrowser()
