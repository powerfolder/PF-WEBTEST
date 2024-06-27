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
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait
import java.util.Arrays as Arrays
import javax.swing.filechooser.FileSystemView as FileSystemView
import org.apache.poi.xwpf.usermodel.XWPFDocument as XWPFDocument
import java.io.FileOutputStream as FileOutputStream
import java.nio.file.Paths as Paths
import java.nio.file.Files as Files
import org.apache.commons.io.FileUtils as FileUtils
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.StringSelection as StringSelection
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import java.io.IOException as IOException
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

WebUI.callTestCase(findTestCase('Upload form/Pre_Test/Creat_upload_form'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementClickable(findTestObject('1Upload_Form/Page_Link - PowerFolder/span_Add directory'))

WebUI.click(findTestObject('1Upload_Form/Page_Link - PowerFolder/span_Add directory'))

// Création d'un dossier avec des fichiers Word sur le bureau
String folderName = 'folder_with_files_' + RandomStringUtils.randomNumeric(4)

String folderPath = createFolderWithWordFilesOnDesktop(folderName, 3)

selectFolderAutomatically(folderPath)

WebUI.click(findTestObject('1Upload_Form/Page_Link - PowerFolder/button_Upload_2'))

WebUI.delay(5)

WebUI.click(findTestObject('1Upload_Form/Page_Link - PowerFolder/lang_Close'))

WebUI.switchToWindowIndex('0')

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Close'))

WebElement btn = findFolder(GlobalVariable.folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebElement btn1 = findFolder(GlobalVariable.Name)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

// Vérification de la présence du dossier 
def btn2 = findUploadFolder(folderName)

// Cliquer sur le bouton
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn2))

assert btn2 != null

WebUI.delay(2)

WebUI.closeBrowser()

// Suppression du dossier
deleteFolder(folderPath // Méthode pour trouver un dossier par nom
    ) // Méthode pour créer un dossier avec des fichiers Word sur le bureau
// Méthode pour créer un fichier Word vide
// Méthode pour sélectionner un dossier automatiquement
// Méthode pour supprimer un dossier

@Keyword
WebElement findFolder(String folderName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//a[contains(text(),\'' + folderName) + '\')]'))
}

String createFolderWithWordFilesOnDesktop(String folderName, int numFiles) {
    def desktopFolderPath = Paths.get(System.getProperty('user.home'), 'Desktop', folderName)

    if (!(Files.exists(desktopFolderPath))) {
        Files.createDirectories(desktopFolderPath)
    }
    
    for (int i = 0; i < numFiles; i++) {
        String fileName = ('word_file_' + RandomStringUtils.randomNumeric(4)) + '.docx'

        createEmptyWordFileOnDesktop(desktopFolderPath.resolve(fileName).toString())
    }
    
    return desktopFolderPath.toString()
}

String createEmptyWordFileOnDesktop(String filePath) {
    XWPFDocument document = new XWPFDocument()

    FileOutputStream out = null

    try {
        out = new FileOutputStream(filePath)

        document.write(out)
    }
    catch (IOException e) {
        e.printStackTrace()
    } 
    finally { 
        if (out != null) {
            try {
                out.close()
            }
            catch (IOException e) {
                e.printStackTrace()
            } 
        }
        
        try {
            document.close()
        }
        catch (IOException e) {
            e.printStackTrace()
        } 
    }
    
    return filePath
}

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
    }
    catch (Exception e) {
        e.printStackTrace()
    } 
}

void deleteFolder(String folderPath) {
    try {
        def folder = new File(folderPath)

        if (folder.exists()) {
            FileUtils.deleteDirectory(folder)

            println('Le dossier a été supprimé avec succès.')
        } else {
            println('Le dossier n\'existe pas.')
        }
    }
    catch (Exception e) {
        println("Une erreur s'est produite lors de la suppression du dossier : $e.message")

        e.printStackTrace()
    } 
}

@Keyword
WebElement findUploadFolder(String folderName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + folderName) + '\')]/td[1]/span'))
}

