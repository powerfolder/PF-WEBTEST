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
import static helpers.Helper.getRandomFolderName

// Follows the same create-folder -> select -> delete flow as
// Test Cases/Folders/TF3_VerifyDeleteFolder, but stops to scan the delete
// confirmation dialog while it is open, before confirming.
WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.STOP_ON_FAILURE)

String folderName = 'TAR39_' + getRandomFolderName()

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createFolder'))

WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)

WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.click(findTestObject('Object Repository/Folders/Page_Folders - PowerFolder/lang_Folders'))

WebUI.setText(findTestObject('Folders/inputSearch'), folderName)

TestObject folderRow = new TestObject('folderRow')
folderRow.addProperty('xpath', ConditionType.EQUALS, "//*[contains(@data-search-keys, '" + folderName + "')]/td[1]/span")

WebUI.waitForElementVisible(folderRow, 10)

WebUI.waitForElementClickable(folderRow, 10)

WebUI.click(folderRow)

WebUI.click(findTestObject('Folders/buttonDelete'))

WebUI.delay(3)

// Scans the delete confirmation dialog while it is open.
CustomKeywords.'accessibility.AccessibilityKeywords.checkAccessibility'()

WebUI.closeBrowser()
