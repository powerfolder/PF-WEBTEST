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

WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.OPTIONAL)

String folderName = 'Folder_' + getTimestamp()

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createFolder'))

WebUI.setText(findTestObject('Folders/inputFolderName'), folderName)

WebUI.click(findTestObject('Folders/buttonOK'))

assert WebUI.getWindowTitle().equals('Folders - PowerFolder')

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createDocument'))

String fileNameA = 'docA_' + getTimestamp()

WebUI.setText(findTestObject('Folders/inputFolderName'), fileNameA)

WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.closeWindowIndex(1)

WebUI.delay(1)

WebUI.switchToWindowIndex(0)

WebUI.refresh()

WebElement shareBtnA = findShareButton(fileNameA)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(shareBtnA))

WebUI.click(findTestObject('Folders/shareLink'))

WebUI.click(findTestObject('Folders/button_SaveSettings'))

WebUI.delay(1)

WebUI.click(findTestObject('Folders/Settings_Link'))

setDateTimePickerValue(generateDatetimeLocalPlusDays(10))

WebUI.click(findTestObject('Folders/button_SaveSettings'))

WebUI.delay(1)

WebUI.click(findTestObject('LinksTable/close links config button'))

WebUI.click(findTestObject('Folders/createFolderIcon'))

WebUI.click(findTestObject('Folders/createDocument'))

String fileNameB = 'docB_' + getTimestamp()

WebUI.setText(findTestObject('Folders/inputFolderName'), fileNameB)

WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.closeWindowIndex(1)

WebUI.delay(1)

WebUI.switchToWindowIndex(0)

WebUI.refresh()

WebElement shareBtnB = findShareButton(fileNameB)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(shareBtnB))

WebUI.click(findTestObject('Folders/shareLink'))

WebUI.click(findTestObject('Folders/button_SaveSettings'))

WebUI.delay(1)

WebUI.click(findTestObject('Folders/Settings_Link'))

String baselineValueB = getDateTimePickerValue()

if (baselineValueB.isEmpty()) {
    throw new Exception('Precondition failed: link B shows no valid-till date at all right after creation - cannot establish a baseline to compare against')
}

WebUI.click(findTestObject('LinksTable/Close link dialog'))

WebUI.click(findTestObject('LinksTable/close links config button'))

WebUI.refresh()

WebElement shareBtnA2 = findShareButton(fileNameA)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(shareBtnA2))

WebUI.click(findTestObject('Folders/Settings_Link'))

String valueShownForA = getDateTimePickerValue()

if (valueShownForA.isEmpty()) {
    throw new Exception('Precondition failed: the link expected to carry a valid-till date shows none')
}

WebUI.click(findTestObject('LinksTable/Close link dialog'))

WebUI.click(findTestObject('LinksTable/close links config button'))

WebUI.refresh()

WebElement shareBtnB2 = findShareButton(fileNameB)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(shareBtnB2))

WebUI.click(findTestObject('Folders/Settings_Link'))

String valueShownForB = getDateTimePickerValue()

WebUI.verifyEqual(valueShownForB, baselineValueB)

WebUI.click(findTestObject('Folders/button_SaveSettings'))

WebUI.delay(1)

WebUI.click(findTestObject('Folders/Settings_Link'))

String valueAfterSave = getDateTimePickerValue()

WebUI.verifyEqual(valueAfterSave, baselineValueB)

WebUI.click(findTestObject('LinksTable/Close link dialog'))

WebUI.click(findTestObject('LinksTable/close links config button'))

WebUI.closeBrowser()

String getTimestamp() {
    Date todaysDate = new Date()

    String formattedDate = todaysDate.format('dd_MMM_yyyy_hh_mm_ss')

    return formattedDate
}

WebElement findShareButton(String fileName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + fileName) + '\')]/td[7]/a/span'))
}

void setDateTimePickerValue(String value) {
    WebUI.executeJavaScript(
        "var el = document.getElementById('pica_link_valid_till'); " +
        "el.value = arguments[0]; " +
        "el.dispatchEvent(new Event('change', { bubbles: true }));",
        [value])
}

String getDateTimePickerValue() {
    def result = WebUI.executeJavaScript(
        "return document.getElementById('pica_link_valid_till').value;", [])
    return result == null ? '' : result.toString()
}

/** Returns today + given number of days in datetime-local format: yyyy-MM-dd'T'HH:mm */
String generateDatetimeLocalPlusDays(int days) {
    Calendar cal = Calendar.getInstance()
    cal.add(Calendar.DAY_OF_MONTH, days)
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
    return sdf.format(cal.getTime())
}
