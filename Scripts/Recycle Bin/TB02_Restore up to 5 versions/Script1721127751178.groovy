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
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository

WebUI.callTestCase(findTestCase('Recycle Bin/Pre_test/Delete_File'), [:], FailureHandling.STOP_ON_FAILURE)

String folderName = GlobalVariable.folderName

String DocName = GlobalVariable.Document

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/span_Delete'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/button_Yes'))

WebUI.click(findTestObject('file_objects/recycle/Page_Folders - PowerFolder/span_recycle'))

WebElement btn = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), 5)

// Attendre la visibilité de la première ligne du tableau
WebElement firstElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//div[2]/table/tbody/tr[1]')))

// Cliquer sur le premier élément du tableau
firstElement.click()

WebUI.click(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/Restore'))

// Attendez que le tableau soit visible
WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//*[@id=\'pica_restore_versions\']/div')))

// Trouvez l'élément button spécifié dans le tableau
WebElement button = table.findElement(By.xpath('./table/tbody/tr[2]/td[6]/button'))

// Cliquez sur le bouton
button.click()

WebUI.click(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/lang_Close'))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebElement btn1 = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

WebUI.delay(1)

WebElement btn2 = findDoc(DocName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn2))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/span_Delete'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/button_Yes'))

WebUI.click(findTestObject('file_objects/recycle/Page_Folders - PowerFolder/span_recycle'))

WebElement btn3 = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn3))

WebDriverWait wait_1 = new WebDriverWait(DriverFactory.getWebDriver(), 5)

// Attendre la visibilité de la première ligne du tableau
WebElement firstElement_1 = wait_1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//div[2]/table/tbody/tr[1]')))

// Cliquer sur le premier élément du tableau
firstElement_1.click()

WebUI.click(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/Restore'))

// Attendez que le tableau soit visible
WebElement table_2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//*[@id=\'pica_restore_versions\']/div')))

// Trouvez l'élément button spécifié dans le tableau
WebElement button_2 = table_2.findElement(By.xpath('./table/tbody/tr[2]/td[6]/button'))

// Cliquez sur le bouton
button_2.click()

WebUI.click(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/lang_Close'))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebElement btn4 = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn4))

WebUI.delay(1)

WebElement btn5 = findDoc(DocName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn5))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/span_Delete'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/button_Yes'))

WebUI.click(findTestObject('file_objects/recycle/Page_Folders - PowerFolder/span_recycle'))

WebElement btn6 = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn6))

WebDriverWait wait_2 = new WebDriverWait(DriverFactory.getWebDriver(), 5)

// Attendre la visibilité de la première ligne du tableau
WebElement firstElement_2 = wait_2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//div[2]/table/tbody/tr[1]')))

// Cliquer sur le premier élément du tableau
firstElement_2.click()

WebUI.click(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/Restore'))

// Attendez que le tableau soit visible
WebElement table_3 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//*[@id=\'pica_restore_versions\']/div')))

// Trouvez l'élément button spécifié dans le tableau
WebElement button_3 = table_3.findElement(By.xpath('./table/tbody/tr[2]/td[6]/button'))

button_3.click()

WebUI.click(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/lang_Close'))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebElement btn7 = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn7))

WebUI.delay(1)

WebElement btn8 = findDoc(DocName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn8))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/span_Delete'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/button_Yes'))

WebUI.click(findTestObject('file_objects/recycle/Page_Folders - PowerFolder/span_recycle'))

WebElement btn9 = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn9))

WebDriverWait wait_3 = new WebDriverWait(DriverFactory.getWebDriver(), 5)

// Attendre la visibilité de la première ligne du tableau
WebElement firstElement_3 = wait_3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//div[2]/table/tbody/tr[1]')))

// Cliquer sur le premier élément du tableau
firstElement_3.click()

WebUI.click(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/Restore'))

// Attendez que le tableau soit visible
WebElement table_4 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//*[@id=\'pica_restore_versions\']/div')))

// Trouvez l'élément button spécifié dans le tableau
WebElement button_4 = table_4.findElement(By.xpath('./table/tbody/tr[2]/td[6]/button'))

button_4.click()

WebUI.click(findTestObject('file_objects/recycle/Page_Recycle bin - PowerFolder/lang_Close'))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebElement btn10 = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn10))

WebUI.delay(1)

def btn11 = findDoc(DocName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn11))

assert DocName != null

WebUI.closeBrowser()

@Keyword
WebElement findFolder(String folderName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//a[contains(text(),\'' + folderName) + '\')]'))
}

@Keyword
WebElement findDoc(String DocName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + DocName) + '\')]/td[1]/span'))
}

