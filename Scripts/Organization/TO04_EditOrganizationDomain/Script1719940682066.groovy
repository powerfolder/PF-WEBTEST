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
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.By as By
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement

WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl(GlobalVariable.URL)
WebUI.verifyEqual(WebUI.getWindowTitle(), 'Login - PowerFolder')
WebUI.verifyEqual(WebUI.getAttribute(findTestObject('Login/poweredBy'), 'href'), 'https://www.powerfolder.com/')
WebUI.verifyEqual(WebUI.getAttribute(findTestObject('Login/documentationLink'), 'href'), 'https://wiki.powerfolder.com/')
WebUI.verifyElementClickable(findTestObject('Login/registerNewAccountLink'))
WebUI.setText(findTestObject('Login/inputEmail'), GlobalVariable.Username)
WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Password)
WebUI.click(findTestObject('Login/loginSubmit'))
WebUI.verifyEqual(WebUI.getWindowTitle(), 'Dashboard - PowerFolder')

assert WebUI.getWindowTitle().equals('Dashboard - PowerFolder')
WebUI.click(findTestObject('Organization/SelectOrganization'))
WebUI.click(findTestObject('Organization/DropDownToggle'))
WebUI.click(findTestObject('Organization/CreateOrganization'))
String lan = GlobalVariable.LANG
if(!lan.equals('GERMAN')) {
WebUI.verifyEqual(WebUI.getText(findTestObject('Organization/VerifyCreateOrganization')), 'Create a new Organization',  FailureHandling.CONTINUE_ON_FAILURE)
}else {
	WebUI.verifyEqual(WebUI.getText(findTestObject('Organization/VerifyCreateOrganization')), 'Organisation neu erstellen',  FailureHandling.CONTINUE_ON_FAILURE)
	}
WebUI.delay(3)
WebUI.setText(findTestObject('Organization/InputName'), "AutomationTest")
WebUI.setText(findTestObject('Organization/InputMaxNumber'), "10")
WebUI.setText(findTestObject('Organization/InputQuota'), "2")
def currentDate = new Date()
// Format the date and time as per your requirement
def dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a")
def formattedDate = dateFormat.format(currentDate)
WebUI.click(findTestObject('Organization/InputValidFrom'))
def calendar = Calendar.getInstance()
calendar.setTime(currentDate)
calendar.add(Calendar.DAY_OF_MONTH, 3)
def futureDate = calendar.getTime()
// Set the date and time with the timestamp plus 3 days
WebUI.setText(findTestObject('Organization/InputValidtill'), dateFormat.format(futureDate))
WebUI.setText(findTestObject('Organization/EnterNotes'), "AutomationNotes")
WebUI.delay(3)
WebUI.click(findTestObject('Organization/SaveButton'))
WebUI.delay(10)
WebUI.click(findTestObject('Organization/SelectCreatedOrganization'))
WebUI.click(findTestObject('Organization/EditButton'))

WebUI.click(findTestObject('Organization/SelectDomain'))
WebUI.setText(findTestObject('Organization/InputDomainName'), "AutomationTest.com")
WebUI.click(findTestObject('Organization/AddDomainButton'))
WebUI.verifyEqual(WebUI.getText(findTestObject('Organization/VerifyDomainName')), 'AutomationTest.com',  FailureHandling.CONTINUE_ON_FAILURE)

WebUI.click(findTestObject('Organization/SaveButton'))
WebUI.verifyEqual(WebUI.getText(findTestObject('Organization/VerifyOrganizationName')), 'AutomationTest',  FailureHandling.CONTINUE_ON_FAILURE)
WebUI.delay(10)
WebUI.click(findTestObject('Organization/SelectCreatedOrganization'))
WebUI.click(findTestObject('Organization/Deletebutton'))
WebUI.delay(3)
if(!lan.equals('GERMAN')) {
WebUI.verifyEqual(WebUI.getText(findTestObject('Organization/VerfiyDeleteMsg')), 'Do you really want to delete AutomationTest with all members and folders?',  FailureHandling.CONTINUE_ON_FAILURE)
}else {
	WebUI.verifyEqual(WebUI.getText(findTestObject('Organization/VerfiyDeleteMsg')), 'Möchten Sie wirklich AutomationTest mit allen Mitgliedern und Ordnern löschen?',  FailureHandling.CONTINUE_ON_FAILURE)
	
}
WebUI.click(findTestObject('Organization/SelectYesButton'))
WebUI.delay(3)
//WebUI.verifyEqual(WebUI.getText(findTestObject('Organization/VerifyToastMsg')), 'Organization deleted')
WebUI.closeBrowser()

