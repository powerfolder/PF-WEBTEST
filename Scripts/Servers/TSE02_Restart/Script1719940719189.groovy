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

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/servers/Page_Servers - PowerFolder/lang_Servers'))

WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), 5)

// Attendre que l'élément devienne visible
WebElement settings_button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//td[1]/span')))

// Cliquer sur l'élément
settings_button.click()

WebUI.click(findTestObject('Object Repository/servers/Page_Servers - PowerFolder/a_Restart'))

WebUI.click(findTestObject('Object Repository/servers/Page_Servers - PowerFolder/lang_Yes'))

WebUI.delay(2)

WebUI.verifyElementVisible(findTestObject('Object Repository/servers/Page_Servers - PowerFolder/span_Server restarted'))

WebUI.closeBrowser()

