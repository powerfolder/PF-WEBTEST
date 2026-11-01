import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import helpers.WindowTitle as WindowTitle

WebUI.openBrowser(GlobalVariable.URL)

WindowTitle.verify('Login')

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.closeBrowser()

