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
import org.openqa.selenium.interactions.Actions as Actions

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

String groupName_1 = ('Group_' + RandomStringUtils.randomNumeric(4))

String groupName_2 = 'Group_' + RandomStringUtils.randomNumeric(4)
String groupName_3 = 'Group_' + RandomStringUtils.randomNumeric(4)
String groupName_4 = 'Group_' + RandomStringUtils.randomNumeric(4)

List<String> subgroupNames = [
	groupName_2,
	groupName_3,
	groupName_4
]

WebUI.click(findTestObject('Object Repository/Groups/Page_Dashboard - PowerFolder/lang_Groups'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/Create_group_button'))

WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/input_Organizations_pica_group_name'),
	groupName_1)

WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/textarea_Organizations_pica_group_notes'),
	'create group number 1')

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.delay(2)

def btn = findGroup(groupName_1)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Members'))


WebDriver driver = DriverFactory.getWebDriver()

WebDriverWait wait = new WebDriverWait(
	driver,
	java.time.Duration.ofSeconds(10)
)

// Ajouter les 3 sous-groupes
for (String subgroupName : subgroupNames) {

	WebElement inputElement = wait.until(
		ExpectedConditions.elementToBeClickable(
			By.xpath(
				"//*[@id='pica_group_accounts']" +
				"//input[contains(concat(' ', normalize-space(@class), ' ')," +
				"' pica-taginput-input ')]"
			)
		)
	)

	inputElement.sendKeys(subgroupName)

	WebUI.delay(1)

	WebUI.click(
		findTestObject(
			'Groups/Page_Groups - PowerFolder/create_new_subgroup_inline'
		)
	)

	WebUI.delay(1)
}

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.delay(2)

def btn_1 = findGroup(groupName_1)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn_1))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Members'))

verifySubgroupsListed(subgroupNames)

WebUI.closeBrowser()


WebElement findGroup(String groupName) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(
		By.xpath(
			"//tr[contains(@data-search-keys, '${groupName}')]/td[1]/span"
		)
	)
}
void verifySubgroupsListed(List<String> subgroupNames) {
	WebDriver driver = DriverFactory.getWebDriver()

	WebDriverWait wait = new WebDriverWait(
		driver,
		java.time.Duration.ofSeconds(10)
	)

	for (String subgroupName : subgroupNames) {
		String subgroupXpath =
			"//div[@id='pica_group_accounts']" +
			"//tr[.//*[normalize-space(text())='${subgroupName}']]"

		WebElement subgroupRow = wait.until(
			ExpectedConditions.visibilityOfElementLocated(
				By.xpath(subgroupXpath)
			)
		)

		WebUI.verifyEqual(
			subgroupRow.isDisplayed(),
			true,
			FailureHandling.STOP_ON_FAILURE
		)

		WebUI.comment(
			"Sous-groupe trouvé dans la liste : ${subgroupName}"
		)
	}
}