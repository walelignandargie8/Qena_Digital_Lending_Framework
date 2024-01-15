package portal.ui.elements;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class AccessManagementElements {

    public static final SelenideElement ADD_GROUP_BUTTON = $x("//button[contains(@class, 'MuiButton-containedSizeMedium')]");
    public static final SelenideElement ACCESS_MANAGEMENT_TAB = $x("//div[contains(@class, 'MuiTabs-root')]");
    public static final SelenideElement USER_GROUP_TABLE = $x("//table[contains(@class, 'MuiTable-root')]");
    public static SelenideElement getAccessManagementTab(String tabName){
        return $x("//div[@role = 'tablist']/button[text()='"+tabName+"']");
    }


}