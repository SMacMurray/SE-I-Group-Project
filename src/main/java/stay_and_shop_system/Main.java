package stay_and_shop_system;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import stay_and_shop_system.occupancy.Room;
import stay_and_shop_system.occupancy.database.ReservationRepository;
import stay_and_shop_system.occupancy.database.RoomRepository;
import stay_and_shop_system.user.UserRepository;
import stay_and_shop_system.user.User;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JFrame;
import com.formdev.flatlaf.FlatLightLaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main extends JFrame {
    //static Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int WINDOW_W = 1000;
    public static int WINDOW_H = 800;
    public static String APP_TITLE = "The Ocean's Waters Hotel: the illustrious hotel/luxury shopping experience!";
    public static String HOME_TEXT = "Store Logo Here";
    // Use this to indicate what user is currently logged in, or null for logged out.


    public static void main(String[] args) {
        FlatLightLaf.setup();
        SetupUI.setUpJOptionPaneDesign();
        ReservationRepository.createTable();
        UserRepository.initAccountTable();
        RoomRepository.createTable();

        SpringApplication.run(Main.class, args);

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    HomePage2 frame = new HomePage2();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @GetMapping("/")
    public String home (Model model){
        return "home";
    }
}