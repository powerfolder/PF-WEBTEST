 // Importations des classes nécessaires
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
import javax.swing.filechooser.FileSystemView as FileSystemView
import org.apache.poi.xwpf.usermodel.XWPFDocument as XWPFDocument
import java.io.FileOutputStream as FileOutputStream
import java.nio.file.Paths as Paths
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.apache.commons.io.FileUtils as FileUtils
import java.io.ByteArrayInputStream as ByteArrayInputStream
import java.net.URL as URL
import java.nio.file.Files as Files
import java.nio.file.StandardCopyOption as StandardCopyOption
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.StringSelection as StringSelection
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import java.awt.FileDialog as FileDialog
import javax.swing.JFrame as JFrame
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

// Définition du chemin vers le dossier sur le bureau
def desktopWordPath = Paths.get(System.getProperty('user.home'), 'Desktop')

String folderPath = desktopWordPath.resolve('mon_dossier').toString()

// Fonctions
// Appel du test case pour créer un dossier
WebUiBuiltInKeywords.callTestCase(findTestCase('File/Pre_test/Create_folder'), [:], FailureHandling.STOP_ON_FAILURE)

// Clic pour créer un élément à l'intérieur d'un dossier
WebUiBuiltInKeywords.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

// Vérification de la possibilité de cliquer sur un élément
WebUiBuiltInKeywords.verifyElementClickable(findTestObject('file_objects/upload/Page_Folders - PowerFolder/Upload file'))

// Clic pour uploader un fichier
WebUiBuiltInKeywords.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/Upload file'))

// Attendre que l'élément devienne visible
WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), 5)

WebElement addfile = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//div[5]/div/div/div[3]/div/div/span')))

addfile.click()

// Créer le dossier sur le bureau s'il n'existe pas déjà
createFolderIfNotExists(folderPath)

// Créer 10 fichiers dans le dossier
createFilesInFolder(folderPath, 10)

// Attendre que la fenêtre de dialogue de fichier soit disponible
Robot robot = new Robot()

robot.delay(2000 // Attendre 2 secondes pour laisser la fenêtre s'ouvrir
    )

// Coller le chemin dans la fenêtre de dialogue
StringSelection stringSelection = new StringSelection(folderPath)

Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null)

pasteFromClipboardAndConfirm(robot)

// Attendre le temps nécessaire pour le chargement des fichiers
robot.delay(2000)

// Sélectionner tous les fichiers dans le dossier
String allFiles = ''

for (int i = 1; i <= 10; i++) {
    allFiles += (('"file' + i) + '" ')
}

StringSelection allFilesSelection = new StringSelection(allFiles.trim())

Toolkit.getDefaultToolkit().getSystemClipboard().setContents(allFilesSelection, null)

// Coller la sélection dans la fenêtre de dialogue
pasteFromClipboardAndConfirm(robot)

// Attendre le temps nécessaire pour le téléchargement des fichiers
robot.delay(5000)

WebUI.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/lang_Cancel'))

WebUI.delay(3)

List<WebElement> items = findFiles()

// Méthode pour trouver les éléments dans la table
assert !(items.isEmpty())

// Méthode pour supprimer le dossier
// Supprimer le dossier créé sur le bureau
deleteFolder(folderPath)

WebUI.closeBrowser()

def pasteFromClipboardAndConfirm(Robot robot) {
    robot.keyPress(KeyEvent.VK_CONTROL)

    robot.keyPress(KeyEvent.VK_V)

    robot.keyRelease(KeyEvent.VK_V)

    robot.keyRelease(KeyEvent.VK_CONTROL)

    robot.keyPress(KeyEvent.VK_ENTER)

    robot.keyRelease(KeyEvent.VK_ENTER)
}

def createFolderIfNotExists(String folderPath) {
    File folder = new File(folderPath)

    if (!(folder.exists())) {
        folder.mkdir()
    }
}

def createFilesInFolder(String folderPath, int numberOfFiles) {
    for (int i = 1; i <= numberOfFiles; i++) {
        File file = new File(((folderPath + '/file') + i) + '.txt')

        file.createNewFile()
    }
}

List<WebElement> findFiles() {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElements(By.xpath('//div[2]/table/tbody'))
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

