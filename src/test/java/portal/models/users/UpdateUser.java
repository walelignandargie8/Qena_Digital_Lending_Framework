package portal.models.users;


public class UpdateUser {
    public String firstName;
    public String lastName;
    public String emailAddress;
    public String id;
    public Boolean isActive = true;

    public UpdateUser(User user) {
        firstName = user.getFirstName();
        lastName = user.getLastName();
        emailAddress = user.getEmail();
        id = user.getId();
    }
}
