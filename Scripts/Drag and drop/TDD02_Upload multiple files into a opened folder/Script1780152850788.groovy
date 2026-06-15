import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import helpers.Helper

import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

import java.util.Arrays
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.Path

// ================== LOGIN + CREATE TOP FOLDER ==================

String topFolder = "Top_lvl_" + Helper.getRandomFolderName()

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebUI.click(findTestObject('Object Repository/Folders/createFolderIcon'))

WebUI.click(findTestObject('Object Repository/Folders/createFolder'))

WebUI.setText(findTestObject('Object Repository/Folders/inputFolderName'), topFolder)

WebUI.click(findTestObject('Object Repository/Folders/buttonOK'))

WebUI.delay(3)

// ================== OPEN CREATED FOLDER ==================

WebDriver driver = DriverFactory.getWebDriver()

List<WebElement> folderLinks = driver.findElements(
    By.xpath("//td//a[contains(text(),'" + topFolder + "')]")
)

if (folderLinks.size() > 0) {
    WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(folderLinks.get(0)))
    WebUI.delay(2)
}

// ================== CREATE MULTIPLE LOCAL FILES ==================

List<String> fileNames = []
List<String> filePaths = []

for (int i = 1; i <= 5; i++) {
    String fileName = Helper.getRandomFileName() + ".txt"
    String filePath = createLocalFileOnDesktop(fileName)

    fileNames.add(fileName)
    filePaths.add(filePath)
}

// ================== DRAG AND DROP MULTIPLE FILES ==================

Helper.dragAndDropFilesNative('#pica_content', filePaths)

WebUI.delay(5)

WebUI.click(findTestObject('Drag and drop/Close_button'))

// ================== VERIFY UPLOADED FILES ==================

for (String fileName : fileNames) {
    boolean uploaded = driver.findElements(
        By.xpath("//table[@id='files_files_table']//*[contains(text(),'" + fileName + "')]")
    ).size() > 0

    WebUI.verifyEqual(uploaded, true)
}

// ================== DELETE LOCAL FILES ==================

for (String filePath : filePaths) {
    Files.deleteIfExists(Paths.get(filePath))
    println('LOCAL FILE DELETED : ' + filePath)
}

WebUI.closeBrowser()

// ================== FUNCTIONS ==================

String createLocalFileOnDesktop(String fileName) {
    Path desktop = Paths.get(System.getProperty('user.home'), 'Desktop')

    if (!Files.exists(desktop)) {
        Files.createDirectories(desktop)
    }

    Path file = desktop.resolve(fileName)

    Files.write(file, 'file drag test'.getBytes())

    return file.toString()
}