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
import tags.TagHelper as TagHelper

WebUI.callTestCase(findTestCase('Accounts/Edit_Account/pre_test/Create_Account'), [:], FailureHandling.STOP_ON_FAILURE)
String memberEmail = GlobalVariable.userEmail

WebUI.click(findTestObject('LeftNavigationIcons/folders'))

String tlfName = 'SFS37_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))
WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Folders/inputFolderName'), tlfName)
WebUI.click(findTestObject('Folders/buttonOK'))

String outsideFileName = 'SFS37_outside_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_Document'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), outsideFileName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebUI.switchToWindowIndex(1)
WebUI.verifyElementNotPresent(findTestObject('file_objects/document/Page_Open - PowerFolder/span_Unable to create document'), 5)
WebUI.delay(15)
WebUI.closeWindowIndex(1)
WebUI.switchToWindowIndex(0)
WebUI.refresh()
WebUI.delay(3)

String subAName = 'SFS37_SubA_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), subAName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

String subAFileName = 'SFS37_inside_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_Document'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), subAFileName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebUI.switchToWindowIndex(1)
WebUI.verifyElementNotPresent(findTestObject('file_objects/document/Page_Open - PowerFolder/span_Unable to create document'), 5)
WebUI.delay(15)
WebUI.closeWindowIndex(1)
WebUI.switchToWindowIndex(0)
WebUI.refresh()
WebUI.delay(3)

WebUI.click(findTestObject('Links/share_icon_inside_folder'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/folder_share_permission_dropdown_toggle'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/folder_share_permission_r_w'))
WebUI.setText(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), memberEmail)
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/buttonAddEmail'))
WebUI.click(findTestObject('Share/close_button_folder_share_mail'))

// capture the top folder's own URL via a genuine row click (creation auto-navigate does not push
// a URL - only clicking an existing row does), so its base64-encoded folder id can be extracted
// for the forced request below
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/lang_Home'))
WebUI.delay(2)
WebElement tlfRowForUrl = findFolder(tlfName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(tlfRowForUrl))
WebUI.delay(1)
String tlfUrl = WebUI.getUrl()
String tlfFolderIdEnc = tlfUrl.substring(tlfUrl.lastIndexOf('/') + 1)

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))
WebUI.setText(findTestObject('Login/inputEmail'), memberEmail)
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

TagHelper.clickItemNameLink(subAName)

// positive control: tagging still works inside the shared subfolder
String tagText = 'sfs37' + RandomStringUtils.randomAlphanumeric(6).toLowerCase()

TagHelper.openTagEditorViaRowMenu(subAFileName)
TagHelper.addTag(tagText)
TagHelper.saveEditorViaEnter()
WebUI.delay(2)
WebUI.refresh()
WebUI.delay(2)

List<String> chips = TagHelper.getChipTexts(subAFileName)
WebUI.verifyEqual(chips.contains(tagText), true)

// negative test: force the exact setTags request PF-PRO's own client sends
// (POST /wapi/folders/<folderIdEnc>/<relativePath>?action=setTags&tags=<json>) directly against
// the file that lives in the top folder, outside SubA - there is no UI path to reach it at all
// (the top folder is completely invisible to this account), so the app's own tag-editor UI
// cannot even be opened on it; this proves the server itself denies the write, not just that the
// UI declines to offer it
def forcedStatus = WebUI.executeJavaScript(
    "var xhr = new XMLHttpRequest();" +
    "xhr.open('POST', '/wapi/folders/' + arguments[0] + '/' + encodeURIComponent(arguments[1]) + " +
    "'?action=setTags&tags=' + encodeURIComponent(JSON.stringify(['sfs37forced'])), false);" +
    "xhr.send();" +
    "return xhr.status;",
    Arrays.asList(tlfFolderIdEnc, outsideFileName))

WebUI.verifyEqual(((Number) forcedStatus).intValue(), 403)

WebUI.closeBrowser()

WebElement findFolder(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//td[2]/span/a[contains(text(),'" + name + "')]"))
}

WebElement findRow(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//*[contains(@data-search-keys, '" + name + "')]/td[1]/span"))
}
