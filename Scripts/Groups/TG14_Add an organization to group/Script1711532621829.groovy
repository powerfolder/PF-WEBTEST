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


WebUiBuiltInKeywords.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

GlobalVariable.organisationName = ('Organisation_' + RandomStringUtils.randomNumeric(4))

WebUiBuiltInKeywords.click(findTestObject('Organization/SelectOrganization'))

WebUiBuiltInKeywords.click(findTestObject('Organization/DropDownToggle'))

WebUiBuiltInKeywords.click(findTestObject('Organization/CreateOrganization'))

WebUiBuiltInKeywords.setText(findTestObject('Organization/InputName'), GlobalVariable.organisationName)

WebUiBuiltInKeywords.setText(findTestObject('Organization/InputMaxNumber'), '10')

WebUiBuiltInKeywords.setText(findTestObject('Organization/Page_Organizations - PowerFolder/organization_storage'), '2')

WebUiBuiltInKeywords.click(findTestObject('Organization/SaveButton'))

WebUI.delay(2)

WebDriver driver = DriverFactory.getWebDriver()

WebElement organisation = driver.findElement(By.xpath(('//td/a[contains(text(), \'' + GlobalVariable.organisationName) + 
        '\')]'))

boolean isOrganisationCreated = organisation.isDisplayed()

WebUiBuiltInKeywords.verifyEqual(isOrganisationCreated, true)

GlobalVariable.GroupName = ('Group_' + RandomStringUtils.randomNumeric(4))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Dashboard - PowerFolder/lang_Groups'))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/Create_group_button'))

WebUiBuiltInKeywords.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/input_Organizations_pica_group_name'), 
    GlobalVariable.GroupName)

WebUiBuiltInKeywords.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/textarea_Organizations_pica_group_notes'), 
    'create group')

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.delay(2)

WebElement btn = findGroup(GlobalVariable.GroupName)

WebUI.delay(1)

WebUiBuiltInKeywords.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

assert GlobalVariable.GroupName != null

WebUiBuiltInKeywords.click(findTestObject('Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUiBuiltInKeywords.click(findTestObject('Groups/Page_Groups - PowerFolder/a_Organizations'))

WebUI.delay(2)

WebElement inputElement = driver.findElement(By.xpath('//*[@id=\'pica_group_organizations\']/div[1]/div[1]/input'))

inputElement.sendKeys(GlobalVariable.organisationName)

WebUiBuiltInKeywords.click(findTestObject('Groups/Page_Groups - PowerFolder/Organisation click'))

WebUiBuiltInKeywords.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.delay(3)

WebElement btn1 = findGroup(GlobalVariable.GroupName)

WebUiBuiltInKeywords.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

WebUiBuiltInKeywords.click(findTestObject('Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUiBuiltInKeywords.click(findTestObject('Groups/Page_Groups - PowerFolder/a_Organizations'))

WebUI.delay(2)

WebUI.verifyElementText(findTestObject('Groups/Page_Groups - PowerFolder/verify_organization'), GlobalVariable.organisationName)

WebUI.click(findTestObject('Share/Page_Groups - PowerFolder/button_Cancel'))

WebUI.closeBrowser()

@Keyword
WebElement findGroup(String groupName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + GlobalVariable.GroupName) + '\')]/td[1]/span'))
}

