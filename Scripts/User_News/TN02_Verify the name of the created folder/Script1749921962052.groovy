import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

// Call the test case Create Folder

WebUI.callTestCase(findTestCase('User_News/Pre_test/create_user_file'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('News_User/Page_News - PowerFolder/News'))

// STEP 2: Read folder name from UI

String folderNameFromUI = WebUI.getText(findTestObject('News_User/Page_News - PowerFolder/Name_folder_in_news_part'))

println('📁 Folder name from UI: ' + folderNameFromUI)

// STEP 3: Compare with variable
String expectedFolderName = GlobalVariable.folderName

println('📌 Expected folder name: ' + expectedFolderName)

if (folderNameFromUI == expectedFolderName) {
    println('✅ Folder name matches.')
} else {
    println('❌ Folder name does NOT match.')

    assert false
}

WebUI.closeBrowser()


