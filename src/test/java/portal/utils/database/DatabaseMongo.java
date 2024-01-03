package portal.utils.database;

public class DatabaseMongo {
    public static DatabaseMongoConnection getAppDbConnection() {

        ConfigUtilityMongo configUtility = ConfigUtilityMongo.getConfigUtility();
        return new DatabaseMongoConnection(
                configUtility.getAppHost(),
                configUtility.getPortNumber(),
                configUtility.getAppDBName(),
                configUtility.getUsername(),
                configUtility.getPassword()
        );
    }
}
