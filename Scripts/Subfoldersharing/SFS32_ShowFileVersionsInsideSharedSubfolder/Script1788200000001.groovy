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
import org.apache.poi.xwpf.usermodel.XWPFDocument

WebUI.callTestCase(findTestCase('Accounts/Edit_Account/pre_test/Create_Account'), [:], FailureHandling.STOP_ON_FAILURE)
String memberEmail = GlobalVariable.userEmail

WebUI.click(findTestObject('LeftNavigationIcons/folders'))

String tlfName = 'SFS32_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))
WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Folders/inputFolderName'), tlfName)
WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/lang_Home'))
WebUI.delay(2)

WebElement tlfRowForManage = findRow(tlfName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(tlfRowForManage))
WebUI.click(findTestObject('ManagePopup/button_Manage'))

TestObject versionsDropdownToggle = new TestObject()
versionsDropdownToggle.addProperty('xpath', ConditionType.EQUALS, "//div[@id='pica_settings_versions_group']//button[contains(@class,'dropdown-toggle')]")
WebUI.click(versionsDropdownToggle)

TestObject versionsOptionFive = new TestObject()
versionsOptionFive.addProperty('xpath', ConditionType.EQUALS, "//div[@id='pica_settings_versions_group']//ul[contains(@class,'dropdown-menu')]/li[3]/a")
WebUI.click(versionsOptionFive)

WebUI.click(findTestObject('Folders/version/Save buton'))

WebElement tlfRow = findFolder(tlfName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(tlfRow))

String subAName = 'SFS32_SubA_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), subAName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

String docName = 'SFS32_Doc_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_Document'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), docName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebUI.switchToWindowIndex(1)
WebUI.verifyElementNotPresent(findTestObject('file_objects/document/Page_Open - PowerFolder/span_Unable to create document'), 5)
WebUI.delay(15)
WebUI.closeWindowIndex(1)
WebUI.switchToWindowIndex(0)
WebUI.refresh()
WebUI.delay(3)

String v1Marker = 'SFS32_V1_' + RandomStringUtils.randomAlphanumeric(8)
String v2Marker = 'SFS32_V2_' + RandomStringUtils.randomAlphanumeric(8)

editDocument(docName, v1Marker)
editDocument(docName, v2Marker)

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

WebElement invitationRow = findRow(subAName)
WebUI.verifyEqual(invitationRow.isDisplayed(), true)
WebElement invitationLink = findFolder(subAName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(invitationLink))
WebUI.verifyElementClickable(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))

WebElement subARow = findFolder(subAName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(subARow))

WebElement docRowToSelect = findRow(docName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(docRowToSelect))
WebUI.verifyElementVisible(findTestObject('Recycle bin/Page_Folders - PowerFolder/a_Restore'))
WebUI.click(findTestObject('Recycle bin/Page_Folders - PowerFolder/a_Restore'))
WebUI.waitForElementVisible(findTestObject('Recycle bin/Page_Folders - PowerFolder/restore_popup'), 10)

List<WebElement> restoreLinks = DriverFactory.getWebDriver().findElements(By.xpath("//div[@id='pica_restore_versions']//tbody/tr//a[.//span[contains(concat(' ',normalize-space(@class),' '),' glyphicons-restart ')]]"))
WebUI.verifyEqual(restoreLinks.size() >= 2, true)

restoreLinks.get(0).click()
WebUI.delay(5)
WebUI.click(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/lang_Close'))
WebUI.delay(2)

WebElement docRowForDownload = findDoc(docName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(docRowForDownload))
WebUI.verifyElementClickable(findTestObject('file_objects/document/Download/Page_Folders - PowerFolder/span_download'))
WebUI.click(findTestObject('file_objects/document/Download/Page_Folders - PowerFolder/span_download'))

String downloadPath = System.getProperty('user.home') + '/Downloads/'
File downloadedFile = new File(downloadPath, docName + '.docx')
long deadline = System.currentTimeMillis() + (2 * 60 * 1000)
while (!downloadedFile.exists() && System.currentTimeMillis() < deadline) {
    Thread.sleep(2000)
}
WebUI.verifyEqual(downloadedFile.exists(), true)

XWPFDocument downloadedDoc = new XWPFDocument(new FileInputStream(downloadedFile))
String downloadedText = downloadedDoc.getParagraphs().collect { it.getText() }.join('\n')
downloadedDoc.close()
downloadedFile.delete()

WebUI.verifyMatch(downloadedText, '.*' + v1Marker + '.*', true)
WebUI.verifyEqual(downloadedText.contains(v2Marker), false)

WebUI.closeBrowser()

def editDocument(String name, String textToAdd) {
    WebElement row = findFolder(name)
    WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(row))
    WebUI.switchToWindowIndex(1)
    WebUI.waitForElementVisible(findTestObject('ONLY OFFICE/iframe_editor'), 20)
    WebUI.switchToFrame(findTestObject('ONLY OFFICE/iframe_editor'), 10)
    WebUI.executeJavaScript('document.body.focus();', null)
    WebUI.sendKeys(findTestObject('ONLY OFFICE/editor_body'), textToAdd)
    WebUI.delay(2)
    WebUI.sendKeys(findTestObject('ONLY OFFICE/editor_body'), Keys.chord(Keys.CONTROL, 's'))
    WebUI.delay(2)
    WebUI.switchToDefaultContent()
    WebUI.closeWindowIndex(1)
    WebUI.switchToWindowIndex(0)
    WebUI.refresh()
    WebUI.delay(10)
}

WebElement findFolder(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//td[2]/span/a[contains(text(),'" + name + "')]"))
}

WebElement findRow(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//*[contains(@data-search-keys, '" + name + "')]/td[1]/span"))
}

WebElement findDoc(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//*[contains(@data-search-keys, '" + name + "')]/td[1]/span"))
}
