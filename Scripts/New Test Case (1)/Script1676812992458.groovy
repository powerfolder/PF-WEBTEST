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

WebUI.openBrowser('')

WebUI.navigateToUrl('http://localhost:8080/getlink/fiWQSVWDdWXWvPK9T4CUCJK7/dir2')

WebUI.closeBrowser()

WebUI.openBrowser('')

WebUI.navigateToUrl('http://localhost:8080/login?originalURI=folderstable')

WebUI.closeBrowser()

WebUI.openBrowser('')

WebUI.navigateToUrl('http://localhost:8080/login?originalURI=folderstable')

WebUI.setText(findTestObject('Object Repository/input_Log in_Username'), 'admin')

WebUI.setEncryptedText(findTestObject('Object Repository/input_Recover password_Password'), '8SQVv/p9jVScEs4/2CZsLw==')

WebUI.click(findTestObject('Object Repository/input_Recover password_Login'))

WebUI.click(findTestObject('Object Repository/button_OK I understand'))
WebUI.click(findTestObject('Object Repository/div_Invite users and groups or create a lin_ba0936'))


WebUI.click(findTestObject('Object Repository/span_Admin_pica-glyph glyphicons glyphicons_d6270d'))

WebUI.click(findTestObject('Object Repository/div_Invite users and groups or create a lin_ba0936'))

WebUI.click(findTestObject('Object Repository/div_Invite users and groups or create a lin_ba0936'))

WebUI.click(findTestObject('Object Repository/div_Invite users and groups or create a lin_ba0936'))

WebUI.click(findTestObject('Object Repository/div_Invite users and groups or create a lin_ba0936'))

