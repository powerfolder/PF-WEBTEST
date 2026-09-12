import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import java.text.SimpleDateFormat as SimpleDateFormat
import java.util.Calendar as Calendar

WebUI.callTestCase(findTestCase('Accounts/Edit_Account/pre_test/Create_Account'), [:], FailureHandling.STOP_ON_FAILURE)

String accountWithDateEmail = GlobalVariable.userEmail

WebUI.refresh()

WebUI.delay(5)

WebElement accountWithDateRow = findAccount(accountWithDateEmail)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(accountWithDateRow))

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/Edit_account'))

setDateTimePickerValue(generateDatetimeLocalToday())

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.refresh()

WebUI.delay(5)

WebUI.callTestCase(findTestCase('Accounts/Edit_Account/pre_test/Create_Account'), [:], FailureHandling.STOP_ON_FAILURE)

String accountWithoutDateEmail = GlobalVariable.userEmail

WebUI.refresh()

WebUI.delay(5)

WebElement accountWithDateRow2 = findAccount(accountWithDateEmail)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(accountWithDateRow2))

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/Edit_account'))

String valueShownForAccountWithDate = getDateTimePickerValue()

if (valueShownForAccountWithDate.isEmpty()) {
    throw new Exception('Precondition failed: the account expected to carry a valid-till date shows none - cannot verify the carry-over bug')
}

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/button_Cancel'))

WebUI.delay(1)

WebElement accountWithoutDateRow = findAccount(accountWithoutDateEmail)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(accountWithoutDateRow))

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/Edit_account'))

String valueShownForAccountWithoutDate = getDateTimePickerValue()

WebUI.verifyEqual(valueShownForAccountWithoutDate, '')

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.refresh()

WebUI.delay(5)

WebElement accountWithoutDateRow2 = findAccount(accountWithoutDateEmail)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(accountWithoutDateRow2))

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/Edit_account'))

String valueAfterSave = getDateTimePickerValue()

WebUI.verifyEqual(valueAfterSave, '')

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/button_Cancel'))

WebUI.closeBrowser()

void setDateTimePickerValue(String value) {
    WebUI.executeJavaScript(
        "var el = document.getElementById('pica_account_valid_till'); " +
        "el.value = arguments[0]; " +
        "el.dispatchEvent(new Event('change', { bubbles: true }));",
        [value])
}

String getDateTimePickerValue() {
    def result = WebUI.executeJavaScript(
        "return document.getElementById('pica_account_valid_till').value;", [])
    return result == null ? '' : result.toString()
}

/** Returns today in datetime-local format: yyyy-MM-dd'T'HH:mm */
String generateDatetimeLocalToday() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
    return sdf.format(Calendar.getInstance().getTime())
}

WebElement findAccount(String searchKey) {
    WebDriver driver = DriverFactory.getWebDriver()
    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10)).until(
        org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(
            By.xpath("//table[@id='accounts_table']/tbody/tr[@id]")))

    String xp = "//table[@id='accounts_table']/tbody/tr[contains(@data-search-keys,'" + searchKey + "') or .//a[contains(@title,'" + searchKey + "') or contains(text(),'" + searchKey + "')]]/td[1]/span"
    return driver.findElement(By.xpath(xp))
}
