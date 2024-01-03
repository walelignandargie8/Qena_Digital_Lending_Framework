package portal.database.queries_executor;

import portal.database.queries.ContentQueries;

import java.util.HashMap;
import java.util.List;

public class ContentQueriesExecutor extends BaseQueriesExecutor<ContentQueriesExecutor> {

    public static ContentQueriesExecutor getContentQueriesExecutor() {
        return new ContentQueriesExecutor();
    }

    /**
     * Method to remove a Content
     *
     * @param contentName Content Name
     */
    public void removeContent(String contentName) {
        int contentId = getContentId(contentName);
        executeUpdate(buildQuery(ContentQueries.QUERY_DELETE_APPLICATION_CONTENT, contentId));
        executeUpdate(buildQuery(ContentQueries.QUERY_DELETE_APPLICATION_CONTENT_DESCRIPTION, contentId));
        executeUpdate(buildQuery(ContentQueries.QUERY_DELETE_CONTENT_REFERENCES, contentId));
        executeUpdate(buildQuery(ContentQueries.QUERY_DELETE_CONTENT, contentId));
        logInformation("All the information related to content '%s' is removed", String.valueOf(contentId));
    }

    /**
     * Method to get the content group ID
     *
     * @param contentGroupName content group name
     * @return content group ID
     */
    public int getContentGroupId(String contentGroupName) {
        List<HashMap<String, Object>> resultSetAsMap = executeScriptForHashMap(
                buildQuery(ContentQueries.QUERY_GET_CONTENT_GROUP_WITH_NAME, contentGroupName));
        return (int) resultSetAsMap.get(0).get("Id");
    }

    /**
     * Method to remove a Content Group
     *
     * @param contentGroupName Content Group Name
     */
    public void removeContentGroup(String contentGroupName) {
        executeUpdate(buildQuery(ContentQueries.QUERY_REMOVE_CONTENT_GROUP_BY_NAME, contentGroupName));
    }

    /**
     * Method to get the content Id
     *
     * @param contentName content Name
     * @return the content ID
     */
    public int getContentId(String contentName) {

        int contentId = -1;

        List<HashMap<String, Object>> resultSetAsMap = executeScriptForHashMap(buildQuery(ContentQueries.QUERY_GET_CONTENT_PROPERTIES, contentName));
        if (resultSetAsMap.size() > 0) {
            contentId = (int) resultSetAsMap.get(0).get("Id");
        }

        if (contentId < 0) {
            try {
                throw new Exception(String.format("The content '%s' was not found", contentName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return contentId;
    }

    /**
     * Method to remove application content references
     *
     * @param contentGroupName content group name
     */
    public void removeApplicationContentReferences(String contentGroupName) {
        executeUpdate(buildQuery(ContentQueries.REMOVE_APPLICATION_CONTENT_REFERENCES, contentGroupName));
    }

    /**
     * Method to remove application contents
     *
     * @param contentGroupName content group name
     */
    public void removeApplicationContents(String contentGroupName) {
        executeUpdate(buildQuery(ContentQueries.REMOVE_APPLICATION_CONTENTS, contentGroupName));
    }

    /**
     * Method to remove application content descriptions
     *
     * @param contentGroupName content group name
     */
    public void removeApplicationContentDescriptions(String contentGroupName) {
        executeUpdate(buildQuery(ContentQueries.REMOVE_APPLICATION_CONTENT_DESCRIPTIONS, contentGroupName));
    }

    /**
     * Method to remove the contents
     *
     * @param contentGroupName content group name
     */
    public void removeContents(String contentGroupName) {
        executeUpdate(buildQuery(ContentQueries.REMOVE_CONTENTS, contentGroupName));
    }
}