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

GlobalVariable.userName = generateRandomString(8)

GlobalVariable.userLastName = generateRandomString(8)

GlobalVariable.userEmail = generateRandomEmail().toLowerCase()

GlobalVariable.PhoneNumber = generateRandomPhoneNumber()

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.OPTIONAL)

WebUI.click(findTestObject('LeftNavigationIcons/account'))

WebUI.click(findTestObject('Accounts/CreateButton'))

WebUI.click(findTestObject('Accounts/ClickCreateAccount'))

WebUI.setText(findTestObject('Accounts/InputUserOrEmail'), GlobalVariable.userEmail)

WebUI.setText(findTestObject('Accounts/InputPassword'), GlobalVariable.Pass)

WebUI.setText(findTestObject('Accounts/InputFirstName'), GlobalVariable.userName)

WebUI.setText(findTestObject('Accounts/InputLastName'), GlobalVariable.userLastName)

WebUI.setText(findTestObject('Accounts/InputPhoneNo'), GlobalVariable.PhoneNumber)

WebUI.setText(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/account_storage_overwiew'), '5')

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/input_valid_till'))

WebUI.sendKeys(findTestObject('Accounts/Edit_Accounts - PowerFolder/input_valid_till'), Keys.chord(Keys.ENTER))

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.refresh()

WebUI.delay(2)

WebElement btn = findAccount(GlobalVariable.userEmail)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/Edit_account'))

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/input_valid_till'))

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/clear_date'))

String newDateTime = generateDateTimePlusOneYear()

WebUI.setText(findTestObject('Accounts/Edit_Accounts - PowerFolder/input_valid_till'), newDateTime)

WebUI.sendKeys(findTestObject('Accounts/Edit_Accounts - PowerFolder/input_valid_till'), Keys.chord(Keys.ENTER))

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.refresh()

String emailId = GlobalVariable.userEmail

try {
    WebDriver driver = DriverFactory.getWebDriver()

    String dynamicXPath = ('//*[contains(@data-search-keys, \'' + emailId) + '\')]/td[7]/span'

    WebElement dateElement = driver.findElement(By.xpath(dynamicXPath))

    String actualDate = dateElement.getText().trim()

    String expectedDate = getExpectedDate()

    println('📌 Retrieved date: ' + actualDate)

    println('📆 Expected date: ' + expectedDate)

    if (!(actualDate.equals(expectedDate))) {
        throw new Exception(((('❌ ERROR: The displayed date \'' + actualDate) + '\' does not match \'') + expectedDate) + 
        '\'')
    } else {
        println('✅ Verification successful: The displayed date correctly matches +1 year')
    }
}
catch (Exception e) {
    println(e.getMessage())
} 

WebUI.closeBrowser()

String getExpectedDate() {
    Calendar calendar = Calendar.getInstance()

    calendar.add(Calendar.YEAR, 1)

    SimpleDateFormat sdf = new SimpleDateFormat('d MMMM yyyy', Locale.ENGLISH)

    return sdf.format(calendar.getTime())
}

String generateDateTimePlusOneYear() {
    Calendar calendar = Calendar.getInstance()

    calendar.add(Calendar.YEAR, 1)

    SimpleDateFormat sdf = new SimpleDateFormat('dd/MM/yyyy HH:mm')

    return sdf.format(calendar.getTime())
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
    return generateRandomString(8) + '@yoemail.com'
}

String generateRandomPhoneNumber() {
    Random random = new Random()

    return String.format('(%03d) %03d-%04d', random.nextInt(1000), random.nextInt(1000), random.nextInt(10000))
}

WebElement findAccount(String emailId) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + emailId) + '\')]/td[1]/span'))
}

