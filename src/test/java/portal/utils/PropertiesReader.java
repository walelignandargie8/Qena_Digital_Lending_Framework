package portal.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    final private static String PROPERTIES_FILE_PATH = "/properties/configuration.properties";

    public static String readProperty(String propertyName) {
        Properties properties = new Properties();

        try (InputStream input = PropertiesReader.class.getResourceAsStream(PROPERTIES_FILE_PATH)) {

            if (input == null) {
                System.out.printf("Unable to find %s%n", PROPERTIES_FILE_PATH);
            }
            properties.load(input);

            return properties.getProperty(propertyName);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * Method to get the default setting value or the preferred setting value
     * @param defaultSettingName the default setting name
     * @param preferredSettingName the preferred setting name
     * @return the default setting value or the preferred setting value
     */
    public static String getDefaultOrPreferredSetting(String defaultSettingName, String preferredSettingName) {
        String defaultSettingValue = PropertiesReader.readProperty(defaultSettingName);
        String preferredSettingValue = System.getProperty(preferredSettingName, defaultSettingValue);
        return preferredSettingValue == null ? defaultSettingValue : preferredSettingValue;
    }
}
