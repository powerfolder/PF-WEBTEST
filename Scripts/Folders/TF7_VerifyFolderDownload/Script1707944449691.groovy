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
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import org.openqa.selenium.chrome.ChromeDriver as ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions as ChromeOptions

WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.OPTIONAL)

String folderName = getRandomFolderName()

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createFolder'))

WebUI.verifyEqual(WebUI.getText(findTestObject('lang/getCreateText')), 'Create', FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyEqual(WebUI.getText(findTestObject('lang/getFolderNameLabelText')), 'Create a new Folder', FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)

WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.setText(findTestObject('Folders/inputSearch'), folderName)

WebDriver driver = DriverFactory.getWebDriver()

WebElement folderNameElement = driver.findElement(By.xpath("//td/a[text()='$folderName']/ancestor::tr/td[1]/span"))

WebUI.delay(5)

folderNameElement.click()

WebUI.click(findTestObject('Folders/downloadLink'))

String parentWindow = driver.getWindowHandle()

JavascriptExecutor jsExecutor = ((driver) as JavascriptExecutor)

jsExecutor.executeScript('window.open()')

Set<String> allWinowHandles = driver.getWindowHandles()

for (String winHandle : allWinowHandles) {
    if (!(winHandle.equals(parentWindow))) {
        driver.switchTo().window(winHandle)
    }
}

WebUI.delay(5)

driver.get('chrome://downloads')

JavascriptExecutor downloadWindowExecutor = ((driver) as JavascriptExecutor)

String fileName = ((downloadWindowExecutor.executeScript('return document.querySelector(\'downloads-manager\').shadowRoot.querySelector(\'#downloadsList downloads-item\').shadowRoot.querySelector(\'div#content #file-link\').text')) as String)

String downloadSourceLink = ((downloadWindowExecutor.executeScript('return document.querySelector(\'downloads-manager\').shadowRoot.querySelector(\'#downloadsList downloads-item\').shadowRoot.querySelector(\'div#content #file-link\').href')) as String)

System.out.println('Downloaded File Name: ' + fileName)

WebUI.verifyEqual(fileName, folderName + '.zip')

WebUI.closeBrowser()

String getRandomFolderName() {
    String folderName = 'FDTF7' + getTimestamp()

    return folderName
}

String getTimestamp() {
    Date todaysDate = new Date()

    String formattedDate = todaysDate.format('dd_MMM_yyyy_hh_mm_ss')

    return formattedDate
}

