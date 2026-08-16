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
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import java.util.Arrays as Arrays
import tags.TagHelper as TagHelper

WebUI.callTestCase(findTestCase('Accounts/Edit_Account/pre_test/Create_Account'), [:], FailureHandling.STOP_ON_FAILURE)
String ownerEmail = GlobalVariable.userEmail

String secondName = generateRandomString(8)
String secondLastName = generateRandomString(8)
String secondEmail = generateRandomEmail().toLowerCase()
String secondPhone = generateRandomPhoneNumber()

WebUI.click(findTestObject('Accounts/CreateButton'))
WebUI.click(findTestObject('Accounts/ClickCreateAccount'))
WebUI.setText(findTestObject('Accounts/InputUserOrEmail'), secondEmail)
WebUI.setText(findTestObject('Accounts/InputPassword'), GlobalVariable.Pass)
WebUI.setText(findTestObject('Accounts/InputFirstName'), secondName)
WebUI.setText(findTestObject('Accounts/InputLastName'), secondLastName)
WebUI.setText(findTestObject('Accounts/InputPhoneNo'), secondPhone)
WebUI.setText(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/account_storage_overwiew'), '5')
WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))
WebUI.setText(findTestObject('Login/inputEmail'), ownerEmail)
WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)
WebUI.click(findTestObject('Login/loginSubmit'))
WebUI.delay(2)
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

String workspaceName = TagHelper.createWorkspace()
String docName = TagHelper.createDocumentInCurrentFolder()

String tagText = 'TAG27_' + RandomStringUtils.randomAlphanumeric(6)
TagHelper.openTagEditorViaIcon(docName)
TagHelper.addTag(tagText)
TagHelper.saveEditorViaEnter()
WebUI.refresh()
WebUI.delay(2)

TagHelper.backToFolderList()
TagHelper.openItem(workspaceName)

WebUI.click(findTestObject('Links/share_icon_inside_folder'))
WebUI.click(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/folder_share_permission_dropdown_toggle'))
WebUI.delay(2)
WebUI.click(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/folder_share_permission_r_w'))
WebUI.setText(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/inputEmail_Share'), secondEmail)
WebUI.sendKeys(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/inputEmail_Share'), Keys.chord(Keys.ENTER))
WebUI.click(findTestObject('Share/close_button_folder_share_mail'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))
WebUI.setText(findTestObject('Login/inputEmail'), secondEmail)
WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)
WebUI.click(findTestObject('Login/loginSubmit'))
WebUI.delay(2)
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

TagHelper.clickItemNameLink(workspaceName)
WebUI.verifyElementClickable(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))

TagHelper.clickItemNameLink(workspaceName)

TagHelper.backToFolderList()
assert TagHelper.searchForTagAndWaitForRow(tagText, docName)

TagHelper.openTagEditorViaIcon(docName)
TagHelper.removeTag(tagText)
TagHelper.saveEditorViaEnter()
WebUI.refresh()
WebUI.delay(2)

TagHelper.searchForTag(tagText)
WebDriver driver = DriverFactory.getWebDriver()
assert driver.findElements(By.xpath("//*[contains(@data-search-keys, '" + docName + "')]")).isEmpty()

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

String generateRandomString(int length) {
    String characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'
    StringBuilder sb = new StringBuilder()
    Random random = new Random()
    for (int i = 0; i < length; i++) {
        sb.append(characters.charAt(random.nextInt(characters.length())))
    }
    return sb.toString().toLowerCase()
}

String generateRandomEmail() {
    return generateRandomString(8) + '@qa-automated-webtest.com'
}

String generateRandomPhoneNumber() {
    Random random = new Random()
    return String.format('(%03d) %03d-%04d', random.nextInt(1000), random.nextInt(1000), random.nextInt(10000))
}

WebUI.closeBrowser()
