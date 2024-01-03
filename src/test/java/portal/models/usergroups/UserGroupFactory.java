package portal.models.usergroups;


import java.util.Map;

public class UserGroupFactory {

    public static UserGroup getUserGroup(Map<String, String> userGroupDetail) {
        return UserGroup.builder()
                .name(userGroupDetail.get("Name"))
                .status(userGroupDetail.get("Status"))
                .build();
    }
}