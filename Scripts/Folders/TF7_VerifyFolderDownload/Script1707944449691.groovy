import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import com.kms.katalon.core.webui.driver.DriverFactory

import java.io.File
import java.text.SimpleDateFormat

WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.STOP_ON_FAILURE)

String folderName = generateFolderName()

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))

WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)
WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.click(findTestObject('Object Repository/Folders/Page_Folders - PowerFolder/lang_Folders'))
WebUI.setText(findTestObject('Folders/inputSearch'), folderName)

TestObject folderObject = new TestObject()
folderObject.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//*[contains(@data-search-keys, '" + folderName + "')]/td[1]/span"
)

WebUI.waitForElementVisible(folderObject, 10)

WebUI.click(folderObject)

WebUI.click(findTestObject('Folders/downloadLink'))

String downloadPath = System.getProperty('user.home') + '/Downloads/'

boolean downloaded = waitForFile(downloadPath, folderName + '.zip', 300)

WebUI.verifyEqual(downloaded, true)

WebUI.closeBrowser()

// ==========================
// HELPER METHODS
// ==========================

String generateFolderName() {
	return 'FDTF7_' + new SimpleDateFormat('yyyyMMdd_HHmmss').format(new Date())
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