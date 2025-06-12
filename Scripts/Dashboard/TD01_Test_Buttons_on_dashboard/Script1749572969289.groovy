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

WebUI.verifyElementPresent(findTestObject('Dashboard/servertab_mimas'), 0)

WebUI.verifyElementPresent(findTestObject('Dashboard/Accounts_Manage'), 0)

WebUI.verifyElementPresent(findTestObject('Dashboard/Storage_Manage'), 0)

WebUI.verifyElementPresent(findTestObject('Dashboard/Folders_Manage'), 0)

WebUI.verifyElementPresent(findTestObject('Dashboard/Pricing_License'), 0)

WebUI.verifyElementPresent(findTestObject('Dashboard/Activation_Manage'), 0)

WebUI.verifyElementPresent(findTestObject('Dashboard/Groups_Manage'), 0)

WebUI.verifyElementPresent(findTestObject('Dashboard/Org_Manage'), 0)

WebUI.verifyElementPresent(findTestObject('Dashboard/Dashboard_Logs'), 0)

WebUI.click(findTestObject('Dashboard/Accounts_Manage'))

WebUI.verifyEqual(WebUI.getWindowTitle(), 'Accounts - PowerFolder')

WebUI.click(findTestObject('Dashboard/Left_Menu_Link_Dashboard'))

WebUI.click(findTestObject('Dashboard/Storage_Manage'))

WebUI.verifyEqual(WebUI.getWindowTitle(), 'Storage - PowerFolder')

WebUI.click(findTestObject('Dashboard/Left_Menu_Link_Dashboard'))

WebUI.click(findTestObject('Dashboard/Folders_Manage'))

WebUI.verifyEqual(WebUI.getWindowTitle(), 'Folders - PowerFolder')

WebUI.click(findTestObject('Dashboard/Left_Menu_Link_Dashboard'))

WebUI.click(findTestObject('Dashboard/Groups_Manage'))

WebUI.verifyEqual(WebUI.getWindowTitle(), 'Groups - PowerFolder')

WebUI.click(findTestObject('Dashboard/Left_Menu_Link_Dashboard'))

WebUI.click(findTestObject('Dashboard/Org_Manage'))

WebUI.verifyEqual(WebUI.getWindowTitle(), 'Organizations - PowerFolder')

WebUI.click(findTestObject('Dashboard/Left_Menu_Link_Dashboard'))

WebUI.click(findTestObject('Dashboard/Pricing_License'))

WebUI.switchToWindowIndex(1)

WebUI.verifyEqual(WebUI.getWindowTitle(), 'Pricing for PowerFolder Sync, Share and Backup Cloud and Onpremise')

WebUI.closeWindowIndex(1)

WebUI.switchToWindowIndex(0)

WebUI.click(findTestObject('Dashboard/Activation_Manage'))

WebUI.switchToWindowIndex(1)

WebUI.verifyEqual(WebUI.getWindowTitle(), 'Activation - PowerFolder')

WebUI.closeWindowIndex(1)

WebUI.switchToWindowIndex(0)

WebUI.closeBrowser()

