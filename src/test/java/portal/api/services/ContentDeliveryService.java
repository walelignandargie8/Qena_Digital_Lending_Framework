package portal.api.services;

import portal.api.endpoints.ContentEndpoint;
import portal.constants.HttpStatus;
import portal.database.queries_executor.ApplicationsQueriesExecutor;
import portal.utils.PropertiesReader;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.sleep;

import static io.restassured.RestAssured.given;

public class ContentDeliveryService extends GenericService<ContentDeliveryService> {
    public ApplicationsQueriesExecutor applicationsQueriesExecutor;
    public static final String PATH_CONTENT_VALUE = "[0].value";
    public static final String STRUCTURED_PATH_CONTENT_VALUE = "[0].structuredValue";

    public ContentDeliveryService() {
        applicationsQueriesExecutor = ApplicationsQueriesExecutor.getApplicationsScriptExecutor();
    }

    public static ContentDeliveryService getContentDeliveryService() {
        return new ContentDeliveryService();
    }

    /**
     * Executes the Get request to get the content value
     *
     * @param contentName      Content name
     * @param contentGroupName Content Group Name
     * @param CultureCode      Culture code
     * @param applicationKey   Key Application Key
     * @return value from required content
     */
    private String getContentValueString(String contentName, String contentGroupName, String CultureCode, String applicationKey, boolean isStructured) {
        // providing some milliseconds to retrieve the updated content value
        sleep(5000);

        RestAssured.baseURI = PropertiesReader.getDefaultOrPreferredSetting("contentDeliveryApiEndpoint", "contentDeliveryApiEndpoint");
        Response response = given()
                .param("ApplicationKey", applicationKey)
                .param("CultureCode", CultureCode)
                .param("ContentGroupName", contentGroupName)
                .param("ContentName", contentName)
                .get(ContentEndpoint.GET_CONTENTS_FROM_CONTENT_DELIVERY)
                .then()
                .log().all()
                .statusCode(HttpStatus.OK_RESPONSE)
                .extract().response();
        RestAssured.baseURI = PropertiesReader.getDefaultOrPreferredSetting("gatewayApiEndpoint", "gatewayApiEndpoint");
        String responseAsString = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseAsString);
        if (isStructured) {
            return jsonPath.get(STRUCTURED_PATH_CONTENT_VALUE).toString();
        } else {
            return jsonPath.get(PATH_CONTENT_VALUE);
        }
    }

    /**
     * Method to get contents list by content group name
     * @param applicationKey application key
     * @param cultureCode culture code
     * @param contentGroupName content group name
     * @return api response
     */
    private Response getContentsOfAGroupByGroupName(String applicationKey, String cultureCode, String contentGroupName) {
        RestAssured.baseURI = PropertiesReader.getDefaultOrPreferredSetting("contentDeliveryApiEndpoint", "contentDeliveryApiEndpoint");
        Response response = given()
                .param("applicationKey", applicationKey)
                .param("cultureCode", cultureCode)
                .param("contentGroupName", contentGroupName)
                .get(ContentEndpoint.GET_CONTENTS_FROM_CONTENT_DELIVERY)
                .then()
                .log().all()
                .statusCode(HttpStatus.OK_RESPONSE)
                .extract().response();
        return response;
    }

    /**
     * Method to get the content value of non-structured content
     *
     * @param contentName    content name
     * @param applicationKey application Key
     * @param CultureCode    Culture code
     * @return the content value
     */
    public String getContentValueWithAppKey(String contentName, String contentGroupName, String applicationKey, String CultureCode) {
        return getContentValueString(contentName, contentGroupName, CultureCode, applicationKey, false);
    }

    /**
     * Method to get the content Value of a structure content
     *
     * @param contentName    content name
     * @param applicationKey application Key
     * @param CultureCode    Culture code
     * @return the content value
     */
    public String getContentValueOfStructuredContent(String contentName, String contentGroupName, String applicationKey, String CultureCode) {
        return getContentValueString(contentName, contentGroupName, CultureCode, applicationKey, true);
    }

    /**
     * Method to get list of contents with value by content group
     * @param applicationKey application key
     * @param cultureCode culture code
     * @param contentGroupName content group name
     * @param contentNames content names
     * @return contentNameValueList
     */
    public Map<String, String> getContentsNameAndValue(String applicationKey, String cultureCode, String contentGroupName, List<String> contentNames) {
        Map<String, String> contentNameValueList = new HashMap<>();
        Response response = getContentsOfAGroupByGroupName(applicationKey, cultureCode, contentGroupName);
        String responseAsString = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseAsString);
        List<String> nameList = jsonPath.get("name");
        List<String> valueList = jsonPath.get("value");
        for (int i = 0; i < nameList.size(); i++) {
            if (contentNames.contains(nameList.get(i))) {
                contentNameValueList.put(nameList.get(i), valueList.get(i));
            }
        }
        return contentNameValueList;
    }
}