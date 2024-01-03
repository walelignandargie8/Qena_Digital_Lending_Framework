package portal.api.services;

import portal.constants.LogsConstant;
import portal.utils.ScenarioContext;
import portal.utils.TestContext;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Cookie;
import io.restassured.specification.RequestSpecification;

import java.util.logging.Logger;

@SuppressWarnings("unchecked")
public abstract class GenericService<T extends GenericService<T>> {

    protected Cookie cookieSessionId;
    public TestContext testContext;

    /**
     * Method to set the Cookie of the session Id
     *
     * @param cookieSessionId Cookie of session Id
     */
    public T setCookieSessionId(Cookie cookieSessionId) {
        this.cookieSessionId = cookieSessionId;
        return (T)this;
    }

    /***
     * Logs information using the common logger
     * @param message principal message
     * @param parameters possible parameters to add
     */
    public void logInformationMessage(String message, Object... parameters) {
        Logger logger = (Logger) ScenarioContext.getContext(LogsConstant.LOGGER);
        logger.info(String.format(message, parameters));
    }

    /**
     * Logs a warning message
     *
     * @param message desired message to Log
     */
    public void LogWarningMessage(String message) {
        Logger logger;
        logger = (Logger) ScenarioContext.getContext(LogsConstant.LOGGER);
        logger.warning(message);
    }

    /***
     * Logs information using the common logger
     * @param message principal message
     * @param parameters possible parameters to add
     */
    public void logWarningMessage(String message, Object... parameters) {
        Logger logger = (Logger) ScenarioContext.getContext(LogsConstant.LOGGER);
        logger.warning(String.format(message, parameters));
    }

    /**
     * Method to create Request Specifications for Login Authentication
     * @param username username
     * @param password password
     * @return RequestSpecification instance
     */
    public RequestSpecification getRequestSpecification(String username, String password) {
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName(username);
        authScheme.setPassword(password);

        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setAuth(authScheme)
                .build();
        return requestSpecification;
    }
}
