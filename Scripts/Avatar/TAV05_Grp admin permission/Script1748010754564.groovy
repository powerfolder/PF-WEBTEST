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
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import java.util.Arrays as Arrays
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait
import java.time.Duration as Duration
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration

// Appel du cas de test pour ajouter un membre
WebUI.callTestCase(findTestCase('Groups/Pre_test/add member'), [:], FailureHandling.STOP_ON_FAILURE)

// Initialisation de la variable globale
println('Global Variable: ' + GlobalVariable.GroupName)

println('Global Variable: ' + GlobalVariable.userName)

// Navigation vers la page d'édition des groupes
WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Members'))

// Localisation du bouton via XPath et clic
def xpath = '/html/body/div[2]/div[1]/div[2]/div[3]/div/div/div[2]/div[4]/div[2]/table/tbody/tr[2]/td[3]/div/button'

def driver = DriverFactory.getWebDriver()

def button = driver.findElement(By.xpath(xpath))

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(button))

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/Page_Groups - PowerFolder/Page_Groups - PowerFolder/Is member and admin'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.delay(1)

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

WebUI.setText(findTestObject('Login/inputEmail'), GlobalVariable.userName)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(3)

WebUI.click(findTestObject('Object Repository/Groups/Page_Dashboard - PowerFolder/lang_Groups'))

def btn = findGroup(GlobalVariable.GroupName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Avatar'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Change'))

String projDir = RunConfiguration.getProjectDir()

String fname = projDir + '/Images/group.png'

WebUI.uploadFile(findTestObject('Accounts/AddFileButton'), fname)

WebUI.delay(3)

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Close'))

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Save'))

def btn2 = findGroup(GlobalVariable.GroupName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn2))

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Avatar'))

WebUI.verifyElementVisible(findTestObject('Groups/Page_Groups - PowerFolder/group_avatar'))

WebUI.delay(1)

WebUI.closeBrowser()




@Keyword
WebElement findGroup(String groupName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + GlobalVariable.GroupName) + '\')]/td[1]/span'))
}

