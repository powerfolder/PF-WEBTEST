import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberBuiltinKeywords
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGBuiltinKeywords
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as WindowsBuiltinKeywords
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import java.util.Arrays as Arrays
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import file.FileFinder as FileFinder
import helpers.Helper as Helper
import com.kms.katalon.core.testobject.ConditionType as ConditionType

WebUiBuiltInKeywords.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

userName_A = (('user_A_' + RandomStringUtils.randomNumeric(4)) + '@qa-automated-webtest.com')

userName_B = (('user_B_' + RandomStringUtils.randomNumeric(4)) + '@qa-automated-webtest.com')

createAccount(userName_A)

createAccount(userName_B)

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

// choose invitations

WebUI.setText(findTestObject('Login/inputEmail'), userName_A)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(3)

String Folder_1 = 'Folder_1' + Helper.getRandomFolderName()

createFolder(Folder_1)

WebUI.executeJavaScript("try { sessionStorage.removeItem('searchQuery'); } catch (e) {}", null)

WebUI.refresh()

new org.openqa.selenium.support.ui.WebDriverWait(DriverFactory.getWebDriver(), java.time.Duration.ofSeconds(15)).until(
    org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(
        By.xpath("//*[contains(@data-search-keys, '" + GlobalVariable.folderName + "')]/td[7]/a/span")))

WebElement btn = findShareButton(GlobalVariable.folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.setText(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/inputEmail_Share'), userName_B)

WebUI.sendKeys(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/inputEmail_Share'), Keys.chord(Keys.ENTER))

WebUI.click(findTestObject('Share/close_button_folder_share_mail'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

WebUI.setText(findTestObject('Login/inputEmail'), userName_B)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(3)

WebUI.click(findTestObject('file_objects/folder view/Drop_down_all_folders'))

TestObject Invitations = new TestObject('Invitations')

Invitations.addProperty('xpath', ConditionType.EQUALS, '//a[normalize-space()=\'Invitations\']')

WebUI.waitForElementClickable(Invitations, 10)

WebUI.click(Invitations)

WebUI.delay(2)

String xpath = "//*[@id='files_files_table']//tbody//tr[contains(@data-search-keys, '${Folder_1}')]"

assert findElementsInXPath(xpath).size() > 0 : "The Folder '${Folder_1}' is not found."

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

WebUI.setText(findTestObject('Login/inputEmail'), userName_A)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(3)

WebUI.click(findTestObject('file_objects/folder view/Drop_down_all_folders'))

TestObject myShared = new TestObject('Invitations')

myShared.addProperty('xpath', ConditionType.EQUALS, '//a[normalize-space()=\'My Shared\']')

WebUI.waitForElementClickable(myShared, 10)

WebUI.click(myShared)

WebUI.delay(2)

xpath = "//*[@id='files_files_table']//tbody//tr[contains(@data-search-keys, '${Folder_1}')]"

assert findElementsInXPath(xpath).size() > 0 : "The Folder '${Folder_1}' is not found."

WebUI.closeBrowser()


List<WebElement> findElementsInXPath(String xpath) {
	WebDriver driver = DriverFactory.getWebDriver()
	return driver.findElements(By.xpath(xpath))
}

def createAccount(String email) {
	WebUI.click(findTestObject('LeftNavigationIcons/account'))

	WebUI.click(findTestObject('Accounts/CreateButton'))

	WebUI.click(findTestObject('Accounts/ClickCreateAccount'))

	WebUI.setText(findTestObject('Accounts/InputUserOrEmail'), email)

	WebUI.setText(findTestObject('Accounts/InputPassword'), GlobalVariable.Pass)

	WebUI.setText(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/account_storage_overwiew'), '5')

	WebUI.click(findTestObject('Accounts/SaveButton'))

	WebUI.delay(2)
}


def createFolder(String Folder) {
	
	WebUI.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

	WebUI.click(findTestObject('Folders/createFolderIcon'))

	WebUI.click(findTestObject('Folders/createFolder'))

	WebUI.verifyEqual(WebUI.getText(findTestObject('lang/getCreateText')), 'Create', FailureHandling.CONTINUE_ON_FAILURE)

	WebUI.verifyEqual(WebUI.getText(findTestObject('lang/getFolderNameLabelText')), 'Create a new Folder', FailureHandling.CONTINUE_ON_FAILURE)

	WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)

	WebUI.setText(findTestObject('Folders/inputFolderName'), Folder)

	WebUI.click(findTestObject('Folders/buttonOK'))

	WebUI.delay(2)

	WebUI.back()
	
	WebUI.delay(2)
	
}

WebElement findShareButton(String fileName) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + fileName) + '\')]/td[7]/a/span'))
}

