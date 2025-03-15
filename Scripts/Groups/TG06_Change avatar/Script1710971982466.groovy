import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.annotation.Keyword as Keyword

import java.nio.file.Files as Files
import java.nio.file.Path as Path
import java.nio.file.Paths as Paths
import java.nio.file.StandardCopyOption as StandardCopyOption

import java.awt.Robot as Robot
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.StringSelection as StringSelection
import java.awt.event.KeyEvent as KeyEvent
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

Path desktopPath = Paths.get(System.getProperty("user.home"), "Desktop", "temp_avatar_folder")

Path tempImagePath = createTempFolderWithImage(desktopPath)

try {
	// Appel du cas de test pour créer un groupe
	WebUI.callTestCase(findTestCase('Groups/Pre_test/create_group'), [:], FailureHandling.STOP_ON_FAILURE)


	WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/a_Edit_m'))
	
	WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Avatar'))
	
	WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Change'))
	
	WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/span_Add file_m'))

	// Utiliser le chemin de l'image temporaire dans le reste du script
	selectImageAutomatically(tempImagePath.toString())

	WebUI.delay(3)
	
	WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Close'))
	
	WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Save'))
	
	WebUI.delay(3)

	WebUI.verifyElementVisible(findTestObject('Groups/Page_Groups - PowerFolder/div_File successfully uploaded_av'))
	
} finally {
	// Suppression du dossier temporaire
	deleteTempFolder(desktopPath)
	println("Dossier temporaire supprimé : ${desktopPath}")
}

WebUI.closeBrowser()

///////////////////////////// Méthodes /////////////////////////////////////////

// Fonction pour créer un dossier temporaire contenant l'image sur le bureau
def createTempFolderWithImage(Path folderPath) {
	try {
		if (!Files.exists(folderPath)) {
			Files.createDirectories(folderPath)
		}
		Path sourceImage = Paths.get(RunConfiguration.getProjectDir(), 'images', 'avatar.png')
		Path targetImage = folderPath.resolve("avatar.png")
		Files.copy(sourceImage, targetImage, StandardCopyOption.REPLACE_EXISTING)
		println("Image copiée dans le dossier temporaire : ${targetImage}")
		return targetImage
	} catch (Exception e) {
		e.printStackTrace()
		throw new RuntimeException("Erreur lors de la création du dossier temporaire")
	}
}

// Fonction pour supprimer le dossier temporaire
def deleteTempFolder(Path folderPath) {
	try {
		Files.walk(folderPath)
			 .sorted(Comparator.reverseOrder())
			 .forEach(Files.&deleteIfExists)
		println("Dossier temporaire supprimé avec succès.")
	} catch (Exception e) {
		e.printStackTrace()
		println("Erreur lors de la suppression du dossier temporaire.")
	}
}

// Fonction pour sélectionner une image automatiquement
def selectImageAutomatically(String imagePath) {
	try {
		Robot robot = new Robot()
		WebUI.delay(1)
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(imagePath), null)
		robot.keyPress(KeyEvent.VK_CONTROL)
		robot.keyPress(KeyEvent.VK_V)
		robot.keyRelease(KeyEvent.VK_V)
		robot.keyRelease(KeyEvent.VK_CONTROL)
		WebUI.delay(1)
		robot.keyPress(KeyEvent.VK_ENTER)
		robot.keyRelease(KeyEvent.VK_ENTER)
	} catch (Exception e) {
		e.printStackTrace()
	}
}
