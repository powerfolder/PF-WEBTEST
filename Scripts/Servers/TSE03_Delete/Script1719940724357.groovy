import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import java.time.Duration

// Appeler le cas de test de connexion
WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

// Cliquer sur l'élément de la page Serveurs
WebUI.click(findTestObject('servers/Page_Dashboard - PowerFolder/lang_Servers_v'))

WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), Duration.ofSeconds(5))

// Attendre que l'élément devienne visible
WebElement settings_button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//td[1]/span')))

// Cliquer sur l'élément
settings_button.click()

// Cliquer sur le bouton Restart
WebUI.click(findTestObject('Object Repository/servers/Page_Servers - PowerFolder/span_Restart_pica-table-selection-multi pic_96fda4'))

// Cliquer sur le bouton Yes pour confirmer
WebUI.click(findTestObject('Object Repository/servers/Page_Servers - PowerFolder/button_Yes'))

// Ajouter un délai de 2 secondes
WebUI.delay(2)

// Vérifier que l'élément est présent à la fin du test
try {
    WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//td[1]/span')))

    assert element != null : 'L\'élément //td[1]/span n\'est pas présent à la fin du test.'

    println('Le serveur est toujours présent.')
}
catch (Exception e) {
    WebUI.takeScreenshot()

    throw new AssertionError('Le serveur est supprimé.')
} 

// Fermer le navigateur
WebUI.closeBrowser()

