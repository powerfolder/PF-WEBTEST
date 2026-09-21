package credentials

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.util.KeywordUtil

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.attribute.PosixFilePermission
import java.nio.file.attribute.PosixFileAttributeView

// Reads credentials from "pfwebtest-credentials.properties" in the user's home directory.
// Required entries: adminusername:<user> and adminpassword:<password>
class CredentialsManager {

	private static final String CONFIG_FILE_NAME = 'pfwebtest-credentials.properties'

	private static File getConfigFile() {
		String home = System.getProperty('user.home')
		return new File(home, CONFIG_FILE_NAME)
	}

	private static void warnIfPermissionsTooOpen(File configFile) {
		try {
			Path path = configFile.toPath()
			PosixFileAttributeView view = Files.getFileAttributeView(path, PosixFileAttributeView.class)
			if (view == null) {
				return
			}
			Set<PosixFilePermission> perms = Files.getPosixFilePermissions(path)
			boolean readableByOthers = perms.contains(PosixFilePermission.GROUP_READ) ||
					perms.contains(PosixFilePermission.OTHERS_READ)
			if (readableByOthers) {
				KeywordUtil.markWarning("Credentials file ${configFile.absolutePath} is readable by group/others. " +
						"Please restrict permissions with 'chmod 600 ${configFile.absolutePath}'.")
			}
		} catch (Exception ignored) {
		}
	}

	private static Properties loadProperties() {
		File configFile = getConfigFile()
		if (!configFile.exists()) {
			KeywordUtil.markFailedAndStop("Credentials file not found: ${configFile.absolutePath}. " +
					"Please create it with entries like 'adminusername:<user>' and 'adminpassword:<password>'.")
		}
		warnIfPermissionsTooOpen(configFile)

		Properties props = new Properties()
		configFile.withInputStream { InputStream is ->
			props.load(is)
		}
		return props
	}

	@Keyword
	static String getCredential(String key) {
		Properties props = loadProperties()
		String value = props.getProperty(key)
		if (value == null) {
			KeywordUtil.markFailedAndStop("Key '${key}' is missing in credentials file ${getConfigFile().absolutePath}.")
		}
		return value
	}

	@Keyword
	static String getAdminUsername() {
		return getCredential('adminusername')
	}

	@Keyword
	static String getAdminPassword() {
		return getCredential('adminpassword')
	}
}
