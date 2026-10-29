import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.DataFlavor as DataFlavor
import org.openqa.selenium.By as By
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.nio.file.Path as Path
import java.nio.file.Files as Files
import java.text.SimpleDateFormat as SimpleDateFormat
import java.util.Calendar as Calendar
import java.util.Date as Date
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import java.util.Random as Random
import com.kms.katalon.core.testobject.ConditionType
import credentials.CredentialsManager as CredentialsManager

WebUI.callTestCase(findTestCase('Accounts/Edit_Account/pre_test/Create_Account'), [:], FailureHandling.STOP_ON_FAILURE)

GlobalVariable.userName = generateRandomString(8)

GlobalVariable.userLastName = generateRandomString(8)

GlobalVariable.userEmail = generateRandomEmail().toLowerCase()

GlobalVariable.PhoneNumber = generateRandomPhoneNumber()

WebUI.click(findTestObject('Accounts/CreateButton'))

WebUI.click(findTestObject('Accounts/ClickCreateAccount'))

WebUI.setText(findTestObject('Accounts/InputUserOrEmail'), GlobalVariable.userEmail)

WebUI.setText(findTestObject('Accounts/InputPassword'), GlobalVariable.Pass)

WebUI.setText(findTestObject('Accounts/InputFirstName'), GlobalVariable.userName)

WebUI.setText(findTestObject('Accounts/InputLastName'), GlobalVariable.userLastName)

WebUI.setText(findTestObject('Accounts/InputPhoneNo'), GlobalVariable.PhoneNumber)

WebUI.setText(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/account_storage_overwiew'), '5')

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.refresh()

WebUI.delay(5)

List<String> protectedAccounts = ['admin', 'adminqa', 'qa@powerfolder.com', CredentialsManager.getAdminUsername()].unique()

WebUI.setText(findTestObject('Accounts/inputAccountSearch'), 'qa-automated-webtest.com')

WebUI.sendKeys(findTestObject('Accounts/inputAccountSearch'), Keys.chord(Keys.ENTER))

WebUI.delay(2)

WebElement btn = findFirstAccount()

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.click(findTestObject('Accounts/select_all_checkbox'))

for (WebElement row : accountRows().findAll { isProtected(it, protectedAccounts) && isSelected(it) }) {
    WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(row.findElement(By.xpath('./td[1]/span'))))
}

WebUI.delay(1)

List<WebElement> rows = accountRows()

List<WebElement> protectedRows = rows.findAll { isProtected(it, protectedAccounts) }

assert protectedRows.every { !isSelected(it) } : 'Protected accounts are still selected, nothing was deleted'

assert rows.count { isSelected(it) } == rows.size() - protectedRows.size() : 'Deselecting the protected accounts changed the selection of other accounts, nothing was deleted'

WebUI.verifyElementPresent(findTestObject('Accounts/DeleteButton'), 2)

WebUI.click(findTestObject('Accounts/DeleteButton'))

WebUI.verifyElementPresent(findTestObject('Accounts/TitelAccountDeleteDialog'), 2)

WebUI.verifyElementPresent(findTestObject('Accounts/TextAccountDeleteDialog'), 2)

WebUI.click(findTestObject('Folders/yesButton_Delete'))

WebUI.verifyElementPresent(findTestObject('Accounts/SuccessAccountDeleteMessage'), 2)

TestObject accountRow = new TestObject()
accountRow.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//*[contains(@data-search-keys, '${GlobalVariable.userEmail}')]/td[1]/span"
)

boolean notPresent = WebUI.waitForElementNotPresent(accountRow, 10)
WebUI.verifyEqual(notPresent, true)

WebUI.delay(10)

WebUI.setText(findTestObject('Accounts/inputAccountSearch'), CredentialsManager.getAdminUsername())

WebUI.sendKeys(findTestObject('Accounts/inputAccountSearch'), Keys.chord(Keys.ENTER))

WebUI.delay(2)

TestObject adminaccount = new TestObject()
adminaccount.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//*[contains(@data-search-keys, '${CredentialsManager.getAdminUsername()}')]/td[1]/span"
)

boolean Present = WebUI.waitForElementPresent(adminaccount, 10)
WebUI.verifyEqual(Present, true)

WebUI.closeBrowser()

WebElement findFirstAccount() {
    WebDriver driver = DriverFactory.getWebDriver()
    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10)).until(
        org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(
            By.xpath("//table[@id='accounts_table']/tbody/tr[@id]")))

    return driver.findElement(By.xpath("(//table[@id='accounts_table']/tbody/tr[@id])[1]/td[1]/span"))
}

List<WebElement> accountRows() {
    return DriverFactory.getWebDriver().findElements(By.xpath("//table[@id='accounts_table']/tbody/tr[@id]"))
}

boolean isSelected(WebElement row) {
    return (row.getAttribute('class') ?: '').split(/\s+/).contains('info')
}

boolean isProtected(WebElement row, List<String> protectedAccounts) {
    List<String> names = (row.getAttribute('data-search-keys') ?: '').split(/[\s,;|]+/).toList()

    for (WebElement link : row.findElements(By.xpath('.//a'))) {
        names << link.getText()
        names << (link.getAttribute('title') ?: '')
    }

    names = names*.trim()*.toLowerCase()

    names += names.findAll { it.contains('@') }.collect { it.substring(0, it.indexOf('@')) }

    return protectedAccounts.any { names.contains(it.toLowerCase()) }
}

String generateRandomString(int length) {
	String characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'

	StringBuilder randomString = new StringBuilder()

	Random random = new Random()

	for (int i = 0; i < length; i++) {
		randomString.append(characters.charAt(random.nextInt(characters.length())))
	}
	
	return randomString.toString().toLowerCase()
}

String generateRandomEmail() {
	return generateRandomString(8) + '@qa-automated-webtest.com'
}

String generateRandomPhoneNumber() {
	Random random = new Random()

	return String.format('(%03d) %03d-%04d', random.nextInt(1000), random.nextInt(1000), random.nextInt(10000))
}

