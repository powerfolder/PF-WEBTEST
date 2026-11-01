package group

import com.kms.katalon.core.annotation.Keyword
import folder.FolderName

public class GroupName {

    static final String SUFFIX = FolderName.SUFFIX

    @Keyword
    static String withSuffix(String name) {
        return name.contains(SUFFIX) ? name : name + SUFFIX
    }
}
