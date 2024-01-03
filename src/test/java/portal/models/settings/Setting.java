package portal.models.settings;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Setting {
    private String name;
    private String description;
    private String value;
    private String dataType;
    private String enumDataType;
    private String validationPattern;
    private String status;
    private String originalValue;
    private String updatedValue;
}