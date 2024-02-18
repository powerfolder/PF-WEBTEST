import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable



WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl(GlobalVariable.URL)
WebUI.verifyEqual(WebUI.getWindowTitle(), 'Login - PowerFolder')
WebUI.verifyElementClickable(findTestObject('Login/registerNewAccountLink'))
WebUI.setText(findTestObject('Login/inputEmail'), 'username')
WebUI.setText(findTestObject('Login/inputPassword'), 'password')
WebUI.click(findTestObject('Login/loginSubmit'))
WebUI.verifyEqual(WebUI.getText(findTestObject('Login/getLoginErrorMessage')), 'Unknown Email or wrong password')
WebUI.verifyNotEqual(WebUI.getWindowTitle(), 'Dashboard - PowerFolder')
WebUI.closeBrowser()
