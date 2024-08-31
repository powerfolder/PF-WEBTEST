import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.apache.commons.io.FileUtils as FileUtils
import org.apache.commons.io.IOUtils as IOUtils
import java.io.ByteArrayInputStream as ByteArrayInputStream
import java.net.URL as URL
import java.nio.file.Files as Files
import java.nio.file.Paths as Paths
import java.nio.file.StandardCopyOption as StandardCopyOption
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebElement as WebElement
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.StringSelection as StringSelection
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import java.nio.file.Paths as Paths
import javax.net.ssl.*
import java.security.cert.X509Certificate as X509Certificate
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver

// Disable SSL verification
def disableSSLVerification() {
	TrustManager[] trustAllCerts = [new X509TrustManager() {
		public X509Certificate[] getAcceptedIssuers() { return null }
		public void checkClientTrusted(X509Certificate[] certs, String authType) { }
		public void checkServerTrusted(X509Certificate[] certs, String authType) { }
	}]

	SSLContext sc = SSLContext.getInstance("SSL")
	sc.init(null, trustAllCerts, new java.security.SecureRandom())
	HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory())

	HostnameVerifier allHostsValid = new HostnameVerifier() {
		public boolean verify(String hostname, SSLSession session) { return true }
	}

	HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid)
}

// Call this method at the beginning of your test case
disableSSLVerification()

// Start the test case
WebUI.callTestCase(findTestCase('My Account/Pre_test/Create Account'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Icon_account'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/My_account'))

WebUI.verifyElementVisible(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Page_Profile - PowerFolder/lang_Overview'),
	FailureHandling.STOP_ON_FAILURE)

WebUI.verifyElementClickable(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Page_Profile - PowerFolder/Change_avatar'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Page_Profile - PowerFolder/Change_avatar'))

WebUI.verifyElementClickable(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Page_Profile - PowerFolder/span_Add file'))

WebUI.click(findTestObject('My_Account/Overview/Page_Accounts - PowerFolder/Page_Profile - PowerFolder/span_Add file'))

// Download the image and place it on the desktop
downloadImageAndPlaceOnDesktop('https://cdn-icons-png.flaticon.com/512/2919/2919906.png', 'avatar.png')

// Select the image automatically
def desktopImagePath = Paths.get(System.getProperty('user.home'), 'Desktop', 'avatar.png')
selectImageAutomatically(desktopImagePath.toString())

WebUI.verifyElementClickable(findTestObject('My_Account/Overview/Page_Profile - PowerFolder/button_Close_change_avatar'))

WebUI.click(findTestObject('My_Account/Overview/Page_Profile - PowerFolder/button_Close_change_avatar'))

WebUI.waitForElementPresent(findTestObject('My_Account/Overview/Page_Profile - PowerFolder/Assert_image'), 2)


def downloadImageAndPlaceOnDesktop(String imageUrl, String imageName) {
	def desktopImagePath = Paths.get(System.getProperty('user.home'), 'Desktop')

	if (!Files.exists(desktopImagePath)) {
		Files.createDirectories(desktopImagePath)
	}

	def url = new URL(imageUrl)
	def imagePath = Paths.get(desktopImagePath.toString(), imageName)
	Files.copy(url.openStream(), imagePath, StandardCopyOption.REPLACE_EXISTING)
}

def selectImageAutomatically(String imagePath) {
	try {
		Robot robot = new Robot()
		WebUI.delay(1)
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(imagePath), null)
		robot.keyPress(KeyEvent.VK_CONTROL)
		robot.keyPress(KeyEvent.VK_V)
		robot.keyRelease(KeyEvent.VK_V)
		robot.keyRelease(KeyEvent.VK_CONTROL)
		WebUI.delay(1)
		robot.keyPress(KeyEvent.VK_ENTER)
		robot.keyRelease(KeyEvent.VK_ENTER)
	} catch (Exception e) {
		e.printStackTrace()
	}
}
