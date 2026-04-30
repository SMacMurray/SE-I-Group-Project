package stay_and_shop_system.spring_boot.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String signIn(Model model, String email, String password) {
        int res = AccountController.login(email, password);
        switch (res){
            case 0:
                return "redirect:/";
            case 1:
                model.addAttribute("warning", "Your login did not match our records");
                break;
            case 3:
                model.addAttribute("warning", "Enter a valid email address");
                break;
            default:
                model.addAttribute("warning", "Warning: an expected error occurred");
                break;
        }
        return "login";
    }


}
