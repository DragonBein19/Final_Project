package lt.viko.eif.nSalunov;

import lt.viko.eif.nSalunov.DB.MyJBDC;
import lt.viko.eif.nSalunov.Interface.Interface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Autorization {
    private String Login;
    private String Password;
    private Scanner Input = new Scanner(System.in);
    private MyJBDC myJBDC = new MyJBDC();
    private Interface anInterface = new Interface();
    private UserRegistration userRegistration = new UserRegistration();

    public void DataEntry() {
        Scanner userInput = new Scanner(System.in);
        int userChoice;

        do{
            userChoice = anInterface.AutorizationInterface(userInput);
            switch (userChoice)
            {
                case 1:
                    myJBDC.openConnection();
                    System.out.println("Login: ");
                    Login = Input.nextLine();

                    System.out.println("Password: ");
                    Password = Input.nextLine();

                    ComparisonWithDB();
                    myJBDC.openConnection();
                    System.exit(7);
                    break;

                case 2:
                    myJBDC.openConnection();
                    userRegistration.Registration(myJBDC);
                    myJBDC.openConnection();
                    break;
                case 6:
                    System.exit(7);
                    break;
            }
        } while (userChoice != 6);
    }

    private void ComparisonWithDB() {
        try {
            Connection connection = myJBDC.GetConnection();

            String SQL = "SELECT * FROM account WHERE login = ? AND password = ?";
            PreparedStatement stmt = connection.prepareStatement(SQL);
            stmt.setString(1, Login);
            stmt.setString(2, Password);

            ResultSet result = stmt.executeQuery();

            if(result.next()) {
                System.out.println("Access granted!!!");
            } else {
                System.out.println("Access denied!!!");
            }

            result.close();
            stmt.close();
            connection.close();

            ShowData();
        } catch (SQLException e) {
            System.out.println("Error. something gone wrong. Error code: " + e.getLocalizedMessage());
        }
    }

    private void ShowData() {
        System.out.println("Login: " + Login + "\nPassword: " + Password);
    }

    public String getLogin() { return Login; }
    public void setLogin(String login) { Login = login; }

    public String getPassword() { return Password; }
    public void setPassword(String password) { Password = password; }
}
