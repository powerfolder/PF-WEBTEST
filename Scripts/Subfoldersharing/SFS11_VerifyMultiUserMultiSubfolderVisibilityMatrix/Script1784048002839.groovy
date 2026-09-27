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


WebUI.callTestCase(findTestCase('Accounts/Edit_Account/pre_test/Create_Account'), [:], FailureHandling.STOP_ON_FAILURE)
String user1Email = GlobalVariable.userEmail

String user2Email = ('sfs11u2_' + RandomStringUtils.randomNumeric(6)) + '@qa-automated-webtest.com'
WebUI.click(findTestObject('Accounts/CreateButton'))
WebUI.click(findTestObject('Accounts/ClickCreateAccount'))
WebUI.setText(findTestObject('Accounts/InputUserOrEmail'), user2Email)
WebUI.setText(findTestObject('Accounts/InputPassword'), GlobalVariable.Pass)
WebUI.setText(findTestObject('Accounts/InputFirstName'), 'SFS11U2')
WebUI.setText(findTestObject('Accounts/InputLastName'), RandomStringUtils.randomAlphabetic(6))
WebUI.setText(findTestObject('Accounts/InputPhoneNo'), '(030) 1234567')
WebUI.setText(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/account_storage_overwiew'), '5')
WebUI.click(findTestObject('Accounts/SaveButton'))
WebUI.delay(2)

String user3Email = ('sfs11u3_' + RandomStringUtils.randomNumeric(6)) + '@qa-automated-webtest.com'
WebUI.click(findTestObject('Accounts/CreateButton'))
WebUI.click(findTestObject('Accounts/ClickCreateAccount'))
WebUI.setText(findTestObject('Accounts/InputUserOrEmail'), user3Email)
WebUI.setText(findTestObject('Accounts/InputPassword'), GlobalVariable.Pass)
WebUI.setText(findTestObject('Accounts/InputFirstName'), 'SFS11U3')
WebUI.setText(findTestObject('Accounts/InputLastName'), RandomStringUtils.randomAlphabetic(6))
WebUI.setText(findTestObject('Accounts/InputPhoneNo'), '(030) 1234567')
WebUI.setText(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/account_storage_overwiew'), '5')
WebUI.click(findTestObject('Accounts/SaveButton'))
WebUI.delay(2)

WebUI.click(findTestObject('LeftNavigationIcons/folders'))

String tlfName = 'SFS11_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))
WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Folders/inputFolderName'), tlfName)
WebUI.click(findTestObject('Folders/buttonOK'))

String subAName = 'SFS11_SubA_' + RandomStringUtils.randomAlphanumeric(6)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), subAName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebUI.click(findTestObject('Links/share_icon_inside_folder'))
WebUI.setText(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), user2Email)
WebUI.sendKeys(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), Keys.chord(Keys.ENTER))
WebUI.click(findTestObject('Share/close_button_folder_share_mail'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/lang_Home'))
WebElement tlfRow = findFolder(tlfName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(tlfRow))

String subBName = 'SFS11_SubB_' + RandomStringUtils.randomAlphanumeric(6)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), subBName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebUI.click(findTestObject('Links/share_icon_inside_folder'))
WebUI.delay(2)
WebElement inheritanceToggleEl = WebUI.findWebElement(findTestObject('Subfoldersharing/share_inheritance_toggle'), 5)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(inheritanceToggleEl))
WebUI.waitForElementVisible(findTestObject('Subfoldersharing/inheritance_dialog_title'), 5)
WebUI.click(findTestObject('Subfoldersharing/inheritance_dialog_ok'))
WebUI.verifyElementText(findTestObject('notifications_toastmessage'), '"' + subBName + '" now has its own access rights.')

TestObject shareTableLoading = new TestObject()
shareTableLoading.addProperty('xpath', ConditionType.EQUALS, "//table[@id='share_table']//tr[contains(@class,'pica-table-loading')]")
WebUI.verifyElementNotPresent(shareTableLoading, 10)

WebUI.setText(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), user3Email)
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/buttonAddEmail'))
WebUI.click(findTestObject('Share/close_button_folder_share_mail'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/lang_Home'))
WebElement tlfRow2 = findFolder(tlfName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(tlfRow2))

WebUI.click(findTestObject('Links/share_icon_inside_folder'))
WebUI.setText(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), user1Email)
WebUI.sendKeys(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), Keys.chord(Keys.ENTER))
WebUI.click(findTestObject('Share/close_button_folder_share_mail'))

logoutAndLoginAs(user1Email)
acceptInvitation(tlfName)
WebElement user1TlfRow = findFolder(tlfName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(user1TlfRow))

TestObject subAPresent = new TestObject()
subAPresent.addProperty('xpath', ConditionType.EQUALS, "//a[contains(concat(' ',normalize-space(@class),' '),' pica-name ') and normalize-space(text())='" + subAName + "']")
WebUI.verifyElementPresent(subAPresent, 10)

TestObject subBPresent = new TestObject()
subBPresent.addProperty('xpath', ConditionType.EQUALS, "//a[contains(concat(' ',normalize-space(@class),' '),' pica-name ') and normalize-space(text())='" + subBName + "']")
WebUI.verifyElementNotPresent(subBPresent, 10)

TestObject tlfPresent = new TestObject()
tlfPresent.addProperty('xpath', ConditionType.EQUALS, "//a[contains(concat(' ',normalize-space(@class),' '),' pica-name ') and normalize-space(text())='" + tlfName + "']")

logoutAndLoginAs(user2Email)
acceptInvitation(subAName)
WebUI.verifyElementNotPresent(tlfPresent, 10)
WebUI.verifyElementNotPresent(subBPresent, 10)
WebElement user2SubARow = findFolder(subAName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(user2SubARow))
WebUI.verifyElementPresent(findTestObject('Folders/createFolderIcon'), 10)

logoutAndLoginAs(user3Email)
acceptInvitation(subBName)
WebUI.verifyElementNotPresent(tlfPresent, 10)
WebUI.verifyElementNotPresent(subAPresent, 10)
WebElement user3SubBRow = findFolder(subBName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(user3SubBRow))
WebUI.verifyElementPresent(findTestObject('Folders/createFolderIcon'), 10)

WebUI.closeBrowser()

void logoutAndLoginAs(String email) {
    WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
    WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))
    WebUI.setText(findTestObject('Login/inputEmail'), email)
    WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)
    WebUI.click(findTestObject('Login/loginSubmit'))
    WebUI.delay(3)
    WebUI.click(findTestObject('LeftNavigationIcons/folders'))
}

void acceptInvitation(String name) {
    WebElement invitationRow = findRow(name)
    WebUI.verifyEqual(invitationRow.isDisplayed(), true)
    WebElement invitationLink = findFolder(name)
    WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(invitationLink))
    WebUI.verifyElementClickable(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))
    WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))
}

WebElement findFolder(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//td[2]/span/a[contains(text(),'" + name + "')]"))
}

WebElement findRow(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//*[contains(@data-search-keys, '" + name + "')]/td[1]/span"))
}
