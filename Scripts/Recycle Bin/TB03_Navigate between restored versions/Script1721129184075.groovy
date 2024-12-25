import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberBuiltinKeywords
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGBuiltinKeywords
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as WindowsBuiltinKeywords
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
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository



// Appel du test case
WebUI.callTestCase(findTestCase('Recycle Bin/Pre_test/restore 5 versions'), [:], FailureHandling.STOP_ON_FAILURE)
// Variables globales
String folderName = GlobalVariable.folderName
String docName = GlobalVariable.Document
// Restauration des versions
restoreVersion(2)
restoreDocumentVersion(docName, 3)
restoreDocumentVersion(docName, 4)
restoreDocumentVersion(docName, 5)
restoreDocumentVersion(docName, 6)

// Vérification finale
def btnFinal = findDoc(docName)
if (btnFinal != null) {
	WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btnFinal))
	assert docName != null
} else {
	println("Final document check failed: Document not found.")
	throw new RuntimeException("Final document check failed: Document not found.")
}

WebUI.closeBrowser()

/**
 * Trouve un dossier par son nom
 */
@Keyword
WebElement findFolder(String folderName) {
	WebDriver driver = DriverFactory.getWebDriver()
	WebDriverWait wait = new WebDriverWait(driver, 30)

	println("Searching for folder with name: " + folderName)
	WebElement element = waitForElementVisible(driver, By.xpath("//a[contains(text(),'" + folderName + "')]"), wait)

	if (element == null) {
		println("Folder not found: " + folderName)
		println("Page source:\n" + driver.getPageSource())
	}

	return element
}

/**
 * Trouve un document par son nom
 */
@Keyword
WebElement findDoc(String docName) {
	WebDriver driver = DriverFactory.getWebDriver()
	WebDriverWait wait = new WebDriverWait(driver, 30)

	try {
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[contains(@data-search-keys, '" + docName + "')]/td[1]/span")))
		println("Document found: " + docName)
		return element
	} catch (org.openqa.selenium.TimeoutException e) {
		println("Timeout while waiting for document: " + docName)
		println("Page source at timeout:\n" + driver.getPageSource())
		return null
	}
}

/**
 * Restaure une version spécifique à partir de l'index de ligne
 */
@Keyword
void restoreVersion(int rowIndex) {
	WebUI.click(findTestObject('Recycle bin/Page_Folders - PowerFolder/a_Restore'))

	WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), 10)
	WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//*[@id="pica_restore_versions"]/div')))
	WebElement button = table.findElement(By.xpath("./table/tbody/tr[" + rowIndex + "]/td[6]/button"))

	button.click()
	WebUI.click(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/lang_Close'))
}

/**
 * Restaure une version d'un document spécifique
 */
@Keyword
void restoreDocumentVersion(String docName, int rowIndex) {
	WebUI.refresh()
	WebUI.delay(2)

	WebElement btn = findDoc(docName)
	if (btn != null && btn.isDisplayed() && btn.isEnabled()) {
		println("Clicking on the document: " + docName)
		WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))
		restoreVersion(rowIndex)
	} else {
		println("Cannot restore version: Document not found or not interactable.")
		throw new RuntimeException("Cannot restore version: Document not found.")
	}
}

/**
 * Attends qu'un élément soit visible
 */
@Keyword
WebElement waitForElementVisible(WebDriver driver, By by, WebDriverWait wait) {
	try {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(by))
	} catch (org.openqa.selenium.TimeoutException e) {
		println("Timeout while waiting for element: " + by.toString())
		return null
	}
}
