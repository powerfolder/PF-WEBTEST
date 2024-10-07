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
import org.apache.commons.lang.RandomStringUtils as RandomStringUtils
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType


WebUI.callTestCase(findTestCase('My Account/Pre_test/Create Account'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/My_account'))

WebUI.verifyElementClickable(findTestObject('My_Account/Overview/Page_Profile - PowerFolder/Change_Password'))

WebUI.click(findTestObject('My_Account/Overview/Page_Profile - PowerFolder/Change_Password'))

WebUI.setText(findTestObject('My_Account/Overview/Page_Profile - PowerFolder/Set_Old_Password'), GlobalVariable.Pass)

String characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_-+=<>?'

String NewPasswordWithSymbols = RandomStringUtils.random(12, characters.toCharArray())

println(NewPasswordWithSymbols)

WebUI.setText(findTestObject('My_Account/Overview/Page_Profile - PowerFolder/Set_New_Password'), NewPasswordWithSymbols)

WebUI.setText(findTestObject('My_Account/Overview/Page_Profile - PowerFolder/Confirme_New_Password'), NewPasswordWithSymbols)

WebUI.delay(2)

// Cliquez directement sur l'élément en utilisant XPath
TestObject btn_change = new TestObject().addProperty('xpath', ConditionType.EQUALS, '//body/div[2]/div[1]/div[2]/div[5]/div/div/div[3]/button[1]')
WebUI.click(btn_change)

//TestObject btn_cancel = new TestObject().addProperty('xpath', ConditionType.EQUALS, '//body/div[2]/div[1]/div[2]/div[5]/div/div/div[3]/button[2]')
//WebUI.click(btn_cancel)

WebUI.delay(3)

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))

String userEmail = GlobalVariable.userEmail

println(userEmail)

WebUI.setText(findTestObject('Login/inputEmail'), userEmail)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.setText(findTestObject('Login/inputPassword'), NewPasswordWithSymbols)

WebUI.click(findTestObject('Login/loginSubmit'))

WebUI.delay(3)

WebUI.closeBrowser()

