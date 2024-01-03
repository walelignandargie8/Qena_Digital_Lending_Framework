package portal.models.recognitions;


import java.util.Map;

public class RecognitionEntitiesFactory {

    public static RecognitionEntities getRecognitionEntityData(Map<String, String> recognitionCatalogDetail) {
        return RecognitionEntities.builder()
                .name(recognitionCatalogDetail.get("Name"))
                .description(recognitionCatalogDetail.get("Description"))
                .recognitionType(recognitionCatalogDetail.get("RecognitionType"))
                .entityType(recognitionCatalogDetail.get("EntityType"))
                .updatedName(recognitionCatalogDetail.get("UpdatedName"))
                .updatedDescription(recognitionCatalogDetail.get("UpdatedDescription"))
                .build();
    }
}