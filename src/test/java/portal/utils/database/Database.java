package portal.utils.database;

public class Database {
    public static DatabaseConnection getAppDbConnection() {

        ConfigUtility configUtility = ConfigUtility.getConfigUtility();
        return new DatabaseConnection(
                configUtility.getAppHost(),
                configUtility.getPortNumber(),
                configUtility.getAppDBName(),
                configUtility.getUsername(),
                configUtility.getPassword(),
                configUtility.isWindowsAuth()
        );
    }
}
