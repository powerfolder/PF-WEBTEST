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
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import java.util.Arrays

WebUI.callTestCase(findTestCase('Accounts/Edit_Account/pre_test/Create_Account'), [:], FailureHandling.STOP_ON_FAILURE)
String ownerEmail = GlobalVariable.userEmail

String memberEmail = ('sfs40_' + RandomStringUtils.randomNumeric(6)) + '@qa-automated-webtest.com'
WebUI.click(findTestObject('Accounts/CreateButton'))
WebUI.click(findTestObject('Accounts/ClickCreateAccount'))
WebUI.setText(findTestObject('Accounts/InputUserOrEmail'), memberEmail)
WebUI.setText(findTestObject('Accounts/InputPassword'), GlobalVariable.Pass)
WebUI.setText(findTestObject('Accounts/InputFirstName'), 'SFS40Member')
WebUI.setText(findTestObject('Accounts/InputLastName'), RandomStringUtils.randomAlphabetic(6))
WebUI.setText(findTestObject('Accounts/InputPhoneNo'), '(030) 1234567')
WebUI.setText(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/account_storage_overwiew'), '5')
WebUI.click(findTestObject('Accounts/SaveButton'))
WebUI.delay(2)

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))
WebUI.setText(findTestObject('Login/inputEmail'), ownerEmail)
WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)
WebUI.click(findTestObject('Login/loginSubmit'))
WebUI.delay(3)
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

String tlfName = 'SFS40_' + RandomStringUtils.randomAlphanumeric(6)
WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))
WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Folders/inputFolderName'), tlfName)
WebUI.click(findTestObject('Folders/buttonOK'))

String subAName = 'SFS40_SubA_' + RandomStringUtils.randomAlphanumeric(6)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))
WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), subAName)
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebUI.click(findTestObject('Links/share_icon_inside_folder'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/folder_share_permission_dropdown_toggle'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/folder_share_permission_r_w'))
WebUI.setText(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), memberEmail)
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/buttonAddEmail'))
WebUI.click(findTestObject('Share/close_button_folder_share_mail'))

double ownerUsageBeforeBytes = readOwnStorageBytes()
println('Owner storage used before upload (bytes): ' + ownerUsageBeforeBytes)

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))
WebUI.setText(findTestObject('Login/inputEmail'), memberEmail)
WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)
WebUI.click(findTestObject('Login/loginSubmit'))
WebUI.delay(3)
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

WebElement invitationRow = findRow(subAName)
WebUI.verifyEqual(invitationRow.isDisplayed(), true)
WebElement invitationLink = findFolder(subAName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(invitationLink))
WebUI.verifyElementClickable(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))

double memberUsageBeforeBytes = readOwnStorageBytes()
println('Member storage used before upload (bytes): ' + memberUsageBeforeBytes)

WebUI.click(findTestObject('LeftNavigationIcons/folders'))
WebElement subARowMember = findFolder(subAName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(subARowMember))

String bigFileName = 'sfs40_' + RandomStringUtils.randomAlphanumeric(6) + '.bin'
long bigFileBytes = 50L * 1024 * 1024

startUpload(bigFileName, bigFileBytes)

TestObject successBar = new TestObject('successBar')
successBar.addProperty('xpath', ConditionType.EQUALS, "//*[contains(@class,'progress-bar-success')]")
WebUI.waitForElementPresent(successBar, 120)
WebUI.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/close_upload'))
WebUI.delay(2)

WebElement uploadedFile = findDoc(bigFileName)
WebUI.verifyEqual(uploadedFile != null, true)

double memberUsageAfterBytes = readOwnStorageBytes()
println('Member storage used after upload (bytes): ' + memberUsageAfterBytes)
WebUI.verifyEqual(Math.abs(memberUsageAfterBytes - memberUsageBeforeBytes) < (bigFileBytes * 0.5), true)

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))
WebUI.setText(findTestObject('Login/inputEmail'), ownerEmail)
WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)
WebUI.click(findTestObject('Login/loginSubmit'))
WebUI.delay(3)

double ownerUsageAfterBytes = readOwnStorageBytes()
println('Owner storage used after upload (bytes): ' + ownerUsageAfterBytes)

double ownerDeltaBytes = ownerUsageAfterBytes - ownerUsageBeforeBytes
println('Owner storage delta (bytes): ' + ownerDeltaBytes)
WebUI.verifyEqual(ownerDeltaBytes >= (bigFileBytes * 0.9), true)

WebUI.click(findTestObject('LeftNavigationIcons/folders'))

WebElement tlfRowAtRoot = findRow(tlfName)
WebUI.verifyEqual(tlfRowAtRoot.isDisplayed(), true)

TestObject subAAtRoot = new TestObject('subAAtRoot')
subAAtRoot.addProperty('xpath', ConditionType.EQUALS, "//*[contains(@data-search-keys, '" + subAName + "')]")
WebUI.verifyElementNotPresent(subAAtRoot, 5)

WebUI.closeBrowser()

double readOwnStorageBytes() {
    WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
    WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/My_account'))
    WebUI.verifyElementVisible(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Page_Profile - PowerFolder/lang_Overview'),
        FailureHandling.CONTINUE_ON_FAILURE)
    WebUI.mouseOver(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Storage used'))
    WebUI.delay(2)
    String tooltipText = WebUI.getAttribute(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Storage used'), 'data-bs-content')
    return parseUsedBytes(tooltipText)
}

double parseUsedBytes(String tooltipText) {
    def matcher = (tooltipText =~ /(\d+(?:\.\d+)?)\s*(bytes|KB|MB|GB|TB|PB)/)
    if (!matcher.find()) {
        return 0.0d
    }
    double value = Double.parseDouble(matcher.group(1))
    String unit = matcher.group(2)
    Map<String, Double> multipliers = ['bytes': 1.0d, 'KB': 1024.0d, 'MB': 1024.0d * 1024, 'GB': 1024.0d * 1024 * 1024,
        'TB': 1024.0d * 1024 * 1024 * 1024, 'PB': 1024.0d * 1024 * 1024 * 1024 * 1024]
    return value * multipliers[unit]
}

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

void injectDynamicFile(WebElement inputEl, String fileName, long totalBytes) {
    long chunkBytes = Math.min(totalBytes, 8L * 1024 * 1024)
    if (chunkBytes <= 0) {
        chunkBytes = totalBytes
    }

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

WebElement findFolder(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    By locator = By.xpath("//td[2]/span/a[contains(text(),'" + name + "')]")
    new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.presenceOfElementLocated(locator))
    return driver.findElement(locator)
}

WebElement findRow(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    By locator = By.xpath("//*[contains(@data-search-keys, '" + name + "')]/td[1]/span")
    new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.presenceOfElementLocated(locator))
    return driver.findElement(locator)
}

WebElement findDoc(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    By locator = By.xpath("//*[contains(@data-search-keys, '" + name + "')]/td[1]/span")
    new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.presenceOfElementLocated(locator))
    return driver.findElement(locator)
}
