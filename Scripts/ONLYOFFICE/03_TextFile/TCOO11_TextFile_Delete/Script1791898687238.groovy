import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.text.SimpleDateFormat

import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import onlyoffice.OOEditor
import onlyoffice.OnlyOffice

String fileType = 'txt'
String baseName = 'QA-Text-Delete-' + new SimpleDateFormat('HHmmss-SSS').format(new Date())
String fileName = baseName + '.' + fileType

try {
	WebUI.callTestCase(findTestCase('File/Pre_test/Create_folder'), [:], FailureHandling.STOP_ON_FAILURE)
	String folderId = OnlyOffice.currentFolderId()

	WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
	WebUI.click(findTestObject('ONLY OFFICE/a_create_file_by_type', [('type') : fileType]))
	WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), baseName)
	WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))

	OnlyOffice.waitForFile(folderId, fileName, 30)
	WebUI.switchToWindowIndex(1)
	OOEditor.waitReady(fileType)
	WebUI.closeWindowIndex(1)
	WebUI.switchToWindowIndex(0)

	WebUI.refresh()
	WebUI.click(findTestObject('ONLY OFFICE/a_row_menu_by_name', [('name') : fileName]))
	WebUI.click(findTestObject('ONLY OFFICE/a_row_menu_delete_by_name', [('name') : fileName]))
	WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/button_Yes'))

	OnlyOffice.waitForFileGone(folderId, fileName, 30)
} finally {
	WebUI.closeBrowser()
}
