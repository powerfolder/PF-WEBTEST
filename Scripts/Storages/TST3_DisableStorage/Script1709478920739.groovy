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
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebElement as WebElement
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import java.util.List as List

WebUI.callTestCase(findTestCase('Storages/PreTest/CreateStorage'), [:], FailureHandling.STOP_ON_FAILURE)

println('Global Variable: ' + GlobalVariable.StorageName)


WebUI.delay(3)

WebUI.click(findTestObject('Object Repository/Storage/Page_Storage - PowerFolder/a_Enable'))

WebUI.click(findTestObject('Storage/Page_Storage - PowerFolder/lang_Yes'))

WebUI.delay(3)

def btn = findStorage(GlobalVariable.StorageName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.click(findTestObject('Object Repository/Storage/Page_Storage - PowerFolder/a_Disable'))

WebUI.click(findTestObject('Object Repository/Storage/Page_Storage - PowerFolder/lang_Yes'))

WebUI.verifyElementVisible(findTestObject('Object Repository/Storage/Page_Storage - PowerFolder/span_Storage disabled'))

WebUI.closeBrowser()

@Keyword
WebElement findStorage(String storageName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + storageName) + '\')]/td[1]/span'))
}


