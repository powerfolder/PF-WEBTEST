package helpers

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import java.util.regex.Pattern

public class WindowTitle {

    @Keyword
    static boolean verify(String page) {
        return WebUI.verifyMatch(WebUI.getWindowTitle(), pattern(page), true)
    }

    @Keyword
    static boolean verifyNot(String page) {
        return WebUI.verifyNotMatch(WebUI.getWindowTitle(), pattern(page), true)
    }

    private static String pattern(String page) {
        if (ServerProfile.is(ServerProfile.MIMAS)) {
            return Pattern.quote(page + ' - PowerFolder')
        }
        return Pattern.quote(page + ' - ') + '.+'
    }
}
