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
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import java.util.List as List

WebUI.callTestCase(findTestCase('File/Pre_test/Create 10 files'), [:], FailureHandling.STOP_ON_FAILURE)

WebElement premierElement = DriverFactory.getWebDriver().findElement(By.xpath('//div[2]/table/tbody/tr[1]'))

premierElement.click()

WebUI.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/Page_Folders - PowerFolder/select_all'))

// Clique sur le lien "a_Delete" dans la page "Groups - PowerFolder"
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/span_Delete'))

// Clique sur le bouton "Yes" dans la page "Groups - PowerFolder"
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/button_Yes'))

WebUI.delay(3)

// Vérification de la suppression des éléments dans la table
List<WebElement> items = findGroup()

// Méthode pour trouver les éléments dans la table
assert items.isEmpty() : 'Les éléments n\'ont pas été supprimés avec succès'

WebUI.delay(3)

// Fermeture du navigateur
WebUI.closeBrowser()

List<WebElement> findGroup() {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElements(By.xpath('//td[1]/span'))
}

