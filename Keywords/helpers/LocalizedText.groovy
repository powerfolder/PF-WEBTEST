package helpers

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import java.util.regex.Pattern

/**
 * UI texts in English and German, so checks pass whichever language the web UI shows.
 * Keys and texts are taken from lang_en.json / lang_de.json (PF-PRO web-resources/locales).
 * %1 is replaced by the argument, e.g. a folder name.
 */
public class LocalizedText {

    private static final Map<String, List<String>> TEXTS = [
        'dialog_title_interrupt_inheritance'     : ['Own access rights for "%1"?',
                                                    'Eigene Zugriffsrechte für "%1"?'],
        'dialog_body_interrupt_inheritance'      : ['This folder will no longer take over access rights from its parent folder. The currently effective permissions are copied as its own, so nobody loses access immediately. You can adjust them afterwards.',
                                                    'Dieser Ordner übernimmt dann keine Zugriffsrechte mehr vom übergeordneten Ordner. Die aktuell geltenden Berechtigungen werden als eigene Berechtigungen übernommen, damit niemand sofort den Zugriff verliert. Sie können sie anschließend anpassen.'],
        'notification_inheritance_interrupted'   : ['"%1" now has its own access rights.',
                                                    '"%1" hat jetzt eigene Zugriffsrechte.'],
        'dialog_title_restore_inheritance'       : ['Use the parent folder\'s rights again?',
                                                    'Wieder die Rechte des übergeordneten Ordners verwenden?'],
        'dialog_body_restore_inheritance'        : ['This folder\'s own permissions are archived. The parent folder\'s access rights apply again - anyone permitted only on "%1" loses access.',
                                                    'Die eigenen Berechtigungen dieses Ordners werden archiviert. Es gelten wieder die Zugriffsrechte des übergeordneten Ordners - wer nur auf "%1" berechtigt war, verliert den Zugriff.'],
        'notification_inheritance_restored'      : ['"%1" takes over the parent folder\'s access rights again.',
                                                    '"%1" übernimmt wieder die Zugriffsrechte des übergeordneten Ordners.'],
        'permission_inherited'                   : ['(inherited)',
                                                    '(vererbt)'],
        'dialog_title_remove'                    : ['Remove',
                                                    'Entfernen'],
        'notification_error_access_not_possible' : ['Access not possible. You are not authorized to open this folder.',
                                                    'Zugriff nicht möglich. Sie sind nicht berechtigt, diesen Ordner zu öffnen.'],
        'notification_upload_completed'         : ['Successfully uploaded.',
                                                    'Erfolgreich hochgeladen.']
    ]

    /**
     * @return the text of the key in all supported languages
     */
    @Keyword
    static List<String> get(String key, String arg = '') {
        List<String> texts = TEXTS[key]
        assert texts != null : "No localized text for key '" + key + "'"
        return texts.collect { it.replace('%1', arg) }
    }

    /**
     * Verifies that the element shows exactly the text of the key, in one of the supported languages.
     */
    @Keyword
    static void verifyText(TestObject testObject, String key, String arg = '') {
        verifyElementTextIsOneOf(testObject, get(key, arg))
    }

    /**
     * Verifies that a share dialog row shows the name followed by the "(inherited)" marker.
     */
    @Keyword
    static void verifyInheritedName(TestObject testObject, String displayName) {
        verifyElementTextIsOneOf(testObject, get('permission_inherited').collect { displayName + ' ' + it })
    }

    /**
     * Verifies that the text contains the text of the key, in one of the supported languages.
     */
    @Keyword
    static void verifyContains(String actual, String key, String arg = '') {
        WebUI.verifyMatch(actual, '(?s).*(' + alternatives(get(key, arg)) + ').*', true, FailureHandling.STOP_ON_FAILURE)
    }

    /**
     * @return an XPath predicate matching an element whose text contains the text of the key in any supported
     *         language - without its trailing period, so it also hits a message that continues
     */
    @Keyword
    static String containsTextPredicate(String key, String arg = '') {
        return get(key, arg).collect { "contains(text()," + xpathLiteral(it.replaceAll('\\.$', '')) + ")" }.join(' or ')
    }

    private static void verifyElementTextIsOneOf(TestObject testObject, List<String> expected) {
        WebUI.waitForElementVisible(testObject, 5)
        String actual = WebUI.getText(testObject).trim()
        WebUI.verifyMatch(actual, alternatives(expected), true, FailureHandling.STOP_ON_FAILURE)
    }

    private static String alternatives(List<String> texts) {
        return texts.collect { Pattern.quote(it) }.join('|')
    }

    private static String xpathLiteral(String text) {
        return text.contains("'") ? '"' + text + '"' : "'" + text + "'"
    }
}
