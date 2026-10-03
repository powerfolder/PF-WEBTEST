package folder

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

/**
 * The folder list loads 50 entries per request, the first ten pages one after another and every further page
 * only when the table is scrolled to its end. It is sorted by name, so with more than 500 folders on the server
 * a freshly created folder whose name sorts late is not in the DOM right after opening the list - neither a
 * plain findElement nor a WebDriverWait finds it. These helpers wait for an element and scroll the list down
 * until it is loaded.
 */
public class FolderFinder {

    /**
     * Finds the name link of a folder in the folder list.
     *
     * @param folderName     name of the folder
     * @param timeoutSeconds how long to wait at most
     * @return the name link of the folder
     */
    @Keyword
    public static WebElement findFolder(String folderName, int timeoutSeconds = 60) {
        return findElement("//a[contains(text(),'${folderName}')]", timeoutSeconds)
    }

    /**
     * Finds an element of a list row, loading further pages of the list until it is there.
     *
     * @param xpath          locator of the element
     * @param timeoutSeconds how long to wait at most
     * @return the first matching element
     */
    @Keyword
    public static WebElement findElement(String xpath, int timeoutSeconds = 60) {
        return findElement(By.xpath(xpath), timeoutSeconds)
    }

    /**
     * Finds an element of a list row, loading further pages of the list until it is there.
     *
     * @param locator        locator of the element
     * @param timeoutSeconds how long to wait at most
     * @return the first matching element
     */
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

    /**
     * Scrolls every scrollable table body to its end, which makes it load its next page. Not only the folder
     * list loads that way - the recycle bin does too.
     */
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
