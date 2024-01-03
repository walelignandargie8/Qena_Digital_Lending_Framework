package portal.constants;


public class PathConstants {

    public static final String LOGS_BASE_PATH = "src/tmp/Logs/";
    public static final String API_LOGS_PATH = LOGS_BASE_PATH + "API_Logging.txt";
    public static final String UI_LOGS_PATH = LOGS_BASE_PATH + "UI_Logging.log";
    public static final String HANGFIRE_SCHEDULED_PATH = "/hangfire/jobs/scheduled";
    public static final String HEALTH_URL = "/healthchecks-ui#/healthchecks";
    public static final String HEALTH_STATUS = "/health";
    public static final String HEALTH_READ_STATUS = "/health/ready";
    public static final String HEALTH_LIVE_STATUS = "/health/live";
    public static final String PATH_DOWNLOADS = "build/downloads";
    }
