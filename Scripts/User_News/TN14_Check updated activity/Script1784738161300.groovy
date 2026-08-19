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

WebUI.verifyElementPresent(findTestObject('News_User/Page_News - PowerFolder/Updated'), 5)

WebUI.click(findTestObject('News_User/Page_News - PowerFolder/Updated'))

WebUI.verifyElementText(findTestObject('News_User/Page_News - PowerFolder/nothing_to_show'), 'Nothing to show')

assert !(isFolderPresent(folderName))

WebUI.click(findTestObject('News_User/Page_News - PowerFolder/all_activities'))

WebUI.click(findTestObject('News_User/Page_News - PowerFolder/file_wpath'))

WebUI.switchToWindowIndex(1)

WebUI.delay(10)

WebUI.verifyElementVisible(findTestObject('ONLY OFFICE/iframe_editor'))

WebUI.switchToFrame(findTestObject('ONLY OFFICE/iframe_editor'), 5)

WebUI.sendKeys(findTestObject('ONLY OFFICE/editor_body'), 'I\'m updating the doc')

// Ctrl + S
WebUI.sendKeys(findTestObject('ONLY OFFICE/editor_body'), Keys.chord(Keys.CONTROL, 's'))

WebUI.delay(5)

String content = WebUI.getText(findTestObject('ONLY OFFICE/editor_body'))

println('✅ READ-WRITE CONFIRMED — text was written')

WebUI.switchToDefaultContent()

WebUI.closeWindowIndex(1)

WebUI.delay(1)

WebUI.switchToWindowIndex(0)

WebUI.delay(10)

WebUI.refresh()

TestObject update = findTestObject('News_User/Page_News - PowerFolder/updated a file')

WebUI.waitForElementVisible(update, 10)

WebUI.verifyElementText(update, 'updated a file')

WebUI.click(findTestObject('News_User/Page_News - PowerFolder/Updated'))

assert isFolderPresent(folderName)

WebUI.closeBrowser()

boolean isFolderPresent(String folderName) {
    WebDriver driver = DriverFactory.getWebDriver()

    String xpath = ('//a[contains(@class,\'pf-path-link\') and normalize-space(text())=\'' + folderName) + '\']'

    return driver.findElements(By.xpath(xpath)).size() > 0
}

