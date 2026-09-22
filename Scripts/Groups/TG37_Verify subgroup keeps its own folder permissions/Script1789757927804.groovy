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
import helpers.Helper as Helper
import org.openqa.selenium.interactions.Actions as Actions
import com.kms.katalon.core.llm.keyword.LlmKeywords as LLM

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

String groupName_parent = 'Group_' + RandomStringUtils.randomNumeric(4)

String groupName_sub = 'Group_' + RandomStringUtils.randomNumeric(4)

String Folder_parent = 'Folder_parent_' + Helper.getRandomFolderName()

String Folder_sub = 'Folder_sub_' + Helper.getRandomFolderName()

WebUI.click(findTestObject('Object Repository/Groups/Page_Dashboard - PowerFolder/lang_Groups'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/Create_group_button'))

WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/input_Organizations_pica_group_name'),
	groupName_parent)

WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/textarea_Organizations_pica_group_notes'),
	'create parent group')

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.delay(2)

def btn = findGroup(groupName_parent)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/a_Folders'))

WebUiBuiltInKeywords.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/input_Delete_pica-taginput-input form-control'),
	Folder_parent)

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/create_new_folder_inline'))

WebUI.delay(2)

// Folder 1 : Can read
changeFolderPermission(Folder_parent, 'Can administrate')

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/Create_group_button'))

WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/input_Organizations_pica_group_name'),
	groupName_sub)

WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/textarea_Organizations_pica_group_notes'),
	'create Subgroup')

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/a_Folders'))

WebUiBuiltInKeywords.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/input_Delete_pica-taginput-input form-control'),
	Folder_sub)

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/create_new_folder_inline'))

WebUI.delay(2)

// Folder 1 : Can read
changeFolderPermission(Folder_sub, 'Can read and write')

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.delay(2)

def btn_1 = findGroup(groupName_parent)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn_1))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Members'))

WebDriver driver = DriverFactory.getWebDriver()

WebElement inputElement = driver.findElement(By.xpath('//*[@id=\'pica_group_accounts\']//input[contains(concat(\' \', normalize-space(@class), \' \'), \' pica-taginput-input \')]'))

inputElement.sendKeys(groupName_sub)

new WebDriverWait(driver, java.time.Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath('(//div[@id=\'pica_group_accounts\']//ul[contains(@class,\'pica-taginput-dropdown\')]/li[not(contains(@class,\'pica-taginput-dropdown-fixed\'))])[1]/a'))).click()

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Save'))

def btn_2 = findGroup(groupName_sub)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn_2))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/a_Folders'))

WebUI.delay(2)

WebUI.delay(2)

verifyFolderPermission(Folder_sub,'Can read and write')

WebUI.closeBrowser()

@Keyword
WebElement findGroup(String Groupname) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + Groupname) + '\')]/td[1]/span'))
}

@Keyword
void changeFolderPermission(String folderName, String permission) {
	WebDriver driver = DriverFactory.getWebDriver()

	WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20))

	List<String> allowedPermissions = ['Can read', 'Can read and write', 'Can administrate']

	assert allowedPermissions.contains(permission)

	String folderRowXpath = "//td[@data-bs-original-title='$folderName']/ancestor::tr[1]"

	WebUI.comment("Recherche du dossier : $folderName")

	WebUI.comment("XPath utilisé : $folderRowXpath")

	WebElement folderRow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(folderRowXpath)))

	/*
	 * Trouver le bouton visible, car la page contient
	 * potentiellement une version desktop et une version mobile.
	 */
	WebElement permissionButton = wait.until({
			List<WebElement> buttons = folderRow.findElements(By.cssSelector('button.dropdown-toggle'))

			return buttons.find({ WebElement button ->
					button.isDisplayed() && button.isEnabled()
				})
		})

	WebElement permissionContainer = permissionButton.findElement(By.xpath('./parent::div'))

	String currentPermission = permissionContainer.getAttribute('data-selected')

	WebUI.comment("Permission actuelle de '$folderName' : '$currentPermission'")

	/*
	 * Can read est le droit par défaut.
	 * Il n'est donc pas nécessaire d'ouvrir le menu.
	 */
	if (currentPermission == permission) {
		WebUI.comment("Le dossier '$folderName' possède déjà '$permission'")

		return null
	}
	
	WebUI.executeJavaScript('arguments[0].scrollIntoView({block:\'center\'});', Arrays.asList(permissionButton))

	/*
	 * Clic Selenium réel pour déclencher Bootstrap.
	 */
	Actions actions = new Actions(driver)

	actions.moveToElement(permissionButton).pause(java.time.Duration.ofMillis(300)).click().perform()

	/*
	 * Attendre que le menu visible porte la classe show.
	 */
	WebElement permissionOption = wait.until({
			List<WebElement> options = permissionContainer.findElements(By.cssSelector('ul.dropdown-menu.show ' + 'a[data-dropdown-group=\'permission\']'))

			return options.find({ WebElement option ->
					option.isDisplayed() && (option.getText().trim() == permission)
				})
		})

	WebUI.comment("Sélection de '$permission' pour '$folderName'")

	/*
	 * Clic réel sur la permission.
	 */
	actions.moveToElement(permissionOption).pause(java.time.Duration.ofMillis(300)).click().perform()

	/*
	 * Vérification avec data-selected.
	 */
	wait.until({
			WebElement updatedRow = driver.findElement(By.xpath(folderRowXpath))

			List<WebElement> buttons = updatedRow.findElements(By.cssSelector('button.dropdown-toggle'))

			WebElement visibleButton = buttons.find({ WebElement button ->
					button.isDisplayed()
				})

			return (visibleButton != null) && (visibleButton.getText().trim() == permission)
		})

	WebUI.comment("Permission '$permission' appliquée au dossier '$folderName'")
}

@Keyword
void verifyFolderPermission(
	String folderName,
	String expectedPermission
) {
	WebDriver driver = DriverFactory.getWebDriver()

	WebDriverWait wait = new WebDriverWait(
		driver,
		java.time.Duration.ofSeconds(20)
	)

	String folderRowXpath =
		"//td[@data-bs-original-title='${folderName}']/ancestor::tr[1]"

	WebElement folderRow = wait.until(
		ExpectedConditions.visibilityOfElementLocated(
			By.xpath(folderRowXpath)
		)
	)

	WebElement permissionButton = wait.until({
		List<WebElement> buttons =
			folderRow.findElements(By.tagName('button'))

		return buttons.find { WebElement button ->
			button.isDisplayed()
		}
	})

	String actualPermission = permissionButton.getText().trim()

	WebUI.comment(
		"Permission du dossier '${folderName}' : '${actualPermission}'"
	)

	WebUI.verifyEqual(
		actualPermission,
		expectedPermission,
		FailureHandling.STOP_ON_FAILURE
	)
}

