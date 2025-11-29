
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
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
// Top-level
String topLevel = 'Top-level_' + getRandomFolderName()

WebUI.callTestCase(
	findTestCase('Login/Pretest - Admin Login'),
	['variable' : ''],
	FailureHandling.STOP_ON_FAILURE
)

WebUI.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebUI.click(findTestObject('Object Repository/Folders/createFolderIcon'))

WebUI.click(findTestObject('Object Repository/Folders/createFolder'))

WebUI.setText(findTestObject('Object Repository/Folders/inputFolderName'), topLevel)

WebUI.click(findTestObject('Object Repository/Folders/buttonOK'))

WebUI.delay(3)

WebUI.setText(findTestObject('Folders/inputSearch'), topLevel)

WebUI.delay(5)

WebElement btnTop = findFolder(topLevel)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btnTop))

// subdirectory
WebUI.verifyElementClickable(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

String subDirectory = 'subdirectory_' + getRandomFolderName()

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))

WebUI.setText(
	findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'),
	subDirectory
)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebElement btnSub = findFolder(subDirectory)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btnSub))

// sub-subdirectory
WebUI.verifyElementClickable(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

String subSubDirectory = 'subSubDirectory_' + getRandomFolderName()

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))

WebUI.setText(
	findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'),
	subSubDirectory
)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebElement btnSubSub = findFolder(subSubDirectory)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btnSubSub))

WebUI.click(findTestObject('Object Repository/Folders/createFolderIcon'))

WebUI.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/Upload file'))

WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), Duration.ofSeconds(5))

WebElement addfile = wait.until(
	ExpectedConditions.visibilityOfElementLocated(
		By.xpath('//body/div[2]/div[1]/div[2]/div[5]/div/div/div[3]/div/div[1]/span[1]')
	)
)
addfile.click()

// Générer le fichier binaire
String binaryName = "binary_" + getRandomFileName()

String binaryFilePath = createBinaryFileOnDesktop(binaryName)

WebUI.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/lang_Cancel'))

selectFileAutomatically(binaryFilePath)

WebUI.delay(2)

WebUI.refresh()

WebElement shareBtn1 = Helper.findShareButton(binaryName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(shareBtn1))

WebUI.click(findTestObject('Object Repository/Folders/shareLink'))

deleteBinaryFile(binaryFilePath)


// Sauvegarder les paramètres
WebUI.click(findTestObject('Object Repository/Folders/button_SaveSettings'))

// Copier le lien
WebUI.doubleClick(findTestObject('Object Repository/Page_Folders - PowerFolder/icon-copy'))

String my_clipboard = Toolkit
	.getDefaultToolkit()
	.getSystemClipboard()
	.getContents(null)
	.getTransferData(DataFlavor.stringFlavor)

assert (my_clipboard != null) && my_clipboard.startsWith('https')

WebUI.click(findTestObject('links files/Page_Folders - PowerFolder/button_Close'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

WebUI.delay(2)

WebUI.navigateToUrl(my_clipboard)


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
