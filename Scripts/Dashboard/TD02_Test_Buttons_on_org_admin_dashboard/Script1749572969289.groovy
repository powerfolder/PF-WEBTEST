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
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.DataFlavor as DataFlavor
import org.openqa.selenium.By as By
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.nio.file.Path as Path
import java.nio.file.Files as Files
import java.text.SimpleDateFormat as SimpleDateFormat
import java.util.Calendar as Calendar
import java.util.Date as Date
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import java.util.Random as Random

WebUI.callTestCase(findTestCase('Accounts/Edit_Account/pre_test/Create_Account'), [:], FailureHandling.STOP_ON_FAILURE)

println(GlobalVariable.userEmail)

WebElement btn = findAccount(GlobalVariable.userEmail)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/Edit_account'))

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/a_Organizations'))

String organization_name = 'My_Organization_' + RandomStringUtils.randomAlphabetic(4)

WebUI.setText(findTestObject('Accounts/Edit_Accounts - PowerFolder/organizations_input'), organization_name)

WebUI.delay(2)

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/Create new Organization'))

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

println(GlobalVariable.userEmail)

WebUI.setText(findTestObject('Login/inputEmail'), GlobalVariable.userEmail)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(3)

WebUI.verifyElementPresent(findTestObject('Dashboard/org_admin_accounts_Manage'), 0)

WebUI.verifyElementPresent(findTestObject('Dashboard/org_admin_groups_Manage'), 0)

WebUI.verifyElementPresent(findTestObject('Dashboard/org_admin_edit_org'), 0)

WebUI.click(findTestObject('Dashboard/org_admin_accounts_Manage'))

WebUI.verifyEqual(WebUI.getWindowTitle(), 'Accounts - PowerFolder')

WebUI.click(findTestObject('Dashboard/Left_Menu_Link_Dashboard'))

WebUI.click(findTestObject('Dashboard/org_admin_groups_Manage'))

WebUI.verifyEqual(WebUI.getWindowTitle(), 'Groups - PowerFolder')

WebUI.click(findTestObject('Dashboard/Left_Menu_Link_Dashboard'))

WebUI.click(findTestObject('Dashboard/org_admin_edit_org'))

WebUI.verifyElementPresent(findTestObject('Dashboard/org_admin_organization_name'), 0)

WebUI.closeBrowser()

WebElement findAccount(String emailId) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + emailId) + '\')]/td[1]/span'))
}

