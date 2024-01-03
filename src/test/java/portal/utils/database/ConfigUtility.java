package portal.utils.database;

import portal.utils.PropertiesReader;

public class ConfigUtility {
    public static ConfigUtility getConfigUtility() {
        return new ConfigUtility();
    }

    public String getAppHost() {
        return PropertiesReader.getDefaultOrPreferredSetting("contentManagement.db.host", "contentManagement.db.host");
    }

    public int getPortNumber() {
        return Integer.parseInt(
                PropertiesReader.getDefaultOrPreferredSetting("contentManagement.db.portNumber", "contentManagement.db.portNumber"));
    }

    public String getAppDBName() {
        return PropertiesReader.getDefaultOrPreferredSetting("contentManagement.db.dbname", "contentManagement.db.dbname");
    }

    public String getUsername() {
        return PropertiesReader.getDefaultOrPreferredSetting("contentManagement.db.username", "contentManagement.db.username");
    }

    public String getPassword() {
        return PropertiesReader.getDefaultOrPreferredSetting("contentManagement.db.password", "contentManagement.db.password");
    }

    public boolean isWindowsAuth() {
        return Boolean.parseBoolean(
                PropertiesReader.getDefaultOrPreferredSetting("contentManagement.db.windows.auth","contentManagement.db.windows.auth"));
    }

}
