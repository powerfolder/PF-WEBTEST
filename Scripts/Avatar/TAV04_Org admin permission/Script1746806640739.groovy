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
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
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
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import java.util.Random as Random

WebUI.callTestCase(findTestCase('Organization/Pre_test/Crete_membr_org'), [:], FailureHandling.STOP_ON_FAILURE)

String Organization_name = GlobalVariable.organisationName

String emailId = GlobalVariable.userEmail

println('Organization_name: ' + Organization_name)

println('Organization_name: ' + emailId)

WebUI.click(findTestObject('Organization/AddMembers'))

WebUI.setText(findTestObject('Organization/InputAddAccountByName'), emailId)

WebUI.sendKeys(findTestObject('Organization/InputAddAccountByName'), Keys.chord(Keys.ENTER))

WebUI.delay(3)

WebUI.click(findTestObject('Organization/AddMemberButton'))

WebUI.click(findTestObject('Organization/SaveButton'))

WebUI.delay(2)

WebUI.refresh()

WebElement btn = findORG(Organization_name)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.click(findTestObject('Organization/AddMembers'), FailureHandling.OPTIONAL)

String memberName = WebUI.getText(findTestObject('Accounts/addedMember'))

WebUI.verifyEqual(memberName, emailId)

WebUI.click(findTestObject('Organization/Page_Organizations - PowerFolder/button_Is member'))

WebUI.waitForElementVisible(findTestObject('Organization/Page_Organizations - PowerFolder/checkbox_IsAdmin'), 5)

WebUI.check(findTestObject('Organization/Page_Organizations - PowerFolder/checkbox_IsAdmin'))

WebUI.click(findTestObject('Organization/SaveButton'))

WebUI.delay(1)

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

WebUI.setText(findTestObject('Login/inputEmail'), emailId)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(3)

WebUI.click(findTestObject('Organization/Page_Dashboard - PowerFolder/Manage_Org_Dashboard'))

WebUI.click(findTestObject('Organization/SelectBranding'))

//WebUI.click(findTestObject('Organization/Page_Organizations - PowerFolder/Change_org_avatar'))
WebUI.click(findTestObject('Page_Organizations - PowerFolder/a_Change'))

String projDir = RunConfiguration.getProjectDir()

String fname = projDir + '/Images/organization.png'

WebUI.uploadFile(findTestObject('Accounts/AddFileButton'), fname)

WebUI.delay(3)

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Close'))

WebUI.scrollToElement(findTestObject('Organization/SaveButton'), 5)

WebUI.click(findTestObject('Organization/SaveButton'))

WebUI.click(findTestObject('Organization/Page_Dashboard - PowerFolder/Manage_Org_Dashboard'))

WebUI.click(findTestObject('Organization/SelectBranding'))

WebUI.delay(3)

WebUI.closeBrowser()

WebElement findORG(String Organization_name) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//a[contains(text(),\'' + Organization_name) + '\')]'))
}

