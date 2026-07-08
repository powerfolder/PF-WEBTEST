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
import java.time.Duration as Duration

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('LeftNavigationIcons/Preferences'))

WebUI.click(findTestObject('Preferences/Web/Web_Configure'))

WebUI.click(findTestObject('Preferences/Web/Open files in web settings'))

WebUI.delay(2)

WebUI.click(findTestObject('Preferences/Web/Open files in web settings'))

TestObject pdfInput = new TestObject('PDF hidden input')

pdfInput.addProperty('xpath', com.kms.katalon.core.testobject.ConditionType.EQUALS, '//input[@id=\'PDF\']')

WebUI.verifyElementAttributeValue(pdfInput, 'value', 'BROWSER', 10)

WebUI.click(findTestObject('Preferences/Web/button_Cancel'))

GlobalVariable.userName = generateRandomString(8)

GlobalVariable.userLastName = generateRandomString(8)

GlobalVariable.userEmail = generateRandomEmail().toLowerCase()

GlobalVariable.PhoneNumber = generateRandomPhoneNumber()

WebUI.click(findTestObject('LeftNavigationIcons/account'))

WebUI.click(findTestObject('Accounts/CreateButton'))

WebUI.click(findTestObject('Accounts/ClickCreateAccount'))

WebUI.setText(findTestObject('Accounts/InputUserOrEmail'), GlobalVariable.userEmail)

WebUI.setText(findTestObject('Accounts/InputPassword'), GlobalVariable.Pass)

WebUI.setText(findTestObject('Accounts/InputFirstName'), GlobalVariable.userName)

WebUI.setText(findTestObject('Accounts/InputLastName'), GlobalVariable.userLastName)

WebUI.setText(findTestObject('Accounts/InputPhoneNo'), GlobalVariable.PhoneNumber)

WebUI.setText(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/account_storage_overwiew'), '5')

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.refresh()

WebUI.delay(2)

WebUI.delay(3)

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

WebUI.setText(findTestObject('Login/inputEmail'), GlobalVariable.userEmail)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(3)

String folderName = getRandomFolderName()

WebUI.click(findTestObject('Object Repository/Folders/createFolderIcon'))

WebUI.click(findTestObject('Object Repository/Folders/createFolder'))

WebUI.setText(findTestObject('Object Repository/Folders/inputFolderName'), folderName)

WebUI.click(findTestObject('Object Repository/Folders/buttonOK'))

assert WebUI.getWindowTitle().equals('Folders - PowerFolder')

String textfilename = getRandomFileName()

GlobalVariable.textfilename = textfilename

WebUI.click(findTestObject('Object Repository/Folders/createFolderIcon'))

WebUI.verifyElementClickable(findTestObject('file_objects/upload/Page_Folders - PowerFolder/Upload file'))

WebUI.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/Upload file'))

WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), Duration.ofSeconds(5))

WebElement addfile = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//body/div[2]/div/div/main/div[4]/div/div/div[3]/div/span[1]')))

addfile.click()

// Création du fichier PDF vide sur le bureau
String pdfname = getRandomFileName()

GlobalVariable.pdfname = pdfname

def pdfFilePath = createEmptyPdfFileOnDesktop(pdfname)

// Uploader le fichier PDF vide dans le test
selectPdfFileAutomatically(pdfFilePath)

WebUI.delay(2)

WebUI.refresh()

deletePdfFile(pdfFilePath)

WebElement btn1 = findDoc(pdfname)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

WebUI.switchToWindowIndex(1)

WebUI.delay(2)

String currentUrl = WebUI.getUrl()

WebUI.comment('L\'URL actuelle est: ' + currentUrl)

assert currentUrl.contains(pdfname)

WebUI.closeBrowser()

String getRandomFolderName() {
    String folderName = 'Folder_' + getTimestamp()

    return folderName
}

String getTimestamp() {
    Date todaysDate = new Date()

    String formattedDate = todaysDate.format('dd_MMM_yyyy_hh_mm_ss')

    return formattedDate
}

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

String getRandomFileName() {
    String documentname = 'pdf_file_' + getTimestamp()

    return documentname
}

String generateRandomString(int length) {
    String characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'

    StringBuilder randomString = new StringBuilder()

    Random random = new Random()

    for (int i = 0; i < length; i++) {
        randomString.append(characters.charAt(random.nextInt(characters.length())))
    }
    
    return randomString.toString().toLowerCase()
}

String generateRandomEmail() {
    return generateRandomString(8) + '@qa-automated-webtest.com'
}

String generateRandomPhoneNumber() {
    Random random = new Random()

    return String.format('(%03d) %03d-%04d', random.nextInt(1000), random.nextInt(1000), random.nextInt(10000))
}

