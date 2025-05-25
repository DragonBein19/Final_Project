package lt.viko.eif.nSalunov.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @GetMapping("/Authorization")
    public String GetAutorizationPage() {
        return "Authorization";
    }

    @PostMapping("/Authorization")
    public String PostAutorizationPage(@RequestParam String username, @RequestParam String password) {
        return "Hi " + username + "!\nYour password: " + password + ".";
    }
}
