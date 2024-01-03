package portal.utils;

import portal.constants.LogsConstant;
import portal.models.communications.Communications;

import java.util.logging.Logger;

public class CommunicationUtils {
    /**
     * Util method to get all the Communications from external file
     * @return the list of Communications
     */
    public static Communications getCommunications() {
        Logger logger;
        logger = (Logger) ScenarioContext.getContext(LogsConstant.LOGGER);
        logger.info("About to get the Communications information from external file");
        String fileName = String.format("Communications%s.yaml",
                PropertiesReader.getDefaultOrPreferredSetting("environment", "environment"));
        return TestDataReader.readData(fileName, Communications.class);
    }
}
