import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject


WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.OPTIONAL)

String folderName = getRandomFolderName()

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createFolder'))

WebUI.verifyEqual(WebUI.getText(findTestObject('lang/getCreateText')), 'Create', FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyEqual(WebUI.getText(findTestObject('lang/getFolderNameLabelText')), 'Create a new Folder', FailureHandling.CONTINUE_ON_FAILURE)

WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)

WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)

WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.setText(findTestObject('Folders/inputSearch'), folderName)


TestObject dynamicFolder = new TestObject('dynamicFolder')
dynamicFolder.addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//*[contains(@data-search-keys, '" + folderName + "')]/td[1]/span"
)

WebUI.waitForElementVisible(dynamicFolder, 10)

WebUI.waitForElementClickable(dynamicFolder, 10)

WebUI.click(dynamicFolder)

WebUI.click(findTestObject('Folders/buttonDelete'))

String deleteAlertText = WebUI.getText(findTestObject('lang/getDeleteAlertText'))

String expectedText = ('Do you really want to delete ' + folderName) + '?'

WebUI.verifyEqual(deleteAlertText, expectedText)

WebUI.click(findTestObject('Folders/yesButton_Delete'))

WebUI.closeBrowser()

String getRandomFolderName() {
    String folderName = 'Folder' + getTimestamp()

    return folderName
}

String getTimestamp() {
    Date todaysDate = new Date()

    String formattedDate = todaysDate.format('dd_MMM_yyyy_hh_mm_ss')

    return formattedDate
}

