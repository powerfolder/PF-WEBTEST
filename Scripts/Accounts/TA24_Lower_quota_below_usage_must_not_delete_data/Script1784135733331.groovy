import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import org.openqa.selenium.By as By
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait
import java.time.Duration as Duration
import java.util.Arrays as Arrays

/*
 * PFS-5629 regression test
 *
 * v26.1.104 (correct, PFS-4499): lowering a user's quota below their current
 * usage only blocks further uploads until the user deletes data.
 *
 * v26.2.111 (regression): confirming the same warning deletes the user's
 * files/folders and leaves shared folders without an owner.
 *
 * This test creates a fresh account, uploads a ~2 GB file that is generated
 * entirely in browser memory (never written to the local filesystem, per
 * PFS-5629 requirements) to build up real usage, then has the admin lower
 * the quota below that usage and confirms the warning dialog. It then
 * asserts none of the described data loss happened.
 */

int uploadWaitSeconds = 300

WebUI.callTestCase(findTestCase('Accounts/Edit_Account/pre_test/Create_Account'), [:], FailureHandling.STOP_ON_FAILURE)

String folderName = getRandomFolderName()

String bigFileName = 'big_file_' + RandomStringUtils.randomNumeric(6) + '.bin'

long bigFileBytes = 2L * 1024 * 1024 * 1024

// The test asserts on English UI strings (upload success/failure messages,
// the storage warning dialog text). The account's own language preference
// can otherwise fall back to the browser/OS locale, so force it to English
// regardless of what language the machine running the browser is set to.
WebUI.refresh()

WebUI.delay(5)

WebElement accountRowForLanguage = findAccount(GlobalVariable.userName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(accountRowForLanguage))

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/Edit_account'))

WebUI.selectOptionByLabel(findTestObject('Accounts/SelectLanguageDropDrown'), 'English', false)

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.waitForElementVisible(findTestObject('Accounts/alertAccountUpdated'), 15)

WebUI.delay(2)

// --- Log out of the admin session and log in as the freshly created user ---
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

WebUI.delay(2)

WebUI.setText(findTestObject('Login/inputEmail'), GlobalVariable.userEmail)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(3)

// --- As the user: create a folder and upload a ~2 GB file into it ---
WebUI.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebUI.delay(2)

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createFolder'))

WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)

WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.delay(2)

// Creating the folder navigates straight into it (same as File/Pre_test/Create_folder),
// so the "+" menu here already opens items inside the new folder.
startUpload(bigFileName, bigFileBytes)

TestObject successMsg = new TestObject('successMsg')
successMsg.addProperty('xpath', ConditionType.EQUALS, "//*[contains(text(),'Successfully uploaded')]")

WebUI.waitForElementVisible(successMsg, uploadWaitSeconds)

WebUI.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/close_upload'))

WebUI.delay(2)

WebElement uploadedFile = findDoc(bigFileName)

assert uploadedFile != null

// --- Capture the user's own storage usage as a baseline before the admin touches the quota ---
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/My_account'))

