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

WebUI.callTestCase(findTestCase('Upload form/Pre_Test/Creat_Folder'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementClickable(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Create upload form'))

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Create upload form'))

WebUI.setText(findTestObject('1Upload_Form/Page_Folders - PowerFolder/input_uploadform_heading'), 'Workshop')

WebUI.setText(findTestObject('1Upload_Form/Page_Folders - PowerFolder/input_upload_description'), 'Workshop number 1')

WebUI.scrollToElement(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Save'), 1)

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Save'))

WebUI.executeJavaScript('window.open();', [])

WebUI.delay(2)

WebUI.switchToWindowIndex(0)

WebUI.waitForElementClickable(findTestObject('1Upload_Form/Page_Folders - PowerFolder/clipboard_buttom'), 2)

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/clipboard_buttom'))

WebUI.delay(2)

WebUI.switchToWindowIndex(1)

String my_clipboard = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor)

WebUI.navigateToUrl(my_clipboard)

GlobalVariable.userEmail = (('user_' + RandomStringUtils.randomNumeric(4)) + '@test.com')

GlobalVariable.Name = ('My_Name_' + RandomStringUtils.randomNumeric(4))

WebUI.setText(findTestObject('1Upload_Form/Page_Link - PowerFolder/input_username'), GlobalVariable.Name)

WebUI.setText(findTestObject('1Upload_Form/Page_Link - PowerFolder/input_mail'), GlobalVariable.userEmail)

WebUI.click(findTestObject('1Upload_Form/Page_Link - PowerFolder/button_Upload'))

WebUI.delay(2)

