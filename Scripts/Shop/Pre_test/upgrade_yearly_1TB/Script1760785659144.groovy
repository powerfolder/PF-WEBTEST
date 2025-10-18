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
import com.kms.katalon.core.testobject.ConditionType as ConditionType

// go to shop
WebUI.callTestCase(findTestCase('Registration/TR1_VerifyRegistrationTest'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementClickable(findTestObject('External links/Page_Folders - PowerFolder/user_label_shop'))

WebUI.click(findTestObject('External links/Page_Folders - PowerFolder/user_label_shop'))

WebUI.click(findTestObject('External links/Page_Pricing - PowerFolder/Cloud 1 TB_yearly'))

WebUI.delay(2)

String currentUrl = WebUI.getUrl()

WebUI.comment('L\'URL actuelle est: ' + currentUrl)

boolean isCorrectUrl = currentUrl.contains('https://shop.powerfolder.com/c/pay/')

WebUI.verifyEqual(isCorrectUrl, true)

WebUI.delay(5)

// we are in stripe now checkout card as payment method
WebUI.executeJavaScript('document.querySelector(\'[data-testid="card-accordion-item-button"]\').click()', [])

// enter card and account info
WebUI.click(findTestObject('Page_PowerFolder - shop_stripe/input_Kartendaten_cardNumber'))

WebUI.delay(5)

WebUI.setText(findTestObject('Page_PowerFolder - shop_stripe/input_Kartendaten_cardNumber'), GlobalVariable.CreditcardNumber)

WebUI.click(findTestObject('Page_PowerFolder - shop_stripe/input_Kartendaten_cardExpiry'))

WebUI.setText(findTestObject('Page_PowerFolder - shop_stripe/input_Kartendaten_cardExpiry'), '0229')

WebUI.click(findTestObject('Page_PowerFolder - shop_stripe/input_Kartendaten_cardCvc'))

WebUI.setText(findTestObject('Page_PowerFolder - shop_stripe/input_Kartendaten_cardCvc'), '123')

WebUI.click(findTestObject('Page_PowerFolder - shop_stripe/input_Name desder Karteninhaberin_billingName'))

WebUI.setText(findTestObject('Page_PowerFolder - shop_stripe/input_Name desder Karteninhaberin_billingName'), 'TSH03 Testcheckout')

//WebUI.click(findTestObject('Page_PowerFolder - shop_stripe/select_Country'))

//WebUI.selectOptionByLabel(findTestObject('Page_PowerFolder - shop_stripe/select_Country'), 'Germany', false)

WebUI.delay(2)

WebUI.click(findTestObject('Page_PowerFolder - shop_stripe/span_Rechnungsadresse_Button-textCheckoutSecondary Text Text-color--gray400 Text-fontWeight--500 Text--truncate'))

WebUI.delay(2)

WebUI.click(findTestObject('Page_PowerFolder - shop_stripe/input_Rechnungsadresse_billingAddressLine1'))

WebUI.delay(2)

WebUI.setText(findTestObject('Page_PowerFolder - shop_stripe/input_Rechnungsadresse_billingAddressLine1'), 'Rheinpromenade 4a')

WebUI.click(findTestObject('Page_PowerFolder - shop_stripe/input_Rechnungsadresse_billingPostalCode'))

WebUI.delay(2)

WebUI.setText(findTestObject('Page_PowerFolder - shop_stripe/input_Rechnungsadresse_billingPostalCode'), '40789')

WebUI.click(findTestObject('Page_PowerFolder - shop_stripe/input_Rechnungsadresse_billingLocality'))

WebUI.delay(2)

WebUI.setText(findTestObject('Page_PowerFolder - shop_stripe/input_Rechnungsadresse_billingLocality'), 'Monheim am Rhein')

WebUI.delay(2)

WebUI.click(findTestObject('Page_PowerFolder - shop_stripe/div_Zahlungspflichtig abonnieren_SubmitButton-IconContainer'))

// high delay needed so stripe call back is done in background
WebUI.delay(120)

//Verify that the storage is 1 TB.
WebUI.verifyElementText(findTestObject('Page_PowerFolder - shop_stripe/Cloud 1 TB_Current_y'), 'Current')




