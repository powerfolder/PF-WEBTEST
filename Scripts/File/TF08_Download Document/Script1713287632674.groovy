import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject;
import com.kms.katalon.core.annotation.Keyword as Keyword;
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint;
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberBuiltinKeywords;
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as MobileBuiltInKeywords;
import com.kms.katalon.core.model.FailureHandling as FailureHandling;
import com.kms.katalon.core.testcase.TestCase as TestCase;
import com.kms.katalon.core.testdata.TestData as TestData;
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGBuiltinKeywords;
import com.kms.katalon.core.testobject.TestObject as TestObject;
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords;
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords;
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as WindowsBuiltinKeywords;
import internal.GlobalVariable as GlobalVariable;
import org.openqa.selenium.Keys as Keys;
import org.apache.commons.lang3.RandomStringUtils as RandomStringUtils;
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory;
import org.openqa.selenium.WebDriver as WebDriver;
import org.openqa.selenium.WebElement as WebElement;
import org.openqa.selenium.By as By;
import java.util.Arrays as Arrays;
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait;

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI;
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile;
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW;
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS;
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows;
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint;
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase;
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData;
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject;
import java.io.File as File;
import java.util.Date as Date;

WebUI.callTestCase(findTestCase('File/Pre_test/Create_folder'), [:], FailureHandling.STOP_ON_FAILURE);

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Create_Itemes_Insid_a_folder'));

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Page_Folders - PowerFolder/Create_Document'));

String DocName = 'Doc_num_' + RandomStringUtils.randomNumeric(4);

WebUI.setText(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/set_folder_name'), 
    DocName);

WebUI.click(findTestObject('file_objects/document/Page_Folders - PowerFolder/Page_Folders - PowerFolder/button_Ok'));

WebUI.delay(10);

WebUI.closeWindowIndex(1);

WebUI.switchToWindowUrl('https://lab.powerfolder.net:8666/folderstable');

def btn = findDoc(DocName);

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(btn));

assert DocName != null;

WebUI.verifyElementClickable(findTestObject('file_objects/document/Download/Page_Folders - PowerFolder/span_download'));

WebUI.click(findTestObject('file_objects/document/Download/Page_Folders - PowerFolder/span_download'));

// Attendre un certain temps pour que le fichier soit téléchargé et stocké dans le chemin spécifié par l'utilisateur
WebUI.delay(5);

// Définir le chemin de téléchargement
String home = System.getProperty('user.home');

String downloadPath = home + '/Downloads/';

// Vérifier si le fichier est téléchargé dans le chemin spécifié par l'utilisateur
assert isFileDownloaded(downloadPath, DocName + '.docx'); // 5 minutes

WebUI.closeBrowser();

@Keyword
WebElement findDoc(String DocName) {
    WebDriver driver = DriverFactory.getWebDriver();

    return driver.findElement(By.xpath(('//*[contains(@data-search-keys, \'' + DocName) + '\')]/td[1]/span'));
}

@Keyword
boolean isFileDownloaded(String downloadPath, String DocName) {
    long timeout = (5 * 60) * 1000;

    long start = new Date().getTime();

    boolean downloaded = false;

    File file = new File(downloadPath, DocName);

    while (!(downloaded)) {
        println("Checking file exists $file.absolutePath");

        downloaded = file.exists();

        if (downloaded) {
            file.delete();
        } else {
            long now = new Date().getTime();

            if ((now - start) > timeout) {
                break;
            }
            
            Thread.sleep(3000);
        }
    }
    
    return downloaded;
}
