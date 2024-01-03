package portal.database.queries;

public class UserGroupQueries {

    public static final String GET_FIRST_USER_GROUP_BY_NAME =
            "SELECT TOP 1 *\n" +
                    "FROM [CxsIdentity].[dbo].[UserGroups]\n" +
                    "WHERE Name = '%s'";

    public static final String DELETE_USER_GROUP_BY_ID =
            "DELETE FROM [CxsIdentity].[dbo].[UserGroups]\n" +
                    "WHERE Id = %s";
}