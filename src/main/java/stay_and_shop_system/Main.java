package stay_and_shop_system;

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

        // This part below is for testing
//        ReservationRepository.dropTable();
        ReservationRepository.createTable();

//        RoomRepository.dropTable();
        RoomRepository.createTable();

        List<Room.BedType> bts = new ArrayList<>();
        bts.add(Room.BedType.Full);
        bts.add(Room.BedType.King);
        RoomRepository.addRoom(new Room(101, 100, 100, 101.01, true, bts, Room.QualityLevel.Executive, Room.RoomSize.Deluxe));
//        System.out.println(RoomRepository.loadRoomOfRoomNumber(101));
        bts.add(Room.BedType.Queen);
        bts.add(Room.BedType.King);
        RoomRepository.addRoom(new Room(200, 13, 46, 203.99, false, bts, Room.QualityLevel.Comfort, Room.RoomSize.Double));
//        System.out.println(RoomRepository.loadRoomOfRoomNumber(200));

        // UserRepository.createEmployeeAccount("joel@gmail.com", "joel@gmail.com", Objects.hash("0"), "0", User.UserType.CLERK.ordinal());

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