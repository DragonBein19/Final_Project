package lt.viko.eif.nSalunov.Interface;

import java.util.Scanner;

public class Interface {

    public void MainMenu() {
        System.out.println(
                "|1\tBudget" +
                "\n|2\tGoals" +
                "\n|3\tLog out" +
                "\n|6\tExit"
        );
    }

    public int AutorizationInterface(Scanner input) {
        System.out.println(
                "|1\tSign in" +
                "\n|2\tRegister" +
                "\n|6\tExit"
        );
        return input.nextInt();
    }
}
