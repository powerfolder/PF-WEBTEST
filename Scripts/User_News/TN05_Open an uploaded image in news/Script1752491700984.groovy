import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import internal.GlobalVariable as GlobalVariable
import java.io.File as File
import java.util.Date as Date
import java.util.Random as Random
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
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import org.openqa.selenium.Keys as Keys

WebUI.callTestCase(findTestCase('User_News/Pre_test/Create_user'), [:], FailureHandling.STOP_ON_FAILURE)

println(GlobalVariable.userEmail)

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

WebUI.setText(findTestObject('Login/inputEmail'), GlobalVariable.userEmail)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(3)

String folderName = getRandomFolderName()

WebUI.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebUI.click(findTestObject('Object Repository/Folders/createFolderIcon'))

WebUI.click(findTestObject('Object Repository/Folders/createFolder'))

WebUI.setText(findTestObject('Object Repository/Folders/inputFolderName'), folderName)

WebUI.click(findTestObject('Object Repository/Folders/buttonOK'))

WebUI.delay(2)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))

WebUI.click(findTestObject('file_objects/upload/Page_Folders - PowerFolder/Upload file'))

String projDir = RunConfiguration.getProjectDir()

String filePath = projDir + '/Images/user.png'

WebUI.uploadFile(findTestObject('Accounts/AddFileButton'), filePath)

WebUI.click(findTestObject('News_User/Page_Folders - PowerFolder/button_Close_upload_file'))

WebUI.click(findTestObject('News_User/Page_News - PowerFolder/News'))

WebUI.mouseOver(findTestObject('News_User/Page_News - PowerFolder/file_wpath'))

WebUI.click(findTestObject('News_User/Page_News - PowerFolder/file_wpath'))

WebUI.delay(2)

WebUI.switchToWindowIndex(1)

WebUI.switchToWindowIndex(1)

String title = WebUI.getWindowTitle()

WebUI.verifyEqual(title, 'user.png (1024×1024)')

WebUI.closeBrowser()

String getTimestamp() {
    return new Date().format('dd_MMM_yyyy_hh_mm_ss')
}

String getRandomFolderName() {
    return 'Folder_' + getTimestamp()
}

