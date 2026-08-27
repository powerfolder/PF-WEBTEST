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

String tlfName = 'SFS26_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))
WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Folders/inputFolderName'), tlfName)
WebUI.click(findTestObject('Folders/buttonOK'))

String subAName = 'SFS26_SubA_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), subAName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

String docName = 'SFS26_Doc_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_Document'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), docName)
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

TestObject docPresent = new TestObject()
docPresent.addProperty('xpath', ConditionType.EQUALS, "//*[contains(@data-search-keys, '" + docName + "')]/td[1]/span")
WebUI.verifyElementPresent(docPresent, 10)

WebElement docRow = findDoc(docName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(docRow))
WebUI.verifyElementClickable(findTestObject('file_objects/document/Download/Page_Folders - PowerFolder/span_download'))
WebUI.click(findTestObject('file_objects/document/Download/Page_Folders - PowerFolder/span_download'))

String downloadPath = System.getProperty('user.home') + '/Downloads/'
File downloadedFile = new File(downloadPath, docName + '.docx')
long deadline = System.currentTimeMillis() + (2 * 60 * 1000)
while (!downloadedFile.exists() && System.currentTimeMillis() < deadline) {
    Thread.sleep(2000)
}
WebUI.verifyEqual(downloadedFile.exists(), true)
downloadedFile.delete()

WebUI.verifyElementNotVisible(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

String uploadFileName = 'sfs26_upload_' + RandomStringUtils.randomAlphanumeric(6) + '.txt'
File localFile = File.createTempFile('sfs26_', '.txt')
localFile.text = 'SFS26 read-only upload denial check'
File renamedFile = new File(localFile.getParentFile(), uploadFileName)
localFile.renameTo(renamedFile)

TestObject uploadInput = new TestObject('uploadInput')
uploadInput.addProperty('xpath', ConditionType.EQUALS, "//input[@id='upload_input_files']")
WebUI.uploadFile(uploadInput, renamedFile.getAbsolutePath())
WebUI.delay(5)
renamedFile.delete()

TestObject uploadedFilePresent = new TestObject()
uploadedFilePresent.addProperty('xpath', ConditionType.EQUALS, "//*[contains(@data-search-keys, '" + uploadFileName + "')]/td[1]/span")
WebUI.verifyElementNotPresent(uploadedFilePresent, 10)

String forcedDirName = 'SFS26_Denied_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.executeJavaScript("document.querySelector('#pica-files-create-dropdown li.files-ui-create-dir > a').click()", null)
WebUI.waitForElementVisible(findTestObject('Folders/inputFolderName'), 5)
WebUI.setText(findTestObject('Folders/inputFolderName'), forcedDirName)
WebUI.click(findTestObject('Folders/buttonOK'))
WebUI.delay(3)

TestObject forcedDirPresent = new TestObject()
forcedDirPresent.addProperty('xpath', ConditionType.EQUALS, "//a[contains(concat(' ',normalize-space(@class),' '),' pica-name ') and normalize-space(text())='" + forcedDirName + "']")
WebUI.verifyElementNotPresent(forcedDirPresent, 10)

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

WebElement docRowToggle = WebUI.findWebElement(findTestObject('file_objects/document/Page_Open - PowerFolder/file_manage'), 5)
WebUI.executeJavaScript("arguments[0].closest('tr').querySelector('a.dropdown-toggle').click()", Arrays.asList(docRowToggle))
WebUI.delay(1)

TestObject rowRenameOption = new TestObject()
rowRenameOption.addProperty('xpath', ConditionType.EQUALS, "//table[@id='files_files_table']//ul[contains(@class,'conext-dropdown-menu')]//a[contains(concat(' ',normalize-space(@class),' '),' files-ui-rename ')]")
WebUI.verifyElementNotPresent(rowRenameOption, 5)

TestObject rowCutOption = new TestObject()
rowCutOption.addProperty('xpath', ConditionType.EQUALS, "//table[@id='files_files_table']//ul[contains(@class,'conext-dropdown-menu')]//a[contains(concat(' ',normalize-space(@class),' '),' files-ui-cut ')]")
WebUI.verifyElementNotPresent(rowCutOption, 5)

TestObject rowDeleteOption = new TestObject()
rowDeleteOption.addProperty('xpath', ConditionType.EQUALS, "//table[@id='files_files_table']//ul[contains(@class,'conext-dropdown-menu')]//a[contains(concat(' ',normalize-space(@class),' '),' files-ui-delete ')]")
WebUI.verifyElementNotPresent(rowDeleteOption, 5)

TestObject rowDownloadOption = new TestObject()
rowDownloadOption.addProperty('xpath', ConditionType.EQUALS, "//table[@id='files_files_table']//ul[contains(@class,'conext-dropdown-menu')]//a[contains(concat(' ',normalize-space(@class),' '),' files-ui-download ')]")
WebUI.verifyElementPresent(rowDownloadOption, 5)

WebUI.closeBrowser()

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
