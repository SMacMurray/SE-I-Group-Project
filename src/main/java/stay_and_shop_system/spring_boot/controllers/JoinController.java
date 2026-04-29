package stay_and_shop_system.spring_boot.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import stay_and_shop_system.user.AccountController;

@Controller
public class JoinController {
    @GetMapping("/join")
    public String landingPage () {
        return "join";
    }

    @PostMapping("/join")
    public String createAccount(Model model, String email, String password, String username, String phoneNumber) {
        int res = AccountController.createAccount(email, username, password, phoneNumber);
        System.out.println(res);
        switch (res){
            case 0:
                return "redirect:/";
            case 1:
                model.addAttribute("warning", "Your account was already found in our system. Please Sign In instead.");
                break;
            case 2:
                model.addAttribute("warning", "Please enter a valid phone number (international form).");
                break;
            case 3:
                model.addAttribute("warning", "Please enter a valid email address.");
                break;
            default:
                model.addAttribute("warning", "Warning: an unexpected error occurred.");
                break;
        }
        return "join";
    }
}
