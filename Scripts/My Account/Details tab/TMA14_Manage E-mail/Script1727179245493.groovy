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



// Plan de test
WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.OPTIONAL)

// Variables globales
GlobalVariable.userEmail = (('user_' + RandomStringUtils.randomNumeric(4)) + '@test.com')

String Emailid = GlobalVariable.userEmail

GlobalVariable.Name = ('My_Name_' + RandomStringUtils.randomNumeric(4))

String firstName = GlobalVariable.Name

String lastName = 'nachname ' + generateRandomString(4)

// Fonction pour générer un numéro de téléphone aléatoire
String phone = generateRandomPhoneNumber()

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

WebUI.click(findTestObject('Accounts/CreateButton'))

WebUI.click(findTestObject('Accounts/ClickCreateAccount'))

String Email2 = ('user_2_' + RandomStringUtils.randomNumeric(4)) + '@test.com'

WebUI.setText(findTestObject('Accounts/InputUserOrEmail'), Email2)

WebUI.setText(findTestObject('Accounts/InputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.delay(2)

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

WebUI.setText(findTestObject('Login/inputEmail'), Emailid)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(3)

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/My_account'))

WebUI.click(findTestObject('My_Account/Detail/Page_Profile - PowerFolder/a_Details'))

WebUI.verifyElementClickable(findTestObject('My_Account/Detail/Page_Profile - PowerFolder/Details_Manage_E-mail'))

WebUI.click(findTestObject('My_Account/Detail/Page_Profile - PowerFolder/Details_Manage_E-mail'))

WebUI.setText(findTestObject('My_Account/Detail/Page_Profile - PowerFolder/Details_manage_E-Mails'), Email2)

xpath = '//body/div[2]/div[1]/div[2]/div[4]/div/div/div[2]/div/div[1]/div[2]/button'

driver = DriverFactory.getWebDriver()

button = driver.findElement(By.xpath(xpath))

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(button))

WebUI.click(findTestObject('My_Account/Detail/Page_Profile - PowerFolder/Email_manage_button_Save'))

WebUI.verifyElementPresent(findTestObject('My_Account/Detail/Page_Profile - PowerFolder/div_Verification needed'), 3)

WebUI.click(findTestObject('My_Account/Detail/Page_Profile - PowerFolder/verification_button_Ok'))

WebUI.closeBrowser()

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

