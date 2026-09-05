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
String memberEmail = GlobalVariable.userEmail

WebUI.click(findTestObject('LeftNavigationIcons/folders'))

String tlfName = 'SFS30_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))
WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Folders/inputFolderName'), tlfName)
WebUI.click(findTestObject('Folders/buttonOK'))

String subAName = 'SFS30_SubA_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), subAName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

String docAName = 'SFS30_DocA_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_Document'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), docAName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebUI.switchToWindowIndex(1)
WebUI.verifyElementNotPresent(findTestObject('file_objects/document/Page_Open - PowerFolder/span_Unable to create document'), 5)
WebUI.delay(15)
WebUI.switchToWindowIndex(0)
WebUI.refresh()

WebUI.click(findTestObject('Links/share_icon_inside_folder'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/folder_share_permission_dropdown_toggle'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/folder_share_permission_r_o'))
WebUI.setText(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), memberEmail)
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/buttonAddEmail'))
WebUI.click(findTestObject('Share/close_button_folder_share_mail'))

String innerName = 'SFS30_Inner_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), innerName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

String docInnerName = 'SFS30_DocInner_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_Document'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), docInnerName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebUI.switchToWindowIndex(1)
WebUI.verifyElementNotPresent(findTestObject('file_objects/document/Page_Open - PowerFolder/span_Unable to create document'), 5)
WebUI.delay(15)
WebUI.switchToWindowIndex(0)
WebUI.refresh()

WebUI.click(findTestObject('Links/share_icon_inside_folder'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/folder_share_permission_dropdown_toggle'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/folder_share_permission_r_w'))
WebUI.setText(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), memberEmail)
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/buttonAddEmail'))
WebUI.click(findTestObject('Share/close_button_folder_share_mail'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))
WebUI.setText(findTestObject('Login/inputEmail'), memberEmail)
WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)
WebUI.click(findTestObject('Login/loginSubmit'))
WebUI.delay(3)
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

WebElement invitationRowA = findRow(subAName)
WebUI.verifyEqual(invitationRowA.isDisplayed(), true)
WebElement invitationLinkA = findFolder(subAName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(invitationLinkA))
WebUI.verifyElementClickable(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))

WebElement invitationRowInner = findRow(innerName)
WebUI.verifyEqual(invitationRowInner.isDisplayed(), true)
WebElement invitationLinkInner = findFolder(innerName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(invitationLinkInner))
WebUI.verifyElementClickable(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))

WebElement subARow = findFolder(subAName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(subARow))

TestObject docAPresent = new TestObject()
docAPresent.addProperty('xpath', ConditionType.EQUALS, "//*[contains(@data-search-keys, '" + docAName + "')]/td[1]/span")
WebUI.verifyElementPresent(docAPresent, 10)
WebUI.verifyElementNotVisible(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

WebElement docARowToggle = WebUI.findWebElement(findTestObject('file_objects/document/Page_Open - PowerFolder/file_manage'), 5)
WebUI.executeJavaScript("arguments[0].closest('tr').querySelector('a.dropdown-toggle').click()", Arrays.asList(docARowToggle))
WebUI.delay(1)

TestObject docARenameOption = new TestObject()
docARenameOption.addProperty('xpath', ConditionType.EQUALS, "//table[@id='files_files_table']//ul[contains(@class,'conext-dropdown-menu')]//a[contains(concat(' ',normalize-space(@class),' '),' files-ui-rename ')]")
WebUI.verifyElementNotPresent(docARenameOption, 5)

TestObject docACutOption = new TestObject()
docACutOption.addProperty('xpath', ConditionType.EQUALS, "//table[@id='files_files_table']//ul[contains(@class,'conext-dropdown-menu')]//a[contains(concat(' ',normalize-space(@class),' '),' files-ui-cut ')]")
WebUI.verifyElementNotPresent(docACutOption, 5)

TestObject docADeleteOption = new TestObject()
docADeleteOption.addProperty('xpath', ConditionType.EQUALS, "//table[@id='files_files_table']//ul[contains(@class,'conext-dropdown-menu')]//a[contains(concat(' ',normalize-space(@class),' '),' files-ui-delete ')]")
WebUI.verifyElementNotPresent(docADeleteOption, 5)

TestObject docADownloadOption = new TestObject()
docADownloadOption.addProperty('xpath', ConditionType.EQUALS, "//table[@id='files_files_table']//ul[contains(@class,'conext-dropdown-menu')]//a[contains(concat(' ',normalize-space(@class),' '),' files-ui-download ')]")
WebUI.verifyElementPresent(docADownloadOption, 5)

String forcedUploadName = 'sfs30_denied_' + RandomStringUtils.randomAlphanumeric(6) + '.txt'
File forcedLocalFile = File.createTempFile('sfs30_', '.txt')
forcedLocalFile.text = 'SFS30 SubA read-only upload denial check'
File forcedRenamedFile = new File(forcedLocalFile.getParentFile(), forcedUploadName)
forcedLocalFile.renameTo(forcedRenamedFile)

TestObject forcedUploadInput = new TestObject('forcedUploadInput')
forcedUploadInput.addProperty('xpath', ConditionType.EQUALS, "//input[@id='upload_input_files']")
WebUI.uploadFile(forcedUploadInput, forcedRenamedFile.getAbsolutePath())
WebUI.delay(5)
forcedRenamedFile.delete()

TestObject forcedUploadPresent = new TestObject()
forcedUploadPresent.addProperty('xpath', ConditionType.EQUALS, "//*[contains(@data-search-keys, '" + forcedUploadName + "')]/td[1]/span")
WebUI.verifyElementNotPresent(forcedUploadPresent, 10)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/lang_Home'))
WebElement innerRow = findFolder(innerName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(innerRow))

TestObject docInnerPresent = new TestObject()
docInnerPresent.addProperty('xpath', ConditionType.EQUALS, "//*[contains(@data-search-keys, '" + docInnerName + "')]/td[1]/span")
WebUI.verifyElementPresent(docInnerPresent, 10)
WebUI.verifyElementVisible(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

String innerSubName = 'SFS30_InnerSub_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), innerSubName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebUI.verifyElementNotPresent(findTestObject('file_objects/document/Page_Open - PowerFolder/span_Unable to create document'), 5)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/lang_Home'))
WebElement subARowAgain = findFolder(subAName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(subARowAgain))

WebUI.verifyElementNotVisible(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

WebUI.closeBrowser()

WebElement findFolder(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//td[2]/span/a[contains(text(),'" + name + "')]"))
}

WebElement findRow(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//*[contains(@data-search-keys, '" + name + "')]/td[1]/span"))
}
