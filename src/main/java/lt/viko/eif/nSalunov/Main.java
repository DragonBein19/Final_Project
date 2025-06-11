package lt.viko.eif.nSalunov;

/*import lt.viko.eif.nSalunov.DB.MyJDBC;
import lt.viko.eif.nSalunov.DB.TablesCopyInteract.UsersTable;*/
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*import java.awt.desktop.SystemEventListener;
 import java.lang.reflect.Executable;*/


@SpringBootApplication
public class Main{
   /* private static MyJDBC myJDBC;
    private static UsersTable usersTable = new UsersTable();*/

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        /*myJDBC = new MyJDBC();

        try {
            usersTable.CopyUsersTable(myJDBC);
            usersTable.TestMethod();
        } catch (Exception e) {
            System.out.println("Error: " + e.getLocalizedMessage());
        }*/
    }

}