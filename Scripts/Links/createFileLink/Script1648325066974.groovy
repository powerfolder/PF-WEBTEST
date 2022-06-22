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
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor

WebUI.callTestCase(findTestCase('Folders/Should Go to Folderstable'), [:], FailureHandling.OPTIONAL)

String folderName = org.apache.commons.lang.RandomStringUtils.random(9, true, true)

WebUI.click(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/span_Paste_pica-glyph glyphicons glyphicons_ca92f0'))

WebUI.click(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/a_Create Folder                            _852e7b'))

WebUI.setText(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/input_Create a new Folder_pencil'), folderName)

WebUI.sendKeys(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/input_Create a new Folder_pencil'), Keys.chord(
		Keys.ENTER))

assert WebUI.getWindowTitle().equals('Folders - PowerFolder')


WebDriver driver = DriverFactory.getWebDriver()
WebElement folder =  driver.findElement(By.xpath("//table[@id='files_files_table']/tbody/tr/td[2]/a[contains(text(),'$folderName')]"))

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(folder))


WebUI.click(findTestObject('Page_Folders - PowerFolder/span_Paste_pica-glyph glyphicons glyphicons_ca92f0'))

WebUI.click(findTestObject('Object Repository/Page_Folders - PowerFolder/lang_Create Document'))

WebUI.setText(findTestObject('Page_Folders - PowerFolder/input_Create a new Folder_pencil'), folderName)

WebUI.click(findTestObject('Object Repository/Page_Folders - PowerFolder/button_Ok'))

WebUI.closeWindowIndex(1)
WebUI.delay(1)
WebUI.switchToWindowIndex(0)
WebUI.refresh()

WebElement btn = CustomKeywords.'share.ShareHelper.findShareButton'(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.click(findTestObject('Page_Folders - PowerFolder/button_Create link'))


WebUI.click(findTestObject('Page_Folders - PowerFolder/icon-copy'))

String my_clipboard = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor);

WebUI.navigateToUrl(my_clipboard)

assert WebUI.getWindowTitle().equals('Link - PowerFolder')


WebUI.closeBrowser()

