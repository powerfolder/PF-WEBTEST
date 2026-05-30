import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement

WebUI.callTestCase(findTestCase('Accounts/Edit_Account/pre_test/Create_Account'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.refresh()

WebUI.delay(5)

String emailId = GlobalVariable.userEmail

println(emailId)

WebElement btn = findAccount(emailId)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUI.click(findTestObject('Accounts/ClickOnAvatar'))

WebUI.click(findTestObject('Accounts/ChangeButton'))

String projDir = RunConfiguration.getProjectDir()

String fname = projDir + '/Images/user.png'

WebUI.uploadFile(findTestObject('Accounts/AddFileButton'), fname)

WebUI.click(findTestObject('Accounts/CloseButton'))

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.refresh()

WebUI.delay(5)

WebElement btn1 = findAccount(emailId)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUI.click(findTestObject('Accounts/ClickOnAvatar'))

WebUI.verifyElementVisible(findTestObject('Accounts/Page_Accounts - PowerFolder/account_avatar'))

WebUI.delay(1)

WebUI.closeBrowser()

WebElement findAccount(String searchKey) {
    WebDriver driver = DriverFactory.getWebDriver()
    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10)).until(
        org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(
            By.xpath("//table[@id='accounts_table']/tbody/tr[@id]")))

    String xp = "//table[@id='accounts_table']/tbody/tr[contains(@data-search-keys,'" + searchKey + "') or .//a[contains(@title,'" + searchKey + "') or contains(text(),'" + searchKey + "')]]/td[1]/span"
    return driver.findElement(By.xpath(xp))
}
