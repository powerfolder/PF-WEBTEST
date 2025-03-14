import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
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
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait
import java.nio.file.Files as Files
import java.nio.file.Paths as Paths
import java.nio.file.Path as Path
import java.util.Random as Random
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.StringSelection as StringSelection
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import internal.GlobalVariable as GlobalVariable
import java.util.Date as Date
import java.time.Duration


//////////////////////////////////////// MAIN CODE ////////////////////////////////////////

WebUI.callTestCase(findTestCase('Links/pre_test/Create folder'), [:])

WebUI.verifyElementClickable(findTestObject('file_objects/upload/Page_Folders - PowerFolder/Upload file'))

WebUI.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/Upload file'))

WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), Duration.ofSeconds(5))

WebElement addfile = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//body/div[2]/div[1]/div[2]/div[5]/div/div/div[3]/div/div[1]/span[1]')))
addfile.click()

// Générer le fichier binaire
String binaryName = getRandomFileName()

GlobalVariable.binaryName = binaryName

def binaryFilePath = createBinaryFileOnDesktop(binaryName)

WebUI.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/lang_Cancel'))
selectFileAutomatically(binaryFilePath)

WebUI.delay(2)


WebUI.refresh()

WebElement btn1 = findShareButton(binaryName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

WebUI.click(findTestObject('Object Repository/Folders/shareLink'))

deleteBinaryFile(binaryFilePath)

//////////////////////////////////// MÉTHODES ////////////////////////////////////

// Méthode pour créer un fichier binaire
String createBinaryFileOnDesktop(String fileName) {
	Path desktopPath = Paths.get(System.getProperty('user.home'), 'Desktop')

	if (!Files.exists(desktopPath)) {
		Files.createDirectories(desktopPath)
	}
	
	Path binaryFilePath = desktopPath.resolve(fileName + '.bin')

	// Créer un fichier binaire avec des données aléatoires
	byte[] randomData = new byte[1024] // 1 Ko de données aléatoires
	new Random().nextBytes(randomData)

	Files.write(binaryFilePath, randomData)
	return binaryFilePath.toString()
}

// Méthode pour supprimer le fichier binaire
void deleteBinaryFile(String filePath) {
	try {
		Path path = Paths.get(filePath)
		boolean deleted = Files.deleteIfExists(path)
		println(deleted ? 'Le fichier binaire a été supprimé avec succès.' : 'Le fichier binaire n\'a pas été trouvé.')
	} catch (Exception e) {
		e.printStackTrace()
	}
}

// Méthode pour sélectionner un fichier automatiquement
void selectFileAutomatically(String filePath) {
	try {
		Robot robot = new Robot()
		Thread.sleep(500)

		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(filePath), null)
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

// Localiser un document
WebElement findDoc(String fileName) {
	WebDriver driver = DriverFactory.getWebDriver()
	return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + fileName) + '\')]/td[2]/span/a'))
}

// Générer un nom de fichier unique
String getRandomFileName() {
	String name = 'binary_' + new Date().format('dd_MMM_yyyy_hh_mm_ss')
	return name
}

// Localiser le bouton de partage
WebElement findShareButton(String fileName) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + fileName) + '\')]/td[7]/a/span'))
}

