import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import org.apache.commons.lang3.RandomStringUtils
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import com.kms.katalon.core.webui.driver.DriverFactory
import tags.TagHelper as TagHelper
import subfoldersharing.InheritanceHelper as InheritanceHelper

/*
 * Subfolder Sharing / Tags (PFS-5911): tags of a shared subfolder whose inheritance is interrupted.
 *
 *   TopFolder  [tagTop]          -> not shared
 *     `-- Subfolder [tagA -> tagB] -> inheritance interrupted, shared with the member
 *
 * Admin: the tag search finds both folders by their tags.
 * Member: sees only the shared subfolder, with its tag, and finds only it - also after the admin
 *         replaced its tag (tagA -> tagB): then by the new tag only. The top folder's tag finds nothing.
 */
// create the member who gets the subfolder shared (also logs in as admin)
WebUI.callTestCase(findTestCase('Accounts/Edit_Account/pre_test/Create_Account'), [:], FailureHandling.STOP_ON_FAILURE)
String memberEmail = GlobalVariable.userEmail

WebUI.click(findTestObject('LeftNavigationIcons/folders'))

String tlfName = TagHelper.createWorkspace()
String subName = TagHelper.createSubfolder()

String tagTop = 'sfs46top' + RandomStringUtils.randomAlphanumeric(6).toLowerCase()
String tagA = 'sfs46a' + RandomStringUtils.randomAlphanumeric(6).toLowerCase()
String tagB = 'sfs46b' + RandomStringUtils.randomAlphanumeric(6).toLowerCase()

// --- Admin: interrupt the inheritance of the (currently open) subfolder before it is shared ---
InheritanceHelper.interruptInheritance(subName)

// tag the subfolder and the top folder
InheritanceHelper.openTopFolder(tlfName)
InheritanceHelper.editTags(subName, [], [tagA])
verifyChips(subName, [tagA])

TagHelper.backToFolderList()
InheritanceHelper.editTags(tlfName, [], [tagTop])
verifyChips(tlfName, [tagTop])

// share the subfolder with the member
InheritanceHelper.openTopFolder(tlfName)
TagHelper.openItem(subName)
InheritanceHelper.shareCurrentFolderWith(memberEmail)

// the admin finds both folders by their tags
verifyFoundByTag(tagTop, tlfName)
verifyFoundByTag(tagA, subName)

// --- Member: accepts the invitation, sees only the subfolder with its tag ---
loginAsMember(memberEmail)

TagHelper.clickItemNameLink(subName)
WebUI.verifyElementClickable(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))
WebUI.delay(2)

TagHelper.backToFolderList()
WebUI.refresh()
WebUI.delay(2)
verifyChips(subName, [tagA])
WebUI.verifyEqual(TagHelper.rowExistsEventually(tlfName, 3), false, FailureHandling.STOP_ON_FAILURE)

verifyFoundByTag(tagA, subName)
verifyNotFoundByTag(tagTop, tlfName)

// --- Admin: replaces the subfolder's tag ---
loginAsAdmin()

InheritanceHelper.openTopFolder(tlfName)
InheritanceHelper.editTags(subName, [tagA], [tagB])
verifyChips(subName, [tagB])

verifyFoundByTag(tagB, subName)
verifyNotFoundByTag(tagA, subName)
verifyFoundByTag(tagTop, tlfName)

// --- Member: sees the new tag and finds the subfolder by it only ---
loginAsMember(memberEmail)

WebUI.refresh()
WebUI.delay(2)
verifyChips(subName, [tagB])
WebUI.verifyEqual(TagHelper.rowExistsEventually(tlfName, 3), false, FailureHandling.STOP_ON_FAILURE)

verifyFoundByTag(tagB, subName)
verifyNotFoundByTag(tagA, subName)
verifyNotFoundByTag(tagTop, tlfName)

WebUI.closeBrowser()

/**
 * Verifies the tag chips shown on a row, independent of their order.
 */
void verifyChips(String itemName, List<String> expected) {
    List<String> actual = TagHelper.getChipTexts(itemName)
    WebUI.comment("Tags of '" + itemName + "': " + actual)
    WebUI.verifyEqual(actual.sort(), expected.sort(), FailureHandling.STOP_ON_FAILURE)
}

/**
 * Verifies that a tag search from the folder list finds the item (retries while the search catches up).
 */
void verifyFoundByTag(String tagText, String itemName) {
    TagHelper.backToFolderList()
    boolean found = TagHelper.searchForTagAndWaitForRow(tagText, itemName)
    WebUI.comment("Search 'tag:" + tagText + "' finds '" + itemName + "': " + found)
    if (!found) {
        logShownRows()
    }
    WebUI.verifyEqual(found, true, FailureHandling.STOP_ON_FAILURE)
}

/**
 * Verifies that a tag search from the folder list does not find the item (retries while an old tag is still
 * found, e.g. right after it was replaced).
 */
void verifyNotFoundByTag(String tagText, String itemName) {
    TagHelper.backToFolderList()
    long deadline = System.currentTimeMillis() + 20000
    boolean found = true
    while (found) {
        TagHelper.searchForTag(tagText)
        found = TagHelper.rowExistsEventually(itemName, 3)
        if (System.currentTimeMillis() >= deadline) {
            break
        }
    }
    WebUI.comment("Search 'tag:" + tagText + "' finds '" + itemName + "': " + found)
    WebUI.verifyEqual(found, false, FailureHandling.STOP_ON_FAILURE)
}

/**
 * Diagnostics: logs every row the current list shows, by its search keys and text.
 */
void logShownRows() {
    List<WebElement> rows = DriverFactory.getWebDriver().findElements(By.xpath("//*[@data-search-keys]"))
    WebUI.comment("Rows shown: " + rows.size())
    rows.each { WebElement row ->
        WebUI.comment("row: keys='" + row.getAttribute('data-search-keys') + "' displayed=" + row.isDisplayed()
            + " text='" + row.getText().replaceAll('\\s+', ' ').take(200) + "'")
    }
    List<WebElement> empty = DriverFactory.getWebDriver().findElements(By.xpath("//tr[contains(@class,'pica-table-empty')]"))
    empty.each { WebElement e -> WebUI.comment("empty message: '" + e.getText().trim() + "'") }
}

void loginAsMember(String email) {
    logout()
    WebUI.setText(findTestObject('Login/inputEmail'), email)
    WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)
    WebUI.click(findTestObject('Login/loginSubmit'))
    WebUI.delay(3)
    WebUI.click(findTestObject('LeftNavigationIcons/folders'))
    WebUI.delay(1)
}

void loginAsAdmin() {
    logout()
    WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)
    WebUI.click(findTestObject('LeftNavigationIcons/folders'))
    WebUI.delay(1)
}

void logout() {
    WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
    WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))
}
