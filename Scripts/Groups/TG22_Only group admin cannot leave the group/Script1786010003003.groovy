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
 * Ticket scenario 6: the only remaining admin of a group cannot leave it. LeaveAction 403s
 * (API_PERMISSION_DENIED) when isTheOnlyGroupAdmin(...) is true; the frontend menu item itself is NOT
 * disabled (groups.js never wires isSimpleAdmin/isMultiAdmin into populateGroupDropdownMenu), so the
 * block only becomes visible as a warning notification after confirming - which is what we assert here.
 * Group is built self-service by 'user1', matching real usage.
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

WebElement user1Element = driver.findElement(By.xpath(('//td/a[contains(text(),\'' + user1) + '\')]'))

WebUiBuiltInKeywords.verifyEqual(user1Element.isDisplayed(), true)

// Log out of admin, log in as 'user1' - the group is entirely self-service from here on.
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

WebUI.setText(findTestObject('Login/inputEmail'), user1)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(3)

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

// 'user1' adds himself as the SOLE member+admin (no second admin here - that is the point).
WebElement inputElement = driver.findElement(By.xpath("//*[@id='pica_group_accounts']//input[contains(concat(' ',normalize-space(@class),' '),' pica-taginput-input ')]"))

inputElement.sendKeys(user1)

WebUI.delay(3)

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/user click'))

String user1LocalPart = user1.contains('@') ? user1.substring(0, user1.indexOf('@')) : user1

new WebDriverWait(driver, java.time.Duration.ofSeconds(15)).until(
    ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='pica_group_accounts']//table//tr[@data-userdata and contains(@data-userdata,'" + user1LocalPart + "')]")))

def xpathUser1 = "//div[@id='pica_group_accounts']//table//tr[@data-userdata and contains(@data-userdata,'" + user1LocalPart + "')]//button[contains(@class,'dropdown-toggle')]"

def buttonUser1 = driver.findElement(By.xpath(xpathUser1))

// A JS-injected click skips the pointerdown/focusin events the app relies on to configure the
// dropdown's Popper positioning before Bootstrap opens it (see combo.js), leaving the menu clipped
// by the scrollable member list - a real click is required for it to open visibly.
buttonUser1.click()

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/Page_Groups - PowerFolder/Page_Groups - PowerFolder/Is member and admin'))

WebUiBuiltInKeywords.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.verifyElementPresent(findTestObject('Groups/Page_Groups - PowerFolder/div_Group updated'), 5)

new WebDriverWait(driver, java.time.Duration.ofSeconds(15)).until(
    ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='pica_group_dialog' and contains(concat(' ',normalize-space(@class),' '),' show ')]")))

WebUI.delay(2)

// Back on the groups list: try to leave via the "..." menu.
WebUI.refresh()

WebUI.delay(3)

WebUI.setText(findTestObject('Groups/Search group'), GlobalVariable.GroupName)

WebUI.delay(2)

def rowMenuXpath = "//table[@id='groups_table']/tbody/tr[contains(@data-search-keys,'" + groupName + "')]//a[@role='button' and contains(@class,'dropdown-toggle')]"

new WebDriverWait(driver, java.time.Duration.ofSeconds(15)).until(
    ExpectedConditions.presenceOfElementLocated(By.xpath(rowMenuXpath)))

WebElement rowMenuButton = driver.findElement(By.xpath(rowMenuXpath))

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(rowMenuButton))

def leaveLinkXpath = "//table[@id='groups_table']/tbody/tr[contains(@data-search-keys,'" + groupName + "')]//a[contains(@class,'groups_leave')]"

new WebDriverWait(driver, java.time.Duration.ofSeconds(10)).until(
    ExpectedConditions.elementToBeClickable(By.xpath(leaveLinkXpath)))

WebElement leaveLink = driver.findElement(By.xpath(leaveLinkXpath))

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(leaveLink))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Yes'))

WebUI.delay(2)

// LeaveAction#execute: isTheOnlyGroupAdmin(...) -> 403 API_PERMISSION_DENIED -> the JS onError callback
// shows a "warning" notification and never removes the row.
TestObject warningNotification = new TestObject('warningNotification')
warningNotification.addProperty('xpath', ConditionType.EQUALS,
    "//div[contains(@class,'pica-notification') and contains(@class,'warning')]")

WebUI.verifyElementPresent(warningNotification, 10)

TestObject groupStillListed = new TestObject('groupStillListed')
groupStillListed.addProperty('xpath', ConditionType.EQUALS,
    "//table[@id='groups_table']/tbody/tr[contains(@data-search-keys,'" + groupName + "')]")

WebUI.verifyElementPresent(groupStillListed, 5)

// Re-open the group and confirm 'user1' is still admin - the leave attempt changed nothing.
WebUI.refresh()

WebUI.delay(3)

WebUI.setText(findTestObject('Groups/Search group'), GlobalVariable.GroupName)

WebUI.delay(2)

WebElement btn1 = findGroup(GlobalVariable.GroupName)

WebUiBuiltInKeywords.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Members'))

TestObject user1StillAdmin = new TestObject('user1StillAdmin')
user1StillAdmin.addProperty('xpath', ConditionType.EQUALS,
    "//div[@id='pica_group_accounts']//table//tr[@data-userdata and contains(@data-userdata,'" + user1LocalPart + "') and contains(@data-userdata,'\"isGroupAdmin\":true')]")

WebUI.verifyElementPresent(user1StillAdmin, 10)

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
