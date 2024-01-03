package portal.models.recognitions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecognitionEntities {
    private String name;
    private String description;
    private String recognitionType;
    private String entityType;
    private String updatedName;
    private String updatedDescription;
}