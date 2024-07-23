import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGBuiltinKeywords
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import java.util.Arrays as Arrays
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait

String folderName = GlobalVariable.folderName
String DocName = GlobalVariable.Document

WebUI.callTestCase(findTestCase('Recycle Bin/Pre_test/restore 5 versions'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.delay(2)

//First one
restoreVersion(2)
WebUI.delay(2)

//Second
restoreDocumentVersion(DocName, 3)
WebUI.delay(2)

//Third
restoreDocumentVersion(DocName, 4)
WebUI.delay(2)

//Fourth
restoreDocumentVersion(DocName, 5)
WebUI.delay(2)

//Fifth
restoreDocumentVersion(DocName, 6)
WebUI.delay(2)

// Final check
def btn5 = findDoc(DocName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn5))
assert DocName != null

WebUI.closeBrowser()

@Keyword
WebElement findFolder(String folderName) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath('//a[contains(text(),\'' + folderName + '\')]'))
}

@Keyword
WebElement findDoc(String DocName) {
    WebDriver driver = DriverFactory.getWebDriver()
    println("Searching for document with name: " + DocName)
    WebDriverWait wait = new WebDriverWait(driver, 10)
    return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//*[contains(@data-search-keys, \'' + DocName + '\')]/td[1]/span')))
}

@Keyword
void restoreVersion(int rowIndex) {
    WebUI.click(findTestObject('Recycle bin/Page_Folders - PowerFolder/a_Restore'))
    WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), 5)
    WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//*[@id=\'pica_restore_versions\']/div')))
    WebElement button = table.findElement(By.xpath('./table/tbody/tr[' + rowIndex + ']/td[6]/button'))
    button.click()
    WebUI.click(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/lang_Close'))
}

@Keyword
void restoreDocumentVersion(String docName, int rowIndex) {
    WebElement btn = findDoc(docName)
    WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))
    restoreVersion(rowIndex)
}
