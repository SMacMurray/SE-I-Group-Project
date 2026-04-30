package stay_and_shop_system.spring_boot.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import stay_and_shop_system.user.User;
import stay_and_shop_system.user.UserRepository;

@Controller
public class HomeController {
    @GetMapping("/")
    public String landingPage (Model model){
        User user = UserRepository.getSessionAccount();
        model.addAttribute("username", (user == null ? "Not Logged In" : "Logged in as: " + user.getName()));
        return "home";
    }
}
