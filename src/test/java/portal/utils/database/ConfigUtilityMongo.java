package portal.utils.database;

import portal.utils.PropertiesReader;

public class ConfigUtilityMongo {
    public static ConfigUtilityMongo getConfigUtility() {
        return new ConfigUtilityMongo();
    }

    public String getAppHost() {
        return PropertiesReader.getDefaultOrPreferredSetting("communications.mongodb.host", "communications.mongodb.host");
    }

    public int getPortNumber() {
        return Integer.parseInt(
                PropertiesReader.getDefaultOrPreferredSetting("communications.mongodb.port", "communications.mongodb.port"));
    }

    public String getAppDBName() {
        return PropertiesReader.getDefaultOrPreferredSetting("communications.mongodb.dbname", "communications.mongodb.dbname");
    }

    public String getUsername() {
        return PropertiesReader.getDefaultOrPreferredSetting("communications.mongodb.username", "communications.mongodb.username");
    }

    public String getPassword() {
        return PropertiesReader.getDefaultOrPreferredSetting("communications.mongodb.password", "communications.mongodb.password");
    }
}
