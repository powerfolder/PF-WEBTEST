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
import com.kms.katalon.core.webui.driver.DriverFactory

WebUI.callTestCase(findTestCase('Accounts/Edit_Account/pre_test/Create_Account'), [:], FailureHandling.STOP_ON_FAILURE)
String memberEmail = GlobalVariable.userEmail

WebUI.click(findTestObject('LeftNavigationIcons/folders'))

String tlfName = 'SFS41_' + RandomStringUtils.randomAlphanumeric(6)
WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))
WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Folders/inputFolderName'), tlfName)
WebUI.click(findTestObject('Folders/buttonOK'))

String subAName = 'SFS41_SubA_' + RandomStringUtils.randomAlphanumeric(6)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), subAName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

String docName = 'SFS41_Doc_' + RandomStringUtils.randomAlphanumeric(6)
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
boolean fileDownloaded = waitForFile(downloadPath, docName + '.docx', 120)
WebUI.verifyEqual(fileDownloaded, true)

WebUI.click(findTestObject('LeftNavigationIcons/folders'))

WebElement subARowForZip = findRow(subAName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(subARowForZip))
WebUI.click(findTestObject('Folders/downloadLink'))

boolean zipDownloaded = waitForFile(downloadPath, subAName + '.zip', 120)
WebUI.verifyEqual(zipDownloaded, true)

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
WebUI.verifyEqual(suspiciousConsoleEntries.isEmpty(), true)

WebUI.closeBrowser()

boolean waitForFile(String path, String fileName, int timeoutSeconds) {
    File file = new File(path, fileName)
    File tempFile = new File(path, fileName + '.crdownload')
    int waited = 0
    while (waited < timeoutSeconds) {
        if (file.exists() && !tempFile.exists()) {
            file.delete()
            return true
        }
        Thread.sleep(1000)
        waited++
    }
    return false
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
