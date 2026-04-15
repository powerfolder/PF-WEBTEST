import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.driver.DriverFactory

import org.apache.commons.lang3.RandomStringUtils
import java.io.File

WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.STOP_ON_FAILURE)

assert WebUI.getWindowTitle().equals('Folders - PowerFolder')

String folderName = "TF9_" + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))
WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)
WebUI.click(findTestObject('Folders/buttonOK'))

String sub1 = "TF9_subfolder_" + RandomStringUtils.randomAlphanumeric(6)

createSubFolder(sub1)

WebUI.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))
WebUI.executeJavaScript('arguments[0].click()', [findFolder(folderName)])

String sub2 = "TF9_subfolder2_" + RandomStringUtils.randomAlphanumeric(6)

createSubFolder(sub2)

WebUI.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))
WebUI.executeJavaScript('arguments[0].click()', [findFolder(folderName)])

TestObject subFolderObj = new TestObject()
subFolderObj.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//*[contains(@data-search-keys, '" + sub1 + "')]/td[1]/span"
)

WebUI.waitForElementVisible(subFolderObj, 10)
WebUI.click(subFolderObj)

// Select all
WebUI.click(findTestObject('Folders/Page_Folders - PowerFolder/select_all_checkbox'))

WebUI.click(findTestObject('Folders/downloadLink'))

String downloadPath = System.getProperty('user.home') + '/Downloads/'

List<String> expectedFiles = [
    sub1 + '.zip',
    sub2 + '.zip'
]

boolean allDownloaded = waitForMultipleFiles(downloadPath, expectedFiles, 300)

WebUI.verifyEqual(allDownloaded, true)

WebUI.closeBrowser()

WebElement findFolder(String folderName) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//td[2]/span/a[contains(text(),'" + folderName + "')]"))
}

void createSubFolder(String name) {
    WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
    WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))

    WebUI.setText(
        findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'),
        name
    )

    WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

    TestObject obj = new TestObject()
    obj.addProperty("xpath", ConditionType.EQUALS, "//span[text()='" + name + "']")

    WebUI.verifyElementPresent(obj, 10, FailureHandling.OPTIONAL)
}

boolean waitForMultipleFiles(String path, List<String> fileNames, int timeoutSeconds) {

    Map<String, Boolean> foundFiles = [:]

    fileNames.each { foundFiles[it] = false }

    int waited = 0

    while (waited < timeoutSeconds) {

        fileNames.each { fileName ->
            File file = new File(path, fileName)
            File tempFile = new File(path, fileName + '.crdownload')

            if (file.exists() && !tempFile.exists()) {
                foundFiles[fileName] = true
            }
        }

        if (foundFiles.values().every { it == true }) {
            println("✅ All files downloaded: " + foundFiles)

            // optional cleanup
            fileNames.each { new File(path, it).delete() }

            return true
        }

        println("⏳ Waiting... " + foundFiles)

        Thread.sleep(1000)
        waited++
    }

    println("❌ Missing files: " + foundFiles.findAll { !it.value })
    return false
}