package portal.ui.pages;

import portal.ui.elements.HomeElements;

import static com.codeborne.selenide.Condition.visible;

public class HomePage extends GenericPage<HomePage> {

    public static HomePage getHomePage() {
        return new HomePage();
    }


    /**
     * Method to verify user is logged in
     */
    public void verifyUserIsLoggedIn() {
        HomeElements.TAB_CONTAINER.shouldBe(visible.because("Tab container is not visible in the homepage"));
        HomeElements.DASHBOARD_CONTAINER.shouldBe(visible.because("Report dashboard does not displayed"));
        HomeElements.MICHU_LOGO.shouldBe(visible.because("Logo does not visible"));
    }
}