import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.apache.commons.lang3.RandomStringUtils
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.commons.io.FileUtils

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.Arrays

WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.OPTIONAL)

// Vérifie le titre
assert WebUI.getWindowTitle().equals('Folders - PowerFolder')

// Création dossier principal
WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))

String mainFolderName = getRandomFolderName()

WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Folders/inputFolderName'), mainFolderName)
WebUI.click(findTestObject('Folders/buttonOK'))

TestObject dynamicObject = new TestObject()
dynamicObject.addProperty('xpath', ConditionType.EQUALS, "//span[text()='" + mainFolderName + "']")
WebUI.verifyElementPresent(dynamicObject, 10, FailureHandling.OPTIONAL)

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.verifyElementPresent(findTestObject('Folders/Page_Folders - PowerFolder/New subdirectory'), 5)
WebUI.click(findTestObject('Folders/Page_Folders - PowerFolder/New subdirectory'))

String subFolderName = "Sub_" + getRandomFolderName()
WebUI.setText(findTestObject('Folders/inputFolderName'), subFolderName)
WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/Page_Folders - PowerFolder/New subdirectory'))

String subSubFolderName = "Sub_Sub_" + getRandomFolderName()
WebUI.setText(findTestObject('Folders/inputFolderName'), subSubFolderName)
WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.verifyElementPresent(findTestObject('Folders/Page_Folders - PowerFolder/Upload files'), 5)
WebUI.click(findTestObject('Folders/Page_Folders - PowerFolder/Upload files'))

// Création d’un dossier local avec plusieurs fichiers Word
String folderName = 'folder_with_files_' + RandomStringUtils.randomNumeric(4)
String folderPath = createFolderWithWordFilesOnDesktop(folderName, 3)

// Upload du dossier via l’input directories
TestObject uploadInput = new TestObject('uploadInput')
uploadInput.addProperty('xpath', ConditionType.EQUALS, "//input[@id='upload_input_directories']")

WebUI.waitForElementPresent(uploadInput, 10)
WebUI.uploadFile(uploadInput, folderPath)

// Vérifier le succès
TestObject successMsg = new TestObject('successMsg')
successMsg.addProperty('xpath', ConditionType.EQUALS, "//*[contains(text(),'Successfully uploaded')]")
WebUI.waitForElementVisible(successMsg, 15)

// Fermer la popup
TestObject closeBtn = new TestObject('closeBtn')
closeBtn.addProperty('xpath', ConditionType.EQUALS, "//button[@id='upload_stop_button']")

WebUI.waitForElementClickable(closeBtn, 10)
WebUI.click(closeBtn)

WebUI.delay(2)

// Vérification que le dossier est visible
WebElement btn = findFolder(folderName)
assert btn != null

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.delay(3)

// Nettoyage local
deleteFolder(folderPath)

WebUI.closeBrowser()

// =========================
// Méthodes utilitaires
// =========================

@Keyword
WebElement findFolder(String folderName) {
	WebDriver driver = DriverFactory.getWebDriver()
	return driver.findElement(By.xpath("//*[contains(@data-search-keys, '" + folderName + "')]/td[1]/span"))
}

String createFolderWithWordFilesOnDesktop(String folderName, int numFiles) {
	Path desktopFolderPath = Paths.get(System.getProperty('user.home'), 'Desktop', folderName)

	if (!Files.exists(desktopFolderPath)) {
		Files.createDirectories(desktopFolderPath)
	}

	for (int i = 0; i < numFiles; i++) {
		String fileName = 'word_file_' + RandomStringUtils.randomNumeric(4) + '.docx'
		String filePath = desktopFolderPath.resolve(fileName).toString()
		createEmptyWordFile(filePath)
	}

	return desktopFolderPath.toString()
}

String createEmptyWordFile(String filePath) {
	XWPFDocument document = new XWPFDocument()
	FileOutputStream out = null

	try {
		out = new FileOutputStream(filePath)
		document.write(out)
	} finally {
		if (out != null) {
			out.close()
		}
		document.close()
	}

	return filePath
}

void deleteFolder(String folderPath) {
	try {
		File folder = new File(folderPath)
		if (folder.exists()) {
			FileUtils.deleteDirectory(folder)
			println('Le dossier a été supprimé avec succès.')
		} else {
			println('Le dossier n\'existe pas.')
		}
	} catch (Exception e) {
		println("Une erreur s'est produite lors de la suppression du dossier : ${e.message}")
		e.printStackTrace()
	}
}

String getRandomFolderName() {
	return 'TF' + getTimestamp()
}

String getTimestamp() {
	Date todaysDate = new Date()
	return todaysDate.format('_dd_MM_yyyy_hh_mm_ss')
}