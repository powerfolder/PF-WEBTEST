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

// Scroll to the Recent Warnings section on the admin dashboard (dashboard.vm:253-286).
TestObject logsHeader = new TestObject().addProperty('xpath', com.kms.katalon.core.testobject.ConditionType.EQUALS, "//h2[@id='dashboard_logs_label']")

WebUI.scrollToElement(logsHeader, 5)

// Verify the Recent Warnings table exists on the dashboard.
TestObject logsTable = new TestObject().addProperty('xpath', com.kms.katalon.core.testobject.ConditionType.EQUALS, "//table[@id='dashboard_logs_table']")

boolean tablePresent = WebUI.verifyElementPresent(logsTable, 10, FailureHandling.CONTINUE_ON_FAILURE)

assert tablePresent == true : 'Recent Warnings table (#dashboard_logs_table) is not present on the dashboard.'

// Verify a real data row shows up (spinner row has class pica-not-striped — exclude it).
TestObject logsRow = new TestObject().addProperty('xpath', com.kms.katalon.core.testobject.ConditionType.EQUALS, "//table[@id='dashboard_logs_table']/tbody/tr[not(contains(concat(' ',normalize-space(@class),' '),' pica-not-striped '))]")

boolean rowPresent = WebUI.verifyElementPresent(logsRow, 15, FailureHandling.CONTINUE_ON_FAILURE)

assert rowPresent == true : 'Recent Warnings table did not populate with any log entry.'

WebUI.closeBrowser()

