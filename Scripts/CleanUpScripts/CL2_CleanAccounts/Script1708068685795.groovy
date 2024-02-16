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
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.OPTIONAL)
WebUI.click(findTestObject('LeftNavigationIcons/account'))
WebUI.setText(findTestObject('Accounts/inputAccountSearch'), 'TA')
WebDriver driver = DriverFactory.getWebDriver()
println("(//td/a[contains(text(),'F')]/ancestor::tr/td[1]/span)[1]")
WebElement accountHolder =  driver.findElement(By.xpath("(//td/a[contains(text(),'TA')]/ancestor::tr/td[1]/span)[1]"))
WebUI.delay(5)
Actions action = new Actions(driver)
action.moveToElement(accountHolder)
accountHolder.click()
WebUI.click(findTestObject('Folders/allFolder'))
WebUI.click(findTestObject('Accounts/accountDelete'))
WebUI.click(findTestObject('Folders/yesButton_Delete'))

WebUI.waitForAlert(20, FailureHandling.OPTIONAL)
WebUI.delay(10)

WebUI.closeBrowser()