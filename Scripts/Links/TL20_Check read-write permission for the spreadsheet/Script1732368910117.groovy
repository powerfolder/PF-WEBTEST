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
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.DataFlavor as DataFlavor
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions

println(GlobalVariable.spreadsheetname)

spreadsheetname = GlobalVariable.spreadsheetname

// Appel du cas de test pour créer un lien
WebUI.callTestCase(findTestCase('Links/pre_test/Create_spreadsheet_link'), [:], FailureHandling.STOP_ON_FAILURE)

// Vérifier si l'élément "Can read and write" est cliquable
TestObject readWriteLabel = findTestObject('links files/Page_Folders - PowerFolder/label_Can read and write')

WebUI.verifyElementClickable(readWriteLabel, FailureHandling.CONTINUE_ON_FAILURE)

// Cliquer sur l'élément "Can read and write"
WebUI.click(readWriteLabel)

// Sauvegarder les paramètres
WebUI.click(findTestObject('Object Repository/Folders/button_SaveSettings'))

// Copier le lien
WebUI.click(findTestObject('Object Repository/Page_Folders - PowerFolder/icon-copy'))

String my_clipboard = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor)

WebUI.comment('URL copiée : ' + my_clipboard)

// Vérifier que l'URL est valide
assert (my_clipboard != null) && my_clipboard.startsWith('https')

// Ouvrir l'URL dans une nouvelle fenêtre
WebUI.switchToWindowIndex(1)

WebUI.navigateToUrl(my_clipboard)

// Vérifier le titre de la fenêtre
assert WebUI.getWindowTitle().equals('Link - PowerFolder')

// Attendre le chargement de la page
WebUI.delay(3)

// Revenir à la fenêtre principale
WebUI.switchToWindowIndex(0)

// Fermer la boîte de dialogue
WebUI.click(findTestObject('links files/Page_Folders - PowerFolder/button_Close'))

// Trouver et cliquer sur le bouton Share
WebElement btn1 = findShareButton(spreadsheetname)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

// Configurer le lien
WebUI.click(findTestObject('links files/Page_Folders - PowerFolder/links_config'))

// Vérifier de nouveau si l'élément est cliquable
boolean isChecked_2 = WebUI.verifyElementClickable(readWriteLabel, FailureHandling.CONTINUE_ON_FAILURE)

WebUI.comment('Le label \'Can read and write\' est cliquable : ' + isChecked_2)

// Délai et fermeture du navigateur
WebUI.delay(2)

WebUI.closeBrowser() // Fonction pour trouver le bouton Share

WebElement findShareButton(String pdfFileName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + pdfFileName) + '\')]/td[7]/a'))
}

