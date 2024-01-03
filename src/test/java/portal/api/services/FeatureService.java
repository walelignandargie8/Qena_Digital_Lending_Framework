package portal.api.services;

import portal.api.endpoints.FeatureEndpoints;
import portal.constants.HttpStatus;
import portal.utils.PropertiesReader;
import portal.utils.TestContext;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class FeatureService extends GenericService<FeatureService> {

    public static String API_PARAM_APPLICATION_KEY = "applicationKey";
    public static String API_PARAM_FEATURE_ID = "featureId";
    private final TestContext testContext;
    private RequestSpecification specForFeature;


    public FeatureService(TestContext testContext) {
        specForFeature = given().baseUri(PropertiesReader
                .getDefaultOrPreferredSetting("gatewayApiEndpoint", "gatewayApiEndpoint"));
        this.testContext = testContext;
    }

    public static FeatureService getFeatureService(TestContext testContext) {
        return new FeatureService(testContext);
    }

    /**
     * Method to create a new feature name
     *
     * @param applicationKey applicationKey
     * @param featureName    Feature Name
     * @param cookies          cookies
     * @return response
     */
    public int createFeature(String applicationKey, String featureName, Cookies cookies) {
        Response response = given(specForFeature)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .cookies(cookies)
                .body("{\"name\": \"" + featureName + "\"}")
                .when()
                .post(FeatureEndpoints.CREATE_FEATURE)
                .then().log().all()
                .extract().response();

        assertThat(
                "Create Feature endpoint status code is incorrect:\n" + response.getBody().asString(),
                response.getStatusCode(), equalTo(HttpStatus.CREATED));

        return response.jsonPath().get("data.id");
    }

    /**
     * Create feature group
     *
     * @param applicationKey application key
     * @param featureId      feature ID
     * @param groupName      group name
     * @param cookies          cookies
     * @return response
     */
    public void createFeatureGroup(String applicationKey, int featureId, String groupName, Cookies cookies) {

        String bodyPayload = String.format("{\"featureId\":%s,\"name\":\"%s\"}", featureId, groupName);

        Response response = given(specForFeature)
                .contentType("application/json")
                .pathParam(API_PARAM_APPLICATION_KEY, applicationKey)
                .pathParam(API_PARAM_FEATURE_ID, featureId)
                .cookies(cookies)
                .body(bodyPayload)
                .when()
                .post(FeatureEndpoints.CREATE_FEATURE_GROUP)
                .then().log().all()
                .extract().response();

        assertThat(
                "Create Feature Group endpoint status code is incorrect:\n" + response.getBody().asString(),
                response.getStatusCode(), equalTo(HttpStatus.CREATED));
    }
}