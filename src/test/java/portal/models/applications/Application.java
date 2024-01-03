package portal.models.applications;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;

@Getter
@Setter
public class Application {
    private String id;
    private String applicationKey;
    private String name;
    private String value;
    private String hierarchy;
    private String rootApp;
    private String hierarchyLevel;
    private String invalidApplicationKey;
    private String applicationGroup;

    public Application(Application application) {
        id = application.id;
        applicationKey = application.applicationKey;
        name = application.name;
        value = application.value;
        hierarchy = application.hierarchy;
        rootApp = application.rootApp;
        hierarchyLevel = application.hierarchyLevel;
        invalidApplicationKey = application.invalidApplicationKey;
        applicationGroup = application.applicationGroup;
    }

    public Application() {
    }

    public Application(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Creates a list with the hierarchy information
     *
     * @return A list of the hierarchy
     */
    public String[] getHierarchyAsList() {
        return hierarchy.split("/");
    }

    /**
     * Creates a list with the hierarchy information plus the name of the application
     *
     * @return A list of the hierarchy plus the application name
     */
    public String[] getEntireHierarchyAsList() {
        String hierarchyPlusName = hierarchy + "/" + name;
        return hierarchyPlusName.split("/");
    }

    /**
     * Creates a list with the hierarchy information plus root application name
     *
     * @return A complete hierarchy including root
     */
    public String[] getHierarchyAsListIncludingRoot() {
        String hierarchyPlusName = rootApp + "/" + hierarchy;
        return hierarchyPlusName.split("/");
    }

    @Override
    public String toString() {
        return String.format("id: '%s'\n name: '%s' \n value: '%s'\nhierarchy:'%s'\nrootApp:'%s'", id, name, value, hierarchy, rootApp);
    }

    /**
     * Returns the desired information from the app
     *
     * @param applicationInfo desired property from class
     * @return value from desired property
     */
    public String getInfoValue(String applicationInfo) {
        Application app = new Application(this);
        Class obj = app.getClass();
        Field desiredField = null;
        try {
            desiredField = obj.getDeclaredField(applicationInfo);
            desiredField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        try {
            return String.valueOf(desiredField.get(this));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}