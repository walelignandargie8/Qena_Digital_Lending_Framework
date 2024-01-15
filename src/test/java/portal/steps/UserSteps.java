package portal.steps;

import portal.ui.pages.AccessManagementPage;

import static portal.ui.pages.AccessManagementPage.getAccessManagementPage;

public class UserSteps {

    private final AccessManagementPage accessManagementPage;


    public UserSteps() {
        accessManagementPage = getAccessManagementPage();
    }
}