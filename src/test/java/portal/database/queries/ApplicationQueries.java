package portal.database.queries;

public class ApplicationQueries {
    public static final String SQL_STATEMENT_TO_DELETE_AN_APPLICATION =
            "DELETE FROM [CxsHierarchy].[dbo].[Applications]\n" +
                    "WHERE Name = '%s'\n" +
                    "AND Value = '%s'";

    public static final String GET_APPLICATION_WITH_NAME =
            "SELECT TOP 1 *\n" +
            "FROM [CxsHierarchy].[dbo].[Applications]\n" +
            "WHERE Name = '%s'";

    public static final String GET_RANDOM_APPLICATION =
            "SELECT TOP 1 * \n" +
            "FROM [CxsHierarchy].[dbo].[Applications]\n" +
            "WHERE Name <> 'E2E Group'\n" +
            "ORDER BY NEWID()";

    public static final String DELETE_APPLICATION_GROUP =
            "delete from [CxsHierarchy].[dbo].[ApplicationGroups]\n" +
            "where name = '%s'";
}
