import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberBuiltinKeywords
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as MobileBuiltInKeywords
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGBuiltinKeywords
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as WindowsBuiltinKeywords
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import java.util.Arrays as Arrays
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait
import javax.swing.filechooser.FileSystemView as FileSystemView
import org.apache.poi.xwpf.usermodel.XWPFDocument as XWPFDocument
import java.io.FileOutputStream as FileOutputStream
import java.nio.file.FileSystems as FileSystems
import org.apache.commons.io.FileUtils as FileUtils
import org.apache.commons.io.IOUtils as IOUtils
import java.io.ByteArrayInputStream as ByteArrayInputStream
import java.net.URL as URL
import java.nio.file.Files as Files
import java.nio.file.Paths as Paths
import java.nio.file.StandardCopyOption as StandardCopyOption
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.StringSelection as StringSelection
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import java.awt.FileDialog as FileDialog
import javax.swing.JFrame as JFrame
import java.nio.file.Path as Path
import java.time.Duration as Duration

WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.OPTIONAL)

// Vérifie si le titre de la page correspond à "Folders - PowerFolder"
assert WebUI.getWindowTitle().equals('Folders - PowerFolder')

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createFolder'))

String mainFolderName = getRandomFolderName()

WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.setText(findTestObject('Folders/inputFolderName'), mainFolderName)

WebUI.click(findTestObject('Folders/buttonOK'))

TestObject dynamicObject = new TestObject()

dynamicObject.addProperty('xpath', ConditionType.EQUALS, ('//span[text()=\'' + mainFolderName) + '\']')

boolean exists = WebUI.verifyElementPresent(dynamicObject, 10, FailureHandling.OPTIONAL)

WebUI.verifyElementPresent(findTestObject('Folders/Page_Folders - PowerFolder/New subdirectory'), 5)

WebUI.click(findTestObject('Folders/Page_Folders - PowerFolder/New subdirectory'))

String subFolderName = "Sub_"+ getRandomFolderName()

WebUI.setText(findTestObject('Folders/inputFolderName'), subFolderName)

WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.verifyElementPresent(findTestObject('Folders/Page_Folders - PowerFolder/Upload files'), 5)

WebUI.click(findTestObject('Folders/Page_Folders - PowerFolder/Upload files'))

WebUI.click(findTestObject('Folders/Page_Folders - PowerFolder/add folder'))

String folderName = 'folder_with_files_' + RandomStringUtils.randomNumeric(4)

String folderPath = createFolderWithWordFilesOnDesktop(folderName, 3)

selectFolderAutomatically(folderPath)

// Annulation de l'upload
WebUI.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/lang_Cancel'))
WebUI.delay(3)

// Vérification de la présence du dossier
def btn = findFolder(folderName)
assert btn != null : 'Le document n\'est pas présent.'

// Cliquer sur le bouton trouvé
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))
WebUI.delay(3)

// Suppression du dossier
deleteFolder(folderPath)

// Fermeture du navigateur
WebUI.closeBrowser()

// Méthode pour trouver un dossier par nom
@Keyword
WebElement findFolder(String folderName) {
	WebDriver driver = DriverFactory.getWebDriver()
	return driver.findElement(By.xpath('//*[contains(@data-search-keys, \'' + folderName + '\')]/td[1]/span'))
}

// Méthode pour créer un dossier avec des fichiers Word sur le bureau
String createFolderWithWordFilesOnDesktop(String folderName, int numFiles) {
	def desktopFolderPath = Paths.get(System.getProperty('user.home'), 'Desktop', folderName)
	if (!Files.exists(desktopFolderPath)) {
		Files.createDirectories(desktopFolderPath)
	}
	for (int i = 0; i < numFiles; i++) {
		String fileName = 'word_file_' + RandomStringUtils.randomNumeric(4) + '.docx'
		createEmptyWordFileOnDesktop(desktopFolderPath.resolve(fileName).toString())
	}
	return desktopFolderPath.toString()
}

// Méthode pour créer un fichier Word vide
String createEmptyWordFileOnDesktop(String filePath) {
	XWPFDocument document = new XWPFDocument()
	FileOutputStream out = null
	try {
		out = new FileOutputStream(filePath)
		document.write(out)
	} catch (IOException e) {
		e.printStackTrace()
	} finally {
		if (out != null) {
			try {
				out.close()
			} catch (IOException e) {
				e.printStackTrace()
			}
		}
		try {
			document.close()
		} catch (IOException e) {
			e.printStackTrace()
		}
	}
	return filePath
}

// Méthode pour sélectionner un dossier automatiquement
void selectFolderAutomatically(String folderPath) {
	try {
		Robot robot = new Robot()
		Thread.sleep(500)
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(folderPath), null)
		robot.keyPress(KeyEvent.VK_CONTROL)
		robot.keyPress(KeyEvent.VK_O)
		robot.keyRelease(KeyEvent.VK_O)
		robot.keyRelease(KeyEvent.VK_CONTROL)
		Thread.sleep(500)
		robot.keyPress(KeyEvent.VK_CONTROL)
		robot.keyPress(KeyEvent.VK_V)
		robot.keyRelease(KeyEvent.VK_V)
		robot.keyRelease(KeyEvent.VK_CONTROL)
		Thread.sleep(500)
		robot.keyPress(KeyEvent.VK_TAB)
		robot.keyRelease(KeyEvent.VK_TAB)
		Thread.sleep(500)
		robot.keyPress(KeyEvent.VK_ENTER)
		robot.keyRelease(KeyEvent.VK_ENTER)
		Thread.sleep(2000)
		robot.keyPress(KeyEvent.VK_TAB)
		robot.keyRelease(KeyEvent.VK_TAB)
		Thread.sleep(500)
		robot.keyPress(KeyEvent.VK_ENTER)
		robot.keyRelease(KeyEvent.VK_ENTER)
		Thread.sleep(2000)
	} catch (Exception e) {
		e.printStackTrace()
	}
}

// Méthode pour supprimer un dossier
void deleteFolder(String folderPath) {
	try {
		def folder = new File(folderPath)
		if (folder.exists()) {
			FileUtils.deleteDirectory(folder)
			println('Le dossier a été supprimé avec succès.')
		} else {
			println('Le dossier n\'existe pas.')
		}
	} catch (Exception e) {
		println("Une erreur s'est produite lors de la suppression du dossier : $e.message")
		e.printStackTrace()
	}
}

@Keyword

String getRandomFolderName() {
	String folderName = 'TF' + getTimestamp()

	return folderName
}

String getTimestamp() {
	Date todaysDate = new Date()

	String formattedDate = todaysDate.format('_dd_MM_yyyy_hh_mm_ss')

	return formattedDate
}