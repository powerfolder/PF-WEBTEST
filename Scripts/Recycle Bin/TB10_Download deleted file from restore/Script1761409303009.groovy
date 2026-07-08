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
import java.time.Duration as Duration

WebUI.callTestCase(findTestCase('Recycle Bin/Pre_test/Create_Document'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/span_Delete'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/button_Yes'))

WebUI.refresh()

WebUI.click(findTestObject('file_objects/recycle/Page_Folders - PowerFolder/span_recycle'))

String folderName = GlobalVariable.folderName

println('folderName: ' + folderName)

WebElement btn = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), Duration.ofSeconds(5))

// Attendre la visibilité de la première ligne du tableau
WebElement firstElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//table[@id=\'versions_table\']/tbody/tr[@id][1]')))

// Cliquer sur le premier élément du tableau
firstElement.click()

WebUI.verifyElementClickable(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/Restore'))

WebUI.click(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/Restore'))

WebUI.verifyElementClickable(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/Page_Recycle bin - PowerFolder/button_restore_Download'))

WebUI.click(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/Page_Recycle bin - PowerFolder/button_restore_Download'))


WebUI.delay(5)

println(GlobalVariable.Document)

String DocName = GlobalVariable.Document

println('folderName: ' + DocName)

String home = System.getProperty('user.home')

String downloadPath = home + '/Downloads/'

assert isFileDownloaded(downloadPath, DocName + '.docx') 

WebUI.click(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/lang_Close'))

WebUI.closeBrowser()

@Keyword
WebElement findFolder(String folderName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//a[contains(text(),\'' + folderName) + '\')]'))
}

@Keyword
WebElement findDoc(String DocName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + DocName) + '\')]/td[1]/span'))
}
@Keyword
boolean isFileDownloaded(String downloadPath, String DocName) {
	long timeout = (5 * 60) * 1000
	long start = new Date().getTime()
	boolean downloaded = false
	File file = new File(downloadPath, DocName)

	while (!downloaded) {
		println("Checking file exists $file.absolutePath")
		downloaded = file.exists()

		if (downloaded) {
			file.delete()
		} else {
			long now = new Date().getTime()
			if ((now - start) > timeout) {
				break
			}
			Thread.sleep(3000)
		}
	}
	return downloaded
}
