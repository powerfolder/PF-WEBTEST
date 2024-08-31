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
import java.awt.FileDialog as FileDialog
import javax.swing.JFrame as JFrame
import javax.net.ssl.*
import java.security.cert.X509Certificate
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement



WebUI.callTestCase(findTestCase('Groups/Pre_test/create_group'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/a_Edit_m'))
WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Avatar'))
WebUI.click(findTestObject('Object Repository/Groups/Page_Groups - PowerFolder/a_Change'))
WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/span_Add file_m'))

// Select the downloaded image to continue the test
def desktopImagePath = Paths.get(System.getProperty('user.home'), 'Desktop', 'images', 'avatar.png')
selectImageAutomatically(desktopImagePath.toString())

WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Close'))

/*

// Localisation du bouton via XPath et clic
def xpath = '/html/body/div[2]/div[1]/div[2]/div[6]/div/div/div[3]/button[1]'

def driver = DriverFactory.getWebDriver()

def button = driver.findElement(By.xpath(xpath))

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(button))


WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Close'))
*/
WebUI.click(findTestObject('Groups/Page_Groups - PowerFolder/button_Save'))

WebUI.verifyElementVisible(findTestObject('Groups/Page_Groups - PowerFolder/div_File successfully uploaded_av'))

WebUI.closeBrowser()

// Disable SSL verification
def disableSSLVerification() {
	TrustManager[] trustAllCerts = [ new X509TrustManager() {
		public X509Certificate[] getAcceptedIssuers() { null }
		public void checkClientTrusted(X509Certificate[] certs, String authType) { }
		public void checkServerTrusted(X509Certificate[] certs, String authType) { }
	} ]

	SSLContext sc = SSLContext.getInstance("SSL")
	sc.init(null, trustAllCerts, new java.security.SecureRandom())
	HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory())

	// Create all-trusting host name verifier
	HostnameVerifier allHostsValid = new HostnameVerifier() {
		public boolean verify(String hostname, SSLSession session) { true }
	}

	// Set the all-trusting host verifier
	HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid)
}

// Call this method at the beginning of your test case
disableSSLVerification()

// Function to download an image from a URL and place it on the desktop
def downloadImageAndPlaceOnDesktop(String imageUrl, String imageName) {
	def desktopImagePath = Paths.get(System.getProperty('user.home'), 'Desktop', 'images')

	if (!Files.exists(desktopImagePath)) {
		Files.createDirectories(desktopImagePath)
	}

	def url = new URL(imageUrl)
	def imagePath = Paths.get(desktopImagePath.toString(), imageName)

	Files.copy(url.openStream(), imagePath, StandardCopyOption.REPLACE_EXISTING)
}

// Function to select an image automatically
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

// Main test script
downloadImageAndPlaceOnDesktop('https://cdn-icons-png.flaticon.com/512/2919/2919906.png', 'avatar.png')

// Execute test steps
