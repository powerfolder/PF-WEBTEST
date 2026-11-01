package utils

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.util.KeywordUtil
import credentials.CredentialsManager
import groovy.json.JsonSlurper
import internal.GlobalVariable
import java.net.HttpURLConnection

class PreferencesApi {

	@Keyword
	static String getConfig(String entry) {
		URL baseUrl = new URL(GlobalVariable.URL)
		String urlStr = baseUrl.protocol + '://' + baseUrl.authority + '/api/preferences?action=getConfig&entry=' +
				URLEncoder.encode(entry, 'UTF-8')
		String auth = (CredentialsManager.getAdminUsername() + ':' + CredentialsManager.getAdminPassword())
				.getBytes('UTF-8').encodeBase64().toString()

		HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection()
		conn.setRequestMethod('GET')
		conn.setRequestProperty('Authorization', 'Basic ' + auth)

		int code = conn.getResponseCode()
		String response = (code == 200 ? conn.getInputStream() : conn.getErrorStream())?.getText('UTF-8')
		conn.disconnect()

		if (code != 200 || !response?.trim()?.startsWith('{')) {
			KeywordUtil.markFailedAndStop("getConfig for '${entry}' -> HTTP ${code}: ${response}")
		}
		def json = new JsonSlurper().parseText(response)
		if (!json.known || json.config?.get(entry) == null) {
			KeywordUtil.markFailedAndStop("getConfig for '${entry}' -> unknown entry: ${response}")
		}
		String value = json.config.get(entry)
		KeywordUtil.logInfo("getConfig for '${entry}' -> ${value}")
		return value
	}
}
