import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.openBrowser(GlobalVariable.URL)
WebUI.maximizeWindow()
WebUI.setText(findTestObject('Login/inputEmail'), 'username')
WebUI.setText(findTestObject('Login/inputPassword'), 'password')
WebUI.click(findTestObject('Login/loginSubmit'))

String actualErrorMessage = WebUI.getText(findTestObject('Login/getLoginErrorMessage'))
String expectedMessageGerman = 'Benutzername/Email unbekannt oder falsches Passwort'
String expectedMessageEnglish = 'Unknown Email or wrong password'

if (actualErrorMessage == expectedMessageGerman) {
	WebUI.comment('Error message in German verified successfully.')
} else if (actualErrorMessage == expectedMessageEnglish) {
	WebUI.comment('Error message in English verified successfully.')
} else {
	WebUI.verifyFail('The error message does not match any of the expected values. Actual message: ' + actualErrorMessage)
}

WebUI.verifyNotEqual(WebUI.getWindowTitle(), 'Dashboard - PowerFolder')
WebUI.closeBrowser()
