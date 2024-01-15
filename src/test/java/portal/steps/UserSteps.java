package portal.steps;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.Cookies;

import portal.constants.CookiesConstant;
import portal.constants.UserConstants;

import portal.models.users.User;
import portal.ui.pages.AccessManagementPage;

import static portal.ui.pages.AccessManagementPage.getAccessManagementPage;
import static portal.ui.pages.LoginPage.getLoginPage;

public class UserSteps {

    private final AccessManagementPage accessManagementPage;


    public UserSteps() {
        accessManagementPage = getAccessManagementPage();
    }
}