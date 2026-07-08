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
import java.time.Duration

WebUI.callTestCase(findTestCase('File/Pre_test/delete file'), [:], FailureHandling.STOP_ON_FAILURE)

String folderName = GlobalVariable.folderName

String DocName = GlobalVariable.Document

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/span_Delete'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/button_Yes'))

// Poll every 500ms: force-close the confirmation dialog if it is (or becomes) .show.
// Handles the race where BS5 fade-in has not yet added the .show class when we first try to close,
// so we retry until it stays closed. setOkHandler runs (delete succeeds) but BS5 data-bs-dismiss
// sometimes fails to fire in this Picasso build.
new WebDriverWait(DriverFactory.getWebDriver(), Duration.ofSeconds(15)).until { drv ->
    def js = (org.openqa.selenium.JavascriptExecutor) drv
    js.executeScript("var d = document.getElementById('pica_confirmation_dialog');" +
        " if (d && d.classList.contains('show') && window.bootstrap && window.bootstrap.Modal) {" +
        "   var i = bootstrap.Modal.getInstance(d) || bootstrap.Modal.getOrCreateInstance(d);" +
        "   if (i) i.hide();" +
        " }" +
        " document.querySelectorAll('.modal-backdrop').forEach(function(b){ b.parentNode.removeChild(b); });" +
        " document.body.classList.remove('modal-open');")
    return !((Boolean) js.executeScript(
        "var el = document.getElementById('pica_confirmation_dialog'); return !!(el && el.classList.contains('show'));"))
}

WebUI.click(findTestObject('file_objects/recycle/Page_Folders - PowerFolder/span_recycle'))

WebElement btn = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), Duration.ofSeconds(5))

// Attendre la visibilité de la première ligne du tableau
WebElement firstElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//table[@id=\'versions_table\']/tbody/tr[@id][1]')))

// Cliquer sur le premier élément du tableau
firstElement.click()

WebUI.click(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/Restore'))

// Attendez que le tableau soit visible
WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//*[@id=\'pica_restore_versions\']/div')))

// Trouvez l'élément button spécifié dans le tableau
WebUI.click(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/button_Restore_file'))

WebUI.click(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/lang_Close'))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebElement btn1 = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

WebUI.delay(1)

WebElement btn2 = findDoc(DocName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn2))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/span_Delete'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/button_Yes'))

// Poll every 500ms: force-close the confirmation dialog if it is (or becomes) .show.
// Handles the race where BS5 fade-in has not yet added the .show class when we first try to close,
// so we retry until it stays closed. setOkHandler runs (delete succeeds) but BS5 data-bs-dismiss
// sometimes fails to fire in this Picasso build.
new WebDriverWait(DriverFactory.getWebDriver(), Duration.ofSeconds(15)).until { drv ->
    def js = (org.openqa.selenium.JavascriptExecutor) drv
    js.executeScript("var d = document.getElementById('pica_confirmation_dialog');" +
        " if (d && d.classList.contains('show') && window.bootstrap && window.bootstrap.Modal) {" +
        "   var i = bootstrap.Modal.getInstance(d) || bootstrap.Modal.getOrCreateInstance(d);" +
        "   if (i) i.hide();" +
        " }" +
        " document.querySelectorAll('.modal-backdrop').forEach(function(b){ b.parentNode.removeChild(b); });" +
        " document.body.classList.remove('modal-open');")
    return !((Boolean) js.executeScript(
        "var el = document.getElementById('pica_confirmation_dialog'); return !!(el && el.classList.contains('show'));"))
}

WebUI.click(findTestObject('file_objects/recycle/Page_Folders - PowerFolder/span_recycle'))

WebElement btn3 = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn3))

// Attendre la visibilité de la première ligne du tableau
WebElement firstElement_1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//table[@id=\'versions_table\']/tbody/tr[@id][1]')))

// Cliquer sur le premier élément du tableau
firstElement_1.click()

WebUI.click(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/Restore'))

// Attendez que le tableau soit visible
WebElement table_2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//*[@id=\'pica_restore_versions\']/div')))

// Trouvez l'élément button spécifié dans le tableau
WebUI.click(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/button_Restore_file'))

WebUI.click(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/lang_Close'))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebElement btn4 = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn4))

WebUI.delay(1)

WebElement btn5 = findDoc(DocName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn5))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/span_Delete'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/button_Yes'))

