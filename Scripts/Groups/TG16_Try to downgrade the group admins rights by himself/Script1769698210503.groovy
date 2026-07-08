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
import com.kms.katalon.core.testobject.ConditionType as ConditionType

// Appel du cas de test pour ajouter un membre
WebUI.callTestCase(findTestCase('Groups/Pre_test/add member'), [:], FailureHandling.STOP_ON_FAILURE)

// Génération d'un nom d'utilisateur aléat
println('Global Variable: ' + GlobalVariable.userName)

println('Global Variable: ' + GlobalVariable.GroupName)

// Navigation vers la section "Edit"
WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

// Navigation vers la section "Members"
WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Members'))

String userLocalPart = GlobalVariable.userName.contains('@') ? GlobalVariable.userName.substring(0, GlobalVariable.userName.indexOf('@')) : GlobalVariable.userName
def xpath = "//div[@id='pica_group_accounts']//table//tr[@data-userdata and contains(@data-userdata,'" + userLocalPart + "')]//button[contains(@class,'dropdown-toggle')]"

def driver = DriverFactory.getWebDriver()

new WebDriverWait(driver, java.time.Duration.ofSeconds(15)).until(
    ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)))

def button = driver.findElement(By.xpath(xpath))

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(button))

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/a_Is member and admin'))

// Clic sur le bouton "Save"
WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))

// Vérification de la présence du message de confirmation de mise à jour du groupe
WebUI.verifyElementPresent(findTestObject('Groups/Page_Groups - PowerFolder/div_Group updated'), 5)

new WebDriverWait(driver, java.time.Duration.ofSeconds(15)).until(
    ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='pica_group_dialog' and contains(concat(' ',normalize-space(@class),' '),' show ')]")))

WebUI.delay(2)

WebElement btn1 = findGroup(GlobalVariable.GroupName)

WebUiBuiltInKeywords.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Members'))

new WebDriverWait(driver, java.time.Duration.ofSeconds(15)).until(
    ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)))

def button1 = driver.findElement(By.xpath(xpath))

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(button1))

WebUI.delay(2)

String memberLocalPart = GlobalVariable.userName.contains('@') ? GlobalVariable.userName.substring(0, GlobalVariable.userName.indexOf('@')) : GlobalVariable.userName

TestObject isAdminRoleSelected = new TestObject('isAdminRoleSelected')

isAdminRoleSelected.addProperty('xpath', ConditionType.EQUALS,
    "//div[@id='pica_group_accounts']//tr[@data-userdata" +
    " and contains(@data-userdata,'" + memberLocalPart + "')" +
    " and contains(@data-userdata,'\"isGroupAdmin\":true')]")

WebUI.verifyElementPresent(isAdminRoleSelected, 15)

WebUI.closeBrowser()

@Keyword
WebElement findGroup(String groupName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + GlobalVariable.GroupName) + '\')]/td[1]/span'))
}

