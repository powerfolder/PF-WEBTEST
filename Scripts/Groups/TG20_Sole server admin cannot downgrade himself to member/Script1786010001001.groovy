import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberBuiltinKeywords
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGBuiltinKeywords
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as WindowsBuiltinKeywords
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import java.util.Arrays as Arrays
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.testobject.ConditionType as ConditionType

/*
 * Ticket scenario 3: a server/org admin can never be downgraded to a plain group member.
 * The group itself is built self-service by a regular user (not by the site admin) - matches how
 * groups are actually created in production. The site admin is only invited into the group afterwards,
 * to check that HIS OWN row disables "Is member" once he is a member.
 *
 * We never learn the site admin's plaintext email/password - WebUI.setEncryptedText types the
 * already-encrypted "Pretest - Admin Login" credentials directly into the taginput / login fields,
 * so the browser session ends up as the admin without the script ever holding the plaintext value.
 */

WebUiBuiltInKeywords.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

GlobalVariable.userName = (('user_' + RandomStringUtils.randomNumeric(4)) + '@qa-automated-webtest.com')

String user1 = GlobalVariable.userName

WebUiBuiltInKeywords.click(findTestObject('LeftNavigationIcons/account'))

WebUiBuiltInKeywords.click(findTestObject('Accounts/CreateButton'))

WebUiBuiltInKeywords.click(findTestObject('Accounts/ClickCreateAccount'))

WebUiBuiltInKeywords.setText(findTestObject('Accounts/InputUserOrEmail'), user1)

WebUiBuiltInKeywords.setText(findTestObject('Accounts/InputPassword'), GlobalVariable.Pass)

WebUiBuiltInKeywords.setText(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/account_storage_overwiew'),
    '5')

WebUiBuiltInKeywords.click(findTestObject('Accounts/SaveButton'))

WebUI.delay(2)

WebDriver driver = DriverFactory.getWebDriver()

WebElement userElement = driver.findElement(By.xpath(('//td/a[contains(text(),\'' + user1) + '\')]'))

WebUiBuiltInKeywords.verifyEqual(userElement.isDisplayed(), true)

// Log out of admin, log in as the freshly created regular user
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

WebUI.setText(findTestObject('Login/inputEmail'), user1)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(3)

// 'user1' creates the group himself (self-service) - group creation is open to any logged-in account
// (StoreAction#checkPermissions returns true when no group id is given), the creator is auto-granted
// GroupAdminPermission but is NOT auto-added as an explicit member.
String groupName = 'Group_' + RandomStringUtils.randomNumeric(4)

GlobalVariable.GroupName = groupName

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Dashboard - PowerFolder/lang_Groups'))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/Create_group_button'))

WebUiBuiltInKeywords.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/input_Organizations_pica_group_name'),
    groupName)

WebUiBuiltInKeywords.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/textarea_Organizations_pica_group_notes'),
    'create group')

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.delay(2)

def btn = findGroup(groupName)

WebUiBuiltInKeywords.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Members'))

// Invite the site admin into the group without ever knowing his plaintext email: type the same
// encrypted credential 'Pretest - Admin Login' already uses, just aimed at the taginput this time.
TestObject taginput = new TestObject('taginput')
taginput.addProperty('xpath', ConditionType.EQUALS,
    "//*[@id='pica_group_accounts']//input[contains(concat(' ',normalize-space(@class),' '),' pica-taginput-input ')]")

WebUI.setEncryptedText(taginput, 'CKkAs2Ee0vA=')

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/user click'))

new WebDriverWait(driver, java.time.Duration.ofSeconds(15)).until(
    ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='pica_group_accounts']//table//tr[@data-userdata]")))

// The admin is the only row in a brand-new group - no need to know his email to target his row.
def xpathAdminRow = "//div[@id='pica_group_accounts']//table//tr[@data-userdata]//button[contains(@class,'dropdown-toggle')]"

def adminRowButton = driver.findElement(By.xpath(xpathAdminRow))

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(adminRowButton))

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/Page_Groups - PowerFolder/Page_Groups - PowerFolder/Is member and admin'))

WebUiBuiltInKeywords.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.verifyElementPresent(findTestObject('Groups/Page_Groups - PowerFolder/div_Group updated'), 5)

new WebDriverWait(driver, java.time.Duration.ofSeconds(15)).until(
    ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='pica_group_dialog' and contains(concat(' ',normalize-space(@class),' '),' show ')]")))

WebUI.delay(2)

// Log out of 'user1', log back in as the site admin - same encrypted credentials, never decrypted in the script.
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

WebUI.setEncryptedText(findTestObject('Login/inputEmail'), 'CKkAs2Ee0vA=')

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.setEncryptedText(findTestObject('Login/inputPassword'), 'PpFy9OM6JMUrpEOD1UO9247r7Yrm9E0x')

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(3)

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Dashboard - PowerFolder/lang_Groups'))

WebUI.delay(3)

WebUI.setText(findTestObject('Groups/Search group'), GlobalVariable.GroupName)

WebUI.delay(2)

WebElement btn1 = findGroup(GlobalVariable.GroupName)

WebUiBuiltInKeywords.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Members'))

new WebDriverWait(driver, java.time.Duration.ofSeconds(15)).until(
    ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='pica_group_accounts']//table//tr[@data-userdata]")))

// Same locator as before: the admin is still the only explicit member of this group.
def adminRowButton2 = driver.findElement(By.xpath(xpathAdminRow))

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(adminRowButton2))

// PFS-5585 / group.js isOnlyGroupAdmin: since the admin is the sole GroupAdminPermission holder here
// (AdminPermission implies GroupAdminPermission for any group, see AdminPermission#implies), his own
// "Is member" option must carry no click handler at all.
WebElement isMemberOption = driver.findElement(By.xpath(
    "//div[@id='pica_group_accounts']//ul[contains(concat(' ',normalize-space(@class),' '),' dropdown-menu ') and contains(concat(' ',normalize-space(@class),' '),' show ')]/li[a[@data-dropdown-group='permission']][1]/a"))

Object hasClickHandler = WebUI.executeJavaScript(
    "var el = arguments[0]; var ev = (window.jQuery && jQuery._data) ? jQuery._data(el, 'events') : null; return !!(ev && ev.click && ev.click.length > 0);",
    Arrays.asList(isMemberOption))

WebUiBuiltInKeywords.verifyEqual(hasClickHandler, false)

WebUI.closeBrowser()

@Keyword
WebElement findGroup(String groupName) {
    WebDriver driver = DriverFactory.getWebDriver()
    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10)).until(
        org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(
            By.xpath("//table[@id='groups_table']/tbody/tr[@id]")))

    String xp = "//table[@id='groups_table']/tbody/tr[contains(@data-search-keys,'" + groupName + "') or .//a[contains(text(),'" + groupName + "')]]/td[1]/span"
    return driver.findElement(By.xpath(xp))
}
