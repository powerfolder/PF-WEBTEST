import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static org.apache.commons.lang.StringUtils.isNotBlank
import com.kms.katalon.core.webui.common.WebUiCommonHelper
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
import java.util.concurrent.TimeUnit as TimeUnit
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import internal.GlobalVariable as GlobalVariable

WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.OPTIONAL)
String folderName = getRandomFolderName()
WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))
WebUI.verifyEqual(WebUI.getText(findTestObject('lang/getCreateText')), 'Create', FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyEqual(WebUI.getText(findTestObject('lang/getFolderNameLabelText')), 'Create a new Folder',  FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Folders/inputFolderName'),folderName)
WebUI.click(findTestObject('Folders/buttonOK'))
assert WebUI.getWindowTitle().equals('Folders - PowerFolder')
WebElement btn =findShareButton(folderName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))
WebUI.click(findTestObject('Folders/createLink'))
WebUI.click(findTestObject('Object Repository/Page_Folders - PowerFolder/button_Save (1)'))
WebUI.waitForElementClickable(findTestObject('Links/buttonCreateLink'), 30, FailureHandling.CONTINUE_ON_FAILURE)
WebElement buttonCreateLink = 	WebUiCommonHelper.findWebElement(findTestObject('Links/buttonCreateLink'),30)
WebUI.executeJavaScript("arguments[0].click()", Arrays.asList(buttonCreateLink))
//WebUI.click(findTestObject('Object Repository/Page_Folders - PowerFolder/button_Can read'))
WebUI.click(findTestObject('Page_Folders - PowerFolder/inputValidTill'))
WebUI.sendKeys(findTestObject('Page_Folders - PowerFolder/inputValidTill'), Keys.chord(Keys.TAB))
WebUI.setText(findTestObject('Object Repository/Page_Folders - PowerFolder/input_MaxDownloads'), 	'1')
WebUI.delay(10)
WebUI.click(findTestObject('SettingsPopUp/buttonSave'))

WebUI.click(findTestObject('Page_Folders - PowerFolder/icon-copy'))
TimeUnit.MINUTES.sleep(2);
WebUI.executeJavaScript('window.open();', [])
WebUI.switchToWindowIndex(1)
String my_clipboard = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor)
WebUI.navigateToUrl(my_clipboard)
assert WebUI.getWindowTitle().equals('Link - PowerFolder')
WebUI.click(findTestObject('Page_Folders - PowerFolder/closeExpiredAlert'))
WebUI.verifyElementPresent(findTestObject('lang/expiredlinkText'), 30)
WebUI.closeBrowser()


def String getRandomFolderName() {
	String folderName = 'TL3FL'+getTimestamp();
	return folderName;
}
def WebElement findShareButton(String fileName) {
	WebDriver driver = DriverFactory.getWebDriver()
	return driver.findElement(By.xpath("//table[@id='files_files_table']/tbody/tr/td[2]/a[contains(text(),'$fileName')]/../../td[6]/a"))
}

def String getTimestamp() {
	Date todaysDate = new Date();
	String formattedDate = todaysDate.format("ddMMMyyyyhhmmss");
	return formattedDate;
}
