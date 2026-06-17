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
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.Path

// ================== LOGIN + CREATE FOLDER ==================

String topFolder = "Top_lvl_" + Helper.getRandomFolderName()

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebUI.click(findTestObject('Object Repository/Folders/createFolderIcon'))
WebUI.click(findTestObject('Object Repository/Folders/createFolder'))

WebUI.setText(findTestObject('Object Repository/Folders/inputFolderName'), topFolder)
WebUI.click(findTestObject('Object Repository/Folders/buttonOK'))

WebUI.delay(3)

WebUI.verifyElementPresent(findTestObject('Drag and drop/create_icone'), 5)

WebUI.click(findTestObject('Drag and drop/create_icone'))
WebUI.click(findTestObject('Drag and drop/Create_folder'))

String subFolderName = 'Sub_' + Helper.getRandomFolderName()

WebUI.setText(findTestObject('Folders/inputFolderName'), subFolderName)
WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.delay(3)

WebUI.back()
WebUI.delay(2)

// ================== OPEN CREATED FOLDER ==================

WebDriver driver = DriverFactory.getWebDriver()

List<WebElement> folderLinks = driver.findElements(
	By.xpath("//td//a[contains(text(),'" + topFolder + "')]")
)

if (folderLinks.size() > 0) {
	WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(folderLinks.get(0)))
	WebUI.delay(2)
}

// ================== CREATE MULTIPLE LOCAL FILES ==================

List<String> fileNames = []
List<String> filePaths = []

for (int i = 1; i <= 5; i++) {
	String fileName = Helper.getRandomFileName()
	String filePath = createTextFileOnDesktop(fileName)

	fileNames.add(fileName + '.txt')
	filePaths.add(filePath)
}

// ================== DRAG AND DROP MULTIPLE FILES ==================

Helper.dragAndDropFilesNative('#pica_content', filePaths)

WebUI.delay(5)

WebUI.click(findTestObject('Drag and drop/Close_button'))

// ================== VERIFY UPLOADED FILES ==========================

for (String fileName : fileNames) {
	boolean uploaded = driver.findElements(
		By.xpath("//table[@id='files_files_table']//*[contains(text(),'" + fileName + "')]")
	).size() > 0

	WebUI.verifyEqual(uploaded, true)
}

// ================== MOVE UPLOADED FILES INTO SUB FOLDER ==================

Helper.selectUploadedFolders(fileNames, subFolderName)

WebUI.delay(1)

Helper.dragSelectedFoldersToSubFolder(subFolderName)

// ================== OPEN TARGET FOLDER TO VERIFY FILES ==================

WebElement targetFolderLink = driver.findElement(
	By.xpath("//tr[contains(@data-search-keys,'" + subFolderName + "')]//a[contains(text(),'" + subFolderName + "')]")
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

	Files.write(file, 'file drag test'.getBytes())

	return file.toString()
}

void deleteLocalFile(String filePath) {
	Path path = Paths.get(filePath)

	if (Files.exists(path)) {
		Files.deleteIfExists(path)
	}

	println('LOCAL FILE DELETED : ' + filePath)
}