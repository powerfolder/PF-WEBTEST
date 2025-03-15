import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable
import org.openqa.selenium.Keys
import org.apache.commons.lang3.RandomStringUtils
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.util.Arrays
import javax.swing.filechooser.FileSystemView
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.FileOutputStream
import java.nio.file.Paths
import java.nio.file.Files
import org.apache.commons.io.FileUtils
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.awt.Robot
import java.awt.event.KeyEvent
import java.io.IOException
import java.time.Duration

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

// Appel du cas de test pour créer un dossier
WebUI.callTestCase(findTestCase('File/Pre_test/Create_folder'), [:], FailureHandling.STOP_ON_FAILURE)

// Navigation vers la création d'un élément dans le dossier
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/Upload file'))

// Attente explicite pour l'élément d'ajout
WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), Duration.ofSeconds(5))
WebElement adddir = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//div[5]/div/div/div[3]/div/div/span[2]')))
adddir.click()

// Création d'un dossier avec des fichiers Word sur le bureau
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
