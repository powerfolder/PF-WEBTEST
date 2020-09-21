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

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Dashboard/lang_Folders'))

WebUI.click(findTestObject('Object Repository/Page_Folders - PowerFolder/span_Keine Mitglieder_pica-glyph glyphicons_e847e5'))

WebUI.click(findTestObject('Object Repository/Page_Folders - PowerFolder/lang_Link erstellen'))

WebUI.click(findTestObject('Object Repository/Page_Folders - PowerFolder/i_Entfernen_glyphicons glyphicons-copy'))

WebUI.click(findTestObject('Object Repository/Page_Folders - PowerFolder/button_Schlieen'))

WebUI.click(findTestObject('Object Repository/Page_Folders - PowerFolder/span_Keine Mitglieder_pica-glyph glyphicons_e847e5'))

WebUI.click(findTestObject('Object Repository/Page_Folders - PowerFolder/button_Kann lesen'))

WebUI.click(findTestObject('Object Repository/Page_Folders - PowerFolder/a_Entfernen'))

WebUI.click(findTestObject('Object Repository/Page_Folders - PowerFolder/lang_Schlieen'))

WebUI.click(findTestObject('Object Repository/Page_Folders - PowerFolder/span_Link erstellt_caret'))

WebUI.click(findTestObject('Object Repository/Page_Folders - PowerFolder/lang_Ausloggen'))

WebUI.closeBrowser()

