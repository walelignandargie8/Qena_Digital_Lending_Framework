package portal.database.queries;

public class PermissionQueries {
    public static final String GET_FIRST_PERMISSIONVALUE_BY_PERMISSIONID_AND_USERID =
            "SELECT TOP 1 *\n" +
                    "FROM [CxsIdentity].[dbo].[PermissionValues]\n" +
                    "WHERE PermissionId='%s' and UserId='%s'";
    public static final String GET_PERMISSIONID_BY_NAME =
            "SELECT TOP 1 *\n" +
                    "FROM [CxsIdentity].[dbo].[Permissions]\n" +
                    "WHERE name='%s'";
    public static final String GET_USERID_BY_NAME =
            "SELECT TOP 1 *\n" +
                    "FROM [CxsIdentity].[dbo].[Users]\n" +
                    "WHERE Username='%s'";
    public static final String DELETE_USER_PERMISSION_BY_ID =
            "DELETE FROM [CxsIdentity].[dbo].[PermissionValues] WHERE Id  = '%s'";
    public static final String DELETE_USER_GROUP_PERMISSION_BY_GROUP_ID =
            "DELETE FROM [CxsIdentity].[dbo].[PermissionValues]\n" +
                    "WHERE UserGroupId = %s";
}