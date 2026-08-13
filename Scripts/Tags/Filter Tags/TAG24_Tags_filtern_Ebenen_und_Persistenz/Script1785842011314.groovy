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
String tagText = 'TAG24_' + RandomStringUtils.randomAlphanumeric(6)

// Tag am Arbeitsbereich selbst
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
String docName1 = TagHelper.createDocumentInCurrentFolder()
TagHelper.openTagEditorViaIcon(docName1)
TagHelper.addTag(tagText)
TagHelper.saveEditorViaEnter()
WebUI.refresh()
WebUI.delay(2)

String docName2 = TagHelper.createDocumentInCurrentFolder()
TagHelper.openTagEditorViaIcon(docName2)
TagHelper.addTag(tagText)
TagHelper.saveEditorViaEnter()
WebUI.refresh()
WebUI.delay(2)

// (a) Filter von der obersten Arbeitsbereichs-Ebene aus
TagHelper.backToFolderList()
TagHelper.filterByTag(tagText)
WebDriver driver = DriverFactory.getWebDriver()
assert TagHelper.rowExistsEventually(workspaceName)

// F5-Persistenz
WebUI.refresh()
WebUI.delay(2)
assert TagHelper.isTagFilterActive(tagText)

TagHelper.clearTagFilterViaX(tagText)
assert !TagHelper.isTagFilterActive(tagText, 3)

// (b) Filter von innerhalb eines Unterordners aus - muss weiterhin arbeitsbereichsweit filtern
TagHelper.openItem(workspaceName)
TagHelper.openItem(subfolderName)
TagHelper.filterByTag(tagText)

WebDriver driver2 = DriverFactory.getWebDriver()
assert TagHelper.rowExistsEventually(docName1)
assert TagHelper.rowExistsEventually(docName2)

TagHelper.clickResetFilterAndSearch()
assert !TagHelper.isTagFilterActive(tagText, 3)

WebUI.closeBrowser()
