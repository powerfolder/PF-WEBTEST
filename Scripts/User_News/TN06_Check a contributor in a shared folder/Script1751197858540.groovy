import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static helpers.Helper.getRandomFolderName
import static helpers.Helper.findShareButton
import static helpers.Helper.getMembersCount
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


WebUI.callTestCase(findTestCase('User_News/Pre_test/create_admin_file'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/lang_Home'))

println(GlobalVariable.folderName)

folderName = GlobalVariable.folderName

WebElement btn = findShareButton(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

println(GlobalVariable.userEmail)

mail = GlobalVariable.userEmail

WebUI.setText(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/inputEmail_Share'), mail)

WebUI.sendKeys(findTestObject('Object Repository/Share/Page_Folders - PowerFolder/inputEmail_Share'), Keys.chord(Keys.ENTER))

WebUI.delay(3)

WebUI.click(findTestObject('News_User/Page_Folders - PowerFolder/button_Close_share_buttom'))

WebUI.delay(2)

println(GlobalVariable.userEmail)

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

WebUI.setText(findTestObject('Login/inputEmail'), GlobalVariable.userEmail)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(3)

WebUI.click(findTestObject('News_User/Page_Folders - PowerFolder/manage_invitation'))

WebUI.click(findTestObject('News_User/Page_Folders - PowerFolder/button_invitation_Accept'))


WebUI.click(findTestObject('News_User/Page_News - PowerFolder/News'))

WebUI.mouseOver(findTestObject('News_User/Page_News - PowerFolder/file_wpath'))

String tooltipText = WebUI.getAttribute(findTestObject('News_User/Page_News - PowerFolder/file_wpath'), 'data-original-title')

println('Tooltip content: ' + tooltipText)

String expectedAdminEmail = GlobalVariable.Username

String actualEmail = tooltipText.substring(tooltipText.lastIndexOf(' ') + 1).trim()

println("Email from tooltip: " + actualEmail)
println("Expected admin email: " + expectedAdminEmail)

// Comparaison
assert actualEmail == expectedAdminEmail : "❌ Email mismatch in tooltip."

println("✅ Tooltip shows the correct admin email.")

WebUI.delay(3)

WebUI.click(findTestObject('Object Repository/Groups/Page_Folders - PowerFolder/lang_Folders'))

WebElement btn1 = findFolder(folderName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

println(GlobalVariable.Document)

WebElement btn2 = findDoc(GlobalVariable.Document)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn2))

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/span_Rename'),
	FailureHandling.STOP_ON_FAILURE)

String DocRename = ('Doc_num_' + RandomStringUtils.randomNumeric(4))

WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'),
	DocRename)

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))


WebUI.click(findTestObject('News_User/Page_News - PowerFolder/News'))

WebUI.mouseOver(findTestObject('News_User/Page_News - PowerFolder/file_wpath'))

String tooltipText_2 = WebUI.getAttribute(findTestObject('News_User/Page_News - PowerFolder/file_wpath'), 'data-original-title')

println('Tooltip content: ' + tooltipText_2)

String expectedcontributorEmail = GlobalVariable.userEmail

String actualcontributorEmail = tooltipText_2.substring(tooltipText.lastIndexOf(' ') + 1).trim()

println("Email from tooltip: " + actualcontributorEmail)
println("Expected contributor email: " + expectedcontributorEmail)

// Comparaison

assert actualcontributorEmail == expectedcontributorEmail : "❌ Email mismatch in tooltip."

println("✅ Tooltip shows the correct contributor email.")

WebUI.delay(3)

WebUI.closeBrowser()





/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

WebElement findShareButton(String fileName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//table[@id=\'files_files_table\']/tbody/tr/td[2]/span/a[contains(text(),\'' + 
            fileName) + '\')]/../../../td[7]/a'))
}

WebElement findDoc(String DocRename) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + DocRename) + '\')]/td[1]/span'))
}

WebElement findFolder(String folderName) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(By.xpath(('//td[2]/span/a[contains(text(),\'' + folderName) + '\')]'))
}


