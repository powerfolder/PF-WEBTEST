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

WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.OPTIONAL)

String folderName = getRandomFolderName()

WebUI.click(findTestObject('Object Repository/Folders/createFolderIcon'))

WebUI.click(findTestObject('Object Repository/Folders/createFolder'))

WebUI.verifyEqual(WebUI.getText(findTestObject('Object Repository/lang/getCreateText')), 'Create', FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyEqual(WebUI.getText(findTestObject('Object Repository/lang/getFolderNameLabelText')), 'Create a new Folder',
	FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyElementClickable(findTestObject('Object Repository/Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.setText(findTestObject('Object Repository/Folders/inputFolderName'), folderName)

WebUI.click(findTestObject('Object Repository/Folders/buttonOK'))

assert WebUI.getWindowTitle().equals('Folders - PowerFolder')

String textfilename = getRandomFileName()

GlobalVariable.textfilename = textfilename

WebUI.setText(findTestObject('Object Repository/Accounts/inputAccountSearch'), folderName)

WebUI.sendKeys(findTestObject('Object Repository/Accounts/inputAccountSearch'), Keys.chord(Keys.ENTER))

WebDriver driver = DriverFactory.getWebDriver()

WebElement folder = driver.findElement(By.xpath("//table[@id='files_files_table']/tbody/tr/td[2]/a[contains(text(),'$folderName')]"))

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(folder))

WebUI.click(findTestObject('Object Repository/Folders/createFolderIcon'))


String getRandomFolderName() {
	String folderName = 'Folder_' + getTimestamp()

	return folderName
}

String getRandomFileName() {
	String fileName = 'File_' + getTimestamp()

	return fileName
}

String getTimestamp() {
	Date todaysDate = new Date()

	String formattedDate = todaysDate.format('dd_MMM_yyyy_hh_mm_ss')

	return formattedDate
}

WebElement findShareButton(String textfilename) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(By.xpath("//table[@id='files_files_table']/tbody/tr/td[2]/a[contains(text(),'$textfilename')]/../../td[7]/a"))
}

