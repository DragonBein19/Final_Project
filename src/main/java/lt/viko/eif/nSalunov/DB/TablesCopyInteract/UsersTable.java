package lt.viko.eif.nSalunov.DB.TablesCopyInteract;

import lt.viko.eif.nSalunov.DB.MyJDBC;
import lt.viko.eif.nSalunov.DB.model.Users;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The {@code UsersTable} class is responsible for retrieving all records
 * from the {@code users} table in the database and storing them in memory
 * as a list of {@link Users} objects.
 * <p>
 * It provides a method to copy data from the database and another to test
 * the data by printing it to the console.
 */
public class UsersTable {
    /**
     * SQL query used to select all records from the {@code users} table.
     */
    private String CopyCommand = "SELECT * FROM users";

    /**
     * A list that holds {@link Users} objects representing rows from the database table.
     */
    private List<Users>  users = new ArrayList<>();

    /**
     * Connects to the database using the provided {@link MyJDBC} object,
     * executes a query to fetch all rows from the {@code users} table,
     * and fills the {@code users} list with the retrieved data.
     *
     * @param myJDBC an instance of {@link MyJDBC} used to open and close the database connection
     * @throws SQLException if a database access error occurs
     */
    public void CopyUsersTable(MyJDBC myJDBC) throws SQLException {
        myJDBC.openConnection();

        try (Connection connection = myJDBC.GetConnection();
             Statement stmt = connection.createStatement();
             ResultSet result = stmt.executeQuery(CopyCommand)) {

            while (result.next()) {
                int id = result.getInt("ID");
                String userName = result.getString("UserName");
                String name = result.getString("Name");
                String surname = result.getString("Surname");
                String email = result.getString("Email");
                String phone = result.getString("Phone");
                String password = result.getString("Password");
                Date registrationDate = result.getDate("Registration_Date");
                Boolean isAdmin = result.getBoolean("IsAdmin");

                users.add(new Users(id, userName, name, surname, email, phone, password, registrationDate, isAdmin));
            }
            System.out.println("Class UsersTable.\nMethod CopyUsersTable.\nAll work");
            myJDBC.closeConnection();
        } catch (SQLException e) {
            System.out.println("Error in class UsersTable.\nMethod:\tCopyUsersTable.\nError description: " + e.getLocalizedMessage());
            myJDBC.closeConnection();
        }
    }

    /**
     * A test method that iterates over the {@code users} list
     * and prints each user to the console.
     */
    public void TestMethod()
    {
        try{
            for(Users user : users) {
                System.out.println();
            }
            System.out.println("Class UsersTable.\nMethod TestMethod.\nAll work");
        } catch (Exception e) {
            System.out.println("Error in class UsersTable.\nMethod:\tTestMethod.\nError description: " + e.getLocalizedMessage());
        }
    }
}
