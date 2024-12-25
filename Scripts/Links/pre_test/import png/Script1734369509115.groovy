import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait
import java.awt.Graphics2D as Graphics2D
import java.awt.image.BufferedImage as BufferedImage
import java.nio.file.Files as Files
import java.nio.file.Paths as Paths
import java.nio.file.Path as Path
import javax.imageio.ImageIO as ImageIO
import java.awt.Color as Color
import java.awt.Font as Font
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.StringSelection as StringSelection
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
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
import java.util.Arrays as Arrays
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
import java.nio.file.StandardCopyOption as StandardCopyOption
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.awt.FileDialog as FileDialog
import javax.swing.JFrame as JFrame
import java.io.OutputStream as OutputStream

WebUI.callTestCase(findTestCase('Links/pre_test/Create folder'), [:])

WebUI.verifyElementClickable(findTestObject('file_objects/upload/Page_Folders - PowerFolder/Upload file'))

WebUI.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/Upload file'))

WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), 5)

WebElement addfile = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//div[5]/div/div/div[3]/div/div/span')))

addfile.click()

// Générer l'image PNG
String imageName = getRandomFileName()

GlobalVariable.imageName = imageName

def imagePath = createEmptyImageFileOnDesktop(imageName)

WebUI.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/lang_Cancel'))

selectFileAutomatically(imagePath)

WebUI.delay(2)

def btn = findDoc(imageName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

assert btn != null

WebUI.delay(2)

WebUI.refresh()

WebElement btn1 = findShareButton(imageName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

WebUI.click(findTestObject('Object Repository/Folders/shareLink'))

deleteImageFile(imagePath)

 //////////////////////////////// MÉTHODES //////////////////////////////////
// Méthode pour créer une image PNG avec du texte
// Méthode pour uploader automatiquement un fichier
// Méthode pour supprimer le fichier image
// Localiser un document
// Générer un nom de fichier unique

String createEmptyImageFileOnDesktop(String fileName) {
    Path desktopPath = Paths.get(System.getProperty('user.home'), 'Desktop')

    if (!(Files.exists(desktopPath))) {
        Files.createDirectories(desktopPath)
    }
    
    Path imagePath = desktopPath.resolve(fileName + '.png')

    BufferedImage image = new BufferedImage(200, 100, BufferedImage.TYPE_INT_RGB)

    Graphics2D g2d = image.createGraphics()

    g2d.setColor(Color.WHITE)

    g2d.fillRect(0, 0, 200, 100)

    g2d.setColor(Color.BLACK)

    g2d.setFont(new Font('Arial', Font.BOLD, 14))

    g2d.drawString('Image Test', 50, 50)

    g2d.dispose()

    ImageIO.write(image, 'png', imagePath.toFile())

    return imagePath.toString()
}

void selectFileAutomatically(String filePath) {
    try {
        Robot robot = new Robot()

        Thread.sleep(500)

        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(filePath), null)

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

void deleteImageFile(String filePath) {
    try {
        Path path = Paths.get(filePath)

        boolean deleted = Files.deleteIfExists(path)

        println(deleted ? 'Le fichier image a été supprimé avec succès.' : 'Le fichier image n\'a pas été trouvé.')
    }
    catch (Exception e) {
        e.printStackTrace()
    } 
}

WebElement findDoc(String fileName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + fileName) + '\')]/td[2]/span/a'))
}

String getRandomFileName() {
    String name = 'image_' + new Date().format('dd_MMM_yyyy_hh_mm_ss')

    return name
}

WebElement findShareButton(String fileName) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + fileName) + '\')]/td[7]/a/span'))
}

