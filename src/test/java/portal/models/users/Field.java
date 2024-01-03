package portal.models.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Field {
    public List<String> userName;
    public List<String> firstName;
    public List<String> lastName;
    public List<String> emailAddress;
    public List<String> password;

    /**
     * Verifies must not be empty error message on all error fields
     * @return True if there is must not be empty error on all error fields
     */
    public Boolean mustNotBeEmptyIsOnAllFields() {
        String notEmptyBaseMessage = "'%s' must not be empty.";
        return userName.contains(String.format(notEmptyBaseMessage, "User Name"))
                && firstName.contains(String.format(notEmptyBaseMessage, "First Name"))
                && lastName.contains(String.format(notEmptyBaseMessage, "Last Name"))
                && emailAddress.contains(String.format(notEmptyBaseMessage, "Email Address"))
                && password.contains(String.format(notEmptyBaseMessage, "Password"));
    }
}
