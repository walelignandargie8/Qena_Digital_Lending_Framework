package portal.ui.elements;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class CommonElements {

    public static SelenideElement getPageLabel(String pageName) {
        return $x("//div[h4[text() = '" + pageName + "']]");
    }

    public static SelenideElement getTabLink(String tabName) {
        return $x("//div[text()='" + tabName + "']");
    }
}