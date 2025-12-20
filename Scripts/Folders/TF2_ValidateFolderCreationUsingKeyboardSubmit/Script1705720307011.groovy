 // Import statements
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
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils

// Ouvre un nouveau navigateur
WebUI.openBrowser('')

// Maximize the browser window
WebUI.maximizeWindow()

// Navigue vers l'URL spécifiée dans la variable globale URL
WebUI.navigateToUrl(GlobalVariable.URL)

// Vérifie si le titre de la page correspond à "Login - PowerFolder"
assert WebUI.getWindowTitle().equals('Login - PowerFolder')

// Remplit le champ de saisie pour le nom d'utilisateur avec la valeur de la variable globale Username
WebUI.setEncryptedText(findTestObject('Login/inputEmail'), 'CKkAs2Ee0vA=')

// Attend jusqu'à 2 secondes pour que l'élément de saisie de mot de passe soit visible et stocke le résultat dans isPresent
isPresent = WebUI.waitForElementVisible(findTestObject('Object Repository/Login/Page_Login - PowerFolder/input_Recover password_Password'),
	2)

// Vérifie si l'élément de saisie de mot de passe est présent sur la page
assert isPresent

WebUI.setEncryptedText(findTestObject('Login/inputPassword'), 'PpFy9OM6JMUrpEOD1UO9247r7Yrm9E0x')

// Clique sur le bouton "Login"
WebUI.click(findTestObject('Object Repository/Login/Page_Login - PowerFolder/input_register new account_Login'))

WebUI.delay(2)

// Clique sur le bouton "Folders"
WebUI.click(findTestObject('Object Repository/Folders/Page_Folders - PowerFolder/lang_Folders'))

// Generate a random string of length 5
String folderN = RandomStringUtils.randomAlphanumeric(5)

// Store the original folder count
originfolderCount = getFoldersCount()

// Generate a random folder name
String folderName = getRandomFolderName()

// Click on create folder icon
WebUI.click(findTestObject('Folders/createFolderIcon'))

// Click on create folder
WebUI.click(findTestObject('Folders/createFolder'))

// Set text in input folder name
WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)

// Send keys ENTER to submit folder creation
WebUI.sendKeys(findTestObject('Folders/inputFolderName'), Keys.chord(Keys.ENTER))

// Clique sur le bouton "Folders"
WebUI.click(findTestObject('Object Repository/Folders/Page_Folders - PowerFolder/lang_Folders'))

// Assert folder count has increased
assert getFoldersCount() > originfolderCount

// Assert folder table contains folder name
assert tableContainsFolder(folderName)

// Close browser
WebUI.closeBrowser() 


// Function to get folders count
int getFoldersCount() {
    WebDriver driver = DriverFactory.getWebDriver()

    WebElement tbody = driver.findElement(By.xpath('//table[@id=\'files_files_table\']/tbody'))

    assert tbody

    List<WebElement> rows_table = tbody.findElements(By.tagName('tr'))

    return rows_table.size()
}
// Function to check if table contains folder
boolean tableContainsFolder(String fileName) {
    WebDriver driver = DriverFactory.getWebDriver()

    WebElement tfolder = driver.findElement(By.xpath(('//td[2]/span/a[contains(text(),\'' + fileName) + '\')]'))
	
    return tfolder.isDisplayed()
}
// Function to generate random folder name
String getRandomFolderName() {
    String folderName = 'F' + getTimestamp()

    return folderName
}
// Function to get timestamp
String getTimestamp() {
    Date todaysDate = new Date()

    String formattedDate = todaysDate.format('ddMMMyyyyhhmmss')

    return formattedDate
}

