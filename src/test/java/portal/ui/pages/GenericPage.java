package portal.ui.pages;

import portal.utils.PropertiesReader;

public abstract class GenericPage<T extends GenericPage<T>> {

    public static final String LOGGER = "logger";
    public static final int WAITING_FOR_FIELD_TIME = 50000;
    public static final int WAIT_SECONDS = Integer.parseInt(
            PropertiesReader.getDefaultOrPreferredSetting("defaultPageLoadTimeOut", "selenide.pageLoadTimeout")) / 1000;


}