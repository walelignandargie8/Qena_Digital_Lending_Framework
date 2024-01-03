# Logs
Logs information is being created in different files, one for **UI logs** (UI_Logging.log) and one for API logs (API_Logging).

The UI one displays the initialization of an scenario with the scenario name like this:
```
------- Before 'Scenario name' Test ----------------------------
```
And the following text after the scenario
```
------- After 'Scenario name' Test ----------------------------
```

For adding more information to the scenario execution from a steps class, you can use the method:
```
        LogInformationMessage(String)
        LogWarningMessage(String)
```
And if you want to from an other class you need to extract the Logger from the scenario context information:
```
        Logger logger;
        logger = (Logger) ScenarioContext.getContext(LOGGER);
            logger.info(String);
            logger.warning(String);
```

### API logs
For adding logs related to API calls we can use `log().all().` methods to log in the file **API_Logging.txt** 
z