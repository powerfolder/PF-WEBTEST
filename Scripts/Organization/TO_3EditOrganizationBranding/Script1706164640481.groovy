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
WebUI.verifyEqual(WebUI.getText(findTestObject('Organization/VerifyCreateOrganization')), 'Create a new Organization',  FailureHandling.CONTINUE_ON_FAILURE)
WebUI.delay(3)
WebUI.setText(findTestObject('Organization/InputName'), "AutomationTest")
WebUI.setText(findTestObject('Organization/InputMaxNumber'), "10")
WebUI.setText(findTestObject('Organization/InputQuota'), "2")
WebUI.setText(findTestObject('Organization/InputValidFrom'), "01/17/2024 12:01 PM")
WebUI.setText(findTestObject('Organization/InputValidtill'), "01/26/2024 11:02 AM")
WebUI.setText(findTestObject('Organization/EnterNotes'), "AutomationNotes")
WebUI.delay(3)
WebUI.click(findTestObject('Organization/SaveButton'))
WebUI.delay(10)
WebUI.click(findTestObject('Organization/SelectCreatedOrganization'))
WebUI.click(findTestObject('Organization/EditButton'))
WebUI.verifyEqual(WebUI.getText(findTestObject('Organization/VerifyEditPageHeader')), 'Edit Organization',  FailureHandling.CONTINUE_ON_FAILURE)
WebUI.click(findTestObject('Organization/SelectBranding'))
WebUI.setText(findTestObject('Organization/SetTextIconAndColor'), "#1f1a1a")
WebUI.setText(findTestObject('Organization/SecondaryColor'), "#5f1f1f")
WebUI.setText(findTestObject('Organization/BackgroundColor'), "#972e2e")
WebUI.click(findTestObject('Organization/SaveButton'))
WebUI.verifyEqual(WebUI.getText(findTestObject('Organization/VerifyOrganizationName')), 'AutomationTest',  FailureHandling.CONTINUE_ON_FAILURE)
WebUI.delay(10)
WebUI.click(findTestObject('Organization/SelectCreatedOrganization'))
WebUI.click(findTestObject('Organization/Deletebutton'))
WebUI.delay(3)
WebUI.verifyEqual(WebUI.getText(findTestObject('Organization/VerfiyDeleteMsg')), 'Do you really want to delete AutomationTest with all members and folders?',  FailureHandling.CONTINUE_ON_FAILURE)
WebUI.click(findTestObject('Organization/SelectYesButton'))
WebUI.delay(3)
//WebUI.verifyEqual(WebUI.getText(findTestObject('Organization/VerifyToastMsg')), 'Organization deleted')
WebUI.closeBrowser()

