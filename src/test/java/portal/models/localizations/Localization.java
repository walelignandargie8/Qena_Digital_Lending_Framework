package portal.models.localizations;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
/**
 * Model class used to map a localization detail from external file
 */
public class Localization {

    private String pageName;
    private List<String> contentGroupsList;
    private List<String> settingGroupsList;
    private List<Map<String, String>> contentsList;
    private List<Map<String, String>> settingsList;

    public Localization() {

    }

    public Localization(Localization localization) {
        pageName = localization.pageName;
        contentGroupsList = localization.contentGroupsList;
        settingGroupsList = localization.settingGroupsList;
        contentsList = localization.contentsList;
        settingsList = localization.settingsList;
    }
}