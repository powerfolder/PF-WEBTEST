import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable

import java.util.Arrays
import java.util.List
import org.openqa.selenium.By as By
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Storage/Page_Dashboard - PowerFolder/td_Storage'))

WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), 5)

// Attendre que l'élément devienne visible
WebElement settings_button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//td[1]/span')))

// Cliquer sur l'élément
settings_button.click()

WebUI.click(findTestObject('Object Repository/Storage/Page_Storage - PowerFolder/a_Delete'))

WebUI.click(findTestObject('Object Repository/Storage/Page_Storage - PowerFolder/button_Yes'))

WebUI.delay(2)

// Vérifier que l'élément est présent à la fin du test
try {
    WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//td[1]/span')))
    assert element != null : 'L\'élément //td[1]/span n\'est pas présent à la fin du test.'
    println('Le serveur est toujours présent.')
} catch (Exception e) {
    WebUI.takeScreenshot()
    throw new AssertionError('Le serveur est supprimé.')
} 

WebUI.closeBrowser()

@Keyword
List<WebElement> findStorage() {
	WebDriver driver = DriverFactory.getWebDriver()
	return driver.findElements(By.xpath('//td[1]/span'))
}
