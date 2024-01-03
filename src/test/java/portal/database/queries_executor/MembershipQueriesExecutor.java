package portal.database.queries_executor;


import portal.database.queries.MembershipQueries;

public class MembershipQueriesExecutor extends BaseQueriesExecutor<MembershipQueriesExecutor> {
    public static MembershipQueriesExecutor getMembershipQueriesExecutor() {
        return new MembershipQueriesExecutor();
    }

    /**
     * Method to delete the user group member by user group id
     *
     * @param userGroupId User Group Name
     */
    public void removeUserGroupMembership(int userGroupId) {
        logInformation("About to delete User group membership");
        executeUpdate(buildQuery(MembershipQueries.DELETE_USER_GROUP_MEMBERSHIP_BY_GROUP_ID, userGroupId));
    }
    /**
     * Method to delete the user member by user id
     *
     * @param userId User Name
     */
    public void removeUserMembership(int userId) {
        logInformation("About to delete User membership");
        executeUpdate(buildQuery(MembershipQueries.DELETE_USER_MEMBERSHIP_BY_USER_ID, userId));
    }
}