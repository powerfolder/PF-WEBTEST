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
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.DataFlavor as DataFlavor
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import com.kms.katalon.core.annotation.Keyword as Keyword
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait
import java.util.Arrays as Arrays
import javax.swing.filechooser.FileSystemView as FileSystemView
import org.apache.poi.xwpf.usermodel.XWPFDocument as XWPFDocument
import java.io.FileOutputStream as FileOutputStream
import java.nio.file.Path as Path
import java.nio.file.Paths as Paths
import java.nio.file.Files as Files
import org.apache.commons.io.FileUtils as FileUtils
import java.awt.datatransfer.StringSelection as StringSelection
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import java.io.IOException as IOException

WebUI.callTestCase(findTestCase('Upload form/Pre_Test/Creat_Folder'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementClickable(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Create upload form'))

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Create upload form'))

WebUI.setText(findTestObject('1Upload_Form/Page_Folders - PowerFolder/input_uploadform_heading'), 'Workshop')

WebUI.setText(findTestObject('1Upload_Form/Page_Folders - PowerFolder/change_description'), 'Workshop number 1')

WebUI.scrollToElement(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Save'), 1)

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Save'))

WebUI.executeJavaScript('window.open();', [])

WebUI.delay(2)

WebUI.switchToWindowIndex(0)

WebUI.waitForElementClickable(findTestObject('1Upload_Form/Page_Folders - PowerFolder/clipboard_buttom'), 2)

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/clipboard_buttom'))

WebUI.delay(2)

WebUI.switchToWindowIndex(1)

String my_clipboard = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor)

WebUI.navigateToUrl(my_clipboard)

GlobalVariable.userEmail = (('user_' + RandomStringUtils.randomNumeric(4)) + '@test.com')

GlobalVariable.Name = ('My_Name_' + RandomStringUtils.randomNumeric(4))

WebUI.setText(findTestObject('1Upload_Form/Page_Link - PowerFolder/input_username'), GlobalVariable.Name)

WebUI.setText(findTestObject('1Upload_Form/Page_Link - PowerFolder/input_mail'), GlobalVariable.userEmail)

WebUI.verifyElementClickable(findTestObject('1Upload_Form/Page_Link - PowerFolder/button_Upload'))

WebUI.click(findTestObject('1Upload_Form/Page_Link - PowerFolder/button_Upload'))

WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), 5)

// Attendre que l'élément devienne visible
WebElement addfile = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//div[3]/div/div/span')))

// Cliquer sur l'élément
addfile.click()

// Création du fichier Word vide sur le bureau
String wordFileName = 'word_file_' + RandomStringUtils.randomNumeric(4)

def wordFilePath = createEmptyWordFileOnDesktop(wordFileName)

// Uploader le fichier Word vide dans le test
selectWordFileAutomatically(wordFilePath)

WebUI.click(findTestObject('1Upload_Form/Page_Link - PowerFolder/button_Upload_2'))

WebUI.delay(3)

WebUI.click(findTestObject('1Upload_Form/Page_Link - PowerFolder/lang_Close'))

// Supprimer le fichier Word créé sur le bureau
deleteWordFile(wordFilePath)

WebUI.switchToWindowIndex(0)

WebUI.delay(2)

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Close'))

WebElement btn = findFolder(GlobalVariable.folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebElement btn1 = findFolder(GlobalVariable.Name)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

WebUI.delay(2)

def btn2 = findDoc(wordFileName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn2))

assert btn2 != null

WebUI.delay(2)

WebUI.closeWindowIndex('1')

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

@Keyword
WebElement findFolder(String folderName) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(By.xpath(('//a[contains(text(),\'' + folderName) + '\')]'))
}

@Keyword
WebElement findDoc(String wordFileName) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + wordFileName) + '\')]/td[1]/span'))
}

