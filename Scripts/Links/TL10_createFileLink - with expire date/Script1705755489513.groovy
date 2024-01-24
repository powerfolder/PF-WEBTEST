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
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.util.concurrent.TimeUnit as TimeUnit


WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.OPTIONAL)

String folderName = CustomKeywords.'utility.helper.getRandomFolderName'()

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))

WebUI.verifyEqual(WebUI.getText(findTestObject('Folders/getCreateText')), 'Create', FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyEqual(WebUI.getText(findTestObject('Folders/getFolderNameLabelText')), 'Create a new Folder',  FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)
WebUI.click(findTestObject('Folders/buttonOK'))

assert WebUI.getWindowTitle().equals('Folders - PowerFolder')


WebDriver driver = DriverFactory.getWebDriver()
WebElement folder =  driver.findElement(By.xpath("//table[@id='files_files_table']/tbody/tr/td[2]/a[contains(text(),'$folderName')]"))

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(folder))


WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createDocument'))

WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)

WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.closeWindowIndex(1)
WebUI.delay(1)
WebUI.switchToWindowIndex(0)
WebUI.refresh()
WebElement btn = CustomKeywords.'share.ShareHelper.findShareButton'(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.click(findTestObject('Folders/createLink'))

WebUI.click(findTestObject('Folders/button_SaveSettings'))


WebUI.click(findTestObject('Folders/buttonCopyToClipboard'))


WebUI.click(findTestObject('Object Repository/Page_Folders - PowerFolder/button_Can read'))
WebElement settings =  driver.findElement(By.xpath("//tr[contains(@id,'share_Object')]/td[2]/div/div/div/span"))
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(settings))


WebUI.click(findTestObject('SettingsPopUp/inputValidTill'))

WebUI.click(findTestObject('Object Repository/Page_Folders - PowerFolder/span_Dec_glyphicon glyphicon-time'))

WebUI.click(findTestObject('Object Repository/Page_Folders - PowerFolder/span_Dec_glyphicon glyphicon-chevron-up'))

WebUI.click(findTestObject('Object Repository/Page_Folders - PowerFolder/span_Dec_glyphicon glyphicon-remove'))

WebUI.click(findTestObject('Object Repository/Page_Folders - PowerFolder/button_Save'))

TimeUnit.MINUTES.sleep(1);

String my_clipboard = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor)

WebUI.navigateToUrl(my_clipboard)

assert WebUI.getWindowTitle().equals('Link - PowerFolder')
Thread.sleep(5000)

WebUI.back()

assert WebUI.waitForElementVisible(findTestObject('Folders/expireTabTitle'),2)
assert  isNotBlank(WebUI.getText(findTestObject('Folders/expireTabTitle')))

WebUI.closeBrowser()


def String getRandomGroupName() {
	String folderName = 'Group_'+getTimestamp();
	return folderName;
	
}

def String getTimestamp() {
	Date todaysDate = new Date();
	String formattedDate = todaysDate.format("dd_MMM_yyyy_hh_mm_ss");
	return formattedDate;
}