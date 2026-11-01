package utils

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.util.KeywordUtil
import credentials.CredentialsManager
import groovy.json.JsonSlurper
import internal.GlobalVariable
import java.net.HttpURLConnection

class AccountApi {

	@Keyword
	static String getServerUrl() {
		URL url = new URL(GlobalVariable.URL)
		return url.protocol + '://' + url.authority
	}

	@Keyword
	static String createTokenForAccount(String username, int validitySeconds = 3600) {
		String urlStr = getServerUrl() + '/api/accounts'
		String body = 'action=createTokenForAccount' +
				'&name=' + URLEncoder.encode(username, 'UTF-8') +
				'&validitySeconds=' + validitySeconds
		byte[] data = body.getBytes('UTF-8')
		String auth = (CredentialsManager.getAdminUsername() + ':' + CredentialsManager.getAdminPassword())
				.getBytes('UTF-8').encodeBase64().toString()

		HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection()
		conn.setRequestMethod('POST')
		conn.setDoOutput(true)
		conn.setRequestProperty('Authorization', 'Basic ' + auth)
		conn.setRequestProperty('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8')
		conn.setFixedLengthStreamingMode(data.length)

		conn.getOutputStream().withCloseable { it.write(data) }

		int code = conn.getResponseCode()
		String response = (code == 200 ? conn.getInputStream() : conn.getErrorStream())?.getText('UTF-8')
		conn.disconnect()

		if (code != 200) {
			KeywordUtil.markFailedAndStop("createTokenForAccount for '${username}' -> HTTP ${code}: ${response}")
		}
		String token = new JsonSlurper().parseText(response).token
		KeywordUtil.logInfo("createTokenForAccount for '${username}' -> token created, valid for ${validitySeconds} s")
		return token
	}
}
