import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.By as By
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebElement as WebElement
import helpers.WindowTitle as WindowTitle
import organization.OrganizationName as OrganizationName

WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Organization/SelectOrganization'))

WindowTitle.verify('Organizations')

List<WebElement> rows = searchKatalonOrganizations()

int round = 0

while (!rows.isEmpty() && round < 10) {
    round++

    WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(selectionIcon(rows[0])))

    DriverFactory.getWebDriver().findElement(By.xpath("//table[@id='organizations_table']//th//span[@class='glyphicons glyphicons-unchecked pica-glyph']")).click()

    selectedOrganizationRows().findAll { !hasSuffix(it) }.each {
        WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(selectionIcon(it)))
    }

    List<WebElement> selected = selectedOrganizationRows()

    assert !selected.isEmpty() : 'No organizations are selected, nothing was deleted'

    assert selected.every { hasSuffix(it) } : 'Organizations without ' + OrganizationName.SUFFIX + ' are selected, nothing was deleted'

    WebUI.click(findTestObject('Organization/Deletebutton'))

    WebUI.click(findTestObject('Organization/SelectYesButton'))

    WebUI.delay(5)

    WebUI.refresh()

    WebUI.delay(3)

    rows = searchKatalonOrganizations()
}

assert rows.isEmpty() : 'Organizations with ' + OrganizationName.SUFFIX + ' are still present after deleting'

WebUI.closeBrowser()

List<WebElement> searchKatalonOrganizations() {
    WebUI.setText(findTestObject('Organization/inputSearch'), OrganizationName.SUFFIX)

    WebUI.sendKeys(findTestObject('Organization/inputSearch'), Keys.chord(Keys.ENTER))

    WebUI.delay(3)

    return DriverFactory.getWebDriver().findElements(By.xpath("//table[@id='organizations_table']/tbody/tr[@id]")).findAll {
        it.isDisplayed() && hasSuffix(it)
    }
}

List<WebElement> selectedOrganizationRows() {
    return DriverFactory.getWebDriver().findElements(By.xpath("//table[@id='organizations_table']/tbody/tr[contains(concat(' ', normalize-space(@class), ' '), ' info ')]"))
}

WebElement selectionIcon(WebElement row) {
    return row.findElement(By.xpath("./td[1]//*[contains(concat(' ',normalize-space(@class),' '),' pica-glyph ')]"))
}

boolean hasSuffix(WebElement row) {
    return row.findElements(By.xpath('./td[2]')).any {
        (it.getAttribute('textContent') ?: '').contains(OrganizationName.SUFFIX)
    }
}
