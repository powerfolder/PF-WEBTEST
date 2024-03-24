import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords
import internal.GlobalVariable
import org.openqa.selenium.Keys
import org.apache.commons.lang3.RandomStringUtils
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import java.util.Arrays
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import com.kms.katalon.core.webui.driver.DriverFactory

@Keyword
WebElement findGroup(String groupName) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + groupName) + '\')]/td[1]/span'))
}

user = ('user_' + RandomStringUtils.randomNumeric(4) + '@test.com')
GlobalVariable.userName = user

WebUiBuiltInKeywords.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

WebUiBuiltInKeywords.click(findTestObject('LeftNavigationIcons/account'))

WebUiBuiltInKeywords.click(findTestObject('Accounts/CreateButton'))

WebUiBuiltInKeywords.click(findTestObject('Accounts/ClickCreateAccount'))

WebUiBuiltInKeywords.setText(findTestObject('Accounts/InputUserOrEmail'), user)

WebUiBuiltInKeywords.setText(findTestObject('Accounts/InputPassword'), 'Alexa@131190')

WebUiBuiltInKeywords.setText(findTestObject('Accounts/InputQuota'), '5')

WebUiBuiltInKeywords.click(findTestObject('Accounts/SaveButton'))

WebDriver driver = DriverFactory.getWebDriver()

WebElement userElement = driver.findElement(By.xpath(('//td/a[contains(text(),\'' + user ) + '\')]'))

boolean isUserCreated = userElement.isDisplayed()

WebUiBuiltInKeywords.verifyEqual(isUserCreated, true)

String groupName = 'Group_' + RandomStringUtils.randomNumeric(4)

GlobalVariable.GroupName = groupName

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Dashboard - PowerFolder/lang_Groups'))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/span_Log out_pica-icon pica-glyph glyphicon_0824b5'))

WebUiBuiltInKeywords.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/input_Organizations_pica_group_name'),
	GlobalVariable.GroupName)

WebUiBuiltInKeywords.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/textarea_Organizations_pica_group_notes'),
	'create group')

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))

def btn = findGroup(groupName)

WebUiBuiltInKeywords.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

assert groupName != null

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Members'))

WebElement inputElement = driver.findElement(By.xpath("//*[@id='pica_group_accounts']/div[1]/div[1]/input"))
inputElement.sendKeys(user)

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/user click'))

WebUiBuiltInKeywords.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Save'))

WebElement btn1 = findGroup(GlobalVariable.GroupName)

WebUiBuiltInKeywords.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))






