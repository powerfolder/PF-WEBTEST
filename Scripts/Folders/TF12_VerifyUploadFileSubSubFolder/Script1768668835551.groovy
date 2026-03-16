import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import org.apache.poi.xwpf.usermodel.XWPFDocument as XWPFDocument
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement

import java.util.Arrays as Arrays
import java.io.FileOutputStream as FileOutputStream
import java.nio.file.Files as Files
import java.nio.file.Paths as Paths
import java.nio.file.Path as Path

WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.OPTIONAL)

// Vérifie si le titre de la page correspond à "Folders - PowerFolder"
assert WebUI.getWindowTitle().equals('Folders - PowerFolder')

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))

String folderName = getRandomFolderName()

WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)
WebUI.click(findTestObject('Folders/buttonOK'))

TestObject dynamicObject = new TestObject()
dynamicObject.addProperty('xpath', ConditionType.EQUALS, "//span[text()='" + folderName + "']")

boolean exists = WebUI.verifyElementPresent(dynamicObject, 10, FailureHandling.OPTIONAL)

WebUI.verifyElementPresent(findTestObject('Folders/Page_Folders - PowerFolder/New subdirectory'), 5)
WebUI.click(findTestObject('Folders/Page_Folders - PowerFolder/New subdirectory'))

String subFolderName = "Sub_" + getRandomFolderName()
WebUI.setText(findTestObject('Folders/inputFolderName'), subFolderName)
WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.click(findTestObject('Folders/Page_Folders - PowerFolder/New subdirectory'))

String subSubFolderName = "Sub_Sub_" + getRandomFolderName()
WebUI.setText(findTestObject('Folders/inputFolderName'), subSubFolderName)
WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.verifyElementPresent(findTestObject('Folders/Page_Folders - PowerFolder/Upload files'), 5)
WebUI.click(findTestObject('Folders/Page_Folders - PowerFolder/Upload files'))

// Création du fichier Word vide sur le bureau
String wordFileName = 'word_file_' + RandomStringUtils.randomNumeric(4)
String wordFilePath = createEmptyWordFileOnDesktop(wordFileName)

// Upload direct dans l'input file
TestObject uploadInput = new TestObject('uploadInput')
uploadInput.addProperty('xpath', ConditionType.EQUALS, "//input[@id='upload_input_files']")

WebUI.waitForElementPresent(uploadInput, 10)
WebUI.uploadFile(uploadInput, wordFilePath)

// Attendre que l’upload soit visible comme réussi
TestObject successMsg = new TestObject('successMsg')
successMsg.addProperty('xpath', ConditionType.EQUALS, "//*[contains(text(),'Successfully uploaded')]")

WebUI.waitForElementVisible(successMsg, 15)

// Fermer la popup avec le vrai bouton Close
TestObject closeBtn = new TestObject('closeBtn')
closeBtn.addProperty('xpath', ConditionType.EQUALS, "//button[@id='upload_stop_button']")

WebUI.waitForElementClickable(closeBtn, 10)
WebUI.click(closeBtn)

WebUI.delay(2)

// Vérification de la présence du document
def btn = findDoc(wordFileName)
assert btn != null

// Cliquer sur le document si besoin
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.delay(3)

// Supprimer le fichier Word créé sur le bureau
deleteWordFile(wordFilePath)

WebUI.closeBrowser()

String createEmptyWordFileOnDesktop(String fileName) {
    def desktopWordPath = Paths.get(System.getProperty('user.home'), 'Desktop')

    if (!Files.exists(desktopWordPath)) {
        Files.createDirectories(desktopWordPath)
    }

    def wordFilePath = desktopWordPath.resolve(fileName + '.docx')

    XWPFDocument document = new XWPFDocument()
    FileOutputStream out = new FileOutputStream(wordFilePath.toFile())

    document.write(out)
    out.close()
    document.close()

    return wordFilePath.toString()
}

void deleteWordFile(String wordFilePath) {
    try {
        Path path = Paths.get(wordFilePath)
        boolean deleted = Files.deleteIfExists(path)

        if (deleted) {
            println('Le fichier a été supprimé avec succès.')
        } else {
            println("Le fichier n'a pas été trouvé ou n'a pas pu être supprimé.")
        }
    } catch (Exception e) {
        e.printStackTrace()
    }
}

@Keyword
WebElement findDoc(String wordFileName) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//*[contains(@data-search-keys, '" + wordFileName + "')]/td[1]/span"))
}

String getRandomFolderName() {
    return 'TF' + getTimestamp()
}

String getTimestamp() {
    Date todaysDate = new Date()
    return todaysDate.format('_dd_MM_yyyy_hh_mm_ss')
}