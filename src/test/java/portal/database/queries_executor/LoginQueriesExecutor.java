package portal.database.queries_executor;

import portal.database.queries.LoginQueries;

public class LoginQueriesExecutor extends BaseQueriesExecutor<LoginQueriesExecutor> {

    public static LoginQueriesExecutor getLoginQueriesExecutor() {
        return new LoginQueriesExecutor();
    }

    /**
     * Method to update the expiration of the token created
     *
     * @param token token
     */
    public void updateTokenExpirationDate(String token) {
        executeUpdate(buildQuery(LoginQueries.UPDATE_LINK_EXPIRATION_DATE, token));
    }
}