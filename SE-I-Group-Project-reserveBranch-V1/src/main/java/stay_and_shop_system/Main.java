package stay_and_shop_system;

import stay_and_shop_system.occupancy.database.ReservationRepository;
import stay_and_shop_system.user.UserRepository;
import stay_and_shop_system.user.User;

import java.awt.EventQueue;

import javax.swing.JFrame;
import com.formdev.flatlaf.FlatLightLaf;

// Welcome to my big ball of mud design for making JFrames.


public class Main extends JFrame {
    //static Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int WINDOW_W = 640;
    public static int WINDOW_H = 400;
    public static String APP_TITLE = "Stop & Shop: the illustrious hotel/luxury shopping experience, for all your stop & shopping needs!";
    public static String HOME_TEXT = "Store Logo Here";
    static ReservationRepository rdt = new ReservationRepository();
    // Use this to indicate what user is currently logged in, or null for logged out.
    public static User SessionAccount = null;

    public static void initRooms() {
        LoadCSV.loadRooms(); // Cant do this outside a function.

    }

    public static void main(String[] args) {
        FlatLightLaf.setup();
        initRooms();
        SetupUI.setUpJOptionPaneDesign();
//        rdt.dropTable();
        rdt.createTable();
        UserRepository.initAccountTable();


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