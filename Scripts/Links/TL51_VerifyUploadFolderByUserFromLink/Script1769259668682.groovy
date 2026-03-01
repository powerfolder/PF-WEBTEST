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
import org.openqa.selenium.WebElement as Keys
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.DataFlavor as DataFlavor
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberBuiltinKeywords
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as MobileBuiltInKeywords
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGBuiltinKeywords
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as WindowsBuiltinKeywords
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import java.util.Arrays as Arrays
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait
import javax.swing.filechooser.FileSystemView as FileSystemView
import org.apache.poi.xwpf.usermodel.XWPFDocument as XWPFDocument
import java.io.FileOutputStream as FileOutputStream
import java.nio.file.FileSystems as FileSystems
import org.apache.commons.io.FileUtils as FileUtils
import org.apache.commons.io.IOUtils as IOUtils
import java.io.ByteArrayInputStream as ByteArrayInputStream
import java.net.URL as URL
import java.nio.file.Files as Files
import java.nio.file.Paths as Paths
import java.nio.file.StandardCopyOption as StandardCopyOption
import java.awt.datatransfer.StringSelection as StringSelection
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import java.awt.FileDialog as FileDialog
import javax.swing.JFrame as JFrame
import java.nio.file.Path as Path
import java.time.Duration as Duration

WebUI.callTestCase(findTestCase('My Account/Pre_test/Create Account'), [:], FailureHandling.STOP_ON_FAILURE)

String folderName = 'Main_Folder_' + getTimestamp()

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createFolder'))

WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)

WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.click(findTestObject('Object Repository/Folders/Page_Folders - PowerFolder/lang_Folders'))

WebElement btn = findShareButton(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.waitForElementClickable(findTestObject('Links/buttonCreateLink'), 30, FailureHandling.CONTINUE_ON_FAILURE)

WebElement buttonCreateLink = WebUiCommonHelper.findWebElement(findTestObject('Links/buttonCreateLink'), 30)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(buttonCreateLink))

WebUI.delay(3)

// Vérifier si l'élément "Can read and write" est cliquable
TestObject readWriteLabel = findTestObject('links files/Page_Folders - PowerFolder/label_Can read and write')

WebUI.verifyElementClickable(readWriteLabel, FailureHandling.CONTINUE_ON_FAILURE)

// Cliquer sur l'élément "Can read and write"
WebUI.click(readWriteLabel)

WebUI.click(findTestObject('SettingsPopUp/buttonSave'))

WebUI.doubleClick(findTestObject('Page_Folders - PowerFolder/icon-copy'))

String my_clipboard = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor)

WebUI.navigateToUrl(my_clipboard)

WebUI.delay(3)

assert WebUI.getWindowTitle().equals('Link - PowerFolder')

WebDriver driver = DriverFactory.getWebDriver()

List<WebElement> list = driver.findElements(By.className('pica-crumb'))

assert list.get(list.size() - 1).getText().equals(folderName)

WebUI.verifyElementClickable(findTestObject('Links/Upload from link'), FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Links/Upload from link'))

WebUI.delay(2)

WebUI.click(findTestObject('Links/Upload Folder'))

String uploadFolderName = 'folder_with_files_' + getTimestamp()

String folderPath = createFolderWithWordFilesOnDesktop(uploadFolderName, 3)

selectFolderAutomatically(folderPath)

WebUI.click(findTestObject('Links/Confirm Upload'))

WebUI.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/lang_Cancel'))

WebUI.delay(3)

def btn1 = findFolderLink(uploadFolderName)

// Cliquer sur le bouton trouvé
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

WebUI.delay(2)

deleteFolder(folderPath) 

WebUI.navigateToUrl('https://mimas.powerfolder.net/folderstable')

WebUI.delay(3)

def btn2 = findFolder(folderName)

// Cliquer sur le bouton trouvé
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn2))

// Vérification de la présence du dossier
def btn3 = clickFolder(uploadFolderName)

assert btn3 != null : 'Le document n\'est pas présent.'

// Cliquer sur le bouton trouvé
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn3))

WebUI.delay(2)


WebUI.closeBrowser()

@Keyword
WebElement findFolderLink(String folderName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//table[@id=\'filelink_table\']//tbody//tr/td[2]//a[contains(normalize-space(.),\'' + 
            folderName) + '\')]'))
}

WebElement findFolder(String folderName) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(By.xpath(('//td[2]/span/a[contains(text(),\'' + folderName) + '\')]'))
}

WebElement clickFolder(String DocRename) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + DocRename) + '\')]/td[1]/span'))
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

WebElement findShareButton(String fileName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + fileName) + '\')]/td[7]/a/span'))
}

String getTimestamp() {
    Date todaysDate = new Date()

    String formattedDate = todaysDate.format('dd_MM_yyyy_hh_mm_ss')

    return formattedDate
}

