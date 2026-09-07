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

// Sélectionner explicitement une ligne de serveur EN LIGNE (icône glyphicons-ok-circle)
String onlineServerRowXpath = "//tr[.//span[contains(@class,'glyphicons-ok-circle')]]"

WebElement settings_button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(onlineServerRowXpath + "/td[contains(@class,'pica-name')]")))

// Cliquer sur l'élément pour sélectionner uniquement cette ligne
settings_button.click()

// Cliquer sur le bouton Delete de la barre d'outils
WebUI.click(findTestObject('Object Repository/servers/Page_Servers - PowerFolder/span_Restart_pica-table-selection-multi pic_96fda4'))

// Vérifier qu'une notification d'avertissement s'affiche ("Only offline servers can be deleted")
WebElement warning = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//div[contains(@class,'pica-notification-box') and contains(@class,'alert-warning')]")))

assert warning != null : 'La notification d\'avertissement pour la suppression d\'un serveur en ligne ne s\'est pas affichée.'

// Vérifier qu'aucune boîte de dialogue de confirmation de suppression ne s'est ouverte
boolean confirmDialogOpened
try {
    WebDriverWait shortWait = new WebDriverWait(DriverFactory.getWebDriver(), Duration.ofSeconds(2))

    shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
            "//div[@id='pica_confirmation_dialog']//div[contains(@class,'modal-footer')]/button[.//lang[@name='button_yes']]")))

    confirmDialogOpened = true
}
catch (Exception e) {
    confirmDialogOpened = false
}

assert !confirmDialogOpened : 'La boîte de dialogue de confirmation de suppression s\'est ouverte alors que le serveur sélectionné est en ligne.'

// Vérifier que le serveur en ligne est toujours présent, la suppression n'a pas eu lieu
try {
    WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(onlineServerRowXpath + "/td[contains(@class,'pica-name')]")))

    assert element != null : 'Le serveur en ligne n\'est plus présent après la tentative de suppression.'

    println('Le serveur en ligne est toujours présent, la suppression a bien été refusée.')
}
catch (Exception e) {
    WebUI.takeScreenshot()

    throw new AssertionError('Le serveur en ligne a été supprimé alors que cela ne devrait pas être possible (PFS-5815).')
}

// Fermer le navigateur
WebUI.closeBrowser()

