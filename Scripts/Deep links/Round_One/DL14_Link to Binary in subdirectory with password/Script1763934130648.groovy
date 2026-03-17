import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

/***********************
 *   KATALON KEYWORDS
 ***********************/
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.driver.DriverFactory
import internal.GlobalVariable

/***********************
 *   SELENIUM
 ***********************/
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.util.Date
import java.util.Arrays
import java.util.Random
import java.awt.Toolkit
import java.awt.Robot
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection
import java.awt.event.KeyEvent
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.Duration
import java.text.SimpleDateFormat
import java.util.Calendar
import helpers.Helper
import org.apache.commons.lang3.RandomStringUtils
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
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
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.DataFlavor as DataFlavor
import java.nio.file.Path as Path
import java.nio.file.Files as Files
import java.text.SimpleDateFormat as SimpleDateFormat
import java.util.Calendar as Calendar
import java.util.Date as Date
import org.junit.Assert as Assert
import helpers.Helper as Helper

//Top-level

String topLevel = "Top-level_" + getRandomFolderName()

WebUiBuiltInKeywords.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Folders/createFolderIcon'))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Folders/createFolder'))

WebUiBuiltInKeywords.setText(findTestObject('Object Repository/Folders/inputFolderName'), topLevel)

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Folders/buttonOK'))

//subdirectory

WebUI.verifyElementClickable(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

String subDirectory = "subdirectory_" + getRandomFolderName()

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))

WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'),
	subDirectory)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))


//sub-subdirectory

WebUI.verifyElementClickable(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

String subSubDirectory = "subSubDirectory_" + getRandomFolderName()

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))

WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'),
	subSubDirectory)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

// Upload via dialog (fichier binaire)
String projDir = RunConfiguration.getProjectDir()
String image_path = projDir + '/Images/image_links.png'   // (déclaré mais non utilisé ici, tu peux le supprimer si tu veux)

WebUI.click(findTestObject('Object Repository/Folders/createFolderIcon'))

WebUI.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/Upload file'))

WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), Duration.ofSeconds(5))

WebElement addfile = wait.until(
	ExpectedConditions.visibilityOfElementLocated(
		By.xpath('//body/div[2]/div[1]/div[2]/div[5]/div/div/div[3]/div/span[1]')
	)
)
addfile.click()

// Générer le fichier binaire
String binaryName = getRandomFileName()
GlobalVariable.binaryName = binaryName

String binaryFilePath = createBinaryFileOnDesktop(binaryName)

selectFileAutomatically(binaryFilePath)

WebUI.delay(2)

WebUI.refresh()

WebElement shareBtn1 = Helper.findShareButton(binaryName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(shareBtn1))

WebUI.click(findTestObject('Object Repository/Folders/shareLink'))

deleteBinaryFile(binaryFilePath)

binaryName = GlobalVariable.binaryName

// password
WebUI.setText(findTestObject('Page_Link - PowerFolder/lang_Password required'), 'Easy_PASS@131190')

// Sauvegarder les paramètres
WebUI.click(findTestObject('Object Repository/Folders/button_SaveSettings'))

// Copier le lien
WebUI.doubleClick(findTestObject('Object Repository/Page_Folders - PowerFolder/icon-copy'))

String my_clipboard = Toolkit
	.getDefaultToolkit()
	.getSystemClipboard()
	.getContents(null)
	.getTransferData(DataFlavor.stringFlavor)

WebUI.comment('URL copié : ' + my_clipboard)

// Vérifier que l'URL est valide
assert (my_clipboard != null) && my_clipboard.startsWith('https')

// Ouvrir le lien dans un nouvel onglet
WebUI.executeJavaScript("window.open(arguments[0], '_blank');", Arrays.asList(my_clipboard))

// Passer au nouvel onglet (index 1)
WebUI.switchToWindowIndex(1)

WebUI.delay(3)

WebUI.setText(findTestObject('Page_Link - PowerFolder/inputPassword'), 'Easy_PASS@131190')

WebUI.click(findTestObject('Page_Link - PowerFolder/buttonOK'))

// Optionnel : Vérification du titre de la page ou chargement réussi
WebUI.verifyNotEqual(WebUI.getWindowTitle(), '', FailureHandling.CONTINUE_ON_FAILURE)

// Vérification du titre de la fenêtre
assert WebUI.getWindowTitle().equals('Link - PowerFolder')

// Attendre que la page charge
WebUI.delay(3)

// Vérifier présence du bouton de download (ou élément PDF)
WebUI.verifyElementPresent(findTestObject('Links/Page_Link - PowerFolder/pdf_Download'), 2)


// Fermer le navigateur
WebUI.closeBrowser()


/*******************************
 *         MÉTHODES
 *******************************/


// Méthode pour créer un fichier binaire
String createBinaryFileOnDesktop(String fileName) {
	Path desktopPath = Paths.get(System.getProperty('user.home'), 'Desktop')

	if (!Files.exists(desktopPath)) {
		Files.createDirectories(desktopPath)
	}

	Path binaryFilePath = desktopPath.resolve(fileName + '.bin')

	// Créer un fichier binaire avec des données aléatoires (1 Ko)
	byte[] randomData = new byte[1024]
	new Random().nextBytes(randomData)

	Files.write(binaryFilePath, randomData)
	return binaryFilePath.toString()
}

// Méthode pour supprimer le fichier binaire
void deleteBinaryFile(String filePath) {
	try {
		Path path = Paths.get(filePath)
		boolean deleted = Files.deleteIfExists(path)
		println(deleted ?
			'Le fichier binaire a été supprimé avec succès.' :
			'Le fichier binaire n\'a pas été trouvé.')
	} catch (Exception e) {
		e.printStackTrace()
	}
}

// Méthode pour sélectionner un fichier automatiquement (dialog système)
void selectFileAutomatically(String filePath) {
	try {
		Robot robot = new Robot()
		Thread.sleep(500)

		Toolkit
			.getDefaultToolkit()
			.getSystemClipboard()
			.setContents(new StringSelection(filePath), null)

		robot.keyPress(KeyEvent.VK_CONTROL)
		robot.keyPress(KeyEvent.VK_V)
		robot.keyRelease(KeyEvent.VK_V)
		robot.keyRelease(KeyEvent.VK_CONTROL)

		Thread.sleep(500)

		robot.keyPress(KeyEvent.VK_ENTER)
		robot.keyRelease(KeyEvent.VK_ENTER)
	} catch (Exception e) {
		e.printStackTrace()
	}
}

// Localiser un document (non utilisé mais prêt si besoin)
WebElement findDoc(String fileName) {
	WebDriver driver = DriverFactory.getWebDriver()
	return driver.findElement(
		By.xpath("//*[contains(@data-search-keys, '" + fileName + "')]/td[2]/span/a")
	)
}

// Générer un nom de fichier unique pour le binaire
String getRandomFileName() {
	String name = 'binary_' + new Date().format('dd_MMM_yyyy_hh_mm_ss')
	return name
}

// Localiser un folder par son nom
@Keyword
WebElement findFolder(String folderName) {
	WebDriver driver = DriverFactory.getWebDriver()
	return driver.findElement(
		By.xpath("//td[2]/span/a[contains(text(),'" + folderName + "')]")
	)
}

// Timestamp générique
String getTimestamp() {
	Date todaysDate = new Date()
	String formattedDate = todaysDate.format('dd_MMM_yyyy_hh_mm_ss')
	return formattedDate
}

// Nom de dossier aléatoire
String getRandomFolderName() {
	String folderName = getTimestamp()
	return folderName
}
