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
 * Subfolder Sharing / Tags (PFS-5911): sharing a subfolder copies the directory's tags onto the subfolder.
 * If the directory is tagged again afterwards, that copy is stale. Interrupting the inheritance must take
 * the current tags of the directory, never the stale copy - in the admin's view as well as in the folder
 * list of the member, who sees the subfolder at top level.
 */
// create the member who gets the subfolder shared (also logs in as admin)
WebUI.callTestCase(findTestCase('Accounts/Edit_Account/pre_test/Create_Account'), [:], FailureHandling.STOP_ON_FAILURE)
String memberEmail = GlobalVariable.userEmail

WebUI.click(findTestObject('LeftNavigationIcons/folders'))

String tlfName = TagHelper.createWorkspace()
String subName = TagHelper.createSubfolder()

String tagOld = 'sfs44old' + RandomStringUtils.randomAlphanumeric(6).toLowerCase()
String tagNew = 'sfs44new' + RandomStringUtils.randomAlphanumeric(6).toLowerCase()

// tag the subfolder, then share it - sharing copies the tag onto the subfolder
InheritanceHelper.openTopFolder(tlfName)
InheritanceHelper.editTags(subName, [], [tagOld])

TagHelper.openItem(subName)
InheritanceHelper.shareCurrentFolderWith(memberEmail)

// retag the subfolder - the copy made by sharing is stale now
InheritanceHelper.openTopFolder(tlfName)
InheritanceHelper.editTags(subName, [tagOld], [tagNew])
verifyChips(subName, [tagNew])

// interrupt: the subfolder must carry the current tag, not the stale one
TagHelper.openItem(subName)
InheritanceHelper.interruptInheritance(subName)

InheritanceHelper.openTopFolder(tlfName)
verifyChips(subName, [tagNew])

TagHelper.backToFolderList()
WebUI.verifyEqual(TagHelper.searchForTagAndWaitForRow(tagNew, subName), true, FailureHandling.STOP_ON_FAILURE)
verifyNotFoundByTag(tagOld, subName)

// the member sees the subfolder at top level of their folder list, with the current tag
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))
WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/lang_Log out'))
WebUI.setText(findTestObject('Login/inputEmail'), memberEmail)
WebUI.setText(findTestObject('Login/inputPassword'), GlobalVariable.Pass)
WebUI.click(findTestObject('Login/loginSubmit'))
WebUI.delay(3)
WebUI.click(findTestObject('LeftNavigationIcons/folders'))

TagHelper.clickItemNameLink(subName)
WebUI.verifyElementClickable(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))
WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/accept_invitation'))
WebUI.delay(2)

TagHelper.backToFolderList()
WebUI.refresh()
WebUI.delay(2)
verifyChips(subName, [tagNew])

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
