package portal.steps;

import static com.codeborne.selenide.Selenide.open;

import portal.api.services.ContentDeliveryService;
import portal.api.services.SettingsService;
import portal.api.services.UserService;
import portal.constants.CookiesConstant;
import portal.constants.UserConstants;
import portal.database.queries_executor.LoginQueriesExecutor;
import portal.database.queries_executor.UserQueriesExecutor;
import portal.models.localizations.Localization;
import portal.models.users.User;
import portal.models.users.Users;
import portal.ui.pages.LoginPage;
import portal.utils.StringUtils;
import portal.utils.TestContext;
import portal.utils.UserUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.Cookies;

import java.util.HashMap;
import java.util.Map;

import static portal.ui.pages.LoginPage.getLoginPage;

public class LoginSteps {

    private final LoginPage loginPage;
    private final Users users;
    private final UserQueriesExecutor userQueriesExecutor;
    private final TestContext testContext;
    private final LoginQueriesExecutor loginQueriesExecutor;
    private final SettingsService settingsService;
    private final ContentDeliveryService contentDeliveryService;
    public final Map<String, String> currentSettingValues;
    public final Map<String, String> currentContentValues;
    public Localization localizationData;
    public final UserService userService;

    public LoginSteps(TestContext testContext) {
        loginPage = getLoginPage();
        users = UserUtils.getUsers();
        this.testContext = testContext;
        loginQueriesExecutor = LoginQueriesExecutor.getLoginQueriesExecutor();
        userService = UserService.getUserService(testContext);
        userQueriesExecutor = UserQueriesExecutor.getUserQueriesExecutor();
        settingsService = SettingsService.getSettingsService(testContext);
        contentDeliveryService = ContentDeliveryService.getContentDeliveryService();
        currentSettingValues = new HashMap<>();
        currentContentValues = new HashMap<>();
        localizationData = new Localization();
    }

    @After(value = "@ResetAccount", order = 2)
    public void resetAccount() {
        User userToReset = (User) testContext.getContext(UserConstants.USER_INFORMATION);
        Cookies cookies = (Cookies) testContext.getContext(CookiesConstant.COOKIES);
        userService.updatedUserDetail(cookies, userToReset.getApplicationKey(), Integer.parseInt(userToReset.getId()));
    }

    @Given("I login as {string} user role")
    public void loginAsUserRole(String userRole) {
        User user = users.getUserGivenRole(userRole);
        loginPage.loginWithCredentials(user.getUsername(), user.getPassword());
    }

    @Given("I login as {string} level user")
    public void loginAsLevelUser(String userLevel) {
        User user = users.getUserForLevel(userLevel);
        loginPage.loginWithCredentials(user.getUsername(), user.getPassword());
        testContext.setContext(UserConstants.USER_INFORMATION, user);
    }

    @Given("I login as {string} user purpose")
    public void loginAsUserPurpose(String userPurpose) {
        testContext.setContext(UserConstants.LOGGED_IN_USER_PURPOSE, userPurpose);
        User user = users.getUserGivenPurpose(userPurpose);
        loginPage.loginWithCredentials(user.getUsername(), user.getPassword());
    }

    @When("I login to page after user detail is updated")
    public void loginAfterUserDetailUpdate() {
        User user = (User) testContext.getContext(UserConstants.USER_INFORMATION);
        loginPage.loginWithCredentials(user.getUsername(), user.getPassword());
    }



    @And("I input {string} and submit the form")
    public void inputValueAndSubmitForm(String fieldType) {
        User user = (User) testContext.getContext(UserConstants.USER_INFORMATION);
        if (fieldType.equalsIgnoreCase("username")) {
            loginPage.inputValueAndSubmitForm(fieldType, user.getUsername());
        } else {
            user.setPassword(user.getPassword() + StringUtils.generateAlphabeticString(5));
            loginPage.inputValueAndSubmitForm(fieldType, user.getPassword());
            if (fieldType.equalsIgnoreCase("password")) {
                testContext.setContext(UserConstants.USER_INFORMATION, user);
            }
        }
    }

    @Then("I verify {string} success page is displayed")
    public void verifyConfirmationPage(String pageType) {
        loginPage.verifyConfirmationPage(pageType);
    }

    @Then("I verify error message displayed for {string}")
    public void loginWithInvalidCredentials(String pageOption) {

    }


}