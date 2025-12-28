import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static org.apache.commons.lang.StringUtils.isNotBlank
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
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor

WebUI.callTestCase(findTestCase('Links/pre_test/Binary file'), [:], FailureHandling.STOP_ON_FAILURE)

println(GlobalVariable.binaryName)

binaryName = GlobalVariable.binaryName

// Vérification si l'élément est cliquable
boolean isChecked = WebUI.verifyElementClickable(findTestObject('Object Repository/Links/Page_Folders - PowerFolder/label_Can read'), 
    FailureHandling.CONTINUE_ON_FAILURE)

WebUI.setText(findTestObject('Page_Link - PowerFolder/lang_Password required'), 'Alexa@131190')

// Sauvegarder les paramètres
WebUI.click(findTestObject('Object Repository/Folders/button_SaveSettings'))

// Copier le lien
WebUI.doubleClick(findTestObject('Object Repository/Page_Folders - PowerFolder/icon-copy'))

String my_clipboard = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor)

WebUI.comment('URL copié : ' + my_clipboard)

// Vérifier que l'URL est valide
assert (my_clipboard != null) && my_clipboard.startsWith('https')

// Ouvrir le lien dans un nouvel onglet
WebUI.executeJavaScript('window.open(arguments[0], \'_blank\');', Arrays.asList(my_clipboard))

// Passer au nouvel onglet (index 1)
WebUI.switchToWindowIndex(1)

assert WebUI.getWindowTitle().equals('Link - PowerFolder')

WebUI.verifyElementText(findTestObject('Page_Link - PowerFolder/getPasswordBodyText'), 'This link is password protected')

WebUI.setText(findTestObject('Page_Link - PowerFolder/inputPassword'), 'Alexa@131190')

WebUI.click(findTestObject('Page_Link - PowerFolder/buttonOK'))

assert WebUI.getWindowTitle().equals('Link - PowerFolder')

WebUI.click(findTestObject('Links/Page_Link - PowerFolder/folder_link_download_button'))

// Save the parent window handle
WebDriver driver = DriverFactory.getWebDriver()
String parentWindow = driver.getWindowHandle()

// Open a new tab to check the downloads
JavascriptExecutor jsExecutor = (JavascriptExecutor) driver
jsExecutor.executeScript('window.open()')

// Switch to the new tab
Set<String> allWindowHandles = driver.getWindowHandles()
for (String winHandle : allWindowHandles) {
	if (!winHandle.equals(parentWindow)) {
		driver.switchTo().window(winHandle)
	}
}

// Navigate to the downloads page
WebUI.delay(5)
driver.get('chrome://downloads')

// Retry mechanism for fetching the file name
JavascriptExecutor downloadWindowExecutor = (JavascriptExecutor) driver
String fileName = ''
int retryCount = 0
boolean isFileNameRetrieved = false

while (retryCount < 5 && !isFileNameRetrieved) {
	try {
		fileName = downloadWindowExecutor.executeScript('return document.querySelector("downloads-manager").shadowRoot.querySelector("#downloadsList downloads-item").shadowRoot.querySelector("div#content #file-link").textContent').toString()
		isFileNameRetrieved = (fileName != null && !fileName.isEmpty())
	} catch (Exception e) {
		WebUI.delay(2)  // Wait for 2 seconds before retrying
		retryCount++
	}
}

if (!isFileNameRetrieved) {
	throw new RuntimeException('Failed to retrieve the downloaded file name after retries.')
}

String downloadSourceLink = downloadWindowExecutor.executeScript('return document.querySelector("downloads-manager").shadowRoot.querySelector("#downloadsList downloads-item").shadowRoot.querySelector("div#content #file-link").href').toString()

System.out.println('Downloaded File Name: ' + binaryName)

// Verify the downloaded file name
WebUI.delay(5)
WebUI.verifyEqual(fileName, binaryName + '.bin')

WebUI.closeBrowser()

WebElement findShareButton(String fileName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + fileName) + '\')]/td[7]/a/span'))
}

String getTimestamp() {
    Date todaysDate = new Date()

    String formattedDate = todaysDate.format('ddMMMyyyyhhmmss')

    return formattedDate
}

String getRandomFolderName() {
    String folderName = 'FD' + getTimestamp()

    return folderName
}

