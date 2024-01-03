package portal.models.contents;

import portal.constants.LogsConstant;
import portal.utils.ScenarioContext;
import portal.utils.IntUtils;
import lombok.Getter;

import java.util.List;
import java.util.logging.Logger;

@Getter

public class Contents {
    private List<Content> contents;

    /**
     * Method to get any application information
     *
     * @return the User
     */
    public Content getRandomContentFromList() {
        int randomPosition = IntUtils.getRandomNumber(contents.size());
        Logger logger;
        logger = (Logger) ScenarioContext.getContext(LogsConstant.LOGGER);
        Content content = contents.get(randomPosition);
        logger.info("Random selected content is: " + content.getName());
        return content;
    }

    /**
     * Method to get content with desired type
     *
     * @param contentType Desired content type
     * @return First content that matches given content type
     */
    public Content getContentForType(String contentType) {
        return contents.stream()
                .filter(user -> user.getType().toLowerCase().equals(contentType.toLowerCase()))
                .findFirst().orElse(null);
    }
}