// Poll every 500ms: force-close the confirmation dialog if it is (or becomes) .show.
// Handles the race where BS5 fade-in has not yet added the .show class when we first try to close,
// so we retry until it stays closed. setOkHandler runs (delete succeeds) but BS5 data-bs-dismiss
// sometimes fails to fire in this Picasso build.
new WebDriverWait(DriverFactory.getWebDriver(), Duration.ofSeconds(15)).until { drv ->
    def js = (org.openqa.selenium.JavascriptExecutor) drv
    js.executeScript("var d = document.getElementById('pica_confirmation_dialog');" +
        " if (d && d.classList.contains('show') && window.bootstrap && window.bootstrap.Modal) {" +
        "   var i = bootstrap.Modal.getInstance(d) || bootstrap.Modal.getOrCreateInstance(d);" +
        "   if (i) i.hide();" +
        " }" +
        " document.querySelectorAll('.modal-backdrop').forEach(function(b){ b.parentNode.removeChild(b); });" +
        " document.body.classList.remove('modal-open');")
    return !((Boolean) js.executeScript(
        "var el = document.getElementById('pica_confirmation_dialog'); return !!(el && el.classList.contains('show'));"))
}

WebUI.click(findTestObject('file_objects/recycle/Page_Folders - PowerFolder/span_recycle'))

WebElement btn6 = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn6))

// Attendre la visibilité de la première ligne du tableau
WebElement firstElement_2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//table[@id=\'versions_table\']/tbody/tr[@id][1]')))

// Cliquer sur le premier élément du tableau
firstElement_2.click()

WebUI.click(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/Restore'))

// Attendez que le tableau soit visible
WebElement table_3 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//*[@id=\'pica_restore_versions\']/div')))

// Trouvez l'élément button spécifié dans le tableau
WebUI.click(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/button_Restore_file'))

WebUI.click(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/lang_Close'))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebElement btn7 = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn7))

WebUI.delay(1)

WebElement btn8 = findDoc(DocName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn8))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/span_Delete'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/button_Yes'))

// Poll every 500ms: force-close the confirmation dialog if it is (or becomes) .show.
// Handles the race where BS5 fade-in has not yet added the .show class when we first try to close,
// so we retry until it stays closed. setOkHandler runs (delete succeeds) but BS5 data-bs-dismiss
// sometimes fails to fire in this Picasso build.
new WebDriverWait(DriverFactory.getWebDriver(), Duration.ofSeconds(15)).until { drv ->
    def js = (org.openqa.selenium.JavascriptExecutor) drv
    js.executeScript("var d = document.getElementById('pica_confirmation_dialog');" +
        " if (d && d.classList.contains('show') && window.bootstrap && window.bootstrap.Modal) {" +
        "   var i = bootstrap.Modal.getInstance(d) || bootstrap.Modal.getOrCreateInstance(d);" +
        "   if (i) i.hide();" +
        " }" +
        " document.querySelectorAll('.modal-backdrop').forEach(function(b){ b.parentNode.removeChild(b); });" +
        " document.body.classList.remove('modal-open');")
    return !((Boolean) js.executeScript(
        "var el = document.getElementById('pica_confirmation_dialog'); return !!(el && el.classList.contains('show'));"))
}

WebUI.click(findTestObject('file_objects/recycle/Page_Folders - PowerFolder/span_recycle'))

WebElement btn9 = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn9))

// Attendre la visibilité de la première ligne du tableau
WebElement firstElement_3 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//table[@id=\'versions_table\']/tbody/tr[@id][1]')))

// Cliquer sur le premier élément du tableau
firstElement_3.click()

WebUI.click(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/Restore'))

// Attendez que le tableau soit visible
WebElement table_4 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//*[@id=\'pica_restore_versions\']/div')))

// Trouvez l'élément button spécifié dans le tableau
WebUI.click(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/button_Restore_file'))

WebUI.click(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/lang_Close'))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebElement btn10 = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn10))

WebUI.delay(1)

WebElement btn11 = findDoc(DocName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn11))

WebUI.click(findTestObject('Recycle bin/Page_Folders - PowerFolder/a_Restore'))

WebElement table_5 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//*[@id=\'pica_restore_versions\']/div')))

WebUI.verifyElementClickable(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/Page_Recycle bin - PowerFolder/button_restore_Download'))

WebUI.click(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/Page_Recycle bin - PowerFolder/button_restore_Download'))

WebUI.delay(5)

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
