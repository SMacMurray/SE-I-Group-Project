package stay_and_shop_system.spring_boot.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import stay_and_shop_system.user.AccountController;
import stay_and_shop_system.user.Guest;
import stay_and_shop_system.user.User;
import stay_and_shop_system.user.UserRepository;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String landingPage () {
        return "login";
    }

    //@PostMapping("/login")
    public String createAccount(String email, String password, String username, String phoneNumber) {
        int res = AccountController.createAccount(email, username, password, phoneNumber);
        if (res == 0) return "redirect:/";
        else return "login";
    }

    @PostMapping("/login")
    public String signIn(String email, String password) {
        int res = AccountController.login(email, password);
        if (res == 0) return "redirect:/";
        else return "login";
    }

}
