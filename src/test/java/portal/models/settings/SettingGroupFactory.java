package portal.models.settings;

import java.util.Map;

public class SettingGroupFactory {
    /**
     * Method to create a new SettingGroup Object
     *
     * @param settingGroup setting group hashmap
     * @return SettingGroup object
     */
    public static SettingGroup getSettingGroup(Map<String, String> settingGroup) {
        return SettingGroup.builder()
                .name(settingGroup.get("Name"))
                .description(settingGroup.get("Description"))
                .status(settingGroup.get("Status"))
                .build();
    }

    /**
     * Method to create a SettingGroup Object to update status
     *
     * @param settingGroupId group id
     * @param settingGroupValue setting group hashmap
     * @param newStatus new status
     * @return SettingGroup Object
     */
    public static SettingGroupValue getSettingGroupForUpdate(Integer settingGroupId, Map<String, String> settingGroupValue, String newStatus) {
        return SettingGroupValue.builder()
                .id(settingGroupId)
                .name(settingGroupValue.get("Name"))
                .description(settingGroupValue.get("Description"))
                .status(newStatus)
                .build();
    }
}