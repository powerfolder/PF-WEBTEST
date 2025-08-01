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

WebUI.callTestCase(findTestCase('CleanUpScripts/CL1_CleanFolders'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.OPTIONAL)

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createFolder'))

String folderName = getRandomFolderName()

String lan = GlobalVariable.LANG

if (!(lan.equals('GERMAN'))) {
    WebUI.verifyEqual(WebUI.getText(findTestObject('lang/getCreateText')), 'Create', FailureHandling.CONTINUE_ON_FAILURE)

    WebUI.verifyEqual(WebUI.getText(findTestObject('lang/getFolderNameLabelText')), 'Create a new Folder', FailureHandling.CONTINUE_ON_FAILURE)
} else {
    WebUI.verifyEqual(WebUI.getText(findTestObject('lang/getCreateText')), 'Erstellen', FailureHandling.CONTINUE_ON_FAILURE)

    WebUI.verifyEqual(WebUI.getText(findTestObject('lang/getFolderNameLabelText')), 'Ordner neu erstellen', FailureHandling.CONTINUE_ON_FAILURE)
}

WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)

WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.setText(findTestObject('Folders/inputSearch'), folderName)

WebDriver driver = DriverFactory.getWebDriver()

WebElement folder = driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + folderName) + '\')]/td[1]/span'))

boolean isfolderCreated = folder.isDisplayed()

WebUI.verifyEqual(isfolderCreated, true)

WebUI.closeBrowser()

String getRandomFolderName() {
    String folderName = 'TF1' + getTimestamp()

    return folderName
}

String getTimestamp() {
    Date todaysDate = new Date()

    String formattedDate = todaysDate.format('ddMMMyyyyhhmmss')

    return formattedDate
}

