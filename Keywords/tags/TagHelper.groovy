package tags

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.apache.commons.lang3.RandomStringUtils
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

public class TagHelper {

    private static final String ROW_XPATH = "//*[contains(@data-search-keys, '%s')]"
    private static final String TAG_ADD_ICON = "//*[contains(@class,'pica-tag-add')]"
    private static final String TAG_CHIP = "//*[contains(@class,'pica-tag-chip') and not(contains(@class,'pica-tag-chip-more'))]"
    private static final String EDITOR_INPUT_CSS = ".pica-tageditor-wrapper.pica-tageditor-inline .pica-tageditor-input"
    private static final String EDITOR_SUGGESTION_CSS = ".pica-tageditor-wrapper.pica-tageditor-inline .pica-tageditor-suggestions .pica-tageditor-suggestion"
    private static final String ROW_MENU_TAGS_ITEM_CSS = "#files_files_table ul.conext-dropdown-menu a.files-ui-tags"
    private static final String ACTIONBAR_TAGS_ITEM_CSS = ".pica-table-selection-context a.files-ui-tags"
    private static final String TAG_FILTER_CONTAINER_ID = "pica_op_filter_chips"

    @Keyword
    static WebElement findRow(String itemName) {
        WebDriver driver = DriverFactory.getWebDriver()
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15))
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(ROW_XPATH, itemName))))
    }

    @Keyword
    static boolean rowExistsEventually(String itemName, int timeoutSeconds = 10) {
        WebDriver driver = DriverFactory.getWebDriver()
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(ROW_XPATH, itemName))))
            return true
        } catch (org.openqa.selenium.TimeoutException ignored) {
            return false
        }
    }

    @Keyword
    static void openItem(String itemName) {
        clickItemNameLink(itemName)
        waitForFolderView()
    }

    @Keyword
    static void clickItemNameLink(String itemName) {
        WebElement row = findRow(itemName)
        WebElement nameLink = row.findElement(By.xpath(".//a[contains(concat(' ',normalize-space(@class),' '),' pica-name ')]"))
        WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(nameLink))
    }

    private static void waitForFolderView() {
        WebUI.waitForElementPresent(
            findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'), 10)
    }

    @Keyword
    static void backToFolderList() {
        WebUI.click(findTestObject('LeftNavigationIcons/folders'))
        WebUI.delay(1)
    }

    @Keyword
    static boolean isTagIconPresent(String itemName) {
        List<WebElement> icons = findRow(itemName).findElements(By.xpath("." + TAG_ADD_ICON))
        return !icons.isEmpty()
    }

    @Keyword
    static boolean isTagsMenuEntryOfferedInRowMenu(String itemName) {
        openRowMenu(itemName)
        List<WebElement> items = DriverFactory.getWebDriver().findElements(By.cssSelector(ROW_MENU_TAGS_ITEM_CSS))
        boolean present = !items.isEmpty()
        WebUI.sendKeys(findTestObject('Folders/inputSearch'), Keys.chord(Keys.ESCAPE))
        return present
    }

    @Keyword
    static void openTagEditorViaIcon(String itemName) {
        WebElement icon = findRow(itemName).findElement(By.xpath("." + TAG_ADD_ICON))
        WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(icon))
        waitForEditor()
    }

    @Keyword
    static void openRowMenu(String itemName) {
        WebElement trigger = findRow(itemName).findElement(By.xpath("./td[6]/div/a/a"))
        WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(trigger))
        WebUI.delay(1)
    }

    @Keyword
    static void openTagEditorViaRowMenu(String itemName) {
        openRowMenu(itemName)
        WebDriver driver = DriverFactory.getWebDriver()
        WebElement tagsItem = driver.findElement(By.cssSelector(ROW_MENU_TAGS_ITEM_CSS))
        tagsItem.click()
        waitForEditor()
    }

    @Keyword
    static void selectRowCheckbox(String itemName) {
        WebElement icon = findRow(itemName).findElement(By.xpath("./td[1]/span"))
        WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(icon))
        WebUI.delay(1)
    }

    @Keyword
    static void openTagEditorViaActionbar(String itemName) {
        selectRowCheckbox(itemName)
        WebDriver driver = DriverFactory.getWebDriver()
        WebElement tagsItem = driver.findElement(By.cssSelector(ACTIONBAR_TAGS_ITEM_CSS))
        tagsItem.click()
        waitForEditor()
    }

    @Keyword
    static void typeTagText(String tagText) {
        WebElement input = editorInputElement()
        input.sendKeys(tagText)
        WebUI.delay(2)
    }

    @Keyword
    static void addTag(String tagText) {
        WebElement input = editorInputElement()
        input.sendKeys(tagText)
        input.sendKeys(Keys.ENTER)
    }

    @Keyword
    static void removeTag(String tagText) {
        WebDriver driver = DriverFactory.getWebDriver()
        WebElement chipX = driver.findElement(By.xpath(
            "//*[contains(@class,'pica-tageditor-chip')][.//*[contains(@class,'pica-tageditor-chip-label') and normalize-space(text())='" + tagText + "']]" +
            "//*[contains(@class,'pica-tageditor-chip-x')]"
        ))
        chipX.click()
    }

    @Keyword
    static void saveEditorViaEnter() {
        editorInputElement().sendKeys(Keys.ENTER)
    }

    @Keyword
    static void saveEditorViaOutsideClick() {
        WebUI.executeJavaScript(
            "document.body.dispatchEvent(new MouseEvent('mousedown', {bubbles: true, cancelable: true, view: window}));",
            null
        )
        WebUI.delay(1)
    }

    @Keyword
    static void cancelEditorViaEsc() {
        editorInputElement().sendKeys(Keys.ESCAPE)
    }

    @Keyword
    static void selectSuggestionByArrowDown() {
        editorInputElement().sendKeys(Keys.ARROW_DOWN)
    }

    @Keyword
    static void selectSuggestionByText(String expectedTag) {
        WebDriver driver = DriverFactory.getWebDriver()
        WebElement suggestion = driver.findElement(By.xpath(
            "//*[contains(concat(' ',normalize-space(@class),' '),' pica-tageditor-suggestion ') and normalize-space(text())='" + expectedTag + "']"
        ))
        suggestion.click()
    }

    @Keyword
    static List<String> getSuggestionTexts() {
        WebDriver driver = DriverFactory.getWebDriver()
        List<WebElement> items = driver.findElements(By.cssSelector(EDITOR_SUGGESTION_CSS))
        return items.collect { it.getText().trim() }
    }

    @Keyword
    static List<String> getChipTexts(String itemName) {
        List<WebElement> chips = findRow(itemName).findElements(By.xpath("." + TAG_CHIP))
        return chips.collect { it.getText().trim() }
    }

    @Keyword
    static List<String> getEditorChipTexts() {
        WebDriver driver = DriverFactory.getWebDriver()
        List<WebElement> labels = driver.findElements(
            By.cssSelector(".pica-tageditor-wrapper.pica-tageditor-inline .pica-tageditor-chip-label")
        )
        return labels.collect { it.getText().trim() }
    }

    @Keyword
    static void searchForTag(String tagText) {
        TestObject searchInput = findTestObject('Folders/inputSearch')
        String query = tagText.contains(' ') ? ('tag:"' + tagText + '"') : ('tag:' + tagText)
        WebUI.setText(searchInput, query)
        WebUI.sendKeys(searchInput, Keys.chord(Keys.ENTER))
        WebUI.delay(2)
    }

    @Keyword
    static boolean searchForTagAndWaitForRow(String tagText, String itemName, int timeoutSeconds = 20) {
        long deadline = System.currentTimeMillis() + (timeoutSeconds * 1000L)
        while (true) {
            searchForTag(tagText)
            if (rowExistsEventually(itemName, 3)) {
                return true
            }
            if (System.currentTimeMillis() >= deadline) {
                return false
            }
        }
    }

    @Keyword
    static void filterByTag(String tagText) {
        WebDriver driver = DriverFactory.getWebDriver()
        WebElement chip = driver.findElement(By.xpath(
            "//*[contains(@class,'pica-tag-chip') and not(contains(@class,'pica-tag-chip-more')) and normalize-space(text())='" + tagText + "']"
        ))
        chip.click()
        WebUI.delay(1)
    }

    @Keyword
    static boolean isTagFilterActive(String tagText, int timeoutSeconds = 8) {
        WebDriver driver = DriverFactory.getWebDriver()
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//*[@id='" + TAG_FILTER_CONTAINER_ID + "']//*[contains(@class,'pica-tag-chip') and contains(@class,'active') and contains(normalize-space(text()),'" + tagText + "')]"
            )))
            return true
        } catch (org.openqa.selenium.TimeoutException ignored) {
            return false
        }
    }

    @Keyword
    static boolean isTagFilterPersistedInStorage(String tagText) {
        Object raw = WebUI.executeJavaScript("return window.sessionStorage.getItem('searchFilters');", null)
        return raw != null && raw.toString().contains(tagText)
    }

    @Keyword
    static void clearTagFilterViaX(String tagText) {
        WebDriver driver = DriverFactory.getWebDriver()
        WebElement x = driver.findElement(By.xpath(
            "//*[@id='" + TAG_FILTER_CONTAINER_ID + "']//*[contains(@class,'pica-tag-chip') and contains(normalize-space(text()),'" + tagText + "')]" +
            "//*[contains(@class,'pica-tag-filter-x')]"
        ))
        x.click()
        WebUI.delay(1)
    }

    @Keyword
    static void clickResetFilterAndSearch() {
        WebDriver driver = DriverFactory.getWebDriver()
        WebElement link = driver.findElement(By.xpath(
            "//tr[contains(@class,'pica-table-empty')]//a[normalize-space(text())='Reset filter and search']"
        ))
        link.click()
        WebUI.delay(1)
    }

    @Keyword
    static String createWorkspace() {
        String name = 'TAG_' + RandomStringUtils.randomAlphanumeric(8)
        WebUI.click(findTestObject('Folders/createFolderIcon'))
        WebUI.click(findTestObject('Folders/createFolder'))
        WebUI.setText(findTestObject('Folders/inputFolderName'), name)
        WebUI.click(findTestObject('Folders/buttonOK'))
        WebUI.delay(1)
        return name
    }

    @Keyword
    static String createSubfolder() {
        String name = 'Sub_' + RandomStringUtils.randomAlphanumeric(8)
        WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
        WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_folder_insid_folder'))
        WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), name)
        WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))
        WebUI.delay(1)
        return name
    }

    @Keyword
    static String createDocumentInCurrentFolder() {
        String name = 'Doc_' + RandomStringUtils.randomAlphanumeric(8)
        WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'))
        WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_Document'))
        WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), name)
        WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'))
        WebUI.switchToWindowIndex('1')
        WebUI.verifyElementNotPresent(findTestObject('file_objects/document/Page_Open - PowerFolder/span_Unable to create document'), 3)
        WebUI.refresh()
        WebUI.delay(15)
        WebUI.closeWindowIndex(1)
        WebUI.switchToWindowIndex(0)
        WebUI.refresh()
        WebUI.delay(2)
        return name
    }

    private static WebElement editorInputElement() {
        WebDriver driver = DriverFactory.getWebDriver()
        return driver.findElement(By.cssSelector(EDITOR_INPUT_CSS))
    }

    private static void waitForEditor() {
        WebUI.waitForElementPresent(editorInputTestObject(), 5, FailureHandling.STOP_ON_FAILURE)
    }

    private static TestObject editorInputTestObject() {
        TestObject to = new TestObject('Tags_dynamic_editorInput')
        to.addProperty('css', com.kms.katalon.core.testobject.ConditionType.EQUALS, EDITOR_INPUT_CSS)
        return to
    }
}
