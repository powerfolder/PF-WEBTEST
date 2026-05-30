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

// ================== LOGIN + CREATE TARGET FOLDER ==================

String topFolder = "Top_lvl" + Helper.getRandomFolderName()

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

// ================== CREATE MULTIPLE LOCAL FILES ==================

List<String> fileNames = []
List<String> filePaths = []

for (int i = 1; i <= 5; i++) {
	String fileName = Helper.getRandomFileName()
	String filePath = createTextFileOnDesktop(fileName)

	fileNames.add(fileName)
	filePaths.add(filePath)
}

// ================== DRAG MULTIPLE FILES DIRECTLY INTO TARGET FOLDER ROW ==================

String targetFolderCss = "tr[data-search-keys*='" + topFolder + "']"

Helper.dragAndDropFilesNative(targetFolderCss, filePaths)

WebUI.delay(5)

if (WebUI.waitForElementVisible(findTestObject('Drag and drop/Close_button'), 10, FailureHandling.OPTIONAL)) {
	WebUI.click(findTestObject('Drag and drop/Close_button'))
}

// ================== OPEN TARGET FOLDER TO VERIFY FILES ==================

WebElement targetFolderLink = driver.findElement(
	By.xpath("//tr[contains(@data-search-keys,'" + GlobalVariable.folderName + "')]//a[contains(text(),'" + topFolder + "')]")
)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(targetFolderLink))

WebUI.delay(3)

// ================== VERIFY UPLOADED FILES INSIDE FOLDER ==================

for (String fileName : fileNames) {
	boolean uploaded = driver.findElements(
		By.xpath("//table[@id='files_files_table']//*[contains(text(),'" + fileName + "')]")
	).size() > 0

	WebUI.verifyEqual(uploaded, true)
}

// ================== DELETE LOCAL FILES ==================

for (String filePath : filePaths) {
	deleteLocalFile(filePath)
}

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