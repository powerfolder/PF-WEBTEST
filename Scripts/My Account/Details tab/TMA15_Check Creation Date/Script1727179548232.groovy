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
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.DataFlavor as DataFlavor
import org.openqa.selenium.By as By
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.nio.file.Path as Path
import java.nio.file.Files as Files
import java.text.SimpleDateFormat as SimpleDateFormat
import java.util.Date as Date
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils


// Plan de test : Connexion en tant qu'administrateur
WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.OPTIONAL)

// Variables globales
GlobalVariable.userEmail = (('user_' + RandomStringUtils.randomNumeric(4)) + '@test.com')

String Emailid = GlobalVariable.userEmail


// Création d'un nouvel utilisateur
WebUI.click(findTestObject('LeftNavigationIcons/account'))

WebUI.click(findTestObject('Accounts/CreateButton'))

WebUI.click(findTestObject('Accounts/ClickCreateAccount'))

// Remplissage des informations utilisateur
WebUI.setText(findTestObject('Accounts/InputUserOrEmail'), Emailid)

WebUI.setText(findTestObject('Accounts/InputPassword'), GlobalVariable.Pass)

WebUI.setText(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/account_storage_overwiew'), '5')

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.delay(3 // Ajout d'un délai pour s'assurer que la page est complètement chargée
    )

// Déconnexion
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

// Connexion avec le nouvel utilisateur
WebUI.setText(findTestObject('Login/inputEmail'), Emailid)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(3)

// Accès aux détails du compte utilisateur
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/My_account'))

WebUI.click(findTestObject('My_Account/Detail/Page_Profile - PowerFolder/a_Details'))

WebUI.mouseOver(findTestObject('My_Account/Detail/Page_Profile - PowerFolder/creation_date'))

WebUI.delay(1)

// Récupération de la date dans l'onglet utilisateur
String Date_from_user_tab = WebUI.getText(findTestObject('My_Account/Detail/Page_Profile - PowerFolder/creation_date'))

println('Date from user tab: ' + Date_from_user_tab)

// Vérification si la date est d'aujourd'hui
SimpleDateFormat sdf = new SimpleDateFormat('yyyy-MM-dd' // Format de la date à ajuster selon votre cas
)

String today = sdf.format(new Date( // Date d'aujourd'hui au même format
    ))

// Vérification si la date récupérée est d'aujourd'hui
if (Date_from_user_tab.contains(today)) {
    println('La date est d\'aujourd\'hui : ' + Date_from_user_tab)
} else {
    println('La date n\'est pas d\'aujourd\'hui : ' + Date_from_user_tab)
}

WebUI.delay(3)

WebUI.closeBrowser()

