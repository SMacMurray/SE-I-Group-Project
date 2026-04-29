package stay_and_shop_system;

import stay_and_shop_system.occupancy.database.ReservationRepository;
import stay_and_shop_system.occupancy.database.RoomRepository;
import stay_and_shop_system.spring_boot.SpringBootApp;
import stay_and_shop_system.user.UserRepository;

import java.awt.EventQueue;

import javax.swing.JFrame;
import com.formdev.flatlaf.FlatLightLaf;



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

        SpringBootApp.main(args);

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

}