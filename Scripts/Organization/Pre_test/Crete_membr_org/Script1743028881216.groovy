import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

import java.text.SimpleDateFormat
import java.util.Arrays
import java.util.Calendar
import java.util.Date
import java.util.Random

import org.apache.commons.lang3.RandomStringUtils
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.OPTIONAL)

GlobalVariable.userName = generateRandomString(8)
GlobalVariable.userLastName = generateRandomString(8)
GlobalVariable.userEmail = generateRandomEmail().toLowerCase()
GlobalVariable.PhoneNumber = generateRandomPhoneNumber()

WebUI.click(findTestObject('LeftNavigationIcons/account'))
WebUI.click(findTestObject('Accounts/CreateButton'))
WebUI.click(findTestObject('Accounts/ClickCreateAccount'))

WebUI.setText(findTestObject('Accounts/InputUserOrEmail'), GlobalVariable.userEmail)
WebUI.setText(findTestObject('Accounts/InputPassword'), GlobalVariable.Pass)
// WebUI.setText(findTestObject('Accounts/InputFirstName'), GlobalVariable.userName)
// WebUI.setText(findTestObject('Accounts/InputLastName'), GlobalVariable.userLastName)
WebUI.setText(findTestObject('Accounts/InputPhoneNo'), GlobalVariable.PhoneNumber)
WebUI.setText(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/account_storage_overwiew'), '5')

WebUI.click(findTestObject('Accounts/SaveButton'))
WebUI.refresh()
WebUI.delay(2)

WebUI.click(findTestObject('Organization/SelectOrganization'))
WebUI.click(findTestObject('Organization/DropDownToggle'))
WebUI.click(findTestObject('Organization/CreateOrganization'))

GlobalVariable.organisationName = 'Organisation_' + RandomStringUtils.randomNumeric(4)
String organizationName = GlobalVariable.organisationName

WebUI.setText(findTestObject('Organization/InputName'), organizationName)
WebUI.setText(findTestObject('Organization/InputMaxNumber'), '10')
WebUI.setText(findTestObject('Organization/Page_Organizations - PowerFolder/organization_storage'), '5')

TestObject validFromInput = findTestObject('Organization/InputValidFrom')
TestObject validTillInput = findTestObject('Organization/InputValidtill')

String fromDateTime = generateDateTimePlusOneMinute()
String tillDateTime = generateDateTimePlusOneYear()

println("FromDateTime = " + fromDateTime)
println("TillDateTime = " + tillDateTime)

WebUI.executeJavaScript("""
    arguments[0].value = arguments[1];
    arguments[0].dispatchEvent(new Event('input', { bubbles: true }));
    arguments[0].dispatchEvent(new Event('change', { bubbles: true }));
""", [WebUI.findWebElement(validFromInput), fromDateTime])

WebUI.delay(1)

WebUI.executeJavaScript("""
    arguments[0].value = arguments[1];
    arguments[0].dispatchEvent(new Event('input', { bubbles: true }));
    arguments[0].dispatchEvent(new Event('change', { bubbles: true }));
""", [WebUI.findWebElement(validTillInput), tillDateTime])

WebUI.delay(1)

WebUI.setText(findTestObject('Organization/EnterNotes'), 'AutomationNotes')
WebUI.click(findTestObject('Organization/SaveButton'))

WebUI.refresh()
WebUI.delay(3)

WebElement btn = findORG(organizationName)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn))

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

String generateRandomPhoneNumber() {
    Random random = new Random()
    return String.format('(%03d) %03d-%04d', random.nextInt(1000), random.nextInt(1000), random.nextInt(10000))
}

String getRandomFolderName() {
    return 'Folder' + getTimestamp()
}

String getTimestamp() {
    Date todaysDate = new Date()
    return todaysDate.format('dd_MMMyyyyhhmmss')
}

String generateDateTimePlusOneMinute() {
    Calendar calendar = Calendar.getInstance()
    calendar.add(Calendar.MINUTE, 1)

    SimpleDateFormat sdf = new SimpleDateFormat('MM/dd/yyyy hh:mm a')
    return sdf.format(calendar.getTime())
}

String generateDateTimePlusOneYear() {
    Calendar calendar = Calendar.getInstance()
    calendar.add(Calendar.YEAR, 1)

    SimpleDateFormat sdf = new SimpleDateFormat('MM/dd/yyyy hh:mm a')
    return sdf.format(calendar.getTime())
}

@Keyword
WebElement findORG(String organizationName) {
    WebDriver driver = DriverFactory.getWebDriver()
    return driver.findElement(By.xpath("//a[contains(text(),'${organizationName}')]"))
}
