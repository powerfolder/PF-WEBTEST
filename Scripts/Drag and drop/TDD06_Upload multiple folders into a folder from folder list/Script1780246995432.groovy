import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable
import helpers.Helper

import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.Path
import java.util.Arrays
import java.util.Comparator

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

// ================== CREATE MULTIPLE LOCAL FOLDERS ==================

List<String> folderNames = []
List<String> folderPaths = []

for (int i = 1; i <= 5; i++) {
	String folderName = Helper.getRandomFolderName()
	String folderPath = createLocalFolderOnDesktop(folderName)

	folderNames.add(folderName)
	folderPaths.add(folderPath)
}

// ================== DRAG MULTIPLE FOLDERS DIRECTLY INTO TARGET FOLDER ROW ==================

// Helper.dragAndDropFoldersNative computes Robot coords from window.screenX/Y + chrome diff —
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

Helper.dragAndDropFoldersNative(targetFolderCss, folderPaths)

WebUI.delay(5)

if (WebUI.waitForElementVisible(findTestObject('Drag and drop/Close_button'), 10, FailureHandling.OPTIONAL)) {
	WebUI.click(findTestObject('Drag and drop/Close_button'))
}

// ================== OPEN TARGET FOLDER TO VERIFY FOLDERS ==================

WebElement targetFolderLink = driver.findElement(
	By.xpath("//tr[contains(@data-search-keys,'" + topFolder + "')]//a[contains(text(),'" + topFolder + "')]")
)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(targetFolderLink))

WebUI.delay(3)

// ================== VERIFY UPLOADED FOLDERS INSIDE FOLDER ==================

for (String folderName : folderNames) {
	boolean uploaded = driver.findElements(
		By.xpath("//table[@id='files_files_table']//*[contains(text(),'" + folderName + "')]")
	).size() > 0

	WebUI.verifyEqual(uploaded, true)
}

// ================== DELETE LOCAL FOLDERS ==================

for (String folderPath : folderPaths) {
	deleteLocalFolder(folderPath)
}

WebUI.closeBrowser()

// ================== FUNCTIONS ==================

String createLocalFolderOnDesktop(String folderName) {
	Path desktop = Paths.get(System.getProperty('user.home'), 'Desktop')

	if (!Files.exists(desktop)) {
		Files.createDirectories(desktop)
	}

	Path folder = desktop.resolve(folderName)

	if (!Files.exists(folder)) {
		Files.createDirectories(folder)
	}

	Path fileInside = folder.resolve('test_file_inside.txt')

	Files.write(fileInside, 'folder drag test'.getBytes())

	return folder.toString()
}

void deleteLocalFolder(String folderPath) {
	Path path = Paths.get(folderPath)

	if (Files.exists(path)) {
		Files.walk(path)
			.sorted(Comparator.reverseOrder())
			.forEach { p -> Files.deleteIfExists(p) }
	}

	println('LOCAL FOLDER DELETED : ' + folderPath)
}