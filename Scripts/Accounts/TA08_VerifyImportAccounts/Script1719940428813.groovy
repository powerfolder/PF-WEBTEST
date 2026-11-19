import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.Keys as Keys

List<String> emailIds = (1..3).collect { generateRandomEmail() }

File importFile = File.createTempFile('ta08_', '.csv')

importFile.text = 'username,server,registration date,last login date,last login from,pro user,booked size,booked size,total size,total size,archive size,folders,OS valid till,OS warned expired,OS disabled expired,OS warned usage,OS disabled usage,custom1,custom2,custom3,notes\n' +
    emailIds.collect { it + ',tspfdd05,,,,y,5 GB,5368709120,0 Bytes,0,0,0,,,,,,,,,\n' }.join('')

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.OPTIONAL)

WebUI.click(findTestObject('LeftNavigationIcons/account'))

WebUI.click(findTestObject('Accounts/CreateButton'))

WebUI.click(findTestObject('Accounts/ImportAccounts'))

WebUI.uploadFile(findTestObject('Accounts/AddFileButton'), importFile.getAbsolutePath())

WebUI.delay(4)

WebUI.click(findTestObject('Accounts/CloseButton'))

WebUI.refresh()

for (String emailId : emailIds) {
    WebUI.setText(findTestObject('Accounts/inputAccountSearch'), emailId)

    WebUI.sendKeys(findTestObject('Accounts/inputAccountSearch'), Keys.chord(Keys.ENTER))

    TestObject accountRow = new TestObject()

    accountRow.addProperty('xpath', ConditionType.EQUALS, "//table[@id='accounts_table']/tbody/tr[contains(@data-search-keys, '" + emailId + "')]")

    WebUI.verifyElementPresent(accountRow, 10)
}

importFile.delete()

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
    return generateRandomString(8) + '@qa-automated-webtest.com'
}

