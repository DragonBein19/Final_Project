package lt.viko.eif.nSalunov.DB;

import java.sql.*;

public class MyJBDC {
    private final String URL = "jdbc:mysql://127.0.0.1:3306/fri_db";
    private final String USER = "root";
    private final String PASSWORD = "root";
    private final String tableAccount = "ACCOUNT";
    private Connection connection;


    public Connection GetConnection(){ return connection; }

    public void openConnection(){
        try{
            if(connection == null || connection.isClosed()){
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.out.println("Something wrong with opening connection method. Error code: " + e.getLocalizedMessage());
        }
    }

    public void closeConnection(){
        try{
            if(connection != null || !connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Something wrong with closing connection method. Error code: " + e.getLocalizedMessage());
        }
    }

    public void test(){
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableAccount);

            while(resultSet.next()){
                System.out.println(resultSet.getString("Login"));
            }
        } catch (SQLException e)
        {
            System.out.println("Something is wrong with the testing method. Error: " + e.getLocalizedMessage());
        }
    }
}
