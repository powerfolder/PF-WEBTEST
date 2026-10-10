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
 * Subfolder Sharing / Tags (PFS-5911): a subfolder keeps its tags across interrupting and restoring the
 * permission inheritance. While it inherits, the tags live on the directory; while interrupted, on the
 * subfolder itself. Each switch hands the current tags over:
 *   - interrupt: the tags set before are still shown
 *   - tags edited while interrupted are shown and survive the restore
 *   - the tag search finds the subfolder by its current tag only
 */
WebUI.callTestCase(findTestCase('Login/Pretest - Admin Login'), [('variable') : ''], FailureHandling.STOP_ON_FAILURE)
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

String tlfName = TagHelper.createWorkspace()
String subName = TagHelper.createSubfolder()

String tagBefore = 'sfs43before' + RandomStringUtils.randomAlphanumeric(6).toLowerCase()
String tagDuring = 'sfs43during' + RandomStringUtils.randomAlphanumeric(6).toLowerCase()

// tag the subfolder while it still inherits
InheritanceHelper.openTopFolder(tlfName)
InheritanceHelper.editTags(subName, [], [tagBefore])
verifyChips(subName, [tagBefore])

// interrupt: the subfolder keeps the tags of the directory
TagHelper.openItem(subName)
InheritanceHelper.interruptInheritance(subName)

InheritanceHelper.openTopFolder(tlfName)
verifyChips(subName, [tagBefore])

// change the tags while interrupted
InheritanceHelper.editTags(subName, [tagBefore], [tagDuring])
verifyChips(subName, [tagDuring])

// restore: the tags set while interrupted are handed back to the directory
TagHelper.openItem(subName)
InheritanceHelper.restoreInheritance(subName)

InheritanceHelper.openTopFolder(tlfName)
verifyChips(subName, [tagDuring])

// tag search finds the subfolder by its current tag, not by the old one
TagHelper.backToFolderList()
WebUI.verifyEqual(TagHelper.searchForTagAndWaitForRow(tagDuring, subName), true, FailureHandling.STOP_ON_FAILURE)
verifyNotFoundByTag(tagBefore, subName)

WebUI.closeBrowser()

/**
 * Verifies the tag chips shown on a row, independent of their order.
 */
void verifyChips(String itemName, List<String> expected) {
    List<String> actual = TagHelper.getChipTexts(itemName)
    WebUI.comment("Tags of '" + itemName + "': " + actual)
    // diagnostics: which elements are counted as chips
    TagHelper.findRow(itemName).findElements(By.xpath(".//*[contains(@class,'pica-tag-chip')]")).each { WebElement el ->
        WebUI.comment("chip candidate: class='" + el.getAttribute('class') + "' displayed=" + el.isDisplayed() + " html=" + el.getAttribute('outerHTML').take(300))
    }
    WebUI.verifyEqual(actual.sort(), expected.sort(), FailureHandling.STOP_ON_FAILURE)
}

/**
 * Verifies that a tag search from the folder list does not find the item.
 */
void verifyNotFoundByTag(String tagText, String itemName) {
    TagHelper.backToFolderList()
    TagHelper.searchForTag(tagText)
    WebUI.verifyEqual(TagHelper.rowExistsEventually(itemName, 3), false, FailureHandling.STOP_ON_FAILURE)
}
