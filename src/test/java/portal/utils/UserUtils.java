package portal.utils;

import portal.constants.LogsConstant;
import portal.models.users.Users;

import java.util.logging.Logger;

public class UserUtils {
    /**
     * Util method to get all the users from external file
     *
     * @return the list of Users
     */
    public static Users getUsers() {
        Logger logger;
        logger = (Logger) ScenarioContext.getContext(LogsConstant.LOGGER);
        logger.info("About to get the users information from external file");
        String fileName = String.format("Users%s.yaml",
                PropertiesReader.getDefaultOrPreferredSetting("environment", "environment"));
        return TestDataReader.readData(fileName, Users.class);
    }
}