import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.annotation.Keyword as Keyword
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
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.testobject.ConditionType as ConditionType

// Call the test case Create Folder
WebUI.callTestCase(findTestCase('User_News/Pre_test/create_user_file'), [:], FailureHandling.STOP_ON_FAILURE)

println(GlobalVariable.folderName)
println(GlobalVariable.Document)


String folderName = GlobalVariable.folderName

String docName = GlobalVariable.Document


WebUI.click(findTestObject('News_User/Page_News - PowerFolder/News'))

WebUI.verifyElementPresent(findTestObject('News_User/Page_News - PowerFolder/deleted'), 5)

WebUI.click(findTestObject('News_User/Page_News - PowerFolder/deleted'))

WebUI.verifyElementText(findTestObject('News_User/Page_News - PowerFolder/nothing_to_show'), 'Nothing to show')

WebUI.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

def btn = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUI.delay(3)

def btn1 = findDoc(docName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

WebUI.verifyElementClickable(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/span_Delete'));

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/span_Delete'));

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/button_Yes'));

WebUI.delay(5);

// Vérifier que DocName n'est plus présent dans le tableau après la suppression
List<WebElement> items = findElementsInXPath("//*[@id='files_files_table']/tbody//span[contains(text(), '" + docName + "')]");
assert items.isEmpty();

WebUI.click(findTestObject('News_User/Page_News - PowerFolder/News'))

TestObject update = findTestObject('News_User/Page_News - PowerFolder/updated a file')

WebUI.waitForElementVisible(update, 10)

WebUI.verifyElementText(update, 'deleted a file')

WebUI.click(findTestObject('News_User/Page_News - PowerFolder/deleted'))

assert isFolderPresent(folderName)

// Dynamic object for the "deleted" activity counter
TestObject deletedActivity = new TestObject('deleted activity counter')

deletedActivity.addProperty('xpath', ConditionType.EQUALS, '//div[contains(@class,\'pica-chart-legend-item\')][contains(normalize-space(.),\'Deleted\')]')

WebUI.waitForElementVisible(deletedActivity, 10)

String deletedText = WebUI.getText(deletedActivity).trim()

println("Activity overview value: $deletedText")

WebUI.verifyMatch(deletedText, '.*Deleted\\s*:\\s*1.*', true, FailureHandling.STOP_ON_FAILURE)

WebUI.closeBrowser()

boolean isFolderPresent(String folderName) {
	WebDriver driver = DriverFactory.getWebDriver()

	String xpath = ('//a[contains(@class,\'pf-path-link\') and normalize-space(text())=\'' + folderName) + '\']'

	return driver.findElements(By.xpath(xpath)).size() > 0
}


@Keyword
WebElement findFolder(String folderName) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(By.xpath(('//td[2]/span/a[contains(text(),\'' + folderName) + '\')]'))
}

@Keyword
WebElement findDoc(String DocName) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + DocName) + '\')]/td[1]/span'))
}

List<WebElement> findElementsInXPath(String xpath) {
	WebDriver driver = DriverFactory.getWebDriver();
	return driver.findElements(By.xpath(xpath));
}
