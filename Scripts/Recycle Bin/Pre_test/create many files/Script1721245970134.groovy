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
import javax.swing.filechooser.FileSystemView as FileSystemView
import org.apache.poi.xwpf.usermodel.XWPFDocument as XWPFDocument
import java.io.FileOutputStream as FileOutputStream
import java.nio.file.Paths as Paths
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.apache.commons.io.FileUtils as FileUtils
import java.io.ByteArrayInputStream as ByteArrayInputStream
import java.net.URL as URL
import java.nio.file.Files as Files
import java.nio.file.StandardCopyOption as StandardCopyOption
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.StringSelection as StringSelection
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import java.awt.FileDialog as FileDialog
import javax.swing.JFrame as JFrame
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import java.time.Duration
import org.openqa.selenium.support.ui.ExpectedConditions
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



WebUiBuiltInKeywords.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

GlobalVariable.folderName = ('folder_' + RandomStringUtils.randomNumeric(4))

String folderName = GlobalVariable.folderName

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Folders/createFolderIcon'))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Folders/createFolder'))

WebUiBuiltInKeywords.setText(findTestObject('Object Repository/Folders/inputFolderName'), GlobalVariable.folderName)

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Folders/buttonOK'))

WebUI.click(findTestObject('Object Repository/Folders/Page_Folders - PowerFolder/lang_Folders'))

WebUI.delay(2)

WebElement btn = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

// Définition du chemin vers le dossier sur le bureau
def desktopWordPath = Paths.get(System.getProperty('user.home'), 'Desktop')
String folderPath = desktopWordPath.resolve('mon_dossier').toString()

// Fonctions
def pasteFromClipboardAndConfirm(Robot robot) {
	robot.keyPress(KeyEvent.VK_CONTROL)
	robot.keyPress(KeyEvent.VK_V)
	robot.keyRelease(KeyEvent.VK_V)
	robot.keyRelease(KeyEvent.VK_CONTROL)
	robot.keyPress(KeyEvent.VK_ENTER)
	robot.keyRelease(KeyEvent.VK_ENTER)
}

def createFolderIfNotExists(String folderPath) {
	File folder = new File(folderPath)
	if (!(folder.exists())) {
		folder.mkdir()
	}
}

def createFilesInFolder(String folderPath, int numberOfFiles) {
	for (int i = 1; i <= numberOfFiles; i++) {
		File file = new File(((folderPath + '/file') + i) + '.txt')
		file.createNewFile()
	}
}

List<WebElement> findFiles() {
	WebDriver driver = DriverFactory.getWebDriver()
	return driver.findElements(By.xpath("//table[@id='files_files_table']/tbody"))
}


// Clic pour créer un élément à l'intérieur d'un dossier
WebUiBuiltInKeywords.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

// Vérification de la possibilité de cliquer sur un élément
WebUiBuiltInKeywords.verifyElementClickable(findTestObject('file_objects/upload/Page_Folders - PowerFolder/Upload file'))

// Clic pour uploader un fichier
WebUiBuiltInKeywords.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/Upload file'))

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


@Keyword
WebElement findFolder(String folderName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//a[contains(text(),\'' + folderName) + '\')]'))
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
List<String> buildFilePaths(String folderPath, int numberOfFiles) {
	List<String> files = []
	for (int i = 1; i <= numberOfFiles; i++) {
		files.add("${folderPath}/file${i}.txt")
	}
	return files
}
