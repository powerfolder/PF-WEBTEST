import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static org.apache.commons.lang.StringUtils.isNotBlank
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
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor

WebUI.callTestCase(findTestCase('CleanUpScripts/CL3_DeleteGroups'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.OPTIONAL)

WebUI.waitForElementClickable(findTestObject('LeftNavigationIcons/groups'), 30, FailureHandling.CONTINUE_ON_FAILURE)

WebUI.click(findTestObject('LeftNavigationIcons/groups'))

WebUI.click(findTestObject('Groups/createGroup'))

String groupName = getRandomGroupName()

WebUI.setText(findTestObject('Groups/inputGroupName'), groupName)

WebUI.click(findTestObject('Groups/folderTab'))

WebUI.setText(findTestObject('Groups/inputFolderName'), 'Folder')

WebDriver driver = DriverFactory.getWebDriver()

for (int i = 1; i <= 1; i++) {
    WebUI.setText(findTestObject('Groups/inputFolderName'), 'Folder')

    String xpath = ('(//ul[@class=\'pica-taginput-dropdown dropdown-menu\']//a)[' + i) + ']'

    println(xpath)

    Thread.sleep(3000)

    WebElement folder = driver.findElement(By.xpath(xpath))

  JavascriptExecutor executor = (JavascriptExecutor) driver;

    executor.executeScript('arguments[0].click();', folder) //folder.click()
}

WebUI.click(findTestObject('Groups/buttonSave'))

WebElement grpName = driver.findElement(By.xpath("//a[@class='pica-name'  and text () ='$groupName']"))

WebUI.verifyEqual(grpName.isDisplayed(), true)

WebUI.closeBrowser()

String getRandomGroupName() {
    String folderName = 'G_' + getTimestamp()

    return folderName
}

String getTimestamp() {
    Date todaysDate = new Date()

    String formattedDate = todaysDate.format('ddMMMyyyyhhmmss')

    return formattedDate
}

