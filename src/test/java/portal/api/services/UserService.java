package portal.api.services;

import portal.api.endpoints.UserEndpoints;
import portal.api.payloads.UserPayloads;
import portal.constants.HttpStatus;
import portal.models.users.*;
import portal.utils.IntUtils;
import portal.utils.TestContext;
import portal.utils.UserUtils;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserService extends GenericService<UserService> {

    public static final int MAX_VALUE_TO_ADD_IN_EDITION = 50000;


    public static UserService getUserService(TestContext testContext) {
        return new UserService();
    }

    /***
     * Edits the user information using a random number on the first name field
     *
     * @param userToEdit user information for user that is going to be edited
     * @param userCredentialsForEdition credentials of user that is going to be edited
     * @return The information for the new user values
     */
    public User editUserInformationUsingCredentialsFor(User userToEdit, User userCredentialsForEdition) {
        User updatedUser = new User(userToEdit);
        updatedUser.setFirstName(updatedUser.getFirstName() + IntUtils.getRandomNumber(MAX_VALUE_TO_ADD_IN_EDITION));
        UpdateUser updateUser = new UpdateUser(updatedUser);
        updateUserInformation(userCredentialsForEdition, updatedUser.getApplicationKey(), updateUser);
        return updatedUser;
    }

    /***
     * Executes the api call to update the user information with the given info
     *
     * @param userCredentialsForEdition credentials used for the update
     * @param applicationId application id from user to update
     * @param userBodyForUpdate new information for user
     */
    private void updateUserInformation(User userCredentialsForEdition, String applicationId, UpdateUser userBodyForUpdate) {
        given()
                .auth()
                .preemptive()
                .basic(userCredentialsForEdition.getUsername(), userCredentialsForEdition.getPassword())
                .contentType(ContentType.JSON)
                .body(userBodyForUpdate).log().all()
                .put(String.format(UserEndpoints.UPDATE_GET_USERS, applicationId))
                .then()
                .log().all()
                .statusCode(HttpStatus.OK_RESPONSE)
                .extract().response();
    }

    /***
     * Restores the information from user
     *
     * @param userToRestore Old information to perform the update
     */
    public void restoreUserInformation(User userToRestore) {
        Users users = UserUtils.getUsers();
        User user = users.getUserGivenRole("portal");
        UpdateUser updateUser = new UpdateUser(userToRestore);
        updateUserInformation(user, userToRestore.getApplicationKey(), updateUser);
    }

    /***
     * Compares the given user information with the result of the searched user using its login value
     *
     * @param userToReview User to validate against result from service
     * @param userCredentialsForEdition credentials used for the service call
     * @return true if the values matches
     */
    public Boolean userInformationForIdMatchesWithUser(User userToReview, User userCredentialsForEdition) {
        UsersListResponse usersResponse;
        Response response =
                given()
                        .auth()
                        .preemptive()
                        .basic(userCredentialsForEdition.getUsername(), userCredentialsForEdition.getPassword())
                        .log().all()
                        .param("login", userToReview.getUsername())
                        .get(String.format(UserEndpoints.UPDATE_GET_USERS, userToReview.getApplicationKey()))
                        .then()
                        .log().all()
                        .statusCode(HttpStatus.OK_RESPONSE)
                        .extract().response();
        usersResponse = response.getBody().as(UsersListResponse.class);
        return usersResponse.getData().getItems().get(0).compareWithUserOnlyImportantInformation(userToReview);
    }

    /***
     * Tries to create a user with the given credentials at desired level
     *
     * @param userCredentialsForCreation credentials that are going to be use for the creation
     * @param levelToCreateUser desired level to use for the new user
     * @param expectedStatusResponse expected status response
     * @param userToCreate
     * @return the Information from created user
     */
    public User createUserWithCredentialsAtLevel(User userCredentialsForCreation, String levelToCreateUser, int expectedStatusResponse, User userToCreate) {
        CreateUser createUserBody = new CreateUser(userToCreate);
        createUserBody.setApplicationId(levelToCreateUser);
        Response response =
                getCreateUserResponse(userCredentialsForCreation, createUserBody, levelToCreateUser)
                        .then()
                        .log().all()
                        .statusCode(expectedStatusResponse)
                        .extract().response();
        if (expectedStatusResponse == HttpStatus.OK_RESPONSE) {
            User newUser = response.as(CreatedUserResponse.class).data;
            userToCreate.setId(newUser.getId());
        }
        return userToCreate;
    }

    /***
     * Looks for user with given information
     *
     * @param searchedUser Information that the user must contain to be marked as found
     * @return true if there is a user wit the given information
     */
    public Boolean lookForUser(User searchedUser) {
        UsersListResponse usersResponse;
        Users users = UserUtils.getUsers();
        User userCredentialsForSearching = users.getUserGivenRole("portal");
        Response response =
                given()
                        .auth()
                        .preemptive()
                        .basic(userCredentialsForSearching.getUsername(), userCredentialsForSearching.getPassword())
                        .log().all()
                        .param("login", searchedUser.getUsername())
                        .get(String.format(UserEndpoints.UPDATE_GET_USERS, userCredentialsForSearching.getApplicationKey()))
                        .then()
                        .log().all()
                        .statusCode(HttpStatus.OK_RESPONSE)
                        .extract().response();
        usersResponse = response.getBody().as(UsersListResponse.class);
        return usersResponse.getData().getItems().size() > 0;
    }

    /**
     * Performs a search using correct values for the required parameters
     *
     * @param parameters list of desired parameters to be using
     * @return returns the success value of response
     */
    public Boolean successValueForSearchUsingParams(List<String> parameters) {
        UsersListResponse usersResponse;
        Users users = UserUtils.getUsers();
        User userCredentialsForSearching = users.getUserGivenRole("portal");
        RequestSpecification request =
                given()
                        .auth()
                        .preemptive()
                        .basic(userCredentialsForSearching.getUsername(), userCredentialsForSearching.getPassword())
                        .log().all();
        parameters.forEach(param -> {
            String paramInfo = getParamInformation(param);
            request.param(param, paramInfo);
        });
        Response response =
                request
                        .get(String.format(UserEndpoints.UPDATE_GET_USERS, userCredentialsForSearching.getApplicationKey()))
                        .then()
                        .log().all()
                        .statusCode(HttpStatus.OK_RESPONSE)
                        .extract().response();
        usersResponse = response.getBody().as(UsersListResponse.class);
        return usersResponse.getSuccess();
    }

    /**
     * Takes the desired value from a user
     *
     * @param param desired value name
     * @return by default returns null
     */
    private String getParamInformation(String param) {
        Users users = UserUtils.getUsers();
        User userToSearch = users.getUserGivenRole("portal");
        String info;
        switch (param) {
            default:
                info = null;
                break;
            case "login":
                info = userToSearch.getUsername();
                break;
            case "email":
                info = userToSearch.getEmail();
                break;
            case "firstName":
                info = userToSearch.getFirstName();
                break;
            case "lastName":
                info = userToSearch.getLastName();
                break;
        }
        return info;
    }

    /**
     * Tries to create a user with empty values
     *
     * @return the response body
     */
    public CreatedUserResponse createUserWithEmptyBody() {
        Users users = UserUtils.getUsers();
        User userToCreate = users.getUserGivenPurpose("CreateEmpty");
        User userCredentialsForCreation = users.getUserGivenRole("portal");
        CreateUser createUserBody = new CreateUser(userToCreate);
        Response response =
                getCreateUserResponse(userCredentialsForCreation, createUserBody, userToCreate.getHierarchyLevel())
                        .then()
                        .log().all()
                        .statusCode(HttpStatus.BAD_REQUEST)
                        .extract().response();
        return response.getBody().as(CreatedUserResponse.class);
    }

    /**
     * Executes the post to create a user, and logs information before post is executed
     *
     * @param userCredentialsForCreation credentials to be use for creation
     * @param createUserBody             User body to be created
     * @param hierarchyLevel             hierarchy level for the new user
     * @return the response of the user creation
     */
    private Response getCreateUserResponse(User userCredentialsForCreation, CreateUser createUserBody, String hierarchyLevel) {
        return given()
                .auth()
                .preemptive()
                .basic(userCredentialsForCreation.getUsername(), userCredentialsForCreation.getPassword())
                .contentType(ContentType.JSON)
                .body(createUserBody)
                .log().all()
                .post(String.format(UserEndpoints.CREATE_USERS, hierarchyLevel));
    }

    /**
     * Method to update user permission
     *
     * @param userId           user Id
     * @param permissionId     permission Id
     * @param applicationKey   application key
     * @param cookies          cookies
     * @param permissionStatus permission status
     */
    public void updateUserPermission(int userId, int permissionId, String applicationKey, Cookies cookies, String permissionStatus) {
        given()
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .pathParam("userId", userId)
                .pathParam("permissionId", permissionId)
                .cookies(cookies)
                .body("{\"permissionId\": " + permissionId + ",\n" +
                        "\"value\": \"" + permissionStatus + "\"}")
                .when()
                .put(UserEndpoints.UPDATE_USER_PERMISSION)
                .then()
                .extract().response();
    }

    /**
     * Method to create new user
     *
     * @param applicationKey    application key
     * @param userName          username
     * @param firstName         first name
     * @param lastName          last name
     * @param email             email
     * @param preferredName     preferred name
     * @param preferredLanguage preferred language
     * @param cookies           cookies
     * @return CreatedUserResponse
     */
    public CreatedUserResponse createUser(String applicationKey, String userName, String firstName, String lastName, String email, String preferredName,
                                          String preferredLanguage, Cookies cookies) {
        Response response = given()
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .cookies(cookies)
                .body(UserPayloads.createUserPayload(applicationKey, userName, firstName, lastName, email, preferredName, preferredLanguage))
                .when()
                .post(UserEndpoints.CREATE_USER)
                .then().log().all()
                .extract().response();

        assertThat(
                "Create User endpoint status code is incorrect:\n" + response.getBody().asString(),
                response.getStatusCode(), equalTo(HttpStatus.CREATED));
        return response.getBody().as(CreatedUserResponse.class);
    }

    /**
     * Method to get a user by user id
     *
     * @param cookies        cookie
     * @param applicationKey appliaction key
     * @param userId         user id
     * @return user detail
     */
    public Map<String, Object> getUserDetail(Cookies cookies, String applicationKey, int userId) {
        Map<String, Object> userDetail = new HashMap<>();
        Response response = given()
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .pathParam("userId", userId)
                .queryParam("userScope", "Self")
                .cookies(cookies)
                .when()
                .get(UserEndpoints.GET_USER)
                .then().log().all()
                .extract().response();
        JsonPath getUserJson = response.jsonPath();
        userDetail.putAll(getUserJson.get("data"));
        return userDetail;
    }

    /**
     * Method to update a user detail
     *
     * @param cookies        cookie
     * @param applicationKey application key
     * @param userId         user id
     */
    public void updatedUserDetail(Cookies cookies, String applicationKey, int userId) {
        given()
                .contentType("application/json")
                .pathParam("applicationKey", applicationKey)
                .pathParam("userId", userId)
                .cookies(cookies)
                .body(UserPayloads.updateUserPayload(getUserDetail(cookies, applicationKey, userId)))
                .when()
                .put(UserEndpoints.GET_USER)
                .then().log().all()
                .extract().response();
    }
}