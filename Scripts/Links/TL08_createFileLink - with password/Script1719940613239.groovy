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

WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.OPTIONAL)

String folderName = getRandomFolderName()

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createFolder'))

WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)

WebUI.click(findTestObject('Folders/buttonOK'))

WebDriver driver = DriverFactory.getWebDriver()

WebElement folder = driver.findElement(By.xpath("//table[@id='files_files_table']/tbody/tr/td[2]/span/a[contains(text(),'$folderName')]"))

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(folder))

WebUI.click(findTestObject('Page_Folders - PowerFolder/span_Paste_pica-glyph glyphicons glyphicons_ca92f0'))

WebUI.click(findTestObject('Object Repository/Page_Folders - PowerFolder/lang_Create Document'))

WebUI.setText(findTestObject('Page_Folders - PowerFolder/input_Create a new Folder_pencil'), folderName)

WebUI.click(findTestObject('Object Repository/Page_Folders - PowerFolder/button_Ok'))

WebUI.closeWindowIndex(1)

WebUI.delay(5)

WebUI.switchToWindowIndex(0)

WebUI.refresh()

WebElement btn = findShareButton(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.click(findTestObject('Links/buttonCreateLink'))

WebUI.waitForElementClickable(findTestObject('Links/buttonCreateLink'), 30, FailureHandling.CONTINUE_ON_FAILURE)

WebElement buttonCreateLink = WebUiCommonHelper.findWebElement(findTestObject('Links/buttonCreateLink'), 30)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(buttonCreateLink))

WebUI.setText(findTestObject('Page_Link - PowerFolder/lang_Password required'), 'Alexa@131190')

WebUI.delay(10)

WebUI.click(findTestObject('SettingsPopUp/buttonSave'))

WebUI.doubleClick(findTestObject('Page_Folders - PowerFolder/icon-copy'))

String my_clipboard = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor)

WebUI.navigateToUrl(my_clipboard)

WebUI.delay(3)

assert WebUI.getWindowTitle().equals('Link - PowerFolder')

WebUI.verifyElementText(findTestObject('Page_Link - PowerFolder/getPasswordBodyText'), 'This link is password protected')

WebUI.setText(findTestObject('Page_Link - PowerFolder/inputPassword'), 'Alexa@131190')

WebUI.click(findTestObject('Page_Link - PowerFolder/buttonOK'))

assert WebUI.getWindowTitle().equals('Link - PowerFolder')

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

