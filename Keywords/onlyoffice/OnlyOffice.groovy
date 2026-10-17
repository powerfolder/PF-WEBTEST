package onlyoffice

import java.time.Duration
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.json.JsonSlurper
import internal.GlobalVariable

class OnlyOffice {

	static String base() {
		return GlobalVariable.URL.toString().replaceAll('/+$', '').replaceAll('/login$', '')
	}

	static WebDriver driver() {
		return DriverFactory.getWebDriver()
	}

	static Object js(String script, Object... args) {
		return ((JavascriptExecutor) driver()).executeScript(script, args)
	}

	static Object jsAsync(String script, Object... args) {
		driver().manage().timeouts().scriptTimeout(Duration.ofSeconds(60))
		return ((JavascriptExecutor) driver()).executeAsyncScript(script, args)
	}

	static boolean waitUntil(int seconds, String what, Closure<Boolean> cond) {
		if (tryWaitUntil(seconds, cond)) {
			return true
		}
		KeywordUtil.markFailedAndStop("Timed out after ${seconds}s waiting for ${what}")
		return false
	}

	static boolean tryWaitUntil(int seconds, Closure<Boolean> cond) {
		long end = System.currentTimeMillis() + seconds * 1000L
		while (System.currentTimeMillis() < end) {
			try {
				if (cond.call()) {
					return true
				}
			} catch (Throwable ignored) {
			}
			Thread.sleep(500)
		}
		return false
	}

	@Keyword
	static String currentFolderId() {
		WebUI.switchToDefaultContent()
		waitUntil(30, 'a folder page (/files/<id>)') { WebUI.getUrl() ==~ /.*\/files\/[^\/?#]+.*/ }
		return WebUI.getUrl().replaceAll(/.*\/files\/([^\/?#]+).*/, '$1')
	}

	@Keyword
	static void openFolder(String folderId) {
		WebUI.switchToDefaultContent()
		WebUI.navigateToUrl(base() + '/files/' + folderId)
		WebUI.waitForPageLoad(30)
	}

	@Keyword
	static void openFile(String folderId, String fileName) {
		WebUI.switchToDefaultContent()
		String encoded = URLEncoder.encode(fileName, 'UTF-8').replace('+', '%20')
		WebUI.navigateToUrl(base() + '/open/' + folderId + '/' + encoded)
		WebUI.waitForPageLoad(30)
	}

	static Object api(String path) {
		WebUI.switchToDefaultContent()
		String script = "var cb=arguments[arguments.length-1];" +
				"var token=(window.Picasso&&Picasso.get)?Picasso.get('CSRF'):'';" +
				"fetch(arguments[0],{credentials:'same-origin',headers:{'X-CSRFToken':token}})" +
				".then(function(r){return r.text().then(function(t){cb(JSON.stringify({status:r.status,body:t}));});})" +
				".catch(function(e){cb(JSON.stringify({status:-1,body:String(e)}));});"
		def res = new JsonSlurper().parseText(jsAsync(script, path) as String)
		if (res.status != 200) {
			KeywordUtil.markFailedAndStop("API ${path} returned ${res.status}: ${res.body.toString().take(300)}")
		}
		return new JsonSlurper().parseText(res.body as String)
	}

	@Keyword
	static Map fileInfo(String folderId, String fileName) {
		def data = api("/wapi/folders/${folderId}?action=getFiles&page=1&size=&json=1")
		def list = data?.ResultSet?.Result ?: []
		def f = list.find { it.fileName == fileName && !it.deleted }
		if (!f) {
			return null
		}
		return [size: f.size as long, version: f.version as long, modified: f.modifiedDateMS as long, url: f.resourceURL]
	}

	@Keyword
	static String readFileText(String folderId, String fileName) {
		Map info = fileInfo(folderId, fileName)
		if (!info?.url) {
			KeywordUtil.markFailedAndStop("No download URL for ${fileName}")
		}
		String script = "var cb=arguments[arguments.length-1];" +
				"fetch(arguments[0],{credentials:'same-origin'}).then(function(r){return r.arrayBuffer().then(function(b){" +
				"  var a=new Uint8Array(b),s='';" +
				"  for(var i=0;i<a.length;i+=8192){s+=String.fromCharCode.apply(null,a.subarray(i,i+8192));}" +
				"  cb(JSON.stringify({status:r.status,data:btoa(s)}));" +
				"});}).catch(function(e){cb(JSON.stringify({status:-1,data:''}));});"
		WebUI.switchToDefaultContent()
		def res = new JsonSlurper().parseText(jsAsync(script, info.url) as String)
		if (res.status != 200) {
			KeywordUtil.markFailedAndStop("Download of ${fileName} returned ${res.status}")
		}
		byte[] bytes = Base64.decoder.decode(res.data as String)
		if (fileName ==~ /(?i).*\.(txt|csv)/) {
			return new String(bytes, 'UTF-8')
		}
		StringBuilder text = new StringBuilder()
		ZipInputStream zip = new ZipInputStream(new ByteArrayInputStream(bytes))
		try {
			ZipEntry entry
			while ((entry = zip.nextEntry) != null) {
				if (entry.name.endsWith('.xml')) {
					text.append(new String(zip.readAllBytes(), 'UTF-8').replaceAll('<[^>]+>', '')).append('\n')
				}
			}
		} finally {
			zip.close()
		}
		return text.toString()
	}

	@Keyword
	static Map waitForFile(String folderId, String fileName, int seconds) {
		Map info = null
		waitUntil(seconds, "${fileName} to appear in the folder") {
			info = fileInfo(folderId, fileName)
			info != null
		}
		return info
	}

	@Keyword
	static void waitForFileGone(String folderId, String fileName, int seconds) {
		waitUntil(seconds, "${fileName} to disappear from the folder") { fileInfo(folderId, fileName) == null }
	}

	@Keyword
	static Map waitForChange(String folderId, String fileName, Map previous, int seconds) {
		Map current = null
		waitUntil(seconds, "a saved change of ${fileName} (was ${previous})") {
			current = fileInfo(folderId, fileName)
			current != null && (current.version != previous.version || current.size != previous.size ||
					current.modified != previous.modified)
		}
		KeywordUtil.logInfo("${fileName}: ${previous} -> ${current}")
		return current
	}

	@Keyword
	static Map readWopiDiscovery(String discoveryUrl) {
		WebUI.switchToDefaultContent()
		String script = "var cb=arguments[arguments.length-1];" +
				"fetch(arguments[0]).then(function(r){return r.text().then(function(t){" +
				"  var d=new DOMParser().parseFromString(t,'application/xml');" +
				"  var acts=[].slice.call(d.getElementsByTagName('action')).map(function(a){" +
				"    return {name:a.getAttribute('name'),ext:a.getAttribute('ext'),url:a.getAttribute('urlsrc')};});" +
				"  cb(JSON.stringify({status:r.status,root:d.documentElement.nodeName,actions:acts}));" +
				"});}).catch(function(e){cb(JSON.stringify({status:-1,root:String(e),actions:[]}));});"
		return new JsonSlurper().parseText(jsAsync(script, discoveryUrl) as String) as Map
	}
}
