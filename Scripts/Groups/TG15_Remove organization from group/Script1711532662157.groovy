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

WebUI.callTestCase(findTestCase('Groups/Pre_test/add organization'), [:], FailureHandling.STOP_ON_FAILURE)

println('Global Variable: ' + GlobalVariable.organisationName)

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUI.click(findTestObject('Page_Groups - PowerFolder/a_Organisationen'))

// Wait for organizations inputlist to finish loading before selecting the row
new org.openqa.selenium.support.ui.WebDriverWait(DriverFactory.getWebDriver(), java.time.Duration.ofSeconds(15)).until(
    org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(
        By.xpath("//div[@id='pica_group_organizations']//table//tr[@data-userdata]")))

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/td_Organisation'))

WebUI.delay(1)

// Remove action lives in the table's "selection" thead (visible once a row is selected) — anchored on .pica-inputlist-remove from input-list.js
WebElement element = DriverFactory.getWebDriver().findElement(By.xpath("//div[@id='pica_group_organizations']//a[contains(concat(' ',normalize-space(@class),' '),' pica-inputlist-remove ')]"))

WebUI.executeJavaScript('arguments[0].click();', Arrays.asList(element))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Yes'))

WebUI.delay(3)

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.verifyElementPresent(findTestObject('Groups/Page_Groups - PowerFolder/div_Group updated'), 2)

WebUI.delay(2)

WebElement btn1 = findGroup(GlobalVariable.GroupName)

WebUiBuiltInKeywords.executeJavaScript('arguments[0].click()', Arrays.asList(btn1))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUI.click(findTestObject('Page_Groups - PowerFolder/a_Organisationen'))

// Wait until the organizations inputlist has finished its AJAX load —
// either a data row appears, or the "Nothing to show" empty marker. Otherwise the verify could fire while the table is briefly empty.
new org.openqa.selenium.support.ui.WebDriverWait(DriverFactory.getWebDriver(), java.time.Duration.ofSeconds(15)).until(
    org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(
        By.xpath("//div[@id='pica_group_organizations']//table//tr[@data-userdata] | //div[@id='pica_group_organizations']//tr[contains(concat(' ',normalize-space(@class),' '),' pica-table-empty ')]")))

verifyNoElementWithorganizationNamePresent("//div[@id='pica_group_organizations']//tr[@data-userdata][.//*[contains(normalize-space(.), '" + GlobalVariable.organisationName + "')]]")

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.delay(1)

WebUI.closeBrowser()

@Keyword
WebElement findGroup(String groupName) {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + GlobalVariable.GroupName) + '\')]/td[1]/span'))
}

@Keyword
void verifyNoElementWithorganizationNamePresent(String userNameXpath) {
    WebDriver driver = DriverFactory.getWebDriver()

    List<WebElement> elements = driver.findElements(By.xpath(userNameXpath))

    if (elements.size() == 0) {
        println("No element containing organization name '${GlobalVariable.organisationName}' found — remove succeeded.")
    } else {
        WebUiBuiltInKeywords.comment("Organization '${GlobalVariable.organisationName}' still present after remove — verification failed.")
        assert false : "Organization '${GlobalVariable.organisationName}' still present in pica_group_organizations after remove."
    }
}

