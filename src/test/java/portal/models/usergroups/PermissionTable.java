package portal.models.usergroups;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PermissionTable {
    private String permissionCategory;
    private String permission;
    private String status;
}