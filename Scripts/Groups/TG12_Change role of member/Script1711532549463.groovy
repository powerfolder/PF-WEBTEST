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
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

// Appel du cas de test pour ajouter un membre
WebUI.callTestCase(findTestCase('Groups/Pre_test/add member'), [:], FailureHandling.STOP_ON_FAILURE)

// Navigation vers la page d'édition des groupes
WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))
WebUI.delay(2) // Ajout d'un délai pour permettre le chargement de la page

// Navigation vers la page des membres
WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Members'))
WebUI.delay(2) // Ajout d'un délai pour permettre le chargement de la page

// Délai pour attendre le chargement de la page
WebUI.delay(1)

// Clic sur le bouton "Is member"
WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Is member'))
WebUI.delay(1) // Ajout d'un délai pour permettre le chargement du menu déroulant

// Clic sur l'élément "Is member"
WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Is member'))
WebUI.delay(1) // Ajout d'un délai pour permettre la sélection de l'élément

// Clic sur le bouton "Save"
WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))
WebUI.delay(1) // Ajout d'un délai pour permettre la sauvegarde des modifications

// Vérification de la présence du message de confirmation de mise à jour du groupe
WebUI.verifyElementPresent(findTestObject('Groups/Page_Groups - PowerFolder/div_Group updated'), 5)

// Fermeture du navigateur
WebUI.closeBrowser()
