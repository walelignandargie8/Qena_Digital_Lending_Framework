package portal.utils;

import portal.constants.LogsConstant;
import portal.models.contents.Content;
import portal.models.contents.Contents;

import java.util.Objects;
import java.util.logging.Logger;

public class ContentUtils {
    /**
     * Util method to get all the Contents from an external file
     *
     * @return all the applications
     */
    public static Contents getContents() {
        Logger logger;
        logger = (Logger) ScenarioContext.getContext(LogsConstant.LOGGER);
        logger.info("About to get the Contents information from external file");
        return TestDataReader.readData("Content.yaml", Contents.class);
    }

    /**
     * Util method to get one random Content from list
     *
     * @return One random Content
     */
    public static Content getRandomContent() {
        Contents contents = getContents();
        Logger logger;
        logger = (Logger) ScenarioContext.getContext(LogsConstant.LOGGER);
        logger.info("About to select a random Content from external file");
        if (contents == null)
            logger.warning("An error occurred while loading the file");
        return Objects.requireNonNull(contents).getRandomContentFromList();
    }
    /**
     * Util method to get the first  Content from list that is related with the given type
     *
     * @return First Content for type
     */
    public static Content getContentForType(String contentType) {
        Contents contents = getContents();
        Logger logger;
        logger = (Logger) ScenarioContext.getContext(LogsConstant.LOGGER);
        logger.info("About to select a random Content from external file");
        if (contents == null)
            logger.warning("An error occurred while loading the file");
        return Objects.requireNonNull(contents).getContentForType(contentType);
    }
}
