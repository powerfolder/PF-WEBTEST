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
import org.openqa.selenium.By as By
import org.openqa.selenium.WebElement as WebElement

WebUI.callTestCase(findTestCase('My Account/Pre_test/change avatar'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementClickable(findTestObject('My_Account/Overview/Page_Profile - PowerFolder/Delete_avatar'))

WebUI.click(findTestObject('My_Account/Overview/Page_Profile - PowerFolder/Delete_avatar'))

WebUI.verifyElementClickable(findTestObject('My_Account/Overview/Page_Profile - PowerFolder/button_Yes_delete'))

WebUI.click(findTestObject('My_Account/Overview/Page_Profile - PowerFolder/button_Yes_delete'))

TestObject imageElement = findTestObject('My_Account/Page_Profile - PowerFolder/profile')

WebUI.waitForElementVisible(imageElement, 10)

// Get the text of the element
String elementText = WebUI.getText(imageElement)

// Check if the element is empty
if (elementText.trim().isEmpty()) {
    println('The image has been successfully deleted.')

    WebUI.comment('The image has been successfully deleted.')
} else {
    println('The image has not been deleted correctly.')

    WebUI.comment('The image has not been deleted correctly.')
}

WebUI.closeBrowser(FailureHandling.STOP_ON_FAILURE)

