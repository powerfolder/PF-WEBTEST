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
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import group.GroupFinder


WebUI.callTestCase(findTestCase('Shop/Pre_test/upgrade_monthly_6TB'), [:], FailureHandling.STOP_ON_FAILURE)

// --- Loop to create 5 users automatically ---
for (int i = 1; i <= 5; i++) {
    WebUI.comment("=== Creating user #$i ===")

    WebUI.click(findTestObject('LeftNavigationIcons/account'))

    WebUI.click(findTestObject('Accounts/CreateButton'))

    WebUI.click(findTestObject('Accounts/ClickCreateAccount'))

    String userName = generateRandomString(8)

    String userLastName = generateRandomString(8)

    String userEmail = generateRandomEmail().toLowerCase()

    WebUI.setText(findTestObject('Accounts/InputUserOrEmail'), userEmail)

    WebUI.setText(findTestObject('Accounts/InputPassword'), GlobalVariable.Pass)

    WebUI.setText(findTestObject('Accounts/InputFirstName'), userName)

    WebUI.setText(findTestObject('Accounts/InputLastName'), userLastName)

    // --- Set storage quota to 1TB (1024 GB) ---
    WebUI.setText(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/account_storage_overwiew'), '1024')

    WebUI.click(findTestObject('Accounts/SaveButton'))

    WebUI.delay(2)

    WebUI.refresh()

    WebUI.delay(2)

    WebUI.comment("✅ User $userEmail created successfully.")
}

WebUI.click(findTestObject('LeftNavigationIcons/account'))

WebUI.click(findTestObject('Accounts/CreateButton'))

WebUI.click(findTestObject('Accounts/ClickCreateAccount'))

String userName_1 = generateRandomString(8)

String userLastName_1 = generateRandomString(8)

String userEmail_1 = generateRandomEmail().toLowerCase()

WebUI.setText(findTestObject('Accounts/InputUserOrEmail'), userEmail_1)

WebUI.setText(findTestObject('Accounts/InputPassword'), GlobalVariable.Pass)

WebUI.setText(findTestObject('Accounts/InputFirstName'), userName_1)

WebUI.setText(findTestObject('Accounts/InputLastName'), userLastName_1)

// --- Set storage quota to 1TB (1024 GB) ---
WebUI.setText(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/account_storage_overwiew'), '1024')

WebUI.click(findTestObject('Accounts/SaveButton'))

WebUI.delay(1)

WebUI.verifyElementVisible(findTestObject('Page_PowerFolder - shop_stripe/Max users'), FailureHandling.STOP_ON_FAILURE)

String actualText = WebUI.getText(findTestObject('Page_PowerFolder - shop_stripe/Max users'))

WebUI.verifyMatch(actualText.trim(), 'Max users of organization reached.*×', true)

WebUI.delay(2)

WebElement element = CustomKeywords.'group.GroupFinder.findGroupSafe'(userEmail_1)

assert element == null : "❌ ERROR: A group exists for ${userEmail_1}"

WebUI.closeBrowser()


String generateRandomString(int length) {
    String characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'

    StringBuilder randomString = new StringBuilder()

    Random random = new Random()

    for (int i = 0; i < length; i++) {
        randomString.append(characters.charAt(random.nextInt(characters.length())))
    }
    
    return randomString.toString().toLowerCase()
}

String generateRandomEmail() {
    return generateRandomString(8) + '@yopmail.com'
}

String generateRandomPhoneNumber() {
    Random random = new Random()

    return String.format('(%03d) %03d-%04d', random.nextInt(1000), random.nextInt(1000), random.nextInt(10000))
}

