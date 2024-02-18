import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static org.apache.commons.lang.StringUtils.isNotBlank
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.util.concurrent.TimeUnit
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.OPTIONAL)

String folderName = getRandomGroupName()

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))

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
WebElement btn =  findShareButton(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.waitForElementClickable(findTestObject('Links/buttonCreateLink'), 30, FailureHandling.CONTINUE_ON_FAILURE)
WebElement buttonCreateLink = 	WebUiCommonHelper.findWebElement(findTestObject('Links/buttonCreateLink'),30)
WebUI.executeJavaScript("arguments[0].click()", Arrays.asList(buttonCreateLink))

WebUI.click(findTestObject('Folders/button_SaveSettings'))


WebUI.click(findTestObject('Folders/buttonCopyToClipboard'))

WebUI.click(findTestObject('Folders/cogWheelSettings'))
WebUI.click(findTestObject('Object Repository/Page_Folders - PowerFolder/button_Can read'))
WebElement settings =  driver.findElement(By.xpath("//tr[contains(@id,'share_Object')]/td[2]/div/div/div/span"))
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(settings))


WebUI.click(findTestObject('SettingsPopUp/inputValidTill'))

WebUI.click(findTestObject('Object Repository/Page_Folders - PowerFolder/span_Dec_glyphicon glyphicon-time'))

WebUI.click(findTestObject('Object Repository/Page_Folders - PowerFolder/span_Dec_glyphicon glyphicon-chevron-up'))

WebUI.click(findTestObject('Object Repository/Page_Folders - PowerFolder/span_Dec_glyphicon glyphicon-remove'))

WebUI.click(findTestObject('Object Repository/Page_Folders - PowerFolder/button_Save'))

TimeUnit.MINUTES.sleep(2);

String my_clipboard = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor)

WebUI.navigateToUrl(my_clipboard)

assert WebUI.getWindowTitle().equals('Link - PowerFolder')

Thread.sleep(5000)

WebUI.back()

assert WebUI.waitForElementVisible(findTestObject('Folders/expireTabTitle'),2)
assert  isNotBlank(WebUI.getText(findTestObject('Folders/expireTabTitle')))

WebUI.closeBrowser()


def String getRandomFolderName() {
	String folderName = 'Folder'+getTimestamp();
	return folderName;
}

def String getTimestamp() {
	Date todaysDate = new Date();
	String formattedDate = todaysDate.format("dd_MMM_yyyy_hh_mm_ss");
	return formattedDate;
}
def String getRandomGroupName() {
	String folderName = 'G_'+getTimestamp();
	return folderName;
	
}
def WebElement findShareButton(String fileName) {
	WebDriver driver = DriverFactory.getWebDriver()
	return driver.findElement(By.xpath("//table[@id='files_files_table']/tbody/tr/td[2]/a[contains(text(),'$fileName')]/../../td[6]/a"))
}
