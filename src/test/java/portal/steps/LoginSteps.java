package portal.steps;


import portal.models.users.User;
import portal.models.users.Users;
import portal.ui.pages.LoginPage;
import portal.utils.TestContext;
import portal.utils.UserUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;


import static portal.ui.pages.LoginPage.getLoginPage;

public class LoginSteps {

    private final LoginPage loginPage;
    private final Users users;

    public LoginSteps(TestContext testContext) {
        loginPage = getLoginPage();
        users = UserUtils.getUsers();
    }


    @Given("I login as {string} user role")
    public void loginAsUserRole(String userRole) {
        User user = users.getUserGivenRole(userRole);
        loginPage.loginWithCredentials(user.getUsername(), user.getPassword());
    }

}