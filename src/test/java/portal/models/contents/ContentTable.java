package portal.models.contents;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContentTable {
    private String name;
    private String description;
    private String dataType;
    private String value;
    private String valueToUpdate;
    private String updatedDescription;
    private String emailSubject;
    private String updatedEmailSubject;
    private String editorMode;
    private String updatedName;
    private String defaultCategory;
    private String updatedCategory;

    /***
     * Returns the value that should be displayed on Content List
     * @return Value on Content list
     */
    public String getValueOnList() {
        switch (dataType) {
            case "Email":
                return "Email";
            case "HTML":
                return "<HTML>";
            case "Advanced HTML":
                return "<Advanced HTML>";
            case "Stylesheet":
                return "Stylesheet";
            case "Theme":
                return "Theme";
            default:
                return value;
        }
    }

    public String getUpdatedValueOnList() {
        switch (dataType) {
            case "Email":
                return "Email";
            case "HTML":
                return "<HTML>";
            case "Advanced HTML":
                return "<Advanced HTML>";
            case "Stylesheet":
                return "Stylesheet";
            case "Theme":
                return "Theme";
            default:
                return valueToUpdate;
        }
    }

    /**
     * Method to check if the content is an Asset
     *
     * @return true if content type is image, video or document.
     */
    public boolean isAsset() {
        return dataType.equalsIgnoreCase("Image") ||
                dataType.equalsIgnoreCase("Video") ||
                dataType.equalsIgnoreCase("Document") ||
                dataType.equalsIgnoreCase("ECard") ||
                dataType.equalsIgnoreCase("Badge");
    }

    /**
     * Method to get content data type
     * @return dataType
     */
    public String getContentDataType() {
        switch (dataType) {
            case "ECard":
            case "Badge":
                return "Recognition Image Asset";
            default:
                return dataType;
        }
    }
}