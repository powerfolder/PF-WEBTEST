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

// Appel du test case de connexion
WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.OPTIONAL)

// Cliquez sur l'icône d'aide dans la navigation de gauche
WebUI.click(findTestObject('LeftNavigationIcons/Help'))

// Passez à la nouvelle fenêtre
WebUI.switchToWindowIndex(1)

// Attendez que la page charge complètement
WebUI.delay(10) 

// Obtenez l'URL actuelle
String currentUrl = WebUI.getUrl()

// Loguez l'URL actuelle pour vérification
WebUI.comment("L'URL actuelle est: " + currentUrl)

// Vérifiez si l'URL correspond à celle attendue
String expectedUrl = 'https://powerfolder.atlassian.net/wiki/spaces/PF/overview'
boolean isCorrectUrl = currentUrl.equals(expectedUrl)

// Vérifiez si l'URL est correcte
WebUI.verifyEqual(isCorrectUrl, true)

// Fermez le navigateur
WebUI.closeBrowser()
