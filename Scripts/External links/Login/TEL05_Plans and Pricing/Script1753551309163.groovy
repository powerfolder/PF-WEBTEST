import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint

WebUI.openBrowser(GlobalVariable.URL)

WebUI.maximizeWindow()

WebUI.verifyElementClickable(findTestObject('External links/Page_Login - PowerFolder/Plans and Pricing'))

WebUI.delay(2)

WebUI.click(findTestObject('External links/Page_Login - PowerFolder/Plans and Pricing'))

WebUI.switchToWindowIndex(1)

WebUI.delay(2)

String currentUrl = WebUI.getUrl()

WebUI.comment('L\'URL actuelle est: ' + currentUrl)

String expectedUrl = 'https://mimas.powerfolder.net/pricing'

boolean isCorrectUrl = currentUrl.equals(expectedUrl)

WebUI.verifyEqual(isCorrectUrl, true)

WebUI.closeWindowIndex(1)

WebUI.switchToWindowIndex(0)

WebUI.delay(2)

WebUI.closeBrowser()

