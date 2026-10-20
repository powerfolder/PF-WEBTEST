package credentials

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.util.KeywordUtil

import helpers.ServerProfile

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.attribute.PosixFilePermission
import java.nio.file.attribute.PosixFileAttributeView

class CredentialsManager {

	private static final String CONFIG_FILE_SUFFIX = '_pf_webtest.conf'

	private static File getConfigFile() {
		String home = System.getProperty('user.home')
		return new File(home, ServerProfile.getName() + CONFIG_FILE_SUFFIX)
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
			KeywordUtil.markFailedAndStop("Missing Configuration file: ${configFile.absolutePath}")
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
