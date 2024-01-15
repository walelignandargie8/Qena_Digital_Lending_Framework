package portal.steps;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import portal.constants.LogsConstant;
import portal.constants.PathConstants;
import portal.utils.PropertiesReader;
import portal.utils.ScenarioContext;
import portal.ui.pages.GenericPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import org.apache.commons.io.output.WriterOutputStream;
import portal.utils.StringUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class Hooks {

    public static final String PATH_SCREENSHOTS = "target/build/screenshots";

    @Before(value = "@ui", order = 1)
    public void beforeScenario(Scenario scenario) throws IOException {
        initializeUIBrowserAccordingToSettings();
        Configuration.baseUrl = PropertiesReader.getDefaultOrPreferredSetting("baseURL", "baseURL");
        InitializeLogger(scenario);
        initializeAPI();
        open(PropertiesReader.getDefaultOrPreferredSetting("baseURL", "baseURL"));
    }

    private void initializeUIBrowserAccordingToSettings() {
        Configuration.browser = PropertiesReader.getDefaultOrPreferredSetting("defaultBrowser", "selenide.browser");
        Configuration.reportsFolder = PATH_SCREENSHOTS;
        Configuration.browserSize = PropertiesReader.getDefaultOrPreferredSetting("defaultBrowserSize", "selenide.browserSize");
        Configuration.timeout = Long.parseLong(PropertiesReader.getDefaultOrPreferredSetting("defaultTimeOut", "selenide.timeout"));
        Configuration.pageLoadTimeout = Long.parseLong(PropertiesReader.getDefaultOrPreferredSetting("defaultPageLoadTimeOut", "selenide.pageLoadTimeout"));
        Configuration.headless = Boolean.parseBoolean(PropertiesReader.getDefaultOrPreferredSetting("headlessMode", "selenide.headless"));
        Configuration.screenshots = true;
    }

    @Before("@apiAndUI")
    public void beforeScenarioApiWithUI(Scenario scenario) throws IOException {
        initializeUIBrowserAccordingToSettings();
        initializeAPI();
        InitializeLogger(scenario);
        open(PropertiesReader.getDefaultOrPreferredSetting("oicdToken", "oicdToken"));
    }

    private void initializeAPI() throws IOException {
        RestAssured.baseURI = PropertiesReader.getDefaultOrPreferredSetting("gatewayApiEndpoint", "gatewayApiEndpoint");
        FileWriter fileWriter = new FileWriter(PathConstants.API_LOGS_PATH);
        @SuppressWarnings("deprecation") PrintStream printStream = new PrintStream(new WriterOutputStream(fileWriter), true);
        RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().defaultStream(printStream));
    }

    @Before("@api")
    public void beforeScenarioAPI(Scenario scenario) throws IOException {
        initializeAPI();
        InitializeLogger(scenario);
    }

    @After(value = "@apiAndUI", order = 1)
    public void afterScenarioApiAndUI(Scenario scenario) {
        addTestEvidenceToScenario(scenario);
    }

    @After(value = "@ui", order = 1)
    public void afterScenarioUI(Scenario scenario) {
        addTestEvidenceToScenario(scenario);
    }

    private void addTestEvidenceToScenario(Scenario scenario) {
        Logger logger = (Logger) ScenarioContext.getContext(LogsConstant.LOGGER);
        try {
            if (scenario.isFailed()) {
                Path path = Screenshots.getLastThreadScreenshot().get().toPath();
                String imagePath = path.toString();
                imagePath = StringUtils.convertToWindowsPath(imagePath);
                String sourcePagePath = imagePath.replace(".png", ".html");
                if (StringUtils.verifyIfImageExist(imagePath)) {
                    scenario.embed(StringUtils.getImageAsArrayOfBytes(imagePath), "image/png", "Image");
                    scenario.embed(StringUtils.getDocumentAsArrayOfBytes(sourcePagePath), "text/html", "HTML Page");
                }
            }
        } catch (NoSuchElementException exception) {
            logger.warning(String.format("Failed to get screenshot for the Test Case '%s' with the following error:\n%s",
                    scenario.getName(), exception.getMessage()));
        }
        finally {
            logger.info(String.format("------- After '%s' Test ----------------------------", scenario.getName()));
            closeWebDriver();
        }
    }

    private void InitializeLogger(Scenario scenario) {
        Logger logger;
        if (ScenarioContext.Contains(LogsConstant.LOGGER)) {
            logger = (Logger) ScenarioContext.getContext(LogsConstant.LOGGER);
        } else {
            logger = Logger.getLogger(GenericPage.class.getName());
            FileHandler fh = null;
            try {
                fh = new FileHandler(PathConstants.UI_LOGS_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
            logger.addHandler(fh);
            ScenarioContext.setContext(LogsConstant.LOGGER, logger);
        }
        logger.info(String.format("------- Before '%s' Test ----------------------------", scenario.getName()));
    }
}
