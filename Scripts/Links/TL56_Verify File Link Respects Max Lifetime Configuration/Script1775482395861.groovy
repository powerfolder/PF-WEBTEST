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
import static org.apache.commons.lang.StringUtils.isNotBlank
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import java.util.concurrent.TimeUnit as TimeUnit
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import java.util.Random as Random
import java.util.Locale as Locale

GlobalVariable.userEmail = (('user_' + RandomStringUtils.randomNumeric(4)) + '@qa-automated-webtest.com')

String Emailid = GlobalVariable.userEmail

GlobalVariable.Name = ('My_Name_' + RandomStringUtils.randomNumeric(4))

String firstName = GlobalVariable.Name

String lastName = 'nachname ' + generateRandomString(4)

String phone = generateRandomPhoneNumber()

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.OPTIONAL)

WebUI.click(findTestObject('LeftNavigationIcons/account'))

WebUI.click(findTestObject('Accounts/CreateButton'))

WebUI.click(findTestObject('Accounts/ClickCreateAccount'))

WebUI.setText(findTestObject('Accounts/InputUserOrEmail'), Emailid)

WebUI.setText(findTestObject('Accounts/InputPassword'), GlobalVariable.Pass)

WebUI.setText(findTestObject('Accounts/InputFirstName'), firstName)

WebUI.setText(findTestObject('Accounts/InputLastName'), lastName)

WebUI.setText(findTestObject('Accounts/InputPhoneNo'), phone)

WebUI.setText(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/account_storage_overwiew'), '5')

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.delay(3)

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

WebUI.setText(findTestObject('Login/inputEmail'), Emailid)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(3)

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createFolder'))

String folderName = getRandomFolderName()

WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)

WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.delay(2)

assert WebUI.getWindowTitle().equals('Folders - PowerFolder')

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createDocument'))

String filename = 'doc_' + getTimestamp()

WebUI.setText(findTestObject('Folders/inputFolderName'), filename)

WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.closeWindowIndex(1)

WebUI.delay(1)

WebUI.switchToWindowIndex(0)

WebUI.refresh()

WebElement btn = findShareButton(filename)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.click(findTestObject('Folders/shareLink'))

WebUI.click(findTestObject('Page_Folders - PowerFolder/inputValidTill'))

TestObject validTillInput = findTestObject('Page_Folders - PowerFolder/inputValidTill')

String tillDateTime = generateDateTimePlus21Days()

println('TillDateTime = ' + tillDateTime)

WebElement validTillElement = WebUI.findWebElement(validTillInput)

WebUI.executeJavaScript('\n    const input = arguments[0];\n    const value = arguments[1];\n    const nativeInputValueSetter =\n        Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, \'value\').set;\n    nativeInputValueSetter.call(input, value);\n    input.dispatchEvent(new Event(\'input\', { bubbles: true }));\n    input.dispatchEvent(new Event(\'change\', { bubbles: true }));\n    input.dispatchEvent(new Event(\'blur\', { bubbles: true }));\n', 
    [validTillElement, tillDateTime])

WebUI.delay(1)

WebUI.sendKeys(validTillInput, Keys.chord(Keys.ENTER))

WebUI.click(findTestObject('Object Repository/Folders/button_SaveSettings'))

WebUI.waitForElementVisible(findTestObject('Page_Folders - PowerFolder/Check_date_links_file'), 10)

String currentValue = WebUI.getText(findTestObject('Page_Folders - PowerFolder/Check_date_links_file'))

println(('current valid till date = [' + currentValue) + ']')

assert (currentValue != null) && (currentValue.trim() != '')

String cleanCurrentValue = currentValue.replace('until ', '').trim()

Date currentDate = new SimpleDateFormat('dd MMMM yyyy HH:mm', Locale.ENGLISH).parse(cleanCurrentValue)

Date tillDate = new SimpleDateFormat('yyyy-MM-dd\'T\'HH:mm').parse(tillDateTime)

println('currentDate = ' + currentDate)

println('tillDate    = ' + tillDate)

assert currentDate.before(tillDate)

println('✅ currentValue is before tillDateTime')

WebUI.closeBrowser(FailureHandling.STOP_ON_FAILURE)

String generateRandomString(int length) {
    String characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'

    StringBuilder randomString = new StringBuilder()

    Random random = new Random()

    for (int i = 0; i < length; i++) {
        randomString.append(characters.charAt(random.nextInt(characters.length())))
    }
    
    return randomString.toString().toLowerCase()
}

String generateRandomPhoneNumber() {
    Random random = new Random()

    return String.format('(%03d) %03d-%04d', random.nextInt(1000), random.nextInt(1000), random.nextInt(10000))
}

String getRandomFolderName() {
    String folderName = 'TF1' + getTimestamp()

    return folderName
}

String getTimestamp() {
    Date todaysDate = new Date()

    String formattedDate = todaysDate.format('ddMMMyyyyhhmmss')

    return formattedDate
}

WebElement findShareButton(String fileName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath("//*[contains(@data-search-keys, '$fileName')]/td[7]/a/span"))
}

String generateDateTimePlus21Days() {
    Calendar calendar = Calendar.getInstance()

    calendar.add(Calendar.DAY_OF_YEAR, 21)

    SimpleDateFormat sdf = new SimpleDateFormat('yyyy-MM-dd\'T\'HH:mm')

    return sdf.format(calendar.getTime())
}

