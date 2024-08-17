import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import org.openqa.selenium.WebDriver as WebDriver
import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import java.util.List as List

WebUI.callTestCase(findTestCase('Storages/PreTest/CreateTwoStorages'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.delay(2)

findStorage().each({ WebElement element ->
        WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(element))
    })

WebUI.click(findTestObject('Object Repository/Storage/Page_Storage - PowerFolder/a_Delete'))

WebUI.click(findTestObject('Storage/Page_Storage - PowerFolder/lang_Yes'))

WebUI.delay(2)

WebUI.closeBrowser()

@Keyword
List<WebElement> findStorage() {
    WebDriver driver = DriverFactory.getWebDriver()

    return driver.findElements(By.xpath('//td[1]/span'))
}

