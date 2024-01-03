package portal.api.services;

import portal.api.endpoints.SettingsEndpoint;
import portal.constants.ApiConstants;
import portal.constants.HttpStatus;
import portal.constants.SettingsConstants;
import portal.database.queries_executor.SettingQueriesExecutor;
import portal.models.settings.Setting;
import portal.models.settings.SettingProperties;
import portal.models.settings.SettingValue;
import portal.utils.PropertiesReader;
import portal.utils.StringUtils;
import portal.utils.TestContext;
import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SettingsService extends GenericService<SettingsService> {

    public static final String DATA_NAME = "data.name";
    public static final String DATA_DESCRIPTION = "data.description";
    public static final String DATA_VALUE = "data.value";
    public static final String DATA_DATA_TYPE = "data.dataType";
    public static final String DATA_VALIDATION_PATTERN = "data.validationPattern";
    public static final String DATA_STATUS = "data.status";
    public static final String DATA_SETTING_GROUP_ID = "data.settingGroupId";
    public static final String SCHEMAS_SETTING_JSON = "schemas/Setting.json";
    private final TestContext testContext;
    private final SettingQueriesExecutor settingQueriesExecutor;

    private SettingsService(TestContext testContext) {
        this.testContext = testContext;
        settingQueriesExecutor = SettingQueriesExecutor.getSettingQueriesExecutor();
    }

    public static SettingsService getSettingsService(TestContext testContext) {
        return new SettingsService(testContext);
    }

    /**
     * Method to hit the create setting endpoint
     *
     * @param cookies        cookies
     * @param settingGroupId settingGroupID
     * @param applicationKey applicationKey
     * @param setting        setting object
     * @return Response body
     */
    public Response createSettingEndpoint(Cookies cookies, Integer settingGroupId, String applicationKey, Setting setting) {
        return given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("settingGroupId", settingGroupId)
                .pathParam("applicationKey", applicationKey)
                .body(setting)
                .log().body()
                .when()
                .post(SettingsEndpoint.CREATE_SETTING)
                .then().log().all()
                .extract().response();
    }

    /**
     * Method to create a new Setting and verify the response
     *
     * @param cookies        cookies
     * @param settingGroupId settingGroupID
     * @param applicationKey applicationKey
     * @param setting        setting object
     */
    public void createSetting(Cookies cookies, Integer settingGroupId, String applicationKey, Setting setting) {
        Response response = createSettingEndpoint(cookies, settingGroupId, applicationKey, setting);
        assertThat(
                "Create Setting endpoint status code is incorrect:\n" + response.getBody().asString(),
                response.getStatusCode(), equalTo(HttpStatus.CREATED));

        JsonPath jsonPath = response.getBody().jsonPath();
        assertThat("Setting was not created with the correct SettingGroupId", jsonPath.get(DATA_SETTING_GROUP_ID), equalTo(settingGroupId));
        assertThat("Setting was not created with the correct Name", jsonPath.get(DATA_NAME), equalTo(setting.getName()));
        assertThat("Setting was not created with the correct Description", jsonPath.get(DATA_DESCRIPTION), equalTo(setting.getDescription()));
        assertThat("Setting was not created with the correct Value", jsonPath.get(DATA_VALUE), equalTo(setting.getValue()));
        assertThat("Setting was not created with the correct DataType", jsonPath.get(DATA_DATA_TYPE), equalTo(setting.getDataType()));
        assertThat("Setting was not created with the correct Validation Pattern", jsonPath.get(DATA_VALIDATION_PATTERN), equalTo(setting.getValidationPattern()));
        assertThat("Setting was not created with the correct Status", jsonPath.get(DATA_STATUS), equalTo(setting.getStatus()));
        testContext.setContext(SettingsConstants.SETTING_ID, jsonPath.get("data.id"));
    }

    /**
     * Method to update the setting value
     *
     * @param cookies        cookies
     * @param settingId      settingId
     * @param applicationKey applicationKey
     * @param settingValue   SettingValue object
     * @return response
     */
    public Response updateSettingValue(Cookies cookies, Integer settingId, String applicationKey, SettingValue settingValue) {
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("settingId", settingId)
                .pathParam("applicationKey", applicationKey)
                .body(settingValue)
                .when()
                .put(SettingsEndpoint.UPDATE_SETTING_VALUE)
                .then()
                .statusCode(HttpStatus.OK_RESPONSE)
                .extract().response();
        return response;
    }


    /**
     * Method to update the setting properties
     *
     * @param cookies           cookies
     * @param applicationKey    applicationKey
     * @param settingGroupId    setting group ID
     * @param settingId         settingId
     * @param settingProperties setting properties object
     */
    public void updateSettingProperties(Cookies cookies, String applicationKey, Integer settingGroupId, Integer settingId, SettingProperties settingProperties) {
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .pathParam("settingGroupId", settingGroupId)
                .pathParam("settingId", settingId)
                .body(settingProperties)
                .when()
                .put(SettingsEndpoint.UPDATE_SETTING_PROPERTIES)
                .then()
                .statusCode(HttpStatus.OK_RESPONSE)
                .extract().response();

        JsonPath jsonPath = response.getBody().jsonPath();
        assertThat("Setting was not updated with the correct SettingGroupId", jsonPath.get(DATA_SETTING_GROUP_ID), equalTo(settingGroupId));
        assertThat("Setting was not updated with the correct Name", jsonPath.get(DATA_NAME), equalTo(settingProperties.getName()));
        assertThat("Setting was not updated with the correct Description", jsonPath.get(DATA_DESCRIPTION), equalTo(settingProperties.getDescription()));
        assertThat("Setting was not updated with the correct DataType", jsonPath.get(DATA_DATA_TYPE), equalTo(settingProperties.getDataType()));
        assertThat("Setting was not updated with the correct DataType", jsonPath.get(DATA_VALIDATION_PATTERN), equalTo(settingProperties.getValidationPattern()));
        assertThat("Setting was not updated with the correct Status", jsonPath.get(DATA_STATUS), equalTo(settingProperties.getStatus()));
    }

    /**
     * Method to get the setting properties by given the setting Id
     *
     * @param cookies           cookies
     * @param settingId         settingId
     * @param applicationKey    applicationKey
     * @param settingProperties setting object with its properties
     */
    public void getSettingByID(Cookies cookies, Integer settingId, String applicationKey, Setting settingProperties) {
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("settingId", settingId)
                .pathParam("applicationKey", applicationKey)
                .when()
                .get(SettingsEndpoint.GET_SETTING_BY_ID)
                .then()
                .statusCode(HttpStatus.OK_RESPONSE)
                .extract().response();

        JsonPath jsonPath = response.getBody().jsonPath();

        ValidatableResponse validatableResponse = response.then();
        validatableResponse.body(JsonSchemaValidator.matchesJsonSchemaInClasspath(SCHEMAS_SETTING_JSON));
        validatableResponse.time(Matchers.lessThanOrEqualTo(ApiConstants.LIMIT_RESPONSE_TIME), TimeUnit.SECONDS);

        assertThat("Setting retrieved with incorrect Name", jsonPath.get(DATA_NAME), equalTo(settingProperties.getName()));
        assertThat("Setting retrieved with incorrect Description", jsonPath.get(DATA_DESCRIPTION), equalTo(settingProperties.getDescription()));
        assertThat("Setting retrieved with incorrect DataType", jsonPath.get(DATA_DATA_TYPE), equalTo(settingProperties.getDataType()));
        assertThat("Setting retrieved with incorrect DataType", jsonPath.get(DATA_VALIDATION_PATTERN), equalTo(settingProperties.getValidationPattern()));
        assertThat("Setting retrieved with incorrect Status", jsonPath.get(DATA_STATUS), equalTo(settingProperties.getStatus()));
    }

    /**
     * Method to get setting by Name
     *
     * @param cookies           cookies
     * @param applicationKey    application key
     * @param settingProperties setting properties
     * @param settingGroupName  setting group name
     */
    public void getSettingByName(Cookies cookies, String applicationKey, Setting settingProperties, String settingGroupName) {
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .queryParam("name", settingProperties.getName())
                .queryParam("group", settingGroupName)
                .when()
                .get(SettingsEndpoint.GET_SETTING_BY_NAME)
                .then()
                .statusCode(HttpStatus.OK_RESPONSE)
                .extract().response();

        JsonPath jsonPath = response.getBody().jsonPath();
        assertThat("Setting retrieved with incorrect Name", jsonPath.get("data.items.name[0]"), equalTo(settingProperties.getName()));
        assertThat("Setting retrieved with incorrect Description", jsonPath.get("data.items.description[0]"), equalTo(settingProperties.getDescription()));
        assertThat("Setting retrieved with incorrect DataType", jsonPath.get("data.items.dataType[0]"), equalTo(settingProperties.getDataType()));
        assertThat("Setting retrieved with incorrect DataType", jsonPath.get("data.items.validationPattern[0]"), equalTo(settingProperties.getValidationPattern()));
        assertThat("Setting retrieved with incorrect Status", jsonPath.get("data.items.status[0]"), equalTo(settingProperties.getStatus()));
    }

    /**
     * Method to try to create a new invalid setting
     *
     * @param cookies              cookies
     * @param settingGroupId       settingGroupID
     * @param applicationKey       applicationKey
     * @param setting              setting object
     * @param expectedErrorMessage expected error message
     */
    public void tryCreateSetting(Cookies cookies, Integer settingGroupId, String applicationKey, Setting setting
            , String expectedErrorMessage) {
        Response response = createSettingEndpoint(cookies, settingGroupId, applicationKey, setting);
        assertThat("Create Setting endpoint status code is incorrect when trying to duplicate setting name",
                response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
        String responseBody = StringUtils.replaceNonBreakSpace(response.getBody().asString());
        assertThat("The error message when trying to create an invalid setting is incorrect",
                responseBody, containsString(expectedErrorMessage));
    }

    /**
     * Method to verify setting versions count api along with values
     *
     * @param cookies        cookies
     * @param settingId      settingID
     * @param applicationKey applicationKey
     * @param versionCount   version count
     * @param versionValues  version values
     */
    public void verifyVersionCountAndValues(Cookies cookies, Integer settingId, String applicationKey, int versionCount,
                                            List<String> versionValues) {
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .pathParam("settingId", settingId)
                .when()
                .get(SettingsEndpoint.GET_SETTING_VERSION_DETAILS)
                .then()
                .statusCode(HttpStatus.OK_RESPONSE)
                .extract().response();

        assertThat("Mismatching Setting version Count", response.getBody().path("data.totalCount"), equalTo(versionCount));
        for (int i = 0; i < versionCount; i++) {
            assertThat("Mismatching version value for index " + i,
                    response.getBody().path("data.items.value[" + i + "]"), containsString(versionValues.get(i)));
        }
    }

    /**
     * Method to verify setting limit and sort direction along with values
     *
     * @param cookies        cookies
     * @param settingId      settingID
     * @param applicationKey applicationKey
     *                       n@param newValue Updated Value
     * @param limit          setting version limit count
     * @param order          setting version order
     */
    public void verifyLimitAndOrderForSettingAPI(Cookies cookies, Integer settingId, String applicationKey, int limit, String order) {
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .pathParam("settingId", settingId)
                .queryParam("limit", limit)
                .queryParam("sortDirection", order)
                .when()
                .get(SettingsEndpoint.GET_SETTING_VERSION_DETAILS)
                .then()
                .statusCode(HttpStatus.OK_RESPONSE)
                .extract().response();

        assertThat("Versions displayed are not equal to the limit ", response.getBody().path("data.items.size()"), equalTo(limit));
        assertThat("Mismatching limit value", response.getBody().path("data.limit"), equalTo(limit));
        assertThat("Mismatching Sort Direction", response.getBody().path("data.sortDirection"), equalTo(order));
    }

    /**
     * Method to update the setting  with value
     *
     * @param cookies        cookies
     * @param settingId      settingId
     * @param applicationKey applicationKey
     * @param settingValue   SettingValue object
     */
    public void updateSettingWithValue(Cookies cookies, Integer settingId, String applicationKey, SettingValue settingValue) {
        Response response = updateSettingValue(cookies, settingId, applicationKey, settingValue);
        JsonPath jsonPath = response.getBody().jsonPath();
        assertThat("Setting was not Updated with the correct Value", jsonPath.get(DATA_VALUE), equalTo(settingValue.getValue()));
    }

    /**
     * Method to get settings which are related to Setting groupId information as default pagination count
     *
     * @param cookies        cookies
     * @param settingGroupId Setting group ID
     * @param applicationKey applicationKey
     */
    public void verifyDefaultPaginationForSettings(Cookies cookies, int settingGroupId, String applicationKey) {

        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .queryParam("groupId", settingGroupId)
                .pathParam("applicationKey", applicationKey)
                .when()
                .get(SettingsEndpoint.GET_SETTING_BY_NAME)
                .then()
                .statusCode(HttpStatus.OK_RESPONSE)
                .extract().response();

        JsonPath jsonPath = response.getBody().jsonPath();
        assertThat("Settings retrieved with incorrect default pagination count",
                jsonPath.get("data.limit"), equalTo(25));
    }

    /**
     * Method to get settings information with limit count
     *
     * @param cookies        cookies
     * @param settingGroupId setting group id
     * @param applicationKey applicationKey
     * @param limitCount     limit count
     */
    public void verifyLimitForTheSettingsAPIRequest(Cookies cookies, int settingGroupId, String applicationKey, int limitCount) {
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .queryParam("groupId", settingGroupId)
                .queryParam("limit", limitCount)
                .when()
                .get(SettingsEndpoint.GET_SETTING_BY_NAME)
                .then()
                .statusCode(HttpStatus.OK_RESPONSE)
                .extract().response();

        JsonPath jsonPath = response.getBody().jsonPath();
        assertThat("Settings retrieved with incorrect limit count",
                jsonPath.get("data.limit"), equalTo(limitCount));
    }

    /**
     * Method to verify settings names along with sort order
     *
     * @param cookies        cookies
     * @param applicationKey applicationKey
     * @param order          settings order
     */
    public void verifySettingsNameAlongWithSortingOrder(Cookies cookies, String applicationKey, int settingGroupID, String order) {
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .queryParam("groupId", settingGroupID)
                .queryParam("status", "Active")
                .queryParam("sortDirection", order)
                .when()
                .get(SettingsEndpoint.GET_SETTING_BY_NAME)
                .then().log().all()
                .statusCode(HttpStatus.OK_RESPONSE)
                .extract().response();

        assertThat("Mismatching Settings Sort Direction", response.getBody().path("data.sortDirection"), equalTo(order));

        ArrayList<String> allSettingsNamesFromAPI = response.path("data.items.name");
        List<HashMap<String, Object>> settingsNamesFromDB = settingQueriesExecutor.getSettingsNames_BySettingGroupID(String.valueOf(settingGroupID), order);
        ArrayList<String> settingGroupIDArrayList = new ArrayList<>();

        for (HashMap<String, Object> entry : settingsNamesFromDB) {
            Set<String> setOf = entry.keySet();
            Iterator<String> iterator = setOf.iterator();
            while (iterator.hasNext()) {
                String keyIs = iterator.next();
                settingGroupIDArrayList.add((String) entry.get(keyIs));
            }
        }
        assertThat("Mismatching API and Database SettingNames sorting array list", allSettingsNamesFromAPI.toString(), equalTo(settingGroupIDArrayList.toString()));
    }

    /**
     * Method to get settings exact matching criteria information
     *
     * @param cookies            cookies
     * @param applicationKey     applicationKey
     * @param settingGroupID     setting GroupID
     * @param exactMatchCriteria exact Match Criteria
     */
    public void verifyExactMatchCriteriaForSettings(Cookies cookies, String applicationKey, int settingGroupID, String exactMatchCriteria) {
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .queryParam("groupId", settingGroupID)
                .queryParam("limit", 1000)
                .when()
                .get(SettingsEndpoint.GET_SETTING_BY_NAME)
                .then()
                .statusCode(HttpStatus.OK_RESPONSE)
                .extract().response();
        ArrayList<String> totalSettingsNamesFromAPI = response.path("data.items.name");

        Response responseExactMatch = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .queryParam("name", totalSettingsNamesFromAPI.get(0))
                .queryParam("groupId", settingGroupID)
                .queryParam("exactMatch", exactMatchCriteria)
                .queryParam("limit", 1000)
                .when()
                .get(SettingsEndpoint.GET_SETTING_BY_NAME)
                .then()
                .statusCode(HttpStatus.OK_RESPONSE)
                .extract().response();

        ArrayList<String> exactMatchSettingsNamesFromAPI = responseExactMatch.path("data.items.name");
        if (exactMatchCriteria.contentEquals("true")) {
            assertThat("Settings exact matching TRUE criteria retrieved with incorrect searched settings result",
                    totalSettingsNamesFromAPI.get(0), equalTo(exactMatchSettingsNamesFromAPI.get(0)));
        } else {
            boolean flag = true;
            for (String arrayItem : exactMatchSettingsNamesFromAPI) {
                if (!arrayItem.contains(totalSettingsNamesFromAPI.get(0))) {
                    flag = false;
                    break;
                }
            }
            assertThat("Settings exact matching criteria FALSE retrieved with incorrect searched settings result",
                    true, equalTo(flag));
        }
    }

    /**
     * Method to hit the update setting endpoint
     *
     * @param cookies        cookies
     * @param settingGroupId settingGroupID
     * @param applicationKey applicationKey
     * @param settingData    setting object
     * @return Response body
     */
    public Response updateSettingProperties(Cookies cookies, Integer settingGroupId, Integer settingId, String applicationKey, SettingValue settingData) {
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("settingGroupId", settingGroupId)
                .pathParam("settingId", settingId)
                .pathParam("applicationKey", applicationKey)
                .body(settingData)
                .when()
                .put(SettingsEndpoint.UPDATE_SETTING_PROPERTIES)
                .then()
                .extract().response();
        return response;
    }

    /**
     * Method to try to update the setting Enum data type
     *
     * @param cookies              cookies
     * @param settingGroupId       settingGroupId
     * @param settingId            settingId
     * @param applicationKey       applicationKey
     * @param settingData          SettingValue object
     * @param expectedErrorMessage expected error message
     */
    public void tryUpdateSettingEnumDataType(Cookies cookies, Integer settingGroupId, Integer settingId, String applicationKey, SettingValue settingData, String expectedErrorMessage) {
        Response response = updateSettingProperties(cookies, settingGroupId, settingId, applicationKey, settingData);
        assertThat("Update Setting Properties endpoint status code is incorrect when trying to update Enum data type",
                response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
        String responseBody = StringUtils.replaceNonBreakSpace(response.getBody().asString());
        assertThat("The error message when trying to update Enum data type is incorrect", responseBody, containsString(expectedErrorMessage));
    }

    /**
     * Method to verify GetDataTypes api along with values
     *
     * @param cookies        cookies
     * @param applicationKey applicationKey
     */
    public void verifyGetDataTypesAPI(Cookies cookies, String applicationKey) {
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .when()
                .get(SettingsEndpoint.GET_DATA_TYPES)
                .then()
                .extract().response();

        assertThat("Response doesn't have correct status code", response.getStatusCode(), equalTo(HttpStatus.OK_RESPONSE));
        ArrayList<String> allSettingDataTypeNamesFromAPI = response.path("data.items.name");
        List<HashMap<String, Object>> settingsDataTypesFromDB = settingQueriesExecutor.getAllDataTypes();
        //Column name you want to filter and make list of
        String columnName = "Name";
        //use String util function that will convert list of hashmaps from db to arraylist of strings based your column name;
        ArrayList<String> settingDataTypeNamesFromDB = StringUtils.getListOfStringFromListOfHashMap(settingsDataTypesFromDB,
                columnName);

        assertThat("Mismatching Setting data types Count", response.getBody().path("data.totalCount"), equalTo(settingsDataTypesFromDB.size()));
        assertThat("Mismatching API and Database data types Name lists", allSettingDataTypeNamesFromAPI, equalTo(settingDataTypeNamesFromDB));

    }

    /**
     * Method to verify GetDataTypes api along with values
     *
     * @param cookies        cookies
     * @param applicationKey applicationKey
     * @param limit          limit
     */
    public void verifyLimitForGetDataTypesAPI(Cookies cookies, String applicationKey, Integer limit) {
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .queryParam("limit", limit)
                .when()
                .get(SettingsEndpoint.GET_DATA_TYPES)
                .then()
                .extract().response();

        assertThat("Response doesn't have correct status code", response.getStatusCode(), equalTo(HttpStatus.OK_RESPONSE));
        JsonPath jsonPath = response.getBody().jsonPath();
        if ((Integer) jsonPath.get("data.totalCount") >= limit) {
            ArrayList<String> settingDataTypesFromAPI = response.path("data.items");
            assertThat("Setting Data Types size is not aligned with limit count",
                    settingDataTypesFromAPI.size(), equalTo(limit));

            assertThat("Setting Data Types retrieved with incorrect limit count",
                    jsonPath.get("data.limit"), equalTo(limit));
        }
    }

    /**
     * Method to verify GetDataTypes api along with values
     *
     * @param cookies        cookies
     * @param applicationKey applicationKey
     * @param sortBy         sortType
     * @param orderBy        sortDirection
     */
    public void verifySortTypeAndSortOrderForGetDataTypesAPI(Cookies cookies, String applicationKey, String sortBy, String orderBy) {
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .queryParam("sortType", sortBy)
                .queryParam("sortDirection", orderBy)
                .when()
                .get(SettingsEndpoint.GET_DATA_TYPES)
                .then()
                .extract().response();

        assertThat("Response doesn't have correct status code", response.getStatusCode(), equalTo(HttpStatus.OK_RESPONSE));
        ArrayList<String> allSettingDataTypeNamesFromAPI = response.path("data.items.name");
        List<HashMap<String, Object>> settingsDataTypesFromDB = settingQueriesExecutor.getDataTypesAfterSortAndOrder(sortBy, orderBy);
        //Column name you want to filter and make list of
        String columnName = "Name";
        //use String util function that will convert list of hashmaps from db to arraylist of strings based your column name;
        ArrayList<String> settingDataTypeNamesFromDB = StringUtils.getListOfStringFromListOfHashMap(settingsDataTypesFromDB,
                columnName);

        assertThat("Mismatching Setting data types Count", response.getBody().path("data.totalCount"),
                equalTo(settingsDataTypesFromDB.size()));
        assertThat("Mismatching dataType lists between Db abd API after sort and order", allSettingDataTypeNamesFromAPI,
                equalTo(settingDataTypeNamesFromDB));

    }

    /**
     * A method to get Setting Data Type by name and with the following value
     *
     * @param cookies        cookies
     * @param applicationKey applicationKey
     * @param dataTypeName   name
     * @param exactMatch     exactMatch
     */
    public void verifyGetDataTypesAPIByNameAndExactMatch(Cookies cookies, String applicationKey, String dataTypeName,
                                                         String exactMatch) {
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .queryParam("name", dataTypeName)
                .queryParam("exactMatch", exactMatch)
                .when()
                .get(SettingsEndpoint.GET_DATA_TYPES)
                .then()
                .extract().response();

        assertThat("Response doesn't have correct status code", response.getStatusCode(), equalTo(HttpStatus.OK_RESPONSE));
        ArrayList<String> allSettingDataTypeNamesFromAPI = response.path("data.items.name");
        List<HashMap<String, Object>> settingsDataTypesFromDB = settingQueriesExecutor.getDataTypesByNameAndExactMatch(dataTypeName, exactMatch);
        //Column name you want to filter and make list of
        String columnName = "Name";
        //use String util function that will convert list of hashmaps from db to arraylist of strings based your column name;
        ArrayList<String> settingDataTypeNamesFromDB = StringUtils.getListOfStringFromListOfHashMap(settingsDataTypesFromDB,
                columnName);

        assertThat("Mismatching Setting data types Count", allSettingDataTypeNamesFromAPI.size(), equalTo(settingsDataTypesFromDB.size()));
        assertThat("Mismatching dataType lists between Db abd API after sort and order", allSettingDataTypeNamesFromAPI, equalTo(settingDataTypeNamesFromDB));
    }

    /**
     * Method to validate response status code and message of an XHeader request
     *
     * @param responseFromXHeaderRequest response of XHeader request
     * @param requestType                request type
     * @param paramName                  request param name
     * @param requestParamType           param type
     * @param errorMessage               message to be displayed
     */
    public void validateMessageOfXHeaderRequestResponse(Response responseFromXHeaderRequest, String requestType, String paramName,
                                                        String requestParamType, String errorMessage) {
        assertThat("Status code of " + requestType + " is not correct", responseFromXHeaderRequest.statusCode(),
                is(HttpStatus.UNAUTHORIZED));
        assertThat("Validation Message for " + requestType + " with invalid" + paramName + "as a " + requestParamType
                        + " param is not correct",
                responseFromXHeaderRequest.getBody().asString(), containsString(errorMessage));
    }

    /**
     * Method to create setting using x-identity headers
     *
     * @param settingGroupId         settingGroupId
     * @param applicationKey         applicationKey
     * @param setting                setting object
     * @param identityApplicationKey x-identity-applicationKey
     * @param identityUserId         x-identity-userId
     */
    public void verifyCreateSettingUsingXHeadersResponse(String applicationKey, Integer settingGroupId, Setting setting,
                                                         String identityApplicationKey, String identityUserId, String paramName,
                                                         String requestParamType, String errorMessage) {
        Response responseCreateByHeaders = given()
                .header("x-identity-applicationKey", identityApplicationKey)
                .header("x-identity-userId", identityUserId)
                .contentType("application/json")
                .pathParam("settingGroupId", settingGroupId)
                .pathParam("applicationKey", applicationKey)
                .body(setting)
                .log().body()
                .when()
                .post(SettingsEndpoint.CREATE_SETTING)
                .then().log().all()
                .extract().response();

        validateMessageOfXHeaderRequestResponse(responseCreateByHeaders, "create setting", paramName, requestParamType, errorMessage);
    }

    /**
     * Method to update setting detail using identity header details and verify response
     *
     * @param applicationKey         application key
     * @param settingId              setting id
     * @param settingValue           setting value
     * @param identityApplicationKey identity application key
     * @param identityUserId         identity user id
     */
    public void verifyUpdateSettingXHeadersResponse(String applicationKey, Integer settingId, SettingValue settingValue,
                                                    String identityApplicationKey, String identityUserId, String paramName,
                                                    String requestParamType, String errorMessage) {
        Response responseUpdateByHeaders = given()
                .header("x-identity-applicationKey", identityApplicationKey)
                .header("x-identity-userId", identityUserId)
                .contentType("application/json")
                .pathParam("settingId", settingId)
                .pathParam("applicationKey", applicationKey)
                .body(settingValue)
                .when()
                .put(SettingsEndpoint.UPDATE_SETTING_VALUE)
                .then().log().all()
                .extract().response();
        validateMessageOfXHeaderRequestResponse(responseUpdateByHeaders, "update setting", paramName,
                requestParamType, errorMessage);
    }

    /**
     * Method to get setting by id using identity header details and verify response
     *
     * @param applicationKey         application key
     * @param settingId              setting id
     * @param identityApplicationKey identity application key
     * @param identityUserId         identity user id
     * @param paramName              request param name
     * @param requestParamType       param type
     * @param errorMessage           error message
     */
    public void verifyGetSettingByIDXHeadersResponse(String applicationKey, Integer settingId,
                                                     String identityApplicationKey, String identityUserId, String paramName,
                                                     String requestParamType, String errorMessage) {
        Response responseGetByHeaders = given()
                .header("x-identity-applicationKey", identityApplicationKey)
                .header("x-identity-userId", identityUserId)
                .contentType("application/json")
                .pathParam("settingId", settingId)
                .pathParam("applicationKey", applicationKey)
                .when()
                .get(SettingsEndpoint.GET_SETTING_BY_ID)
                .then().log().all()
                .extract().response();
        validateMessageOfXHeaderRequestResponse(responseGetByHeaders, "get setting by Id", paramName,
                requestParamType, errorMessage);
    }

    /**
     * Method to get settings using identity header details and verify response
     *
     * @param applicationKey         application key
     * @param settingGroupId         setting group id
     * @param identityApplicationKey identity application key
     * @param identityUserId         identity user id
     * @param paramName              request param name
     * @param requestParamType       request param type
     * @param errorMessage           error message
     */
    public void verifyGetSettingsByIDXHeadersResponse(String applicationKey, int settingGroupId,
                                                      String identityApplicationKey, String identityUserId, String paramName,
                                                      String requestParamType, String errorMessage) {
        Response responseGetSettingsByHeaders = given()
                .header("x-identity-applicationKey", identityApplicationKey)
                .header("x-identity-userId", identityUserId)
                .contentType("application/json")
                .queryParams("settingGroupId", settingGroupId)
                .pathParam("applicationKey", applicationKey)
                .when()
                .get(SettingsEndpoint.GET_SETTING_BY_NAME)
                .then().log().all()
                .extract().response();
        validateMessageOfXHeaderRequestResponse(responseGetSettingsByHeaders, "get settings", paramName,
                requestParamType, errorMessage);
    }

    /**
     * Method to get setting at client level
     *
     * @param cookies        cookies
     * @param settingId      settingId
     * @param applicationKey applicationKey
     */
    public void verifyGetSettingAtLevel(Cookies cookies, Integer settingId, String applicationKey) {
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("settingId", settingId)
                .pathParam("applicationKey", applicationKey)
                .when()
                .get(SettingsEndpoint.GET_SETTING_BY_ID)
                .then()
                .statusCode(HttpStatus.NOT_FOUND)
                .extract().response();
        JsonPath jsonPath = response.getBody().jsonPath();
        assertThat("User is able to access setting at different level",
                jsonPath.get("error.userMessage"), equalTo("Setting not found."));
    }

    /**
     * Method to verify GetEnumDataTypes api along with values
     *
     * @param cookies        cookies
     * @param applicationKey applicationKey
     * @param id             enumDataTypeId
     */
    public void verifyGetEnumDataTypesAPI(Cookies cookies, String applicationKey, Integer id) {
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .pathParam("id", id)
                .when()
                .get(SettingsEndpoint.GET_ENUM_DATA_TYPE)
                .then()
                .extract().response();

        assertThat("Response doesn't have correct status code", response.getStatusCode(), equalTo(HttpStatus.OK_RESPONSE));
        ArrayList<String> settingEnumDataTypeNamesFromAPI = response.path("data.items.name");
        List<HashMap<String, Object>> settingsEnumDataTypesFromDB = settingQueriesExecutor.getEnumDataTypes(id);
        //Column name you want to make list of
        String columnName = "Name";
        ArrayList<String> settingEnumDataTypeNamesFromDB = StringUtils.getListOfStringFromListOfHashMap
                (settingsEnumDataTypesFromDB, columnName);

        assertThat("Mismatching Enum data types Count", response.getBody().path("data.totalCount"),
                equalTo(settingsEnumDataTypesFromDB.size()));
        assertThat("Mismatching API and Database Enum data types Name lists", settingEnumDataTypeNamesFromAPI,
                equalTo(settingEnumDataTypeNamesFromDB));
    }

    /**
     * Method to verify GetDataTypes api along with values
     *
     * @param cookies        cookies
     * @param applicationKey applicationKey
     * @param enumDataTypeId id
     * @param limit          limit
     */
    public void verifyLimitForGetEnumDataTypeAPI(Cookies cookies, String applicationKey, Integer enumDataTypeId,
                                                 Integer limit) {
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .pathParam("id", enumDataTypeId)
                .queryParam("limit", limit)
                .when()
                .get(SettingsEndpoint.GET_ENUM_DATA_TYPE)
                .then()
                .extract().response();

        assertThat("Response doesn't have correct status code", response.getStatusCode(), equalTo(HttpStatus.OK_RESPONSE));
        JsonPath jsonPath = response.getBody().jsonPath();
        if ((Integer) jsonPath.get("data.totalCount") >= limit) {
            ArrayList<String> settingDataTypesFromAPI = response.path("data.items");
            assertThat("Setting Data Types size is not aligned with limit count",
                    settingDataTypesFromAPI.size(), equalTo(limit));

            assertThat("Setting Data Types retrieved with incorrect limit count",
                    jsonPath.get("data.limit"), equalTo(limit));
        }
    }


    /**
     * Method to verify GetEnumDataTypes api along with values
     *
     * @param cookies        cookies
     * @param applicationKey applicationKey
     * @param enumDataTypeId enumDataTypeId
     * @param sortBy         sortType
     * @param orderBy        sortDirection
     */
    public void verifySortTypeAndSortOrderForGetEnumDataTypeAPI(Cookies cookies, String applicationKey,
                                                                Integer enumDataTypeId, String sortBy, String orderBy) {
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .pathParam("id", enumDataTypeId)
                .queryParam("sortType", sortBy)
                .queryParam("sortDirection", orderBy)
                .when()
                .get(SettingsEndpoint.GET_ENUM_DATA_TYPE)
                .then()
                .extract().response();

        assertThat("Response doesn't have correct status code", response.getStatusCode(), equalTo(HttpStatus.OK_RESPONSE));
        ArrayList<String> allSettingDataTypeNamesFromAPI = response.path("data.items.name");
        List<HashMap<String, Object>> settingsDataTypesFromDB = settingQueriesExecutor.getEnumDataTypesAfterSortAndOrder(enumDataTypeId, sortBy, orderBy);

        //use String util function that will convert list of hashmaps from db to arraylist of strings based your column name;
        ArrayList<String> settingDataTypeNamesFromDB = StringUtils.getListOfStringFromListOfHashMap(settingsDataTypesFromDB,
                "Name");

        assertThat("Mismatching Setting data types Count", response.getBody().path("data.totalCount"),
                equalTo(settingsDataTypesFromDB.size()));
        assertThat("Mismatching dataType lists between Db abd API after sort and order", allSettingDataTypeNamesFromAPI,
                equalTo(settingDataTypeNamesFromDB));

    }

    /**
     * This method will verify enum data types are displayed with options
     *
     * @param cookies        cookies
     * @param applicationKey applicationKey
     * @param enumDataTypeId enumDataTypeId
     * @param includeOption  include option /true or false
     */
    public Response getEnumDataTypesWithIncludeParameter(Cookies cookies, String applicationKey,
                                                         Integer enumDataTypeId, String includeOption) {
        return given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .pathParam("id", enumDataTypeId)
                .queryParam("includeOptions", includeOption)
                .when()
                .get(SettingsEndpoint.GET_ENUM_DATA_TYPE)
                .then()
                .extract().response();
    }

    public void verifyGetEnumDataTypesWithIncludeParameterTrue(Cookies cookies, String applicationKey,
                                                               Integer enumDataTypeId, String includeOption) {

        Response response = getEnumDataTypesWithIncludeParameter(cookies, applicationKey,
                enumDataTypeId, includeOption);

        assertThat("Response doesn't have correct status code", response.getStatusCode(),
                equalTo(HttpStatus.OK_RESPONSE));

        //Retrieve one enum datatypeId which has options from EnumDataTypeOptions - get id and option count
        List<HashMap<String, Object>> enumDataTypeIDWithOptionCountMap = settingQueriesExecutor.getEnumDataTypesWithOptionCount();
        JsonPath json = response.getBody().jsonPath();
        //create list of hashMap of datatypeId and Option Count
        ArrayList<HashMap<Integer, Integer>> listOfEnumDataTypeAndOptionCountFromDb = new ArrayList<HashMap<Integer, Integer>>();

        for (HashMap<String, Object> optionCountDb : enumDataTypeIDWithOptionCountMap) {
            //HashMap of datatypeId and Option Count
            HashMap<Integer, Integer> optionCountHashMapFromDb = new HashMap<>();
            int keyId = 0, optionCountValue = 0;
            for (String key : optionCountDb.keySet()) {
                if (key.equalsIgnoreCase("EnumDataTypeId")) {
                    keyId = (Integer) optionCountDb.get(key);
                } else if (key.equalsIgnoreCase("OptionCount")) {
                    optionCountValue = (Integer) optionCountDb.get(key);
                }
            }
            optionCountHashMapFromDb.put(keyId, optionCountValue);
            listOfEnumDataTypeAndOptionCountFromDb.add(optionCountHashMapFromDb);
        }
        ArrayList<HashMap<Integer, Integer>> listOfEnumDataTypeAndOptionCountFromAPI = new ArrayList<HashMap<Integer, Integer>>();
        //prepare list of id and option count from API
        //get items size to loop in

        Integer sizeOfItems = json.get("data.items.size()");
        for (int i = 0; i < sizeOfItems; i++) {
            HashMap<Integer, Integer> optionCountHashMapFromApi = new HashMap<>();
            int id = (Integer) json.get("data.items[" + i + "].id");
            int optionsCount = json.get("data.items[" + i + "].options.size()");
            optionCountHashMapFromApi.put(id, optionsCount);
            if (!optionCountHashMapFromApi.isEmpty())
                listOfEnumDataTypeAndOptionCountFromAPI.add(optionCountHashMapFromApi);
        }
        assertThat("Mismatching list size", listOfEnumDataTypeAndOptionCountFromAPI.size(),
                equalTo(listOfEnumDataTypeAndOptionCountFromDb.size()));
        assertThat("Mismatching dataType option count between Db and API ",
                listOfEnumDataTypeAndOptionCountFromAPI, equalTo(listOfEnumDataTypeAndOptionCountFromDb));
    }

    /**
     * This method will verify enum data types are displayed without options with the below params
     *
     * @param cookies        cookies
     * @param applicationKey applicationKey
     * @param enumDataTypeId enumDataTypeId
     * @param includeOption  include option /true or false
     */
    public void verifyGetEnumDataTypesWithOutOption(Cookies cookies, String applicationKey,
                                                    Integer enumDataTypeId, String includeOption) {
        Response response = getEnumDataTypesWithIncludeParameter(cookies, applicationKey,
                enumDataTypeId, includeOption);

        assertThat("Response doesn't have correct status code", response.getStatusCode(),
                equalTo(HttpStatus.OK_RESPONSE));

        JsonPath json = response.getBody().jsonPath();
        Integer sizeOfItems = json.get("data.items.size()");
        for (int i = 0; i < sizeOfItems; i++) {
            Integer sizeOfOptions = json.get("data.items[" + i + "].options.size()");
            assertThat("Options size is greater tha 0, Options are listed", sizeOfOptions, equalTo(0));
        }
    }

    /**
     * Method to get setting value by group and setting name
     * @param cookies cookie
     * @param applicationKey application key
     * @param settingGroupName group name
     * @param settingName setting name
     * @return setting value
     */
    public String getSettingByGroupAndName(Cookies cookies, String applicationKey,
                                         String settingGroupName, String settingName){
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .queryParam("name", settingName)
                .queryParam("group", settingGroupName)
                .when()
                .get(SettingsEndpoint.GET_SETTING_BY_NAME)
                .then()
                .statusCode(HttpStatus.OK_RESPONSE)
                .extract().response();
        JsonPath json = response.getBody().jsonPath();
        return json.get("data.items.value[0]").toString();
    }

    /**
     * Method to get settings list by content group name
     * @param cookies cookie
     * @param applicationKey application key
     * @param settingGroupName setting group name
     * @return api response
     */
    public Response getSettingsByGroupName(Cookies cookies, String applicationKey, String settingGroupName) {
        RestAssured.baseURI = PropertiesReader.getDefaultOrPreferredSetting("gatewayApiEndpoint", "gatewayApiEndpoint");
        Response response = given()
                .cookies(cookies)
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .queryParam("groupName", settingGroupName)
                .when()
                .get(SettingsEndpoint.GET_SETTING_BY_NAME)
                .then()
                .statusCode(HttpStatus.OK_RESPONSE)
                .extract().response();
        return response;
    }

    /**
     * Method to get list of settings with value by content group
     * @param cookies cookies
     * @param applicationKey application key
     * @param settingGroupName setting group name
     * @param settingNames setting names
     * @return settingNameValueList
     */
    public Map<String, String> getSettingsNameAndValue(Cookies cookies, String applicationKey, String settingGroupName, List<String> settingNames) {
        Response response = getSettingsByGroupName(cookies, applicationKey, settingGroupName);
        String responseAsString = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseAsString);
        Map<String, String> settingNameValueList = new HashMap<>();
        List<String> nameList = jsonPath.get("data.items.name");
        List<String> valueList = jsonPath.get("data.items.value");
        for (int i = 0; i < nameList.size(); i++) {
            if (settingNames.contains(nameList.get(i))) {
                settingNameValueList.put(nameList.get(i), valueList.get(i));
            }
        }
        return settingNameValueList;
    }
}