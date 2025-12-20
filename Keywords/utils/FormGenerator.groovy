package utils

import com.kms.katalon.core.annotation.Keyword
import java.util.Random

public class FormGenerator {

	@Keyword
	static String generateDepartment() {
		String[] departments = [
			"Personalabteilung",   
			"Finanzabteilung",
			"IT-Abteilung",
			"Marketingabteilung",
			"Logistikabteilung",
			"Kundendienst",
			"Verwaltung"
		]
		return departments[new Random().nextInt(departments.length)] + " " + System.currentTimeMillis()
	}

	@Keyword
	static String generateStreet() {
		int num = new Random().nextInt(200) + 1

		String[] streets = [
			"Hauptstraße",
			"Bahnhofstraße",
			"Gartenstraße",
			"Schillerstraße",
			"Goethestraße",
			"Lindenweg",
			"Rosenweg",
			"Tannenweg",
			"Mühlenweg",
			"Marktplatz"
		]

		return num + " " + streets[new Random().nextInt(streets.length)]
	}

	@Keyword
	static String generatePostalCode() {
		Random r = new Random()
		return String.valueOf(10000 + r.nextInt(89999))
	}

	@Keyword
	static String generateLocation() {
		String[] cities = [
			"Berlin",
			"Hamburg",
			"München",
			"Köln",
			"Frankfurt am Main",
			"Stuttgart",
			"Düsseldorf",
			"Dortmund",
			"Essen",
			"Leipzig",
			"Bremen",
			"Dresden",
			"Hannover",
			"Nürnberg",
			"Duisburg",
			"Bochum",
			"Wuppertal",
			"Bielefeld",
			"Bonn",
			"Münster",
			"Karlsruhe",
			"Mannheim",
			"Augsburg",
			"Wiesbaden",
			"Gelsenkirchen",
			"Aachen",
			"Braunschweig",
			"Kiel",
			"Chemnitz",
			"Magdeburg",
			"Freiburg im Breisgau",
			"Lübeck",
			"Rostock",
			"Kaiserslautern",
			"Saarbrücken",
			"Trier",
			"Heidelberg"
		]

		return cities[new Random().nextInt(cities.length)]
	}

	@Keyword
	static String generateReason() {
		String[] reasons = [
			"Neues Konto erstellen",
			"Datenaktualisierung",
			"Interne Anfrage",
			"Prozessverbesserung",
			"Sicherheitsaktualisierung",
			"Dienstregistrierung"
		]
		return reasons[new Random().nextInt(reasons.length)] + " - " + System.currentTimeMillis()
	}
}
