package portal.database.queries_executor;

import portal.database.queries.UserQueries;

import java.util.HashMap;
import java.util.List;

public class UserQueriesExecutor extends BaseQueriesExecutor<UserQueriesExecutor> {
    public static UserQueriesExecutor getUserQueriesExecutor() {
        return new UserQueriesExecutor();
    }

    /**
     * Method to update the password modified date of the user password
     *
     * @param userId user id
     */
    public void updatePasswordModifiedDate(String userId) {
        executeUpdate(buildQuery(UserQueries.UPDATE_PASSWORD_MODIFIED_DATE, userId));
    }

    /**
     * Method to remove a user with a user id
     *
     * @param userId user id
     */
    public void removeUser(int userId) {
        executeUpdate(buildQuery(UserQueries.REMOVE_USER_ATTRIBUTES, userId));
        executeUpdate(buildQuery(UserQueries.REMOVE_USER_PASSWORD_HISTORY, userId));
        executeUpdate(buildQuery(UserQueries.REMOVE_USER_ACTIVITY_FAILURES, userId));
        executeUpdate(buildQuery(UserQueries.QUERY_REMOVE_USER_BY_ID, userId));
        executeUpdate(buildQuery(UserQueries.REMOVE_USER_INFO, userId));
    }

    /**
     * Method to get user  id
     *
     * @param userName username
     * @return id
     */
    public int getUserId(String userName) {
        int userId = -1;

        List<HashMap<String, Object>> resultSetAsMap = executeScriptForHashMap(buildQuery(UserQueries.GET_FIRST_USER_BY_NAME, userName));
        if (resultSetAsMap.size() > 0) {
            userId = (int) resultSetAsMap.get(0).get("Id");
        }

        if (userId < 0) {
            try {
                throw new Exception(String.format("The user '%s' was not found", userName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return userId;
    }
}