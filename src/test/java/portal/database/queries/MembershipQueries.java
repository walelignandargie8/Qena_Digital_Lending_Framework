package portal.database.queries;

public class MembershipQueries {
    public static final String DELETE_USER_GROUP_MEMBERSHIP_BY_GROUP_ID =
            "DELETE FROM [CxsIdentity].[dbo].[Memberships]\n" +
                    "WHERE MemberUserGroupId = %s";
    public static final String DELETE_USER_MEMBERSHIP_BY_USER_ID =
            "DELETE FROM [CxsIdentity].[dbo].[Memberships]\n" +
                    "WHERE MemberUserId = %s";
}