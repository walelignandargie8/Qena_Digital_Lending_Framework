package portal.ui.pages;

import portal.ui.elements.LoginElements;

import static com.codeborne.selenide.Condition.visible;

public class AccessManagementPage extends GenericPage<AccessManagementPage> {

    public static AccessManagementPage getAccessManagementPage() {
        return new AccessManagementPage();
    }


    /**
     * Method to verify user is logged in
     */
    public void verifyUserIsLoggedIn() {
        if (LoginElements.LOGGED_OUT_MESSAGE.isDisplayed() && LoginElements.LOGGED_OUT_MESSAGE.text().contains("You are Logged out")) {
            LoginElements.LOGGED_OUT_PAGE_LOGIN_BUTTON.shouldBe(visible.because("Login link is not visible")).click();
        }

    }


}