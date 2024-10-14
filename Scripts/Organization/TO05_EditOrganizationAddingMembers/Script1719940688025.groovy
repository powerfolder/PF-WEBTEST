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
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils

String Organization_name = GlobalVariable.organisationName
println('Organization_name: ' + Organization_name)

WebUI.callTestCase(findTestCase('Organization/Pre_test/Create_Org'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Organization/AddMembers'))

String emailId = (('user_' + RandomStringUtils.randomNumeric(4)) + '@test.com')

WebUI.setText(findTestObject('Organization/InputAddAccountByName'), emailId)

WebUI.sendKeys(findTestObject('Organization/InputAddAccountByName'), Keys.chord(Keys.ENTER))

WebUI.delay(2)

WebUI.click(findTestObject('Organization/AddMemberButton'))

WebUI.click(findTestObject('Organization/SaveButton'))

WebUI.delay(2)

WebUI.setText(findTestObject('Accounts/inputAccountSearch'), Organization_name)

WebDriver driver = DriverFactory.getWebDriver()

WebElement orgElement = driver.findElement(By.xpath(('//a[(@class=\'pica-name\') and (text() =\'' + Organization_name) + '\')]'))

orgElement.click()

WebUI.click(findTestObject('Organization/EditButton'), FailureHandling.OPTIONAL)

WebUI.click(findTestObject('Organization/AddMembers'), FailureHandling.OPTIONAL)

String memberName = WebUI.getText(findTestObject('Accounts/addedMember'))

WebUI.verifyEqual(memberName, emailId)

WebUI.click(findTestObject('Organization/SaveButton'))

WebUI.delay(3)
WebUI.closeBrowser()
