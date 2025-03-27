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
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.By as By
import java.text.SimpleDateFormat as SimpleDateFormat
import java.util.Calendar as Calendar
import java.util.Date as Date
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils

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

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.OPTIONAL)

GlobalVariable.userName = generateRandomString(8)

GlobalVariable.userLastName = generateRandomString(8)

GlobalVariable.userEmail = generateRandomEmail().toLowerCase()

GlobalVariable.PhoneNumber = generateRandomPhoneNumber()

WebUI.click(findTestObject('LeftNavigationIcons/account'))

WebUI.click(findTestObject('Accounts/CreateButton'))

WebUI.click(findTestObject('Accounts/ClickCreateAccount'))

WebUI.setText(findTestObject('Accounts/InputUserOrEmail'), GlobalVariable.userEmail)

WebUI.setText(findTestObject('Accounts/InputPassword'), GlobalVariable.Pass)

//WebUI.setText(findTestObject('Accounts/InputFirstName'), GlobalVariable.userName)

//WebUI.setText(findTestObject('Accounts/InputLastName'), GlobalVariable.userLastName)

WebUI.setText(findTestObject('Accounts/InputPhoneNo'), GlobalVariable.PhoneNumber)

WebUI.setText(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/account_storage_overwiew'), '5')

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.refresh()

WebUI.delay(2)

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

WebUI.click(findTestObject('Organization/SelectOrganization'))

WebUI.click(findTestObject('Organization/DropDownToggle'))

WebUI.click(findTestObject('Organization/CreateOrganization'))

GlobalVariable.organisationName = ('Organisation_' + RandomStringUtils.randomNumeric(4))

Organization_name = GlobalVariable.organisationName

WebUI.setText(findTestObject('Organization/InputName'), Organization_name)

WebUI.setText(findTestObject('Organization/InputMaxNumber'), '10')

WebUI.setText(findTestObject('Organization/Page_Organizations - PowerFolder/organization_storage'), '5')

String FromDateTime = generateDateTimePlusOneMinute()

WebUI.setText(findTestObject('Organization/InputValidFrom'), FromDateTime)

WebUI.sendKeys(findTestObject('Organization/InputValidFrom'), Keys.chord(Keys.TAB))

String TillDateTime = generateDateTimePlusOneYear()

WebUI.setText(findTestObject('Organization/InputValidtill'), TillDateTime)

WebUI.sendKeys(findTestObject('Organization/InputValidtill'), Keys.chord(Keys.TAB))

WebUI.setText(findTestObject('Organization/EnterNotes'), 'AutomationNotes')

WebUI.click(findTestObject('Organization/SaveButton'))

WebUI.refresh()

WebUI.delay(3)

WebElement btn = findORG(Organization_name)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

String getRandomFolderName() {
	String folderName = 'Folder' + getTimestamp()

	return folderName
}

String getTimestamp() {
	Date todaysDate = new Date()

	String formattedDate = todaysDate.format('dd_MMMyyyyhhmmss')

	return formattedDate
}

String generateDateTimePlusOneMinute() {
	Calendar calendar = Calendar.getInstance()

	calendar.add(Calendar.MINUTE, 1)

	SimpleDateFormat sdf = new SimpleDateFormat('MM/dd/yyyy HH:mm')

	return sdf.format(calendar.getTime())
}

String generateDateTimePlusOneYear() {
	Calendar calendar = Calendar.getInstance()

	calendar.add(Calendar.YEAR, 1)

	SimpleDateFormat sdf = new SimpleDateFormat('MM/dd/yyyy HH:mm')

	return sdf.format(calendar.getTime())
}

WebElement findORG(String Organization_name) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(By.xpath(('//a[contains(text(),\'' + Organization_name) + '\')]'))
}

