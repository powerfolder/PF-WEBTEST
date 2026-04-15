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

String folderName = "TF8_" + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))
WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)
WebUI.click(findTestObject('Folders/buttonOK'))

String subFolderName = "TF8_subfolder_" + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))

WebUI.setText(
    findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'),
    subFolderName
)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebElement parentFolder = findFolder(folderName)
WebUI.executeJavaScript('arguments[0].click()', [parentFolder])

TestObject subFolderObj = new TestObject()
subFolderObj.addProperty(
    "xpath",
    ConditionType.EQUALS,
    "//*[contains(@data-search-keys, '" + subFolderName + "')]/td[1]/span"
)

WebUI.waitForElementVisible(subFolderObj, 10)
WebUI.click(subFolderObj)

WebUI.click(findTestObject('Folders/downloadLink'))

String downloadPath = System.getProperty('user.home') + '/Downloads/'

boolean downloaded = waitForFile(downloadPath, subFolderName + '.zip', 300)

WebUI.verifyEqual(downloaded, true)
WebUI.closeBrowser()

WebElement findFolder(String folderName) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//td[2]/span/a[contains(text(),'" + folderName + "')]"))
}

boolean waitForFile(String path, String fileName, int timeoutSeconds) {
    File file = new File(path, fileName)
    File tempFile = new File(path, fileName + '.crdownload')

    int waited = 0

    while (waited < timeoutSeconds) {
        if (file.exists() && !tempFile.exists()) {
            println("✅ File downloaded: " + file.absolutePath)

            // optional cleanup
            file.delete()

            return true
        }

        println("⏳ Waiting for download... (" + waited + "s)")
        Thread.sleep(1000)
        waited++
    }

    println("❌ File NOT downloaded: " + file.absolutePath)
    return false
}