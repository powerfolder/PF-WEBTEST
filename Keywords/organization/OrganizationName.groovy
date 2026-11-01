package organization

import com.kms.katalon.core.annotation.Keyword
import folder.FolderName

public class OrganizationName {

    static final String SUFFIX = FolderName.SUFFIX

    @Keyword
    static String withSuffix(String name) {
        return name.contains(SUFFIX) ? name : name + SUFFIX
    }
}
