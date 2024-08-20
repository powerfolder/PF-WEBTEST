import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.annotation.Keyword as Keyword
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
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import java.util.Arrays as Arrays
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait

// Initialisation de la variable globale
GlobalVariable.userName = (('user_' + RandomStringUtils.randomNumeric(4)) + '@test.com')
println('Global Variable: ' + GlobalVariable.GroupName)

// Appel du cas de test pour ajouter un membre
WebUI.callTestCase(findTestCase('Groups/Pre_test/add member'), [:], FailureHandling.STOP_ON_FAILURE)

// Navigation vers la page d'édition des groupes
WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUI.delay(2)

// Navigation vers la page des membres
WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Members'))

WebUI.delay(2)

// Initialisation de l'attente explicite
WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), 10)

// Localisation de l'utilisateur ajouté
WebElement user = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(('//*[@id="pica_group_accounts"]//div[2]/table/tbody/tr/td[2][contains(text(), \'' + 
            GlobalVariable.userName) + '\')]')))

// Assertion pour vérifier que l'utilisateur a été trouvé
assert user != null : 'L\'élément utilisateur n\'a pas été trouvé.'

WebUI.delay(2)

user.click()

// Délai pour voir l'action effectuée
WebUI.delay(2)

// Fermeture du navigateur
WebUI.closeBrowser()

@Keyword
WebElement findGroup(String groupName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + GlobalVariable.GroupName) + '\')]/td[1]/span'))
}

