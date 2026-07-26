import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import org.openqa.selenium.WebElement as WebElement
import java.util.Arrays as Arrays

WebUI.openBrowser(GlobalVariable.URL)

WebUI.maximizeWindow()

// attendre et cliquer sur le lien
WebUI.waitForElementClickable(findTestObject('External links/Page_Login - PowerFolder/Powered by PowerFolder'), 10)

WebUI.click(findTestObject('External links/Page_Login - PowerFolder/Powered by PowerFolder'))

// switch vers nouvel onglet
WebUI.switchToWindowIndex(1)

WebUI.delay(5)

// cliquer sur Accept du cookie (le bandeau ne s'affiche qu'une fois par profil navigateur ;
TestObject cookieAccept = findTestObject('External links/Page_File Sync, Share und Backup Lsungen fr_1e97b9/button_Accept')

if (WebUI.verifyElementPresent(cookieAccept, 5, FailureHandling.OPTIONAL)) {
	WebElement acceptBtn = WebUiCommonHelper.findWebElement(cookieAccept, 5)
	WebUI.executeJavaScript('arguments[0].click();', Arrays.asList(acceptBtn))
}

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