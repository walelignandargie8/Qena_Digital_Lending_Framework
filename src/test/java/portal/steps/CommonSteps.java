package portal.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import portal.ui.pages.HomePage;
import portal.ui.pages.LoginPage;

import static portal.ui.pages.HomePage.getHomePage;
import static portal.ui.pages.LoginPage.getLoginPage;


public class CommonSteps {

    private final HomePage homePage;
    private final LoginPage loginPage;


    public CommonSteps() {
        homePage = getHomePage();
        loginPage = getLoginPage();

    }

    @When("I navigate to {string} page")
    public void navigateToPage(String pageName) {
        homePage.navigateToPage(pageName);
    }

    @Then("I verify {string} page")
    public void verifyPage(String pageName) {
        homePage.verifyPage(pageName);
    }
}