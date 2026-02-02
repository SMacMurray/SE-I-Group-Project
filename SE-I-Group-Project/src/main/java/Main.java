import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;

// Welcome to my big ball of mud design for making JFrames.

public class Main extends JFrame {
    static int WINDOW_W = 640;
    static int WINDOW_H = 400;
    static String APP_TITLE = "Stop & Shop: the illustrious hotel/luxury shopping experience, for all your stop & shopping needs!";
    static String HOME_TEXT = "Store Logo Here";

    public static void main(String[] args) {
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