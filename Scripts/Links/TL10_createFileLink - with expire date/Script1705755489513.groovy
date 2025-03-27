import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static org.apache.commons.lang.StringUtils.isNotBlank
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
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
import java.util.concurrent.TimeUnit as TimeUnit
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import java.nio.file.Path as Path
import java.nio.file.Files as Files
import java.text.SimpleDateFormat as SimpleDateFormat
import java.util.Calendar as Calendar
import java.util.Date as Date
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import java.util.Random as Random

WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.OPTIONAL)

String folderName = getRandomGroupName()

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createFolder'))

WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)

WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.delay(2)

assert WebUI.getWindowTitle().equals('Folders - PowerFolder')

WebUI.setText(findTestObject('Accounts/inputAccountSearch'), folderName)

WebUI.sendKeys(findTestObject('Accounts/inputAccountSearch'), Keys.chord(Keys.ENTER))

WebDriver driver = DriverFactory.getWebDriver()

WebElement folder = driver.findElement(By.xpath("//table[@id='files_files_table']/tbody/tr/td[2]/span/a[contains(text(),'$folderName')]"))

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(folder))

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createDocument'))

String DocName = 'Doc_num_' + RandomStringUtils.randomNumeric(4)

WebUI.setText(findTestObject('Folders/inputFolderName'), DocName)

WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.closeWindowIndex(1)

WebUI.delay(1)

WebUI.switchToWindowIndex(0)

WebUI.refresh()

WebUI.delay(2)

WebElement btn = findShareButton(DocName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.waitForElementClickable(findTestObject('Links/buttonCreateLink'), 30, FailureHandling.CONTINUE_ON_FAILURE)

WebElement buttonCreateLink = WebUiCommonHelper.findWebElement(findTestObject('Links/buttonCreateLink'), 30)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(buttonCreateLink))

WebUI.click(findTestObject('Page_Folders - PowerFolder/inputValidTill'))

// Générer la date et l'heure actuelles avec une minute ajoutée
String newDateTime = generateDateTimePlusTenSeconds()

// Afficher la date dans la console (une seule fois)
println('Date et heure générées : ' + newDateTime)

// Effacer et saisir la nouvelle date dans le champ
TestObject accountValidTill = findTestObject('Page_Folders - PowerFolder/inputValidTill')

WebUI.executeJavaScript('arguments[0].value = ""', [WebUI.findWebElement(accountValidTill)])

WebUI.setText(accountValidTill, newDateTime)

WebUI.sendKeys(findTestObject('Page_Folders - PowerFolder/inputValidTill'), Keys.chord(Keys.TAB))

WebUI.delay(2)

WebUI.click(findTestObject('Folders/button_SaveSettings'))

WebUI.delay(2)

WebUI.doubleClick(findTestObject('Page_Folders - PowerFolder/icon-copy'))

WebUI.delay(10)

WebUI.executeJavaScript('window.open();', [])

WebUI.switchToWindowIndex(1)

String my_clipboard = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor)

WebUI.navigateToUrl(my_clipboard)

WebUI.delay(3)

assert WebUI.getWindowTitle().equals('Link - PowerFolder')

WebUI.click(findTestObject('Page_Folders - PowerFolder/closeExpiredAlert'))

WebUI.verifyElementPresent(findTestObject('lang/expiredlinkText'), 30)

WebUI.closeBrowser()

String getRandomFolderName() {
    String folderName = 'Folder' + getTimestamp()

    return folderName
}

String getTimestamp() {
    Date todaysDate = new Date()

    String formattedDate = todaysDate.format('MM_dd_yyyy_hh_mm_ss')

    return formattedDate
}

String getRandomGroupName() {
    String folderName = 'G_' + getTimestamp()

    return folderName
}

WebElement findShareButton(String fileName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + fileName) + '\')]/td[7]/a/span'))
}

String generateDateTimePlusTenSeconds() {
    Calendar calendar = Calendar.getInstance()

    calendar.add(Calendar.SECOND, 10)

    SimpleDateFormat sdf = new SimpleDateFormat('MM/dd/yyyy HH:mm:ss')

    return sdf.format(calendar.getTime())
}

