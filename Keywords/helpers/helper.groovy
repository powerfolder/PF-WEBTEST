package helpers

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

public class Helper {

	@Keyword
	def static int getMembersCount(){
		WebDriver driver = DriverFactory.getWebDriver()
		WebElement tbody = driver.findElement(By.xpath("//table[@id='share_table']/tbody"))
		assert tbody != null
		List<WebElement> rows_table = tbody.findElements(By.className("pica-highlight"))
		return rows_table.size()
	}

	@Keyword
	def static String getRandomFolderName() {
		String folderName = 'FD' + getTimestamp()
		return folderName
	}
	@Keyword
	def static String getRandomGroupName() {
		String folderName = 'Group_'+getTimestamp();
		return folderName;
		
	}

	@Keyword
	def static String getTimestamp() {
		Date todaysDate = new Date()
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMMyyyyhhmmss", Locale.ENGLISH)
		return formatter.format(todaysDate)
	}

	@Keyword
	def static WebElement findShareButton(String fileName) {
		WebDriver driver = DriverFactory.getWebDriver()
		return driver.findElement(By.xpath("//table[@id='files_files_table']/tbody/tr/td[2]/span/a[contains(text(),'" + fileName + "')]/../../../td[7]/a"))
	}
}
