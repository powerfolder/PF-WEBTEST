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
import utils.PreferencesApi as PreferencesApi
import java.math.RoundingMode as RoundingMode

int registrationQuotaMb = PreferencesApi.getConfig('server.register.os.size') as int

WebUI.callTestCase(findTestCase('Registration/TR1_VerifyRegistrationTest'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/My_account'))

WebUI.verifyElementVisible(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Page_Profile - PowerFolder/lang_Overview'), 
    FailureHandling.STOP_ON_FAILURE)

WebUI.mouseOver(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Storage used'))

WebUI.delay(2)

def tooltipText = WebUI.getAttribute(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Storage used'), 'data-bs-content')

println('Tooltip content: ' + tooltipText)

WebUI.verifyMatch(tooltipText, '.*(?<![\\d.,])' + quotaPattern(registrationQuotaMb) + '\\b.*', true)

WebUI.delay(2)

WebUI.closeBrowser()

String quotaPattern(int quotaMb) {
    if (quotaMb < 1024) {
        return quotaMb + ' MB'
    }

    BigDecimal quotaGb = new BigDecimal(quotaMb).divide(new BigDecimal(1024), 1, RoundingMode.HALF_UP).stripTrailingZeros()

    return quotaGb.toPlainString().replace('.', '[.,]') + ' GB'
}

