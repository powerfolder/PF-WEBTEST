package helpers

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor

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

import java.awt.Robot
import java.awt.event.InputEvent
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.Transferable

import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JComponent
import javax.swing.SwingConstants
import javax.swing.SwingUtilities
import javax.swing.TransferHandler

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
		String folderName = getTimestamp()
		return folderName
	}
	
	@Keyword
	def static String getRandomFileName() {
		String folderName = 'File_' + getTimestamp()
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
	
		String formattedDate = todaysDate.format('dd_MMM_yyyy_hh_mm_ss')
	
		return formattedDate
	}
	
	@Keyword
	def static WebElement findShareButton(String fileName) {
		WebDriver driver = DriverFactory.getWebDriver()
		return driver.findElement(By.xpath("//table[@id='files_files_table']/tbody/tr[contains(@data-search-keys,'" + fileName + "')]/td[7]/a"))
	}
	@Keyword
	def static generateDateTimePlusTenSeconds() {
		Calendar calendar = Calendar.getInstance()

		calendar.add(Calendar.SECOND, 10)

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm")

		return sdf.format(calendar.getTime())
	}
	
	def static generateDateTimeMinusOneMinute() {
		Calendar calendar = Calendar.getInstance()
	
		// 1 Minute abziehen
		calendar.add(Calendar.MINUTE, -1)
	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
	
		return sdf.format(calendar.getTime())
	}
	@Keyword
	def static void dragAndDropElementToElement(String sourceXpath, String targetXpath) {
		WebDriver driver = DriverFactory.getWebDriver()
		JavascriptExecutor js = (JavascriptExecutor) driver
	
		String script = """
		function fireDnD(source, target) {
			const dataTransfer = new DataTransfer();

			function fire(type, element) {
				const event = new DragEvent(type, {
					bubbles: true,
					cancelable: true,
					dataTransfer: dataTransfer
				});
				element.dispatchEvent(event);
			}

			source.scrollIntoView({block: 'center'});
			target.scrollIntoView({block: 'center'});

			fire('mousedown', source);
			fire('dragstart', source);
			fire('dragenter', target);
			fire('dragover', target);
			fire('drop', target);
			fire('dragend', source);
			fire('mouseup', target);
		}

		const source = document.evaluate(arguments[0], document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
		const target = document.evaluate(arguments[1], document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;

		if (!source) throw 'Source not found: ' + arguments[0];
		if (!target) throw 'Target not found: ' + arguments[1];

		fireDnD(source, target);
	"""
	
		js.executeScript(script, sourceXpath, targetXpath)
	
		Thread.sleep(2000)
	}
	

	@Keyword
	
	def static void dragAndDropFileNative(String dropZoneCss, String filePath) {
		WebDriver driver = DriverFactory.getWebDriver()
		JavascriptExecutor js = (JavascriptExecutor) driver

		js.executeScript("var el=document.querySelector(arguments[0]); if(el){el.scrollIntoView({block:'center'});}", dropZoneCss)
		Thread.sleep(500)

		List<Object> geo = (List<Object>) js.executeScript(
				"var el=document.querySelector(arguments[0]);" +
				"var r=el.getBoundingClientRect();" +
				"return [r.left+r.width/2, r.top+r.height/2, window.screenX, window.screenY," +
				" window.outerWidth-window.innerWidth, window.outerHeight-window.innerHeight];",
				dropZoneCss)

		double cx = ((Number) geo.get(0)).doubleValue()
		double cy = ((Number) geo.get(1)).doubleValue()
		double winX = ((Number) geo.get(2)).doubleValue()
		double winY = ((Number) geo.get(3)).doubleValue()
		double chromeW = ((Number) geo.get(4)).doubleValue()
		double chromeH = ((Number) geo.get(5)).doubleValue()

		int dropX = (int) Math.round(winX + (chromeW / 2.0) + cx)
		int dropY = (int) Math.round(winY + chromeH + cy)

		final File theFile = new File(filePath)
		final javax.swing.JFrame frame = new javax.swing.JFrame("DnD Source")
		final javax.swing.JLabel label = new javax.swing.JLabel("DRAG", javax.swing.SwingConstants.CENTER)

		javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
			void run() {
				label.setOpaque(true)
				label.setBackground(java.awt.Color.YELLOW)
				label.setTransferHandler(new javax.swing.TransferHandler() {
					protected java.awt.datatransfer.Transferable createTransferable(javax.swing.JComponent c) {
						return new java.awt.datatransfer.Transferable() {
							java.awt.datatransfer.DataFlavor[] getTransferDataFlavors() {
								return [java.awt.datatransfer.DataFlavor.javaFileListFlavor] as java.awt.datatransfer.DataFlavor[]
							}
							boolean isDataFlavorSupported(java.awt.datatransfer.DataFlavor f) {
								return java.awt.datatransfer.DataFlavor.javaFileListFlavor.equals(f)
							}
							Object getTransferData(java.awt.datatransfer.DataFlavor f) {
								return java.util.Arrays.asList(theFile)
							}
						}
					}
					int getSourceActions(javax.swing.JComponent c) {
						return javax.swing.TransferHandler.COPY
					}
				})
				label.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
					void mouseDragged(java.awt.event.MouseEvent e) {
						label.getTransferHandler().exportAsDrag(label, e, javax.swing.TransferHandler.COPY)
					}
				})
				frame.add(label)
				frame.setSize(160, 120)
				frame.setLocation(40, 40)
				frame.setAlwaysOnTop(true)
				frame.setVisible(true)
			}
		})
		Thread.sleep(800)

		java.awt.Point lp = label.getLocationOnScreen()
		int srcX = lp.x + (int) (label.getWidth() / 2)
		int srcY = lp.y + (int) (label.getHeight() / 2)

		java.awt.Robot robot = new java.awt.Robot()
		robot.setAutoDelay(40)
		robot.mouseMove(srcX, srcY)
		robot.mousePress(java.awt.event.InputEvent.BUTTON1_DOWN_MASK)
		robot.mouseMove(srcX + 4, srcY + 2)
		robot.mouseMove(srcX + 10, srcY + 6)
		robot.mouseMove(srcX + 16, srcY + 12)

		int steps = 30
		for (int i = 1; i <= steps; i++) {
			int x = (int) (srcX + ((dropX - srcX) * i / (double) steps))
			int y = (int) (srcY + ((dropY - srcY) * i / (double) steps))
			robot.mouseMove(x, y)
			Thread.sleep(25)
		}

		for (int i = 0; i < 5; i++) {
			robot.mouseMove(dropX - 5, dropY - 3)
			Thread.sleep(80)
			robot.mouseMove(dropX + 5, dropY + 3)
			Thread.sleep(80)
		}
		robot.mouseMove(dropX, dropY)
		Thread.sleep(500)
		robot.mouseRelease(java.awt.event.InputEvent.BUTTON1_DOWN_MASK)
		Thread.sleep(1000)

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			void run() { frame.dispose() }
		})
	}
	@Keyword
	def static void selectUploadedFolders(List<String> folderNames, String subFolderName) {
		WebDriver driver = DriverFactory.getWebDriver()
	
		List<WebElement> icons = driver.findElements(
			By.xpath("//table[@id='files_files_table']/tbody/tr[not(contains(@data-search-keys,'" + subFolderName + "'))]//td[1]/span")
		)
	
		if (icons.size() == 0) {
			throw new RuntimeException("No uploaded folder icons found")
		}
	
		WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(icons.get(0)))
		Thread.sleep(500)
	
		new org.openqa.selenium.interactions.Actions(driver)
			.keyDown(org.openqa.selenium.Keys.SHIFT)
			.click(icons.get(icons.size() - 1))
			.keyUp(org.openqa.selenium.Keys.SHIFT)
			.build()
			.perform()
	
		Thread.sleep(1000)
	}
	
	@Keyword
	def static void dragSelectedFoldersToSubFolder(String subFolderName) {
		WebDriver driver = DriverFactory.getWebDriver()
	
		WebElement source = driver.findElement(
			By.xpath("//tr[contains(@class,'info') and not(contains(@data-search-keys,'" + subFolderName + "'))]//td[1]/span")
		)
	
		WebElement target = driver.findElement(
			By.xpath("//tr[contains(@data-search-keys,'" + subFolderName + "')]//td[1]/span")
		)
	
		new org.openqa.selenium.interactions.Actions(driver)
			.clickAndHold(source)
			.pause(800)
			.moveToElement(target)
			.pause(1200)
			.release()
			.build()
			.perform()
	
		Thread.sleep(3000)
	}
	@Keyword
	def static void dragAndDropFilesNative(String dropZoneCss, List<String> filePaths) {
		WebDriver driver = DriverFactory.getWebDriver()
		JavascriptExecutor js = (JavascriptExecutor) driver

		js.executeScript(
			"var el=document.querySelector(arguments[0]); if(el){el.scrollIntoView({block:'center'});}",
			dropZoneCss
		)

		Thread.sleep(500)

		List<Object> geo = (List<Object>) js.executeScript(
			"var el=document.querySelector(arguments[0]);" +
			"var r=el.getBoundingClientRect();" +
			"return [r.left+r.width/2, r.top+r.height/2, window.screenX, window.screenY," +
			" window.outerWidth-window.innerWidth, window.outerHeight-window.innerHeight];",
			dropZoneCss
		)

		double cx = ((Number) geo.get(0)).doubleValue()
		double cy = ((Number) geo.get(1)).doubleValue()
		double winX = ((Number) geo.get(2)).doubleValue()
		double winY = ((Number) geo.get(3)).doubleValue()
		double chromeW = ((Number) geo.get(4)).doubleValue()
		double chromeH = ((Number) geo.get(5)).doubleValue()

		int dropX = (int) Math.round(winX + (chromeW / 2.0) + cx)
		int dropY = (int) Math.round(winY + chromeH + cy)

		final List<File> files = filePaths.collect { new File(it) }

		final JFrame frame = new JFrame("DnD Source")
		final JLabel label = new JLabel("DRAG FILES", SwingConstants.CENTER)

		SwingUtilities.invokeAndWait(new Runnable() {
			void run() {
				label.setOpaque(true)
				label.setBackground(java.awt.Color.YELLOW)

				label.setTransferHandler(new TransferHandler() {
					protected Transferable createTransferable(JComponent c) {
						return new Transferable() {

							DataFlavor[] getTransferDataFlavors() {
								return [DataFlavor.javaFileListFlavor] as DataFlavor[]
							}

							boolean isDataFlavorSupported(DataFlavor flavor) {
								return DataFlavor.javaFileListFlavor.equals(flavor)
							}

							Object getTransferData(DataFlavor flavor) {
								return files
							}
						}
					}

					int getSourceActions(JComponent c) {
						return TransferHandler.COPY
					}
				})

				label.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
					void mouseDragged(java.awt.event.MouseEvent e) {
						label.getTransferHandler().exportAsDrag(label, e, TransferHandler.COPY)
					}
				})

				frame.add(label)
				frame.setSize(180, 120)
				frame.setLocation(40, 40)
				frame.setAlwaysOnTop(true)
				frame.setVisible(true)
			}
		})

		Thread.sleep(800)

		java.awt.Point lp = label.getLocationOnScreen()
		int srcX = lp.x + (int) (label.getWidth() / 2)
		int srcY = lp.y + (int) (label.getHeight() / 2)

		Robot robot = new Robot()
		robot.setAutoDelay(40)

		robot.mouseMove(srcX, srcY)
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)

		robot.mouseMove(srcX + 4, srcY + 2)
		robot.mouseMove(srcX + 10, srcY + 6)
		robot.mouseMove(srcX + 16, srcY + 12)

		int steps = 30

		for (int i = 1; i <= steps; i++) {
			int x = (int) (srcX + ((dropX - srcX) * i / (double) steps))
			int y = (int) (srcY + ((dropY - srcY) * i / (double) steps))
			robot.mouseMove(x, y)
			Thread.sleep(25)
		}

		for (int i = 0; i < 5; i++) {
			robot.mouseMove(dropX - 5, dropY - 3)
			Thread.sleep(80)
			robot.mouseMove(dropX + 5, dropY + 3)
			Thread.sleep(80)
		}

		robot.mouseMove(dropX, dropY)
		Thread.sleep(500)

		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
		Thread.sleep(1000)

		SwingUtilities.invokeLater(new Runnable() {
			void run() {
				frame.dispose()
			}
		})
	}
	@Keyword
	def static void dragAndDropFolderNative(String dropZoneCss, String folderPath) {
		dragAndDropFilesNative(dropZoneCss, [folderPath])
	}
	@Keyword
	def static void dragAndDropFoldersNative(String dropZoneCss, List<String> folderPaths) {
		dragAndDropFilesNative(dropZoneCss, folderPaths)
	}


	static String getSmartName() {

		String safeEmojis =
				"☺☹✌✍" +
				"❤❣❥♥♡❦❧" +
				"★☆✪✫✬✭✮✯✰" +
				"✓✔✗✘☑" +
				"←↑→↓↔↕↖↗↘↙" +
				"⚠⚡⚓✈☀☁☂☃☄" +
				"♠♣♥♦♤♧♡♢";

		String accents = "äöüßÄÖÜ"; //éèàùçâêîôûÉÈÀÙÇ";

		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

		String specials = "@!\${}"

		String all = accents + chars + specials;

		Random r = new Random();

		char e1 = safeEmojis.charAt(r.nextInt(safeEmojis.length()));
		char e2 = safeEmojis.charAt(r.nextInt(safeEmojis.length()));

		int length = 10 + r.nextInt(6);
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < length; i++) {
			sb.append(all.charAt(r.nextInt(all.length())));
		}

		int pos = r.nextInt(sb.length());
		sb.insert(pos, " ");

		return e1.toString() + e2.toString() + sb.toString();
	}
}
