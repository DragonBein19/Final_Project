package lt.viko.eif.nSalunov.Services;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerImp implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Test");
    }
}
