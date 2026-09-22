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
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import org.openqa.selenium.By as By
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.interactions.Actions as Actions
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait
import java.util.Arrays as Arrays


WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

String groupName_1 = 'Group_' + RandomStringUtils.randomNumeric(4)

String groupName_2 = 'Group_' + RandomStringUtils.randomNumeric(4)

String groupName_3 = 'Group_' + RandomStringUtils.randomNumeric(4)

String groupName_4 = 'Group_' + RandomStringUtils.randomNumeric(4)

WebUI.click(findTestObject('Object Repository/Groups/Page_Dashboard - PowerFolder/lang_Groups'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/Create_group_button'))

WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/input_Organizations_pica_group_name'),
	groupName_1)

WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/textarea_Organizations_pica_group_notes'),
	'create parent group')

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.delay(2)

def btn = findGroup(groupName_1)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Members'))

WebDriver driver = DriverFactory.getWebDriver()

WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10))


// Add subgroup number 1

WebElement inputElement1 = wait.until(
	ExpectedConditions.elementToBeClickable(
		By.xpath("//*[@id='pica_group_accounts']//input[contains(concat(' ', normalize-space(@class), ' '), ' pica-taginput-input ')]")
	)
)

inputElement1.sendKeys(groupName_2)

WebUI.delay(1)

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/create_new_subgroup_inline'))

WebUI.delay(1)


// Add subgroup number 2

WebElement inputElement2 = wait.until(
	ExpectedConditions.elementToBeClickable(
		By.xpath("//*[@id='pica_group_accounts']//input[contains(concat(' ', normalize-space(@class), ' '), ' pica-taginput-input ')]")
	)
)

inputElement2.sendKeys(groupName_3)

WebUI.delay(1)

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/create_new_subgroup_inline'))

WebUI.delay(1)


// Add subgroup number 3

WebElement inputElement3 = wait.until(
	ExpectedConditions.elementToBeClickable(
		By.xpath("//*[@id='pica_group_accounts']//input[contains(concat(' ', normalize-space(@class), ' '), ' pica-taginput-input ')]")
	)
)

inputElement3.sendKeys(groupName_4)

WebUI.delay(1)

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/create_new_subgroup_inline'))

WebUI.delay(1)

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.delay(2)

def btn_1 = findGroup(groupName_1)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn_1))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Members'))




WebElement subgroup1 = wait.until(
	ExpectedConditions.visibilityOfElementLocated(
		By.xpath(
			"//*[@id='pica_group_accounts']" +
			"//div[contains(@class,'pica-inputlist-scroller')]" +
			"//*[normalize-space(text())='${groupName_2}']"
		)
	)
)

WebUI.verifyEqual(subgroup1.isDisplayed(), true)


WebElement subgroup2 = wait.until(
	ExpectedConditions.visibilityOfElementLocated(
		By.xpath(
			"//*[@id='pica_group_accounts']" +
			"//div[contains(@class,'pica-inputlist-scroller')]" +
			"//*[normalize-space(text())='${groupName_3}']"
		)
	)
)

WebUI.verifyEqual(subgroup2.isDisplayed(), true)


WebElement subgroup3 = wait.until(
	ExpectedConditions.visibilityOfElementLocated(
		By.xpath(
			"//*[@id='pica_group_accounts']" +
			"//div[contains(@class,'pica-inputlist-scroller')]" +
			"//*[normalize-space(text())='${groupName_4}']"
		)
	)
)

WebUI.verifyEqual(subgroup3.isDisplayed(), true)

WebUI.closeBrowser()



@Keyword
WebElement findGroup(String Groupname) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(
		By.xpath(('//*[contains(@data-search-keys, \'' + Groupname) + '\')]/td[1]/span')
	)
}