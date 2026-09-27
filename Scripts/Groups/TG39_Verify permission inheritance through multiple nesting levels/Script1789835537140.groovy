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
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import com.kms.katalon.core.webui.driver.DriverFactory
import java.time.Duration

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

String parentGroupName = 'Parent_Group_' + RandomStringUtils.randomNumeric(4)

String subGroupName = 'subGroup_' + RandomStringUtils.randomNumeric(4)

String subSubGroupName = 'subSubGroup_' + RandomStringUtils.randomNumeric(4)

String Folder_parent = 'parentFolder' + Helper.getRandomFolderName()

// create parent group
WebUI.click(findTestObject('Object Repository/Groups/Page_Dashboard - PowerFolder/lang_Groups'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/Create_group_button'))

WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/input_Organizations_pica_group_name'), 
    parentGroupName)

WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/textarea_Organizations_pica_group_notes'), 
    'create a parent group')

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.delay(2)

//add folder to the main group
def btn = findGroup(parentGroupName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/a_Folders'))

WebUiBuiltInKeywords.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/input_Delete_pica-taginput-input form-control'), 
    Folder_parent)

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/create_new_folder_inline'))

WebUI.delay(2)

changeFolderPermission(Folder_parent, 'Can administrate')

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.delay(2)

//create another group called subgroup
WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/Create_group_button'))

WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/input_Organizations_pica_group_name'), 
    subGroupName)

WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/textarea_Organizations_pica_group_notes'), 
    'create Subgroup')

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.delay(2)

//create another group called subsubgroup
WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/Create_group_button'))

WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/input_Organizations_pica_group_name'), 
    subSubGroupName)

WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/textarea_Organizations_pica_group_notes'), 
    'create SubSubgroup')

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.delay(2)

//tied subgroup to the main one
def btn_1 = findGroup(parentGroupName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn_1))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Members'))

enterGroupName(subGroupName)

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Save'))

// wait for the save request to finish before reloading the page
WebUI.delay(3)

WebUI.refresh()

WebUI.delay(2)

//tied subsubgroup to subgroup


def btn_2 = findGroup(subGroupName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn_2))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Members'))

enterGroupName(subSubGroupName)

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Save'))

// wait for the save request to finish before reloading the page
WebUI.delay(3)

WebUI.refresh()

WebUI.delay(2)

// check level 1: subgroup has subsubgroup as member and inherits the parent folder
def btn_check = findGroup(subGroupName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn_check))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Members'))

verifyGroupMember(subSubGroupName)

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/a_Folders'))

WebUI.delay(2)

verifyFolderPermission(Folder_parent, 'Can administrate (inherited)')

WebUI.refresh()

WebUI.delay(2)

// check level 2: subsubgroup inherits the parent folder
def btn_3 = findGroup(subSubGroupName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn_3))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/a_Folders'))

WebUI.delay(2)

verifyFolderPermission(Folder_parent, 'Can administrate (inherited)')

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
void verifyFolderPermission(String folderName, String expectedPermission) {
    WebDriver driver = DriverFactory.getWebDriver()

    WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20))

    /*
	 * Restrict the search to the Folders tab of the group dialog, otherwise the
	 * groups overview table in the background can match the folder name.
	 * Inherited folders are rendered read-only (no dropdown, no tooltip).
	 */
    String folderRowXpath = "//div[@id='pica_group_folders']//tr[td[@data-bs-original-title='$folderName' or @title='$folderName' or contains(normalize-space(.), '$folderName')]]"

    WebElement folderRow

    try {
        folderRow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(folderRowXpath)))
    }
    catch (org.openqa.selenium.TimeoutException e) {
        List<String> shownRows = driver.findElements(By.xpath("//div[@id='pica_group_folders']//tr")).collect({ WebElement row ->
                row.getText().trim()
            }).findAll({ String text ->
                text
            })

        WebUI.comment("Folders visible in the group dialog: $shownRows")

        WebUI.verifyEqual("Folder '$folderName' not listed", "Folder '$folderName' listed with '$expectedPermission'", FailureHandling.STOP_ON_FAILURE)

        return null
    }

    String rowText = folderRow.getText().trim()

    WebUI.comment("Folder row '$folderName' : '$rowText'")

    WebUI.verifyMatch(rowText, '(?s).*' + java.util.regex.Pattern.quote(expectedPermission) + '.*', true, FailureHandling.STOP_ON_FAILURE)
}

void enterGroupName(String groupName) {
	WebDriver driver = DriverFactory.getWebDriver()

	WebDriverWait wait = new WebDriverWait(
		driver,
		java.time.Duration.ofSeconds(10)
	)

	// Rechercher le champ et saisir le nom du groupe
	WebElement inputElement = wait.until(
		ExpectedConditions.elementToBeClickable(
			By.xpath("//*[@id='pica_group_accounts']//input[contains(concat(' ', normalize-space(@class), ' '), ' pica-taginput-input ')]")
		)
	)

	inputElement.clear()
	inputElement.sendKeys(groupName)

	// Attendre le premier résultat et cliquer dessus
	WebElement firstResult = wait.until(
		ExpectedConditions.elementToBeClickable(
			By.xpath("(//div[@id='pica_group_accounts']//ul[contains(@class,'pica-taginput-dropdown')]/li[not(contains(@class,'pica-taginput-dropdown-fixed'))]/a[contains(normalize-space(.), '${groupName}')])[1]")
		)
	)

	firstResult.click()

	WebUI.delay(1)
}

void verifyGroupMember(String groupName) {
	WebDriver driver = DriverFactory.getWebDriver()

	WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10))

	String memberXpath = "//*[@id='pica_group_accounts']//div[contains(@class,'pica-inputlist-scroller')]//*[normalize-space(text())='${groupName}']"

	try {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(memberXpath)))
	}
	catch (org.openqa.selenium.TimeoutException e) {
		WebUI.verifyEqual("Group '$groupName' not listed as member", "Group '$groupName' listed as member", FailureHandling.STOP_ON_FAILURE)
	}

	WebUI.comment("Group '$groupName' is listed as member")
}
