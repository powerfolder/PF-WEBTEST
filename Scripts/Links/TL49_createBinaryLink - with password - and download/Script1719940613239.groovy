import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling
import internal.GlobalVariable as GlobalVariable

import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.io.File

WebUI.callTestCase(findTestCase('Links/pre_test/Binary file'), [:], FailureHandling.STOP_ON_FAILURE)

String binaryName = GlobalVariable.binaryName
println("Binary Name: " + binaryName)

WebUI.verifyElementClickable(
    findTestObject('Object Repository/Links/Page_Folders - PowerFolder/label_Can read'),
    FailureHandling.CONTINUE_ON_FAILURE
)

String password = 'Alexa@131190'
WebUI.setText(findTestObject('Page_Link - PowerFolder/lang_Password required'), password)

WebUI.click(findTestObject('Object Repository/Folders/button_SaveSettings'))

WebUI.doubleClick(findTestObject('Object Repository/Page_Folders - PowerFolder/icon-copy'))

String url = Toolkit.getDefaultToolkit()
    .getSystemClipboard()
    .getContents(null)
    .getTransferData(DataFlavor.stringFlavor)

WebUI.comment('Copied URL: ' + url)

assert url != null && url.startsWith('https')

WebUI.executeJavaScript('window.open(arguments[0], "_blank");', [url])
WebUI.switchToWindowIndex(1)

assert WebUI.getWindowTitle().equals('Link - PowerFolder')

WebUI.verifyElementText(
    findTestObject('Page_Link - PowerFolder/getPasswordBodyText'),
    'This link is password protected'
)

WebUI.setText(findTestObject('Page_Link - PowerFolder/inputPassword'), password)
WebUI.click(findTestObject('Page_Link - PowerFolder/buttonOK'))

WebUI.click(findTestObject('Links/Page_Link - PowerFolder/folder_link_download_button'))

String downloadPath = System.getProperty('user.home') + '/Downloads/'

boolean downloaded = waitForFile(downloadPath, binaryName + '.bin', 300)

WebUI.verifyEqual(downloaded, true)

WebUI.closeBrowser()

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