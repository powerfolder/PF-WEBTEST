import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import internal.GlobalVariable as GlobalVariable
import helpers.Helper as Helper
import java.nio.file.Files as Files
import java.nio.file.Paths as Paths
import java.nio.file.Path as Path
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import java.util.Arrays as Arrays
import file.FileFinder

// ================== LOGIN + CREATE TARGET FOLDER ==================

String topFolder = "Top_lvl_" + Helper.getRandomFolderName()

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebUI.click(findTestObject('Object Repository/Folders/createFolderIcon'))

WebUI.click(findTestObject('Object Repository/Folders/createFolder'))

WebUI.setText(findTestObject('Object Repository/Folders/inputFolderName'), topFolder)

WebUI.click(findTestObject('Object Repository/Folders/buttonOK'))

WebUI.delay(3)

// ================== GO BACK TO HOME / FOLDER LIST ==================

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/lang_Home'))

WebUI.delay(3)

WebDriver driver = DriverFactory.getWebDriver()

// ================== CREATE LOCAL FILE ==================

String fileName = Helper.getRandomFileName()
String filePath = createTextFileOnDesktop(fileName)

// ================== FIND TARGET FOLDER IN LIST ==================

// Helper.dragAndDropFileNative computes Robot coords from window.screenX/Y + chrome diff —
// any non-zero window position or DevTools offset pushes the drop off-viewport.
// Normalize the window to (0,0) + maximized first.
driver.manage().window().setPosition(new org.openqa.selenium.Point(0, 0))
WebUI.maximizeWindow()
WebUI.delay(1)

// Clear any persisted search filter (files.js restores sessionStorage.searchQuery on reload — would hide the new folder if a previous test typed something).
WebUI.executeJavaScript("try { sessionStorage.removeItem('searchQuery'); } catch (e) {}", null)

String targetFolderCss = "tr[data-search-keys*='" + topFolder + "']"

// Wait until the target folder row is actually present in the DOM before invoking the native drag helper.
new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15)).until(
    org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(
        By.cssSelector(targetFolderCss)))

WebElement targetFolder = driver.findElement(
	By.xpath("//table[contains(@id,'folders') or contains(@id,'files')]//tr[contains(@data-search-keys,'" + topFolder + "')]")
)

// ================== DRAG FILE DIRECTLY INTO TARGET FOLDER ROW ==================

Helper.dragAndDropFileNative(targetFolderCss, filePath)

WebUI.delay(5)

if (WebUI.waitForElementVisible(findTestObject('Drag and drop/Close_button'), 10, FailureHandling.OPTIONAL)) {
	WebUI.click(findTestObject('Drag and drop/Close_button'))
}

// ================== OPEN TARGET FOLDER TO VERIFY FILE ==================

targetFolder = driver.findElement(
	By.xpath("//tr[contains(@data-search-keys,'" + topFolder + "')]//a[contains(text(),'" + topFolder + "')]")
)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(targetFolder))

WebUI.delay(3)

// ================== VERIFY FILE INSIDE FOLDER ==================

boolean uploaded = driver.findElements(
	By.xpath("//table[@id='files_files_table']//*[contains(text(),'" + fileName + "')]")
).size() > 0

WebUI.verifyEqual(uploaded, true)

// ================== DELETE LOCAL FILE ==================

deleteLocalFile(filePath)

WebUI.closeBrowser()

// ================== FUNCTIONS ==================

String createTextFileOnDesktop(String fileName) {
	Path desktop = Paths.get(System.getProperty('user.home'), 'Desktop')

	if (!Files.exists(desktop)) {
		Files.createDirectories(desktop)
	}

	Path file = desktop.resolve(fileName + '.txt')

	Files.write(file, 'drag test'.getBytes())

	return file.toString()
}

void deleteLocalFile(String filePath) {
	Files.deleteIfExists(Paths.get(filePath))
	println('LOCAL FILE DELETED : ' + filePath)
}