package portal.models.settings;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SettingTable {
    private String name;
    private String description;
    private String dataType;
    private String value;
    private String enumDataType;
    private String validationPattern;

    /***
     * Returns the value that should be displayed on Setting List
     * @return Value on Settings list
     */
    public String getValueOnList() {
        switch (dataType){
            case "Json":
                return "{JSON}";
            default:
                return value;
        }
    }

    /***
     * Returns the dataType that should be displayed on Setting List
     * @return DataType on Settings list
     */
    public String getDataTypeOnList() {
        switch (dataType){
            case "Json":
                return "JSON";
            case "Enum":
                return "Enum";
            default:
                return dataType;
        }
    }
}