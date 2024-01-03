package portal.utils.database;

import portal.constants.LogsConstant;
import portal.utils.ScenarioContext;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class DatabaseConnection {
    private final String host;
    private final int portNumber;
    private final String dbname;
    private final String user;
    private final String password;

    private boolean isWindowsAuth;
    private Connection connection;

    public DatabaseConnection(String host, int portNumber, String dbname, String user, String password, boolean isWindowsAuth) {
        this.host = host;
        this.portNumber = portNumber;
        this.dbname = dbname;
        this.user = user;
        this.password = password;
        this.isWindowsAuth = isWindowsAuth;
    }

    /**
     * Creates the connection with the data base
     *
     * @return true if the connection has been performed correctly
     * @throws SQLException
     */
    public boolean getConnection() throws SQLException {
        boolean connectionSuccess = true;

        if (host.isEmpty() || dbname.isEmpty()) {
            logWarning("Host or dbname values are empty");
            throw new SQLException("Missing database information");
        }
        SQLServerDataSource ds = new SQLServerDataSource();

        if (isWindowsAuth) {
            logInformation("Using Windows Authentication");
            ds.setIntegratedSecurity(true);
        } else {
            logInformation("Using SQL Credentials");
            ds.setUser(user);
            ds.setPassword(password);
        }

        ds.setTrustServerCertificate(true);
        ds.setServerName(host);
        ds.setDatabaseName(dbname);
        ds.setPortNumber(portNumber);
        logInformation("Trying connection to the database with information: \n" +
                "host:" + host +
                " portNumber:" + portNumber +
                " dbName:" + dbname);
        try {
            connection = ds.getConnection();
            logInformation("Successfully connection to the database");
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            connectionSuccess = false;
            logWarning("Error creating the connection to the database : " + e.getMessage());
            e.printStackTrace();
        }
        return connectionSuccess;
    }

    /**
     * Removes the connection
     *
     * @throws SQLException
     */
    public void closeConnection() throws SQLException {
        connection.close();
    }

    /**
     * Executes a query
     * This method creates and closes the db connection
     *
     * @param sqlStatement query
     * @return the result set
     */
    public ResultSet executeQuery(String sqlStatement) {
        ResultSet resultSet = null;
        try {
            getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            resultSet = preparedStatement.executeQuery();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
                return resultSet;
    }

    /**
     * Method to execute a query and return a hash map
     *
     * @param sqlStatement query
     * @return the hashmap with values
     */
    public List<HashMap<String, Object>> executeQueryForHashMap(String sqlStatement) {
        List<HashMap<String, Object>> resultSetAsHashMap = null;
        try {
            getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSetAsHashMap = convertResultSetToList(resultSet);
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
               return resultSetAsHashMap;
    }

    /**
     * Method to convert the result set to hashmap
     *
     * @param resultSet result set
     * @return Hashmap with all the results
     * @throws SQLException
     */
    public List<HashMap<String, Object>> convertResultSetToList(ResultSet resultSet) throws SQLException {
        ResultSetMetaData md = resultSet.getMetaData();
        int columns = md.getColumnCount();
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        while (resultSet.next()) {
            HashMap<String, Object> row = new HashMap<String, Object>(columns);
            for (int index = 1; index <= columns; ++index) {
                row.put(md.getColumnName(index), resultSet.getObject(index));
            }
            list.add(row);
        }

        return list;
    }

    /**
     * Method to execute a query update like INSERT, UPDATE, MERGE, or DELETE statement
     *
     * @param sqlStatement sql query update
     */
    public void executeUpdate(String sqlStatement) {
        try {
            getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /***
     * Logs information using the common logger
     * @param principalMessage principal message
     * @param parameters possible parameters to add
     */
    public void logInformation(String principalMessage, String... parameters) {
        Logger logger = (Logger) ScenarioContext.getContext(LogsConstant.LOGGER);
        logger.info(String.format(principalMessage, parameters));
    }

    /***
     * Logs warning using the common logger
     * @param principalMessage principal message
     * @param parameters possible parameters to add
     */
    public void logWarning(String principalMessage, String... parameters) {
        Logger logger = (Logger) ScenarioContext.getContext(LogsConstant.LOGGER);
        logger.warning(String.format(principalMessage, parameters));
    }
}
