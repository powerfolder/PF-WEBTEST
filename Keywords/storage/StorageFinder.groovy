package storage;

import com.kms.katalon.core.annotation.Keyword;
import com.kms.katalon.core.webui.driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;




public class StorageFinder {

	@Keyword
	public WebElement findStorage(String storageName) {
		WebDriver driver = DriverFactory.getWebDriver();
		return driver.findElement(By.xpath("//*[contains(@data-search-keys, '" + storageName + "')]/td[1]/span"));
	}

	@Keyword
	public List<WebElement> findAllStorages() {
		WebDriver driver = DriverFactory.getWebDriver();
		return driver.findElements(By.xpath("//td[1]/span"));
	}
}
