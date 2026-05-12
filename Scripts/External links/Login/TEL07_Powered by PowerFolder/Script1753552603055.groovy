import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.model.FailureHandling as FailureHandling

WebUI.openBrowser(GlobalVariable.URL)

WebUI.maximizeWindow()

// attendre et cliquer sur le lien
WebUI.waitForElementClickable(findTestObject('External links/Page_Login - PowerFolder/Powered by PowerFolder'), 10)

WebUI.click(findTestObject('External links/Page_Login - PowerFolder/Powered by PowerFolder'))

// switch vers nouvel onglet
WebUI.switchToWindowIndex(1)

//WebUI.waitForPageLoad(20)

// cliquer sur Accept du cookie
WebUI.waitForElementClickable(
	findTestObject('External links/Page_File Sync, Share und Backup Lsungen fr_1e97b9/button_Accept'),
	10
)
WebUI.click(
	findTestObject('External links/Page_File Sync, Share und Backup Lsungen fr_1e97b9/button_Accept')
)

// attendre que la page soit bien chargée (élément fiable)
WebUI.waitForElementVisible(findTestObject('External links/Page_File Sync, Share und Backup Lsungen fr_1e97b9/img_Skip to content_NzQxOjMxOA-1'), 20)

// récupérer URL
String currentUrl = WebUI.getUrl()
WebUI.comment("L'URL actuelle est: " + currentUrl)

// vérification
String expectedUrl = 'https://www.powerfolder.com/'
WebUI.verifyEqual(currentUrl, expectedUrl)

// fermeture
WebUI.closeWindowIndex(1)

WebUI.switchToWindowIndex(0)

WebUI.closeBrowser()