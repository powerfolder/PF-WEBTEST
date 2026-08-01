import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import java.nio.file.Files as Files
import java.nio.file.Path as Path
import java.nio.file.Paths as Paths
import static helpers.Helper.getRandomFolderName

// Follows the same create-folder -> Upload files flow as
// Test Cases/Folders/TF16_VerifyUploadFileMainFolder, but stops to scan the file-upload
// dialog itself (progress / "Successfully uploaded" state) - distinct from TAR32/TAR11,
// which scan the "create upload form" admin dialog and its public landing page, not this
// direct drag-and-drop-style upload dialog.
WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.STOP_ON_FAILURE)

String folderName = 'TAR54_' + getRandomFolderName()

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createFolder'))

WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)

WebUI.click(findTestObject('Folders/buttonOK'))

String fileName = 'TAR54_file_' + getRandomFolderName()

String filePath = createGenericBinaryFile(fileName)

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.verifyElementPresent(findTestObject('Folders/Page_Folders - PowerFolder/Upload files'), 5)

WebUI.click(findTestObject('Folders/Page_Folders - PowerFolder/Upload files'))

TestObject uploadInput = new TestObject('uploadInput')
uploadInput.addProperty('xpath', ConditionType.EQUALS, "//input[@id='upload_input_files']")

WebUI.waitForElementPresent(uploadInput, 10)

WebUI.uploadFile(uploadInput, filePath)

TestObject successMsg = new TestObject('successMsg')
successMsg.addProperty('xpath', ConditionType.EQUALS, "//*[contains(text(),'Successfully uploaded')]")

WebUI.waitForElementVisible(successMsg, 15)

WebUI.delay(3)

// Scans the upload dialog (progress / success state) while it is still open.
CustomKeywords.'accessibility.AccessibilityKeywords.checkAccessibility'()

deleteGenericBinaryFile(filePath)

WebUI.closeBrowser()

String createGenericBinaryFile(String fileName) {
	Path desktopPath = Paths.get(System.getProperty('user.home'), 'Desktop')

	if (!Files.exists(desktopPath)) {
		Files.createDirectories(desktopPath)
	}

	Path filePath = desktopPath.resolve(fileName + '.bin')

	byte[] randomBytes = new byte[64]
	new Random().nextBytes(randomBytes)

	Files.write(filePath, randomBytes)

	return filePath.toString()
}

void deleteGenericBinaryFile(String filePath) {
	try {
		Files.deleteIfExists(Paths.get(filePath))
	} catch (Exception e) {
		e.printStackTrace()
	}
}
