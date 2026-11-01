package folder

import com.kms.katalon.core.annotation.Keyword

public class FolderName {

    static final String SUFFIX = '_katalon'

    @Keyword
    static String withSuffix(String name) {
        return name.contains(SUFFIX) ? name : name + SUFFIX
    }
}
