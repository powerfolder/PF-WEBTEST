package helpers

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.configuration.RunConfiguration

public class ServerProfile {

    static final String DEFAULT  = 'default'
    static final String MIMAS    = 'mimas'
    static final String JANUS    = 'janus'
    static final String PAN      = 'pan'
    static final String TITAN    = 'titan'
    static final String CUSTOMER = 'customer'

    @Keyword
    static String getName() {
        return RunConfiguration.getExecutionProfile()
    }

    @Keyword
    static boolean is(String... profiles) {
        return profiles.any { it.equalsIgnoreCase(getName()) }
    }

    @Keyword
    static boolean isNot(String... profiles) {
        return !is(profiles)
    }
}
