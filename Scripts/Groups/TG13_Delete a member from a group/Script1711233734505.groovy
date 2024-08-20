import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberBuiltinKeywords
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGBuiltinKeywords
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as WindowsBuiltinKeywords
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import java.util.Arrays as Arrays
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

// Call the pre-test case to add a member
WebUI.callTestCase(findTestCase('Groups/Pre_test/add member'), [:], FailureHandling.STOP_ON_FAILURE)

// Navigate to the edit group page
WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

// Delay to ensure the page is fully loaded
WebUI.delay(2)

// Navigate to the members page
WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Members'))

// Delay to ensure the page is fully loaded
WebUI.delay(2)

// Initialize explicit wait
WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), 10)

try {
    // Build XPath for the added user
    String userXPath = ('//*[@id=\'pica_group_accounts\']//div[2]/table/tbody/tr/td[2][contains(text(), \'' + GlobalVariable.userName) + 
    '\')]'

    // Wait until the user element is visible
    WebElement userElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(userXPath)))

    // Click on the user element
    userElement.click()

    // Wait for the delete button to be clickable and perform click using JavaScript
    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath('//*[@id="pica_group_accounts"]/div[2]/table/thead[1]/tr/th[3]/div/a')))

    WebUI.executeJavaScript('arguments[0].click();', Arrays.asList(element))

    // Click the Yes button to confirm deletion
    WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Yes'))

    // Delay to ensure the action is completed
    WebUI.delay(3)

    // Click the Save button
    WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))

    // Verify the group is updated
    WebUI.verifyElementPresent(findTestObject('Groups/Page_Groups - PowerFolder/div_Group updated'), 1)
	
	WebUI.refresh()

    // Delay to ensure the action is completed
    WebUI.delay(3)

    // Find the group and click to edit members
    WebElement btn1 = findGroup(GlobalVariable.GroupName)

    WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

    // Click to edit members
    WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

    WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Members'))

    // Verify no element with the user name is present
    verifyNoElementWithuserNamePresent(('//*[@id="pica_group_accounts"]/div[2]//*[contains(text(), \'' + GlobalVariable.userName) + 
        '\')]')

    WebUI.delay(2)

    // Click the Save button
    WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))
}
catch (Exception e) {
    println('Error: ' + e.getMessage())

    WebUI.takeScreenshot()
} 
// Optionally, take a screenshot or additional steps for debugging
finally { 
    // Close the browser
    WebUI.closeBrowser()
}

@Keyword
WebElement findGroup(String groupName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + groupName) + '\')]/td[1]/span'))
}

@Keyword
void verifyNoElementWithuserNamePresent(String userNameXpath) {
    WebDriver driver = DriverFactory.getWebDriver()

    List elements = driver.findElements(By.xpath(userNameXpath))

    if (elements.size() == 0) {
        println(('No element containing user name \'' + GlobalVariable.userName) + '\' found.')
    } else {
        println(('Elements containing user name \'' + GlobalVariable.userName) + '\' found. Verification failed.')
    }
}

