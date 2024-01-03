package portal.models.applications;

import portal.constants.LogsConstant;
import portal.utils.ScenarioContext;
import portal.utils.IntUtils;
import lombok.Getter;

import java.util.List;
import java.util.logging.Logger;

@Getter
public class Applications {
    private List<Application> applications;

    /**
     * Method to get any application information
     *
     * @return the User
     */
    public Application getRandomApplicationFromList() {
        int randomPosition = IntUtils.getRandomNumber(applications.size());
        Logger logger;
        logger = (Logger) ScenarioContext.getContext(LogsConstant.LOGGER);
        Application application = applications.get(randomPosition);
        logger.info("Random selected application is: " + application.toString());
        return application;
    }
}
