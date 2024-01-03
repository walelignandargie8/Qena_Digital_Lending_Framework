package portal.utils;

import portal.constants.LogsConstant;
import portal.models.localizations.Localization;
import portal.models.localizations.Localizations;
import org.apache.commons.text.StringEscapeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocalizationUtils {

    /**
     * Util method to get all the localization details from an external file
     *
     * @return all the localizations
     */
    public static Localizations getLocalizationDetails() {
        Logger logger;
        logger = (Logger) ScenarioContext.getContext(LogsConstant.LOGGER);
        logger.info("About to get the localization information from external file");
        String fileName = String.format("Localizations%s.yaml",
                PropertiesReader.getDefaultOrPreferredSetting("environment", "environment"));
        return TestDataReader.readData(fileName, Localizations.class);
    }

    /**
     * Method to get page localization by page name from an external file
     *
     * @param pageName page name
     * @return localization detail
     */
    public static Localization getLocalizationByPageName(String pageName) {
        Localizations localizationDetails = getLocalizationDetails();
        List<Localization> localizationData = localizationDetails.getLocalizations();
        Localization localization = localizationData.stream()
                .filter(localData -> localData.getPageName().equalsIgnoreCase(pageName))
                .findFirst().orElse(null);
        if (localization == null) {
            try {
                throw new Exception(
                        String.format("The localization detail of '%s' was not found in the external file Localization.yaml", pageName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return localization;
    }

    /**
     * Method to get list of localization records name list by record type
     *
     * @param recordType       record type (content,setting)
     * @param localizationData localization detail
     * @return record name list
     */
    public static List<String> getListOfLocalizationNames(String recordType, Localization localizationData) {
        List<String> namesList = new ArrayList<>();
        if (recordType.equalsIgnoreCase("content")) {
            localizationData.getContentsList().forEach(contents -> namesList.addAll(contents.values()));
        } else {
            localizationData.getSettingsList().forEach(settings -> namesList.addAll(settings.values()));
        }
        return namesList;
    }


    /***
     * Method to create a localization hash map
     * @param recordType record type
     * @param localizationData localization data
     * @return hash map
     */
    public static Map<String, String> createLocalizationHash(String recordType, Localization localizationData) {
        Map<String, String> localizationHash = new HashMap<>();
        if (recordType.equalsIgnoreCase("content")) {
            for (int listIndex = 0; listIndex < localizationData.getContentsList().size(); listIndex++) {
                localizationHash.put(localizationData.getContentsList().get(listIndex).keySet().toString()
                        .replaceAll("[\\[\\]]", ""), localizationData.getContentsList()
                        .get(listIndex).values().toString().replaceAll("[\\[\\]]", ""));
            }
        } else {
            for (int listIndex = 0; listIndex < localizationData.getSettingsList().size(); listIndex++) {
                localizationHash.put(localizationData.getSettingsList().get(listIndex).keySet().toString()
                        .replaceAll("[\\[\\]]", ""), localizationData.getSettingsList()
                        .get(listIndex).values().toString().replaceAll("[\\[\\]]", ""));
            }
        }
        return localizationHash;
    }

    /**
     * Method to get a string where HTML codes are replace with equivalent characters
     *
     * @param HTMLValueToReplace HTML value to replace
     * @return string with replaced code
     */
    public static String replaceAnHTMLValueCode(String HTMLValueToReplace) {
        Pattern pattern = Pattern.compile("&rsquo;");
        Matcher matcher = pattern.matcher(HTMLValueToReplace);
        String replaceValue = matcher.replaceAll("’");
        return StringEscapeUtils.unescapeHtml3(replaceValue);
    }
}