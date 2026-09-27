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
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint

// SAFETY: this dialog's primary button (#confirm-button) actually deletes/closes the
// logged-in account. This script only ever clicks the secondary "No" button to close
// the dialog - #confirm-button must never be clicked.
WebUI.callTestCase(findTestCase('My Account/Pre_test/Create Account'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/My_account'))

TestObject closeAccountButton = new TestObject('closeAccountButton')
closeAccountButton.addProperty('xpath', ConditionType.EQUALS, "//*[@id='profile_close_account']")

WebUI.waitForElementClickable(closeAccountButton, 10)

WebUI.click(closeAccountButton)

WebUI.delay(3)

// Scans the close-account confirmation dialog while it is open.
CustomKeywords.'accessibility.AccessibilityKeywords.checkAccessibility'()

// Dismiss via the "No" button - never #confirm-button.
TestObject noButton = new TestObject('noButton')
noButton.addProperty('xpath', ConditionType.EQUALS, "//*[@id='pica_close_account_confirmation_dialog']//div[contains(@class,'modal-footer')]/button[contains(@class,'btn-secondary')]")

WebUI.click(noButton)

WebUI.closeBrowser()
