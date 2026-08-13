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

// Tags feature (PFS-5306). All locators below were derived from a static read of
// files.js / picasso.css (PF-PRO, branch develop_27) - not from an executed Katalon
// run against a live app, since no Katalon runtime was available in this session.
// The tag name is never present as an HTML attribute (only as chip text / a "title"
// attribute - see files.js buildTagChips/wireTagEditor), so every "does tag X exist"
// check below matches on visible text. Everything is centralised here on purpose:
// if a class name drifts after the first real run, fix it in ONE place instead of
// in 31 scripts.
public class TagHelper {

    private static final String ROW_XPATH = "//*[contains(@data-search-keys, '%s')]"
    private static final String TAG_ADD_ICON = "//*[contains(@class,'pica-tag-add')]"
    private static final String TAG_CHIP = "//*[contains(@class,'pica-tag-chip') and not(contains(@class,'pica-tag-chip-more'))]"
    private static final String EDITOR_INPUT_CSS = ".pica-tageditor-wrapper.pica-tageditor-inline .pica-tageditor-input"
    private static final String EDITOR_SUGGESTION_CSS = ".pica-tageditor-wrapper.pica-tageditor-inline .pica-tageditor-suggestions .pica-tageditor-suggestion"
    private static final String ROW_MENU_TAGS_ITEM_CSS = "#files_files_table ul.conext-dropdown-menu a.files-ui-tags"
    private static final String ACTIONBAR_TAGS_ITEM_CSS = ".pica-table-selection-context a.files-ui-tags"
    // PFS-5653 replaced the separate tag-filter state with a generic search-operator
    // system (Picasso.SearchFilters in search_autocomplete.js); the active-filter chip
    // bar's id changed from "pica_tag_filter_chips" to "pica_op_filter_chips", though
    // the chip/x classes themselves (pica-tag-chip / pica-tag-filter-x) were reused as-is.
    private static final String TAG_FILTER_CONTAINER_ID = "pica_op_filter_chips"

    // ------------------------------------------------------------------
    // Row lookup (same data-search-keys convention as Keywords/file/FileFinder.groovy)
    // ------------------------------------------------------------------

