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
import java.nio.file.Path as Path
import java.nio.file.Files as Files
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.testobject.ConditionType

WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.OPTIONAL)

String folderName = getRandomFolderName()

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createFolder'))

WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)

WebUI.click(findTestObject('Folders/buttonOK'))

assert WebUI.getWindowTitle().equals('Folders - PowerFolder')

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createDirectoryIcon'))

WebUI.setText(findTestObject('Folders/inputFolderName'), 'dir1')

WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createDirectoryIcon'))

WebUI.setText(findTestObject('Folders/inputFolderName'), 'dir2')

WebUI.sendKeys(findTestObject('Folders/inputFolderName'), Keys.chord(Keys.ENTER))

WebUI.click(findTestObject('Links/share_icon_inside_folder'))

WebUI.click(findTestObject('Folders/shareLink'))

WebUI.click(findTestObject('Share/button_allowUpload'))

WebUI.click(findTestObject('Share/buttonSave'))

WebUI.doubleClick(findTestObject('Page_Folders - PowerFolder/icon-copy'))

String my_clipboard = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor)

WebUI.navigateToUrl(my_clipboard)

WebUI.delay(3)

assert WebUI.getWindowTitle().equals('Link - PowerFolder')

TestObject dynamicObject = new TestObject()

dynamicObject.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//span[text()='" + 'dir2' + "']"
)

boolean exists = WebUI.verifyElementPresent(dynamicObject, 10, FailureHandling.STOP_ON_FAILURE)

WebDriver driver = DriverFactory.getWebDriver()

WebElement upload = driver.findElement(By.xpath('//a[@id=\'filelink_upload\']'))

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(upload))

WebElement input = driver.findElement(By.xpath('//input[@id=\'upload_input_files\']'))

Path file = Files.createTempFile('younes', '.txt')

Files.write(file, 'Hello'.getBytes())

WebUI.uploadFile(findTestObject('Page_Link - PowerFolder/span_Add file'), file.toAbsolutePath().toString())

WebUI.uploadFile(findTestObject('Object Repository/Page_Link - PowerFolder/span_Add file'), file.toAbsolutePath().toString())

WebUI.click(findTestObject('Object Repository/Page_Link - PowerFolder/lang_Upload_1'))

WebUI.click(findTestObject('Object Repository/Page_Link - PowerFolder/button_Close'))

WebUI.click(findTestObject('Object Repository/Page_Link - PowerFolder/table'))

WebUI.verifyElementText(findTestObject('Object Repository/Page_Link - PowerFolder/table'), file.getFileName().toString())

WebUI.closeBrowser()

String getRandomFolderName() {
    String folderName = 'FTL6' + getTimestamp()

    return folderName
}

WebElement findShareButton(String fileName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + fileName) + '\')]/td[7]/a/span'))
}

String getTimestamp() {
    Date todaysDate = new Date()

    String formattedDate = todaysDate.format('ddMMMyyyyhhmmss')

    return formattedDate
}

