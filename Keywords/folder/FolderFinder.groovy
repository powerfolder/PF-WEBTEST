package folder

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

public class FolderFinder {

    @Keyword
    public static WebElement findFolder(String folderName, int timeoutSeconds = 60) {
        return findElement("//a[contains(text(),'${folderName}')]", timeoutSeconds)
    }

    @Keyword
    public static WebElement findElement(String xpath, int timeoutSeconds = 60) {
        return findElement(By.xpath(xpath), timeoutSeconds)
    }

    public static WebElement findElement(By locator, int timeoutSeconds = 60) {
        WebDriver driver = DriverFactory.getWebDriver()
        long deadline = System.currentTimeMillis() + timeoutSeconds * 1000L

        while (true) {
            List<WebElement> found = driver.findElements(locator)
            if (!found.isEmpty()) {
                return found.get(0)
            }
            if (System.currentTimeMillis() > deadline) {
                throw new NoSuchElementException(
                    "${locator} not found in the list within ${timeoutSeconds} seconds")
            }
            scrollListsToEnd(driver)
            Thread.sleep(500)
        }
    }

    private static void scrollListsToEnd(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript('''
            document.querySelectorAll("table tbody").forEach(function (tbody) {
                if (tbody.scrollHeight > tbody.clientHeight) {
                    tbody.scrollTop = tbody.scrollHeight;
                    tbody.dispatchEvent(new Event("scroll"));
                }
            });
        ''')
    }
}
