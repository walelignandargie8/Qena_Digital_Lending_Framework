package portal.database.queries_executor;


import portal.database.queries.PermissionQueries;

import java.util.HashMap;

public class PermissionQueriesExecutor extends BaseQueriesExecutor<PermissionQueriesExecutor> {
    public static PermissionQueriesExecutor getPermissionQueriesExecutor() {
        return new PermissionQueriesExecutor();
    }

    /**
     * Method to delete the user permission from PermissionValue table
     *
     * @param permissionName permission Name
     * @param userName       user Name
     */
    public void deleteUserPermission(String permissionName, String userName) {
        logInformation("About to delete permission");
        HashMap<String, Object> permission = getPermission(permissionName);
        HashMap<String, Object> user = getUser(userName);
        if ((permission != null && !permission.isEmpty()) && (user != null && !user.isEmpty())) {
            int permissionId = (int) permission.get("Id");
            int userId = (int) user.get("Id");
            HashMap<String, Object> permissionValue = getPermissionValue(Integer.toString(permissionId),
                    Integer.toString(userId));
            if ((permissionValue != null && !permissionValue.isEmpty())) {
                int permissionValueId = (int) permissionValue.get("Id");
                executeUpdate(buildQuery(PermissionQueries.DELETE_USER_PERMISSION_BY_ID, permissionValueId));
            }
        }
    }

    /**
     * Method to get the users row from the database given the user Name
     *
     * @param userName permission name
     * @return user row
     */
    private HashMap<String, Object> getUser(String userName) {
        logInformation("About to get the User Name from the database with the name '%s'", userName);
        return getFirstRowFromResultSet(buildQuery(PermissionQueries.GET_USERID_BY_NAME, userName));
    }

    /**
     * Method to get the permission row from the database given the permission Name
     *
     * @param permissionName permission name
     * @return permission row
     */
    private HashMap<String, Object> getPermission(String permissionName) {
        logInformation("About to get the permission Name from the database with the name '%s'", permissionName);
        return getFirstRowFromResultSet(buildQuery(PermissionQueries.GET_PERMISSIONID_BY_NAME, permissionName));
    }

    /**
     * Method to get the permissionValue row from the database given the permission Name, user name
     *
     * @param permissionId permission id
     * @param userId       user id
     * @return permissionValue row
     */
    private HashMap<String, Object> getPermissionValue(String permissionId, String userId) {
        logInformation("About to get the permissionValue from the database with the permissionId '%s'", permissionId + " userId '%s'", userId);
        return getFirstRowFromResultSet(buildQuery(PermissionQueries.GET_FIRST_PERMISSIONVALUE_BY_PERMISSIONID_AND_USERID, permissionId, userId));
    }

    /**
     * Method to delete the user group permission by user group id
     *
     * @param userGroupId User Group Name
     */
    public void removeUserGroupPermission(int userGroupId) {
        logInformation("About to delete User group permission");
        executeUpdate(buildQuery(PermissionQueries.DELETE_USER_GROUP_PERMISSION_BY_GROUP_ID, userGroupId));
    }
}