WebUI.mouseOver(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Storage used'))

WebUI.delay(2)

String usageBeforeQuotaEdit = WebUI.getAttribute(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Storage used'),
    'data-bs-content')

println('Storage usage after upload, before quota edit: ' + usageBeforeQuotaEdit)

WebUI.verifyMatch(usageBeforeQuotaEdit, '.*GB.*', true)

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

WebUI.delay(2)

// --- Log back in as admin ---
WebUI.setEncryptedText(findTestObject('Login/inputEmail'), 'CKkAs2Ee0vA=')

WebUI.setEncryptedText(findTestObject('Login/inputPassword'), 'PpFy9OM6JMUrpEOD1UO9247r7Yrm9E0x')

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(2)

assert WebUI.getWindowTitle().equals('Dashboard - PowerFolder')

WebUI.click(findTestObject('LeftNavigationIcons/account'))

WebUI.delay(2)

WebElement accountRow = findAccount(GlobalVariable.userName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(accountRow))

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/Edit_account'))

// --- Admin lowers the quota below the current usage ---
WebUI.setText(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/account_storage_overwiew'), '1')

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.waitForElementVisible(findTestObject('Accounts/QuotaWarningDialog/title_storage_warning'), 10)

String warningText = WebUI.getText(findTestObject('Accounts/QuotaWarningDialog/body_storage_warning'))

println('Storage warning dialog text: ' + warningText)

WebUI.verifyMatch(warningText, '.*lower.*', true)

WebUI.click(findTestObject('Accounts/QuotaWarningDialog/button_confirm_storage_warning'))

WebUI.waitForElementVisible(findTestObject('Accounts/alertAccountUpdated'), 15)

WebUI.delay(2)

// --- Regression checks: nothing must have been deleted or unassigned ---
WebUI.refresh()

WebUI.delay(5)

WebElement accountRowAfter = findAccount(GlobalVariable.userName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(accountRowAfter))

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/Edit_account'))

WebUI.verifyElementAttributeValue(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/account_storage_overwiew'),
    'value', '1', 5)

WebUI.click(findTestObject('Accounts/AddFolder'))

WebElement folderInAccountAfter = findFolderInAccountDialog(folderName)

assert folderInAccountAfter != null

println('Folder is still listed as owned by the account after the quota reduction - no ownerless folder created')

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/button_Cancel'))

// --- Log back in as the affected user to confirm the data is still there from their side too ---
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

WebUI.delay(2)

WebUI.setText(findTestObject('Login/inputEmail'), GlobalVariable.userEmail)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(3)

WebUI.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebUI.delay(2)

WebElement folderRowAfter = findFolderRow(folderName)

assert folderRowAfter != null

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(folderRowAfter))

WebUI.delay(2)

WebElement fileStillPresent = findDoc(bigFileName)

assert fileStillPresent != null

println('Uploaded file is still present after the quota reduction - no data loss')

// --- Expected (correct) behavior: further uploads must now be blocked ---
String smallFileName = 'small_file_' + RandomStringUtils.randomNumeric(6) + '.bin'

startUpload(smallFileName, 1024L * 1024)

TestObject quotaExceededMsg = new TestObject('quotaExceededMsg')
quotaExceededMsg.addProperty('xpath', ConditionType.EQUALS, "//*[contains(text(),'storage limit')]")

WebUI.waitForElementVisible(quotaExceededMsg, 20)

println('New upload was correctly rejected after the quota was lowered below usage')

WebUI.closeBrowser()

@Keyword
void startUpload(String fileName, long totalBytes) {
    WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

    WebUI.verifyElementClickable(findTestObject('file_objects/upload/Page_Folders - PowerFolder/Upload file'))

    WebUI.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/Upload file'))

    TestObject uploadInput = new TestObject('uploadInput')
    uploadInput.addProperty('xpath', ConditionType.EQUALS, "//input[@id='upload_input_files']")

    WebUI.waitForElementPresent(uploadInput, 10)

    WebDriver driver = DriverFactory.getWebDriver()
    WebElement uploadInputEl = driver.findElement(By.xpath("//input[@id='upload_input_files']"))

    injectDynamicFile(uploadInputEl, fileName, totalBytes)
}

@Keyword
void injectDynamicFile(WebElement inputEl, String fileName, long totalBytes) {
    long chunkBytes = Math.min(totalBytes, 8L * 1024 * 1024)

    if (chunkBytes <= 0) {
        chunkBytes = totalBytes
    }

    // Builds the file as a Blob purely in browser memory (repeating one filled
    // chunk) and injects it into the <input> via DataTransfer, so the file
    // never touches the machine running the test.
    String script = '''
        var input = arguments[0];
        var fileName = arguments[1];
        var totalBytes = arguments[2];
        var chunkBytes = arguments[3];

        var chunk = new Uint8Array(chunkBytes);
        var seed = 123456789;
        for (var i = 0; i < chunkBytes; i++) {
            seed = (seed * 1103515245 + 12345) & 0x7fffffff;
            chunk[i] = seed & 0xff;
        }

        var parts = [];
        var remaining = totalBytes;
        while (remaining > 0) {
            var take = Math.min(chunkBytes, remaining);
            parts.push(take === chunkBytes ? chunk : chunk.slice(0, take));
            remaining -= take;
        }

        var blob = new Blob(parts, { type: 'application/octet-stream' });
        var file = new File([blob], fileName, { type: 'application/octet-stream', lastModified: Date.now() });

        var dt = new DataTransfer();
        dt.items.add(file);
        input.files = dt.files;
        input.dispatchEvent(new Event('change', { bubbles: true }));
    '''

    WebUI.executeJavaScript(script, Arrays.asList(inputEl, fileName, totalBytes, chunkBytes))
}

@Keyword
WebElement findAccount(String searchKey) {
    WebDriver driver = DriverFactory.getWebDriver()

    new WebDriverWait(driver, Duration.ofSeconds(10)).until(
        ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='accounts_table']/tbody/tr[@id]")))

    String xp = "//table[@id='accounts_table']/tbody/tr[contains(@data-search-keys,'" + searchKey + "') or .//a[contains(@title,'" + searchKey + "') or contains(text(),'" + searchKey + "')]]/td[1]/span"

    return driver.findElement(By.xpath(xp))
}

@Keyword
WebElement findFolderRow(String folderName) {
    WebDriver driver = DriverFactory.getWebDriver()

    String xp = "//*[contains(@data-search-keys, '" + folderName + "')]"

    new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xp)))

    return driver.findElement(By.xpath(xp + "/td[1]/span"))
}

@Keyword
WebElement findDoc(String fileName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath("//*[contains(@data-search-keys, '" + fileName + "')]/td[1]/span"))
}

@Keyword
WebElement findFolderInAccountDialog(String folderName) {
    WebDriver driver = DriverFactory.getWebDriver()

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10))

    String xpath = "//div[@id='pica_account_folders']//tr[td[contains(normalize-space(), '" + folderName + "')]]/td[1]//span"

    return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)))
}

String getTimestamp() {
    // Numeric-only format: month/weekday names (e.g. 'MMM') are rendered in the
    // JVM's default locale, which produced folder names Selenium could not
    // find again once created (e.g. 'Juli' on a German-locale machine).
    return new Date().format('ddMMyyyy_HHmmssSSS')
}

String getRandomFolderName() {
    return 'Folder_PFS5629_' + getTimestamp()
}
