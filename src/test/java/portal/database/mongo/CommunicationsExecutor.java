package portal.database.mongo;

import java.net.UnknownHostException;

public class CommunicationsExecutor extends BaseQueriesMongoExecutor<CommunicationsExecutor> {
    public static final String COMMUNICATION_COLLECTION_NAME = "communications";

    public static CommunicationsExecutor getCommunicationsExecutor() {
        return new CommunicationsExecutor();
    }

    /**
     * Method to update a Single Document
     *
     * @param communicationName Communication Name
     * @param status            status to update
     * @throws UnknownHostException
     */
    public void updateCommunicationStatus(String communicationName, String status) throws UnknownHostException {
        updateSingleDocument(COMMUNICATION_COLLECTION_NAME,
                "Name", communicationName,
                "Status", status);
    }

    /**
     * Method to get the Communication Job ID from a Communication
     *
     * @param communicationName Communication Name
     * @return the communication Job ID
     * @throws UnknownHostException
     */
    public String getCommunicationJob(String communicationName) throws UnknownHostException {
        return getDocumentPropertyValue(COMMUNICATION_COLLECTION_NAME,
                "Name", communicationName, "JobId");
    }

    /**
     * Method to get the ApplicationKey using Communication from database
     *
     * @param communicationName Communication Name
     * @return the ApplicationKey
     * @throws UnknownHostException
     */
    public Object getCommunicationApplicationKeyObject(String communicationName) throws UnknownHostException {
        return getDocumentPropertyValueAsObject(COMMUNICATION_COLLECTION_NAME,
                "Name", communicationName, "ApplicationKey");
    }
    /**
     * Method to delete a communication from database
     *
     * @param communicationName Communication Name
     * @throws UnknownHostException
     */
    public void deleteCommunication(String communicationName) throws UnknownHostException {
        removeSingleDocument(COMMUNICATION_COLLECTION_NAME, "Name", communicationName);
    }

    /**
     * Method to get the total of communications given a search criteria
     *
     * @param status
     * @return total of communications
     * @throws UnknownHostException
     */
    public long getTotalOfCommunicationForStatus(String status) throws UnknownHostException {
        return getTotalOfDocuments(COMMUNICATION_COLLECTION_NAME, "Status", status);
    }
}