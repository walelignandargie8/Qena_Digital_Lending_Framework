package portal.ui.elements;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.byTagName;
import static com.codeborne.selenide.Selenide.*;

public class LoginElements {
    public static final SelenideElement TAB_CONTAINER = $x("//div[@role='tablist']");
    public static final SelenideElement PASSWORD_LOGIN_TAB_BUTTON = $("button#simple-tab-0");
    public static final SelenideElement PAC_LOGIN_TAB_BUTTON = $("button#simple-tab-1");
    public static final SelenideElement PAGE_LOGO = $x("//parent::div[not(contains(@class,'makeStyles'))]/img");
    public static final SelenideElement USERNAME_INPUT = $x("//input[@name = 'email']");
    public static final SelenideElement PASSWORD_INPUT = $x("//input[@name = 'password']");
    public static final SelenideElement PAC_INPUT = $(byName("AccessCode"));
    public static final SelenideElement CONFIRM_PASSWORD_INPUT = $(byName("ConfirmationPassword"));
    public static final SelenideElement LOGIN_BUTTON = $x("//button[@type = 'submit']");
    public static final SelenideElement LOGIN_VALIDATION_ERROR_MESSAGE = $("div[role='alert'] div[class='MuiAlert-message']");
    public static final SelenideElement CONTACT_US_BUTTON = $x("//div[contains(@class,'headerClass')]//a");
    public static final SelenideElement FOOTER_TEXT = $x("//p[contains(@class,'makeStyles-footertext')]");
    public static final SelenideElement LOGIN_PAGES_LABEL = $x("//div[contains(@class,'makeStyles-textField')]/p");
    public static final SelenideElement LINK_REQUEST_PAGES_LABEL = $x("//label[contains(@class,'MuiFormLabel-root')]/p");
    public static final SelenideElement LINK_REQUEST_SUCCESS_MESSAGE_TITLE = $("div[class='MuiAlert-message'] div");
    public static final SelenideElement LINK_REQUEST_SUCCESS_MESSAGE = $x("//div[normalize-space(@class='MuiAlert-message')]//div/span");
    public static final SelenideElement LOGIN_BACKGROUND = $x("//div[contains(@class,'makeStyles-gridroot')]");
    public static final SelenideElement KEEP_ME_LOGGED_IN_PLACEHOLDER = $x("//span[contains(@class,'MuiFormControlLabel-label')]/p");
    public static final SelenideElement NEXT_BUTTON = $("button.MuiButton-contained");
    public static final SelenideElement BACK_BUTTON = $x("//a[contains(@href, '/oidc/login?')]");
    public static final SelenideElement CHANGE_PASSWORD_LABEL = $("label[class='MuiFormLabel-root']");
    public static final SelenideElement LINK_PAGE_BACK_BUTTON = $x("//a");
    public static final SelenideElement LOGGED_OUT_MESSAGE = $("body h1");
    public static final SelenideElement LOGGED_OUT_PAGE_LOGIN_BUTTON = $(byTagName("a"));
    public static final SelenideElement LANGUAGE_SELECTOR_DIV = $x("//div[contains(@class,'makeStyles-element')]" +
            "//div[contains(@class,'makeStyles-languageLabelText')]");
    public static final SelenideElement LANGUAGE_LIST_DIV = $x("//ul[@role='menu']");
    public static final ElementsCollection AVAILABLE_LANGUAGE_LIST = $$x("//ul[@role='menu']//li" +
            "[not(contains(@class,'Mui-selected'))]");

    public static SelenideElement loginLinks(String linkType) {
        return $x("//a[contains(@href,'" + linkType + "')]");
    }

    public static SelenideElement confirmationPage(String pageType) {
        return $("div[id='" + pageType + "'] div[class='MuiAlert-message']");
    }

    public static SelenideElement getLocaleValue(String locale) {
        return $x("//p[contains(text(),'" + locale + "')]/ancestor::li");
    }
}