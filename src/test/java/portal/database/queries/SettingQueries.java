package portal.database.queries;

public class SettingQueries {

    public static final String GET_FIRST_SETTING_GIVEN_NAME =
            "SELECT TOP 1 *\n" +
                    "FROM [CxsSettings].[dbo].[Settings]\n" +
                    "WHERE Name = '%s'";

    public static final String DELETE_SETTING_GIVEN_ID =
            "DELETE FROM [CxsSettings].[dbo].[Settings]\n" +
                    "WHERE Id = %s";

    public static final String DELETE_SETTING_VERSION_GIVEN_ID =
            "DELETE FROM [CxsSettings].[dbo].[ApplicationSettingVersions]\n" +
                    "WHERE SettingId = %s";

    public static final String DELETE_APPLICATION_SETTING_GIVEN_ID =
            "DELETE FROM [CxsSettings].[dbo].[ApplicationSettings]\n" +
                    "WHERE SettingId = %s";

    public static final String GET_APPLICATION_SETTING_GIVEN_SETTING_ID =
            "SELECT TOP 1*\n" +
                    "FROM [CxsSettings].[dbo].[ApplicationSettings]\n" +
                    "WHERE SettingId = %s";

    public static final String GET_VERSION_SETTING_ID =
            "SELECT TOP 1*\n" +
                    "FROM [CxsSettings].[dbo].[ApplicationSettingVersions]\n" +
                    "WHERE SettingId = %s";

    public static final String GET_LATEST_VERSION_GIVEN_SETTING_ID_AND_APPLICATION_KEY =
            "SELECT Top 1*\n" +
                    "FROM [CxsSettings].[dbo].[ApplicationSettingVersions]\n" +
                    "WHERE SettingId = %s\n and ApplicationKey = '%s'\n" +
                    "ORDER BY Version Desc";

    public static final String GET_SETTING_GROUPID_GIVEN_APPLICATION_KEY =
            "select SettingGroupId\n" +
                    "from [CxsSettings].[dbo].[Settings]\n" +
                    "where SettingGroupId in (select id from SettingGroups where ApplicationKey='%s') \n" +
                    "group by SettingGroupId having count(SettingGroupId)>3 order by count(SettingGroupId) ASC;";
       public static String GET_SETTINGS_NAMELIST_SETTINGGROUPID =
            "select name\n" +
                    "from [CxsSettings].[dbo].[Settings]\n" +
                    "where SettingGroupId=%s and StatusTypeId=1 order by name %s;";

    public static String GET_ENUM_DATA_TYPE_NAME =
            "SELECT TOP (1) *\n" +
                    "FROM [CxsSettings].[dbo].[EnumDataTypes]\n" +
                    "WHERE Name='%s';";

    public static final String UPDATE_SETTING_PUBLISH_DATE =
                    "update CxsSettings.dbo.ApplicationSettings\n" +
                    "set ModifiedDate = '%s 20:00:13.0411495 -04:00'\n" +
                    "where SettingId = (select Id from CxsSettings.dbo.Settings where name = '%s')";


    public static String GET_DATA_TYPES =
            "SELECT * FROM [CxsSettings].[dbo].[DataTypes]";

    public static String GET_DATA_TYPES_SORT_AND_ORDER_BY =
            "SELECT *\n" +
                    "FROM [CxsSettings].[dbo].[DataTypes]\n" +
                    "ORDER BY %s %s";

    public static String GET_DATA_TYPES_BY_NAME_EXACT_MATCH =
            "SELECT *\n" +
                    "FROM [CxsSettings].[dbo].[DataTypes]\n" +
                    "WHERE NAME = '%s'";

    public static String getDataTypeByName(String dataTypeName){
        return "SELECT * FROM [CxsSettings].[dbo].[DataTypes] WHERE NAME like '%%" + dataTypeName +"%%'";

    }

    public static String GET_ENUM_DATA_TYPES =
            "SELECT *\n" +
                    "  FROM [CxsSettings].[dbo].[EnumDataTypes]\n" +
                    "  WHERE Name = '%s'";

    public static String GET_ENUM_DATA_TYPE_ID =
            "SELECT   Id\n" +
                    "FROM   [CxsSettings].[dbo].[DataTypes]\n" +
                    "WHERE  Name = '%s'";

    public static final String GET_ENUM_DATA_TYPES_SORT_AND_ORDER_BY =
            "SELECT *\n" +
                    "FROM  [CxsSettings].[dbo].[EnumDataTypes]\n" +
                    "WHERE DataTypeId = '%s'\n" +
                    "ORDER BY %s %s";

    public static final String GET_ENUM_DATA_TYPES_WITH_OPTION_COUNT =
            "SELECT EnumDataTypeId, count(*) as OptionCount\n" +
                    "FROM [CxsSettings].[dbo].[EnumDataTypeOptions]\n" +
                    "group by EnumDataTypeId";
}