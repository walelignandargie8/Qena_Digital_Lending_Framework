package portal.database.queries_executor;

import portal.database.queries.SettingQueries;

import java.util.HashMap;
import java.util.List;

public class SettingQueriesExecutor extends BaseQueriesExecutor<SettingQueriesExecutor> {
    public static SettingQueriesExecutor getSettingQueriesExecutor() {
        return new SettingQueriesExecutor();
    }

    /**
     * Method to get the setting from the database given the setting name
     *
     * @param name setting name
     * @return setting row
     */
    public HashMap<String, Object> getSetting(String name) {
        logInformation("About to get the setting from the database with the name '%s'", name);
        return getFirstRowFromResultSet(buildQuery(SettingQueries.GET_FIRST_SETTING_GIVEN_NAME, name));
    }

    public HashMap<String, Object> getLastValueForSetting(int settingId, String applicationKey) {
        return getFirstRowFromResultSet(buildQuery(SettingQueries.GET_LATEST_VERSION_GIVEN_SETTING_ID_AND_APPLICATION_KEY, settingId, applicationKey));
    }

    public HashMap<String, Object> getEnumDataTypeId(String name) {
        logInformation("About to get the enum data type from the database with the name '%s'", name);
        return getFirstRowFromResultSet(buildQuery(SettingQueries.GET_ENUM_DATA_TYPES, name));
    }

    /**
     * Method to get the application setting given the settingId
     *
     * @param settingId Setting Id
     * @return the application setting row
     */
    public HashMap<String, Object> getApplicationSetting(int settingId) {
        logInformation("About to get the application setting from the database with the ID '%s'", settingId);
        return getFirstRowFromResultSet(buildQuery(SettingQueries.GET_APPLICATION_SETTING_GIVEN_SETTING_ID, settingId));
    }

    /**
     * Method to get the application setting version given the settingId
     *
     * @param settingId Setting Id
     * @return the application setting row
     */
    public HashMap<String, Object> getVersion(int settingId) {
        logInformation("About to get the application setting versions from the database with the ID '%s'", settingId);
        return getFirstRowFromResultSet(buildQuery(SettingQueries.GET_VERSION_SETTING_ID, settingId));
    }

    /**
     * Method to get the latest application setting version given the settingId and applicationKey
     *
     * @param settingId      Setting Id
     * @param ApplicationKey Application Key
     * @param version        Version
     * @return the application setting row
     */

    public HashMap<String, Object> getLatestVersionGivenSettingIdAndApplicationKey(int settingId, String ApplicationKey, int version) {
        logInformation("About to get the latest QA child application setting versions from the database with the ID '%s' + '' + '%s' + '' + '%s'", settingId, ApplicationKey, version);
        return getFirstRowFromResultSet(buildQuery(SettingQueries.GET_LATEST_VERSION_GIVEN_SETTING_ID_AND_APPLICATION_KEY, settingId, ApplicationKey, version));
    }

    /**
     * Method to delete the setting and application setting version
     *
     * @param name Setting Name
     */
    public void deleteSetting(String name) {
        logInformation("About to delete setting and application setting version");
        HashMap<String, Object> setting = getSetting(name);
        if (setting != null && !setting.isEmpty()) {
            int settingId = (int) setting.get("Id");
            executeUpdate(buildQuery(SettingQueries.DELETE_SETTING_GIVEN_ID, settingId));
            executeUpdate(buildQuery(SettingQueries.DELETE_SETTING_VERSION_GIVEN_ID, settingId));
            executeUpdate(buildQuery(SettingQueries.DELETE_APPLICATION_SETTING_GIVEN_ID, settingId));
        }
    }

    /**
     * Method to get the settings Names from the DB using Setting Group ID
     *
     * @param settingGroupID setting groupID
     * @param order          settings sorting order
     * @return settings names row or null in case that the settings name is not found
     */
    public List<HashMap<String, Object>> getSettingsNames_BySettingGroupID(String settingGroupID, String order) {
        logInformation("About to get settings with Setting groupsID '%s' from the Database", settingGroupID);
        return executeScriptForHashMap(buildQuery(SettingQueries.GET_SETTINGS_NAMELIST_SETTINGGROUPID, settingGroupID, order));
    }

