package lt.viko.eif.nSalunov;

import javax.xml.transform.Result;
import java.sql.*;

public class MyJBDC {
    public static void main(String[] args) {
        try{
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/fri_db", "root", "root"
            );

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ACCOUNT");

            while(resultSet.next()){
                System.out.println(resultSet.getString("Login"));
                System.out.println(resultSet.getString("Password"));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
