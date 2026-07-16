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

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

// Utiliser le même fuseau horaire que la machine et le navigateur
ZoneId applicationZone = ZoneId.systemDefault()

println('🌍 Time zone used: ' + applicationZone)

// Format complet pour les logs
DateTimeFormatter dateTimeFormatter =
	DateTimeFormatter.ofPattern(
		'dd MMMM yyyy HH:mm',
		Locale.ENGLISH
	)

// Format retourné par l'interface, par exemple : 15:45
DateTimeFormatter timeFormatter =
	DateTimeFormatter.ofPattern('HH:mm')

// STEP 1: Save current date before test
LocalDateTime startDate =
	LocalDateTime.now(applicationZone)
		.truncatedTo(ChronoUnit.MINUTES)

println(
	'🕒 Start date: ' +
	startDate.format(dateTimeFormatter)
)

// STEP 2: Run the test
WebUI.callTestCase(
	findTestCase('User_News/Pre_test/create_user_file'),
	[:],
	FailureHandling.STOP_ON_FAILURE
)

WebUI.click(
	findTestObject('News_User/Page_News - PowerFolder/News')
)

// STEP 3: Get the time displayed on the UI
TestObject creationDateObject =
	findTestObject(
		'News_User/Page_News - PowerFolder/date_of_file_creation'
	)

WebUI.mouseOver(creationDateObject)

String dateText =
	WebUI.getText(creationDateObject).trim()

println(
	'🗓️ Displayed time: ' +
	dateText
)

// Convert the displayed time, for example "15:45"
LocalTime displayedTime =
	LocalTime.parse(dateText, timeFormatter)

// Add today's date because the UI only displays the time
LocalDateTime endDate =
	LocalDateTime.of(
		LocalDate.now(applicationZone),
		displayedTime
	)

println(
	'🗓️ Complete displayed date: ' +
	endDate.format(dateTimeFormatter)
)

// STEP 4: Compare the two dates
long diffInMinutes =
	ChronoUnit.MINUTES.between(
		startDate,
		endDate
	)

println(
	'⏱️ Difference: ' +
	diffInMinutes +
	' minute(s)'
)

// Accept a delay between 0 and 5 minutes
if (diffInMinutes >= 0 && diffInMinutes <= 5) {

	println(
		'✅ The displayed creation time is valid.'
	)

} else {

	println(
		'❌ ERROR: The displayed creation time is invalid.'
	)

	println(
		'Start: ' +
		startDate.format(dateTimeFormatter) +
		' | Displayed: ' +
		endDate.format(dateTimeFormatter)
	)

	WebUI.takeScreenshot()

	assert false :
		'Invalid creation time. Difference: ' +
		diffInMinutes +
		' minute(s).'
}

WebUI.delay(2)

WebUI.closeBrowser()