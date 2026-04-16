import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.configuration.RunConfiguration
import internal.GlobalVariable as GlobalVariable

import java.io.File
import java.util.Date
import java.util.Random

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

String downloadPath = System.getProperty('user.home') + '/Downloads/'

String downloadedFile = waitForDownloadedPng(downloadPath, 300)

WebUI.verifyNotEqual(downloadedFile, null)
WebUI.comment("✅ Downloaded file: " + downloadedFile)

WebUI.closeBrowser()

String waitForDownloadedPng(String path, int timeoutSeconds) {

    long start = System.currentTimeMillis()

    while ((System.currentTimeMillis() - start) < timeoutSeconds * 1000) {

        File dir = new File(path)
        File[] files = dir.listFiles()

        if (files != null) {

            println("📂 Checking download folder...")

            for (File file : files) {

                String name = file.getName()

                // unfinished download ignorieren
                if (name.endsWith('.crdownload')) {
                    continue
                }

                if (name.toLowerCase().endsWith('.png')) {

                    println("✅ Found downloaded PNG: " + name)

                    // optional cleanup
                    file.delete()

                    return name
                }
            }
        }

        Thread.sleep(2000)
    }

    println("❌ No PNG file found in download folder")
    return null
}

String getTimestamp() {
    return new Date().format('dd_MMM_yyyy_hh_mm_ss')
}

String getRandomFolderName() {
    return 'Folder_' + getTimestamp()
}