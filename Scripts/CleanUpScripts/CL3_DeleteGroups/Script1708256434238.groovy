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
import org.openqa.selenium.interactions.Actions
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.util.KeywordUtil
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject as TestObject
WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.OPTIONAL)
WebUI.waitForElementClickable(findTestObject('LeftNavigationIcons/groups'), 30, FailureHandling.CONTINUE_ON_FAILURE)
WebUI.click(findTestObject('LeftNavigationIcons/groups'))
TestObject group = findTestObject('Groups/groupRows')
List<WebElement> groups = WebUI.findWebElements(group, 10)
int size = groups.size()
if(size>3) {
	WebDriver driver = DriverFactory.getWebDriver()
	WebElement firstRow = driver.findElement(By.xpath("(//table[@id='groups_table']//tr//span[@class='glyphicons glyphicons-group pica-glyph'])[1]"))
	Actions action = new Actions(driver)
	action.moveToElement(firstRow)
	firstRow.click()
	driver.findElement(By.xpath("//table[@id='groups_table']//th//span[@class='glyphicons glyphicons-unchecked pica-glyph']")).click()
	WebUI.click(findTestObject('Object Repository/deleteButton'))

	WebUI.click(findTestObject('Folders/yesButton_Delete'))
	WebUI.delay(30)
	WebUI.waitForAlert(20, FailureHandling.OPTIONAL)
}
WebUI.closeBrowser()
