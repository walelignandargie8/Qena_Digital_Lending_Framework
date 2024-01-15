package portal.ui.pages;

import portal.ui.elements.LoginElements;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;

public class LoginPage extends GenericPage<LoginPage> {


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


}