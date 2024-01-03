package portal.models.contents;

import portal.utils.ColorUtils;
import portal.utils.IntUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Content {
    public static final String REGEX_TO_REPLACE_NO_NUMBER_VALUES = "[^-?0-9]+";
    private String id;
    private String name;
    private String description;
    private String type;
    private String contentGroup;
    private String version;
    private String value;

    /**
     * Updates the value according to the content type with a random value, also updates version number
     *
     * @return the new value
     */
    public String setRandomValue() {
        if (type.equals("text")) {
            value = value + IntUtils.getRandomNumber(10000);
        } else {
            value = ColorUtils.getRandomColor();
        }
        int versionNumber = Integer.parseInt(version.replaceAll(REGEX_TO_REPLACE_NO_NUMBER_VALUES, ""));
        version = "v " + (versionNumber + 1);

        return value;
    }
}
