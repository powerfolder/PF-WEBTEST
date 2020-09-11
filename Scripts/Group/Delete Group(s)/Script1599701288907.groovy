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

WebUI.openBrowser('')

WebUI.navigateToUrl('https://lab.powerfolder.net:8666/login')

WebUI.setText(findTestObject('Page_Login - PowerFolder/input_neues Konto erstellen_Username'), 'admin')

WebUI.click(findTestObject('Page_Login - PowerFolder/input_neues Konto erstellen_Login'))

WebUI.setEncryptedText(findTestObject('Page_Login - PowerFolder/input_Passwort wiederherstellen_Password'), '8SQVv/p9jVScEs4/2CZsLw==')

WebUI.click(findTestObject('Page_Login - PowerFolder/input_neues Konto erstellen_Login'))

WebUI.click(findTestObject('Page_Dashboard - PowerFolder/lang_Gruppen'))

WebUI.click(findTestObject('Page_Groups - PowerFolder/span_Organisation_glyphicons glyphicons-gro_5b74f3_1'))

WebUI.click(findTestObject('Page_Groups - PowerFolder/span_Ausloggen_glyphicons glyphicons-unchec_7f859a'))

WebUI.click(findTestObject('Page_Groups - PowerFolder/lang_Lschen'))

WebUI.click(findTestObject('Page_Groups - PowerFolder/lang_Ja'))

WebUI.click(findTestObject('Page_Groups - PowerFolder/span_Gruppe gelscht_caret'))

WebUI.click(findTestObject('Page_Groups - PowerFolder/lang_Ausloggen'))

WebUI.closeBrowser()

