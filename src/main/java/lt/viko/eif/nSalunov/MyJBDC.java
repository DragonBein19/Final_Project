package lt.viko.eif.nSalunov;

import java.sql.*;

public class MyJBDC {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/fri_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static Connection connection;

    public static void main(String[] args) {
        openConnection();
        test();
        closeConnection();
//        try{
//            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/fri_db", "root", "root");
//
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM ACCOUNT");
//
//            while(resultSet.next()){
//                System.out.println(resultSet.getString("Login"));
//                System.out.println(resultSet.getString("Password"));
//            }
//        } catch (SQLException e)
//        {
//            e.printStackTrace();
//        }
    }

    public static Connection GetConnection(){ return connection; }

    public static void openConnection(){
        try{
            if(connection == null || connection.isClosed()){
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.out.println("Something wrong with opening connection method. Error code: " + e.getLocalizedMessage());
        }
    }

    public static void closeConnection(){
        try{
            if(connection != null || !connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Something wrong with closing connection method. Error code: " + e.getLocalizedMessage());
        }
    }

    public static void test(){
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ACCOUNT");

            while(resultSet.next()){
                System.out.println(resultSet.getString("Login"));
            }
        } catch (SQLException e)
        {
            System.out.println("Something is wrong with the testing method. Error: " + e.getLocalizedMessage());
        }
    }
}
