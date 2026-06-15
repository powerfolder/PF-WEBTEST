import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable
import helpers.Helper

import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

import java.util.Arrays
import java.util.Comparator
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.Path

String topFolder = "Top_lvl_" + Helper.getRandomFolderName()

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebUI.click(findTestObject('Object Repository/Folders/createFolderIcon'))

WebUI.click(findTestObject('Object Repository/Folders/createFolder'))

WebUI.setText(findTestObject('Object Repository/Folders/inputFolderName'), topFolder)

WebUI.click(findTestObject('Object Repository/Folders/buttonOK'))

WebUI.delay(3)

WebDriver driver = DriverFactory.getWebDriver()

// ================== OPEN CREATED FOLDER ==================

WebElement folderLink = null

for (int i = 0; i < 20; i++) {

	List<WebElement> folders = driver.findElements(
		By.xpath("//a[contains(normalize-space(.),'" + topFolder + "')]")
	)

	if (folders.size() > 0) {
		folderLink = folders.get(0)
		break
	}

	WebUI.delay(1)
	WebUI.refresh()
}

assert folderLink != null : "Folder not found : " + topFolder

WebUI.executeJavaScript(
	"arguments[0].click();",
	Arrays.asList(folderLink)
)

WebUI.delay(2)

// ================== CREATE MULTIPLE LOCAL FOLDERS ==================

List<String> localFolderNames = []
List<String> localFolderPaths = []

for (int i = 1; i <= 3; i++) {

	String localFolderName = Helper.getRandomFolderName()

	String localFolderPath = createLocalFolderOnDesktop(localFolderName)

	localFolderNames.add(localFolderName)
	localFolderPaths.add(localFolderPath)
}

// ================== DRAG & DROP MULTIPLE FOLDERS ==================

Helper.dragAndDropFoldersNative(
	'#pica_content',
	localFolderPaths
)

WebUI.delay(5)

if (WebUI.waitForElementVisible(
	findTestObject('Drag and drop/Close_button'),
	10,
	FailureHandling.OPTIONAL
)) {
	WebUI.click(findTestObject('Drag and drop/Close_button'))
}

// ================== VERIFY FOLDERS UPLOADED ==================

for (String folderName : localFolderNames) {

	boolean uploaded = false

	for (int i = 0; i < 15; i++) {

		uploaded = driver.findElements(
			By.xpath("//*[contains(normalize-space(.),'" + folderName + "')]")
		).size() > 0

		if (uploaded) {
			break
		}

		WebUI.delay(1)
		WebUI.refresh()
	}

	WebUI.verifyEqual(uploaded, true)
}

// ================== DELETE LOCAL FOLDERS ==================

for (String folderPath : localFolderPaths) {
	deleteLocalFolder(folderPath)
}

WebUI.closeBrowser()

// ==========================================================
// FUNCTIONS
// ==========================================================

String createLocalFolderOnDesktop(String folderName) {

	Path desktop = Paths.get(
		System.getProperty('user.home'),
		'Desktop'
	)

	if (!Files.exists(desktop)) {
		Files.createDirectories(desktop)
	}

	Path folder = desktop.resolve(folderName)

	if (!Files.exists(folder)) {
		Files.createDirectories(folder)
	}

	Path fileInside = folder.resolve('test.txt')

	Files.write(
		fileInside,
		'folder upload test'.getBytes()
	)

	return folder.toString()
}

void deleteLocalFolder(String folderPath) {

	Path path = Paths.get(folderPath)

	if (Files.exists(path)) {

		Files.walk(path)
			.sorted(Comparator.reverseOrder())
			.forEach { p ->
				Files.deleteIfExists(p)
			}
	}

	println('LOCAL FOLDER DELETED : ' + folderPath)
}