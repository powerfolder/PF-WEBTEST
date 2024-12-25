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
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.StringSelection as StringSelection
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import internal.GlobalVariable as GlobalVariable

WebUI.callTestCase(findTestCase('Links/pre_test/Create folder'), [:])

WebUI.verifyElementClickable(findTestObject('file_objects/upload/Page_Folders - PowerFolder/Upload file'))
WebUI.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/Upload file'))

WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), 5)
WebElement addfile = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//div[5]/div/div/div[3]/div/div/span')))
addfile.click()

// Générer la vidéo MP4
String videoName = getRandomFileName()
GlobalVariable.videoName = videoName

def videoPath = createEmptyVideoFileOnDesktop(videoName)

WebUI.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/lang_Cancel'))
selectFileAutomatically(videoPath)

WebUI.delay(2)
def btn = findDoc(videoName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))
assert btn != null

WebUI.delay(2)

WebUI.refresh()

WebElement btn1 = findShareButton(videoName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))
WebUI.click(findTestObject('Object Repository/Folders/shareLink'))

deleteVideoFile(videoPath)

//////////////////// MÉTHODES ///////////////////////

// Méthode pour créer une vidéo MP4 vide
String createEmptyVideoFileOnDesktop(String fileName) {
	Path desktopPath = Paths.get(System.getProperty('user.home'), 'Desktop')

	if (!(Files.exists(desktopPath))) {
		Files.createDirectories(desktopPath)
	}
	
	Path videoPath = desktopPath.resolve(fileName + '.mp4')

	// Contenu minimal pour une vidéo MP4 vide
	byte[] emptyMp4Content = [
		0x00, 0x00, 0x00, 0x18, 0x66, 0x74, 0x79, 0x70, 0x6D, 0x70, 0x34, 0x32,
		0x00, 0x00, 0x02, 0x00, 0x6D, 0x70, 0x34, 0x32, 0x00, 0x00, 0x00, 0x08,
		0x6D, 0x64, 0x61, 0x74, 0x00, 0x00, 0x00, 0x00
	] as byte[]

	Files.write(videoPath, emptyMp4Content)
	return videoPath.toString()
}

// Méthode pour uploader automatiquement un fichier
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
	}
	catch (Exception e) {
		e.printStackTrace()
	}
}

// Méthode pour supprimer le fichier vidéo
void deleteVideoFile(String filePath) {
	try {
		Path path = Paths.get(filePath)
		boolean deleted = Files.deleteIfExists(path)
		println(deleted ? 'Le fichier vidéo a été supprimé avec succès.' : 'Le fichier vidéo n\'a pas été trouvé.')
	}
	catch (Exception e) {
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
	String name = 'video_' + new Date().format('dd_MMM_yyyy_hh_mm_ss')
	return name
}

WebElement findShareButton(String fileName) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + fileName) + '\')]/td[7]/a/span'))
}

