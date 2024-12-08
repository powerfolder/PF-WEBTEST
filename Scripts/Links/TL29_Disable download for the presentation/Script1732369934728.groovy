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

println(GlobalVariable.presentationname)

presentationname = GlobalVariable.presentationname

WebUI.callTestCase(findTestCase('Links/pre_test/Create_presentation_link'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementClickable(findTestObject('Links/Page_Folders - PowerFolder/label_Disable Download (View only)'))

WebUI.click(findTestObject('Links/Page_Folders - PowerFolder/label_Disable Download (View only)'))

WebUI.click(findTestObject('Object Repository/Folders/button_SaveSettings'))

WebUI.click(findTestObject('Object Repository/Page_Folders - PowerFolder/icon-copy'))

String my_clipboard = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor)

assert (my_clipboard != null) && my_clipboard.startsWith('http')

WebUI.switchToWindowIndex(1)

WebUI.navigateToUrl(my_clipboard)

assert WebUI.getWindowTitle() == 'Link - PowerFolder'

WebUI.delay(2)

WebUI.switchToWindowIndex(0)

WebUI.click(findTestObject('links files/Page_Folders - PowerFolder/button_Close'))


WebElement btn1 = findShareButton(presentationname)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

WebUI.click(findTestObject('links files/Page_Folders - PowerFolder/links_config'))

String iconClass = WebUI.getAttribute(findTestObject('Links/Page_Folders - PowerFolder/disable_Download_icon'), 'class')

println('Icon class after click: ' + iconClass)

assert iconClass.contains('glyphicons-check') : 'The icon does not contain the \'glyphicons-check\' class.'

WebUI.delay(2)

WebUI.closeBrowser() // Attente maximale de 10 secondes

WebElement findShareButton(String fileName) {
	WebDriver driver = DriverFactory.getWebDriver()
	WebDriverWait wait = new WebDriverWait(driver, 10)

	// Attendre que l'élément soit visible et interactif
	return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
		"//table[@id='files_files_table']/tbody/tr/td[2]/a[contains(text(),'" + fileName + "')]/../../td[7]/a"
	)))
}
