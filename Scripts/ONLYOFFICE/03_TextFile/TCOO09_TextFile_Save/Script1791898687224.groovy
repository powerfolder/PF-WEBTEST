import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.text.SimpleDateFormat

import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import onlyoffice.OOEditor
import onlyoffice.OnlyOffice

String fileType = 'txt'
String baseName = 'QA-Text-Save-' + new SimpleDateFormat('HHmmss-SSS').format(new Date())
String fileName = baseName + '.' + fileType
String savedLine = 'Save test line 1'
String autosavedLine = 'Autosave test line 2'

try {
	WebUI.callTestCase(findTestCase('File/Pre_test/Create_folder'), [:], FailureHandling.STOP_ON_FAILURE)
	String folderId = OnlyOffice.currentFolderId()

	WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
	WebUI.click(findTestObject('ONLY OFFICE/a_create_file_by_type', [('type') : fileType]))
	WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), baseName)
	WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

	Map created = OnlyOffice.waitForFile(folderId, fileName, 30)
	WebUI.switchToWindowIndex(1)
	OOEditor.waitReady(fileType)
	OOEditor.typeLine(fileType, savedLine, 1)
	OOEditor.save()
	WebUI.closeWindowIndex(1)
	WebUI.switchToWindowIndex(0)
	Map savedOnClose = OnlyOffice.waitForChange(folderId, fileName, created, GlobalVariable.saveCallbackTimeout as int)
	assert OnlyOffice.readFileText(folderId, fileName).contains(savedLine)

	OnlyOffice.openFile(folderId, fileName)
	OOEditor.waitReady(fileType)
	OOEditor.typeLine(fileType, autosavedLine, 2)
	assert OOEditor.waitDocumentSaved(GlobalVariable.autosaveWaitSeconds as int)
	OnlyOffice.openFolder(folderId)
	OnlyOffice.waitForChange(folderId, fileName, savedOnClose, GlobalVariable.saveCallbackTimeout as int)
	String savedText = OnlyOffice.readFileText(folderId, fileName)
	assert savedText.contains(savedLine)
	assert savedText.contains(autosavedLine)

	OnlyOffice.openFile(folderId, fileName)
	OOEditor.waitReady(fileType)
} finally {
	WebUI.closeBrowser()
}
