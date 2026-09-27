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
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import com.kms.katalon.core.webui.driver.DriverFactory

// Group C / PFS-5510: group "Child" gets nested under group "Parent" (added as a subgroup on
// Parent's own Members tab - the dedicated "Parents" tab is present in the DOM but deliberately
// hidden, per PF-PRO commit 07861f9ca2). SubA is then shared only with Parent; the member is only
// ever a direct member of Child, never of Parent and never granted anything on SubA directly.

WebUI.callTestCase(findTestCase('Groups/Pre_test/add member'), [:], FailureHandling.STOP_ON_FAILURE)
String memberEmail = GlobalVariable.userName
String childGroupName = GlobalVariable.GroupName

String parentGroupName = 'Group_' + RandomStringUtils.randomNumeric(4)
WebUI.click(findTestObject('Groups/Page_Dashboard - PowerFolder/lang_Groups'))
WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/Create_group_button'))
WebUI.setText(findTestObject('Groups/Page_Groups - PowerFolder/input_Organizations_pica_group_name'), parentGroupName)
WebUI.setText(findTestObject('Groups/Page_Groups - PowerFolder/textarea_Organizations_pica_group_notes'), 'create group')
WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Save'))
WebUI.refresh()
WebUI.delay(3)

WebUI.setText(findTestObject('Groups/Search group'), parentGroupName)
WebUI.delay(2)
WebElement parentGroupRow = findGroup(parentGroupName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(parentGroupRow))

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/a_Edit_m'))
WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/a_Members'))

WebDriver driver = DriverFactory.getWebDriver()
WebElement membersInput = driver.findElement(By.xpath("//*[@id='pica_group_accounts']//input[contains(concat(' ',normalize-space(@class),' '),' pica-taginput-input ')]"))
membersInput.sendKeys(childGroupName)

By groupDropdownEntryLocator = By.xpath("(//div[@id='pica_group_accounts']//ul[contains(@class,'pica-taginput-dropdown')]/li[not(contains(@class,'pica-taginput-dropdown-fixed'))][.//span[contains(@class,'glyphicons-group')]])[1]/a")
new WebDriverWait(driver, java.time.Duration.ofSeconds(10)).until(
    ExpectedConditions.elementToBeClickable(groupDropdownEntryLocator))
driver.findElement(groupDropdownEntryLocator).click()

new WebDriverWait(driver, java.time.Duration.ofSeconds(10)).until(
    ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='pica_group_accounts']//table//tr[@data-userdata]")))
WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Save'))

new WebDriverWait(driver, java.time.Duration.ofSeconds(15)).until(
    ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='pica_group_dialog' and contains(concat(' ',normalize-space(@class),' '),' show ')]")))

WebUI.click(findTestObject('LeftNavigationIcons/folders'))

String tlfName = 'SFS36_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))
WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Folders/inputFolderName'), tlfName)
WebUI.click(findTestObject('Folders/buttonOK'))

String subAName = 'SFS36_SubA_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), subAName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebUI.click(findTestObject('Links/share_icon_inside_folder'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/folder_share_permission_dropdown_toggle'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/folder_share_permission_r_w'))
WebUI.setText(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), parentGroupName)
WebUI.delay(5)
WebUI.sendKeys(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), Keys.chord(Keys.ENTER))
WebUI.verifyElementText(findTestObject('Share/Page_Folders - PowerFolder/td_Group'), parentGroupName)
WebUI.click(findTestObject('Share/close_button_folder_share_mail'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))
WebUI.setText(findTestObject('Login/inputEmail'), memberEmail)
WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)
WebUI.click(findTestObject('Login/loginSubmit'))
WebUI.delay(3)
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

// group shares (direct or via a parent group) apply immediately - no invitation to accept
TestObject subAPresent = new TestObject()
subAPresent.addProperty('xpath', ConditionType.EQUALS, "//a[contains(concat(' ',normalize-space(@class),' '),' pica-name ') and normalize-space(text())='" + subAName + "']")
WebUI.verifyElementPresent(subAPresent, 10)

WebElement subARow = findFolder(subAName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(subARow))

TestObject createInFolderControl = findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder')
WebUI.waitForElementVisible(createInFolderControl, 10)
WebUI.verifyElementVisible(createInFolderControl)

String uploadedFileName = 'sfs36_upload_' + RandomStringUtils.randomAlphanumeric(6) + '.txt'
File localFile = File.createTempFile('sfs36_', '.txt')
localFile.text = 'SFS36 access granted through a parent group, no direct or child-group permission'
File renamedLocalFile = new File(localFile.getParentFile(), uploadedFileName)
localFile.renameTo(renamedLocalFile)

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.verifyElementPresent(findTestObject('Folders/Page_Folders - PowerFolder/Upload files'), 5)
WebUI.click(findTestObject('Folders/Page_Folders - PowerFolder/Upload files'))

TestObject uploadInput = new TestObject('uploadInput')
uploadInput.addProperty('xpath', ConditionType.EQUALS, "//input[@id='upload_input_files']")
WebUI.waitForElementPresent(uploadInput, 10)
WebUI.uploadFile(uploadInput, renamedLocalFile.getAbsolutePath())

TestObject successMsg = new TestObject('successMsg')
successMsg.addProperty('xpath', ConditionType.EQUALS, "//*[contains(text(),'Successfully uploaded')]")
WebUI.waitForElementVisible(successMsg, 15)

TestObject dangerNotification = new TestObject()
dangerNotification.addProperty('xpath', ConditionType.EQUALS, "//div[contains(concat(' ',normalize-space(@class),' '),' alert-danger ')]")
WebUI.verifyElementNotPresent(dangerNotification, 5)

TestObject closeUploadBtn = new TestObject('closeUploadBtn')
closeUploadBtn.addProperty('xpath', ConditionType.EQUALS, "//button[@id='upload_stop_button']")
WebUI.waitForElementClickable(closeUploadBtn, 10)
WebUI.click(closeUploadBtn)
renamedLocalFile.delete()

TestObject uploadedFilePresent = new TestObject()
uploadedFilePresent.addProperty('xpath', ConditionType.EQUALS, "//*[contains(@data-search-keys, '" + uploadedFileName + "')]/td[1]/span")
WebUI.verifyElementPresent(uploadedFilePresent, 10)

def suspiciousConsoleEntries = []
try {
    WebDriver consoleDriver = DriverFactory.getWebDriver()
    def logs = consoleDriver.manage().logs().get('browser').getAll()
    suspiciousConsoleEntries = logs.findAll { entry ->
        String msg = entry.getMessage().toLowerCase()
        msg.contains('403') || msg.contains('permission denied') || msg.contains('forbidden')
    }
} catch (Exception e) {
    WebUI.comment('Browser console log retrieval not available in this environment - skipping console check: ' + e.getMessage())
}
WebUI.verifyEqual(suspiciousConsoleEntries.isEmpty(), true)

WebUI.closeBrowser()

WebElement findGroup(String groupName) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//table[@id='groups_table']/tbody/tr[contains(@data-search-keys,'" + groupName + "') or .//a[contains(text(),'" + groupName + "')]]/td[1]/span"))
}

WebElement findFolder(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//td[2]/span/a[contains(text(),'" + name + "')]"))
}
