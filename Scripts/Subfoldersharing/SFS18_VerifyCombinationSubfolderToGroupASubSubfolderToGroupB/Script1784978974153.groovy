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

/*
 * Subfolder Sharing spec, section 1.1/2.0 for GROUPS: Group A gets an explicit share on the
 * SUBFOLDER, Group B gets an explicit share on the SUB-SUBFOLDER nested inside it. Mirrors SFS13,
 * but with groups instead of individual accounts - group shares apply immediately (no invitation
 * to accept, see SFS16).
 *
 *   TopFolder (not shared)
 *   `-- Subfolder            -> explicit Group A
 *       `-- SubSubfolder     -> explicit Group B (+ inherited from Group A via Subfolder)
 *
 * Expected:
 *   Group A's member: sees Subfolder (explicit) and SubSubfolder (inherited), NOT TopFolder
 *   Group B's member: sees SubSubfolder (explicit) only, NOT Subfolder, NOT TopFolder
 *
 * Only one call to Groups/Pre_test/add member is possible per script (it re-opens the browser via
 * the admin login pretest internally), so Group B and its member are created manually here,
 * mirroring that pretest's own steps.
 */

// create Group A with member A (also logs in as admin); sets GlobalVariable.userName (member A
// email) and GlobalVariable.GroupName (Group A)
WebUI.callTestCase(findTestCase('Groups/Pre_test/add member'), [:], FailureHandling.STOP_ON_FAILURE)
String memberAEmail = GlobalVariable.userName
String groupAName = GlobalVariable.GroupName

// create member B's account manually
String memberBEmail = ('sfs18b_' + RandomStringUtils.randomNumeric(6)) + '@qa-automated-webtest.com'
WebUI.click(findTestObject('LeftNavigationIcons/account'))
WebUI.click(findTestObject('Accounts/CreateButton'))
WebUI.click(findTestObject('Accounts/ClickCreateAccount'))
WebUI.setText(findTestObject('Accounts/InputUserOrEmail'), memberBEmail)
WebUI.setText(findTestObject('Accounts/InputPassword'), GlobalVariable.Pass)
WebUI.setText(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/account_storage_overwiew'), '5')
WebUI.click(findTestObject('Accounts/SaveButton'))
WebUI.delay(2)

// create Group B and add member B to it (mirrors Groups/Pre_test/add member's own steps)
String groupBName = 'Group_' + RandomStringUtils.randomNumeric(4)
WebDriver driver = DriverFactory.getWebDriver()

WebUI.click(findTestObject('Groups/Page_Dashboard - PowerFolder/lang_Groups'))
WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/Create_group_button'))
WebUI.setText(findTestObject('Groups/Page_Groups - PowerFolder/input_Organizations_pica_group_name'), groupBName)
WebUI.setText(findTestObject('Groups/Page_Groups - PowerFolder/textarea_Organizations_pica_group_notes'), 'create group')
WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Save'))
WebUI.refresh()
WebUI.delay(3)

WebUI.setText(findTestObject('Groups/Search group'), groupBName)
WebUI.delay(2)
WebElement groupBRow = findGroup(groupBName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(groupBRow))

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/a_Edit_m'))
WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/a_Members'))

WebElement inputElement = driver.findElement(By.xpath("//*[@id='pica_group_accounts']//input[contains(concat(' ',normalize-space(@class),' '),' pica-taginput-input ')]"))
inputElement.sendKeys(memberBEmail)

new WebDriverWait(driver, java.time.Duration.ofSeconds(10)).until(
    ExpectedConditions.elementToBeClickable(
        By.xpath("(//div[@id='pica_group_accounts']//ul[contains(@class,'pica-taginput-dropdown')]/li[not(contains(@class,'pica-taginput-dropdown-fixed'))])[1]/a")))
WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/user click'))

new WebDriverWait(driver, java.time.Duration.ofSeconds(10)).until(
    ExpectedConditions.presenceOfElementLocated(
        By.xpath("//div[@id='pica_group_accounts']//table//tr[@data-userdata]")))
WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Save'))

new WebDriverWait(driver, java.time.Duration.ofSeconds(15)).until(
    ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='pica_group_dialog' and contains(concat(' ',normalize-space(@class),' '),' show ')]")))

// navigate to the Folders section
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

// create the top-level folder - not shared with anyone - creation navigates straight into it
String tlfName = 'SFS18_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))
WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Folders/inputFolderName'), tlfName)
WebUI.click(findTestObject('Folders/buttonOK'))

// create the subfolder inside the top-level folder - creation navigates straight into it
String subFolderName = 'SFS18_Sub_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), subFolderName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

// share the (currently open) subfolder with Group A
WebUI.click(findTestObject('Links/share_icon_inside_folder'))
WebUI.setText(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), groupAName)
WebUI.delay(5)
WebUI.sendKeys(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), Keys.chord(Keys.ENTER))
WebUI.verifyElementText(findTestObject('Share/Page_Folders - PowerFolder/td_Group'), groupAName)
WebUI.click(findTestObject('Share/close_button_folder_share_mail'))

