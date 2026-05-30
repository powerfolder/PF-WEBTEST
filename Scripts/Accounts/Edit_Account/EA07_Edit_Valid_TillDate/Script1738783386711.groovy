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

// Step 1: Create account via pre_test
WebUI.callTestCase(findTestCase('Accounts/Edit_Account/pre_test/Create_Account'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.refresh()

WebUI.delay(5)

// Step 2: Open edit dialog and set valid_till = today
WebElement btn = findAccount(GlobalVariable.userName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/Edit_account'))

setDateTimePickerValue(generateDatetimeLocalToday())

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.refresh()

WebUI.delay(5)

// Step 3: Open edit dialog and change valid_till to today + 1 year
WebElement btn2 = findAccount(GlobalVariable.userName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn2))

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/Edit_account'))

setDateTimePickerValue(generateDatetimeLocalPlusOneYear())

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.refresh()

WebUI.delay(5)

// Step 4: Verify valid_till = today + 1 year
try {
    WebDriver driver = DriverFactory.getWebDriver()

    String dynamicXPath = '//*[contains(@data-search-keys, \'' + GlobalVariable.userEmail + '\')]/td[7]/span'

    WebElement dateElement = driver.findElement(By.xpath(dynamicXPath))

    String actualDate = dateElement.getText().trim()

    String expectedDate = getExpectedDate()

    println('📌 Retrieved date: ' + actualDate)

    println('📆 Expected date: ' + expectedDate)

    if (!(actualDate.equals(expectedDate))) {
        throw new Exception('❌ ERROR: The displayed date \'' + actualDate + '\' does not match \'' + expectedDate + '\'')
    } else {
        println('✅ Verification successful: The displayed date correctly matches +1 year')
    }
}
catch (Exception e) {
    println(e.getMessage())
}

WebUI.closeBrowser()

void setDateTimePickerValue(String value) {
    WebUI.executeJavaScript(
        "var el = document.getElementById('pica_account_valid_till'); " +
        "el.value = arguments[0]; " +
        "el.dispatchEvent(new Event('change', { bubbles: true }));",
        [value])
}

/** Returns today in datetime-local format: yyyy-MM-dd'T'HH:mm */
String generateDatetimeLocalToday() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
    return sdf.format(Calendar.getInstance().getTime())
}

/** Returns today + 1 year in datetime-local format: yyyy-MM-dd'T'HH:mm */
String generateDatetimeLocalPlusOneYear() {
    Calendar cal = Calendar.getInstance()
    cal.add(Calendar.YEAR, 1)
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
    return sdf.format(cal.getTime())
}

/** Returns today + 1 year as human-readable string for table verification: d MMMM yyyy */
String getExpectedDate() {
    Calendar cal = Calendar.getInstance()
    cal.add(Calendar.YEAR, 1)
    SimpleDateFormat sdf = new SimpleDateFormat('d MMMM yyyy', Locale.ENGLISH)
    return sdf.format(cal.getTime())
}

WebElement findAccount(String searchKey) {
    WebDriver driver = DriverFactory.getWebDriver()
    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10)).until(
        org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(
            By.xpath("//table[@id='accounts_table']/tbody/tr[@id]")))

    String xp = "//table[@id='accounts_table']/tbody/tr[contains(@data-search-keys,'" + searchKey + "') or .//a[contains(@title,'" + searchKey + "') or contains(text(),'" + searchKey + "')]]/td[1]/span"
    return driver.findElement(By.xpath(xp))
}
