package portal.models.settings;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SettingValue {
    private String name;
    private Integer settingGroupId;
    private String value;
    private String dataType;
    private String enumDataType;
}
