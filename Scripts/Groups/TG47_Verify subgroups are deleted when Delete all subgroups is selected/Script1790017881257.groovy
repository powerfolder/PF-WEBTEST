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
import com.kms.katalon.core.llm.keyword.LlmKeywords as LLM

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

String parentGroup = 'parentGroup_' + RandomStringUtils.randomNumeric(4)

String subGroup_1 = 'subGroup_1_' + RandomStringUtils.randomNumeric(4)

String subGroup_2 = 'subGroup_2_' + RandomStringUtils.randomNumeric(4)

String subGroup_3 = 'subGroup_3_' + RandomStringUtils.randomNumeric(4)

List<String> subgroupNames = [subGroup_1, subGroup_2, subGroup_3]

WebUI.click(findTestObject('Object Repository/Groups/Page_Dashboard - PowerFolder/lang_Groups'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/Create_group_button'))

WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/input_Organizations_pica_group_name'), 
    parentGroup)

WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/textarea_Organizations_pica_group_notes'), 
    'create a parent group')

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.delay(2)

def btn = findGroup(parentGroup)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Members'))

WebDriver driver = DriverFactory.getWebDriver()

WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10))

// Ajouter les 3 sous-groupes
for (String subgroupName : subgroupNames) {
    WebElement inputElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(('//*[@id=\'pica_group_accounts\']' + 
                '//input[contains(concat(\' \', normalize-space(@class), \' \'),') + '\' pica-taginput-input \')]')))

    inputElement.sendKeys(subgroupName)

    WebUI.delay(1)

    WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/create_new_subgroup_inline'))

    WebUI.delay(1)
}

// Sauvegarder après l'ajout des 3 sous-groupes
WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.delay(2)

def btn_1 = findGroup(parentGroup)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn_1))

WebUI.delay(2)

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/a_Delete'))

WebUI.check(findTestObject('Groups/Page_Groups - PowerFolder/check_box_delete all subgroups'), FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/Conferm_delete'))

WebUI.refresh()

WebUI.delay(2)

// Vérifier que les trois sous-groupes ont disparu
for (String subgroupName : subgroupNames) {
	boolean disappeared = wait.until {
		driver.findElements(By.xpath(
			"//*[contains(@data-search-keys, '${subgroupName}')]/td[1]/span"
		)).isEmpty()
	}

	WebUI.verifyEqual(
		disappeared,
		true,
		FailureHandling.STOP_ON_FAILURE
	)

	WebUI.comment("Subgroup '${subgroupName}' has been deleted successfully")
}

WebUI.closeBrowser()



@Keyword
WebElement findGroup(String Groupname) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + Groupname) + '\')]/td[1]/span'))
}

