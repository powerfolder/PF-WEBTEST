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
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import java.util.List as List
import group.GroupName as GroupName

// Appel du cas de test de connexion
WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)

// Création de deux groupes
String group_1 = 'Group_' + RandomStringUtils.randomNumeric(4)
String group_2 = 'Group_2_' + RandomStringUtils.randomNumeric(4)

WebUI.click(findTestObject('Object Repository/Groups/Page_Dashboard - PowerFolder/lang_Groups'))

// Création du premier groupe
WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/Create_group_button'))
group_1 = GroupName.withSuffix(group_1)
WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/input_Organizations_pica_group_name'), group_1)
WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/textarea_Organizations_pica_group_notes'), 'create group')
WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))
WebUI.delay(2)

// Création du deuxième groupe
WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/Create_group_button'))
group_2 = GroupName.withSuffix(group_2)
WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/input_Organizations_pica_group_name'), group_2)
WebUI.setText(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/textarea_Organizations_pica_group_notes'), 'create group')
WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/button_Save'))
WebUI.delay(2)

List<WebElement> rows = searchKatalonGroups()

int round = 0

while (!rows.isEmpty() && round < 10) {
    round++

    WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(selectionIcon(rows[0])))

    WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/select_all'))

    selectedGroupRows().findAll { !hasSuffix(it) }.each {
        WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(selectionIcon(it)))
    }

    List<WebElement> selected = selectedGroupRows()

    assert !selected.isEmpty() : 'No groups are selected, nothing was deleted'

    assert selected.every { hasSuffix(it) } : 'Groups without ' + GroupName.SUFFIX + ' are selected, nothing was deleted'

    WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/a_Delete'))

    WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Yes'))

    WebUI.delay(5)

    WebUI.refresh()

    WebUI.delay(3)

    rows = searchKatalonGroups()
}

assert rows.isEmpty() : 'Groups with ' + GroupName.SUFFIX + ' are still present after deleting'

WebUI.closeBrowser()

List<WebElement> searchKatalonGroups() {
    WebUI.setText(findTestObject('Groups/Search group'), GroupName.SUFFIX)

    WebUI.sendKeys(findTestObject('Groups/Search group'), Keys.chord(Keys.ENTER))

    WebUI.delay(3)

    return DriverFactory.getWebDriver().findElements(By.xpath("//table[@id='groups_table']/tbody/tr[@data-search-keys]")).findAll {
        it.isDisplayed() && hasSuffix(it)
    }
}

List<WebElement> selectedGroupRows() {
    return DriverFactory.getWebDriver().findElements(By.xpath("//table[@id='groups_table']/tbody/tr[contains(concat(' ', normalize-space(@class), ' '), ' info ')]"))
}

WebElement selectionIcon(WebElement row) {
    return row.findElement(By.xpath("./td[1]//*[contains(concat(' ',normalize-space(@class),' '),' pica-glyph ')]"))
}

boolean hasSuffix(WebElement row) {
    return row.findElements(By.xpath('./td[2]')).any {
        (it.getAttribute('textContent') ?: '').contains(GroupName.SUFFIX)
    }
}

