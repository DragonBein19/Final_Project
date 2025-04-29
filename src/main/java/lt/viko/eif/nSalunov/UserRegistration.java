package lt.viko.eif.nSalunov;

import lt.viko.eif.nSalunov.DB.MyJBDC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UserRegistration {
    private String Login;
    private String Password;
    private Scanner Input = new Scanner(System.in);

    public void Registration(MyJBDC myJBDC) {

        System.out.print("New login: ");
        Login = Input.nextLine();

        System.out.print("\nPassword: ");
        Password = Input.nextLine();

        try {
            Connection connection = myJBDC.GetConnection();

            String SQL = "INSERT INTO `fri_db`.`account` (`Login`, `Password`) VALUE(?,?)";
            PreparedStatement stmt = connection.prepareStatement(SQL);
            stmt.setString(1, Login);
            stmt.setString(2, Password);

            int rowsInstead = stmt.executeUpdate();

            if(rowsInstead > 0) {
                System.out.println("New user success apply");
            }

            stmt.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Something gone wrong with registration. Error code: " + e.getLocalizedMessage());
        }
    }
}
