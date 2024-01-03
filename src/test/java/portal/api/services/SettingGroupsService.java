package portal.api.services;

import portal.api.endpoints.SettingsEndpoint;
import portal.constants.ApiConstants;
import portal.constants.HttpStatus;
import portal.constants.SettingsConstants;
import portal.database.queries_executor.SettingGroupQueriesExecutor;
import portal.models.settings.SettingGroup;
import portal.models.settings.SettingGroupValue;
import portal.utils.StringUtils;
import portal.utils.TestContext;
import io.restassured.http.Cookies;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SettingGroupsService extends GenericService<SettingGroupsService> {
    public static final String SCHEMAS_SETTING_GROUP_JSON = "schemas/SettingGroup.json";

    private final TestContext testContext;
    private final SettingGroupQueriesExecutor settingGroupQueriesExecutor;

    private SettingGroupsService(TestContext testContext) {
        this.testContext = testContext;
        settingGroupQueriesExecutor = SettingGroupQueriesExecutor.getSettingGroupQueriesExecutor();
    }

    public static SettingGroupsService getSettingGroupsService(TestContext testContext) {
        return new SettingGroupsService(testContext);
    }

    /**
     * Method to create a new setting group
     *
     * @param cookies       cookies
     * @param applicationKey applicationKey
     * @param settingGroup   settingGroup object
     */
    public Response createSettingGroupEndpoint(Cookies cookies, String applicationKey, SettingGroup settingGroup) {
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .body(settingGroup)
                .when()
                .post(SettingsEndpoint.CREATE_SETTING_GROUP)
                .then()
                .extract().response();
        return response;
    }

    /**
     * Method to create a new setting group
     *
     * @param cookies       cookies
     * @param applicationKey applicationKey
     * @param settingGroup   settingGroup object
     */
    public void createSettingGroup(Cookies cookies, String applicationKey, SettingGroup settingGroup) {

        Response response = createSettingGroupEndpoint(cookies, applicationKey, settingGroup);

        assertThat(
                "The Status Code when creating a Setting Group with valid  payload is incorrect:\n" + response.getBody().asString(),
                response.getStatusCode(), equalTo(HttpStatus.CREATED));

        JsonPath jsonPath = response.getBody().jsonPath();
        assertThat("Setting group was not created with the correct Name", jsonPath.get("data.name"), equalTo(settingGroup.getName()));
        assertThat("Setting group was not created with the correct Description", jsonPath.get("data.description"), equalTo(settingGroup.getDescription()));
        assertThat("Setting group was not created with the correct Status", jsonPath.get("data.status"), equalTo(settingGroup.getStatus()));
        testContext.setContext(SettingsConstants.SETTING_GROUP_ID, jsonPath.get("data.id"));
        testContext.setContext(SettingsConstants.SETTING_GROUP_NAME, settingGroup.getName());
    }

    /**
     * Method to try to create a new setting group
     *
     * @param cookies       cookies
     * @param applicationKey applicationKey
     * @param settingGroup   settingGroup object
     */
    public void tryCreateSettingGroup(Cookies cookies, String applicationKey, SettingGroup settingGroup, String expectedErrorMessage) {
        Response response = createSettingGroupEndpoint(cookies, applicationKey, settingGroup);

        assertThat(
                "The Status Code when creating a Setting Group with invalid payload is incorrect:\n" + response.getBody().asString(),
                response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));

        String responseBody = StringUtils.replaceNonBreakSpace(response.getBody().asString());
        assertThat("The setting group error message is incorrect",
                responseBody, containsString(expectedErrorMessage));
    }

    /**
     * Method to update a setting group
     *
     * @param cookies       cookies
     * @param applicationKey applicationKey
     * @param settingGroupID settingGroupID
     * @param settingGroup   settingGroup object
     */
    public void updateSettingGroup(Cookies cookies, String applicationKey, int settingGroupID, SettingGroup settingGroup) {
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .pathParam("settingGroupId", settingGroupID)
                .body(settingGroup)
                .when()
                .put(SettingsEndpoint.UPDATE_SETTING_GROUP)
                .then()
                .statusCode(HttpStatus.OK_RESPONSE)
                .extract().response();

        JsonPath jsonPath = response.getBody().jsonPath();
        assertThat("Setting group was not updated with the correct Name",
                jsonPath.get("data.name"), equalTo(settingGroup.getName()));
        assertThat("Setting group was not updated with the correct Description",
                jsonPath.get("data.description"), equalTo(settingGroup.getDescription()));
        assertThat("Setting group was not updated with the correct Status",
                jsonPath.get("data.status"), equalTo(settingGroup.getStatus()));
    }

    /**
     * Method to get a setting group
     *
     * @param cookies       cookies
     * @param applicationKey applicationKey
     * @param settingGroupId settingGroupId
     * @param settingGroup   settingGroup object
     */
    public void getSettingGroupProperties(Cookies cookies, Integer settingGroupId, String applicationKey, SettingGroup settingGroup) {
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .pathParam("settingGroupId", settingGroupId)
                .when()
                .get(SettingsEndpoint.GET_SETTING_GROUP)
                .then()
                .statusCode(HttpStatus.OK_RESPONSE)
                .extract().response();


        ValidatableResponse validatableResponse = response.then();
        validatableResponse.body(JsonSchemaValidator.matchesJsonSchemaInClasspath(SCHEMAS_SETTING_GROUP_JSON));
        validatableResponse.time(Matchers.lessThanOrEqualTo(ApiConstants.LIMIT_RESPONSE_TIME), TimeUnit.SECONDS);

        JsonPath jsonPath = response.getBody().jsonPath();
        assertThat("Setting group retrieved with incorrect Name",
                jsonPath.get("data.name"), equalTo(settingGroup.getName()));
        assertThat("Setting group retrieved with incorrect Description",
                jsonPath.get("data.description"), equalTo(settingGroup.getDescription()));
        assertThat("Setting group retrieved with incorrect Status",
                jsonPath.get("data.status"), equalTo(settingGroup.getStatus()));
    }

    /**
     * Method to get setting group information as default pagination count
     *
     * @param cookies       cookies
     * @param applicationKey applicationKey
     */
    public void verifyDefaultPaginationForSettingGroup(Cookies cookies, String applicationKey) {
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .when()
                .get(SettingsEndpoint.GET_SETTING_GROUPS)
                .then()
                .statusCode(HttpStatus.OK_RESPONSE)
                .extract().response();

        JsonPath jsonPath = response.getBody().jsonPath();
        assertThat("Setting group retrieved with incorrect default pagination count",
                jsonPath.get("data.limit"), equalTo(25));
    }

    /**
     * Method to get setting group information with limit
     *
     * @param cookies       cookies
     * @param applicationKey applicationKey
     */
    public void verifySettingGroupLimitCount(Cookies cookies, String applicationKey, int limitCount) {
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .queryParam("limit", limitCount)
                .when()
                .get(SettingsEndpoint.GET_SETTING_GROUPS)
                .then()
                .statusCode(HttpStatus.OK_RESPONSE)
                .extract().response();

        JsonPath jsonPath = response.getBody().jsonPath();
        assertThat("Setting group retrieved with incorrect limit count",
                jsonPath.get("data.limit"), equalTo(limitCount));
        assertThat("Setting group retrieved with incorrect IDs count",
                jsonPath.get("data.items.id").toString().split(",").length, equalTo(limitCount));
    }

    /**
     * Method to get setting group information as default limit
     *
     * @param cookies       cookies
     * @param wrongApplicationKey applicationKey
     */
    public void verifyValidationsForSettingGroupAPI(Cookies cookies, String wrongApplicationKey) {
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("applicationKey", wrongApplicationKey)
                .when()
                .get(SettingsEndpoint.GET_SETTING_GROUPS)
                .then()
                .statusCode(HttpStatus.NOT_FOUND)
                .extract().response();

        JsonPath jsonPath = response.getBody().jsonPath();
        assertThat("User is able to access with wrong application key",
                jsonPath.get("error.userMessage"), equalTo("Application Key was not found in the hierarchy."));
    }

    /**
     * Method to verify setting group sort order
     *
     * @param cookies       cookies
     * @param childApplicationKey child applicationKey
     * @param rootApplicationKey  root applicationKey
     * @param order               setting group  order
     */
    public void verifySettingGroupNamesAndSortingOrder(Cookies cookies, String childApplicationKey, String rootApplicationKey, String order) throws SQLException {
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("applicationKey", childApplicationKey)
                .queryParam("sortDirection", order)
                .queryParam("limit", 1000)
                .when()
                .get(SettingsEndpoint.GET_SETTING_GROUPS)
                .then()
                .statusCode(HttpStatus.OK_RESPONSE)
                .extract().response();

        assertThat("Mismatching Sort Direction", response.getBody().path("data.sortDirection"), equalTo(order));
        ArrayList<String> allSettingGroupsNamesFromAPI = response.path("data.items.name");
        List<HashMap<String, Object>> settingGroupNamesDB = settingGroupQueriesExecutor.getSettingGroupNames_ByApplicationKeys(childApplicationKey, rootApplicationKey, order);
        ArrayList<String> settingGroupNamesListFromDataBase = new ArrayList<>();
        for (HashMap<String, Object> entry : settingGroupNamesDB) {
            Set<String> setOf = entry.keySet();
            Iterator<String> iterator = setOf.iterator();
            while (iterator.hasNext()) {
                String keyIs = iterator.next();
                settingGroupNamesListFromDataBase.add((String) entry.get(keyIs));
            }
        }
        assertThat("Mismatching API and Database SettingNames sorting array list", allSettingGroupsNamesFromAPI, equalTo(settingGroupNamesListFromDataBase));
    }

    /**
     * Method to get setting group information as default pagination count
     *
     * @param cookies       cookies
     * @param applicationKey applicationKey
     * @param offset         offset value
     */
    public void verifySettingGroupOffset(Cookies cookies, String applicationKey, int offset) {
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .queryParam("limit", 1000)
                .when()
                .get(SettingsEndpoint.GET_SETTING_GROUPS)
                .then()
                .statusCode(HttpStatus.OK_RESPONSE)
                .extract().response();
        ArrayList<String> totalSettingGroupsNamesFromAPI = response.path("data.items.name");

        Response responseOffset = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .queryParam("offset", offset)
                .queryParam("limit", 1000)
                .when()
                .get(SettingsEndpoint.GET_SETTING_GROUPS)
                .then()
                .statusCode(HttpStatus.OK_RESPONSE)
                .extract().response();

        ArrayList<String> offSetSettingGroupsNamesFromAPI = responseOffset.path("data.items.name");
        assertThat("Setting group retrieved with incorrect offset value",
                responseOffset.path("data.offset"), equalTo(offset));
        assertThat("Setting group retrieved with incorrect offset value of Setting names count",
                offset, equalTo(totalSettingGroupsNamesFromAPI.size() - offSetSettingGroupsNamesFromAPI.size()));
        assertThat("Setting group list returned incorrect items for offset",
                totalSettingGroupsNamesFromAPI.subList(offset, totalSettingGroupsNamesFromAPI.size()), equalTo(offSetSettingGroupsNamesFromAPI));
    }

    /**
     * Method to get setting group exact matching criteria information
     *
     * @param cookies       cookies
     * @param applicationKey     applicationKey
     * @param exactMatchCriteria exact Match Criteria
     */
    public void verifyExactMatchCriteriaForSettingGroup(Cookies cookies, String applicationKey, String exactMatchCriteria) {
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .queryParam("limit", 1000)
                .when()
                .get(SettingsEndpoint.GET_SETTING_GROUPS)
                .then()
                .statusCode(HttpStatus.OK_RESPONSE)
                .extract().response();
        ArrayList<String> totalSettingGroupsNamesFromAPI = response.path("data.items.name");

        Response responseExactMatch = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .queryParam("name", totalSettingGroupsNamesFromAPI.get(0))
                .queryParam("exactMatch", exactMatchCriteria)
                .queryParam("limit", 1000)
                .when()
                .get(SettingsEndpoint.GET_SETTING_GROUPS)
                .then()
                .statusCode(HttpStatus.OK_RESPONSE)
                .extract().response();

        ArrayList<String> exactMatchSettingGroupsNamesFromAPI = responseExactMatch.path("data.items.name");
        if (exactMatchCriteria.contentEquals("true")) {
            assertThat("Setting group exact matching TRUE criteria retrieved with incorrect searched setting group result",
                    totalSettingGroupsNamesFromAPI.get(0), equalTo(exactMatchSettingGroupsNamesFromAPI.get(0)));
        } else {
            boolean flag = true;
            for (String arrayItem : exactMatchSettingGroupsNamesFromAPI) {
                if (!arrayItem.contains(totalSettingGroupsNamesFromAPI.get(0))) {
                    flag = false;
                    break;
                }
            }
            assertThat("Setting group exact matching criteria FALSE retrieved with incorrect searched setting group result",
                    true, equalTo(flag));
        }
    }

    /**
     * Method to get setting group at client level
     *
     * @param cookies       cookies
     * @param settingGroupId settingGroupId
     * @param applicationKey applicationKey
     */
    public void verifyGetSettingGroupAtLevel(Cookies cookies, Integer settingGroupId, String applicationKey) {
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("settingGroupId", settingGroupId)
                .pathParam("applicationKey", applicationKey)
                .when()
                .get(SettingsEndpoint.GET_SETTING_GROUP)
                .then()
                .statusCode(HttpStatus.NOT_FOUND)
                .extract().response();
        JsonPath jsonPath = response.getBody().jsonPath();
        assertThat("User is able to access setting group at different level",
                jsonPath.get("error.userMessage"), equalTo("Setting group not found."));
    }

    /**
     * Method to validate response status code and message of an XHeader request
     *
     * @param responseFromXHeaderRequest response of XHeader request
     * @param requestType                request type
     * @param paramName                  request param name
     * @param requestParamType           param type
     * @param errorMessage               message to compare
     */
    public void validateMessageOfXHeaderRequestResponse(Response responseFromXHeaderRequest, String requestType, String paramName,
                                                        String requestParamType, String errorMessage) {
        assertThat("Status code of " + requestType + " is not correct", responseFromXHeaderRequest.statusCode(),
                is(HttpStatus.UNAUTHORIZED));
        assertThat("Validation Message for " + requestType + " with invalid" + paramName + "as a " + requestParamType
                        + " param is not correct",
                responseFromXHeaderRequest.getBody().asString(), containsString(errorMessage));
    }

    public void verifyCreateSettingGroupXHeadersResponse(String applicationKey, SettingGroup settingGroup,
                                                         String identityApplicationKey, String identityUserId, String paramName,
                                                         String requestParamType, String errorMessage) {
        Response responseCreateByHeaders = given()
                .header("x-identity-applicationKey", identityApplicationKey)
                .header("x-identity-userId", identityUserId)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .body(settingGroup)
                .when()
                .post(SettingsEndpoint.CREATE_SETTING_GROUP)
                .then().log().all()
                .extract().response();
        validateMessageOfXHeaderRequestResponse(responseCreateByHeaders, "create setting group", paramName,
                requestParamType, errorMessage);
    }

    /**
     * Method to update setting group detail using identity header details and verify response
     *
     * @param applicationKey         application key
     * @param settingGroupId         setting group id
     * @param settingGroupValue      setting value
     * @param identityApplicationKey identity application key
     * @param identityUserId         identity user id
     */
    public void verifyUpdateSettingGroupXHeadersResponse(String applicationKey, int settingGroupId, SettingGroupValue settingGroupValue,
                                                         String identityApplicationKey, String identityUserId, String paramName,
                                                         String requestParamType, String errorMessage) {
        Response responseUpdateByHeaders = given()
                .header("x-identity-applicationKey", identityApplicationKey)
                .header("x-identity-userId", identityUserId)
                .contentType("application/json")
                .pathParam("settingGroupId", settingGroupId)
                .pathParam("applicationKey", applicationKey)
                .body(settingGroupValue)
                .when()
                .put(SettingsEndpoint.UPDATE_SETTING_GROUP)
                .then().log().all()
                .extract().response();
        validateMessageOfXHeaderRequestResponse(responseUpdateByHeaders, "update setting group", paramName,
                requestParamType, errorMessage);
    }

    /**
     * Method to get setting group detail using identity header details and verify response
     *
     * @param applicationKey         application key
     * @param settingGroupId         setting group id
     * @param identityApplicationKey identity application key
     * @param identityUserId         identity user id
     */
    public void verifyGetSettingGroupByIDXHeadersResponse(String applicationKey, int settingGroupId, String identityApplicationKey,
                                                          String identityUserId, String paramName, String requestParamType,
                                                          String errorMessage) {
        Response responseGetByHeaders = given()
                .header("x-identity-applicationKey", identityApplicationKey)
                .header("x-identity-userId", identityUserId)
                .contentType("application/json")
                .pathParam("settingGroupId", settingGroupId)
                .pathParam("applicationKey", applicationKey)
                .when()
                .get(SettingsEndpoint.GET_SETTING_GROUP)
                .then().log().all()
                .extract().response();
        validateMessageOfXHeaderRequestResponse(responseGetByHeaders, "get setting group by Id", paramName,
                requestParamType, errorMessage);
    }

    /**
     * Method to get setting groups list using identity header details and verify response
     *
     * @param applicationKey         application key
     * @param identityApplicationKey identity application key
     * @param identityUserId         identity user id
     */
    public void verifyGetSettingGroupsXHeadersResponse(String applicationKey, String identityApplicationKey, String identityUserId,
                                                       String paramName, String requestParamType, String errorMessage) {
        Response responseGetSettingsByHeaders = given()
                .header("x-identity-applicationKey", identityApplicationKey)
                .header("x-identity-userId", identityUserId)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .when()
                .get(SettingsEndpoint.GET_SETTING_GROUPS)
                .then().log().all()
                .extract().response();
        validateMessageOfXHeaderRequestResponse(responseGetSettingsByHeaders, "get setting groups", paramName,
                requestParamType, errorMessage);
    }

    /**
     * Method to update setting group detail using identity header details and verify response
     *
     * @param applicationKey    application key
     * @param settingGroupId    setting group id
     * @param settingGroupValue setting group value
     */
    public void verifyUpdateSettingGroupDetail(Cookies cookies, String applicationKey, int settingGroupId,
                                               SettingGroupValue settingGroupValue, String associationCase, String actionCase) {
        Response updateSettingGroupResponse = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("settingGroupId", settingGroupId)
                .pathParam("applicationKey", applicationKey)
                .body(settingGroupValue)
                .when()
                .put(SettingsEndpoint.UPDATE_SETTING_GROUP)
                .then().log().all()
                .extract().response();

        verifyResponseDetail(associationCase, actionCase, updateSettingGroupResponse, settingGroupValue.getStatus());
    }

    /**
     * Method to validate the response of update setting request response
     *
     * @param associationCase setting to setting group association case
     * @param actionCase      expected setting group  update action case
     * @param apiResponse     update setting group response
     * @param newStatus       new setting group status
     */
    public void verifyResponseDetail(String associationCase, String actionCase, Response apiResponse, String newStatus) {
        JsonPath responseJsonPath = apiResponse.getBody().jsonPath();
        if (actionCase.equalsIgnoreCase("can")) {
            assertThat("Status code displayed when a setting group " + associationCase + " associated settings status is updated to " +
                            newStatus + " is not correct",
                    apiResponse.statusCode(), is(HttpStatus.OK_RESPONSE));
            assertThat("Message displayed when a setting group " + associationCase + " associated settings status is updated to " +
                    newStatus + " is not correct", responseJsonPath.get("data.status"), containsString(newStatus));
        } else {
            assertThat("Status code displayed when a setting group " + associationCase + " associated settings status is updated to " +
                            newStatus + " is not correct", apiResponse.statusCode(), is(HttpStatus.BAD_REQUEST));
            assertThat("Message displayed when a setting group " + associationCase + " associated settings status is updated to " +
                            newStatus + " is not correct", responseJsonPath.get("error.userMessage"),
                    containsString("Setting group can not be inactive."));
        }
    }

    /**
     * Method to verify the list of settings returned when get setting group is requested
     *
     * @param cookies       cookies
     * @param applicationKey applicationKey
     * @param settingGroupId setting group id
     * @param settingsList   expected setting list
     */
    public void verifySettingsListFromGetSettingGroup(Cookies cookies, String applicationKey, int settingGroupId,
                                                      List<Map<String, String>> settingsList) {
        Response getSettingsByGetGroupResponse = given()
                .cookies(cookies)
                .queryParam("includeSettings", "true")
                .contentType("application/json")
                .pathParam("settingGroupId", settingGroupId)
                .pathParam("applicationKey", applicationKey)
                .when()
                .get(SettingsEndpoint.GET_SETTING_GROUP)
                .then().log().all()
                .extract().response();

        JsonPath getSettingsJsonPath = getSettingsByGetGroupResponse.getBody().jsonPath();
        assertThat("Get setting group response code is not correct", getSettingsByGetGroupResponse.statusCode(),
                is(HttpStatus.OK_RESPONSE));
        for (int jsonIndex = 0; jsonIndex < settingsList.size(); jsonIndex++) {
            assertThat("Setting " + settingsList.get(jsonIndex).get("Name") + " is not found in the list",
                    getSettingsJsonPath.get("data.settings.items[" + jsonIndex + "].name"),
                    containsString(settingsList.get(jsonIndex).get("Name")));
        }
    }
}