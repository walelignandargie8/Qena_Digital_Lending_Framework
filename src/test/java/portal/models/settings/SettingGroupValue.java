package portal.models.settings;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SettingGroupValue {
    private Integer id;
    private String name;
    private String description;
    private String status;
}