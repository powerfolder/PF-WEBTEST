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
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement

WebUI.callTestCase(findTestCase('Folders/Should Go to Folderstable'), [:], FailureHandling.OPTIONAL)

String folderName = org.apache.commons.lang.RandomStringUtils.random(9, true, true)

WebUI.click(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/span_Paste_pica-glyph glyphicons glyphicons_ca92f0'))

WebUI.click(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/a_Create Folder                            _852e7b'))

WebUI.setText(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/input_Create a new Folder_pencil'), folderName)

WebUI.sendKeys(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/input_Create a new Folder_pencil'), Keys.chord(
        Keys.ENTER))

WebUI.click(findTestObject('Object Repository/Folders/Page_Dashboard - PowerFolder/lang_Groups'))

WebUI.click(findTestObject('Object Repository/Share/Page_Groups - PowerFolder/span_Log out_pica-icon pica-glyph glyphicon_0824b5'))

WebUI.setText(findTestObject('Object Repository/Share/Page_Groups - PowerFolder/input_Organizations_pica_group_name'), "Group_${ -> folderName }")

WebUI.click(findTestObject('Object Repository/Share/Page_Groups - PowerFolder/lang_Save'))

WebUI.click(findTestObject('Object Repository/Folders/Page_Folders - PowerFolder/lang_Folders'))

assert WebUI.getWindowTitle().equals('Folders - PowerFolder')

WebElement btn = findShareButton(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

String Group = "Group_${ -> folderName }"

WebUI.setText(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/input_Invite users and groups or create a l_797df6'), Group)

WebUI.click(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/tag'))

WebUI.click(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/button_Can administrate_pica-taginput-butto_3eb2fe'))


WebUI.verifyElementText(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/td_Group'), Group)

WebUI.closeBrowser()



WebElement findShareButton(String fileName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath("//table[@id='files_files_table']/tbody/tr/td[2]/a[contains(text(),'$fileName')]/../../td[6]/a"))
}


