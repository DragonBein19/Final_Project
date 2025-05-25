package lt.viko.eif.nSalunov.DB;

import java.sql.*;

/**
 * JDBC - Java DataBase Connectivity
 * MyJDBC is a simple helper class to manage a JDBC connection to a MySQL database.
 * It provides methods to open and close the connection, as well as retrieve the current connection instance.
 */
public class MyJDBC {
    private final String URL = "jdbc:mysql://127.0.0.1:3306/fri_db";
    private final String USER = "root";
    private final String PASSWORD = "root";
    private Connection connection;

    /**
     * Returns the current JDBC connection instance.
     *
     * @return the current {@link Connection} object
     */
    public Connection GetConnection()
    { return connection; }

    /**
     * Opens a connection to the database if it is not already open.
     * If the connection is null or closed, a new connection is established using the provided credentials.
     */
    public void openConnection(){
        try{
            if(connection == null || connection.isClosed()){
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.out.println("Something wrong with opening connection method. Error code: " + e.getLocalizedMessage());
        }
    }

    /**
     * Closes the current database connection if it is open.
     */
    public void closeConnection(){
        try{
            if(connection != null && !connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong in the closeConnection method. Error: " + e.getLocalizedMessage());
        }
    }
}
