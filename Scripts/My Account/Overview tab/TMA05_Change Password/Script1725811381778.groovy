import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
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
import org.apache.commons.lang.RandomStringUtils as RandomStringUtils
import org.openqa.selenium.By as By
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.time.Duration

// Appel du test de création de compte
WebUI.callTestCase(findTestCase('My Account/Pre_test/Create Account'), [:], FailureHandling.STOP_ON_FAILURE)

// Navigation vers les éléments de l'interface utilisateur
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/My_account'))

// Vérification que le bouton de changement de mot de passe est cliquable
WebUI.verifyElementClickable(findTestObject('My_Account/Overview/Page_Profile - PowerFolder/Change_Password'))

// Clic pour changer le mot de passe
WebUI.click(findTestObject('My_Account/Overview/Page_Profile - PowerFolder/Change_Password'))

// Saisie de l'ancien mot de passe
WebUI.setText(findTestObject('My_Account/Overview/Page_Profile - PowerFolder/Set_Old_Password'), GlobalVariable.Pass)

// Génération d'un nouveau mot de passe complexe — garantiert mindestens
// 1 Kleinbuchstabe, 1 Großbuchstabe, 1 Zahl, 1 Sonderzeichen, Mindestlänge 8 (Ziel 12)
List<String> pwChars = []
pwChars << RandomStringUtils.random(1, 'abcdefghijklmnopqrstuvwxyz')
pwChars << RandomStringUtils.random(1, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ')
pwChars << RandomStringUtils.random(1, '0123456789')
pwChars << RandomStringUtils.random(1, '!@#$%^&*()_-+=<>?')
String fillerPool = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_-+=<>?'
(12 - pwChars.size()).times { pwChars << RandomStringUtils.random(1, fillerPool) }
Collections.shuffle(pwChars)
String NewPasswordWithSymbols = pwChars.join('')

// Saisie du nouveau mot de passe
WebUI.setText(findTestObject('My_Account/Overview/Page_Profile - PowerFolder/Set_New_Password'), NewPasswordWithSymbols)

WebUI.setText(findTestObject('My_Account/Overview/Page_Profile - PowerFolder/Confirme_New_Password'), NewPasswordWithSymbols)

// Attente de 2 secondes pour s'assurer que tout est bien saisi
WebUI.delay(2)

// Initialisation de l'attente explicite pour l'élément du bouton de changement de mot de passe
WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), Duration.ofSeconds(5))

WebElement changepassword = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='profile_password_dialog']//div[contains(@class,'modal-footer')]/button[.//lang[@name='button_change']]")))

// Clic sur le bouton pour changer le mot de passe
changepassword.click()

WebUI.refresh()

// Attente de 3 secondes après le changement de mot de passe
WebUI.delay(3)

// Déconnexion du compte
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

// Récupération de l'email utilisateur stocké dans les variables globales
String userEmail = GlobalVariable.userEmail

println(userEmail)

// Connexion avec les nouvelles informations d'identification
WebUI.setText(findTestObject('Login/inputEmail'), userEmail)

WebUI.setText(findTestObject('Login/inputPassword'), NewPasswordWithSymbols)

WebUI.click(findTestObject('Login/loginSubmit'))

// Attente de 3 secondes après la tentative de connexion
WebUI.delay(3)

// Fermeture du navigateur
WebUI.closeBrowser()

