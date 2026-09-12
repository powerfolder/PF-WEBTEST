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
import java.text.SimpleDateFormat as SimpleDateFormat
import java.util.Calendar as Calendar

WebUI.callTestCase(findTestCase('Upload form/Pre_Test/Creat_Folder'), [:], FailureHandling.STOP_ON_FAILURE)

String folderWithDate = GlobalVariable.folderName

WebUI.verifyElementClickable(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Create upload form'))

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Create upload form'))

WebUI.delay(5)

WebUI.setText(findTestObject('1Upload_Form/Page_Error - PowerFolder/Page_Folders - PowerFolder/input_Create_uploadform_heading'), 'Workshop with date')

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/change_description'))
WebUI.sendKeys(findTestObject('1Upload_Form/Page_Folders - PowerFolder/change_description'), 'Has a valid-till date')

setDateTimePickerValue(generateDatetimeLocalToday())

WebUI.scrollToElement(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Save'), 1)

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Save'))

WebUI.delay(1)

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Close'))

WebUI.callTestCase(findTestCase('Upload form/Pre_Test/Creat_Folder'), [:], FailureHandling.STOP_ON_FAILURE)

String folderWithoutDate = GlobalVariable.folderName

WebUI.verifyElementClickable(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Create upload form'))

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Create upload form'))

WebUI.delay(5)

WebUI.setText(findTestObject('1Upload_Form/Page_Error - PowerFolder/Page_Folders - PowerFolder/input_Create_uploadform_heading'), 'Workshop without date')

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/change_description'))
WebUI.sendKeys(findTestObject('1Upload_Form/Page_Folders - PowerFolder/change_description'), 'No valid-till date')

WebUI.scrollToElement(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Save'), 1)

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Save'))

WebUI.delay(1)

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Close'))

WebUI.click(findTestObject('Object Repository/Folders/Page_Folders - PowerFolder/lang_Folders'))

WebUI.refresh()

WebElement shareBtnA = findShareButton(folderWithDate)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(shareBtnA))

new org.openqa.selenium.support.ui.WebDriverWait(DriverFactory.getWebDriver(), java.time.Duration.ofSeconds(10)).until(
    org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(
        By.xpath("(//table[@id='share_table']//tr[.//span[contains(@class,'glyphicons-wallet')]])[1]//span[contains(@class,'cogwheel-icon')]")))

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/Settings_Upload_Form'))

WebUI.delay(1)

String valueShownForFormWithDate = getDateTimePickerValue()

if (valueShownForFormWithDate.isEmpty()) {
    throw new Exception('Precondition failed: the upload form expected to carry a valid-till date shows none - cannot verify the carry-over bug')
}

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Close_uploadform_dialog'))

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Close'))

WebUI.click(findTestObject('Object Repository/Folders/Page_Folders - PowerFolder/lang_Folders'))

WebUI.refresh()

WebElement shareBtnB = findShareButton(folderWithoutDate)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(shareBtnB))

new org.openqa.selenium.support.ui.WebDriverWait(DriverFactory.getWebDriver(), java.time.Duration.ofSeconds(10)).until(
    org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(
        By.xpath("(//table[@id='share_table']//tr[.//span[contains(@class,'glyphicons-wallet')]])[1]//span[contains(@class,'cogwheel-icon')]")))

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/Settings_Upload_Form'))

WebUI.delay(1)

String valueShownForFormWithoutDate = getDateTimePickerValue()

WebUI.verifyEqual(valueShownForFormWithoutDate, '')

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Save'))

WebUI.delay(1)

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Close'))

WebUI.click(findTestObject('Object Repository/Folders/Page_Folders - PowerFolder/lang_Folders'))

WebUI.refresh()

WebElement shareBtnB2 = findShareButton(folderWithoutDate)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(shareBtnB2))

new org.openqa.selenium.support.ui.WebDriverWait(DriverFactory.getWebDriver(), java.time.Duration.ofSeconds(10)).until(
    org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(
        By.xpath("(//table[@id='share_table']//tr[.//span[contains(@class,'glyphicons-wallet')]])[1]//span[contains(@class,'cogwheel-icon')]")))

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/Settings_Upload_Form'))

WebUI.delay(1)

String valueAfterSave = getDateTimePickerValue()

WebUI.verifyEqual(valueAfterSave, '')

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Close_uploadform_dialog'))

WebUI.click(findTestObject('1Upload_Form/Page_Folders - PowerFolder/button_Close'))

WebUI.closeBrowser()

WebElement findShareButton(String folderName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath("//table[@id='files_files_table']/tbody/tr/td[2]/span/a[contains(text(),'$folderName')]/../../../td[7]/a"))
}

void setDateTimePickerValue(String value) {
    WebUI.executeJavaScript(
        "var el = document.getElementById('pica_uploadform_valid_till'); " +
        "el.value = arguments[0]; " +
        "el.dispatchEvent(new Event('change', { bubbles: true }));",
        [value])
}

String getDateTimePickerValue() {
    def result = WebUI.executeJavaScript(
        "return document.getElementById('pica_uploadform_valid_till').value;", [])
    return result == null ? '' : result.toString()
}

/** Returns today in datetime-local format: yyyy-MM-dd'T'HH:mm */
String generateDatetimeLocalToday() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
    return sdf.format(Calendar.getInstance().getTime())
}
