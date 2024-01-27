package folder

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement

public class FolderHelper {


	@Keyword
	def WebElement findFolderRaw(String fileName) {
		WebDriver driver = DriverFactory.getWebDriver()
		return driver.findElement(By.xpath("//table[@id='files_files_table']/tbody/tr/td[2]/a[contains(text(),'$fileName')]/../.."))
	}

	@Keyword
	def boolean isFileDownloaded(String downloadPath, String fileName) {
	long timeout = 5 * 60 * 1000
	long start = new Date().getTime()
	boolean downloaded = false
	File file = new File(downloadPath, fileName)
	while (!downloaded) {
		println("Checking file exists ${file.absolutePath}")
		downloaded = file.exists()
		if (downloaded) {
			file.delete()
		} else {
			long now = new Date().getTime()
			if (now - start > timeout) {
				break
			}
			Thread.sleep(3000)
		}
	}
	return downloaded
}
}
