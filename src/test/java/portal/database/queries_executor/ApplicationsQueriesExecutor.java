package portal.database.queries_executor;

import portal.constants.LogsConstant;
import portal.database.queries.ApplicationQueries;
import portal.models.applications.Application;
import portal.utils.ScenarioContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;


public class ApplicationsQueriesExecutor extends BaseQueriesExecutor<ApplicationsQueriesExecutor> {

    public static final String ID = "Id";
    public static final String KEY = "ApplicationKey";
    public static final int FIRST_INDEX = 0;

    public static ApplicationsQueriesExecutor getApplicationsScriptExecutor() {
        return new ApplicationsQueriesExecutor();
    }

    /**
     * Removes the given application using its name and value trough data base
     *
     * @param applicationToRemove desired application to remove
     */
    public void deleteApplication(Application applicationToRemove) {
        String sqlStatementToDeleteAnApplication =
                buildQuery(
                        ApplicationQueries.SQL_STATEMENT_TO_DELETE_AN_APPLICATION,
                        applicationToRemove.getName(),
                        applicationToRemove.getValue());
        executeUpdate(sqlStatementToDeleteAnApplication);
    }

    /**
     * Method to get the application ID
     *
     * @param applicationName application name
     * @return the application id
     */
    public int getApplicationId(String applicationName) {
        List<HashMap<String, Object>> resultSetAsMap = executeScriptForHashMap(buildQuery(ApplicationQueries.GET_APPLICATION_WITH_NAME, applicationName));
        return (int) resultSetAsMap.get(FIRST_INDEX).get(ID);
    }

    /**
     * Method to get a random application
     *
     * @return random application
     */
    public HashMap<String, Object> getRandomApplication() {
        List<HashMap<String, Object>> resultSetAsMap = executeScriptForHashMap(ApplicationQueries.GET_RANDOM_APPLICATION);
        return resultSetAsMap.get(FIRST_INDEX);
    }

    public void removeApplicationGroup(String applicationGroup) {
        executeUpdate(buildQuery(ApplicationQueries.DELETE_APPLICATION_GROUP, applicationGroup));
    }
    /**
     * Method to get the file as string
     *
     * @param file file
     * @return string
     * @throws IOException
     */
    public static String getFileAsString(File file) throws IOException {
        return new String(Files.readAllBytes(file.getAbsoluteFile().toPath()));
    }
    /**
     * Method to get the application Key
     *
     * @param applicationName application name
     * @return the application key
     */
    public String getApplicationKey(String applicationName) {
        List<HashMap<String, Object>> resultSetAsMap =
                executeScriptForHashMap(buildQuery(ApplicationQueries.GET_APPLICATION_WITH_NAME, applicationName));
        String applicationKey = null;
        try {
            applicationKey = (String) resultSetAsMap.get(FIRST_INDEX).get(KEY);
        } catch (IndexOutOfBoundsException e) {
            Logger logger;
            logger = (Logger) ScenarioContext.getContext(LogsConstant.LOGGER);
            logger.warning(
                    "There are no results for get application Key for application with Name: "
                            + applicationName);
        }
        return applicationKey;
    }
}
