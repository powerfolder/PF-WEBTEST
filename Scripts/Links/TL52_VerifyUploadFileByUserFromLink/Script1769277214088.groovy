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
import org.openqa.selenium.WebElement as Keys
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.DataFlavor as DataFlavor
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.annotation.Keyword as Keyword
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
import java.awt.datatransfer.StringSelection as StringSelection
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import java.awt.FileDialog as FileDialog
import javax.swing.JFrame as JFrame
import java.nio.file.Path as Path
import java.time.Duration as Duration

WebUI.callTestCase(findTestCase('My Account/Pre_test/Create Account'), [:], FailureHandling.STOP_ON_FAILURE)

String folderName = 'Main_Folder_' + getTimestamp()

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createFolder'))

WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)

WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.click(findTestObject('Object Repository/Folders/Page_Folders - PowerFolder/lang_Folders'))

WebElement btn = findShareButton(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.waitForElementClickable(findTestObject('Links/buttonCreateLink'), 30, FailureHandling.CONTINUE_ON_FAILURE)

WebElement buttonCreateLink = WebUiCommonHelper.findWebElement(findTestObject('Links/buttonCreateLink'), 30)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(buttonCreateLink))

WebUI.delay(3)

// Vérifier si l'élément "Can read and write" est cliquable
TestObject readWriteLabel = findTestObject('links files/Page_Folders - PowerFolder/label_Can read and write')

WebUI.verifyElementClickable(readWriteLabel, FailureHandling.CONTINUE_ON_FAILURE)

// Cliquer sur l'élément "Can read and write"
WebUI.click(readWriteLabel)

WebUI.click(findTestObject('SettingsPopUp/buttonSave'))

WebUI.doubleClick(findTestObject('Page_Folders - PowerFolder/icon-copy'))

String my_clipboard = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor)

WebUI.navigateToUrl(my_clipboard)

WebUI.delay(3)

assert WebUI.getWindowTitle().equals('Link - PowerFolder')

WebDriver driver = DriverFactory.getWebDriver()

List<WebElement> list = driver.findElements(By.className('pica-crumb'))

assert list.get(list.size() - 1).getText().equals(folderName)

WebUI.verifyElementClickable(findTestObject('Links/Upload from link'), FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Links/Upload from link'))

WebUI.delay(2)

WebUI.click(findTestObject('Links/Upload File'))

WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), Duration.ofSeconds(5))

// Création du fichier Word vide sur le bureau
String wordFileName = 'word_file_' + RandomStringUtils.randomNumeric(4)

def wordFilePath = createEmptyWordFileOnDesktop(wordFileName)

selectWordFileAutomatically(wordFilePath)

WebUI.click(findTestObject('Links/Confirm Upload'))

WebUI.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/lang_Cancel'))

// Uploader le fichier Word vide dans le test

WebUI.delay(5)

deleteWordFile(wordFilePath)

WebUI.navigateToUrl('https://mimas.powerfolder.net/folderstable')

WebUI.delay(3)

def btn1 = findFolder(folderName)

// Cliquer sur le bouton trouvé
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

// Vérification de la présence du dossier
def btn2 = finfFile(wordFileName)

assert btn2 != null : 'Le document n\'est pas présent.'

// Cliquer sur le bouton trouvé
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn2))

WebUI.delay(2)


WebUI.closeBrowser()


String createEmptyWordFileOnDesktop(String fileName) {
	def desktopWordPath = Paths.get(System.getProperty('user.home'), 'Desktop')

	if (!(Files.exists(desktopWordPath))) {
		Files.createDirectories(desktopWordPath)
	}
	
	def wordFilePath = desktopWordPath.resolve(fileName + '.docx')

	XWPFDocument document = new XWPFDocument()

	FileOutputStream out = new FileOutputStream(wordFilePath.toFile())

	document.write(out)

	out.close()

	return wordFilePath.toString()
}

void selectWordFileAutomatically(String wordFilePath) {
	try {
		Robot robot = new Robot()

		Thread.sleep(500)

		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(wordFilePath), null)

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

void deleteWordFile(String wordFilePath) {
	try {
		Path path = Paths.get(wordFilePath)

		boolean deleted = Files.deleteIfExists(path)

		if (deleted) {
			println('Le fichier a été supprimé avec succès.')
		} else {
			println('Le fichier n\'a pas été trouvé ou n\'a pas pu être supprimé.')
		}
	}
	catch (Exception e) {
		e.printStackTrace()
	}
}

String getTimestamp() {
    Date todaysDate = new Date()

    String formattedDate = todaysDate.format('dd_MM_yyyy_hh_mm_ss')

    return formattedDate
}

WebElement findShareButton(String fileName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + fileName) + '\')]/td[7]/a/span'))
}

WebElement findFolder(String folderName) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(By.xpath(('//td[2]/span/a[contains(text(),\'' + folderName) + '\')]'))
}

WebElement finfFile(String DocRename) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + DocRename) + '\')]/td[1]/span'))
}



