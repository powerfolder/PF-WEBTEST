import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import org.apache.commons.lang3.RandomStringUtils
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import folder.FolderFinder as FolderFinder
import onlyoffice.OOEditor
import onlyoffice.OnlyOffice
import utils.AccountApi as AccountApi

String inviteeEmail = 'tr3_' + RandomStringUtils.randomAlphanumeric(8).toLowerCase() + '@qa-automated-webtest.com'

String folderName = 'TR3_' + RandomStringUtils.randomAlphanumeric(6)

String fileType = 'docx'

String docName = 'TR3_Doc_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

boolean selfRegistrationWasEnabled = setSelfRegistration(false)

try {
    WebUI.click(findTestObject('LeftNavigationIcons/folders'))

    WebUI.click(findTestObject('Folders/createFolderIcon'))
    WebUI.click(findTestObject('Folders/createFolder'))
    WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)
    WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)
    WebUI.click(findTestObject('Folders/buttonOK'))

    WebUI.click(findTestObject('Links/share_icon_inside_folder'))
    WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/folder_share_permission_dropdown_toggle'))
    WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/folder_share_permission_r_w'))
    WebUI.setText(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), inviteeEmail)
    WebUI.sendKeys(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), Keys.chord(Keys.ENTER))

    TestObject inviteeMember = new TestObject()
    inviteeMember.addProperty('xpath', ConditionType.EQUALS, "//table[@id='share_table']/tbody/tr/td[2][contains(normalize-space(),'" + inviteeEmail + "')]")
    WebUI.verifyElementPresent(inviteeMember, 10)

    WebUI.click(findTestObject('Share/close_button_folder_share_mail'))

    String inviteToken = AccountApi.createTokenForAccount(inviteeEmail)
    String registerUrl = AccountApi.getServerUrl() + '/register?Email=' + URLEncoder.encode(inviteeEmail, 'UTF-8') +
        '&inviteToken=' + URLEncoder.encode(inviteToken, 'UTF-8')

    WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
    WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))
    WebUI.navigateToUrl(registerUrl)

    WebUI.verifyEqual(WebUI.getWindowTitle(), 'Register - PowerFolder')
    WebUI.verifyElementAttributeValue(findTestObject('Registration/Page_Register - PowerFolder/input_Cration_Register_Email'), 'value', inviteeEmail, 5)

    WebUI.setText(findTestObject('Registration/Page_Register - PowerFolder/input_Cration_Register_Password'), GlobalVariable.Pass)
    WebUI.check(findTestObject('Registration/Page_Register - PowerFolder/AcceptTermsAndConditions'))
    WebUI.click(findTestObject('Registration/Page_Register - PowerFolder/RegisterButton'))

    WebUI.delay(3)

    WebUI.verifyElementNotPresent(findTestObject('Registration/Page_Register - PowerFolder/notification_ServerError'), 2)
    WebUI.verifyEqual(WebUI.getWindowTitle(), 'Folders - PowerFolder')

    WebElement invitationRow = findRow(folderName)
    WebUI.verifyEqual(invitationRow.isDisplayed(), true)
    WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(findFolder(folderName)))
    WebUI.verifyElementClickable(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))
    WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))

    WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(findFolder(folderName)))
    String folderId = OnlyOffice.currentFolderId()

    WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
    WebUI.click(findTestObject('ONLY OFFICE/a_create_file_by_type', [('type') : fileType]))
    WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), docName)
    WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

    WebUI.switchToWindowIndex(1)
    OOEditor.waitReady(fileType)
    assert OOEditor.editorMode() in ['USER_EDIT', 'COLLABORATIVE_EDIT']

    WebUI.closeWindowIndex(1)
    WebUI.switchToWindowIndex(0)
    OnlyOffice.waitForFile(folderId, docName + '.' + fileType, 30)
} finally {
    WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)
    setSelfRegistration(selfRegistrationWasEnabled)
    WebUI.closeBrowser()
}

boolean setSelfRegistration(boolean enabled) {
    WebUI.click(findTestObject('LeftNavigationIcons/Preferences'))
    WebUI.click(findTestObject('Preferences/Security/Security_Configuration'))
    WebUI.click(findTestObject('Preferences/Security/Registration'))

    TestObject toggle = findTestObject('Preferences/Security/Enable self-registration')
    boolean wasEnabled = WebUI.getAttribute(toggle, 'checked') == 'true'

    if (wasEnabled != enabled) {
        WebUI.click(toggle)
        WebUI.click(findTestObject('Preferences/Security/Save'))
    } else {
        WebUI.click(findTestObject('Preferences/Security/Cancel'))
    }
    return wasEnabled
}

WebElement findFolder(String name) {
    return FolderFinder.findElement("//td[2]/span/a[contains(text(),'" + name + "')]")
}

WebElement findRow(String name) {
    return FolderFinder.findElement("//*[contains(@data-search-keys, '" + name + "')]/td[1]/span")
}
