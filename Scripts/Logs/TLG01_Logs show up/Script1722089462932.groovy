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

WebUI.scrollToElement(findTestObject('1Logs/Page_Dashboard - PowerFolder/lang_Logs'), 1)

WebUI.verifyElementClickable(findTestObject('1Logs/Page_Dashboard - PowerFolder/lang_Logs'))

WebUI.click(findTestObject('1Logs/Page_Dashboard - PowerFolder/lang_Logs'))

WebUI.verifyElementPresent(findTestObject('1Logs/Page_Logs - PowerFolder/div_Started auto refresh'), 2)

WebUI.click(findTestObject('1Logs/Page_Logs - PowerFolder/th_Date'))

WebUI.delay(2)

WebUI.click(findTestObject('1Logs/Page_Logs - PowerFolder/th_Date'))

WebUI.click(findTestObject('1Logs/Page_Logs - PowerFolder/th_Logger'))

WebUI.delay(2)

WebUI.click(findTestObject('1Logs/Page_Logs - PowerFolder/th_Logger'))

WebUI.click(findTestObject('1Logs/Page_Logs - PowerFolder/th_Account'))

WebUI.delay(2)

WebUI.click(findTestObject('1Logs/Page_Logs - PowerFolder/th_Account'))

WebUI.click(findTestObject('1Logs/Page_Logs - PowerFolder/th_Message'))

WebUI.delay(2)

WebUI.click(findTestObject('1Logs/Page_Logs - PowerFolder/th_Message'))

WebUI.closeBrowser()

