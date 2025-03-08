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
import javax.swing.filechooser.FileSystemView as FileSystemView
import org.apache.poi.xwpf.usermodel.XWPFDocument as XWPFDocument
import java.io.FileOutputStream as FileOutputStream
import java.nio.file.FileSystems as FileSystems
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.apache.commons.io.FileUtils as FileUtils
import org.apache.commons.io.IOUtils as IOUtils
import java.io.ByteArrayInputStream as ByteArrayInputStream
import java.net.URL as URL
import java.nio.file.Files as Files
import java.nio.file.Paths as Paths
import java.nio.file.StandardCopyOption as StandardCopyOption
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.StringSelection as StringSelection
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import java.awt.FileDialog as FileDialog
import javax.swing.JFrame as JFrame
import java.nio.file.Path as Path

WebUI.callTestCase(findTestCase('File/Pre_test/Create_folder'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

WebUI.verifyElementClickable(findTestObject('file_objects/upload/Page_Folders - PowerFolder/Upload file'))

WebUI.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/Upload file'))

WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), 5)

// Attendre que l'élément devienne visible
WebElement addfile = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//div[5]/div/div/div[3]/div/div/span')))

// Cliquer sur l'élément
addfile.click()

// Création du fichier Word vide sur le bureau
String wordFileName = 'word_file_' + RandomStringUtils.randomNumeric(4)

def wordFilePath = createEmptyWordFileOnDesktop(wordFileName)

WebUI.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/lang_Cancel'))

// Uploader le fichier Word vide dans le test
selectWordFileAutomatically(wordFilePath)

WebUI.delay(5)

// Vérification de la présence du document
def btn = findDoc(wordFileName)

// Cliquer sur le bouton
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

assert btn != null

WebUI.delay(3)

// Supprimer le fichier Word créé sur le bureau
deleteWordFile(wordFilePath)

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
WebElement findDoc(String wordFileName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + wordFileName) + '\')]/td[1]/span'))
}

