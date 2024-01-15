package portal.steps;

import portal.ui.pages.AccessManagementPage;

import static portal.ui.pages.AccessManagementPage.getAccessManagementPage;

public class UserGroupSteps {

    private final AccessManagementPage accessManagementPage;


    public UserGroupSteps() {
        accessManagementPage = getAccessManagementPage();
    }
}