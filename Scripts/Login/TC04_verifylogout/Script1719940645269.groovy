import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import helpers.WindowTitle as WindowTitle




WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Login/profileIcon'))

WebUI.click(findTestObject('/Login/logout'))
WindowTitle.verify('Login')

WebUI.closeBrowser()