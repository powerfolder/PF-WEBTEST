package subfoldersharing

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.WebElement

import helpers.LocalizedText
import tags.TagHelper

public class InheritanceHelper {

    @Keyword
    static void interruptInheritance(String subFolderName) {
        clickInheritanceToggle()
        LocalizedText.verifyText(findTestObject('Subfoldersharing/inheritance_dialog_title'), 'dialog_title_interrupt_inheritance', subFolderName)
        WebUI.click(findTestObject('Subfoldersharing/inheritance_dialog_ok'))
        LocalizedText.verifyText(findTestObject('notifications_toastmessage'), 'notification_inheritance_interrupted', subFolderName)
        WebUI.verifyElementNotChecked(findTestObject('Subfoldersharing/share_inheritance_toggle'), 5)
        WebUI.click(findTestObject('Share/close_button_folder_share_mail'))
        WebUI.delay(1)
    }

    @Keyword
    static void restoreInheritance(String subFolderName) {
        clickInheritanceToggle()
        LocalizedText.verifyText(findTestObject('Subfoldersharing/inheritance_dialog_title'), 'dialog_title_restore_inheritance')
        WebUI.click(findTestObject('Subfoldersharing/inheritance_dialog_ok'))
        LocalizedText.verifyText(findTestObject('notifications_toastmessage'), 'notification_inheritance_restored', subFolderName)
        WebUI.verifyElementChecked(findTestObject('Subfoldersharing/share_inheritance_toggle'), 5)
        WebUI.click(findTestObject('Share/close_button_folder_share_mail'))
        WebUI.delay(1)
    }

    @Keyword
    static void shareCurrentFolderWith(String email) {
        WebUI.click(findTestObject('Links/share_icon_inside_folder'))
        WebUI.setText(findTestObject('Share/Page_Folders - PowerFolder/inputEmail_Share'), email)
        WebUI.click(findTestObject('Share/Page_Folders - PowerFolder/buttonAddEmail'))
        WebUI.delay(2)
        WebUI.click(findTestObject('Share/close_button_folder_share_mail'))
        WebUI.delay(1)
    }

    @Keyword
    static void openTopFolder(String topFolderName) {
        TagHelper.backToFolderList()
        TagHelper.openItem(topFolderName)
    }

    @Keyword
    static void editTags(String itemName, List<String> tagsToRemove, List<String> tagsToAdd) {
        TagHelper.openTagEditorViaRowMenu(itemName)
        tagsToRemove.each { TagHelper.removeTag(it) }
        tagsToAdd.each { TagHelper.addTag(it) }
        TagHelper.saveEditorViaEnter()
        WebUI.delay(2)
        WebUI.refresh()
        WebUI.delay(2)
    }

    private static void clickInheritanceToggle() {
        WebUI.click(findTestObject('Links/share_icon_inside_folder'))
        WebUI.delay(2)
        WebElement toggle = WebUI.findWebElement(findTestObject('Subfoldersharing/share_inheritance_toggle'), 5)
        WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(toggle))
        WebUI.waitForElementVisible(findTestObject('Subfoldersharing/inheritance_dialog_title'), 5)
    }
}
