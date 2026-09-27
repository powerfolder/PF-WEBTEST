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

String subgroupName = 'Subgroup_' + RandomStringUtils.randomNumeric(4)

String parentGroup_1 = 'Parent_' + RandomStringUtils.randomNumeric(4)

String parentGroup_2 = 'Parent_' + RandomStringUtils.randomNumeric(4)

String parentGroup_3 = 'Parent_' + RandomStringUtils.randomNumeric(4)

WebUI.click(findTestObject('Object Repository/Groups/Page_Dashboard - PowerFolder/lang_Groups'))


// Create subgroup

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/Create_group_button'))

WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/input_Organizations_pica_group_name'),
	subgroupName)

WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/textarea_Organizations_pica_group_notes'),
	'create subgroup')

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.delay(2)


// Create parent group 1

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/Create_group_button'))

WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/input_Organizations_pica_group_name'),
	parentGroup_1)

WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/textarea_Organizations_pica_group_notes'),
	'create parent group 1')

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.delay(2)


// Create parent group 2

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/Create_group_button'))

WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/input_Organizations_pica_group_name'),
	parentGroup_2)

WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/textarea_Organizations_pica_group_notes'),
	'create parent group 2')

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.delay(2)


// Create parent group 3

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/Create_group_button'))

WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/input_Organizations_pica_group_name'),
	parentGroup_3)

WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/textarea_Organizations_pica_group_notes'),
	'create parent group 3')

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.delay(2)


WebDriver driver = DriverFactory.getWebDriver()

WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10))


// Assign subgroup to parent group 1

def btnParent1 = findGroup(parentGroup_1)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btnParent1))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Members'))

WebElement inputParent1 = driver.findElement(By.xpath(
	"//*[@id='pica_group_accounts']//input[contains(concat(' ', normalize-space(@class), ' '), ' pica-taginput-input ')]"
))

inputParent1.sendKeys(subgroupName)

WebElement resultParent1 = wait.until(
	ExpectedConditions.elementToBeClickable(
		By.xpath(
			"(//div[@id='pica_group_accounts']//ul[contains(@class,'pica-taginput-dropdown')]/li[not(contains(@class,'pica-taginput-dropdown-fixed'))])[1]/a"
		)
	)
)

resultParent1.click()

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.delay(2)


// Assign subgroup to parent group 2

def btnParent2 = findGroup(parentGroup_2)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btnParent2))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Members'))

WebElement inputParent2 = driver.findElement(By.xpath(
	"//*[@id='pica_group_accounts']//input[contains(concat(' ', normalize-space(@class), ' '), ' pica-taginput-input ')]"
))

inputParent2.sendKeys(subgroupName)

WebElement resultParent2 = wait.until(
	ExpectedConditions.elementToBeClickable(
		By.xpath(
			"(//div[@id='pica_group_accounts']//ul[contains(@class,'pica-taginput-dropdown')]/li[not(contains(@class,'pica-taginput-dropdown-fixed'))])[1]/a"
		)
	)
)

resultParent2.click()

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.delay(2)


// Assign subgroup to parent group 3

def btnParent3 = findGroup(parentGroup_3)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btnParent3))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Members'))

WebElement inputParent3 = driver.findElement(By.xpath(
	"//*[@id='pica_group_accounts']//input[contains(concat(' ', normalize-space(@class), ' '), ' pica-taginput-input ')]"
))

inputParent3.sendKeys(subgroupName)

WebElement resultParent3 = wait.until(
	ExpectedConditions.elementToBeClickable(
		By.xpath(
			"(//div[@id='pica_group_accounts']//ul[contains(@class,'pica-taginput-dropdown')]/li[not(contains(@class,'pica-taginput-dropdown-fixed'))])[1]/a"
		)
	)
)

resultParent3.click()

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.delay(2)


// Verify subgroup in parent group 1 tooltip

WebElement membersParent1 = wait.until(
	ExpectedConditions.visibilityOfElementLocated(
		By.xpath("//tr[contains(@data-search-keys, '${parentGroup_1}')]//td[@data-tooltip]")
	)
)

new Actions(driver).moveToElement(membersParent1).perform()

WebUI.delay(1)

WebElement tooltipParent1 = wait.until(
	ExpectedConditions.visibilityOfElementLocated(
		By.cssSelector('div.tooltip.show')
	)
)

String tooltipTextParent1 = tooltipParent1.getText()

WebUI.verifyMatch(
	tooltipTextParent1,
	"(?s).*Subgroup:\\s*${java.util.regex.Pattern.quote(subgroupName)}.*",
	true
)


// Verify subgroup in parent group 2 tooltip

WebElement membersParent2 = wait.until(
	ExpectedConditions.visibilityOfElementLocated(
		By.xpath("//tr[contains(@data-search-keys, '${parentGroup_2}')]//td[@data-tooltip]")
	)
)

new Actions(driver).moveToElement(membersParent2).perform()

WebUI.delay(1)

WebElement tooltipParent2 = wait.until(
	ExpectedConditions.visibilityOfElementLocated(
		By.cssSelector('div.tooltip.show')
	)
)

String tooltipTextParent2 = tooltipParent2.getText()

WebUI.verifyMatch(
	tooltipTextParent2,
	"(?s).*Subgroup:\\s*${java.util.regex.Pattern.quote(subgroupName)}.*",
	true
)


// Verify subgroup in parent group 3 tooltip

WebElement membersParent3 = wait.until(
	ExpectedConditions.visibilityOfElementLocated(
		By.xpath("//tr[contains(@data-search-keys, '${parentGroup_3}')]//td[@data-tooltip]")
	)
)

new Actions(driver).moveToElement(membersParent3).perform()

WebUI.delay(1)

WebElement tooltipParent3 = wait.until(
	ExpectedConditions.visibilityOfElementLocated(
		By.cssSelector('div.tooltip.show')
	)
)

String tooltipTextParent3 = tooltipParent3.getText()

WebUI.verifyMatch(
	tooltipTextParent3,
	"(?s).*Subgroup:\\s*${java.util.regex.Pattern.quote(subgroupName)}.*",
	true
)

WebUI.closeBrowser()


@Keyword
WebElement findGroup(String Groupname) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(
		By.xpath(('//*[contains(@data-search-keys, \'' + Groupname) + '\')]/td[1]/span')
	)
}