    // The file/folder table is rendered client-side via AJAX (Picasso), so a row that
    // was just created/navigated to may not exist in the DOM yet at the instant this
    // is called - unlike Katalon's built-in WebUI.* keywords, a raw driver.findElement
    // does NOT retry. Poll for it instead of failing immediately.
    @Keyword
    static WebElement findRow(String itemName) {
        WebDriver driver = DriverFactory.getWebDriver()
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15))
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format(ROW_XPATH, itemName))))
    }

    // For use in test scripts' own assertions instead of a raw, non-retrying
    // driver.findElements(...).isEmpty() check (e.g. right after a search or filter) -
    // fixes the same class of timing failure as findRow()'s wait, for callers that
    // build their own xpath rather than going through findRow().
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

    // Opens (navigates into) a folder/workspace row. IMPORTANT: the icon in td[1] is
    // the SELECTION toggle (see selectRowCheckbox() below / Keywords/helpers/helper.groovy
    // selectUploadedFolders(), which clicks this exact icon to select rows) - it does
    // NOT navigate. The real "open" target is the name link with class "pica-name"
    // (confirmed in Scripts/Subfoldersharing/SFS04.../*.groovy). Clicking the wrong one
    // was the root cause of every "openItem() didn't actually navigate" failure in the
    // first real test run (TAG06-10, TAG16-20, TAG22, TAG24-27).
    @Keyword
    static void openItem(String itemName) {
        clickItemNameLink(itemName)
        waitForFolderView()
    }

    // Just the click, without waitForFolderView()'s postcondition (which waits for a
    // write-permission-only control) - use this for a read-only invitee accepting a
    // pending invitation, where the caller's own next wait (e.g. for the
    // accept_invitation button) is the correct postcondition instead.
    @Keyword
    static void clickItemNameLink(String itemName) {
        WebElement row = findRow(itemName)
        WebElement nameLink = row.findElement(By.xpath(".//a[contains(concat(' ',normalize-space(@class),' '),' pica-name ')]"))
        WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(nameLink))
    }

    // Confirms navigation into a folder/workspace actually completed, by waiting for a
    // control that only exists inside a folder view, instead of returning immediately
    // and letting the caller race the page's AJAX update.
    private static void waitForFolderView() {
        WebUI.waitForElementPresent(
            findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'), 10)
    }

    // Creating a workspace or a subfolder navigates straight INTO it (confirmed against
    // the live app - see Scripts/Subfoldersharing/SFS04.../*.groovy comments and the
    // TAG01 failure this was added to fix). Call this to return to the top-level
    // Folders list before treating the just-created item as a ROW (e.g. to tag the
    // workspace/subfolder itself rather than something created inside it).
    @Keyword
    static void backToFolderList() {
        WebUI.click(findTestObject('LeftNavigationIcons/folders'))
        WebUI.delay(1)
    }

    // ------------------------------------------------------------------
    // Add affordance / permission visibility
    // ------------------------------------------------------------------

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

    // ------------------------------------------------------------------
    // Opening the inline tag editor - three entry points
    // ------------------------------------------------------------------

    @Keyword
    static void openTagEditorViaIcon(String itemName) {
        WebElement icon = findRow(itemName).findElement(By.xpath("." + TAG_ADD_ICON))
        // the icon is only shown via CSS ":hover" on the row; a JS click bypasses the
        // native-hover requirement, same convention as Helper.findManageButton's caller
        WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(icon))
        waitForEditor()
    }

    @Keyword
    static void openRowMenu(String itemName) {
        // 6th column holds the row's "..." menu trigger (see the existing Delete/Rename
        // row-menu flow in Keywords/helpers/helper.groovy / TF24 for the same td[6]/div/a/a shape)
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

    // There is no native <input type="checkbox"> in this table - a row is selected by
    // clicking its td[1] icon (confirmed in Keywords/helpers/helper.groovy
    // selectUploadedFolders(), which uses this exact xpath to select rows via
    // shift-click). This was previously confused with openItem()'s target, which is
    // why row selection failed in the first real test run (TAG03, TAG13, TAG30).
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

    // ------------------------------------------------------------------
    // Editor interaction (keyboard semantics per files.js wireTagEditor)
    // ------------------------------------------------------------------

    @Keyword
    static void typeTagText(String tagText) {
        WebElement input = editorInputElement()
        input.sendKeys(tagText)
        // suggestTags() is debounced 300ms client-side (files.js) before the XHR fires;
        // 2s to leave headroom for the round trip under load
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

    // "Click outside saves" is implemented as a document-level MOUSEDOWN listener
    // (files.js wireTagEditor: $(document).on("mousedown.tagsinline", ...)), not a
    // click listener. document.body.click() only synthesizes a "click" event, which
    // never fires "mousedown" - the save handler was never triggered, so the typed
    // tag was silently dropped (root cause of the second real test run's TAG23
    // failure). Dispatch an actual MouseEvent("mousedown") instead. A native Selenium
    // click on <body> was tried first but throws ElementNotInteractableException
    // ("zero size") when <body> has no rendered height/width of its own (all content
    // sits in absolutely/flex-positioned wrapper divs) - the first real test run's
    // TAG23 failure - so this also sidesteps that geometry requirement.
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
    static List<String> getSuggestionTexts() {
        WebDriver driver = DriverFactory.getWebDriver()
        List<WebElement> items = driver.findElements(By.cssSelector(EDITOR_SUGGESTION_CSS))
        return items.collect { it.getText().trim() }
    }

    // Polls the already-open suggestion dropdown rather than re-typing (typeTagText()
    // appends via sendKeys, so calling it again here would corrupt the query) - covers
    // any suggestion round-trip lag beyond typeTagText()'s own debounce delay.
    @Keyword
    static boolean suggestionsEventuallyContain(String expectedTag, int timeoutSeconds = 8) {
        long deadline = System.currentTimeMillis() + (timeoutSeconds * 1000L)
        while (true) {
            if (getSuggestionTexts().contains(expectedTag)) {
                return true
            }
            if (System.currentTimeMillis() >= deadline) {
                return false
            }
            WebUI.delay(1)
        }
    }

    // ------------------------------------------------------------------
    // Read-only chip inspection (row not in edit mode)
    // ------------------------------------------------------------------

    @Keyword
    static List<String> getChipTexts(String itemName) {
        List<WebElement> chips = findRow(itemName).findElements(By.xpath("." + TAG_CHIP))
        return chips.collect { it.getText().trim() }
    }

    // Chips inside the currently-open inline editor (edit-mode chips), as opposed to
    // getChipTexts() which reads a row's read-only display chips.
    @Keyword
    static List<String> getEditorChipTexts() {
        WebDriver driver = DriverFactory.getWebDriver()
        List<WebElement> labels = driver.findElements(
            By.cssSelector(".pica-tageditor-wrapper.pica-tageditor-inline .pica-tageditor-chip-label")
        )
        return labels.collect { it.getText().trim() }
    }

    // ------------------------------------------------------------------
    // Search (reuses the existing Folders/inputSearch object)
    // ------------------------------------------------------------------

    // A bare-word query (no operator) was expected to recurse into files/subfolders and
    // match the Lucene "tags" field too (confirmed by reading FileInfoCriteriaFactory/
    // GetAllAction/LuceneIndexManager) - but manual verification against the live app
    // showed it does NOT reliably surface tagged files/subfolders in practice, while the
    // explicit "tag:" operator (query=tag:value, confirmed live via the network tab) does.
    // Use the operator explicitly rather than relying on the bare-word path.
    @Keyword
    static void searchForTag(String tagText) {
        TestObject searchInput = findTestObject('Folders/inputSearch')
        String query = tagText.contains(' ') ? ('tag:"' + tagText + '"') : ('tag:' + tagText)
        WebUI.setText(searchInput, query)
        WebUI.sendKeys(searchInput, Keys.chord(Keys.ENTER))
        WebUI.delay(2)
    }

    // A file/subfolder's tag is matched via the async Lucene index (LuceneIndexManager),
    // unlike a workspace's own tag (a direct FolderInfo field, visible to search
    // immediately) - reindexing can lag a few seconds behind the save. Re-issues the
    // search every ~2s until the row shows up or the timeout elapses, instead of
    // searching once and hoping the index has already caught up.
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

    // ------------------------------------------------------------------
    // Filtering by clicking a tag chip
    // ------------------------------------------------------------------

    @Keyword
    static void filterByTag(String tagText) {
        WebDriver driver = DriverFactory.getWebDriver()
        WebElement chip = driver.findElement(By.xpath(
            "//*[contains(@class,'pica-tag-chip') and not(contains(@class,'pica-tag-chip-more')) and normalize-space(text())='" + tagText + "']"
        ))
        chip.click()
        WebUI.delay(1)
    }

    // Retries rather than a one-shot check: after F5, restoreFilters() needs a moment
    // to read sessionStorage and re-render the chip bar before it's present in the DOM.
    @Keyword
    static boolean isTagFilterActive(String tagText, int timeoutSeconds = 8) {
        WebDriver driver = DriverFactory.getWebDriver()
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "//*[@id='" + TAG_FILTER_CONTAINER_ID + "']//*[contains(@class,'pica-tag-chip') and contains(@class,'active') and normalize-space(text())='" + tagText + "']"
            )))
            return true
        } catch (org.openqa.selenium.TimeoutException ignored) {
            return false
        }
    }

    @Keyword
    static void clearTagFilterViaX(String tagText) {
        WebDriver driver = DriverFactory.getWebDriver()
        WebElement x = driver.findElement(By.xpath(
            "//*[@id='" + TAG_FILTER_CONTAINER_ID + "']//*[contains(@class,'pica-tag-chip') and normalize-space(text())='" + tagText + "']" +
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

    // ------------------------------------------------------------------
    // Fixture helpers (top folder / subfolder / document creation)
    // ------------------------------------------------------------------

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

    // Assumes the browser is currently positioned inside the parent folder/workspace.
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

    // Assumes the browser is currently positioned inside the parent folder/workspace.
    // Mirrors Scripts/File/Pre_test/Create_Doc.groovy, minus its own nested workspace
    // creation, so the document lands inside whichever folder the caller already opened.
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
