import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable

try {
	WebUI.openBrowser(GlobalVariable.ooUrl + '/welcome/')
	WebUI.verifyTextPresent('ONLYOFFICE Docs', false, FailureHandling.STOP_ON_FAILURE)

	String pageText = WebUI.executeJavaScript('return document.body.innerText;', null)
	def versionMatch = pageText =~ /Version\s+([0-9]+(?:\.[0-9]+)*)/
	String shownVersion = versionMatch.find() ? versionMatch.group(1) : null
	WebUI.verifyEqual(shownVersion, GlobalVariable.expectedOOVersion, FailureHandling.STOP_ON_FAILURE)
} finally {
	WebUI.closeBrowser()
}
