import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable
import org.openqa.selenium.Keys
import org.apache.commons.lang3.RandomStringUtils
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import java.util.Arrays
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

// Initialisation de la variable globale
GlobalVariable.userName = 'user_' + RandomStringUtils.randomNumeric(4) + '@test.com'

// Appel du cas de test pour ajouter un membre
WebUI.callTestCase(findTestCase('Groups/Pre_test/add member'), [:], FailureHandling.STOP_ON_FAILURE)

// Navigation vers la page d'édition des groupes
WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

// Navigation vers la page des membres
WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Members'))

// Initialisation de l'attente explicite
WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), 5)

// Délai pour attendre le chargement de la page
WebUI.delay(3)

// Localisation de l'utilisateur ajouté
WebElement user = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//*[@id="pica_group_accounts"]//div[2]/table/tbody/tr/td[2][contains(text(), \'' +
	GlobalVariable.userName + '\')]')))

// Assertion pour vérifier que l'utilisateur a été trouvé
assert user != null : 'L\'élément utilisateur n\'a pas été trouvé.'
user.click()

// Délai pour voir l'action effectuée
WebUI.delay(2)

// Fermeture du navigateur
WebUI.closeBrowser()

@Keyword
WebElement findGroup(String groupName) {
	WebDriver driver = DriverFactory.getWebDriver()
	return driver.findElement(By.xpath('//*[contains(@data-search-keys, \'' + groupName + '\')]/td[1]/span'))
}
