package portal.database.queries;

public class SettingGroupQueries {

    public static final String GET_SETTING_GROUP_GIVEN_NAME =
            "SELECT TOP 1 *\n" +
                    "FROM [CxsSettings].[dbo].[SettingGroups]\n" +
                    "WHERE Name = '%s'";

    public static final String REMOVE_SETTING_GROUP_GIVEN_NAME =
            "DELETE TOP (1) FROM [CxsSettings].[dbo].[SettingGroups]\n" +
                    "WHERE Name = '%s'";

    public static final String GET_SETTING_GROUP_NAMES_APPLICATIONKEY =
            "SELECT NAME FROM [CxsSettings].[dbo].[SettingGroups] WHERE applicationkey in ( '%s','%s') and StatusTypeId=1 order by name %s";
}
