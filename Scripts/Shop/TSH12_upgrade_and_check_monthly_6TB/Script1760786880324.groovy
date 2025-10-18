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

WebUI.callTestCase(findTestCase('Shop/Pre_test/upgrade_monthly_6TB'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.mouseOver(findTestObject('Page_PowerFolder - shop_stripe/Size_of_storage'))

String tooltipText = WebUI.getAttribute(findTestObject('Page_PowerFolder - shop_stripe/Size_of_storage'), 'data-content')

println('Tooltip content: ' + tooltipText)

WebUI.verifyMatch(tooltipText, '.*1 TB.*', true)

WebUI.refresh()

WebUI.verifyElementClickable(findTestObject('Page_PowerFolder - shop_stripe/Invoices_6TB'))

WebUI.click(findTestObject('Page_PowerFolder - shop_stripe/Invoices_6TB'))

WebUI.delay(2)

String currentUrl = WebUI.getUrl()

WebUI.comment('L\'URL actuelle est: ' + currentUrl)

boolean isCorrectUrl = currentUrl.contains('https://shop.powerfolder.com/p/session/')

WebUI.verifyEqual(isCorrectUrl, true)

WebUI.delay(2)

WebUI.verifyElementText(findTestObject('Page_PowerFolder - shop_stripe/Size'), 'Team Cloud 6 TB')

WebUI.verifyElementText(findTestObject('Page_PowerFolder - shop_stripe/Price'), '€69.90 per month')

WebUI.click(findTestObject('Page_PowerFolder - shop_stripe/Return to PowerFolder'))

WebUI.delay(2)

WebUI.verifyEqual(WebUI.getWindowTitle(), 'Folders - PowerFolder')

WebUI.closeBrowser()

