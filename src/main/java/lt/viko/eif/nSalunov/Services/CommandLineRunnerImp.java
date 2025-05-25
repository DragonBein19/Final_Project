package lt.viko.eif.nSalunov.Services;

import lt.viko.eif.nSalunov.DB.MyJBDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerImp implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Test");
    }
}
