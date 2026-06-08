package accessibility

import static com.deque.html.axecore.selenium.AxeReporter.getReadableAxeResults

import java.text.SimpleDateFormat

import com.deque.html.axecore.results.ResultType
import com.deque.html.axecore.results.Results
import com.deque.html.axecore.results.Rule
import com.deque.html.axecore.selenium.AxeBuilder
import com.deque.html.axecore.selenium.AxeReporter
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory

public class AccessibilityKeywords {

	@Keyword
	def checkAccessibility() {
		Results results = new AxeBuilder().analyze(DriverFactory.getWebDriver())
		List<Rule> violations = results.getViolations()
		if (violations.size() == 0) {
			KeywordUtil.logInfo("No Violation Found")
		}

		String testCaseName = RunConfiguration.getExecutionSourceName()
		String safeName = testCaseName.replaceAll('[\\\\/:*?"<>|]', '_')

		File reportDir = new File(RunConfiguration.getReportFolder(), "Accessibility" + File.separator + safeName)
		if (!reportDir.exists()) {
			reportDir.mkdirs()
		}

		String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new java.util.Date())
		String axeViolationReportPath = new File(reportDir, "AccessibilityViolations_" + timeStamp).getAbsolutePath()

		AxeReporter.writeResultsToJsonFile(axeViolationReportPath, results)
		KeywordUtil.logInfo("Violation Report Path: " + axeViolationReportPath)

		if (getReadableAxeResults(ResultType.Violations.getKey(), DriverFactory.getWebDriver(), violations)) {
			AxeReporter.writeResultsToTextFile(axeViolationReportPath, AxeReporter.getAxeResultString())
		}
	}
}