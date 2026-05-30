package file

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

public class FileFinder {

    @Keyword
    public static WebElement findDoc(String docName) {
        WebDriver driver = DriverFactory.getWebDriver()

        return driver.findElement(
            By.xpath("//*[contains(@data-search-keys, '${docName}')]/td[1]/span")
        )
    }
}