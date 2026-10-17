package onlyoffice

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

class OOEditor {

	static int timeout() {
		return GlobalVariable.editorTimeout as int
	}

	static String kind(String type) {
		switch (type) {
			case 'xlsx':
			case 'csv':
				return 'cell'
			case 'pptx':
				return 'slide'
			default:
				return 'word'
		}
	}

	static Map editorState() {
		return OnlyOffice.js("var m=document.querySelector('.asc-loadmask, #loadmask, .loadmask');" +
				"var w=[].slice.call(document.querySelectorAll('.asc-window')).filter(function(x){return x.offsetParent;});" +
				"var other=w.filter(function(x){return !/CSV/.test(x.innerText);})[0];" +
				"return {loading: !!(m && m.offsetParent), csv: w.some(function(x){return /CSV/.test(x.innerText);})," +
				" dialog: other ? other.innerText.trim().replace(/\\s+/g, ' ') : null};") as Map
	}

	@Keyword
	static void waitReady(String type) {
		WebUI.switchToDefaultContent()
		WebUI.waitForElementPresent(findTestObject('ONLY OFFICE/iframe_frameEditor'), timeout())
		WebUI.switchToFrame(findTestObject('ONLY OFFICE/iframe_frameEditor'), timeout())
		WebUI.waitForElementVisible(findTestObject('ONLY OFFICE/div_editor_sdk'), timeout())
		OnlyOffice.waitUntil(timeout(), 'the ONLYOFFICE editor to finish loading') {
			Map s = editorState()
			!s.loading || s.dialog || s.csv
		}
		if (type == 'csv') {
			confirmCsvOptions()
			OnlyOffice.waitUntil(timeout(), 'the ONLYOFFICE editor to finish loading the CSV file') {
				Map s = editorState()
				!s.loading || s.dialog
			}
		}
		WebUI.delay(GlobalVariable.editorSettleSeconds as int)
		Map state = editorState()
		if (state.dialog) {
			KeywordUtil.markFailedAndStop("ONLYOFFICE shows a dialog instead of the document: ${state.dialog}")
		}
		if (state.loading) {
			KeywordUtil.markFailedAndStop('ONLYOFFICE did not finish loading the document')
		}
	}

	@Keyword
	static String editorMode() {
		WebUI.switchToDefaultContent()
		return OnlyOffice.js("return (window.Picasso && Picasso.get) ? Picasso.get('ooEditorMode') : null;") as String
	}

	static void confirmCsvOptions() {
		boolean shown = OnlyOffice.tryWaitUntil(8) {
			OnlyOffice.js("return [].slice.call(document.querySelectorAll('.asc-window')).some(function(w){" +
					"return w.offsetParent && /CSV/.test(w.innerText);});") as Boolean
		}
		if (shown) {
			OnlyOffice.js("[].slice.call(document.querySelectorAll('.asc-window')).forEach(function(w){" +
					"if(w.offsetParent && /CSV/.test(w.innerText)){" +
					"var b=[].slice.call(w.querySelectorAll('button')).filter(function(x){return /^OK\$/i.test((x.innerText||'').trim());})[0];" +
					"if(b){b.click();}}});")
			KeywordUtil.logInfo('Confirmed the CSV options dialog with its defaults')
			WebUI.delay(2)
		}
	}

	@Keyword
	static String version() {
		WebUI.switchToDefaultContent()
		return OnlyOffice.js("return (window.DocsAPI && DocsAPI.DocEditor && DocsAPI.DocEditor.version)" +
				" ? DocsAPI.DocEditor.version() : null;") as String
	}

	static WebElement editorArea(WebDriver d, String type) {
		if (kind(type) == 'slide') {
			List<WebElement> main = d.findElements(By.id('id_main_view'))
			if (!main.isEmpty() && main[0].isDisplayed()) {
				return main[0]
			}
		}
		return d.findElement(By.id('editor_sdk'))
	}

	@Keyword
	static void typeLine(String type, String text, int lineNo) {
		WebDriver d = OnlyOffice.driver()
		WebElement area = editorArea(d, type)
		Actions a = new Actions(d)
		switch (kind(type)) {
			case 'word':
				a.moveToElement(area).click().perform()
				a.keyDown(Keys.CONTROL).sendKeys(Keys.END).keyUp(Keys.CONTROL).perform()
				if (lineNo > 1) {
					a.sendKeys(Keys.ENTER).perform()
				}
				a.sendKeys(text).perform()
				break
			case 'cell':
				a.moveToElement(area).click().perform()
				a.keyDown(Keys.CONTROL).sendKeys(Keys.HOME).keyUp(Keys.CONTROL).perform()
				for (int i = 1; i < lineNo; i++) {
					a.sendKeys(Keys.DOWN).perform()
				}
				a.sendKeys(text).sendKeys(Keys.ENTER).perform()
				break
			case 'slide':
				Dimension s = area.getSize()
				double slideW = Math.min(s.width * 0.92d, s.height * 0.92d * 16d / 9d)
				double slideH = slideW * 9d / 16d
				int dy = (int) Math.round(lineNo == 1 ? -0.08d * slideH : 0.19d * slideH)
				a.moveToElement(area).moveByOffset(0, dy).doubleClick().perform()
				WebUI.delay(1)
				a.sendKeys(text).perform()
				a.sendKeys(Keys.ESCAPE).perform()
				break
		}
		WebUI.delay(1)
	}

	static Keys shortcutKey() {
		return System.getProperty('os.name').toLowerCase().contains('mac') ? Keys.COMMAND : Keys.CONTROL
	}

	@Keyword
	static void save() {
		new Actions(OnlyOffice.driver()).keyDown(shortcutKey()).sendKeys('s').keyUp(shortcutKey()).perform()
		WebUI.delay(1)
		if (!waitSavedStatus(20)) {
			KeywordUtil.markWarning('Editor did not show "All changes saved" within 20 s')
		}
	}

	@Keyword
	static boolean waitSavedStatus(int seconds) {
		return OnlyOffice.tryWaitUntil(seconds) {
			OnlyOffice.js("return /Alle \u00c4nderungen (wurden )?gespeichert|All changes saved/.test(document.body.innerText);") as Boolean
		}
	}
}
