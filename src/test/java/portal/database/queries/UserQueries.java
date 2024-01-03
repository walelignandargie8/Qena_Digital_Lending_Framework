package portal.database.queries;

public class UserQueries {
    public static final String QUERY_REMOVE_USER_BY_ID =
            "DELETE FROM [CxsIdentity].[dbo].[Users]\n" +
                    "WHERE Id = %s";

    public static final String GET_FIRST_USER_BY_NAME =
            "SELECT TOP 1 *\n" +
                    "FROM [CxsIdentity].[dbo].[Users]\n" +
                    "WHERE Username = '%s'";

    public static final String UPDATE_PASSWORD_MODIFIED_DATE =
            "UPDATE CxsIdentity.dbo.Users\n" +
                    "SET PasswordModifiedDate =\n" +
                    "    (\n" +
                    "        SELECT CONVERT(\n" +
                    "                          DATETIME,\n" +
                    "                          SWITCHOFFSET(\n" +
                    "                                          CONVERT(DATETIMEOFFSET, DATEADD(YEAR, -3, GETDATE())),\n" +
                    "                                          DATENAME(TzOffset, SYSDATETIMEOFFSET())\n" +
                    "                                      )\n" +
                    "                      )\n" +
                    "    )\n" +
                    "WHERE Id = %s";

    public static final String REMOVE_USER_ATTRIBUTES =
            "DELETE FROM [CxsIdentityAttribute].[dbo].[UserAttributes]\n" +
                    "WHERE UserId = %s";

    public static final String REMOVE_USER_PASSWORD_HISTORY =
            "DELETE FROM [CxsIdentity].[dbo].[PasswordHistory]\n" +
                    "WHERE UserId = %s";

    public static final String REMOVE_USER_ACTIVITY_FAILURES =
            "DELETE FROM [CxsIdentity].[dbo].[ActivityFailures]\n" +
                    "WHERE UserId = %s";

    public static final String REMOVE_USER_INFO =
            "DELETE FROM CxsIdentityAttribute.dbo.UserInfo\n" +
            "WHERE UserId = %s";
}