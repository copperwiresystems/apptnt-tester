package tntRestAutomation;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestPropertyReader {
	private static final String TEST_CONFIG_FILE_PATH = "./src/test/resources/TestConfiguration.properties";
	private static Properties property = null;

	public static String getProperty(String name) {
		String value = null;
		try {
			if (null == property) {
				FileInputStream fis = new FileInputStream(TEST_CONFIG_FILE_PATH);
				property = new Properties();
				property.load(fis);
			}
			value = property.getProperty(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public static String getBaseUri() {
		return getProperty("baseURI");
	}
}
