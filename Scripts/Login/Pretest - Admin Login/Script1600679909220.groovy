import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.openBrowser(GlobalVariable.URL)

WebUI.maximizeWindow()

WebUI.setText(findTestObject('Login/inputEmail'), GlobalVariable.Username)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Password)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.verifyEqual(WebUI.getWindowTitle(), 'Dashboard - PowerFolder')

