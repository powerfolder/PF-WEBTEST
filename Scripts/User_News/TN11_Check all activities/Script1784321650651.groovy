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
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

// Call the test case Create Folder
WebUI.callTestCase(findTestCase('User_News/Pre_test/create_user_file'), [:], FailureHandling.STOP_ON_FAILURE)

println(GlobalVariable.folderName)

String folderName = GlobalVariable.folderName

WebUI.click(findTestObject('News_User/Page_News - PowerFolder/News'))

WebUI.verifyElementPresent(findTestObject('News_User/Page_News - PowerFolder/all_activities'), 5)

WebUI.click(findTestObject('News_User/Page_News - PowerFolder/all_activities'))

WebUI.verifyElementText(findTestObject('News_User/Page_News - PowerFolder/check_time_activities'), 'Today')

assert isFolderPresent(folderName)

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

WebUI.setEncryptedText(findTestObject('Login/inputEmail'), 'CKkAs2Ee0vA=')

WebUI.setEncryptedText(findTestObject('Login/inputPassword'), 'PpFy9OM6JMUrpEOD1UO9247r7Yrm9E0x')

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.click(findTestObject('News_User/Page_News - PowerFolder/News'))

WebUI.click(findTestObject('News_User/Page_News - PowerFolder/all_activities'))

assert isFolderPresent(folderName)

WebUI.closeBrowser()

boolean isFolderPresent(String folderName) {
    WebDriver driver = DriverFactory.getWebDriver()

    String xpath = ('//a[contains(@class,\'pf-path-link\') and normalize-space(text())=\'' + folderName) + '\']'

    return driver.findElements(By.xpath(xpath)).size() > 0
}

