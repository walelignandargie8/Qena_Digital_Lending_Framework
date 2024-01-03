package portal.database.queries_executor;

import portal.constants.LogsConstant;
import portal.utils.ScenarioContext;
import portal.utils.database.Database;
import portal.utils.database.DatabaseMongo;

import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public abstract class BaseQueriesExecutor<T extends BaseQueriesExecutor<T>> {

    /**
     * Executes the given query
     *
     * @param query desired query to be executed
     */
    protected ResultSet executeScript(String query) {
        return Database.getAppDbConnection().executeQuery(query);
    }

    /**
     * Method to execute a query update like INSERT, UPDATE, MERGE, or DELETE statement
     *
     * @param query sql query update
     */
    protected void executeUpdate(String query) {
        logInformation("Update query that is going to be executed: \n" + query);
        Database.getAppDbConnection().executeUpdate(query);
        logInformation("Update query executed");
    }
    /**
     * Method to remove single document
     * @param collectionName          collectionName
     * @param attributeNameToSearch   attribute Name to search in the documents
     * @param attributeValueToSearch  attribute Value to search in the documents
     * @throws UnknownHostException
     */
    public void removeSingleDocument(String collectionName, String attributeNameToSearch, String attributeValueToSearch) throws UnknownHostException {
        logInformation("About to remove a single document from the collection '%s' using search criteria '%s':'%s'",
                collectionName, attributeNameToSearch, attributeValueToSearch);
        DatabaseMongo.getAppDbConnection()
                .deleteSingleDocument(collectionName, attributeNameToSearch, attributeValueToSearch);
    }
    /**
     * Method to execute a query and return a hash map
     *
     * @param query query
     * @return the hashmap with values
     */
    protected List<HashMap<String, Object>> executeScriptForHashMap(String query) {
        logInformation("Query that is going to be executed: \n" + query);
        return Database.getAppDbConnection().executeQueryForHashMap(query);
    }

    /**
     * Creates a String using the given query and a set of parameters
     *
     * @param query      desired query to complete with parameters
     * @param parameters parameters to add in query
     * @return query
     */
    protected String buildQuery(String query, Object... parameters) {
        String queryBuilt = String.format(query, parameters);
        logInformation("Query is ready: \n" + queryBuilt);
        return queryBuilt;
    }

    /***
     * Logs information using the common logger
     * @param principalMessage principal message
     * @param parameters possible parameters to add
     */
    public void logInformation(String principalMessage, Object... parameters) {
        Logger logger = (Logger) ScenarioContext.getContext(LogsConstant.LOGGER);
        logger.info(String.format(principalMessage, parameters));
    }

    /***
     * Logs warning using the common logger
     * @param principalMessage principal message
     * @param parameters possible parameters to add
     */
    public void logWarning(String principalMessage, Object... parameters) {
        Logger logger = (Logger) ScenarioContext.getContext(LogsConstant.LOGGER);
        logger.warning(String.format(principalMessage, parameters));
    }

    /**
     * Method to get the first row from the resultSets given a query
     *
     * @param query SQL Query
     * @return Row or null in case that there is not results given the query
     */
    public HashMap<String, Object> getFirstRowFromResultSet(String query) {
        List<HashMap<String, Object>> resultSet = executeScriptForHashMap(query);
        if (resultSet.size() > 0) {
            return resultSet.get(0);
        } else {
            logWarning("There is not results for the query:\n " + query);
        }
        return null;
    }
}
