import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
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
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import org.apache.commons.lang3.RandomStringUtils
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import com.kms.katalon.core.webui.driver.DriverFactory

/*
 * Subfolder Sharing spec, section 3.1, table "Nachtraegliche Aenderungen am Elternordner": once a
 * subfolder's inheritance is interrupted, members added to the parent top-level folder AFTERWARDS
 * must NOT gain access to that subfolder - only members explicitly shared on the subfolder itself
 * (or snapshotted at interruption time) may see it.
 */

// create memberA - already on the top-level folder before the subfolder is interrupted
WebUI.callTestCase(findTestCase('Accounts/Edit_Account/pre_test/Create_Account'), [:], FailureHandling.STOP_ON_FAILURE)
String memberAEmail = GlobalVariable.userEmail

// create memberB inline - only added to the top-level folder AFTER the interruption
// (still on the Accounts page after Create_Account, so we can create it right away)
String memberBEmail = ('sfs07b_' + RandomStringUtils.randomNumeric(6)) + '@qa-automated-webtest.com'
WebUI.click(findTestObject('Accounts/CreateButton'))
WebUI.click(findTestObject('Accounts/ClickCreateAccount'))
WebUI.setText(findTestObject('Accounts/InputUserOrEmail'), memberBEmail)
WebUI.setText(findTestObject('Accounts/InputPassword'), GlobalVariable.Pass)
WebUI.setText(findTestObject('Accounts/InputFirstName'), 'SFS07B')
WebUI.setText(findTestObject('Accounts/InputLastName'), RandomStringUtils.randomAlphabetic(6))
WebUI.setText(findTestObject('Accounts/InputPhoneNo'), '(030) 1234567')
WebUI.setText(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/account_storage_overwiew'), '5')
WebUI.click(findTestObject('Accounts/SaveButton'))
WebUI.delay(2)

// navigate to the Folders section
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

// create a top-level folder - creation navigates straight into it
String tlfName = 'SFS07_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))
WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Folders/inputFolderName'), tlfName)
WebUI.click(findTestObject('Folders/buttonOK'))

// share the (currently open) top-level folder with memberA
WebUI.click(findTestObject('Links/share_icon_inside_folder'))
WebUI.setText(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), memberAEmail)
WebUI.sendKeys(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), Keys.chord(Keys.ENTER))
WebUI.click(findTestObject('Share/close_button_folder_share_mail'))

// create a subfolder inside it - creation navigates straight into the new subfolder
String subFolderName = 'SFS07_Sub_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), subFolderName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

// interrupt inheritance on the (currently open) subfolder (snapshot mode) and confirm
WebUI.click(findTestObject('Links/share_icon_inside_folder'))
// use a native JS click - a plain Selenium click on this checkbox does not
// reliably fire the jQuery 'change' handler that drives the confirmation dialog
WebElement inheritanceToggleEl = WebUI.findWebElement(findTestObject('Subfoldersharing/share_inheritance_toggle'), 5)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(inheritanceToggleEl))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/confirme_handover'))
WebUI.verifyElementText(findTestObject('notifications_toastmessage'), 'Own permissions have been set.')
WebUI.click(findTestObject('Share/close_button_folder_share_mail'))

// go back to the top-level folder list, re-enter the top-level folder and add memberB AFTER the interruption
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/lang_Home'))
WebElement tlfRow = findFolder(tlfName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(tlfRow))

WebUI.click(findTestObject('Links/share_icon_inside_folder'))
WebUI.setText(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), memberBEmail)
WebUI.sendKeys(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), Keys.chord(Keys.ENTER))
WebUI.click(findTestObject('Share/close_button_folder_share_mail'))

// log out admin, log in as memberB and accept the top-level folder invitation
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))
WebUI.setText(findTestObject('Login/inputEmail'), memberBEmail)
WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)
WebUI.click(findTestObject('Login/loginSubmit'))
WebUI.delay(3)

// login lands on the Dashboard - navigate to the Folders section
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

WebElement invitationRow = findRow(tlfName)
WebUI.verifyEqual(invitationRow.isDisplayed(), true)
// click the folder name link (not the invitation icon) to open the accept dialog
WebElement invitationLink = findFolder(tlfName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(invitationLink))
WebUI.verifyElementClickable(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))

// memberB sees the top-level folder, but NOT the interrupted subfolder
WebElement memberTlfRow = findFolder(tlfName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(memberTlfRow))

TestObject subFolderPresent = new TestObject()
subFolderPresent.addProperty('xpath', ConditionType.EQUALS, "//a[contains(concat(' ',normalize-space(@class),' '),' pica-name ') and normalize-space(text())='" + subFolderName + "']")
WebUI.verifyElementNotPresent(subFolderPresent, 10)

WebUI.closeBrowser()

WebElement findFolder(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//td[2]/span/a[contains(text(),'" + name + "')]"))
}

WebElement findRow(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//*[contains(@data-search-keys, '" + name + "')]/td[1]/span"))
}
