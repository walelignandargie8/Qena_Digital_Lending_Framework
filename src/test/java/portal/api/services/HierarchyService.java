package portal.api.services;

import portal.api.endpoints.HierarchyEndPoints;
import portal.constants.HttpStatus;
import portal.utils.PropertiesReader;
import io.restassured.http.Cookies;
import io.restassured.response.Response;

import static portal.api.endpoints.ApplicationEndpoint.UPDATE_APPLICATION;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HierarchyService {

    public static String API_PARAM_APPLICATION_KEY = "applicationKey";

    public static HierarchyService getHierarchyService() {
        return new HierarchyService();
    }

    /**
     * Restores the application name according to the received information
     *
     * @param applicationKey application key
     * @param cookies cookies
     * @param applicationName application name
     */
    public void restoreApplicationName(String applicationKey, Cookies cookies, String applicationName) {

        String bodyPayload = String.format("{\"name\":\"%s\",\"value\":\"%s\"}", applicationName, applicationName);

        Response response = given()
                .contentType("application/json")
                .pathParam(API_PARAM_APPLICATION_KEY, applicationKey)
                .cookies(cookies)
                .body(bodyPayload)
                .when()
                .put(UPDATE_APPLICATION)
                .then().log().all()
                .extract().response();

        assertThat(
                "Application update endpoint status code is incorrect:\n" + response.getBody().asString(),
                response.getStatusCode(), equalTo(HttpStatus.OK_RESPONSE));
    }
    /**
     * Method to get application information using applicationKey
     *
     * @param applicationKey application key
     * @return
     */
    public String getApplicationResponse_usingApplicationKey(String applicationKey) {
        baseURI = PropertiesReader.getDefaultOrPreferredSetting("hierarchyApiEndpoint", "hierarchyApiEndpoint");
        Response response = given()
                .contentType("application/json")
                .when()
                .pathParam("applicationKey", applicationKey)
                .get(HierarchyEndPoints.HIERARCHY_VIEW_APPLICATIONKEY_API);
        assertThat("Mismatching response status code: ", response.getStatusCode(), is(HttpStatus.OK_RESPONSE));
        return response.getBody().asString();
    }
}
