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
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
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
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import java.util.Random as Random
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import java.time.Duration as Duration
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


GlobalVariable.userEmail = (('user_' + RandomStringUtils.randomNumeric(4)) + '@qa-automated-webtest.com')

String Emailid = GlobalVariable.userEmail

GlobalVariable.Name = ('My_Name_' + RandomStringUtils.randomNumeric(4))

String firstName = GlobalVariable.Name

String lastName = 'nachname ' + generateRandomString(4)

String phone = generateRandomPhoneNumber()

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.OPTIONAL)

WebUI.click(findTestObject('LeftNavigationIcons/account'))

WebUI.click(findTestObject('Accounts/CreateButton'))

WebUI.click(findTestObject('Accounts/ClickCreateAccount'))

WebUI.setText(findTestObject('Accounts/InputUserOrEmail'), Emailid)

WebUI.setText(findTestObject('Accounts/InputPassword'), GlobalVariable.Pass)

WebUI.setText(findTestObject('Accounts/InputFirstName'), firstName)

WebUI.setText(findTestObject('Accounts/InputLastName'), lastName)

WebUI.setText(findTestObject('Accounts/InputPhoneNo'), phone)

WebUI.setText(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/account_storage_overwiew'), '5')

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.delay(3)

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

WebUI.setText(findTestObject('Login/inputEmail'), Emailid)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(3)

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createFolder'))

String folderName = getRandomFolderName()

WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)

WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.delay(2)

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

WebUI.delay(2)

WebUI.setEncryptedText(findTestObject('Login/inputEmail'), 'CKkAs2Ee0vA=')

WebUI.setEncryptedText(findTestObject('Login/inputPassword'), 'PpFy9OM6JMUrpEOD1UO9247r7Yrm9E0x')

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(2)

assert WebUI.getWindowTitle().equals('Dashboard - PowerFolder')

WebUI.click(findTestObject('LeftNavigationIcons/account'))

WebDriver driver = DriverFactory.getWebDriver()

try {
    WebElement clickOnAccount = driver.findElement(By.partialLinkText(firstName))
    WebUI.delay(2)
    clickOnAccount.click()
} catch (Exception e) {
    WebElement clickOnAccount = driver.findElement(By.xpath("//a[@class='pica-name' and @data-original-title ='$Emailid']"))
    JavascriptExecutor executor = driver as JavascriptExecutor
    executor.executeScript('arguments[0].click()', clickOnAccount)
} 


WebUI.click(findTestObject('Accounts/AddFolder'))

def btn = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.click(findTestObject('Accounts/Edit_folder'))

WebUI.verifyElementPresent(findTestObject('Folders/Page_Folders - PowerFolder/Account_rescan_filesystem'), 5)

WebUI.click(findTestObject('Folders/Page_Folders - PowerFolder/Account_rescan_filesystem'), FailureHandling.STOP_ON_FAILURE)

WebUI.waitForElementPresent(findTestObject('Folders/Page_Folders - PowerFolder/Account_Popup_Rescan'), 3)

WebUI.verifyElementText(findTestObject('Folders/Page_Folders - PowerFolder/Account_Popup_Rescan'), 'Filesystem scan successfully triggered. New and changed files will show up after the folder has been completely scanned.')

WebUI.closeBrowser()

WebElement findFolder(String folderName) {
    WebDriver driver = DriverFactory.getWebDriver()

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10))

    String xpath = "//div[@id='pica_account_folders']//tr[td[contains(normalize-space(), '$folderName')]]/td[1]//span"

    return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)))
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

String generateRandomPhoneNumber() {
    Random random = new Random()

    return String.format('(%03d) %03d-%04d', random.nextInt(1000), random.nextInt(1000), random.nextInt(10000))
}

String getRandomFolderName() {
    String folderName = 'TF1' + getTimestamp()

    return folderName
}

String getTimestamp() {
    Date todaysDate = new Date()

    String formattedDate = todaysDate.format('ddMMMyyyyhhmmss')

    return formattedDate
}

