package portal.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import portal.ui.elements.LoginElements;
import portal.utils.PropertiesReader;
import org.openqa.selenium.Keys;


import java.io.IOException;
import java.nio.file.Path;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static java.nio.channels.FileChannel.open;


public class LoginPage extends GenericPage<LoginPage> {

    private static String PASSWORD_LENGTH= "password length";
    private static String PASSWORD_SPECIAL_CHARACTER= "special character";
    private static String PASSWORD_ALPHANUMERIC= "alphanumeric";
    private static String PASSWORD_TEXT_CASE= "text case";
    private static String PASSWORD_MIN_LENGTH = "minPasswordLength";

    public static LoginPage getLoginPage() {
        return new LoginPage();
    }

    /**
     * Method to navigate to username password login tab
     */
    public void openUsernamePasswordLoginTab() {
        LoginElements.LOGIN_BUTTON.shouldBe(visible.because("Login button is not visible"))
                .shouldBe(enabled.because("Login button is not enabled"));
        if (LoginElements.TAB_CONTAINER.isDisplayed() && LoginElements.TAB_CONTAINER.isEnabled()) {
            LoginElements.PASSWORD_LOGIN_TAB_BUTTON
                    .shouldBe(visible.because("Login tab button is not visible"))
                    .click();
        }
    }

    /**
     * Method to navigate to username password login tab
     */
    public void openPACLoginTab() {
        LoginElements.LOGIN_BUTTON.shouldBe(visible.because("Login button is not visible"))
                .shouldBe(enabled.because("Login button is not enabled"));
        if (LoginElements.TAB_CONTAINER.isDisplayed() && LoginElements.TAB_CONTAINER.isEnabled() &&
                LoginElements.USERNAME_INPUT.isDisplayed()) {
            LoginElements.PAC_LOGIN_TAB_BUTTON
                    .shouldBe(visible.because("Login tab button is not visible"))
                    .click();
        }
    }

    /**
     * Method to login to site
     *
     * @param userName username
     * @param password password
     */
    public void loginWithCredentials(String userName, String password) {
        openUsernamePasswordLoginTab();
        LoginElements.USERNAME_INPUT.shouldBe(enabled.because("The Username input filed is not visible")).val(userName);
        LoginElements.PASSWORD_INPUT.val(password);
        LoginElements.LOGIN_BUTTON.click();

    }

    /**
     * Method to check account lock on login page
     */

    public void accountLockedTest(String userName, String password, int allowedAttempts) {
        openUsernamePasswordLoginTab();
        if (allowedAttempts > 0) {
            for (int maxAttempts = 0; maxAttempts < allowedAttempts; maxAttempts++) {
                LoginElements.USERNAME_INPUT
                        .shouldBe(enabled.because("The Username input filed is not visible"))
                        .sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
                LoginElements.USERNAME_INPUT.val(userName);
                LoginElements.PASSWORD_INPUT.val(password);
                LoginElements.LOGIN_BUTTON.click();
            }

            //Assert validation error message
            LoginElements.LOGIN_VALIDATION_ERROR_MESSAGE.shouldBe(visible.because("Account lock validation error message not visible"));
        }
    }

    /**
     * Method to assert displayed message with expected message
     *
     * @param errorMessage expected message
     */
    public void assertErrorMessage(String errorMessage) {
        LoginElements.LOGIN_VALIDATION_ERROR_MESSAGE.shouldBe(enabled.because("validation error message not visible"));
        LoginElements.LOGIN_VALIDATION_ERROR_MESSAGE
                .shouldHave(Condition.text(errorMessage).because("Validation message displayed is not correct"));
    }



    /**
     * Method to input values and submit form
     *
     * @param dataType  data type to be filled
     * @param dataValue data value
     */
    public void inputValueAndSubmitForm(String dataType, String dataValue) {
        switch (dataType) {
            case "PAC":
                LoginElements.PAC_INPUT.shouldBe(visible.because("PAC input text box is not visible")).val(dataValue);
                LoginElements.LOGIN_BUTTON.click();
                break;
            case "username":
                LoginElements.USERNAME_INPUT.val(dataValue);
                LoginElements.NEXT_BUTTON.click();
                break;
            case "password":
                LoginElements.PASSWORD_INPUT.val(dataValue);
                LoginElements.CONFIRM_PASSWORD_INPUT.val(dataValue);
                LoginElements.NEXT_BUTTON.click();
                break;
        }
    }

    /**
     * Method to verify login routes confirmation page
     *
     * @param pageType page type
     */
    public void verifyConfirmationPage(String pageType) {
        if (pageType.equalsIgnoreCase("Forgot Password")) {
            LoginElements.confirmationPage("forgotPasswordMessageroot").shouldBe(visible.because("Forgot password" +
                    " request success message is not displayed"));
        } else {
            LoginElements.confirmationPage("firstTimeMessageroot").shouldBe(visible.because("First time user request" +
                    " success message is not displayed"));
        }
    }

    /**
     * Method to navigate back to default login page given origin page
     */
    public void navigateBackToLoginPage() {
        LoginElements.BACK_BUTTON.shouldBe(visible.because("Back button is not visible")).click();
        if (LoginElements.LOGGED_OUT_MESSAGE.isDisplayed() && LoginElements.LOGGED_OUT_MESSAGE.text().contains("You are Logged out")) {
            LoginElements.LOGGED_OUT_PAGE_LOGIN_BUTTON.shouldBe(enabled.because("Logout link is not enabled")).click();
        }
        LoginElements.LOGIN_BUTTON.shouldBe(visible.because("Login button is not visible"));
    }

    /**
     * Method to verify components of the link pages
     *
     * @param pageDetail expected link page detail
     */
    public void verifyComponentsOfLinkPages(String pageDetail) {
        if (pageDetail.equalsIgnoreCase("Link Expired")) {
            LoginElements.PASSWORD_INPUT.shouldNotBe(visible.because("Password input is visible"));
            LoginElements.CONFIRM_PASSWORD_INPUT.shouldNotBe(visible.because("Confirm Password input is visible"));
            LoginElements.NEXT_BUTTON.shouldNotBe(visible.because("Next button is visible"));
            LoginElements.BACK_BUTTON.shouldBe(visible.because("Back button is not visible"));
        } else {
            LoginElements.PASSWORD_INPUT.shouldBe(visible.because("Password input is not visible"));
            LoginElements.CONFIRM_PASSWORD_INPUT.shouldBe(visible.because("Confirm Password input is not visible"));
            LoginElements.NEXT_BUTTON.shouldBe(visible.because("Next button is not visible"));
            LoginElements.LINK_PAGE_BACK_BUTTON.shouldBe(visible.because("Back button is not visible"));
        }
    }

    /**
     * Method to verify user is logged in
     */
    public void verifyUserIsLoggedIn() {
        // currently sometimes when an email link is manually extracted and opened the oidc javascript logged out page is displayed
        // on auto redirect after password setup so this click action is added, to be removed once the issue is resolved
        Selenide.sleep(3000);
        if (LoginElements.LOGGED_OUT_MESSAGE.isDisplayed() && LoginElements.LOGGED_OUT_MESSAGE.text().contains("You are Logged out")) {
            LoginElements.LOGGED_OUT_PAGE_LOGIN_BUTTON.shouldBe(visible.because("Login link is not visible")).click();
        }

    }

    /**
     * Method to navigate to Cxs Admin
     */
    public void navigateToCxsAdmin() {
        try {
            open(Path.of(PropertiesReader.getDefaultOrPreferredSetting("baseURL", "baseURL")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}