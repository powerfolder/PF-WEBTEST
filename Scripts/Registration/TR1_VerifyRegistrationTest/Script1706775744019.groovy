import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.openqa.selenium.By as By
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint

WebUI.openBrowser('')

WebUI.maximizeWindow()

WebUI.navigateToUrl(GlobalVariable.URL)

WebUI.verifyEqual(WebUI.getWindowTitle(), 'Login - PowerFolder')

WebUI.verifyElementClickable(findTestObject('Login/registerNewAccountLink'))

WebUI.click(findTestObject('Registration/Page_Login - PowerFolder/ClickOnRegisterNewAccount'))

GlobalVariable.userEmail = generateRandomEmail().toLowerCase()

WebUI.setText(findTestObject('Registration/Page_Register - PowerFolder/input_Cration_Register_Email'), GlobalVariable.userEmail)

WebUI.setText(findTestObject('Registration/Page_Register - PowerFolder/input_Cration_Register_Password'), GlobalVariable.Pass)

WebUI.check(findTestObject('Registration/Page_Register - PowerFolder/AcceptTermsAndConditions'))

WebUI.click(findTestObject('Registration/Page_Register - PowerFolder/RegisterButton'))

WebUI.delay(3)

WebUI.verifyEqual(WebUI.getWindowTitle(), 'Check mails - PowerFolder')

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.OPTIONAL)

WebUI.click(findTestObject('LeftNavigationIcons/account'))

WebElement btn = findAccount(GlobalVariable.userEmail)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.verifyElementClickable(findTestObject('Accounts/Edit_Accounts - PowerFolder/Edit_account'))

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/Edit_account'))

WebUI.check(findTestObject('Accounts/Edit_Accounts - PowerFolder/button_Active'))

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.navigateToUrl(GlobalVariable.URL)

WebUI.setText(findTestObject('Login/inputEmail'), GlobalVariable.userEmail)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(2)

WebUI.verifyEqual(WebUI.getWindowTitle(), 'Folders - PowerFolder')

WebElement findAccount(String emailId) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + emailId) + '\')]/td[1]/span'))
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

String generateRandomEmail() {
    return generateRandomString(8) + '@email.com'
}

String generateRandomPhoneNumber() {
    Random random = new Random()

    return String.format('(%03d) %03d-%04d', random.nextInt(1000), random.nextInt(1000), random.nextInt(10000))
}

/*
 
 WebUI.openBrowser('')
 
 WebUI.maximizeWindow()
 
 WebUI.navigateToUrl("https://yopmail.com/")
 
 WebUI.setText(findTestObject('YopMail/YopMailSearchMailBar'), emailId)
 
 WebUI.click(findTestObject('YopMail/ArrowNexrButton'))
 
 WebUI.switchToFrame(findTestObject('YopMail/IFrame'),10)
 
 WebUI.delay(3)
 
 WebUI.click(findTestObject('YopMail/ActivateAccount'))
 
 String newWindowHandle = WebUI.switchToWindowTitle('Login - PowerFolder')
 
 WebUI.switchToWindowTitle("Login - PowerFolder")
 
 WebUI.setText(findTestObject('Registration/EnterLoginPassword'), "Alexa@1311900")
 
 WebUI.delay(3)
 
 WebUI.click(findTestObject('Registration/LoginButton'))
 
 WebUI.delay(3)
 
 assert WebUI.getWindowTitle().equals('Folders - PowerFolder')
 
 */ 