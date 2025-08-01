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

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementClickable(findTestObject('External links/Page_Dashboard - PowerFolder/license'))

WebUI.delay(2)

WebUI.click(findTestObject('External links/Page_Dashboard - PowerFolder/license'))

WebUI.switchToWindowIndex(1)

String currentUrl = WebUI.getUrl()

WebUI.comment('L\'URL actuelle est: ' + currentUrl)

String expectedUrl = 'https://www.powerfolder.com/pricing/'

boolean isCorrectUrl = currentUrl.equals(expectedUrl)

WebUI.verifyEqual(isCorrectUrl, true)

WebUI.verifyElementText(findTestObject('External links/Page_Pricing/Compare Functions'), 'COMPARE FUNCTIONS')

WebUI.closeWindowIndex(1)

WebUI.switchToWindowIndex(0)

WebUI.delay(2)

WebUI.closeBrowser()

