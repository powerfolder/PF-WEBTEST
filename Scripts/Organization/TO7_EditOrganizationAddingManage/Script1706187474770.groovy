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
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import java.text.SimpleDateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import org.openqa.selenium.Keys as Keys
WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.OPTIONAL)

String folderName = CustomKeywords.'utility.helper.getRandomFolderName'()

WebUI.click(findTestObject('Organization/SelectOrganization'))
WebUI.click(findTestObject('Organization/DropDownToggle'))
WebUI.click(findTestObject('Organization/CreateOrganization'))
WebUI.verifyEqual(WebUI.getText(findTestObject('Organization/VerifyCreateOrganization')), 'Create a new Organization',  FailureHandling.CONTINUE_ON_FAILURE)
WebUI.delay(3)
WebUI.setText(findTestObject('Organization/InputName'), folderName)
WebUI.setText(findTestObject('Organization/InputMaxNumber'), "10")
WebUI.setText(findTestObject('Organization/InputQuota'), "2")
def currentDate = new Date()
// Format the date and time as per your requirement
def dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a")
def formattedDate = dateFormat.format(currentDate)
WebUI.click(findTestObject('Organization/InputValidFrom'))
// Add 3 days to the current date
def calendar = Calendar.getInstance()
calendar.setTime(currentDate)
calendar.add(Calendar.DAY_OF_MONTH, 3)
def futureDate = calendar.getTime()
// Set the date and time with the timestamp plus 3 days
WebUI.setText(findTestObject('Organization/InputValidtill'), dateFormat.format(futureDate))
WebUI.setText(findTestObject('Organization/EnterNotes'), "TO7 AutomationNotes")

WebUI.click(findTestObject('Organization/SaveButton'))
WebUI.delay(6)
WebDriver driver = DriverFactory.getWebDriver()
WebElement folderNameElement = driver.findElement(By.xpath("(//a[normalize-space()='$folderName'])[1]/ancestor::td"))
folderNameElement.click()
//WebUI.click(findTestObject('Organization/SelectCreatedOrganization'))
WebUI.delay(3) 
String editPopHeadingText =WebUI.getText(findTestObject('Organization/getEditOrganization'))
WebUI.verifyEqual(editPopHeadingText, 'Edit Organization',  FailureHandling.CONTINUE_ON_FAILURE)
WebUI.click(findTestObject('Organization/ManageTab'))
WebUI.click(findTestObject('Organization/defaultCancel'))
WebUI.click(findTestObject('Organization/sortByName'))
String actualFolderName = folderNameElement.getText()
WebUI.verifyEqual(actualFolderName, folderName, FailureHandling.STOP_ON_FAILURE)
WebUI.scrollToElement(findTestObject('Organization/SelectCreatedOrganization'), 30, FailureHandling.OPTIONAL)
WebUI.click(findTestObject('Organization/checkFirstOrganization'))
WebUI.click(findTestObject('Organization/Deletebutton'))
WebUI.delay(11)
//WebUI.verifyEqual(WebUI.getText(findTestObject('Organization/VerfiyDeleteMsg')), "Do you really want to delete ${folderName} with all members and folders?", FailureHandling.CONTINUE_ON_FAILURE)
 
WebUI.scrollToElement(findTestObject('Organization/SelectYesButton'), 30, FailureHandling.CONTINUE_ON_FAILURE)
WebUI.click(findTestObject('Organization/SelectYesButton'))

WebUI.verifyEqual(WebUI.getText(findTestObject('Organization/VerifyToastMsg')), 'Organization deleted')
WebUI.closeBrowser()

def String getCurrentDate() {
SimpleDateFormat dateFormat = new SimpleDateFormat("MM/DD/yyyy hh:mm aa");
String formattedDate = dateFormat.format(new Date()).toString();
System.out.println(formattedDate);
return formattedDate
}

def String newDate(int n) {
SimpleDateFormat sdf = new SimpleDateFormat("MM/DD/yyyy hh:mm aa");
Calendar c = Calendar.getInstance();
c.setTime(new Date()); // Using today's date
c.add(Calendar.DATE, n); // Adding 5 days
String output = sdf.format(c.getTime());
System.out.println(output);
return output
}