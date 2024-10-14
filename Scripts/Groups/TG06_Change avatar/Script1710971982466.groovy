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
import javax.net.ssl.*
import java.security.cert.X509Certificate
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import com.kms.katalon.core.configuration.RunConfiguration
import java.nio.file.Paths
import java.awt.Robot
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.awt.event.KeyEvent


// Appel du cas de test pour créer un groupe
WebUI.callTestCase(findTestCase('Groups/Pre_test/create_group'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Avatar'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Change'))

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/span_Add file_m'))

// Chemin vers l'image locale déjà présente dans le projet Katalon
def localImagePath = Paths.get(RunConfiguration.getProjectDir(), 'images', 'avatar.png')

// Utilisez l'image locale dans le reste de votre script
selectImageAutomatically(localImagePath.toString())

WebUI.delay(3)

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Close'))

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.delay(3)

WebUI.verifyElementVisible(findTestObject('Groups/Page_Groups - PowerFolder/div_File successfully uploaded_av'))

WebUI.closeBrowser()

// Fonction pour sélectionner une image automatiquement
def selectImageAutomatically(String imagePath) {
	try {
		Robot robot = new Robot()

		WebUI.delay(1)

		// Copier le chemin de l'image dans le presse-papiers
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(imagePath), null)

		// Simulation de CTRL + V pour coller le chemin
		robot.keyPress(KeyEvent.VK_CONTROL)
		robot.keyPress(KeyEvent.VK_V)
		robot.keyRelease(KeyEvent.VK_V)
		robot.keyRelease(KeyEvent.VK_CONTROL)

		WebUI.delay(1)

		// Appuyer sur Entrée pour confirmer
		robot.keyPress(KeyEvent.VK_ENTER)
		robot.keyRelease(KeyEvent.VK_ENTER)
	} catch (Exception e) {
		e.printStackTrace()
	}
}
