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
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import com.kms.katalon.core.configuration.RunConfiguration
import org.openqa.selenium.By as By
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
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.nio.file.Path;
import java.nio.file.Files;
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
String firstName = generateRandomString(8)
String lastName = generateRandomString(8)
String emailId = generateRandomEmail()
Emailid = emailId.toLowerCase();
String phone = generateRandomPhoneNumber()

WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.OPTIONAL)
WebUI.click(findTestObject('LeftNavigationIcons/account'))
WebUI.click(findTestObject('Accounts/CreateButton'))
WebUI.click(findTestObject('Accounts/ClickCreateAccount'))
WebUI.setText(findTestObject('Accounts/InputUserOrEmail'),emailId)

WebUI.setText(findTestObject('Accounts/InputPassword'),"Alexa@131190")
WebUI.setText(findTestObject('Accounts/InputFirstName'),firstName)
WebUI.setText(findTestObject('Accounts/InputLastName'),lastName)
WebUI.setText(findTestObject('Accounts/InputPhoneNo'),phone)
WebUI.setText(findTestObject('Accounts/InputQuota'),"5")
def currentDate = new Date()
def dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a")
def formattedDate = dateFormat.format(currentDate)
WebUI.setText(findTestObject('Accounts/InputTIllDate'), formattedDate)
WebUI.sendKeys(findTestObject('Accounts/InputTIllDate'), Keys.chord(Keys.TAB))
WebUI.click(findTestObject('Accounts/SaveButton'))
WebUI.delay(4)
WebDriver driver = DriverFactory.getWebDriver()
WebElement ClickOnAccount =  driver.findElement(By.xpath("(//a[normalize-space()='$firstName $lastName'])[1]/ancestor::td"))
WebUI.delay(5)
ClickOnAccount.click()
WebUI.click(findTestObject('Accounts/editAccount'))
WebUI.verifyEqual(WebUI.getText(findTestObject('Accounts/VerifyEditPage')), "Edit Account", FailureHandling.CONTINUE_ON_FAILURE)
WebUI.click(findTestObject('Accounts/ClickOnAvatar'))
WebUI.click(findTestObject('Accounts/ChangeButton'))
WebUI.verifyEqual(WebUI.getText(findTestObject('Accounts/VerifyAvatar')), "Select avatar file for upload", FailureHandling.CONTINUE_ON_FAILURE)
String projDir = RunConfiguration.getProjectDir()
String fname = projDir + "/Data Files/priflePic.jpg"
WebUI.uploadFile(findTestObject('Accounts/AddFileButton'), fname)
WebUI.click(findTestObject('Accounts/CloseButton'))
WebUI.click(findTestObject('Accounts/SaveButton'))
WebUI.setText(findTestObject('Accounts/inputAccountSearch'),firstName )
WebUI.delay(10)
WebElement AccountName =  driver.findElement(By.xpath("(//a[normalize-space()='$firstName $lastName'])[1]/ancestor::tr/td[1]/img"))
WebUI.delay(2)
AccountName.click()
WebUI.click(findTestObject('Accounts/DeleteButton'))
WebUI.delay(4)

String actualText = WebUI.getText(findTestObject('Accounts/VerifyDeleteMsg'))
String expectedText = "Do you really want to delete ${emailId}?"

WebUI.verifyEqual(actualText, expectedText, FailureHandling.STOP_ON_FAILURE)
WebUI.click(findTestObject('Accounts/YesButton'))
WebUI.closeBrowser()

String generateRandomString(int length) {
	String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
	StringBuilder randomString = new StringBuilder()
	Random random = new Random()

	for (int i = 0; i < length; i++) {
		randomString.append(characters.charAt(random.nextInt(characters.length())))
	}

	return randomString.toString().toLowerCase()
}

String generateRandomEmail() {
	return generateRandomString(8) + "@yopmail.com"
}

String generateRandomPhoneNumber() {
	Random random = new Random()
	return String.format("(%03d) %03d-%04d", random.nextInt(1000), random.nextInt(1000), random.nextInt(10000))
}
