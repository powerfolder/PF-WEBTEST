import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.openqa.selenium.By as By
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint

// Builds its own two-account scenario (via the admin account, same flow as
// Test Cases/Accounts/Edit_Account/pre_test/Create_Account) instead of relying on the
// pre-provisioned GlobalVariable.userName2/userName3 that TSF10 depends on. Account B
// shares a folder with Account A, then Account A scans the pending-invitation dialog
// (#pica_invitation_dialog, templates/picasso/dialogs/invitation.vm) before accepting.
//
// NOTE: this creates two throwaway accounts on every run (no cleanup) - same as most
// other multi-account test cases in this project.
WebUI.openBrowser(GlobalVariable.URL)

WebUI.maximizeWindow()

WebUI.setEncryptedText(findTestObject('Login/inputEmail'), 'CKkAs2Ee0vA=')

WebUI.setEncryptedText(findTestObject('Login/inputPassword'), 'PpFy9OM6JMUrpEOD1UO9247r7Yrm9E0x')

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(2)

WebUI.verifyEqual(WebUI.getWindowTitle(), 'Dashboard - PowerFolder')

WebUI.click(findTestObject('LeftNavigationIcons/account'))

// --- Create Account A (the invitee) ---
String emailA = 'tar55a_' + getRandomSuffix() + '@qa-automated-webtest.com'

WebUI.click(findTestObject('Accounts/CreateButton'))

WebUI.click(findTestObject('Accounts/ClickCreateAccount'))

WebUI.setText(findTestObject('Accounts/InputUserOrEmail'), emailA)

WebUI.setText(findTestObject('Accounts/InputPassword'), GlobalVariable.Pass)

WebUI.setText(findTestObject('Accounts/InputFirstName'), 'TAR55A')

WebUI.setText(findTestObject('Accounts/InputLastName'), 'Invitee')

WebUI.setText(findTestObject('Accounts/InputPhoneNo'), '(555) 000-0001')

WebUI.setText(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/account_storage_overwiew'), '5')

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.refresh()

WebUI.delay(2)

// --- Create Account B (shares the folder) ---
String emailB = 'tar55b_' + getRandomSuffix() + '@qa-automated-webtest.com'

WebUI.click(findTestObject('Accounts/CreateButton'))

WebUI.click(findTestObject('Accounts/ClickCreateAccount'))

WebUI.setText(findTestObject('Accounts/InputUserOrEmail'), emailB)

WebUI.setText(findTestObject('Accounts/InputPassword'), GlobalVariable.Pass)

WebUI.setText(findTestObject('Accounts/InputFirstName'), 'TAR55B')

WebUI.setText(findTestObject('Accounts/InputLastName'), 'Sharer')

WebUI.setText(findTestObject('Accounts/InputPhoneNo'), '(555) 000-0002')

WebUI.setText(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/account_storage_overwiew'), '5')

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.refresh()

WebUI.delay(2)

// --- Log out of admin, log in as Account B ---
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

WebUI.delay(2)

WebUI.setText(findTestObject('Login/inputEmail'), emailB)

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(2)

WebUI.verifyEqual(WebUI.getWindowTitle(), 'Folders - PowerFolder')

// --- Account B creates a folder and shares it with Account A ---
String folderName = 'TAR55_' + getRandomSuffix()

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createFolder'))

WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)

WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.click(findTestObject('Object Repository/Folders/Page_Folders - PowerFolder/lang_Folders'))

WebElement shareBtn = findShareButton(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(shareBtn))

WebUI.setText(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/inputEmail_Share'), emailA)

WebUI.sendKeys(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/inputEmail_Share'), Keys.chord(Keys.ENTER))

WebUI.delay(2)

WebUI.click(findTestObject('Share/close_button_folder_share_mail'))

// --- Log out of Account B, log in as Account A ---
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

WebUI.delay(2)

WebUI.setText(findTestObject('Login/inputEmail'), emailA)

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(2)

WebUI.verifyEqual(WebUI.getWindowTitle(), 'Folders - PowerFolder')

WebElement folderRow = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(folderRow))

WebUI.verifyElementClickable(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))

WebUI.delay(3)

// Scans the pending-invitation dialog while it is open, before accepting.
CustomKeywords.'accessibility.AccessibilityKeywords.checkAccessibility'()

WebUI.closeBrowser()

String getRandomSuffix() {
	Date todaysDate = new Date()

	return todaysDate.format('ddMMMyyyyHHmmss')
}

WebElement findShareButton(String name) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + name) + '\')]/td[7]/a/span'))
}

WebElement findFolder(String name) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(By.xpath(('//td[2]/span/a[contains(text(),\'' + name) + '\')]'))
}
