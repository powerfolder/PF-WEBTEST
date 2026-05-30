import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import internal.GlobalVariable as GlobalVariable
import helpers.Helper as Helper
import java.nio.file.Files as Files
import java.nio.file.Paths as Paths
import java.nio.file.Path as Path
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import java.util.Arrays as Arrays
import file.FileFinder

String topFolder = "Top_lvl" + Helper.getRandomFolderName()

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebUI.click(findTestObject('Object Repository/Folders/createFolderIcon'))

WebUI.click(findTestObject('Object Repository/Folders/createFolder'))

WebUI.setText(findTestObject('Object Repository/Folders/inputFolderName'), topFolder)

WebUI.click(findTestObject('Object Repository/Folders/buttonOK'))

WebUI.delay(3)

WebDriver driver = DriverFactory.getWebDriver()

List<WebElement> folderLinks = driver.findElements(By.xpath(('//td//a[contains(text(),\'' + topFolder) + 
        '\')]'))

if (folderLinks.size() > 0) {
    WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(folderLinks.get(0)))

    WebUI.delay(2)
}

String fileName = Helper.getRandomFileName()

String filePath = createTextFileOnDesktop(fileName)

Helper.dragAndDropFileNative('#pica_content', filePath)

WebUI.delay(5)

WebUI.click(findTestObject('Drag and drop/Close_button'))

boolean uploaded = driver.findElements(By.xpath(('//table[@id=\'files_files_table\']//*[contains(text(),\'' + fileName) + 
        '\')]')).size() > 0

WebUI.verifyEqual(uploaded, true)

deleteLocalFile(filePath)

/*
def btn = FileFinder.findDoc(fileName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))
assert fileName != null
*/

WebUI.closeBrowser()

String createTextFileOnDesktop(String fileName) {
    Path desktop = Paths.get(System.getProperty('user.home'), 'Desktop')

    if (!(Files.exists(desktop))) {
        Files.createDirectories(desktop)
    }
    
    Path file = desktop.resolve(fileName + '.txt')

    Files.write(file, 'drag test'.getBytes())

    return file.toString()
}

void deleteLocalFile(String filePath) {
    Files.deleteIfExists(Paths.get(filePath))

    println('LOCAL FILE DELETED : ' + filePath)
}

