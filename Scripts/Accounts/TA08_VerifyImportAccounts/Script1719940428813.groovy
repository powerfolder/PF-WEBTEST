import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

String firstName = generateRandomString(8)

String lastName = generateRandomString(8)

String emailId = generateRandomEmail()

Emailid = emailId.toLowerCase()

String phone = generateRandomPhoneNumber()

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.OPTIONAL)

WebUI.click(findTestObject('LeftNavigationIcons/account'))

WebUI.click(findTestObject('Accounts/CreateButton'))

WebUI.click(findTestObject('Accounts/ImportAccounts'))

String projDir = RunConfiguration.getProjectDir()

String fname = projDir + '/Data Files/ImportAccountFile.csv'

WebUI.uploadFile(findTestObject('Accounts/AddFileButton'), fname)

WebUI.delay(4)

WebUI.click(findTestObject('Accounts/CloseButton'))

WebUI.click(findTestObject('Accounts/SelectCheckBox'))

WebUI.click(findTestObject('Accounts/SelectAllAccountCheckBox'))

WebUI.delay(4)

WebUI.click(findTestObject('Accounts/DeleteButton'))

WebUI.click(findTestObject('Accounts/YesButton'))

WebUI.closeBrowser()

String generateRandomString(int length) {
    String characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'

    StringBuilder randomString = new StringBuilder()

    Random random = new Random()

    for (int i = 0; i < length; i++) {
        randomString.append(characters.charAt(random.nextInt(characters.length())))
    }
    
    return randomString.toString()
}

String generateRandomEmail() {
    return generateRandomString(8) + '@yopmail.com'
}

String generateRandomPhoneNumber() {
    Random random = new Random()

    return String.format('(%03d) %03d-%04d', random.nextInt(1000), random.nextInt(1000), random.nextInt(10000))
}

