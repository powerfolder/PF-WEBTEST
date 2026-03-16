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
import java.io.IOException as IOException

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
dynamicObject.addProperty('xpath', ConditionType.EQUALS, "//span[text()='" + mainFolderName + "']")

boolean exists = WebUI.verifyElementPresent(dynamicObject, 10, FailureHandling.OPTIONAL)

WebUI.verifyElementPresent(findTestObject('Folders/Page_Folders - PowerFolder/New subdirectory'), 5)
WebUI.click(findTestObject('Folders/Page_Folders - PowerFolder/New subdirectory'))

String subFolderName = "Sub_" + getRandomFolderName()
WebUI.setText(findTestObject('Folders/inputFolderName'), subFolderName)
WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.click(findTestObject('Folders/Page_Folders - PowerFolder/New subdirectory'))

String subSubFolderName = "Sub_Sub_" + getRandomFolderName()
WebUI.setText(findTestObject('Folders/inputFolderName'), subSubFolderName)
WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.verifyElementPresent(findTestObject('Folders/Page_Folders - PowerFolder/Upload files'), 5)
WebUI.click(findTestObject('Folders/Page_Folders - PowerFolder/Upload files'))



// Création du fichier Word vide sur le bureau
String wordFileName = 'word_file_' + RandomStringUtils.randomNumeric(4)
String wordFilePath = createEmptyWordFileOnDesktop(wordFileName)

// Upload direct dans l'input file
TestObject uploadInput = new TestObject('uploadInput')
uploadInput.addProperty('xpath', ConditionType.EQUALS, "//input[@id='upload_input_files']")

WebUI.waitForElementPresent(uploadInput, 10)
WebUI.uploadFile(uploadInput, wordFilePath)

// Attendre que l’upload soit visible comme réussi
TestObject successMsg = new TestObject('successMsg')
successMsg.addProperty('xpath', ConditionType.EQUALS, "//*[contains(text(),'Successfully uploaded')]")

WebUI.waitForElementVisible(successMsg, 15)

// Fermer la popup avec le vrai bouton Close
TestObject closeBtn = new TestObject('closeBtn')
closeBtn.addProperty('xpath', ConditionType.EQUALS, "//button[@id='upload_stop_button']")

WebUI.waitForElementClickable(closeBtn, 10)
WebUI.click(closeBtn)

WebUI.delay(2)

// Vérification de la présence du document
def btn = findDoc(wordFileName)
assert btn != null

// Cliquer sur le document si besoin
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.delay(3)

// Supprimer le fichier Word créé sur le bureau
deleteWordFile(wordFilePath)

WebUI.closeBrowser()

String createEmptyWordFileOnDesktop(String fileName) {
	def desktopWordPath = Paths.get(System.getProperty('user.home'), 'Desktop')

	if (!Files.exists(desktopWordPath)) {
		Files.createDirectories(desktopWordPath)
	}

	def wordFilePath = desktopWordPath.resolve(fileName + '.docx')

	XWPFDocument document = new XWPFDocument()
	FileOutputStream out = new FileOutputStream(wordFilePath.toFile())

	document.write(out)
	out.close()
	document.close()

	return wordFilePath.toString()
}

void deleteWordFile(String wordFilePath) {
	try {
		Path path = Paths.get(wordFilePath)
		boolean deleted = Files.deleteIfExists(path)

		if (deleted) {
			println('Le fichier a été supprimé avec succès.')
		} else {
			println("Le fichier n'a pas été trouvé ou n'a pas pu être supprimé.")
		}
	} catch (Exception e) {
		e.printStackTrace()
	}
}

@Keyword
WebElement findDoc(String wordFileName) {
	WebDriver driver = DriverFactory.getWebDriver()
	return driver.findElement(By.xpath("//*[contains(@data-search-keys, '" + wordFileName + "')]/td[1]/span"))
}

String getRandomFolderName() {
	return 'TF' + getTimestamp()
}

String getTimestamp() {
	Date todaysDate = new Date()
	return todaysDate.format('_dd_MM_yyyy_hh_mm_ss')
}