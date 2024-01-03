package portal.database.queries_executor;

import portal.database.queries.FeatureQueries;

public class FeatureQueriesExecutor extends BaseQueriesExecutor {
    public static FeatureQueriesExecutor getFeaturesExecutor() {
        return new FeatureQueriesExecutor();
    }

    /**
     * Method to delete Single feature
     *
     * @param featureName feature Name
     */
    public void deleteFeatureName(String featureName) {
        String sqlStatementToDeleteAnFeature =
                buildQuery(
                        FeatureQueries.SQL_STATEMENT_TO_DELETE_AN_FEATURE,
                        featureName);
        executeUpdate(sqlStatementToDeleteAnFeature);
    }

    /**
     * Method to delete Single feature
     *
     * @param featureId features Id
     */
    public void deleteFeatureById(Integer featureId) {
        String sqlStatementToDeleteAnFeature =
                buildQuery(
                        FeatureQueries.SQL_STATEMENT_TO_DELETE_A_FEATURE_BY_ID, featureId);
        executeUpdate(sqlStatementToDeleteAnFeature);
    }

    /**
     * Method to delete feature group
     *
     * @param featureGroupId Feature Group ID
     */
    public void deleteFeatureGroupById(int featureGroupId) {
        String sqlStatementToDeleteAFeatureGroup =
                buildQuery(
                        FeatureQueries.SQL_STATEMENT_TO_DELETE_FEATURE_GROUP_BY_ID,
                        featureGroupId);
        executeUpdate(sqlStatementToDeleteAFeatureGroup);
    }

    /**
     * Method to delete a feature group name
     *
     * @param featureGroupName feature group name
     */
    public void deleteFeatureGroupName(String featureGroupName) {
        String sqlStatementToDeleteAFeatureGroup =
                buildQuery(
                        FeatureQueries.SQL_STATEMENT_TO_DELETE_FEATURE_GROUP_BY_NAME,
                        featureGroupName);
        executeUpdate(sqlStatementToDeleteAFeatureGroup);
    }
}