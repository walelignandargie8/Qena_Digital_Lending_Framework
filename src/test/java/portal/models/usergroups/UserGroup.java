package portal.models.usergroups;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserGroup {
    private String name;
    private String status;
}