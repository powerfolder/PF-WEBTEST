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

WebUI.setText(findTestObject('Object Repository/Page_Login - PowerFolder/input_neues Konto erstellen_Username'), 'admin')

WebUI.click(findTestObject('Object Repository/Page_Login - PowerFolder/input_neues Konto erstellen_Login'))

WebUI.setEncryptedText(findTestObject('Object Repository/Page_Login - PowerFolder/input_Passwort wiederherstellen_Password'), 
    '8SQVv/p9jVScEs4/2CZsLw==')

WebUI.click(findTestObject('Object Repository/Page_Login - PowerFolder/input_neues Konto erstellen_Login'))

WebUI.click(findTestObject('Object Repository/Page_Dashboard - PowerFolder/lang_Konten'))

WebUI.click(findTestObject('Object Repository/Page_Accounts - PowerFolder/span_Lschen_pica-glyph glyphicons glyphicon_aee676'))

WebUI.click(findTestObject('Object Repository/Page_Accounts - PowerFolder/a_Konto erstellen'))

WebUI.setText(findTestObject('Object Repository/Page_Accounts - PowerFolder/input_Notizen_pica_account_username'), 'user@powerfolder.com')

WebUI.setEncryptedText(findTestObject('Object Repository/Page_Accounts - PowerFolder/input_Notizen_pica_account_password'), 
    'PpFy9OM6JMVjJ8cBivBeSg==')

WebUI.setText(findTestObject('Object Repository/Page_Accounts - PowerFolder/input_Notizen_pica_account_quota'), '1')

WebUI.click(findTestObject('Object Repository/Page_Accounts - PowerFolder/lang_Speichern'))

WebUI.click(findTestObject('Object Repository/Page_Accounts - PowerFolder/span_admin_glyphicons glyphicons-user pica-glyph'))

WebUI.click(findTestObject('Object Repository/Page_Accounts - PowerFolder/lang_Lschen'))

WebUI.click(findTestObject('Object Repository/Page_Accounts - PowerFolder/button_Ja'))

WebUI.click(findTestObject('Object Repository/Page_Accounts - PowerFolder/a_Konto erstellt_dropdown-toggle'))

WebUI.click(findTestObject('Object Repository/Page_Accounts - PowerFolder/lang_Ausloggen'))

WebUI.navigateToUrl('https://lab.powerfolder.net:8666/login')

WebUI.closeBrowser()

