package lt.viko.eif.nSalunov;

import lt.viko.eif.nSalunov.Interface.Interface;

import java.io.InputStream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main{

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}

//public class Main {
//    private static Autorization autorization = new Autorization();
//
//    public static void main(String[] args) {
//
//        //DBTest();
//
//        HTMLTest();
//    }
//
//    private static void DBTest() {
//        try {
//            autorization.DataEntry();
//        } catch (Exception e) {
//            System.out.println("Main class has mistake. Error code: " + e.getLocalizedMessage());
//        }
//    }
//
//    private static void HTMLTest() {
//        JettyServer jettyServer = new JettyServer();
//
//        try {
//            JettyServer.Start();
//        } catch (Exception e) {
//            System.out.println("Main class has mistake. Error code: " + e.getLocalizedMessage());
//        }
//    }
//}