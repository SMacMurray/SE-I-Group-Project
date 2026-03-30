import java.awt.*;
import java.awt.EventQueue;

import javax.swing.JFrame;

// Welcome to my big ball of mud design for making JFrames.

public class Main extends JFrame {
	//static Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int WINDOW_W = 640;
    static int WINDOW_H = 400;
    static String APP_TITLE = "Stop & Shop: the illustrious hotel/luxury shopping experience, for all your stop & shopping needs!";
    static String HOME_TEXT = "Store Logo Here";
    static ReservationDataTable rdt = new ReservationDataTable();
 // Use this to indicate what user is currently logged in, or null for logged out.
    static User SessionAccount = null;
    
    public static void initRooms() {
    	LoadCSV.loadRooms(); // Cant do this outside a function.
    	
    }

    public static void main(String[] args) {
    	initRooms();
        rdt.loadReservations();
    	
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    HomePage frame = new HomePage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}