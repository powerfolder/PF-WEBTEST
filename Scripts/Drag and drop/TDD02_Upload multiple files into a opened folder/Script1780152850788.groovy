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

import java.util.Arrays
import java.util.Comparator
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.Path

// ================== LOGIN + CREATE FOLDER ==================

String topFolder = "Top_lvl" + Helper.getRandomFolderName()

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebUI.click(findTestObject('Object Repository/Folders/createFolderIcon'))

WebUI.click(findTestObject('Object Repository/Folders/createFolder'))

WebUI.setText(findTestObject('Object Repository/Folders/inputFolderName'), topFolder)

WebUI.click(findTestObject('Object Repository/Folders/buttonOK'))

WebUI.delay(3)

// ================== OPEN CREATED FOLDER ==================

WebDriver driver = DriverFactory.getWebDriver()

List<WebElement> folderLinks = driver.findElements(
	By.xpath("//td//a[contains(text(),'" + topFolder + "')]")
)

if (folderLinks.size() > 0) {
	WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(folderLinks.get(0)))
	WebUI.delay(2)
}

// ================== CREATE MULTIPLE LOCAL FOLDERS ==================

List<String> folderNames = []
List<String> folderPaths = []

for (int i = 1; i <= 5; i++) {
	String folderName = Helper.getRandomFolderName()
	String folderPath = createLocalFolderOnDesktop(folderName)

	folderNames.add(folderName)
	folderPaths.add(folderPath)
}

// ================== DRAG AND DROP MULTIPLE FOLDERS ==================

Helper.dragAndDropFoldersNative('#pica_content', folderPaths)

WebUI.delay(5)

WebUI.click(findTestObject('Drag and drop/Close_button'))

// ================== VERIFY UPLOADED FOLDERS ==================

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