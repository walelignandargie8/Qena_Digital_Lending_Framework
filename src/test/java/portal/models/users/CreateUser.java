package portal.models.users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUser {
    public String userName;
    public String password;
    public String firstName;
    public String lastName;
    public String emailAddress;
    public boolean isActive;
    public String applicationId;
    private String preferredLanguage;
    private String preferredName;

    public CreateUser(User userBase) {
        this.userName = userBase.getUsername();
        this.password = userBase.getPassword();
        this.firstName = userBase.getFirstName();
        this.lastName = userBase.getLastName();
        this.emailAddress = userBase.getEmail();
        this.applicationId = userBase.getApplicationKey();
        this.preferredLanguage = userBase.getPreferredLanguage();
        this.preferredName = userBase.getPreferredName();
    }
}
