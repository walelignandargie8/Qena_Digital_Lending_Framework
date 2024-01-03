package portal.utils.database;

import portal.constants.LogsConstant;
import portal.utils.ScenarioContext;
import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.net.UnknownHostException;
import java.util.Collections;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.eq;

public class DatabaseMongoConnection {
    private final String host;
    private final int portNumber;
    private final String dbname;
    private final String user;
    private final String password;
    private MongoClient mongoClient;

    public DatabaseMongoConnection(String host, int portNumber, String dbname, String username, String password) {
        this.host = host;
        this.portNumber = portNumber;
        this.dbname = dbname;
        this.user = username;
        this.password = password;
    }

    /**
     * Method to open a connection to MongoDB
     *
     * @throws UnknownHostException
     */
    private void getConnection() throws UnknownHostException {
        MongoCredential credential = MongoCredential.createScramSha1Credential(user,
                dbname,
                password.toCharArray());
        logInformation("About to connect to MongoDB '%s:%s'", host, portNumber);
        mongoClient = MongoClients.create(
                MongoClientSettings.builder()
                        .applyToClusterSettings(builder ->
                                builder.hosts(Collections.singletonList(new ServerAddress(host, portNumber))))
                        .credential(credential)
                        .build());
        logInformation("Connect successfully to MongoDB '%s:%s'", host, portNumber);
    }

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
        getConnection();
        MongoDatabase database = mongoClient.getDatabase(dbname);

        BasicDBObject query = new BasicDBObject();
        query.put(attributeNameToSearch, attributeValueToSearch); // search criteria

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put(attributeNameToUpdate, attributeValueToUpdate); //  new value

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", newDocument);

        database.getCollection(collectionName).updateOne(query, updateObject);
        closeConnection();
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
        getConnection();
        MongoDatabase database = mongoClient.getDatabase(dbname);
        Document document = database.getCollection(collectionName)
                .find(new Document(attributeNameToSearch, attributeValueToSearch)).first();
        String attributeValue = document.getString(attributeNameToGetValue);
        closeConnection();
        return attributeValue;
    }

    /**
     * Method to close the connection
     */
    private void closeConnection() {
        mongoClient.close();
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
        getConnection();
        MongoDatabase database = mongoClient.getDatabase(dbname);
        Document document = database.getCollection(collectionName)
                .find(new Document(attributeNameToSearch, attributeValueToSearch)).first();
        Object attributeValue =document.get(attributeNameToGetValue);
                closeConnection();
        return attributeValue;
    }
    /**
     * Method to get the total of documents
     *
     * @param collectionName         collectionName
     * @param attributeNameToSearch  attribute Name to search in the documents
     * @param attributeValueToSearch attribute Value to search in the documents
     * @return total of documents
     * @throws UnknownHostException
     */
    public long getTotalOfDocuments(String collectionName, String attributeNameToSearch, String attributeValueToSearch)
            throws UnknownHostException {
        getConnection();
        MongoDatabase database = mongoClient.getDatabase(dbname);
        long count = database.getCollection(collectionName)
                .count(new Document(attributeNameToSearch, attributeValueToSearch));
        closeConnection();
        return count;
    }
    /**
     * Method to delete a single document
     *
     * @param collectionName         collectionName
     * @param attributeNameToSearch  attribute Name to search in the documents
     * @param attributeValueToSearch attribute Value to search in the documents
     * @throws UnknownHostException
     */
    public void deleteSingleDocument(String collectionName, String attributeNameToSearch, String attributeValueToSearch) throws UnknownHostException {
        getConnection();
        MongoDatabase database = mongoClient.getDatabase(dbname);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Bson query = eq(attributeNameToSearch, attributeValueToSearch);
        try {
            DeleteResult result = collection.deleteOne(query);
            logInformation("Deleted document: " + result.getDeletedCount());
        } catch (MongoException me) {
            logWarning("Unable to delete document due to an error:" + me);
        }
    }
}