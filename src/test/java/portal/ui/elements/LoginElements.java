package portal.ui.elements;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.*;

public class LoginElements {
    public static final SelenideElement TAB_CONTAINER = $x("//div[@role='tablist']");
    public static final SelenideElement PASSWORD_LOGIN_TAB_BUTTON = $("button#simple-tab-0");
    public static final SelenideElement USERNAME_INPUT = $x("//input[@name = 'email']");
    public static final SelenideElement PASSWORD_INPUT = $x("//input[@name = 'password']");
    public static final SelenideElement LOGIN_BUTTON = $x("//button[@type = 'submit']");

}