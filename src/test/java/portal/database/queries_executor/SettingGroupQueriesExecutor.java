package portal.database.queries_executor;

import portal.database.queries.SettingGroupQueries;
import portal.database.queries.SettingQueries;
import portal.utils.IntUtils;

import java.util.*;

public class SettingGroupQueriesExecutor extends BaseQueriesExecutor<SettingGroupQueriesExecutor> {
    public static SettingGroupQueriesExecutor getSettingGroupQueriesExecutor() {
        return new SettingGroupQueriesExecutor();
    }

    /**
     * Method to get the setting group from the DB
     *
     * @param name setting group name
     * @return setting group row or null in case that the setting group is not found
     */
    public HashMap<String, Object> getSettingGroup(String name) {
        logInformation("About to get setting group with name '%s' from the Database", name);
        return getFirstRowFromResultSet(buildQuery(SettingGroupQueries.GET_SETTING_GROUP_GIVEN_NAME, name));
    }

    /**
     * Method to delete a setting group from the DB
     *
     * @param name setting group name
     */
    public void deleteSettingGroup(String name) {
        logInformation("About to delete the setting group : " + name);
        executeUpdate(buildQuery(SettingGroupQueries.REMOVE_SETTING_GROUP_GIVEN_NAME, name));
    }

    /**
     * Method to get the setting group from the DB suing applicationKey
     *
     * @param childApplicationKey child application key
     * @param rootApplicationKey  root application key
     * @return setting group row or null in case that the setting group is not found
     */
    public List<HashMap<String, Object>> getSettingGroupNames_ByApplicationKeys(String childApplicationKey, String rootApplicationKey, String order) {
        logInformation("About to get setting group with applicationKey '%s' from the Database", childApplicationKey);
        return executeScriptForHashMap(buildQuery(SettingGroupQueries.GET_SETTING_GROUP_NAMES_APPLICATIONKEY, childApplicationKey, rootApplicationKey, order));
    }

    /**
     * Method to get the settingsGroupID from the DB using application key
     *
     * @param ApplicationKey setting groupID
     * @return settings Group Id row or null in case that the Settings count>3 is not found
     */
    public List<HashMap<String, Object>> getSettingGroupID_ByApplication(String ApplicationKey) {
        logInformation("About to get settings with ApplicationKey '%s' from the Database", ApplicationKey);
        return executeScriptForHashMap(buildQuery(SettingQueries.GET_SETTING_GROUPID_GIVEN_APPLICATION_KEY, ApplicationKey));
    }

    /**
     * Method to get random settingsGroupID from DB
     *
     * @param applicationKey applicationKey
     * @return settingGroupID
     */
    public int getSettingGroupID_ByApplicationKey(String applicationKey) {
        List<HashMap<String, Object>> ListOfSettingGroupIDFromDB = getSettingGroupID_ByApplication(applicationKey);
        ArrayList<Integer> settingGroupIDArrayList = new ArrayList<>();
        for (HashMap<String, Object> entry : ListOfSettingGroupIDFromDB) {
            Set<String> setOf = entry.keySet();
            Iterator<String> iterator = setOf.iterator();
            while (iterator.hasNext()) {
                String keyIs1 = iterator.next();
                settingGroupIDArrayList.add((Integer) entry.get(keyIs1));
            }
        }
        return settingGroupIDArrayList.get(IntUtils.getRandomNumber(settingGroupIDArrayList.size()));
    }
}