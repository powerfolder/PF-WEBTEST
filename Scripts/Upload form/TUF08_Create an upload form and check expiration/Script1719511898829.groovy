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
import java.text.SimpleDateFormat as SimpleDateFormat
import java.util.Calendar as Calendar
import java.util.Date as Date
import static com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords.*

// create folder with uploadform where expiration is in 2min future
WebUI.callTestCase(findTestCase('Upload form/Pre_Test/Creat_Folder'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementClickable(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Create upload form'))

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Create upload form'))

WebUI.delay(5)

WebUI.setText(findTestObject('1Upload_Form/Page_Error - PowerFolder/Page_Folders - PowerFolder/input_Create_uploadform_heading'), 
    'Workshop - not expired')

WebUI.setText(findTestObject('1Upload_Form/Page_Folders - PowerFolder/change_description'), 'Workshop number 1')

// get time stamp in 2 min future
String newDateTime = generateDateTimePlusTwoMinutes()

// click in calender
WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/input_Redo_pica_uploadform_valid_till'))

// clear pre filled textfield
WebUI.sendKeys(findTestObject('1Upload_Form/Page_Folders - PowerFolder/input_Redo_pica_uploadform_valid_till'), Keys.chord(
        Keys.DELETE))

// set new expiration date
WebUI.setText(findTestObject('1Upload_Form/Page_Folders - PowerFolder/input_Redo_pica_uploadform_valid_till'), newDateTime)

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

WebUI.switchToWindowIndex(0)

WebUI.delay(2)

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Close'))

WebElement btn = findFolder(GlobalVariable.folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebElement btn1 = findFolder(GlobalVariable.Name)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

WebUI.delay(2)

WebUI.closeWindowIndex('0')

// create new folder with upload form where timestamp is current - should be expired
WebUI.callTestCase(findTestCase('Upload form/Pre_Test/Creat_Folder'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementClickable(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Create upload form'))

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Create upload form'))

WebUI.delay(5)

WebUI.setText(findTestObject('1Upload_Form/Page_Error - PowerFolder/Page_Folders - PowerFolder/input_Create_uploadform_heading'), 
    'Workshop - present - expired')

WebUI.setText(findTestObject('1Upload_Form/Page_Folders - PowerFolder/change_description'), 'Workshop number 2')

// click in calender
WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/input_Redo_pica_uploadform_valid_till'))

WebUI.scrollToElement(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Save'), 1)

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Save'))

WebUI.executeJavaScript('window.open();', [])

WebUI.delay(2)

WebUI.switchToWindowIndex(0)

WebUI.waitForElementClickable(findTestObject('1Upload_Form/Page_Folders - PowerFolder/clipboard_buttom'), 2)

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/clipboard_buttom'))

WebUI.delay(2)

// open upload form and check if its expired

WebUI.switchToWindowIndex(1)

String my_clipboard_present_link = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor)

WebUI.navigateToUrl(my_clipboard_present_link)

assert WebUI.getWindowTitle().equals('Link - PowerFolder')

WebUI.verifyElementPresent(findTestObject('lang/expiredlinkText'), 30)

WebUI.click(findTestObject('1Upload_Form/Page_Link - PowerFolder/close_expired_warn_button'))

WebUI.verifyElementPresent(findTestObject('1Upload_Form/Page_Link - PowerFolder/lang_This link is expired and cannot be used anymore'), 
    30)

WebUI.closeWindowIndex('0')

// create new folder with upload form where timestamp is in past (present-2min) - should be expired
// get time stamp
String newDateTimePast = generateDateTimeMinusTwoMinutes()

// create new folder with upload form
WebUI.callTestCase(findTestCase('Upload form/Pre_Test/Creat_Folder'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementClickable(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Create upload form'))

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Create upload form'))

WebUI.delay(5)

WebUI.setText(findTestObject('1Upload_Form/Page_Error - PowerFolder/Page_Folders - PowerFolder/input_Create_uploadform_heading'),
	'Workshop - expired - 2min from past')

WebUI.setText(findTestObject('1Upload_Form/Page_Folders - PowerFolder/change_description'), 'Workshop number 3')

// click in calender
WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/input_Redo_pica_uploadform_valid_till'))

// clear pre filled textfield
WebUI.sendKeys(findTestObject('1Upload_Form/Page_Folders - PowerFolder/input_Redo_pica_uploadform_valid_till'), Keys.chord(
		Keys.DELETE))

// set new expiration date
WebUI.setText(findTestObject('1Upload_Form/Page_Folders - PowerFolder/input_Redo_pica_uploadform_valid_till'), newDateTimePast)

WebUI.scrollToElement(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Save'), 1)

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Save'))

WebUI.executeJavaScript('window.open();', [])

WebUI.delay(2)

WebUI.switchToWindowIndex(0)

WebUI.delay(2)

WebUI.switchToWindowIndex(0)

WebUI.waitForElementClickable(findTestObject('1Upload_Form/Page_Folders - PowerFolder/clipboard_buttom'), 2)

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/clipboard_buttom'))

WebUI.delay(2)

// open upload form and check if its expired

WebUI.switchToWindowIndex(1)

String my_clipboard_past_link = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor)

WebUI.navigateToUrl(my_clipboard_past_link)

assert WebUI.getWindowTitle().equals('Link - PowerFolder')

WebUI.verifyElementPresent(findTestObject('lang/expiredlinkText'), 30)

WebUI.click(findTestObject('1Upload_Form/Page_Link - PowerFolder/close_expired_warn_button'))

WebUI.verifyElementPresent(findTestObject('1Upload_Form/Page_Link - PowerFolder/lang_This link is expired and cannot be used anymore'),
	30)

WebUI.closeWindowIndex('0')

WebUI.closeBrowser()

@Keyword
WebElement findFolder(String folderName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//a[contains(text(),\'' + folderName) + '\')]'))
}

String generateDateTimePlusTwoMinutes() {
    Calendar calendar = Calendar.getInstance()
    // add two minutes
    calendar.add(Calendar.MINUTE, 2)

    SimpleDateFormat sdf = new SimpleDateFormat('MM/dd/yyyy HH:mm:ss')

    return sdf.format(calendar.getTime())
}

String generateDateTimeMinusTwoMinutes() {
	Calendar calendar = Calendar.getInstance()

	// remove two minutes
	calendar.add(Calendar.MINUTE, -2)

	SimpleDateFormat sdf = new SimpleDateFormat('MM/dd/yyyy HH:mm:ss')

	return sdf.format(calendar.getTime())
}
