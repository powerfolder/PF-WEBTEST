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

WebDriverWait wait = new WebDriverWait(DriverFactory.getWebDriver(), Duration.ofSeconds(5))

WebElement folderElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(('//div[@id=\'pica_group_folders\']/div[2]/table/tbody/tr/td[2][contains(text(), \'' +
			GlobalVariable.folderName) + '\')]')))

folderElement.click()

WebElement element = DriverFactory.getWebDriver().findElement(By.xpath('//*[@id="pica_group_folders"]/div[2]/table/thead[1]/tr/th[4]/div/a'))

WebUI.executeJavaScript('arguments[0].click();', Arrays.asList(element))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Yes'))

WebUI.delay(5)

WebUiBuiltInKeywords.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Save'))

WebElement btn = findGroup(GlobalVariable.GroupName)

WebUiBuiltInKeywords.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

WebUiBuiltInKeywords.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Folders'))

verifyNoElementWithFolderNamePresent('//*[@id="pica_group_folders"]/div[2]//*[contains(text(), \'' + GlobalVariable.folderName + '\')]')
 
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


