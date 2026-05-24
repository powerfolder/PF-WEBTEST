import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.openqa.selenium.By as By
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
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

// 🔹 Step 1: Create an account before testing
WebUI.callTestCase(findTestCase('Accounts/Edit_Account/pre_test/Create_Account'), [:], FailureHandling.STOP_ON_FAILURE)

println('📧 User Email: ' + GlobalVariable.userEmail)

WebUI.refresh()
WebUI.delay(5)

// 🔹 Step 2: Find and open the account for editing
WebElement btn = findAccount(GlobalVariable.userName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/Edit_account'))

// 🔹 Step 3: Navigate to the "Notes" section
WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/a_Notes'))

// 🔹 Step 4: Add a new note
String initialNote = 'I am testing the proper functioning of taking a note.'

WebUI.setText(findTestObject('Accounts/Edit_Accounts - PowerFolder/Notes'), initialNote)

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.refresh()

WebUI.delay(2)

// 🔹 Step 5: Reopen the account
WebElement btn1 = findAccount(GlobalVariable.userName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/Edit_account'))

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/a_Notes'))

// 🔹 Step 6: Verify that the note was saved correctly
String savedNote = WebUI.getAttribute(findTestObject('Accounts/Edit_Accounts - PowerFolder/Notes'), 'value').trim()

if (savedNote.equals(initialNote)) {
    println('✅ Initial note was successfully saved: ' + savedNote)
} else {
    println('❌ ERROR: Unexpected note content: ' + savedNote)

    WebUI.takeScreenshot()

    WebUI.verifyMatch(savedNote, initialNote, true, FailureHandling.STOP_ON_FAILURE)
}

// 🔹 Step 7: Update the note
String updatedNote = 'Tested'

WebUI.setText(findTestObject('Accounts/Edit_Accounts - PowerFolder/Notes'), updatedNote)

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.closeBrowser()


WebElement findAccount(String searchKey) {
    WebDriver driver = DriverFactory.getWebDriver()

    String key = searchKey.contains('@') ? searchKey.substring(0, searchKey.indexOf('@')) : searchKey

    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10)).until(
        org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(
            By.xpath("//table[@id='accounts_table']/tbody/tr[@id]")))

    String xp = "//table[@id='accounts_table']/tbody/tr[contains(@data-search-keys,'" + key + "') or .//a[contains(@title,'" + key + "') or contains(text(),'" + key + "')]]/td[1]/span"
    return driver.findElement(By.xpath(xp))
}

