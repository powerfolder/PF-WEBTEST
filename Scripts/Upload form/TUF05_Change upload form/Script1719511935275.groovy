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
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.DataFlavor as DataFlavor
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import com.kms.katalon.core.annotation.Keyword as Keyword
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait
import java.util.Arrays as Arrays
import javax.swing.filechooser.FileSystemView as FileSystemView
import org.apache.poi.xwpf.usermodel.XWPFDocument as XWPFDocument
import java.io.FileOutputStream as FileOutputStream
import java.nio.file.Path as Path
import java.nio.file.Paths as Paths
import java.nio.file.Files as Files
import org.apache.commons.io.FileUtils as FileUtils
import java.awt.datatransfer.StringSelection as StringSelection
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import java.io.IOException as IOException

WebUI.callTestCase(findTestCase('Upload form/Pre_Test/Creat_upload_form'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.switchToWindowIndex(0)

WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), 5)

// Attendre que l'élément devienne visible
WebElement settings_button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//td[2]/div/div/div[1]/span[1]')))

// Cliquer sur l'élément
settings_button.click()

// Trouver le champ de texte et le vider avant de définir le nouveau texte
TestObject descriptionField = findTestObject('1Upload_Form/Page_Error - PowerFolder/Page_Folders - PowerFolder/change_description_1')

WebElement descriptionElement = WebUiCommonHelper.findWebElement(descriptionField, 5)

// Sélectionner tout le texte et le supprimer
descriptionElement.sendKeys(Keys.chord(Keys.CONTROL, 'a'))

descriptionElement.sendKeys(Keys.DELETE)

// Définir le nouveau texte
WebUI.setText(descriptionField, 'change description')

WebDriverWait wait1 = new WebDriverWait(DriverFactory.getWebDriver(), 5)

// Attendre que l'élément devienne visible
WebElement save_button = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath('//div[16]/div/div/div[3]/button')))

// Cliquer sur l'élément
save_button.click()

WebUI.switchToWindowIndex(1)

WebUI.refresh()

WebUI.delay(2)

WebUI.verifyElementText(findTestObject('1Upload_Form/Page_Link - PowerFolder/Verify_change'), 'change description')

WebUI.delay(2)

WebUI.closeBrowser()

