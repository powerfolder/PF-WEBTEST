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

// Function to get a timestamp
// Global variables
GlobalVariable.userEmail = (('user_' + RandomStringUtils.randomNumeric(4)) + '@test.com')

String Emailid = GlobalVariable.userEmail

GlobalVariable.Name = ('My_Name_' + RandomStringUtils.randomNumeric(4))

String firstName = GlobalVariable.Name

String lastName = 'nachname ' + generateRandomString(4)

// Generate a random phone number
String phone = generateRandomPhoneNumber()

// Test plan
WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.OPTIONAL)

WebUI.click(findTestObject('LeftNavigationIcons/account'))

WebUI.click(findTestObject('Accounts/CreateButton'))

WebUI.click(findTestObject('Accounts/ClickCreateAccount'))

WebUI.setText(findTestObject('Accounts/InputUserOrEmail'), Emailid)

WebUI.setText(findTestObject('Accounts/InputPassword'), GlobalVariable.Pass)

// Generate the current date and time with two months added
WebUI.click(findTestObject('My_Account/Page_Accounts - PowerFolder/input_Active_account_valid_till'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Page_Accounts - PowerFolder/clear_date'))

String newDateTime = generateDateTimePlusOneYear()

WebUI.delay(2)

WebUI.setText(findTestObject('My_Account/Page_Accounts - PowerFolder/input_Active_account_valid_till'), newDateTime)

WebUI.sendKeys(findTestObject('My_Account/Page_Accounts - PowerFolder/input_Active_account_valid_till'), Keys.chord(Keys.ENTER))

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.delay(3)

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

WebUI.setText(findTestObject('Login/inputEmail'), Emailid)

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(2)

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/My_account'))

WebUI.mouseOver(findTestObject('My_Account/Overview/Page_Profile - PowerFolder/span_date_overview'))

// Retrieve the displayed date from 'span_date_overview'
String displayedDateStr = WebUI.getText(findTestObject('My_Account/Overview/Page_Profile - PowerFolder/span_date_overview'))

println('Displayed date: ' + displayedDateStr)

// Convert the string to a Date object with the correct format
SimpleDateFormat sdfDisplayed = new SimpleDateFormat('dd MMMM yyyy', Locale.ENGLISH)

Date displayedDate = sdfDisplayed.parse(displayedDateStr)

// Generate the current date plus one month
Calendar calendar = Calendar.getInstance()

calendar.add(Calendar.MONTH, 1)

Date currentDatePlusOneMonth = calendar.getTime()

// Compare the dates
if (displayedDate.after(currentDatePlusOneMonth)) {
    WebUI.comment('The date in span_date_overview is greater than the current date plus one month.')
} else {
    WebUI.comment('The date in span_date_overview is less than or equal to the current date plus one month.')
}

WebUI.delay(2)

WebUI.closeBrowser()

String getTimestamp() {
    Date todaysDate = new Date()

    SimpleDateFormat sdf = new SimpleDateFormat('ddMMMyyyyHHmmss')

    String formattedDate = sdf.format(todaysDate)

    return formattedDate
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

String generateRandomPhoneNumber() {
    Random random = new Random()

    return String.format('(%03d) %03d-%04d', random.nextInt(1000), random.nextInt(1000), random.nextInt(10000))
}

String generateDateTimePlusOneYear() {
    Calendar calendar = Calendar.getInstance()

    calendar.add(Calendar.YEAR, 1)

    SimpleDateFormat sdf = new SimpleDateFormat('MM/dd/yyyy HH:mm')

    return sdf.format(calendar.getTime())
}

