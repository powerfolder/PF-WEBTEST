import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable


WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl(GlobalVariable.URL)
//WebUI.verifyEqual(WebUI.getWindowTitle(), 'Login - PowerFolder')
//WebUI.verifyEqual(WebUI.getAttribute(findTestObject('Login/poweredBy'), 'href'), 'https://www.powerfolder.com/')
//WebUI.verifyEqual(WebUI.getAttribute(findTestObject('Login/documentationLink'), 'href'), 'https://wiki.powerfolder.com/')
WebUI.verifyElementClickable(findTestObject('Login/registerNewAccountLink'))
WebUI.setText(findTestObject('Login/inputEmail'), GlobalVariable.Username)
WebUI.click(findTestObject('Login/loginSubmit'))
WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Password)
WebUI.click(findTestObject('Login/loginSubmit'))
WebUI.verifyEqual(WebUI.getWindowTitle(), 'Dashboard - PowerFolder')
