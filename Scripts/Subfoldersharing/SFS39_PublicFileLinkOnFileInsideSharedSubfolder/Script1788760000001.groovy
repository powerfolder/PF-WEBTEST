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
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import helpers.Helper

WebUI.callTestCase(findTestCase('Accounts/Edit_Account/pre_test/Create_Account'), [:], FailureHandling.STOP_ON_FAILURE)
String memberEmail = GlobalVariable.userEmail

WebUI.click(findTestObject('LeftNavigationIcons/folders'))

String tlfName = 'SFS39_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))
WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Folders/inputFolderName'), tlfName)
WebUI.click(findTestObject('Folders/buttonOK'))

String subAName = 'SFS39_SubA_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), subAName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

String docName = 'SFS39_Doc_' + RandomStringUtils.randomAlphanumeric(6)

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

WebUI.click(findTestObject('Links/share_icon_inside_folder'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/folder_share_permission_dropdown_toggle'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/folder_share_permission_admin'))
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

WebElement subAShareBtn = Helper.findShareButton(subAName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(subAShareBtn))
WebUI.click(findTestObject('Folders/shareLink'))
WebUI.waitForElementClickable(findTestObject('links files/Page_Folders - PowerFolder/label_Can read and write'), 10)
WebUI.click(findTestObject('links files/Page_Folders - PowerFolder/label_Can read and write'))
WebUI.click(findTestObject('Folders/button_SaveSettings'))
WebUI.waitForElementVisible(findTestObject('Page_Folders - PowerFolder/icon-copy'), 15)
WebUI.doubleClick(findTestObject('Page_Folders - PowerFolder/icon-copy'))
String folderLinkUrl = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor)
WebUI.click(findTestObject('links files/Page_Folders - PowerFolder/button_Close'))

WebElement subARow = findFolder(subAName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(subARow))

WebElement docShareBtn = Helper.findShareButton(docName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(docShareBtn))
WebUI.click(findTestObject('Folders/shareLink'))
WebUI.waitForElementClickable(findTestObject('Folders/button_SaveSettings'), 10)
WebUI.click(findTestObject('Folders/button_SaveSettings'))
WebUI.waitForElementVisible(findTestObject('Page_Folders - PowerFolder/icon-copy'), 15)
WebUI.doubleClick(findTestObject('Page_Folders - PowerFolder/icon-copy'))
String fileLinkUrl = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor)
WebUI.click(findTestObject('links files/Page_Folders - PowerFolder/button_Close'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))
WebUI.delay(2)

WebUI.navigateToUrl(fileLinkUrl)
WebUI.delay(3)
WebUI.verifyEqual(WebUI.getWindowTitle(), 'Link - PowerFolder')

WebUI.click(findTestObject('Links/Page_Link - PowerFolder/folder_link_download_button'))

String downloadPath = System.getProperty('user.home') + '/Downloads/'
File downloadedFile = new File(downloadPath, docName + '.docx')
long deadline = System.currentTimeMillis() + (2 * 60 * 1000)
while (!downloadedFile.exists() && System.currentTimeMillis() < deadline) {
    Thread.sleep(2000)
}
WebUI.verifyEqual(downloadedFile.exists(), true)
downloadedFile.delete()

WebUI.navigateToUrl(folderLinkUrl)
WebUI.delay(3)
WebUI.verifyEqual(WebUI.getWindowTitle(), 'Link - PowerFolder')

String uploadedFileName = 'sfs39_' + RandomStringUtils.randomAlphanumeric(6) + '.txt'
File localFile = File.createTempFile('sfs39_', '.txt')
localFile.text = 'SFS39 upload through a read/write folder link'
File renamedLocalFile = new File(localFile.getParentFile(), uploadedFileName)
localFile.renameTo(renamedLocalFile)

WebDriver driver = DriverFactory.getWebDriver()
WebElement uploadTrigger = driver.findElement(By.xpath("//a[@id='filelink_upload']"))
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(uploadTrigger))
WebElement uploadInput = driver.findElement(By.xpath("//input[@id='upload_input_files']"))
uploadInput.sendKeys(renamedLocalFile.getAbsolutePath())
WebUI.click(findTestObject('Page_Link - PowerFolder/lang_Upload_1'))
WebUI.click(findTestObject('Page_Link - PowerFolder/button_Close'))
renamedLocalFile.delete()

WebUI.verifyElementText(findTestObject('Page_Link - PowerFolder/table'), uploadedFileName)

WebUI.setText(findTestObject('Login/inputEmail'), memberEmail)
WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)
WebUI.click(findTestObject('Login/loginSubmit'))
WebUI.delay(3)
WebUI.click(findTestObject('Links/Page_Dashboard - PowerFolder/lang_Links'))

WebElement fileLinkRow = findLink(docName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(fileLinkRow))
WebUI.verifyElementClickable(findTestObject('LinksTable/Delete Link'))
WebUI.click(findTestObject('LinksTable/Delete Link'))
WebUI.click(findTestObject('LinksTable/Conferme delete'))
WebUI.refresh()
WebUI.delay(3)

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))
WebUI.delay(2)

WebUI.navigateToUrl(fileLinkUrl)
WebUI.delay(3)
WebUI.verifyNotEqual(WebUI.getWindowTitle(), 'Link - PowerFolder')

WebUI.closeBrowser()

WebElement findFolder(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//td[2]/span/a[contains(text(),'" + name + "')]"))
}

WebElement findRow(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//*[contains(@data-search-keys, '" + name + "')]/td[1]/span"))
}

WebElement findLink(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//*[contains(@data-search-keys, '" + name + "')]/td[1]/span"))
}
