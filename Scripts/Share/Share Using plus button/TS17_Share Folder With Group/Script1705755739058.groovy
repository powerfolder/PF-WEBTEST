import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static helpers.Helper.getRandomFolderName
import static helpers.Helper.findShareButton
import static helpers.Helper.getMembersCount
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

WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.OPTIONAL)

String folderName = getRandomFolderName()

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))

WebUI.verifyEqual(WebUI.getText(findTestObject('lang/getCreateText')), 'Create', FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyEqual(WebUI.getText(findTestObject('lang/getFolderNameLabelText')), 'Create a new Folder',  FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Folders/inputFolderName'),folderName)
WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.click(findTestObject('LeftNavigationIcons/groups'))

WebUI.click(findTestObject('Groups/createGroup'))

WebUI.setText(findTestObject('Groups/inputGroupName'), "Group_${ -> folderName }")

WebUI.click(findTestObject('Groups/buttonSave'))

WebUI.refresh()

Thread.sleep(7000)
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

assert WebUI.getWindowTitle().equals('Folders - PowerFolder')

WebElement btn = findShareButton(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

String Group = "Group_${ -> folderName }"

WebUI.setText(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/inputEmail_Share'), Group)

WebUI.click(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/tag'))

WebUI.click(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/buttonAddEmail'))

WebUI.delay(5)

WebUI.verifyElementText(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/td_Group'), Group)

WebUI.closeBrowser()
