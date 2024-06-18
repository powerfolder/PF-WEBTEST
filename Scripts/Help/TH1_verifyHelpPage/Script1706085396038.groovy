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

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.OPTIONAL)

WebUI.click(findTestObject('LeftNavigationIcons/Help'))

WebUI.switchToWindowIndex(1)

Thread.sleep(11000)

// Vérifiez si le titre de la fenêtre contient 'Confluence'
String windowTitle = WebUI.getWindowTitle()

boolean isCorrectTitle = windowTitle.contains('Confluence') && (windowTitle.contains('Spaces') || windowTitle.contains('Espaces'))

WebUI.verifyEqual(isCorrectTitle, true)

WebUI.click(findTestObject('Help/powerFolder'))

Thread.sleep(8000)

// Vérifiez si le titre de la fenêtre contient 'PowerFolder - Confluence'
windowTitle = WebUI.getWindowTitle()

isCorrectTitle = windowTitle.contains('PowerFolder - Confluence')

WebUI.verifyEqual(isCorrectTitle, true)

// Vérifiez si le texte contient 'PowerFolder Documentation and Support'
String actualText = WebUI.getText(findTestObject('Help/getTitleText')).trim()

String expectedText = 'PowerFolder Documentation and Support'

WebUI.verifyEqual(actualText.contains(expectedText), true)

WebUI.closeBrowser()

