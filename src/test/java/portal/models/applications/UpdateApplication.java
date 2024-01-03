package portal.models.applications;

public class UpdateApplication {
    public String name;
    public String value;

    public UpdateApplication(Application application) {
        name = application.getName();
        value = application.getValue();
    }
}
