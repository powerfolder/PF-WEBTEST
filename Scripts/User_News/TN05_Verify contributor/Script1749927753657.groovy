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

WebUI.callTestCase(findTestCase('User_News/Pre_test/create_user_file'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('News_User/Page_News - PowerFolder/News'))

WebUI.mouseOver(findTestObject('News_User/Page_News - PowerFolder/file_wpath'))

String tooltipText = WebUI.getAttribute(findTestObject('News_User/Page_News - PowerFolder/file_wpath'), 'data-original-title')

println('Tooltip content: ' + tooltipText)

String expectedAdminEmail = GlobalVariable.userEmail 

// Extraire l’e-mail après "modified by"
String actualEmail = tooltipText.split("modified by ")[1].trim()

println("Email from tooltip: " + actualEmail)
println("Expected admin email: " + expectedAdminEmail)

// Comparaison
assert actualEmail == expectedAdminEmail : "❌ Email mismatch in tooltip."

println("✅ Tooltip shows the correct contributor email.")

WebUI.delay(3)

WebUI.closeBrowser()






