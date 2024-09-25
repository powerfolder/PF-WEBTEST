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
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils

WebUI.callTestCase(findTestCase('My Account/Pre_test/Create Account'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/My_account'))

WebUI.click(findTestObject('My_Account/Detail/Page_Profile - PowerFolder/a_Details'))

WebUI.verifyElementClickable(findTestObject('My_Account/Detail/Page_Profile - PowerFolder/Details_first_name_Change'))

WebUI.click(findTestObject('My_Account/Detail/Page_Profile - PowerFolder/Details_first_name_Change'))

WebUI.clearText(findTestObject('My_Account/Detail/Page_Profile - PowerFolder/Page_Profile - PowerFolder/Details_Enter a new First Name'))

String newName = ('First_Name' + RandomStringUtils.randomNumeric(4))

WebUI.setText(findTestObject('My_Account/Detail/Page_Profile - PowerFolder/Page_Profile - PowerFolder/Details_Enter a new First Name'), 
    newName)

WebUI.click(findTestObject('My_Account/Detail/Page_Profile - PowerFolder/Page_Profile - PowerFolder/Details_new_name_button_Ok'))

WebUI.verifyElementText(findTestObject('My_Account/Detail/Page_Profile - PowerFolder/Verify_new_name'), newName)

WebUI.delay(3)

WebUI.closeBrowser()



