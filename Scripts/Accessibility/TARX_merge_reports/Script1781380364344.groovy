import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.util.KeywordUtil

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.stream.Collectors

// Suite-Report-Wurzel: enthält den Unterordner "Accessibility/<TestcaseName>/AccessibilityViolations_*.txt"
File reportRoot = new File(RunConfiguration.getReportFolder())
File accessibilityRoot = new File(reportRoot, "Accessibility")

if (!accessibilityRoot.exists()) {
	KeywordUtil.markWarning("Kein Accessibility-Ordner gefunden unter: " + accessibilityRoot.getAbsolutePath())
	return
}

// Alle Violation-Textdateien rekursiv einsammeln
List<Path> txtFiles = Files.walk(accessibilityRoot.toPath())
		.filter { Path p -> Files.isRegularFile(p) }
		.filter { Path p -> p.getFileName().toString().startsWith("AccessibilityViolations_") && p.getFileName().toString().endsWith(".txt") }
		.sorted()
		.collect(Collectors.toList())

if (txtFiles.isEmpty()) {
	KeywordUtil.logInfo("Keine AccessibilityViolations_*.txt gefunden — nichts zu kombinieren.")
	return
}

// Nach Test-Case-Name (= Ordnername direkt unter Accessibility/) gruppieren
Map<String, List<Path>> byTestCase = new LinkedHashMap<>()
txtFiles.each { Path p ->
	// erwartet: <reportRoot>/Accessibility/<TestcaseName>/AccessibilityViolations_*.txt
	Path rel = accessibilityRoot.toPath().relativize(p)
	String testCaseName = rel.getNameCount() > 1 ? rel.getName(0).toString() : "(unzugeordnet)"
	byTestCase.computeIfAbsent(testCaseName, { k -> new ArrayList<Path>() }).add(p)
}

// Zieldatei
String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date())
File combinedFile = new File(accessibilityRoot, "AccessibilityViolations_Combined_" + timeStamp + ".txt")

combinedFile.withWriter("UTF-8") { writer ->
	writer.writeLine("==============================================================")
	writer.writeLine(" Combined Accessibility Report")
	writer.writeLine(" Suite Report Folder: " + reportRoot.getAbsolutePath())
	writer.writeLine(" Generated:           " + new Date().toString())
	writer.writeLine(" Test Cases:          " + byTestCase.size())
	writer.writeLine(" Source Files:        " + txtFiles.size())
	writer.writeLine("==============================================================")
	writer.writeLine("")

	// Inhaltsverzeichnis
	writer.writeLine("Übersicht:")
	byTestCase.each { tcName, files ->
		writer.writeLine("  - " + tcName + "  (" + files.size() + " Lauf/Läufe)")
	}
	writer.writeLine("")
	writer.writeLine("==============================================================")
	writer.writeLine("")

	// Inhalte
	byTestCase.each { tcName, files ->
		writer.writeLine("##############################################################")
		writer.writeLine("# Test Case: " + tcName)
		writer.writeLine("##############################################################")
		writer.writeLine("")

		files.each { Path p ->
			writer.writeLine("--------------------------------------------------------------")
			writer.writeLine("Quelldatei: " + p.toAbsolutePath().toString())
			writer.writeLine("--------------------------------------------------------------")
			p.toFile().eachLine("UTF-8") { line ->
				writer.writeLine(line)
			}
			writer.writeLine("")
		}
	}
}

KeywordUtil.logInfo("Combined Accessibility Report geschrieben: " + combinedFile.getAbsolutePath())