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
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.DataFlavor as DataFlavor
import org.openqa.selenium.By as By
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.nio.file.Path as Path
import java.nio.file.Files as Files
import java.text.SimpleDateFormat as SimpleDateFormat
import java.util.Calendar as Calendar
import java.util.Date as Date
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import java.util.Random as Random
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.Select
import java.util.List
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.Select
import java.util.List

// Exécuter le pré-test pour créer un compte
WebUI.callTestCase(findTestCase('Accounts/Edit_Account/pre_test/Create_Account'), [:], FailureHandling.STOP_ON_FAILURE);

println(GlobalVariable.userEmail);

// Trouver l'utilisateur et ouvrir son profil
WebElement btn = findAccount(GlobalVariable.userEmail);

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn));

WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/Edit_account'));

// 📌 Étape 1 : Vérifier les options réelles disponibles dans le dropdown
WebElement dropdownElement = WebUI.findWebElement(findTestObject('Accounts/SelectLanguageDropDrown'), 5);
Select dropdown = new Select(dropdownElement);
List<WebElement> options = dropdown.getOptions();

System.out.println("📌 Options disponibles dans le menu déroulant :");
List<String> availableOptions = new ArrayList<>();
for (WebElement option : options) {
	String text = option.getText().trim();
	availableOptions.add(text);
	System.out.println("- " + text);
}

// 📌 Étape 2 : Déterminer les libellés corrects des langues en fonction des options disponibles
String englishOption = availableOptions.contains("English") ? "English" : "Englisch";
String germanOption = availableOptions.contains("German") ? "German" : "Deutsch";

// 📌 Étape 3 : Déterminer la langue actuelle
String currentLanguage = WebUI.getText(findTestObject('Accounts/SelectLanguageDropDrown')).trim();
System.out.println("🌍 Langue actuelle détectée : " + currentLanguage);

// 📌 Étape 4 : Déterminer quelle langue sélectionner en premier
String firstSelection = (currentLanguage.contains("English") || currentLanguage.contains("Englisch"))
	? germanOption : englishOption;

// Déterminer la deuxième sélection
String secondSelection = firstSelection.equals(englishOption) ? germanOption : englishOption;

// 📌 Étape 5 : Sélectionner la première langue
WebUI.selectOptionByLabel(findTestObject('Accounts/SelectLanguageDropDrown'), firstSelection, false);
System.out.println("✅ Première sélection : " + firstSelection);

// Sauvegarder les modifications
WebUI.click(findTestObject('Accounts/SaveButton'));

// Vérifier si le changement de langue a bien eu lieu
WebElement btn1 = findAccount(GlobalVariable.userEmail);
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1));
WebUI.click(findTestObject('Accounts/Edit_Accounts - PowerFolder/Edit_account'));

// 📌 Étape 6 : Sélectionner la deuxième langue
WebUI.selectOptionByLabel(findTestObject('Accounts/SelectLanguageDropDrown'), secondSelection, false);
System.out.println("✅ Deuxième sélection : " + secondSelection);

// Sauvegarder et fermer
WebUI.click(findTestObject('Accounts/SaveButton'));
WebUI.closeBrowser();

// 📌 Fonction pour rechercher un compte utilisateur
WebElement findAccount(String emailId) {
	WebDriver driver = DriverFactory.getWebDriver();
	return driver.findElement(By.xpath("//*[contains(@data-search-keys, '" + emailId + "')]/td[1]/span"));
}
