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

// Déclaration de la variable globale folderName
GlobalVariable.folderName = 'folder_' + RandomStringUtils.randomNumeric(4)

WebUiBuiltInKeywords.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Folders/createFolderIcon'))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Folders/createFolder'))

WebUiBuiltInKeywords.setText(findTestObject('Object Repository/Folders/inputFolderName'), GlobalVariable.folderName)

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Folders/buttonOK'))

WebDriver driver = DriverFactory.getWebDriver()

WebElement folder = driver.findElement(By.xpath(('//td/a[contains(text(),\'' + GlobalVariable.folderName) + '\')]'))

boolean isFolderCreated = folder.isDisplayed()

WebUiBuiltInKeywords.verifyEqual(isFolderCreated, true)

// Générer un nom de groupe aléatoire
String groupName = 'Group_' + RandomStringUtils.randomNumeric(4)

// Assigner le nom de groupe aléatoire à la variable globale GroupName
GlobalVariable.GroupName = groupName

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Dashboard - PowerFolder/lang_Groups'))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/span_Log out_pica-icon pica-glyph glyphicon_0824b5'))

WebUiBuiltInKeywords.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/input_Organizations_pica_group_name'), 
    GlobalVariable.GroupName)

WebUiBuiltInKeywords.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/textarea_Organizations_pica_group_notes'), 
    'create group')

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))

// Rechercher le groupe avec le nom généré aléatoirement
WebElement btn = findGroup(GlobalVariable.GroupName)

WebUiBuiltInKeywords.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

assert GlobalVariable.GroupName != null

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Folders'))

WebUiBuiltInKeywords.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/input_Delete_pica-taginput-input form-control'), 
    GlobalVariable.folderName)

// Attendre que l'élément recherché apparaisse dans les résultats de la recherche
WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), 5)

WebElement folderElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(('//div[3]/div/div[contains(.,\'' + 
            GlobalVariable.folderName) + '\')]/ul//a')))

// Cliquer sur l'élément trouvé dans les résultats de la recherche
folderElement.click()

WebUiBuiltInKeywords.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Save'))

WebElement btn1 = findGroup(GlobalVariable.GroupName)

WebUiBuiltInKeywords.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

@Keyword
WebElement findGroup(String groupName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + groupName) + '\')]/td[1]/span'))
}
