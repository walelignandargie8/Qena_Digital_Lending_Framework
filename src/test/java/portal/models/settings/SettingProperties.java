package portal.models.settings;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SettingProperties {
    private Integer settingGroupId;
    private String name;
    private String description;
    private String dataType;
    private String validationPattern;
    private String status;
}
