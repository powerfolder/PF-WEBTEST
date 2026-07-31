import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
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

// Follows the same create-folder -> upload -> share -> create-link uploads a generic .bin file
// navigates to the public getlink page. 
//Then scans the actual public file-link page that an external recipient sees
// when opening the shared link.
WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.STOP_ON_FAILURE)

String folderName = 'TAR35_' + getRandomFolderName()

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createFolder'))

WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)

WebUI.click(findTestObject('Folders/buttonOK'))

String fileName = 'TAR35_file_' + getRandomFolderName()

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

TestObject closeBtn = new TestObject('closeBtn')
closeBtn.addProperty('xpath', ConditionType.EQUALS, "//button[@id='upload_stop_button']")

WebUI.waitForElementClickable(closeBtn, 10)

WebUI.click(closeBtn)

WebUI.delay(2)

WebElement btn = findShareButton(fileName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.click(findTestObject('Folders/shareLink'))

WebUI.click(findTestObject('Folders/button_SaveSettings'))

WebUI.click(findTestObject('LinksTable/close links config button'))

WebUI.click(findTestObject('Links/Page_Dashboard - PowerFolder/lang_Links'))

WebElement btn1 = findShareButton(fileName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

// The share icon in the Links table opens the public getlink.vm page in a new window.
WebUI.switchToWindowIndex(1)

WebUI.delay(3)

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

WebElement findShareButton(String name) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + name) + '\')]/td[7]/a/span'))
}
