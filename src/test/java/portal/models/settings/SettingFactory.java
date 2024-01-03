package portal.models.settings;

import java.util.Map;

public class SettingFactory {

    public static final String NAME = "Name";
    public static final String DESCRIPTION = "Description";
    public static final String DATA_TYPE = "DataType";
    public static final String VALIDATION_PATTERN = "ValidationPattern";
    public static final String STATUS = "Status";
    public static final String VALUE = "Value";
    public static final String ORIGINAL_VALUE = "OriginalValue";
    public static final String ENUM_DATA_TYPE = "EnumDataType";
    public static final String UPDATED_VALUE = "UpdatedValue";

    /**
     * Method to create Setting object
     *
     * @param setting Map with Setting properties
     * @return Setting object
     */
    public static Setting getSetting(Map<String, String> setting) {
        return Setting.builder()
                .name(setting.get(NAME))
                .description(setting.get(DESCRIPTION))
                .value(setting.get(VALUE))
                .dataType(setting.get(DATA_TYPE))
                .enumDataType(setting.get(ENUM_DATA_TYPE))
                .validationPattern(setting.get(VALIDATION_PATTERN))
                .status(setting.get(STATUS))
                .originalValue(ORIGINAL_VALUE)
                .updatedValue(UPDATED_VALUE)
                .build();
    }

    public static Setting getSettingFromTable(SettingTable settingTable){
        return Setting.builder()
                .name(settingTable.getName())
                .description(settingTable.getDescription())
                .value(settingTable.getValue())
                .status("Active")
                .dataType(settingTable.getDataType())
                .enumDataType(settingTable.getEnumDataType())
                .validationPattern(settingTable.getValidationPattern())
                .build();
    }

    /**
     * Method to update the setting Value
     *
     * @param settingGroupId setting group ID
     * @param setting        Map with setting properties
     * @param newValue       new value
     * @return SettingValue object
     */
    public static SettingValue getSettingValue(Integer settingGroupId, Map<String, String> setting, String newValue) {
        return SettingValue.builder()
                .settingGroupId(settingGroupId)
                .dataType(setting.get(DATA_TYPE))
                .value(newValue)
                .build();
    }

    /**
     * Method to update the setting Value
     *
     * @param settingGroupId setting group ID
     * @param dataType       setting dataType
     * @return SettingValue object
     */
    public static SettingValue getSettingValueAsNull(Integer settingGroupId, String dataType) {
        return SettingValue.builder()
                .settingGroupId(settingGroupId)
                .dataType(dataType)
                .build();
    }

    /**
     * Method to update the setting properties
     *
     * @param settingGroupId setting group ID
     * @param setting        Map with new setting properties
     * @return SettingProperties object
     */
    public static SettingProperties getSettingProperties(Integer settingGroupId, Map<String, String> setting) {
        return SettingProperties.builder()
                .settingGroupId(settingGroupId)
                .name(setting.get(NAME))
                .description(setting.get(DESCRIPTION))
                .dataType(setting.get(DATA_TYPE))
                .validationPattern(setting.get(VALIDATION_PATTERN))
                .status(setting.get(STATUS))
                .build();
    }

    /**
     * Method to try to update the setting Enum Data type
     *
     * @param settingGroupId setting group ID
     * @param settingData  Map with new Enum Data type
     * @return Setting Value object
     */
    public static SettingValue getEnumDataType(Integer settingGroupId, Map<String, String> settingData) {
        return SettingValue.builder()
                .settingGroupId(settingGroupId)
                .name(settingData.get(NAME))
                .dataType(settingData.get(DATA_TYPE))
                .value(settingData.get(VALUE))
                .enumDataType(settingData.get(ENUM_DATA_TYPE))
                .build();
    }
}