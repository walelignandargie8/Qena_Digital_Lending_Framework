package portal.ui.elements;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class HomeElements {

    public static final SelenideElement TAB_CONTAINER = $x("//div[@class='simplebar-content']");
    public static final SelenideElement MICHU_LOGO = $x("//img[@alt = 'MICHU']");
    public static final SelenideElement DASHBOARD_CONTAINER = $x("//div[@class = 'report']");

}