// create the sub-subfolder inside the subfolder - creation navigates straight into it
String subSubFolderName = 'SFS18_SubSub_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), subSubFolderName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

// share the (currently open) sub-subfolder with Group B
WebUI.click(findTestObject('Links/share_icon_inside_folder'))
WebUI.setText(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), groupBName)
WebUI.delay(5)
WebUI.sendKeys(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), Keys.chord(Keys.ENTER))
WebUI.verifyElementText(findTestObject('Share/Page_Folders - PowerFolder/td_Group'), groupBName)
WebUI.click(findTestObject('Share/close_button_folder_share_mail'))

// --- Group A's member: sees Subfolder (explicit) and SubSubfolder (inherited), NOT TopFolder ---
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))
WebUI.setText(findTestObject('Login/inputEmail'), memberAEmail)
WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)
WebUI.click(findTestObject('Login/loginSubmit'))
WebUI.delay(3)
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

TestObject tlfPresent = new TestObject()
tlfPresent.addProperty('xpath', ConditionType.EQUALS, "//a[contains(concat(' ',normalize-space(@class),' '),' pica-name ') and normalize-space(text())='" + tlfName + "']")
WebUI.verifyElementNotPresent(tlfPresent, 10)

WebElement memberASubFolderRow = findFolder(subFolderName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(memberASubFolderRow))

TestObject subSubFolderPresent = new TestObject()
subSubFolderPresent.addProperty('xpath', ConditionType.EQUALS, "//a[contains(concat(' ',normalize-space(@class),' '),' pica-name ') and normalize-space(text())='" + subSubFolderName + "']")
WebUI.verifyElementPresent(subSubFolderPresent, 10)

// --- Group B's member: sees SubSubfolder only, NOT Subfolder, NOT TopFolder ---
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))
WebUI.setText(findTestObject('Login/inputEmail'), memberBEmail)
WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)
WebUI.click(findTestObject('Login/loginSubmit'))
WebUI.delay(3)
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

WebUI.verifyElementNotPresent(tlfPresent, 10)

TestObject subFolderPresent = new TestObject()
subFolderPresent.addProperty('xpath', ConditionType.EQUALS, "//a[contains(concat(' ',normalize-space(@class),' '),' pica-name ') and normalize-space(text())='" + subFolderName + "']")
WebUI.verifyElementNotPresent(subFolderPresent, 10)

WebElement memberBSubSubFolderRow = findFolder(subSubFolderName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(memberBSubSubFolderRow))
WebUI.verifyElementPresent(findTestObject('Folders/createFolderIcon'), 10)

WebUI.closeBrowser()

WebElement findFolder(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//td[2]/span/a[contains(text(),'" + name + "')]"))
}

WebElement findGroup(String groupName) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//*[contains(@data-search-keys, '" + groupName + "')]/td[1]/span"))
}
