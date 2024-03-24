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
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import com.kms.katalon.core.annotation.Keyword;
import com.kms.katalon.core.webui.driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
@Keyword
public WebElement findGroup(String groupName) {
	WebDriver driver = DriverFactory.getWebDriver();
	return driver.findElement(By.xpath("//*[contains(@data-search-keys, '" + groupName + "')]/td[1]/span"));
}

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

String groupName = 'Group_' + RandomStringUtils.randomNumeric(4)

GlobalVariable.GroupName = groupName

WebUI.click(findTestObject('Object Repository/Groups/Page_Dashboard - PowerFolder/lang_Groups'))

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/span_Log out_pica-icon pica-glyph glyphicon_0824b5'))

WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/input_Organizations_pica_group_name'), 
    GlobalVariable.GroupName)

WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/textarea_Organizations_pica_group_notes'), 
    'create group')

WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))

def btn = findGroup(groupName)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

assert groupName != null


