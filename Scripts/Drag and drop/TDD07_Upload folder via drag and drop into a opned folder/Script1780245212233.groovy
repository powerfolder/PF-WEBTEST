import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable
import helpers.Helper

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

// ================== CREATE LOCAL FOLDER ==================

String localFolderName = Helper.getRandomFolderName()
String localFolderPath = createLocalFolderOnDesktop(localFolderName)

// ================== DRAG AND DROP FOLDER INTO OPENED FOLDER ==================

Helper.dragAndDropFolderNative('#pica_content', localFolderPath)

WebUI.delay(5)

if (WebUI.waitForElementVisible(findTestObject('Drag and drop/Close_button'), 10, FailureHandling.OPTIONAL)) {
	WebUI.click(findTestObject('Drag and drop/Close_button'))
}

// ================== VERIFY FOLDER UPLOADED ==================

boolean uploaded = WebUI.verifyTextPresent(localFolderName, false, FailureHandling.OPTIONAL)

WebUI.verifyEqual(uploaded, true)

// ================== DELETE LOCAL FOLDER ==================

deleteLocalFolder(localFolderPath)

WebUI.closeBrowser()

// ================== FUNCTIONS ==================

String createLocalFolderOnDesktop(String folderName) {
	Path desktop = Paths.get(System.getProperty('user.home'), 'Desktop')
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