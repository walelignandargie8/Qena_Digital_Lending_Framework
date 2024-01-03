package portal.models.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String id;
    private String userPurpose;
    private String role;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String applicationKey;
    private String applicationHierarchyPath;
    private String applicationName;
    private String hierarchyLevel;
    private String expiration;
    private String invalidApplicationKey;
    private String invalidUserId;
    private String preferredLanguage;
    private String preferredName;

    public User(User user) {
        id = user.id;
        role = user.role;
        username = user.username;
        password = user.password;
        firstName = user.firstName;
        lastName = user.lastName;
        email = user.email;
        applicationKey = user.applicationKey;
        applicationHierarchyPath = user.applicationHierarchyPath;
        applicationName = user.applicationName;
        hierarchyLevel = user.hierarchyLevel;
        expiration = user.expiration;
        invalidApplicationKey = user.invalidApplicationKey;
        invalidUserId = user.invalidUserId;
        preferredName = user.preferredName;
        preferredLanguage = user.preferredLanguage;
    }

    public User() {
    }

    /***
     *Compares the id, login, firstName, lastName, email of this user with the given one
     * @param userToReview User to compare with
     * @return True if the users matches
     */
    public Boolean compareWithUserOnlyImportantInformation(User userToReview) {
        return id.equals(userToReview.id)
                && username.equals(userToReview.username)
                && firstName.equals(userToReview.firstName)
                && lastName.equals(userToReview.lastName)
                && email.equals(userToReview.email);
    }
}