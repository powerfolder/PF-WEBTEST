import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.driver.DriverFactory

import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.io.File
import java.util.Date

WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.STOP_ON_FAILURE)

String folderName = getRandomFolderName()

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))

WebUI.verifyElementPresent(findTestObject('lang/getCreateText'), 30)
WebUI.verifyElementPresent(findTestObject('lang/getFolderNameLabelText'), 30)

WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)
WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.click(findTestObject('Object Repository/Folders/Page_Folders - PowerFolder/lang_Folders'))

WebUI.setText(findTestObject('Accounts/inputAccountSearch'), folderName)
WebUI.sendKeys(findTestObject('Accounts/inputAccountSearch'), Keys.chord(Keys.ENTER))

assert WebUI.getWindowTitle().equals('Folders - PowerFolder')

WebElement btn = findShareButton(folderName)
WebUI.executeJavaScript('arguments[0].click()', [btn])

WebElement buttonCreateLink = com.kms.katalon.core.webui.common.WebUiCommonHelper
    .findWebElement(findTestObject('Links/buttonCreateLink'), 30)

WebUI.executeJavaScript('arguments[0].click()', [buttonCreateLink])

WebUI.click(findTestObject('Object Repository/Page_Folders - PowerFolder/button_Can read'))

String password = 'Alexa@131190'
WebUI.setText(findTestObject('Page_Link - PowerFolder/lang_Password required'), password)

WebUI.click(findTestObject('SettingsPopUp/buttonSave'))

WebUI.doubleClick(findTestObject('Page_Folders - PowerFolder/icon-copy'))

String url = Toolkit.getDefaultToolkit()
    .getSystemClipboard()
    .getContents(null)
    .getTransferData(DataFlavor.stringFlavor)

WebUI.navigateToUrl(url)

WebUI.delay(2)

WebUI.setText(findTestObject('Page_Link - PowerFolder/inputPassword'), password)
WebUI.click(findTestObject('Page_Link - PowerFolder/buttonOK'))

assert WebUI.getWindowTitle().equals('Link - PowerFolder')

WebUI.click(findTestObject('Links/Page_Link - PowerFolder/folder_link_download_button'))

String downloadPath = System.getProperty('user.home') + '/Downloads/'

boolean downloaded = waitForZip(downloadPath, folderName, 300)

WebUI.verifyEqual(downloaded, true)

WebUI.closeBrowser()

boolean waitForZip(String path, String folderName, int timeoutSeconds) {

    long start = System.currentTimeMillis()

    while ((System.currentTimeMillis() - start) < timeoutSeconds * 1000) {

        File dir = new File(path)
        File[] files = dir.listFiles()

        if (files != null) {

            for (File file : files) {

                String name = file.getName()

                // unfinished download ignorieren
                if (name.endsWith('.crdownload')) {
                    continue
                }

                // robust gegen (1), (2), etc.
                if (name.startsWith(folderName) && name.endsWith('.zip')) {

                    println("✅ Found ZIP: " + name)

                    // optional cleanup
                    file.delete()

                    return true
                }
            }
        }

        Thread.sleep(2000)
    }

    println("❌ ZIP file not found")
    return false
}

String getRandomFolderName() {
    return 'Folder' + getTimestamp()
}

WebElement findShareButton(String fileName) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//*[contains(@data-search-keys, '" + fileName + "')]/td[7]/a/span"))
}

String getTimestamp() {
    return new Date().format('ddMMMyyyyhhmmss')
}