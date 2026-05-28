import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils

WebUI.callTestCase(findTestCase('Accounts/Edit_Account/pre_test/Create_Account'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.refresh()

WebUI.delay(5)

println(GlobalVariable.userEmail)

WebElement btn = findAccount(GlobalVariable.userEmail)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/Edit_account'))

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/a_Organizations'))

String organization_name = 'My_Organization_' + RandomStringUtils.randomAlphabetic(4)

WebUI.setText(findTestObject('Accounts/Edit_Accounts - PowerFolder/organizations_input'), organization_name)

WebUI.delay(2)

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/Create new Organization'))

WebUI.click(findTestObject('Accounts/Page_Organizations - PowerFolder/button_Is member'))

WebUI.waitForElementVisible(findTestObject('Accounts/Page_Organizations - PowerFolder/checkbox_IsAdmin'), 5)

WebUI.check(findTestObject('Accounts/Page_Organizations - PowerFolder/checkbox_IsAdmin'))

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

println(GlobalVariable.userEmail)

WebUI.setText(findTestObject('Login/inputEmail'), GlobalVariable.userEmail)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(3)

WebUI.verifyElementPresent(findTestObject('Dashboard/org_admin_accounts_Manage'), 0)

WebUI.verifyElementPresent(findTestObject('Dashboard/org_admin_groups_Manage'), 0)

WebUI.verifyElementPresent(findTestObject('Dashboard/org_admin_edit_org'), 0)

WebUI.click(findTestObject('Dashboard/org_admin_accounts_Manage'))

WebUI.verifyEqual(WebUI.getWindowTitle(), 'Accounts - PowerFolder')

WebUI.click(findTestObject('Dashboard/Left_Menu_Link_Dashboard'))

WebUI.click(findTestObject('Dashboard/org_admin_groups_Manage'))

WebUI.verifyEqual(WebUI.getWindowTitle(), 'Groups - PowerFolder')

WebUI.click(findTestObject('Dashboard/Left_Menu_Link_Dashboard'))

WebUI.click(findTestObject('Dashboard/org_admin_edit_org'))

WebUI.verifyElementPresent(findTestObject('Dashboard/org_admin_organization_name'), 0)

WebUI.closeBrowser()

WebElement findAccount(String searchKey) {
    WebDriver driver = DriverFactory.getWebDriver()
    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10)).until(
        org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(
            By.xpath("//table[@id='accounts_table']/tbody/tr[@id]")))

    String xp = "//table[@id='accounts_table']/tbody/tr[contains(@data-search-keys,'" + searchKey + "') or .//a[contains(@title,'" + searchKey + "') or contains(text(),'" + searchKey + "')]]/td[1]/span"
    return driver.findElement(By.xpath(xp))
}
