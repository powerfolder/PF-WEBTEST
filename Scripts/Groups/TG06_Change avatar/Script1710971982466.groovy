import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.apache.commons.io.FileUtils as FileUtils
import org.apache.commons.io.IOUtils as IOUtils
import java.io.ByteArrayInputStream as ByteArrayInputStream
import java.net.URL as URL
import java.nio.file.Files as Files
import java.nio.file.Paths as Paths
import java.nio.file.StandardCopyOption as StandardCopyOption
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebElement as WebElement
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.StringSelection as StringSelection
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import java.awt.FileDialog as FileDialog
import javax.swing.JFrame as JFrame

// Fonction pour télécharger une image depuis un lien et la placer dans un dossier sur le bureau
// Télécharger l'image depuis le lien spécifié et la placer sur le bureau
downloadImageAndPlaceOnDesktop('https://cdn-icons-png.flaticon.com/512/2919/2919906.png', 'avatar.png')

// Exécuter les étapes de test
WebUI.callTestCase(findTestCase('Groups/Pre_test/create_group'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Avatar'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Change'))

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/span_Add file_m'))

// Sélectionner l'image téléchargée pour continuer le test
def desktopImagePath = Paths.get(System.getProperty('user.home'), 'Desktop', 'images', 'avatar.png')

selectImageAutomatically(desktopImagePath.toString( // Fonction pour sélectionner automatiquement l'image dans l'explorateur de fichiers
        ))

// Créer une instance de Robot
// Attente pour que l'explorateur de fichiers s'ouvre complètement
// Coller le chemin d'accès de l'image
// Attendre un peu
// Appuyer sur la touche Entrée pour sélectionner l'image
WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Close'))

WebUI.verifyElementVisible(findTestObject('Groups/Page_Groups - PowerFolder/div_File successfully uploaded_av'))

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.closeBrowser()

def downloadImageAndPlaceOnDesktop(String imageUrl, String imageName) {
    def desktopImagePath = Paths.get(System.getProperty('user.home'), 'Desktop', 'images')

    if (!(Files.exists(desktopImagePath))) {
        Files.createDirectories(desktopImagePath)
    }
    
    def url = new URL(imageUrl)

    def imagePath = Paths.get(desktopImagePath.toString(), imageName)

    Files.copy(url.openStream(), imagePath, StandardCopyOption.REPLACE_EXISTING)
}

def selectImageAutomatically(String imagePath) {
    try {
        Robot robot = new Robot()

        Thread.sleep(1000)

        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(imagePath), null)

        robot.keyPress(KeyEvent.VK_CONTROL)

        robot.keyPress(KeyEvent.VK_V)

        robot.keyRelease(KeyEvent.VK_V)

        robot.keyRelease(KeyEvent.VK_CONTROL)

        Thread.sleep(1000)

        robot.keyPress(KeyEvent.VK_ENTER)

        robot.keyRelease(KeyEvent.VK_ENTER)
    }
    catch (Exception e) {
        e.printStackTrace()
    } 
}

