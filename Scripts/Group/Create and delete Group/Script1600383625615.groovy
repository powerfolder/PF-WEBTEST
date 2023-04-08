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


//TODO rewrite group cases
//WebUI.openBrowser('')
//
//WebUI.navigateToUrl(GlobalVariable.URL)
//
//WebUI.setText(findTestObject('Object Repository/Page_Login - PowerFolder/input_neues Konto erstellen_Username'), 'admin')
//
//WebUI.click(findTestObject('Object Repository/Page_Login - PowerFolder/input_neues Konto erstellen_Login'))
//
//WebUI.setEncryptedText(findTestObject('Object Repository/Page_Login - PowerFolder/input_Passwort wiederherstellen_Password'), 
//    '8SQVv/p9jVScEs4/2CZsLw==')
//
//WebUI.click(findTestObject('Object Repository/Page_Login - PowerFolder/input_neues Konto erstellen_Login'))
//
//WebUI.navigateToUrl('https://lab.powerfolder.net:8666/admin/dashboard')
//
//WebUI.click(findTestObject('Object Repository/Page_Dashboard - PowerFolder/lang_Gruppen'))
//
//WebUI.click(findTestObject('Object Repository/Page_Groups - PowerFolder/span_Ausloggen_pica-icon pica-glyph glyphic_a7da9c'))
//
//WebUI.setText(findTestObject('Object Repository/Page_Groups - PowerFolder/input_Organisationen_pica_group_name'), 'Group')
//
//WebUI.setText(findTestObject('Object Repository/Page_Groups - PowerFolder/textarea_Organisationen_pica_group_notes'), 'New!')
//
//WebUI.click(findTestObject('Object Repository/Page_Groups - PowerFolder/lang_Ordner'))
//
//WebUI.click(findTestObject('Object Repository/Page_Groups - PowerFolder/lang_Mitglieder'))
//
//WebUI.click(findTestObject('Object Repository/Page_Groups - PowerFolder/lang_Organisationen'))
//
//WebUI.click(findTestObject('Object Repository/Page_Groups - PowerFolder/lang_Speichern'))
//
//WebUI.click(findTestObject('Object Repository/Page_Groups - PowerFolder/span_Organisation_glyphicons glyphicons-gro_5b74f3'))
//
//WebUI.click(findTestObject('Object Repository/Page_Groups - PowerFolder/lang_Lschen'))
//
//WebUI.click(findTestObject('Object Repository/Page_Groups - PowerFolder/lang_Nein'))
//
//WebUI.click(findTestObject('Object Repository/Page_Groups - PowerFolder/a_Group'))
//
//WebUI.click(findTestObject('Object Repository/Page_Groups - PowerFolder/lang_Avatar'))
//
//WebUI.click(findTestObject('Object Repository/Page_Groups - PowerFolder/lang_Ordner'))
//
//WebUI.click(findTestObject('Object Repository/Page_Groups - PowerFolder/lang_Mitglieder'))
//
//WebUI.click(findTestObject('Object Repository/Page_Groups - PowerFolder/lang_Organisationen'))
//
//WebUI.click(findTestObject('Object Repository/Page_Groups - PowerFolder/lang_Abbrechen'))
//
//WebUI.click(findTestObject('Object Repository/Page_Groups - PowerFolder/span_Organisation_glyphicons glyphicons-gro_5b74f3'))
//
//WebUI.click(findTestObject('Object Repository/Page_Groups - PowerFolder/lang_Lschen'))
//
//WebUI.click(findTestObject('Object Repository/Page_Groups - PowerFolder/lang_Ja'))
//
//WebUI.click(findTestObject('Object Repository/Page_Groups - PowerFolder/span_Gruppe gelscht_caret'))
//
//WebUI.click(findTestObject('Object Repository/Page_Groups - PowerFolder/lang_Ausloggen'))
//
//WebUI.closeBrowser()

