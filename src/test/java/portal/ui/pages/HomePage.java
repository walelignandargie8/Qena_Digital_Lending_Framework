package portal.ui.pages;

import portal.ui.elements.AccessManagementElements;
import portal.ui.elements.CommonElements;
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

    public void navigateToPage(String pageName) {
        switch (pageName) {
            case "Dashboard":
            case "Customers":
            case "Loan Configuration":
            case "Reporting":
            case "Customer Control":
            case "Template":
            case "Collections":
            case "Access Management":
            case "System Setting": {
                CommonElements.getTabLink(pageName).shouldBe(visible
                        .because(pageName + " is not visible")).click();
            }
            break;
        }
    }

    public void verifyPage(String pageName) {
        switch (pageName){
            case "Access Management":{
                CommonElements.getPageLabel(pageName).shouldBe(visible
                        .because("Page Title does not displayed"));
                AccessManagementElements.getAccessManagementTab("User Group").shouldBe(visible
                        .because("User group tab should be visible"));
                AccessManagementElements.ADD_GROUP_BUTTON.shouldBe(visible
                        .because("Add user group button does not displayed"));
                AccessManagementElements.ACCESS_MANAGEMENT_TAB.shouldBe(visible
                        .because("User management tab does not displayed"));

            }
            break;
            case "Customer Control":{

            }
            break;
        }
    }
}