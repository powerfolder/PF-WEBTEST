import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.text.SimpleDateFormat

import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import onlyoffice.OOEditor
import onlyoffice.OnlyOffice

String ooUrl = GlobalVariable.ooUrl
String powerFolderUrl = GlobalVariable.URL
String fileType = 'docx'
String baseName = 'QA-Document-HTTPS-' + new SimpleDateFormat('HHmmss-SSS').format(new Date())
String fileName = baseName + '.' + fileType
String exchangedLine = 'HTTPS test line'

try {
	assert powerFolderUrl.startsWith('https://')
	assert ooUrl.startsWith('https://')

	WebUI.callTestCase(findTestCase('File/Pre_test/Create_folder'), [:], FailureHandling.STOP_ON_FAILURE)
	String folderId = OnlyOffice.currentFolderId()

	WebUI.navigateToUrl(ooUrl + '/welcome/')
	WebUI.verifyTextPresent('ONLYOFFICE', false, FailureHandling.STOP_ON_FAILURE)
	assert WebUI.getUrl().startsWith('https://')

	OnlyOffice.openFolder(folderId)
	WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
	WebUI.click(findTestObject('ONLY OFFICE/a_create_file_by_type', [('type') : fileType]))
	WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), baseName)
	WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

	Map created = OnlyOffice.waitForFile(folderId, fileName, 30)
	WebUI.switchToWindowIndex(1)
	OOEditor.waitReady(fileType)
	assert OOEditor.editorMode() in ['USER_EDIT', 'COLLABORATIVE_EDIT']
	String version = OOEditor.version()
	String editorUrl = WebUI.getAttribute(findTestObject('ONLY OFFICE/iframe_frameEditor'), 'src')
	assert editorUrl.startsWith(ooUrl)
	assert version?.startsWith(GlobalVariable.expectedOOVersion as String)

	WebUI.switchToFrame(findTestObject('ONLY OFFICE/iframe_frameEditor'), 10)
	OOEditor.typeLine(fileType, exchangedLine, 1)
	assert OOEditor.waitSavedStatus(GlobalVariable.autosaveWaitSeconds as int)
	WebUI.closeWindowIndex(1)
	WebUI.switchToWindowIndex(0)
	OnlyOffice.waitForChange(folderId, fileName, created, GlobalVariable.saveCallbackTimeout as int)
	assert OnlyOffice.readFileText(folderId, fileName).contains(exchangedLine)
} finally {
	WebUI.closeBrowser()
}
