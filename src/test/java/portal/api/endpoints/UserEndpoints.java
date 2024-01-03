package portal.api.endpoints;

public class UserEndpoints {
    public static final String UPDATE_GET_USERS = "/application/%s/users";
    public static final String CREATE_USERS = "/application/%s/users";
    public static final String CREATE_USER = "/Identity/application/{applicationKey}/user";
    public static final String UPDATE_USER_PERMISSION = "/Identity/application/{applicationKey}/user/" +
            "{userId}/permission/{permissionId}";
    public static final String GET_USER = "/Identity/application/{applicationKey}/user/{userId}";
}