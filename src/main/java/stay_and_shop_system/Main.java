package stay_and_shop_system;

import stay_and_shop_system.occupancy.ReservationService;
import stay_and_shop_system.occupancy.database.ReservationDataTable;
import stay_and_shop_system.user.User;

import java.awt.EventQueue;

import javax.swing.JFrame;
import com.formdev.flatlaf.FlatLightLaf;

public class Main extends JFrame {
	//static Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int WINDOW_W = 1000;
    public static int WINDOW_H = 800;
    public static String APP_TITLE = "The Ocean's Waters Hotel: the illustrious hotel/luxury shopping experience!";
    public static String HOME_TEXT = "Home";
    static ReservationDataTable rdt = new ReservationDataTable();

    public static void initRooms() {

    	LoadCSV.loadRooms(); // Cant do this outside a function.
    	
    }

    public static void main(String[] args) {
        FlatLightLaf.setup();
    	initRooms();
        SetupUI.setUpJOptionPaneDesign();
//        rdt.dropTable();
    	rdt.createTable();

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