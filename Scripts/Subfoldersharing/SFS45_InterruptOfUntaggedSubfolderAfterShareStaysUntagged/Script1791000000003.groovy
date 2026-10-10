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

WebUI.callTestCase(findTestCase('Accounts/Edit_Account/pre_test/Create_Account'), [:], FailureHandling.STOP_ON_FAILURE)
String memberEmail = GlobalVariable.userEmail

WebUI.click(findTestObject('LeftNavigationIcons/folders'))

String tlfName = TagHelper.createWorkspace()
String subName = TagHelper.createSubfolder()

String tagOld = 'sfs45old' + RandomStringUtils.randomAlphanumeric(6).toLowerCase()

InheritanceHelper.openTopFolder(tlfName)
InheritanceHelper.editTags(subName, [], [tagOld])

TagHelper.openItem(subName)
InheritanceHelper.shareCurrentFolderWith(memberEmail)

InheritanceHelper.openTopFolder(tlfName)
InheritanceHelper.editTags(subName, [tagOld], [])
verifyChips(subName, [])

TagHelper.openItem(subName)
InheritanceHelper.interruptInheritance(subName)

InheritanceHelper.openTopFolder(tlfName)
verifyChips(subName, [])

verifyNotFoundByTag(tagOld, subName)

WebUI.closeBrowser()

void verifyChips(String itemName, List<String> expected) {
    List<String> actual = TagHelper.getChipTexts(itemName)
    WebUI.verifyEqual(actual.sort(), expected.sort(), FailureHandling.STOP_ON_FAILURE)
}

void verifyNotFoundByTag(String tagText, String itemName) {
    TagHelper.backToFolderList()
    TagHelper.searchForTag(tagText)
    WebUI.verifyEqual(TagHelper.rowExistsEventually(itemName, 3), false, FailureHandling.STOP_ON_FAILURE)
}
