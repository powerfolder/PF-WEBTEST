// Importations des classes nécessaires
import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import org.apache.commons.io.FileUtils as FileUtils

import java.nio.file.Paths as Paths
import java.nio.file.Files as Files
import java.time.Duration

import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

// Définition du chemin vers le dossier sur le bureau
def desktopPath = Paths.get(System.getProperty('user.home'), 'Desktop')
String folderPath = desktopPath.resolve('mon_dossier').toString()

// Appel du test case pour créer un dossier
WebUI.callTestCase(findTestCase('File/Pre_test/Create_folder'), [:], FailureHandling.STOP_ON_FAILURE)

// Ouvrir la popup d’upload
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.verifyElementClickable(findTestObject('file_objects/upload/Page_Folders - PowerFolder/Upload file'))
WebUI.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/Upload file'))

// Créer le dossier local s'il n'existe pas
createFolderIfNotExists(folderPath)

// Créer 10 fichiers dans le dossier
createFilesInFolder(folderPath, 10)

// Construire la liste complète des fichiers à uploader
List<String> filePaths = buildFilePaths(folderPath, 10)

// Katalon/Selenium attend plusieurs chemins séparés par un saut de ligne
String filesToUpload = filePaths.join('\n')

// Input file direct
TestObject uploadInput = new TestObject('uploadInput')
uploadInput.addProperty('xpath', ConditionType.EQUALS, "//input[@id='upload_input_files']")

WebUI.waitForElementPresent(uploadInput, 10)
WebUI.uploadFile(uploadInput, filesToUpload)

TestObject closeBtn = new TestObject('closeBtn')
closeBtn.addProperty('xpath', ConditionType.EQUALS, "//button[@id='upload_stop_button']")

WebUI.waitForElementClickable(closeBtn, 10)
WebUI.click(closeBtn)

// Attendre que la popup se ferme ou que l’upload soit fini
WebUI.delay(5)

// Vérifier que les fichiers sont présents
List<WebElement> items = findFiles()
assert !items.isEmpty()

WebUI.delay(3)

// Supprimer le dossier local
deleteFolder(folderPath)

WebUI.closeBrowser()

def createFolderIfNotExists(String folderPath) {
	File folder = new File(folderPath)
	if (!folder.exists()) {
		folder.mkdir()
	}
}

def createFilesInFolder(String folderPath, int numberOfFiles) {
	for (int i = 1; i <= numberOfFiles; i++) {
		File file = new File("${folderPath}/file${i}.txt")
		if (!file.exists()) {
			file.createNewFile()
		}
	}
}

List<String> buildFilePaths(String folderPath, int numberOfFiles) {
	List<String> files = []
	for (int i = 1; i <= numberOfFiles; i++) {
		files.add("${folderPath}/file${i}.txt")
	}
	return files
}

List<WebElement> findFiles() {
	WebDriver driver = DriverFactory.getWebDriver()
	return driver.findElements(By.xpath('//div[2]/table/tbody/tr'))
}

void deleteFolder(String folderPath) {
	try {
		File folder = new File(folderPath)
		if (folder.exists()) {
			FileUtils.deleteDirectory(folder)
			println('Le dossier a été supprimé avec succès.')
		} else {
			println('Le dossier n’existe pas.')
		}
	} catch (Exception e) {
		println("Une erreur s'est produite lors de la suppression du dossier : ${e.message}")
		e.printStackTrace()
	}
}