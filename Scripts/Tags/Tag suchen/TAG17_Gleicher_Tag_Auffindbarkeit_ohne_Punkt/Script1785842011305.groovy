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
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils
import java.util.Arrays as Arrays
import tags.TagHelper as TagHelper

WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.STOP_ON_FAILURE)

String workspaceName = TagHelper.createWorkspace()
String tagText = 'TAG17_' + RandomStringUtils.randomAlphanumeric(6) + ''

// Tag am Arbeitsbereich selbst (dazu erst zurueck zur Liste, die ihn als Zeile zeigt)
TagHelper.backToFolderList()
TagHelper.openTagEditorViaIcon(workspaceName)
TagHelper.addTag(tagText)
TagHelper.saveEditorViaEnter()
WebUI.refresh()
WebUI.delay(2)

TagHelper.openItem(workspaceName)
String subfolderName = TagHelper.createSubfolder()

TagHelper.backToFolderList()
TagHelper.openItem(workspaceName)
TagHelper.openTagEditorViaIcon(subfolderName)
TagHelper.addTag(tagText)
TagHelper.saveEditorViaEnter()
WebUI.refresh()
WebUI.delay(2)

TagHelper.openItem(subfolderName)
String docName = TagHelper.createDocumentInCurrentFolder()
TagHelper.openTagEditorViaIcon(docName)

// Vorschlagsliste: der bereits verwendete Tag soll vorgeschlagen werden
TagHelper.typeTagText(tagText.substring(0, Math.max(0, tagText.length() - 3)))
List<String> suggestions = TagHelper.getSuggestionTexts()
assert suggestions.contains(tagText)
TagHelper.selectSuggestionByArrowDown()
TagHelper.saveEditorViaEnter()
WebUI.refresh()
WebUI.delay(2)

TagHelper.backToFolderList()
TagHelper.searchForTag(tagText)

WebDriver driver = DriverFactory.getWebDriver()
assert !driver.findElements(By.xpath("//*[contains(@data-search-keys, '" + workspaceName + "')]")).isEmpty()
assert !driver.findElements(By.xpath("//*[contains(@data-search-keys, '" + subfolderName + "')]")).isEmpty()
assert !driver.findElements(By.xpath("//*[contains(@data-search-keys, '" + docName + "')]")).isEmpty()

WebUI.closeBrowser()
