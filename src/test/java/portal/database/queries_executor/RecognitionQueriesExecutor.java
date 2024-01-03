package portal.database.queries_executor;

import portal.database.queries.RecognitionQueries;
import portal.models.recognitions.RecognitionEntities;

import java.util.HashMap;
import java.util.List;

public class RecognitionQueriesExecutor extends BaseQueriesExecutor<RecognitionQueriesExecutor> {

    public static RecognitionQueriesExecutor getRecognitionQueriesExecutor() {
        return new RecognitionQueriesExecutor();
    }

    /**
     * Get Recognition Entity id
     *
     * @param entityType Recognition entity type
     * @param Name       Entity name
     * @return entity id
     */
    public int getRecognitionEntity(String entityType, String Name, int recognitionAssetType) {

        int entityId = -1;
        List<HashMap<String, Object>> resultSetAsMap;

        if (entityType.equalsIgnoreCase("Catalog")) {
            resultSetAsMap = executeScriptForHashMap(buildQuery(RecognitionQueries.GET_RECOGNITION_CATALOG_BY_NAME, Name, recognitionAssetType));
        } else if (entityType.equalsIgnoreCase("Program")) {
            resultSetAsMap = executeScriptForHashMap(buildQuery(RecognitionQueries.GET_PROGRAM_BY_NAME, Name));
        } else {
            resultSetAsMap = executeScriptForHashMap(buildQuery(RecognitionQueries.GET_RECOGNITION_CATEGORY_BY_NAME, Name));
        }

        if (resultSetAsMap.size() > 0) {
            entityId = (int) resultSetAsMap.get(0).get("Id");
        }

        if (entityId < 0) {
            try {
                throw new Exception(String.format("Recognition " + entityType + " '%s' was not found", Name));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return entityId;
    }

    /**
     * Method to delete an Recognition entity
     *
     * @param recognitionEntities recognition entity detail
     */
    public void deleteRecognitionEntity(RecognitionEntities recognitionEntities) {
        int recognitionEntityId;
        if (recognitionEntities.getRecognitionType().equalsIgnoreCase("eCard")) {
            if (recognitionEntities.getEntityType().equalsIgnoreCase("Catalog")) {
                recognitionEntityId = getRecognitionEntity("Catalog", recognitionEntities.getName(), 2);
                executeUpdate(buildQuery(RecognitionQueries.DELETE_RECOGNITION_ASSET_BY_CATALOG, recognitionEntityId));
                executeUpdate(buildQuery(RecognitionQueries.REMOVE_LINK_BETWEEN_CATEGORY_AND_CATALOG, recognitionEntityId));
                executeUpdate(buildQuery(RecognitionQueries.DELETE_RECOGNITION_CATALOG, recognitionEntityId));
                executeUpdate(buildQuery(RecognitionQueries.DELETE_RECOGNITION_CATALOG,
                        getRecognitionEntity("Catalog", recognitionEntities.getUpdatedName(), 2)));
            } else {
                executeUpdate(buildQuery(RecognitionQueries.DELETE_RECOGNITION_CATEGORY,
                        getRecognitionEntity("Category", recognitionEntities.getName(), 2)));
            }
        } else {
            recognitionEntityId = getRecognitionEntity("Catalog", recognitionEntities.getName(), 1);
            executeUpdate(buildQuery(RecognitionQueries.DELETE_RECOGNITION_ASSET_BY_CATALOG, recognitionEntityId));
            executeUpdate(buildQuery(RecognitionQueries.REMOVE_LINK_BETWEEN_CATEGORY_AND_CATALOG, recognitionEntityId));
            executeUpdate(buildQuery(RecognitionQueries.DELETE_RECOGNITION_CATALOG, recognitionEntityId));
            executeUpdate(buildQuery(RecognitionQueries.DELETE_RECOGNITION_CATALOG,
                    getRecognitionEntity("Catalog", recognitionEntities.getUpdatedName(), 1)));
        }
        logInformation("All the information related to " + recognitionEntities.getRecognitionType() + " " +
                recognitionEntities.getEntityType() + " '%s' is removed", recognitionEntities.getName());
    }

    /**
     * Method to delete program by name
     *
     * @param programName program name
     */
    public void deleteClientProgram(String programName) {
        executeUpdate(buildQuery(RecognitionQueries.DELETE_PROGRAM_BY_Id, getRecognitionEntity("Program",
                programName, 3)));
        logInformation("All the information related to " + programName + " is removed");
    }

    /**
     * Method to assign a random category to eCards
     *
     * @param currentCategoryName current category name
     */
    public void assignRandomCategoryToECards(String currentCategoryName) {
        executeUpdate(buildQuery(RecognitionQueries.ASSIGN_RANDOM_CATEGORY_ECARDS, currentCategoryName));
        logInformation("ECards whose category is " + currentCategoryName + " is updated");
    }
}