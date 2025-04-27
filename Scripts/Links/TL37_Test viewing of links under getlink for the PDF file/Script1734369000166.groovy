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
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions


WebUI.callTestCase(findTestCase('Links/pre_test/import pdf'), [:], FailureHandling.STOP_ON_FAILURE)

println(GlobalVariable.pdfname)

pdfname = GlobalVariable.pdfname

WebUI.click(findTestObject('Object Repository/Folders/button_SaveSettings'))

WebUI.click(findTestObject('links files/Page_Folders - PowerFolder/button_Close'))

WebUI.delay(1)

WebUI.click(findTestObject('Links/Page_Dashboard - PowerFolder/lang_Links'))

// Vérification que documentname n'est pas null
assert pdfname != null : 'Le nom du document ne doit pas être null.'

// Trouver le bouton "Share" lié au document
WebElement btn1 = findLinksButton(pdfname)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

WebUI.delay(2)

// Vérification de l'URL générée
WebUI.switchToWindowIndex(1)

String currentUrl = WebUI.getUrl()

WebUI.comment('L\'URL actuelle est: ' + currentUrl)

// Vérification si l'URL contient '/getlink/'
boolean containsGetLink = currentUrl.contains('/getlink/')

WebUI.comment('L\'URL contient \'/getlink/\': ' + containsGetLink)

WebUI.verifyEqual(containsGetLink, true)

// Fermer le navigateur
WebUI.closeBrowser() ///////////////////////////////////////////////////// Méthodes //////////////////////////////////////////////

WebElement findLinksButton(String pdfFileName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + pdfFileName) + '\')]/td[7]/a/span'))
}

