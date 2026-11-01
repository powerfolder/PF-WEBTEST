import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import onlyoffice.OnlyOffice

String ooUrl = GlobalVariable.ooUrl
List<String> fileTypes = ['docx', 'txt', 'xlsx', 'csv', 'pptx']

try {
	WebUI.openBrowser(ooUrl + '/welcome/')
	Map discovery = OnlyOffice.readWopiDiscovery(ooUrl + '/hosting/discovery')
	WebUI.verifyEqual(discovery.status, 200, FailureHandling.STOP_ON_FAILURE)
	WebUI.verifyEqual(discovery.root, 'wopi-discovery', FailureHandling.STOP_ON_FAILURE)

	List<Map> actions = discovery.actions
	List<String> typesWithoutEdit = fileTypes.findAll { String type ->
		!actions.any { it.name == 'edit' && it.ext == type }
	}
	WebUI.verifyEqual(typesWithoutEdit, [], FailureHandling.STOP_ON_FAILURE)

	List<String> otherHosts = actions.collect { it.url as String }.findAll { !it?.startsWith(ooUrl + '/') }.unique()
	WebUI.verifyEqual(otherHosts, [], FailureHandling.STOP_ON_FAILURE)
} finally {
	WebUI.closeBrowser()
}
