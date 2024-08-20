import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import java.util.List as List

// Appel du cas de test de connexion
WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

// Création de deux groupes
String group_1 = 'Group_' + RandomStringUtils.randomNumeric(4)
String group_2 = 'Group_2_' + RandomStringUtils.randomNumeric(4)

WebUI.click(findTestObject('Object Repository/Groups/Page_Dashboard - PowerFolder/lang_Groups'))

// Création du premier groupe
WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/Create_group_button'))
WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/input_Organizations_pica_group_name'), group_1)
WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/textarea_Organizations_pica_group_notes'), 'create group')
WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))
WebUI.delay(2)

// Création du deuxième groupe
WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/Create_group_button'))
WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/input_Organizations_pica_group_name'), group_2)
WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/textarea_Organizations_pica_group_notes'), 'create group')
WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))
WebUI.delay(2)

// Sélection et suppression des groupes créés
WebElement premierElement = DriverFactory.getWebDriver().findElement(By.xpath('//div[2]/table/tbody/tr[1]'))
premierElement.click()
WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/select_all'))
WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/a_Delete'))
WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Yes'))

WebUI.delay(5)

// Vérification que la table est vide
WebDriver driver = DriverFactory.getWebDriver()

// Recharger la page pour être sûr que la table est mise à jour
WebUI.refresh()

// Attendre que la table soit mise à jour
WebUI.delay(3)

List<WebElement> items = driver.findElements(By.xpath('//td[1]/span'))

assert items.isEmpty() : "La table n'est pas vide après la suppression des groupes."

// Fermeture du navigateur
WebUI.closeBrowser()

