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
import java.io.OutputStream as OutputStream
import java.time.Duration


WebUI.callTestCase(findTestCase('Links/pre_test/Create folder'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementClickable(findTestObject('file_objects/upload/Page_Folders - PowerFolder/Upload file'))

WebUI.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/Upload file'))

WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), Duration.ofSeconds(5))

WebElement addfile = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//body/div[2]/div[1]/div[2]/div[5]/div/div/div[3]/div/div[1]/span[1]')))
addfile.click()

// Création du fichier PDF vide sur le bureau
String pdfname = getRandomFileName()

GlobalVariable.pdfname = pdfname

def pdfFilePath = createEmptyPdfFileOnDesktop(pdfname)

WebUI.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/lang_Cancel'))

// Uploader le fichier PDF vide dans le test
selectPdfFileAutomatically(pdfFilePath)

WebUI.delay(2)

def btn = findDoc(pdfname)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

assert btn != null

WebUI.switchToWindowIndex(1)

WebUI.delay(2)

WebUI.switchToWindowIndex(0)

WebUI.delay(2)

WebUI.refresh()

WebElement btn1 = findShareButton(pdfname)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

WebUI.click(findTestObject('Object Repository/Folders/shareLink')) 

deletePdfFile(pdfFilePath)

/////////////////////////////methodes//////////////////////////////////////////
// Chemin vers le bureau
// Créer un chemin pour le fichier PDF
// Contenu minimal pour un fichier PDF vide
// Écrire le contenu dans le fichier
// Méthode pour uploader automatiquement le fichier PDF
// Méthode pour supprimer un fichier PDF
// Méthode pour localiser un document PDF

String createEmptyPdfFileOnDesktop(String fileName) {
    Path desktopPdfPath = Paths.get(System.getProperty('user.home'), 'Desktop')

    if (!(Files.exists(desktopPdfPath))) {
        Files.createDirectories(desktopPdfPath)
    }
    
    Path pdfFilePath = desktopPdfPath.resolve(fileName + '.pdf')

    String pdfHeader = ((((((('%PDF-1.4\n' + '1 0 obj\n<< /Type /Catalog /Pages 2 0 R >>\n') + 'endobj\n') + '2 0 obj\n<< /Type /Pages /Kids [3 0 R] /Count 1 >>\n') + 
    'endobj\n') + '3 0 obj\n<< /Type /Page /Parent 2 0 R /MediaBox [0 0 612 792] >>\n') + 'endobj\n') + 'xref\n0 4\n0000000000 65535 f \n0000000010 00000 n \n0000000053 00000 n \n0000000096 00000 n \n') + 
    'trailer\n<< /Size 4 /Root 1 0 R >>\nstartxref\n128\n%%EOF'

    OutputStream os = Files.newOutputStream(pdfFilePath)

    os.write(pdfHeader.getBytes())

    os.close()

    return pdfFilePath.toString()
}

void selectPdfFileAutomatically(String pdfFilePath) {
    try {
        Robot robot = new Robot()

        Thread.sleep(500)

        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(pdfFilePath), null)

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

void deletePdfFile(String pdfFilePath) {
    try {
        Path path = Paths.get(pdfFilePath)

        boolean deleted = Files.deleteIfExists(path)

        if (deleted) {
            println('Le fichier PDF a été supprimé avec succès.')
        } else {
            println('Le fichier PDF n\'a pas été trouvé ou n\'a pas pu être supprimé.')
        }
    }
    catch (Exception e) {
        e.printStackTrace()
    } 
}

WebElement findDoc(String pdfFileName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + pdfFileName) + '\')]/td[2]/span/a'))
}
//body/div[2]/div[1]/div[2]/div[2]/table/tbody/tr/td[2]/span/a
String getRandomFileName() {
    String documentname = 'pdf_file_' + getTimestamp()

    return documentname
}

String getTimestamp() {
    Date todaysDate = new Date()

    String formattedDate = todaysDate.format('dd_MMM_yyyy_hh_mm_ss')

    return formattedDate
}

WebElement findShareButton(String fileName) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + fileName) + '\')]/td[7]/a/span'))
}
