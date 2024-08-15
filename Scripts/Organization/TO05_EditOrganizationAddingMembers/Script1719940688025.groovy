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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

WebUI.verifyEqual(WebUI.getWindowTitle(), 'Dashboard - PowerFolder')
assert WebUI.getWindowTitle().equals('Dashboard - PowerFolder')
WebUI.click(findTestObject('Organization/SelectOrganization'))
WebUI.click(findTestObject('Organization/DropDownToggle'))
WebUI.click(findTestObject('Organization/CreateOrganization'))
WebUI.delay(3)
String lan = GlobalVariable.LANG
if(!lan.equals('GERMAN')) {
WebUI.verifyEqual(WebUI.getText(findTestObject('Organization/VerifyCreateOrganization')), 'Create a new Organization',  FailureHandling.CONTINUE_ON_FAILURE)
}else {
	WebUI.verifyEqual(WebUI.getText(findTestObject('Organization/VerifyCreateOrganization')), 'Organisation neu erstellen',  FailureHandling.CONTINUE_ON_FAILURE)
	
}
String orgName = 'TO'+generateRandomString(5)
WebUI.setText(findTestObject('Organization/InputName'), orgName)
WebUI.setText(findTestObject('Organization/InputMaxNumber'), "10")
WebUI.setText(findTestObject('Organization/InputQuota'), "2")
def currentDate = new Date()
// Format the date and time as per your requirement
def dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a")
def formattedDate = dateFormat.format(currentDate)
WebUI.click(findTestObject('Organization/InputValidFrom'))
def calendar = Calendar.getInstance()
calendar.setTime(currentDate)
calendar.add(Calendar.DAY_OF_MONTH, 3)
def futureDate = calendar.getTime()
// Set the date and time with the timestamp plus 3 days
//WebUI.setText(findTestObject('Organization/InputValidtill'), dateFormat.format(futureDate))
WebUI.setText(findTestObject('Organization/EnterNotes'), "AutomationNotes")
WebUI.delay(3)
WebUI.click(findTestObject('Organization/SaveButton'))
WebUI.delay(10)
//WebUI.click(findTestObject('Organization/SelectCreatedOrganization'))
WebDriver driver = DriverFactory.getWebDriver()
orgElement = driver.findElement(By.xpath("//a[(@class='pica-name') and (text() ='"+orgName+"')]"))
orgElement.click()
WebUI.click(findTestObject('Organization/EditButton'),FailureHandling.OPTIONAL )
if(!lan.equals('GERMAN')) {
WebUI.verifyEqual(WebUI.getText(findTestObject('Organization/VerifyEditPageHeader')), 'Edit Organization',  FailureHandling.CONTINUE_ON_FAILURE)
}else {
	WebUI.verifyEqual(WebUI.getText(findTestObject('Organization/VerifyEditPageHeader')), 'Organisation bearbeitet',  FailureHandling.CONTINUE_ON_FAILURE)
}
WebUI.click(findTestObject('Organization/AddMembers'))
WebUI.setText(findTestObject('Organization/InputAddAccountByName'), "ajit@thoughtcoders.com")
WebUI.sendKeys(findTestObject('Organization/InputAddAccountByName'), Keys.chord(Keys.ENTER))
WebUI.click(findTestObject('Organization/AddMemberButton'))
WebUI.click(findTestObject('Organization/SaveButton'))
WebUI.setText(findTestObject('Accounts/inputAccountSearch'),orgName )
orgElement = driver.findElement(By.xpath("//a[(@class='pica-name') and (text() ='"+orgName+"')]"))
//orgElement.click()
WebUI.click(findTestObject('Organization/EditButton'), FailureHandling.OPTIONAL)
WebUI.click(findTestObject('Organization/AddMembers'),  FailureHandling.OPTIONAL)
String memberName = WebUI.getText(findTestObject('Accounts/addedMember'))
WebUI.verifyEqual(memberName, "ajit@thoughtcoders.com")
WebUI.click(findTestObject('Organization/SaveButton'))
WebUI.delay(3)
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
