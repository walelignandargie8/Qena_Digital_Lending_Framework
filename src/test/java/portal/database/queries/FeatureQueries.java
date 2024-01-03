package portal.database.queries;

public class FeatureQueries {
    public static final String SQL_STATEMENT_TO_DELETE_AN_FEATURE =
            "DELETE FROM [CxsFeature].[dbo].[Features] WHERE Name = '%s'";

    public static final String SQL_STATEMENT_TO_DELETE_FEATURE_GROUP_BY_ID =
            "DELETE FROM [CxsFeature].[dbo].[FeatureGroups] WHERE Id in ('%s')";

    public static final String SQL_STATEMENT_TO_DELETE_FEATURE_GROUP_BY_NAME =
            "DELETE FROM [CxsFeature].[dbo].[FeatureGroups] WHERE Name = '%s'";

    public static final String SQL_STATEMENT_TO_DELETE_A_FEATURE_BY_ID =
            "DELETE FROM [CxsFeature].[dbo].[Features] WHERE Id = %s";
}