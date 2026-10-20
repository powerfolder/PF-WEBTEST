package login

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import helpers.ServerProfile

public class LoginHelper {

    @Keyword
    static void enterCredentials(String username, String password) {
        WebUI.setText(findTestObject('Login/inputEmail'), username)

        if (ServerProfile.isNot(ServerProfile.MIMAS)) {
            WebUI.click(findTestObject('Login/loginSubmit'))
            WebUI.waitForElementVisible(findTestObject('Login/inputPassword'), 10)
        }

        WebUI.setText(findTestObject('Login/inputPassword'), password)
    }
}
