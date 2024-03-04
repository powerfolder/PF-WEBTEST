import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import org.openqa.selenium.WebDriver

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import com.kms.katalon.core.webui.driver.DriverFactory

import java.util.List

@Keyword
public List<WebElement> findStorage() {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElements(By.xpath("//td[1]/span"))
}

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Storage/Page_Dashboard - PowerFolder/td_Storage'))

findStorage().each({ WebElement element ->
    WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(element))
})

WebUI.click(findTestObject('Object Repository/Storage/Page_Storage - PowerFolder/a_Delete'))

WebUI.click(findTestObject('Object Repository/Storage/Page_Storage - PowerFolder/button_Yes'))
WebUI.delay(2)
WebUI.closeBrowser()
