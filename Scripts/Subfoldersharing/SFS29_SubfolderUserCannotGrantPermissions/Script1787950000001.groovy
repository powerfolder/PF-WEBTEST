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
String memberBEmail = GlobalVariable.userEmail

String memberCEmail = ('sfs29c_' + RandomStringUtils.randomNumeric(6)) + '@qa-automated-webtest.com'
WebUI.click(findTestObject('Accounts/CreateButton'))
WebUI.click(findTestObject('Accounts/ClickCreateAccount'))
WebUI.setText(findTestObject('Accounts/InputUserOrEmail'), memberCEmail)
WebUI.setText(findTestObject('Accounts/InputPassword'), GlobalVariable.Pass)
WebUI.setText(findTestObject('Accounts/InputFirstName'), 'SFS29C')
WebUI.setText(findTestObject('Accounts/InputLastName'), RandomStringUtils.randomAlphabetic(6))
WebUI.setText(findTestObject('Accounts/InputPhoneNo'), '(030) 1234567')
WebUI.setText(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/account_storage_overwiew'), '5')
WebUI.click(findTestObject('Accounts/SaveButton'))
WebUI.delay(2)

WebUI.click(findTestObject('LeftNavigationIcons/folders'))

String tlfName = 'SFS29_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))
WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Folders/inputFolderName'), tlfName)
WebUI.click(findTestObject('Folders/buttonOK'))

String subAName = 'SFS29_SubA_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), subAName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebUI.click(findTestObject('Links/share_icon_inside_folder'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/folder_share_permission_dropdown_toggle'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/folder_share_permission_r_w'))
WebUI.setText(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), memberBEmail)
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/buttonAddEmail'))
WebUI.click(findTestObject('Share/close_button_folder_share_mail'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))
WebUI.setText(findTestObject('Login/inputEmail'), memberBEmail)
WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)
WebUI.click(findTestObject('Login/loginSubmit'))
WebUI.delay(3)
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

WebElement invitationRow = findRow(subAName)
WebUI.verifyEqual(invitationRow.isDisplayed(), true)
WebElement invitationLink = findFolder(subAName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(invitationLink))
WebUI.verifyElementClickable(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))

WebElement subARow = findFolder(subAName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(subARow))

WebUI.verifyElementNotVisible(findTestObject('Links/share_icon_inside_folder'))

WebUI.executeJavaScript("document.getElementById('files_folder_share').click()", null)
WebUI.delay(2)

TestObject shareDialog = new TestObject()
shareDialog.addProperty('xpath', ConditionType.EQUALS, "//div[@id='pica_share_dialog']")
WebUI.verifyElementVisible(shareDialog)

WebUI.verifyElementNotVisible(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'))

WebUI.executeJavaScript(
    "var input = document.querySelector('input.pica-taginput-input');" +
    "input.value = '" + memberCEmail + "';" +
    "input.dispatchEvent(new Event('input', {bubbles: true}));",
    null)
WebUI.delay(1)
WebUI.executeJavaScript("document.querySelector('#share_buttons button.pica-taginput-button').click()", null)
WebUI.delay(3)

TestObject memberCRowAfterForcedInvite = new TestObject()
memberCRowAfterForcedInvite.addProperty('xpath', ConditionType.EQUALS, "//table[@id='share_table']//td[contains(text(),'" + memberCEmail + "')]")
WebUI.verifyElementNotPresent(memberCRowAfterForcedInvite, 10)

def suspiciousConsoleEntries = []
try {
    WebDriver driver = DriverFactory.getWebDriver()
    def logs = driver.manage().logs().get('browser').getAll()
    suspiciousConsoleEntries = logs.findAll { entry ->
        String msg = entry.getMessage().toLowerCase()
        msg.contains('403') || msg.contains('permission denied') || msg.contains('forbidden')
    }
} catch (Exception e) {
}
WebUI.verifyEqual(suspiciousConsoleEntries.isEmpty(), false)

WebUI.closeBrowser()

WebElement findFolder(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//td[2]/span/a[contains(text(),'" + name + "')]"))
}

WebElement findRow(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//*[contains(@data-search-keys, '" + name + "')]/td[1]/span"))
}
