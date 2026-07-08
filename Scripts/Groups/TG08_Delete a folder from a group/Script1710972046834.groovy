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
import java.time.Duration


WebUI.callTestCase(findTestCase('Groups/Pre_test/add folder'), [:], FailureHandling.STOP_ON_FAILURE)

println('Global Variable: ' + GlobalVariable.folderName)

println('Global Variable: ' + GlobalVariable.GroupName)

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Folders'))

WebDriver pageDriver = DriverFactory.getWebDriver()

WebDriverWait wait = new WebDriverWait(pageDriver, Duration.ofSeconds(15))

WebElement folderElement = wait.until(
    ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//div[@id='pica_group_folders']//table//tr[@data-userdata and .//*[contains(normalize-space(.), '" + GlobalVariable.folderName + "')]]//td[contains(concat(' ',normalize-space(@class),' '),' pica-name ')]")
    )
)

WebUI.executeJavaScript('arguments[0].scrollIntoView({block: "center"});', Arrays.asList(folderElement))
WebUI.delay(1)
WebUI.executeJavaScript('arguments[0].click();', Arrays.asList(folderElement))

WebElement element = pageDriver.findElement(By.xpath("//div[@id='pica_group_folders']//a[contains(concat(' ',normalize-space(@class),' '),' pica-inputlist-remove ')]"))

WebUI.executeJavaScript('arguments[0].click();', Arrays.asList(element))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Yes'))

WebUI.delay(2)

WebUiBuiltInKeywords.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.delay(2)

String groupIconXpath = "//table[@id='groups_table']//tr[.//a[contains(concat(' ',normalize-space(@class),' '),' pica-name ') and contains(normalize-space(text()),'" + GlobalVariable.GroupName + "')]]/td[1]//*[contains(concat(' ',normalize-space(@class),' '),' pica-glyph ')]"

new WebDriverWait(DriverFactory.getWebDriver(), Duration.ofSeconds(15)).until(
    ExpectedConditions.elementToBeClickable(By.xpath(groupIconXpath)))

WebElement rowIcon = DriverFactory.getWebDriver().findElement(By.xpath(groupIconXpath))
WebUI.executeJavaScript('arguments[0].click();', Arrays.asList(rowIcon))

new WebDriverWait(DriverFactory.getWebDriver(), Duration.ofSeconds(10)).until(
    ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(concat(' ',normalize-space(@class),' '),' pica-table-selection-multi ') and contains(concat(' ',normalize-space(@class),' '),' groups_edit ')]")))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Edit_m'))

new WebDriverWait(DriverFactory.getWebDriver(), Duration.ofSeconds(15)).until(
    ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='pica_group_dialog' and contains(concat(' ',normalize-space(@class),' '),' show ')]")))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Folders'))

verifyNoElementWithFolderNamePresent(
  "//td[contains(., '" + GlobalVariable.folderName + "')]"
)
 
WebUI.closeBrowser() 


@Keyword
WebElement findGroup(String Groupname) {
	WebDriver driver = DriverFactory.getWebDriver()

	return driver.findElement(By.xpath(('//td/a[contains(text(), \'' + GlobalVariable.GroupName) + '\')]'))
}

@Keyword
void verifyNoElementWithFolderNamePresent(String folderNameXpath) {
	WebDriver driver = DriverFactory.getWebDriver()
	List<WebElement> elements = driver.findElements(By.xpath(folderNameXpath))
	
	if (elements.size() == 0) {
		println("No element containing folder name '${GlobalVariable.folderName}' found.")
	} else {
		println("Elements containing folder name '${GlobalVariable.folderName}' found. Verification failed.")
	}
}


