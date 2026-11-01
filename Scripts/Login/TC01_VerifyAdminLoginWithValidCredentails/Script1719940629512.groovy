import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import credentials.CredentialsManager as CredentialsManager
import login.LoginHelper as LoginHelper
import helpers.WindowTitle as WindowTitle

WebUI.openBrowser(GlobalVariable.URL)

WebUI.maximizeWindow()

WindowTitle.verify('Login')

LoginHelper.enterCredentials(CredentialsManager.getAdminUsername(), CredentialsManager.getAdminPassword())

WebUI.click(findTestObject('Login/loginSubmit'))

WindowTitle.verify('Dashboard')

WebUI.closeBrowser()

