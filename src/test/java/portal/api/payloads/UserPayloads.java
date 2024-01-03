package portal.api.payloads;


import java.util.Map;

public class UserPayloads {

    public static String createUserPayload(String applicationKey, String userName, String firstName, String lastName, String email, String preferredName,
                                           String preferredLanguage) {
        return "{\n" +
                "  \"applicationKey\": \"" + applicationKey + "\",\n" +
                "  \"username\": \"" + userName + "\",\n" +
                "  \"accountStatus\" : \"Active\",\n" +
                "  \"isPasswordLockedOut\": false,\n" +
                "  \"isPasswordExpired\": false,\n" +
                "  \"isPasswordSet\": false,\n" +
                "  \"forcePasswordChange\": false,\n" +
                "  \"password\": null,\n" +
                "  \"lastLoginDate\": null,\n" +
                "  \"attributes\": {\n" +
                "    \"FirstName\": {\n" +
                "        \"name\": \"First name\",\n" +
                "        \"code\": \"FirstName\",\n" +
                "        \"value\": \"" + firstName + "\"\n" +
                "    },\n" +
                "    \"LastName\": {\n" +
                "        \"name\": \"Last name\",\n" +
                "        \"code\": \"LastName\",\n" +
                "        \"value\": \"" + lastName + "\"\n" +
                "    },  \n" +
                "    \"Email\": {\n" +
                "      \"name\": \"Email\",\n" +
                "      \"code\": \"Email\",\n" +
                "      \"value\": \"" + email + "\"\n" +
                "    },\n" +
                "    \"PreferredName\":{\n" +
                "        \"name\": \"PreferredName\",\n" +
                "        \"code\": \"PreferredName\",\n" +
                "        \"value\": \"" + preferredName + "\"\n" +
                "    },\n" +
                "    \"PreferredLanguage\":{\n" +
                "        \"name\": \"PreferredLanguage\",\n" +
                "        \"code\": \"PreferredLanguage\",\n" +
                "        \"value\": \"" + preferredLanguage + "\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }

    public static String updateUserPayload(Map<String, Object> userResponseBody) {
        return "{\n" +
                "  \"id\": " + userResponseBody.get("id") + ",\n" +
                "  \"applicationKey\": \"" + userResponseBody.get("applicationKey") + "\",\n" +
                "  \"username\": \"" + userResponseBody.get("username") + "\",\n" +
                "  \"accountStatus\" : \"" + userResponseBody.get("accountStatus") + "\",\n" +
                "  \"isPasswordLockedOut\": false,\n" +
                "  \"isPasswordExpired\": " + userResponseBody.get("isPasswordExpired") + ",\n" +
                "  \"isPasswordSet\": " + userResponseBody.get("isPasswordSet") + ",\n" +
                "  \"forcePasswordChange\": " + userResponseBody.get("forcePasswordChange") + ",\n" +
                "  \"password\": null,\n" +
                "  \"lastLoginDate\": null\n" +
                "}";
    }
}