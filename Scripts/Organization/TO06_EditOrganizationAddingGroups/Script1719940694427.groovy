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
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


String Organization_name = GlobalVariable.organisationName

println('Organization_name: ' + Organization_name)

WebUI.callTestCase(findTestCase('Organization/Pre_test/Create_Org'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Organization/AddGroups'))

GlobalVariable.GroupName = ('Group_' + RandomStringUtils.randomNumeric(4))

String Groupname = GlobalVariable.GroupName

WebUI.setText(findTestObject('Organization/InputgroupName'),Groupname)

WebUI.delay(2)

WebUI.click(findTestObject('Organization/SelectGroup'))

WebUI.delay(2)

TestObject dynamicObject = new TestObject().addProperty('xpath', ConditionType.EQUALS, '//body/div[2]/div[1]/div[2]/div[3]/div/div/div[3]/div/div[2]/button[1]')
WebUI.click(dynamicObject)

WebUI.delay(3)

WebUI.refresh()

Organization_name = GlobalVariable.organisationName

println('Organization_name: ' + Organization_name)

WebElement btn1 = findORG(Organization_name)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

WebUI.click(findTestObject('Organization/AddGroups'))

String actualGroup = WebUI.getText(findTestObject('Organization/Page_Organizations - PowerFolder/Verify_Group_Org')).toLowerCase()
String expectedGroup = Groupname.toLowerCase()

WebUI.verifyEqual(actualGroup, expectedGroup, FailureHandling.CONTINUE_ON_FAILURE)

WebUI.delay(3)

WebUI.click(findTestObject('Organization/Page_Organizations - PowerFolder/button_Cancel_organization'))

WebUI.closeBrowser() 

WebElement findORG(String Organization_name) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(By.xpath(('//a[contains(text(),\'' + Organization_name) + '\')]'))
}

