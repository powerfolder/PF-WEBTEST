import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import org.apache.commons.lang3.RandomStringUtils
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import java.util.Arrays
import com.kms.katalon.core.webui.driver.DriverFactory

WebUI.callTestCase(findTestCase('Groups/Pre_test/add member'), [:], FailureHandling.STOP_ON_FAILURE)
String memberEmail = GlobalVariable.userName
String groupName = GlobalVariable.GroupName

WebUI.click(findTestObject('LeftNavigationIcons/folders'))

String tlfName = 'TF19_' + RandomStringUtils.randomAlphanumeric(6)

WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))
WebUI.verifyElementClickable(findTestObject('Folders/resetInput'), FailureHandling.CONTINUE_ON_FAILURE)
WebUI.setText(findTestObject('Folders/inputFolderName'), tlfName)
WebUI.click(findTestObject('Folders/buttonOK'))

TestObject tlfPresent = new TestObject()
tlfPresent.addProperty('xpath', ConditionType.EQUALS, "//*[contains(@data-search-keys, '" + tlfName + "')]/td[1]/span")

WebUI.click(findTestObject('Links/share_icon_inside_folder'))
WebUI.setText(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), groupName)
WebUI.delay(5)
WebUI.sendKeys(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), Keys.chord(Keys.ENTER))
WebUI.verifyElementText(findTestObject('Share/Page_Folders - PowerFolder/td_Group'), groupName)
WebUI.click(findTestObject('Share/close_button_folder_share_mail'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))
WebUI.setText(findTestObject('Login/inputEmail'), memberEmail)
WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)
WebUI.click(findTestObject('Login/loginSubmit'))
WebUI.delay(3)
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

WebUI.verifyElementPresent(tlfPresent, 10)

WebElement memberTlfRow = findFolder(tlfName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(memberTlfRow))
WebUI.verifyElementPresent(findTestObject('Folders/createFolderIcon'), 10)

WebDriver driver = DriverFactory.getWebDriver()

WebUI.click(findTestObject('Object Repository/Groups/Page_Dashboard - PowerFolder/lang_Groups'))
WebUI.delay(3)
WebUI.setText(findTestObject('Groups/Search group'), groupName)
WebUI.delay(2)

String rowMenuXpath = "//table[@id='groups_table']/tbody/tr[contains(@data-search-keys,'" + groupName + "')]//a[@role='button' and contains(@class,'dropdown-toggle')]"
new WebDriverWait(driver, Duration.ofSeconds(15)).until(
    ExpectedConditions.presenceOfElementLocated(By.xpath(rowMenuXpath)))
WebElement rowMenuButton = driver.findElement(By.xpath(rowMenuXpath))
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(rowMenuButton))

String leaveLinkXpath = "//table[@id='groups_table']/tbody/tr[contains(@data-search-keys,'" + groupName + "')]//a[contains(@class,'groups_leave')]"
new WebDriverWait(driver, Duration.ofSeconds(10)).until(
    ExpectedConditions.elementToBeClickable(By.xpath(leaveLinkXpath)))
WebElement leaveLink = driver.findElement(By.xpath(leaveLinkXpath))
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(leaveLink))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Yes'))
WebUI.delay(2)

TestObject warningNotification = new TestObject()
warningNotification.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'pica-notification') and contains(@class,'warning')]")
WebUI.verifyElementNotPresent(warningNotification, 3)

new WebDriverWait(driver, Duration.ofSeconds(10)).until(
    ExpectedConditions.invisibilityOfElementLocated(By.xpath("//table[@id='groups_table']/tbody/tr[contains(@data-search-keys,'" + groupName + "')]")))

WebElement foldersMenuLink = driver.findElement(By.xpath("//a[@id='folders-menu']"))
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(foldersMenuLink))
WebUI.verifyElementNotPresent(tlfPresent, 10)

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))
WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

WebElement adminTlfRow = findFolder(tlfName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(adminTlfRow))
WebUI.click(findTestObject('Links/share_icon_inside_folder'))
WebUI.verifyElementText(findTestObject('Share/Page_Folders - PowerFolder/td_Group'), groupName)
WebUI.click(findTestObject('Share/close_button_folder_share_mail'))

WebUI.closeBrowser()

WebElement findFolder(String name) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//td[2]/span/a[contains(text(),'" + name + "')]"))
}
