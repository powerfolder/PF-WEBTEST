import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.model.FailureHandling as FailureHandling

WebUI.openBrowser(GlobalVariable.URL)
WebUI.maximizeWindow()

WebUI.verifyElementClickable(findTestObject('Object Repository/External links/Page_Login - PowerFolder/Cookies Policy'))
WebUI.click(findTestObject('Object Repository/External links/Page_Login - PowerFolder/Cookies Policy'))

WebUI.switchToWindowIndex(1)
WebUI.waitForPageLoad(20)

// cliquer sur Accept si le bouton est présent
if (WebUI.verifyElementPresent(findTestObject('Object Repository/External links/Page_Datenschutzerklrung/button_Accept'), 5, FailureHandling.OPTIONAL)) {
    WebUI.waitForElementClickable(findTestObject('Object Repository/External links/Page_Datenschutzerklrung/button_Accept'), 10)
    WebUI.click(findTestObject('Object Repository/External links/Page_Datenschutzerklrung/button_Accept'))
}

// attendre que la page soit bien chargée
WebUI.waitForElementVisible(findTestObject('Object Repository/External links/Page_Datenschutzerklrung/Serverstandort'), 20)

// récupérer l’URL seulement après
String currentUrl = WebUI.getUrl()
WebUI.comment("L'URL actuelle est: " + currentUrl)

String expectedUrl = 'https://www.powerfolder.com/7084-2/'
WebUI.verifyEqual(currentUrl, expectedUrl)

WebUI.verifyElementText(findTestObject('Object Repository/External links/Page_Datenschutzerklrung/Serverstandort'), 'SERVERSTANDORT:')

WebUI.closeWindowIndex(1)
WebUI.switchToWindowIndex(0)
WebUI.closeBrowser()
