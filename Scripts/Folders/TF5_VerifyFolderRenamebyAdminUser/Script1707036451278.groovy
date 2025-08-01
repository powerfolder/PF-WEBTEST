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
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.DataFlavor as DataFlavor
import org.openqa.selenium.By as By
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.nio.file.Path as Path
import java.nio.file.Files as Files

WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.OPTIONAL)

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createFolder'))

String folderName = getRandomFolderName()

WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)

WebUI.click(findTestObject('Folders/buttonOK'))

//WebUI.verifyEqual(WebUI.getText(findTestObject('Folders/getFolderCreationNotification')), 'Folder created')
Thread.sleep(7000)

WebDriver driver = DriverFactory.getWebDriver()

WebUI.delay(5)

WebElement folderNameElement = driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + folderName) + '\')]/td[1]/span '))

folderNameElement.click()

WebUI.click(findTestObject('Folders/rename'))

String newFolderName = folderName + '_RENAME'

WebUI.setText(findTestObject('Folders/inputFolderName'), newFolderName)

WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.setText(findTestObject('Folders/inputSearch'), newFolderName)

WebElement grpName = driver.findElement(By.xpath("//a[@class='pica-name'  and text () ='$newFolderName']"))

WebUI.verifyEqual(grpName.isDisplayed(), true)

//String notification = grpName.getText()
//WebUI.verifyEqual(notification, "Renamed "+folderName+" to "+folderName+"_RENAME")
WebUI.closeBrowser()

String getRandomFolderName() {
    String folderName = 'FD' + getTimestamp()

    return folderName
}

String getTimestamp() {
    Date todaysDate = new Date()

    String formattedDate = todaysDate.format('ddMMMyyyyhhmm_ss')

    return formattedDate
}

