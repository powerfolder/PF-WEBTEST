import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberBuiltinKeywords
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGBuiltinKeywords
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as WindowsBuiltinKeywords
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
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import java.time.Duration

WebUI.callTestCase(findTestCase('Recycle Bin/Pre_test/create many files'), [:], FailureHandling.STOP_ON_FAILURE)

WebDriver driver = DriverFactory.getWebDriver()

WebElement premierElement = driver.findElement(By.xpath('//div[2]/table/tbody/tr[1]'))

WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

wait.until(ExpectedConditions.elementToBeClickable(premierElement))

premierElement.click()

WebUI.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/Page_Folders - PowerFolder/select_all'))

// Click on "Delete" link in "Groups - PowerFolder" page
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/span_Delete'))

// Click on "Yes" button in "Groups - PowerFolder" page
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/button_Yes'))

WebUI.delay(3)

WebUI.click(findTestObject('file_objects/recycle/Page_Folders - PowerFolder/span_recycle'))

String folderName = GlobalVariable.folderName

WebElement btn = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebElement premierElement_bin = driver.findElement(By.xpath('//div[2]/table/tbody/tr[1]'))

wait.until(ExpectedConditions.elementToBeClickable(premierElement_bin))

premierElement_bin.click()

WebUI.click(findTestObject('Help/Page_Recycle bin - PowerFolder/select all in bin'))

WebUI.click(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/Restore'))

WebUI.verifyElementPresent(findTestObject('Recycle bin/Page_Recycle bin - PowerFolder/Page_Recycle bin - PowerFolder/div_File restored'), 
    3) 
WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebUI.delay(2)

WebElement btn1 = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

WebElement premierElement_folder = driver.findElement(By.xpath('//div[2]/table/tbody/tr[1]'))

wait.until(ExpectedConditions.elementToBeClickable(premierElement_folder))

premierElement_folder.click()

WebUI.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/Page_Folders - PowerFolder/select_all'))
// Ajoutez une attente pour s'assurer que les éléments sont sélectionnés

WebUI.delay(2)

WebUI.closeBrowser(FailureHandling.STOP_ON_FAILURE)

@Keyword
WebElement findFolder(String folderName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//a[contains(text(),\'' + folderName) + '\')]'))
}

