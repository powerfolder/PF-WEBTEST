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
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import org.openqa.selenium.WebElement as WebElement

WebUI.callTestCase(findTestCase('Group/Create group'), [:], FailureHandling.STOP_ON_FAILURE)

WebElement btn = CustomKeywords.'group.GroupFinder.findGroup'(GlobalVariable.GroupName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.click(findTestObject('Group_Objects/Page_Groups - PowerFolder/Edit group'))
// Generate a random name
String groupNameModified = 'Group_Modified' + RandomStringUtils.randomNumeric(4)

WebUI.setText(findTestObject('Page_Groups - PowerFolder/type the changed name'), groupNameModified)

WebUI.setText(findTestObject('Group_Objects/Page_Groups - PowerFolder/Note of group'), 'the name of the group has been changed')

WebUI.click(findTestObject('Group_Objects/Page_Groups - PowerFolder/button_Save'))

