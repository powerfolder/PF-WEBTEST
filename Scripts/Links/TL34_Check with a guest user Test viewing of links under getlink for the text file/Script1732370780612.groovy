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
import java.text.SimpleDateFormat as SimpleDateFormat
import java.util.Calendar as Calendar
import java.util.Date as Date
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils

GlobalVariable.userEmail = (('user_' + RandomStringUtils.randomNumeric(4)) + '@test.com')

String Emailid = GlobalVariable.userEmail

GlobalVariable.Name = ('My_Name_' + RandomStringUtils.randomNumeric(4))

String firstName = GlobalVariable.Name

// Plan de test
WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.OPTIONAL)

WebUI.click(findTestObject('LeftNavigationIcons/account'))

WebUI.click(findTestObject('Accounts/CreateButton'))

WebUI.click(findTestObject('Accounts/ClickCreateAccount'))

WebUI.setText(findTestObject('Accounts/InputUserOrEmail'), Emailid)

WebUI.setText(findTestObject('Accounts/InputPassword'), GlobalVariable.Pass)

WebUI.setText(findTestObject('Accounts/InputFirstName'), firstName)

WebUI.setText(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/account_storage_overwiew'), '5')

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.delay(3)

WebUI.click(findTestObject('Object Repository/Folders/Page_Folders - PowerFolder/lang_Folders'))

String folderName = getRandomFolderName()

WebUI.click(findTestObject('Object Repository/Folders/createFolderIcon'))

WebUI.click(findTestObject('Object Repository/Folders/createFolder'))

WebUI.setText(findTestObject('Object Repository/Folders/inputFolderName'), folderName)

WebUI.click(findTestObject('Object Repository/Folders/buttonOK'))

assert WebUI.getWindowTitle().equals('Folders - PowerFolder')

String textfilename = getRandomFileName()

GlobalVariable.textfilename = textfilename

WebUI.setText(findTestObject('Object Repository/Accounts/inputAccountSearch'), folderName)

WebUI.sendKeys(findTestObject('Object Repository/Accounts/inputAccountSearch'), Keys.chord(Keys.ENTER))

WebDriver driver = DriverFactory.getWebDriver()

WebElement folder = driver.findElement(By.xpath("//table[@id='files_files_table']/tbody/tr/td[2]/span/a[contains(text(),'$folderName')]"))

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(folder))

WebUI.click(findTestObject('Object Repository/Folders/createFolderIcon'))

WebUI.click(findTestObject('file_objects/text file/Page_Folders - PowerFolder/Create Text File'))

WebUI.setText(findTestObject('Object Repository/Folders/inputFolderName'), textfilename)

WebUI.click(findTestObject('Object Repository/Folders/buttonOK'))

WebUI.delay(5)

WebUI.switchToWindowIndex(0)

WebUI.delay(2)

WebUI.refresh()

WebElement btn = findShareButton(textfilename)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.click(findTestObject('Object Repository/Folders/shareLink'))

WebUI.click(findTestObject('Object Repository/Folders/button_SaveSettings'))

WebUI.doubleClick(findTestObject('Object Repository/Page_Folders - PowerFolder/icon-copy'))

String my_clipboard = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor)

WebUI.click(findTestObject('links files/Page_Folders - PowerFolder/button_Close'))

/*

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/lang_Home'))

WebElement btn2 = findMembersButton(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn2))

WebUI.setText(findTestObject('Links/Page_Folders - PowerFolder/account_emal_forlinx'), Emailid)

WebUI.sendKeys(findTestObject('Links/Page_Folders - PowerFolder/account_emal_forlinx'), Keys.chord(Keys.ENTER))

WebUI.click(findTestObject('Links/Page_Folders - PowerFolder/button_Close_account'))
*/
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

WebUI.setText(findTestObject('Login/inputEmail'), Emailid)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.switchToWindowIndex(1)

WebUI.navigateToUrl(my_clipboard)

// Vérification du titre de la fenêtre
assert WebUI.getWindowTitle().equals('Link - PowerFolder')

WebUI.delay(5)

String currentUrl = WebUI.getUrl()

WebUI.comment('L\'URL actuelle est: ' + currentUrl)

// Vérification si l'URL contient '/getlink/'
boolean containsGetLink = currentUrl.contains('/getlink/')

WebUI.comment('L\'URL contient \'/getlink/\': ' + containsGetLink)

WebUI.verifyEqual(containsGetLink, true)

WebUI.switchToWindowIndex(0)

WebUI.closeBrowser()

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

WebElement findShareButton(String fileName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + fileName) + '\')]/td[7]/a/span'))
}

WebElement findMembersButton(String folderName) {
    WebDriver driver = DriverFactory.getWebDriver()

    String xpath = ('//table[@id=\'files_files_table\']/tbody/tr[.//a[contains(text(),\'' + folderName) + '\')]]/td[5]/a'

    return driver.findElement(By.xpath(xpath))
}

String generateRandomString(int length) {
    String characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'

    StringBuilder randomString = new StringBuilder()

    Random random = new Random()

    for (int i = 0; i < length; i++) {
        randomString.append(characters.charAt(random.nextInt(characters.length())))
    }
    
    return randomString.toString().toLowerCase()
}

