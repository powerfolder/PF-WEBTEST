 // Import des packages et des classes nécessaires
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW

// Ouvre un nouveau navigateur
WebUI.openBrowser('')

// Maximize the browser window
WebUI.maximizeWindow()

// Navigue vers l'URL spécifiée dans la variable globale URL
WebUI.navigateToUrl(GlobalVariable.URL)

// Vérifie si le titre de la page correspond à "Login - PowerFolder"
assert WebUI.getWindowTitle().equals('Login - PowerFolder')

// Remplit le champ de saisie pour le nom d'utilisateur avec la valeur de la variable globale Username
WebUI.setText(findTestObject('Object Repository/Login/Page_Login - PowerFolder/input_register new account_Username'), GlobalVariable.Username)

// Clique sur le bouton "Login"
WebUI.click(findTestObject('Object Repository/Login/Page_Login - PowerFolder/input_register new account_Login'))

// Attend jusqu'à 2 secondes pour que l'élément de saisie de mot de passe soit visible et stocke le résultat dans isPresent
isPresent = WebUI.waitForElementVisible(findTestObject('Object Repository/Login/Page_Login - PowerFolder/input_Recover password_Password'), 
    2)

// Vérifie si l'élément de saisie de mot de passe est présent sur la page
assert isPresent

// Remplit le champ de saisie de mot de passe avec la valeur de la variable globale Password1
WebUI.setText(findTestObject('Object Repository/Login/Page_Login - PowerFolder/input_Recover password_Password'), GlobalVariable.Password)

// Clique sur le bouton "Login"
WebUI.click(findTestObject('Object Repository/Login/Page_Login - PowerFolder/input_register new account_Login'))

WebUI.delay(2)

// Vérifie si le titre de la page correspond à "Dashboard - PowerFolder"
assert WebUI.getWindowTitle().equals('Dashboard - PowerFolder')

// Clique sur le bouton "Folders"
WebUI.click(findTestObject('Object Repository/Folders/Page_Folders - PowerFolder/lang_Folders'))

// Vérifie si le titre de la page correspond à "Folders - PowerFolder"
assert WebUI.getWindowTitle().equals('Folders - PowerFolder')

