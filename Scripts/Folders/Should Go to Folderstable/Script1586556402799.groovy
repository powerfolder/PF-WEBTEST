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

WebUI.navigateToUrl(GlobalVariable.URL)

assert WebUI.getWindowTitle().equals('Login - PowerFolder')

WebUI.setText(findTestObject('Object Repository/Login/Page_Login - PowerFolder/input_register new account_Username'), GlobalVariable.Username)

WebUI.click(findTestObject('Object Repository/Login/Page_Login - PowerFolder/input_register new account_Login'))

isPresent = WebUI.waitForElementVisible(findTestObject('Object Repository/Login/Page_Login - PowerFolder/input_Recover password_Password'), 
    2)

assert isPresent

WebUI.setText(findTestObject('Object Repository/Login/Page_Login - PowerFolder/input_Recover password_Password'), GlobalVariable.Password)

WebUI.click(findTestObject('Object Repository/Login/Page_Login - PowerFolder/input_register new account_Login'))

assert WebUI.getWindowTitle().equals('Dashboard - PowerFolder')

WebUI.click(findTestObject('Object Repository/Folders/Page_Folders - PowerFolder/lang_Folders'))

assert WebUI.getWindowTitle().equals('Folders - PowerFolder')

