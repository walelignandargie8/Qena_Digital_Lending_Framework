package portal.steps;

import io.cucumber.java.en.Then;
import portal.ui.pages.HomePage;
import portal.ui.pages.LoginPage;

import static portal.ui.pages.HomePage.getHomePage;
import static portal.ui.pages.LoginPage.getLoginPage;


public class HomeSteps {

    private final HomePage homePage;
    private final LoginPage loginPage;


    public HomeSteps() {
        homePage = getHomePage();
        loginPage = getLoginPage();

    }

    @Then("I verify user is logged in")
    public void verifyUserIsLoggedIn() {
        homePage.verifyUserIsLoggedIn();
    }
}