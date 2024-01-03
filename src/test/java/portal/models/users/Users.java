package portal.models.users;

import lombok.Getter;

import java.util.List;

@Getter
public class Users {

    public static final String ROOT_LEVEL = "1";
    private List<User> users;

    /**
     * Method to get the user role given the role name
     *
     * @param role Role Name
     * @return the User
     */
    public User getUserGivenRole(String role) {
        return users.stream()
                .filter(user -> user.getRole().equalsIgnoreCase(role))
                .findFirst().orElse(null);
    }

    /**
     * Method to find user given the ID
     *
     * @param id User ID
     * @return the User
     */
    public User getUserGivenId(String id) {
        return users.stream()
                .filter(user -> user.getId().toLowerCase().equals(id))
                .findFirst().orElse(null);
    }

    /**
     * Method to find user given the Username
     *
     * @param username Username
     * @return the User
     */
    public User getUserGivenUserName(String username) {
        return users.stream()
                .filter(user -> user.getUsername().toLowerCase().equals(username))
                .findFirst().orElse(null);
    }

    /**
     * Method to find user that is not the given one
     *
     * @param userToCompare User object
     * @return the first different User to the given one
     */
    public User getDifferentUser(User userToCompare) {
        return users.stream()
                .filter(user -> !user.equals(userToCompare))
                .findFirst().orElse(null);
    }

    /**
     * Method to find a user based on the hierarchy level
     * by default returns the level 1
     *
     * @param levelOfCreation desired level, lowest or the 1st one.
     * @return The first user for desired level
     */
    public User getUserForLevel(String levelOfCreation) {
        return users.stream()
                .filter(user -> user.getHierarchyLevel() != null)
                .filter(user -> user.getHierarchyLevel().equals(levelOfCreation))
                .findFirst().orElse(null);
    }

    /**
     * Method to get the user role given the purpose
     *
     * @param userPurpose Desired purpose
     * @return The first user for desired purpose
     */
    public User getUserGivenPurpose(String userPurpose) {
        return users.stream()
                .filter(user -> user.getUserPurpose() != null)
                .filter(user -> user.getUserPurpose().equalsIgnoreCase(userPurpose))
                .findFirst().orElse(null);
    }

    /**
     * Method to get user given the hierarchy level
     *
     * @param level required level e.g. root, child, or subchild
     * @return the User object
     */
    public User getUserWithHierarchyLevel(String level) {
        return users.stream()
                .filter(user -> user.getHierarchyLevel() != null)
                .filter(user -> user.getHierarchyLevel().equals(level))
                .findFirst().orElse(null);
    }
}

