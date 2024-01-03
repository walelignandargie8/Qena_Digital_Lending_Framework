package portal.database.mongo;

import portal.constants.LogsConstant;
import portal.utils.ScenarioContext;
import portal.utils.database.DatabaseMongo;

import java.net.UnknownHostException;
import java.util.logging.Logger;

public abstract class BaseQueriesMongoExecutor<T extends BaseQueriesMongoExecutor<T>> {

    /**
     * Method to update a Single Document
     *
     * @param collectionName         collectionName
     * @param attributeNameToSearch  attribute Name to search in the documents
     * @param attributeValueToSearch attribute Value to search in the documents
     * @param attributeNameToUpdate  attribute Name to update in the documents
     * @param attributeValueToUpdate attribute Value to update in the documents
     * @throws UnknownHostException
     */
    public void updateSingleDocument(String collectionName, String attributeNameToSearch, String attributeValueToSearch,
                                     String attributeNameToUpdate, String attributeValueToUpdate) throws UnknownHostException {
        logInformation("About to update a single document in the collection '%s' using search criteria '%s':'%s' with the value '%s':'%s'",
                collectionName, attributeNameToSearch, attributeValueToSearch, attributeNameToUpdate, attributeValueToUpdate);
        DatabaseMongo.getAppDbConnection().updateSingleDocument(collectionName, attributeNameToSearch, attributeValueToSearch,
                attributeNameToUpdate, attributeValueToUpdate);
        logInformation("Single document was updated in the collection '%s' using search criteria '%s':'%s' with the value '%s':'%s'",
                collectionName, attributeNameToSearch, attributeValueToSearch, attributeNameToUpdate, attributeValueToUpdate);
    }
    /**
     * Method to get the total of documents given the attribute Name=Value
     * @param collectionName          collectionName
     * @param attributeNameToSearch   attribute Name to search in the documents
     * @param attributeValueToSearch  attribute Value to search in the documents
     * @return total of documents
     * @throws UnknownHostException
     */
    public long getTotalOfDocuments(String collectionName, String attributeNameToSearch, String attributeValueToSearch) throws UnknownHostException {
        logInformation("About to get the total of documents for the collection '%s' using search criteria '%s':'%s'",
                collectionName, attributeNameToSearch, attributeValueToSearch);
        return DatabaseMongo.getAppDbConnection()
                .getTotalOfDocuments(collectionName, attributeNameToSearch, attributeValueToSearch);
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
     * Method to get the Document property Value
     *
     * @param collectionName          collectionName
     * @param attributeNameToSearch   attribute Name to search in the documents
     * @param attributeValueToSearch  attribute Value to search in the documents
     * @param attributeNameToGetValue attribute Name to get the value
     * @return the document property Value
     * @throws UnknownHostException
     */
    public String getDocumentPropertyValue(String collectionName, String attributeNameToSearch,
                                           String attributeValueToSearch, String attributeNameToGetValue) throws UnknownHostException {
        logInformation("About to get the document property '%s' from the collection '%s' using search criteria '%s':'%s'",
                attributeNameToGetValue, collectionName, attributeNameToSearch, attributeValueToSearch);
        return DatabaseMongo.getAppDbConnection()
                .getDocumentPropertyValue(collectionName, attributeNameToSearch, attributeValueToSearch, attributeNameToGetValue);
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
     * Method to get the Document property Value
     *
     * @param collectionName          collectionName
     * @param attributeNameToSearch   attribute Name to search in the documents
     * @param attributeValueToSearch  attribute Value to search in the documents
     * @param attributeNameToGetValue attribute Name to get the value
     * @return the document property Value
     * @throws UnknownHostException
     */
    public Object getDocumentPropertyValueAsObject(String collectionName, String attributeNameToSearch,
                                                   String attributeValueToSearch, String attributeNameToGetValue) throws UnknownHostException {
        logInformation("About to get the document property '%s' from the collection '%s' using search criteria '%s':'%s'",
                attributeNameToGetValue, collectionName, attributeNameToSearch, attributeValueToSearch);
        return DatabaseMongo.getAppDbConnection()
                .getDocumentPropertyValueAsObject(collectionName, attributeNameToSearch, attributeValueToSearch, attributeNameToGetValue);
    }
}