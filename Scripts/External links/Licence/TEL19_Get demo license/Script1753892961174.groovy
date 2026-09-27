import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

// DISABLED (isRun=false in Test Suites/External links.ts, 2026-08-29): the "Get demo license" link
// no longer exists on the Activation page (label_demo_license removed from activation.vm) — only
// "Get server license" remains, already covered by TEL18. Re-enable only if the demo license flow returns.

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementClickable(findTestObject('External links/Page_Dashboard - PowerFolder/Licence_btn'))

WebUI.click(findTestObject('External links/Page_Dashboard - PowerFolder/Licence_btn'))

WebUI.switchToWindowIndex(1)

WebUI.delay(2)

WebUI.verifyElementClickable(findTestObject('External links/Page_Activation - PowerFolder/Get demo license'))

WebUI.click(findTestObject('External links/Page_Activation - PowerFolder/Get demo license'))

String currentUrl = WebUI.getUrl()

WebUI.comment('L\'URL actuelle est: ' + currentUrl)

String expectedUrl = 'https://powerfolder.atlassian.net/servicedesk/customer/portal/11/user/login?destination=portal%2F11'

boolean isCorrectUrl = currentUrl.equals(expectedUrl)

WebUI.verifyEqual(isCorrectUrl, true)

WebUI.verifyElementText(findTestObject('External links/Page_Customer Login/Help Desk'), 'Service Desk')

WebUI.closeWindowIndex(1)

WebUI.switchToWindowIndex(0)

WebUI.delay(2)

WebUI.closeBrowser()

