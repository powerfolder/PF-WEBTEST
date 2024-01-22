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
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement

WebUI.callTestCase(findTestCase('Folders/PreTest_GoToShareable'), [:], FailureHandling.OPTIONAL)

originfolderCount = getFoldersCount()

String folderName =  getRandomFolderName()
WebUI.click(findTestObject('Folders/createFolderIcon'))
WebUI.click(findTestObject('Folders/createFolder'))
WebUI.setText(findTestObject('Folders/inputFolderName'), getRandomFolderName())
//WebUI.click(findTestObject('Folders/buttonOK'))

WebUI.sendKeys(findTestObject('Folders/inputFolderName'),  Keys.chord(Keys.ENTER))

assert getFoldersCount() > originfolderCount

assert true == tableContainsFolder(folderName)

WebUI.closeBrowser()

int getFoldersCount() {
    WebDriver driver = DriverFactory.getWebDriver()

    WebElement tbody = driver.findElement(By.xpath('//table[@id=\'files_files_table\']/tbody'))

    assert tbody

    List<WebElement> rows_table = tbody.findElements(By.tagName('tr'))

    return rows_table.size()
}

boolean tableContainsFolder(String fileName) {
    WebDriver driver = DriverFactory.getWebDriver()

    WebElement tbody = driver.findElement(By.xpath('//table[@id=\'files_files_table\']/tbody'))

    List<WebElement> rows_table = tbody.findElements(By.tagName('tr'))

    return rows_table.any({ def item ->
            item.getText().contains(fileName)
        })
}

def String getRandomFolderName() {
	String folderName = 'Folder'+getTimestamp();
	return folderName;
	
}

def String getTimestamp() {
	Date todaysDate = new Date();
	String formattedDate = todaysDate.format("dd_MMM_yyyy_hh_mm_ss");
	return formattedDate;
}
