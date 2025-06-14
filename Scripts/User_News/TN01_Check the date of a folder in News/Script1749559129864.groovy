import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
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
import java.text.SimpleDateFormat as SimpleDateFormat
import java.util.Date as Date
import java.util.Locale as Locale

// STEP 1: Save current date before test (in UTC+2)
SimpleDateFormat sdf = new SimpleDateFormat('dd MMMM yyyy HH:mm', Locale.ENGLISH)

sdf.setTimeZone(TimeZone.getTimeZone('Europe/Paris' // Force timezone to UTC+2
        ))

Date startDate = sdf.parse(sdf.format(new Date()))

println('🕒 Start date (UTC+2): ' + sdf.format(startDate))

// STEP 2: Run the test
WebUI.callTestCase(findTestCase('User_News/Pre_test/create_user_file'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('News_User/Page_News - PowerFolder/News'))

// STEP 3: Get date displayed on UI and compare
WebUI.mouseOver(findTestObject('News_User/Page_News - PowerFolder/date_of_file_creation'))

String dateText = WebUI.getText(findTestObject('News_User/Page_News - PowerFolder/date_of_file_creation'))

println('🗓️ Displayed date: ' + dateText)

Date endDate = sdf.parse(dateText)

// STEP 4: Compare the two dates
long diffInMillis = endDate.getTime() - startDate.getTime()

long diffInMinutes = diffInMillis / (60 * 1000)

if (diffInMinutes >= 0) {
    println('✅ The displayed date is equal to or later than the start date.')

    WebUI.delay(2)

    println(('⏱️ Difference: ' + diffInMinutes) + ' minute(s)')
} else {
    println('❌ ERROR: Displayed date is earlier than the start date!')

    println((('Start: ' + sdf.format(startDate)) + ' | Displayed: ') + sdf.format(endDate))

    assert false : 'File creation date is invalid.'
}

WebUI.delay(2)

WebUI.closeBrowser()

