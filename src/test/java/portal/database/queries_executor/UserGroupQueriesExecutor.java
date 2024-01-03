package portal.database.queries_executor;


import portal.database.queries.UserGroupQueries;

import java.util.HashMap;
import java.util.List;

public class UserGroupQueriesExecutor extends BaseQueriesExecutor<UserGroupQueriesExecutor> {


    public static UserGroupQueriesExecutor getUserGroupQueriesExecutor() {
        return new UserGroupQueriesExecutor();
    }

    /**
     * Method to delete the user group
     *
     * @param groupName User Group Name
     */
    public void deleteUserGroup(String groupName) {
        logInformation("About to delete User group");
        HashMap<String, Object> userGroup = getUserGroup(groupName);
        if (userGroup != null && !userGroup.isEmpty()) {
            int userGroupId = (int) userGroup.get("Id");
            executeUpdate(buildQuery(UserGroupQueries.DELETE_USER_GROUP_BY_ID, userGroupId));
        }
    }

    /**
     * Method to get the user group from the database given the user group name
     *
     * @param groupName user group name
     * @return user group row
     */
    private HashMap<String, Object> getUserGroup(String groupName) {
        logInformation("About to get the user group from the database with the name '%s'", groupName);
        return getFirstRowFromResultSet(buildQuery(UserGroupQueries.GET_FIRST_USER_GROUP_BY_NAME, groupName));
    }

    /**
     * Method to get user group id
     *
     * @param userGroupName user group name
     * @return user group id
     */
    public int getUserGroupId(String userGroupName) {
        int userGroupId = -1;

        List<HashMap<String, Object>> resultSetAsMap = executeScriptForHashMap(buildQuery(UserGroupQueries.GET_FIRST_USER_GROUP_BY_NAME, userGroupName));
        if (resultSetAsMap.size() > 0) {
            userGroupId = (int) resultSetAsMap.get(0).get("Id");
        }

        if (userGroupId < 0) {
            try {
                throw new Exception(String.format("The user group '%s' was not found", userGroupName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return userGroupId;
    }
}