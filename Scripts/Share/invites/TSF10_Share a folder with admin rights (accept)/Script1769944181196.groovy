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
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.DataFlavor as DataFlavor

WebUI.callTestCase(findTestCase('Share/Pre_test/add members'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

WebUI.setText(findTestObject('Login/inputEmail'), GlobalVariable.userName)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(3)

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createFolder'))

GlobalVariable.folderName = ('TF10_' + RandomStringUtils.randomAlphanumeric(6))

WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.setText(findTestObject('Folders/inputFolderName'), GlobalVariable.folderName)

WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/lang_Home'))

WebElement btn = findShareButton(GlobalVariable.folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.click(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/folder_share_permission_dropdown_toggle'))

WebUI.delay(2)

WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/folder_share_can_administrate'))

WebUI.setText(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/inputEmail_Share'), GlobalVariable.userName2)

WebUI.sendKeys(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/inputEmail_Share'), Keys.chord(Keys.ENTER))

WebUI.click(findTestObject('Share/close_button_folder_share_mail'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

WebUI.setText(findTestObject('Login/inputEmail'), GlobalVariable.userName2)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(3)

WebDriver driver = DriverFactory.getWebDriver()

WebElement folder_invitation = driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + GlobalVariable.folderName) + 
        '\')]/td[1]/span'))

boolean isfolderinvitation = folder_invitation.isDisplayed()

WebUI.verifyEqual(isfolderinvitation, true)

WebElement btn1 = findFolder(GlobalVariable.folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

WebUI.verifyElementClickable(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))

WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))

assert findShareButton(GlobalVariable.folderName) : '❌ Share button not present'

WebElement btn2 = findShareButton(GlobalVariable.folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn2))

WebUI.setText(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/inputEmail_Share'), GlobalVariable.userName3)

WebUI.sendKeys(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/inputEmail_Share'), Keys.chord(Keys.ENTER))

WebUI.click(findTestObject('Share/close_button_folder_share_mail')) //////////////////////////////////////////

WebUI.closeBrowser()

WebElement findShareButton(String fileName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + fileName) + '\')]/td[7]/a/span'))
}

WebElement findFolder(String folderName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//td[2]/span/a[contains(text(),\'' + folderName) + '\')]'))
}

