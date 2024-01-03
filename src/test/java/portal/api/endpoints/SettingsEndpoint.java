package portal.api.endpoints;

public class SettingsEndpoint {
    public static final String CREATE_SETTING_GROUP = "/settings/application/{applicationKey}/settingGroup";
    public static final String UPDATE_SETTING_GROUP = "/settings/application/{applicationKey}/settingGroup/{settingGroupId}";
    public static final String GET_SETTING_GROUP = "/settings/application/{applicationKey}/settingGroup/{settingGroupId}";
    public static final String GET_SETTING_GROUPS = "/settings/application/{applicationKey}/settingGroups";
    public static final String CREATE_SETTING = "/settings/application/{applicationKey}/settingGroup/{settingGroupId}/setting";
    public static final String UPDATE_SETTING_VALUE = "/settings/application/{applicationKey}/setting/{settingId}/value";
    public static final String UPDATE_SETTING_PROPERTIES = "/settings/application/{applicationKey}/settingGroup/{settingGroupId}/setting/{settingId}";
    public static final String GET_SETTING_BY_ID = "/settings/application/{applicationKey}/setting/{settingId}";
    public static final String GET_SETTING_BY_NAME = "/settings/application/{applicationKey}/settings";
    public static final String GET_SETTING_VERSION_DETAILS = "/settings/application/{applicationKey}/setting/{settingId}/versions";
    public static final String GET_DATA_TYPES = "/settings/application/{applicationKey}/dataTypes";
    public static final String GET_ENUM_DATA_TYPE = "/settings/application/{applicationKey}/dataType/{id}/enums";
}