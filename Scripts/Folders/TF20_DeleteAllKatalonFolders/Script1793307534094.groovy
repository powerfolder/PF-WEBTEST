import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.By as By
import org.openqa.selenium.WebElement as WebElement
import helpers.WindowTitle as WindowTitle
import folder.FolderName as FolderName

WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.STOP_ON_FAILURE)

WindowTitle.verify('Folders')

List<WebElement> rows = searchKatalonFolders()

int round = 0

while (!rows.isEmpty() && round < 10) {
    round++

    WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(rows[0].findElement(By.xpath('./td[1]/span'))))

    DriverFactory.getWebDriver().findElement(By.xpath("//table[@id='files_files_table']//th//span[@class='glyphicons glyphicons-unchecked pica-glyph']")).click()

    List<WebElement> selected = selectedFolderRows()

    assert !selected.isEmpty() : 'No folders are selected, nothing was deleted'

    assert selected.every { hasSuffix(it) } : 'Folders without ' + FolderName.SUFFIX + ' are selected, nothing was deleted'

    WebUI.click(findTestObject('Folders/deleteFolder'))

    WebUI.click(findTestObject('Folders/yesButton_Delete'))

    WebUI.delay(30)

    WebUI.refresh()

    rows = searchKatalonFolders()
}

assert rows.isEmpty() : 'Folders with ' + FolderName.SUFFIX + ' are still present after deleting'

WebUI.closeBrowser()

List<WebElement> searchKatalonFolders() {
    WebUI.setText(findTestObject('Folders/inputSearch'), FolderName.SUFFIX)

    WebUI.delay(3)

    return DriverFactory.getWebDriver().findElements(By.xpath("//table[@id='files_files_table']/tbody/tr[@data-search-keys][not(contains(@class,'pica-table-selection-disabled'))]")).findAll {
        it.isDisplayed()
    }
}

List<WebElement> selectedFolderRows() {
    return DriverFactory.getWebDriver().findElements(By.xpath("//table[@id='files_files_table']/tbody/tr[contains(concat(' ', normalize-space(@class), ' '), ' info ')]"))
}

boolean hasSuffix(WebElement row) {
    return row.findElements(By.xpath('./td[2]//a')).any {
        (it.getAttribute('textContent') ?: '').contains(FolderName.SUFFIX)
    }
}
