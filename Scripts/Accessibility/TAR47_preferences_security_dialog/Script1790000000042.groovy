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
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint

// The "Security" tile opens #pica_preference_dialog with sub-tabs for folder security
// and encrypted storage settings.
WebUI.openBrowser(GlobalVariable.URL)

WebUI.maximizeWindow()

WebUI.setEncryptedText(findTestObject('Login/inputEmail'), 'CKkAs2Ee0vA=')

WebUI.setEncryptedText(findTestObject('Login/inputPassword'), 'PpFy9OM6JMUrpEOD1UO9247r7Yrm9E0x')

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(2)

WebUI.verifyEqual(WebUI.getWindowTitle(), 'Dashboard - PowerFolder')

WebUI.click(findTestObject('LeftNavigationIcons/Preferences'))

WebUI.delay(2)

TestObject categoryButton = new TestObject('categoryButton')
categoryButton.addProperty('xpath', ConditionType.EQUALS, "//*[@id='button-security']")

WebUI.waitForElementClickable(categoryButton, 10)

WebUI.click(categoryButton)

WebUI.delay(3)

CustomKeywords.'accessibility.AccessibilityKeywords.checkAccessibility'()

// Close without saving - these are live admin/server configuration values.
TestObject cancelButton = new TestObject('cancelButton')
cancelButton.addProperty('xpath', ConditionType.EQUALS, "//*[@id='pica_preference_dialog']//button[contains(@class,'btn-cancel')]")

WebUI.click(cancelButton)

WebUI.closeBrowser()
