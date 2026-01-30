import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords

// =========================
// Test steps
// =========================
WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [:], FailureHandling.STOP_ON_FAILURE)

String user1 = ('user_1' + RandomStringUtils.randomNumeric(4)) + '@qa-automated-webtest.com'

String user2 = ('user_2' + RandomStringUtils.randomNumeric(4)) + '@qa-automated-webtest.com'

String groupName = 'Group_' + RandomStringUtils.randomNumeric(4)

// --- Account 1 ---
WebUI.click(findTestObject('LeftNavigationIcons/account'))

WebUI.click(findTestObject('Accounts/CreateButton'))

WebUI.click(findTestObject('Accounts/ClickCreateAccount'))

WebUI.setText(findTestObject('Accounts/InputUserOrEmail'), user1)

WebUI.setText(findTestObject('Accounts/InputPassword'), GlobalVariable.Pass)

WebUI.setText(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/account_storage_overwiew'), '5')

WebUI.click(findTestObject('Groups/Groups in account'))

WebUI.setText(findTestObject('Groups/text box groups'), groupName)

WebUI.waitForElementVisible(findTestObject('Groups/Option_CreateNew'), 5)

WebUI.waitForElementClickable(findTestObject('Groups/Option_CreateNew'), 5)

WebUI.click(findTestObject('Groups/Option_CreateNew'))

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.delay(2)

// --- Account 2 (same group name) ---
WebUI.click(findTestObject('Accounts/CreateButton'))

WebUI.click(findTestObject('Accounts/ClickCreateAccount'))

WebUI.setText(findTestObject('Accounts/InputUserOrEmail'), user2)

WebUI.setText(findTestObject('Accounts/InputPassword'), GlobalVariable.Pass)

WebUI.setText(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/account_storage_overwiew'), '5')

WebUI.click(findTestObject('Groups/Groups in account'))

WebUI.setText(findTestObject('Groups/text box groups'), groupName)

WebUI.waitForElementVisible(findTestObject('Groups/Option_CreateNew'), 5)

WebUI.waitForElementClickable(findTestObject('Groups/Option_CreateNew'), 5)

WebUI.click(findTestObject('Groups/Option_CreateNew'))

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.delay(2)

// --- Go to Groups page and search ---
WebUI.click(findTestObject('Groups/Page_Dashboard - PowerFolder/lang_Groups'))

WebUI.setText(findTestObject('Groups/Search group'), groupName)

WebUI.delay(1)

WebUI.waitForElementPresent(findTestObject('Groups/AnyRowInGroupsTable'), 5)

int count = countGroupsInTable(groupName)

assert count == 2

WebUI.delay(2)

WebUI.closeBrowser()

WebElement findGroupByName(String groupName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath("//*[contains(@data-search-keys, '$groupName')]/td[1]/span"))
}

int countGroupsInTable(String groupName) {
    WebDriver driver = DriverFactory.getWebDriver()

    List<WebElement> names = driver.findElements(By.xpath('//table[@id=\'groups_table\']/tbody/tr/td[2]'))

    return names.findAll({ 
            it.getText().trim() == groupName
        }).size()
}