    /**
     * Method to update the publish date of a setting
     *
     * @param name        setting name
     * @param publishDate desired publish date
     */
    public void updatePublishDate(String name, String publishDate) {
        logInformation("About to update the publishDate for the Setting : " + name);
        executeUpdate(buildQuery(SettingQueries.UPDATE_SETTING_PUBLISH_DATE, publishDate, name));
    }

    /**
     * Method to get all data types of setting object from DB
     *
     * @return List of data types
     */
    public List<HashMap<String, Object>> getAllDataTypes() {
        logInformation("About to get all data types from the database");
        return executeScriptForHashMap(buildQuery(SettingQueries.GET_DATA_TYPES));
    }

    public List<HashMap<String, Object>> getDataTypesAfterSortAndOrder(String sortBy, String orderBy) {
        logInformation("About to get all data types sort by '%s' and order by '%s' from db", sortBy, orderBy);
        return executeScriptForHashMap(buildQuery(SettingQueries.GET_DATA_TYPES_SORT_AND_ORDER_BY, sortBy, orderBy));
    }

    public List<HashMap<String, Object>> getDataTypesByNameAndExactMatch(String dataTypeName, String exactMatch) {
        logInformation("About to get all data types by '%s' and exactMatch '%s' from db", dataTypeName, exactMatch);
        if (exactMatch.equalsIgnoreCase("true"))
            return executeScriptForHashMap(buildQuery(SettingQueries.GET_DATA_TYPES_BY_NAME_EXACT_MATCH, dataTypeName));
        else
           return executeScriptForHashMap(SettingQueries.getDataTypeByName(dataTypeName));


    }

    /**
     * Method to get Enum data types  from DB
     *
     * @param dataTypeId dataTypeId of Enum
     * @return List of Enum data types
     */
    public List<HashMap<String, Object>> getEnumDataTypes(Integer dataTypeId) {
        logInformation("About to get Enum data types from the database");
        return executeScriptForHashMap(buildQuery(SettingQueries.GET_ENUM_DATA_TYPES,dataTypeId));
    }

    /**
     * A method that retrieves the EnumDataTypeTd from DataTypes table
     * @param name - Data Type Name
     * @return Integer - id of enum data type from GetDataTypes table
     */
    public Integer getEnumDataTypeIdFromDataTypesTable(String name) {
        Integer enumDataTypeId = 0;
        logInformation("About to get the enum data type id from the DataTypes table with the name '%s'", name);
        HashMap<String, Object> enumDataTypeIDMap =  getFirstRowFromResultSet(buildQuery(SettingQueries.GET_ENUM_DATA_TYPE_ID, name));
        if (enumDataTypeIDMap != null && !enumDataTypeIDMap.isEmpty()) {
            enumDataTypeId = (Integer) enumDataTypeIDMap.get("Id");

        }
        return enumDataTypeId;
    }

    /**
     *
     * @param enumDataTypeId - Enum data type id
     * @param sortBy -  sortType
     * @param orderBy - sortDirection
     * @return
     */
    public List<HashMap<String, Object>> getEnumDataTypesAfterSortAndOrder(Integer enumDataTypeId,String sortBy, String orderBy) {
        logInformation("About to get all data types sort by '%s' and order by '%s' from db", sortBy, orderBy);
        return executeScriptForHashMap(buildQuery(SettingQueries.GET_ENUM_DATA_TYPES_SORT_AND_ORDER_BY,enumDataTypeId, sortBy, orderBy));
    }

    public List<HashMap<String,Object>> getEnumDataTypesWithOptionCount(){
        logInformation("About to get all Enum data types which has options");
        return executeScriptForHashMap(buildQuery(SettingQueries.GET_ENUM_DATA_TYPES_WITH_OPTION_COUNT));

    }
}
