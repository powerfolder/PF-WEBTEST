import com.kms.katalon.core.annotation.Keyword
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
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

// create first account
WebUI.callTestCase(findTestCase('Accounts/Edit_Account/pre_test/Create_Account'), [:], FailureHandling.STOP_ON_FAILURE)

// create second account
secondaccount_userName = generateRandomString(8)

secondaccount_userLastName = generateRandomString(8)

secondaccount_userEmail = generateRandomEmail().toLowerCase()

secondaccount_PhoneNumber = generateRandomPhoneNumber()

WebUI.click(findTestObject('Accounts/CreateButton'))

WebUI.click(findTestObject('Accounts/ClickCreateAccount'))

WebUI.setText(findTestObject('Accounts/InputUserOrEmail'),secondaccount_userEmail)

WebUI.setText(findTestObject('Accounts/InputPassword'), GlobalVariable.Pass)

WebUI.setText(findTestObject('Accounts/InputFirstName'), secondaccount_userName)

WebUI.setText(findTestObject('Accounts/InputLastName'), secondaccount_userLastName)

WebUI.setText(findTestObject('Accounts/InputPhoneNo'), secondaccount_PhoneNumber)

WebUI.setText(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/account_storage_overwiew'), '5')

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

// login as first user

WebUI.setText(findTestObject('Login/inputEmail'), GlobalVariable.userEmail)

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

// create folder

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createFolder'))

String folderName = getRandomFolderName()

WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)

WebUI.click(findTestObject('Folders/buttonOK'))

// share folder to second account

WebUI.click(findTestObject('Links/share_icon_inside_folder'))

WebUI.click(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/folder_share_permission_dropdown_toggle'))

WebUI.delay(3)

WebUI.click(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/folder_share_permission_r_w'))

WebUI.setText(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/inputEmail_Share'), secondaccount_userEmail)

WebUI.sendKeys(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/inputEmail_Share'), Keys.chord(Keys.ENTER))

WebUI.click(findTestObject('Share/close_button_folder_share_mail'))

// logout

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

// login as second user

WebUI.setText(findTestObject('Login/inputEmail'), secondaccount_userEmail)

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

// check if invitation is present

WebDriver driver = DriverFactory.getWebDriver()

WebElement folder_invitation = driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + folderName) + '\')]/td[1]/span'))

boolean isfolderinvitation = folder_invitation.isDisplayed()

WebUI.verifyEqual(isfolderinvitation, true)

// decline invitation

WebElement btn4 = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn4))

WebUI.verifyElementClickable(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))

WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/decline_invitation'))

WebUI.verifyElementPresent(findTestObject('Share/Page_Folders - PowerFolder/message_decline_invitation'), 10)

// Wait for the invitation modal to actually close (BS5 .show class removed) — the decline XHR fires async via the click handler;
// without this wait, the subsequent logout cancels the in-flight request and the share stays accepted server-side.
new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15)).until(
    org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated(
        By.xpath("//div[@id='pica_invitation_dialog' and contains(concat(' ',normalize-space(@class),' '),' show ')]")))

WebUI.delay(2)

// check if folder is not shown

boolean nofoldershown = isFolderPresent(folderName)

WebUI.verifyEqual(nofoldershown, false)

// logout

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

// login as first user

WebUI.setText(findTestObject('Login/inputEmail'), GlobalVariable.userEmail)

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

// open folder

WebElement btn6 = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn6))

// open sharing menu and check members list

WebUI.click(findTestObject('Links/share_icon_inside_folder'))

WebUI.delay(2)

// Owner is always listed in the share dialog — verify the declined second user is NOT in the members list
String secondUserLocalPart = secondaccount_userEmail.contains('@') ? secondaccount_userEmail.substring(0, secondaccount_userEmail.indexOf('@')) : secondaccount_userEmail

TestObject declinedUserRow = new TestObject('declinedUserRow')
declinedUserRow.addProperty('xpath', com.kms.katalon.core.testobject.ConditionType.EQUALS,
    "//table[@id='share_table']//tr[contains(@data-search-keys,'" + secondUserLocalPart + "') or .//a[contains(@title,'" + secondUserLocalPart + "') or contains(text(),'" + secondUserLocalPart + "')]]")

WebUI.verifyElementNotPresent(declinedUserRow, 5)

// close browser

WebUI.closeBrowser()

String generateRandomString(int length) {
	String characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'

	StringBuilder randomString = new StringBuilder()

	Random random = new Random()

	for (int i = 0; i < length; i++) {
		randomString.append(characters.charAt(random.nextInt(characters.length())))
	}
	
	return randomString.toString().toLowerCase()
}

String generateRandomEmail() {
	return generateRandomString(8) + '@qa-automated-webtest.com'
}

String generateRandomPhoneNumber() {
	Random random = new Random()

	return String.format('(%03d) %03d-%04d', random.nextInt(1000), random.nextInt(1000), random.nextInt(10000))
}

String getRandomFolderName() {
	String folderName = 'TA06' + getTimestamp()

	return folderName
}

String getTimestamp() {
	Date todaysDate = new Date()

	String formattedDate = todaysDate.format('ddMMMyyyyhhmmss')

	return formattedDate
}

WebElement findFolder(String folderName) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(By.xpath(('//td[2]/span/a[contains(text(),\'' + folderName) + '\')]'))
}

boolean isFolderPresent(String folderName) {
	return !DriverFactory.getWebDriver()
		.findElements(By.xpath("//td[2]/span/a[contains(text(),'${folderName}')]"))
		.isEmpty()
}