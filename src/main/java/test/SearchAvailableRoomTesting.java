package test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stay_and_shop_system.occupancy.ReservationController;
import stay_and_shop_system.occupancy.Room;
import stay_and_shop_system.occupancy.RoomCriteria;
import stay_and_shop_system.occupancy.SearchController;
import stay_and_shop_system.occupancy.database.ReservationRepository;
import stay_and_shop_system.occupancy.database.RoomRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import stay_and_shop_system.occupancy.Room.BedType;
import stay_and_shop_system.user.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

public class SearchAvailableRoomTesting {
    SearchController sc = new SearchController();
    ReservationController rc = new ReservationController();
    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
    static Calendar todayDate;

    int guestNum = 1;
    String email = "jollyJill@gmail.com";
    String name = "Jolly Jill";
    String password = "jollyGillyDons#love";
    String phoneNumber = "+1 909-909-9999";
    String creditCardNumber = "4242 4242 4242 4242";
    String billingAddr = "1047 Treee Freee drive";
    String ccv = "444";
    static Calendar expDate = Calendar.getInstance();
    static SimpleDateFormat expFormatter = new SimpleDateFormat("MM/yyyy");

    @BeforeAll
    static void init() {
        RoomRepository.dropTable();
        RoomRepository.createTable();

        RoomRepository.addRoom(new Room(100, 5, 5, 10.05, true,
                new ArrayList<>(List.of(BedType.Full)),
                Room.QualityLevel.Comfort, Room.RoomSize.Double));
        RoomRepository.addRoom(new Room(200, 10, 4, 20.05, false,
                new ArrayList<>(List.of(BedType.Full, BedType.King)),
                Room.QualityLevel.Comfort, Room.RoomSize.Double));
        RoomRepository.addRoom(new Room(300, 10, 4, 30.05, false,
                new ArrayList<>(List.of(BedType.Twin, BedType.Queen)),
                Room.QualityLevel.Comfort, Room.RoomSize.Double));
        RoomRepository.addRoom(new Room(230, 2, 4, 1.05, true,
                new ArrayList<>(List.of(BedType.Queen)),
                Room.QualityLevel.Comfort, Room.RoomSize.Deluxe));
        RoomRepository.addRoom(new Room(199, 1, 2, 5.95, true,
                new ArrayList<>(List.of(BedType.Queen)),
                Room.QualityLevel.Executive, Room.RoomSize.Deluxe));
        RoomRepository.addRoom(new Room(145, 30, 15, 150.95, false,
                new ArrayList<>(List.of(BedType.King)),
                Room.QualityLevel.Executive, Room.RoomSize.Deluxe));
        RoomRepository.addRoom(new Room(113, 2, 1, 10.49, true,
                new ArrayList<>(List.of(BedType.Full)),
                Room.QualityLevel.Executive, Room.RoomSize.Deluxe));
        RoomRepository.addRoom(new Room(299, 10, 10, 5.99, true,
                new ArrayList<>(List.of(BedType.Full)),
                Room.QualityLevel.Executive, Room.RoomSize.Deluxe));
        RoomRepository.addRoom(new Room(114, 13, 13, 3.00, false,
                new ArrayList<>(List.of(BedType.Full)),
                Room.QualityLevel.Economy, Room.RoomSize.Family));
        RoomRepository.addRoom(new Room(301, 35, 30, 299.99, false,
                new ArrayList<>(List.of(BedType.King, BedType.Queen)),
                Room.QualityLevel.Economy, Room.RoomSize.Deluxe));


        todayDate = Calendar.getInstance();
        try {
            // Getting rid of the minutes and seconds in today's date.
            todayDate.setTime(formatter.parse(formatter.format(Calendar.getInstance().getTime())));
        }
        catch (Exception e) {
            throw new RuntimeException("Could not parse today's date.");
        }
        try {
            expDate.setTime(expFormatter.parse("11/2027"));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

    }
    @BeforeEach
    void resetTables() {
        ReservationRepository.dropTable();
        ReservationRepository.createTable();
    }
    @Test
    void nullOrEmptyInput() {
//        sc.searchAvailableRooms(new RoomCriteria(new int[] {0, 10}, new int[] {0, 10}, new ArrayList<Boolean>(List.of()),
//                new ArrayList<Integer>(List.of()), new ArrayList<BedType>(List.of()), new ArrayList<Room.RoomSize>(List.of()),
//                new double[] {0, 100.99}, new Calendar[] {todayDate, todayDate}));
        assertThrows(IllegalArgumentException.class, () -> {
            sc.searchAvailableRooms(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sc.searchAvailableRooms(new RoomCriteria(null, new int[] {0, 10}, new ArrayList<Boolean>(List.of()),
                    new ArrayList<Integer>(List.of()), new ArrayList<BedType>(List.of()), new ArrayList<Room.RoomSize>(List.of()),
                    new double[] {0, 100.99}, new Calendar[] {todayDate, todayDate}));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sc.searchAvailableRooms(new RoomCriteria(new int[] {10}, new int[] {0, 10}, new ArrayList<Boolean>(List.of()),
                    new ArrayList<Integer>(List.of()), new ArrayList<BedType>(List.of()), new ArrayList<Room.RoomSize>(List.of()),
                    new double[] {0, 100.99}, new Calendar[] {todayDate, todayDate}));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sc.searchAvailableRooms(new RoomCriteria(new int[] {0, 10}, null, new ArrayList<Boolean>(List.of()),
                    new ArrayList<Integer>(List.of()), new ArrayList<BedType>(List.of()), new ArrayList<Room.RoomSize>(List.of()),
                    new double[] {0, 100.99}, new Calendar[] {todayDate, todayDate}));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sc.searchAvailableRooms(new RoomCriteria(new int[] {0, 10}, new int[] {10}, new ArrayList<Boolean>(List.of()),
                    new ArrayList<Integer>(List.of()), new ArrayList<BedType>(List.of()), new ArrayList<Room.RoomSize>(List.of()),
                    new double[] {0, 100.99}, new Calendar[] {todayDate, todayDate}));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sc.searchAvailableRooms(new RoomCriteria(new int[] {0, 10}, new int[] {0, 10}, null,
                    new ArrayList<Integer>(List.of()), new ArrayList<BedType>(List.of()), new ArrayList<Room.RoomSize>(List.of()),
                    new double[] {0, 100.99}, new Calendar[] {todayDate, todayDate}));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sc.searchAvailableRooms(new RoomCriteria(new int[] {0, 10}, new int[] {0, 10}, new ArrayList<Boolean>(List.of()),
                    null, new ArrayList<BedType>(List.of()), new ArrayList<Room.RoomSize>(List.of()),
                    new double[] {0, 100.99}, new Calendar[] {todayDate, todayDate}));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sc.searchAvailableRooms(new RoomCriteria(new int[] {0, 10}, new int[] {0, 10}, new ArrayList<Boolean>(List.of()),
                    new ArrayList<Integer>(List.of()), null, new ArrayList<Room.RoomSize>(List.of()),
                    new double[] {0, 100.99}, new Calendar[] {todayDate, todayDate}));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sc.searchAvailableRooms(new RoomCriteria(new int[] {0, 10}, new int[] {0, 10}, new ArrayList<Boolean>(List.of()),
                    new ArrayList<Integer>(List.of()), new ArrayList<BedType>(List.of()), null,
                    new double[] {0, 100.99}, new Calendar[] {todayDate, todayDate}));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sc.searchAvailableRooms(new RoomCriteria(new int[] {0, 10}, new int[] {0, 10}, new ArrayList<Boolean>(List.of()),
                    new ArrayList<Integer>(List.of()), new ArrayList<BedType>(List.of()), new ArrayList<Room.RoomSize>(List.of()),
                    null, new Calendar[] {todayDate, todayDate}));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sc.searchAvailableRooms(new RoomCriteria(new int[] {0, 10}, new int[] {0, 10}, new ArrayList<Boolean>(List.of()),
                    new ArrayList<Integer>(List.of()), new ArrayList<BedType>(List.of()), new ArrayList<Room.RoomSize>(List.of()),
                    new double[] {100.99}, new Calendar[] {todayDate, todayDate}));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sc.searchAvailableRooms(new RoomCriteria(new int[] {0, 10}, new int[] {0, 10}, new ArrayList<Boolean>(List.of()),
                    new ArrayList<Integer>(List.of()), new ArrayList<BedType>(List.of()), new ArrayList<Room.RoomSize>(List.of()),
                    new double[] {0, 100.99}, null));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sc.searchAvailableRooms(new RoomCriteria(new int[] {0, 10}, new int[] {0, 10}, new ArrayList<Boolean>(List.of()),
                    new ArrayList<Integer>(List.of()), new ArrayList<BedType>(List.of()), new ArrayList<Room.RoomSize>(List.of()),
                    new double[] {0, 100.99}, new Calendar[] {todayDate}));
        });
//        assertThrows(IllegalArgumentException.class, () -> {
//            sc.searchAvailableRooms(new RoomCriteria(new int[] {0, 10}, new int[] {0, 10}, new ArrayList<Boolean>(List.of()),
//                    new ArrayList<Integer>(List.of()), new ArrayList<BedType>(List.of()), new ArrayList<Room.RoomSize>(List.of()),
//                    new double[] {0, 100.99}, new Calendar[] {todayDate, todayDate}));
//        });
        sc.searchAvailableRooms(new RoomCriteria(new int[] {0, 10}, new int[] {0, 10}, new ArrayList<Boolean>(List.of()),
                new ArrayList<Integer>(List.of()), new ArrayList<BedType>(List.of()), new ArrayList<Room.RoomSize>(List.of()),
                new double[] {0, 100.99}, new Calendar[] {todayDate, todayDate}));
    }
    @Test
    void invalidInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            sc.searchAvailableRooms(new RoomCriteria(new int[] {-1, 10}, new int[] {0, 10}, new ArrayList<Boolean>(List.of()),
                    new ArrayList<Integer>(List.of()), new ArrayList<BedType>(List.of()), new ArrayList<Room.RoomSize>(List.of()),
                    new double[] {0, 100.99}, new Calendar[] {todayDate, todayDate}));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sc.searchAvailableRooms(new RoomCriteria(new int[] {11, 10}, new int[] {0, 10}, new ArrayList<Boolean>(List.of()),
                    new ArrayList<Integer>(List.of()), new ArrayList<BedType>(List.of()), new ArrayList<Room.RoomSize>(List.of()),
                    new double[] {0, 100.99}, new Calendar[] {todayDate, todayDate}));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sc.searchAvailableRooms(new RoomCriteria(new int[] {1, -10}, new int[] {0, 10}, new ArrayList<Boolean>(List.of()),
                    new ArrayList<Integer>(List.of()), new ArrayList<BedType>(List.of()), new ArrayList<Room.RoomSize>(List.of()),
                    new double[] {0, 100.99}, new Calendar[] {todayDate, todayDate}));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sc.searchAvailableRooms(new RoomCriteria(new int[] {1, 10}, new int[] {-1, 10}, new ArrayList<Boolean>(List.of()),
                    new ArrayList<Integer>(List.of()), new ArrayList<BedType>(List.of()), new ArrayList<Room.RoomSize>(List.of()),
                    new double[] {0, 100.99}, new Calendar[] {todayDate, todayDate}));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sc.searchAvailableRooms(new RoomCriteria(new int[] {11, 10}, new int[] {1, 0}, new ArrayList<Boolean>(List.of()),
                    new ArrayList<Integer>(List.of()), new ArrayList<BedType>(List.of()), new ArrayList<Room.RoomSize>(List.of()),
                    new double[] {0, 100.99}, new Calendar[] {todayDate, todayDate}));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sc.searchAvailableRooms(new RoomCriteria(new int[] {11, 10}, new int[] {0, 10}, new ArrayList<Boolean>(List.of()),
                    new ArrayList<Integer>(List.of()), new ArrayList<BedType>(List.of()), new ArrayList<Room.RoomSize>(List.of()),
                    new double[] {10, -100.99}, new Calendar[] {todayDate, todayDate}));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sc.searchAvailableRooms(new RoomCriteria(new int[] {11, 10}, new int[] {0, 10}, new ArrayList<Boolean>(List.of()),
                    new ArrayList<Integer>(List.of()), new ArrayList<BedType>(List.of()), new ArrayList<Room.RoomSize>(List.of()),
                    new double[] {-10, 100.99}, new Calendar[] {todayDate, todayDate}));
        });
        Calendar beforeTodayDate = Calendar.getInstance();
        beforeTodayDate.set(Calendar.YEAR, 1990);
        Calendar furtherDate = Calendar.getInstance();
        furtherDate.set(Calendar.YEAR, 2035);
        assertThrows(IllegalArgumentException.class, () -> {
            sc.searchAvailableRooms(new RoomCriteria(new int[] {11, 10}, new int[] {0, 10}, new ArrayList<Boolean>(List.of()),
                    new ArrayList<Integer>(List.of()), new ArrayList<BedType>(List.of()), new ArrayList<Room.RoomSize>(List.of()),
                    new double[] {0, 100.99}, new Calendar[] {beforeTodayDate, beforeTodayDate}));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sc.searchAvailableRooms(new RoomCriteria(new int[] {11, 10}, new int[] {0, 10}, new ArrayList<Boolean>(List.of()),
                    new ArrayList<Integer>(List.of()), new ArrayList<BedType>(List.of()), new ArrayList<Room.RoomSize>(List.of()),
                    new double[] {0, 100.99}, new Calendar[] {furtherDate, todayDate}));
        });
    }
    @Test
    void allRoomsReserved() {
        List<Room> rooms = RoomRepository.loadRooms();
        for (Room r : rooms) {
            rc.reserveRoom(r, todayDate, todayDate, guestNum, name, email, phoneNumber, creditCardNumber, ccv, billingAddr,
                    expDate);
        }
        List<Room> searchedRooms = sc.searchAvailableRooms(new RoomCriteria(new int[] {0, 10000000}, new int[] {0, 100000000}, new ArrayList<Boolean>(List.of()),
                new ArrayList<Integer>(List.of()), new ArrayList<BedType>(List.of()), new ArrayList<Room.RoomSize>(List.of()),
                new double[] {0, 10000000000.99}, new Calendar[] {todayDate, todayDate}));
        assertTrue(searchedRooms.isEmpty());

    }
    @Test
    void searchReservedRoom() {
        Room r = RoomRepository.loadRoomOfRoomNumber(100);
        rc.reserveRoom(r, todayDate, todayDate, guestNum, name, email, phoneNumber, creditCardNumber, ccv, billingAddr,
                expDate);

        List<Room> searchedRooms = sc.searchAvailableRooms(new RoomCriteria(new int[] {0, 10000000}, new int[] {0, 100000000}, new ArrayList<Boolean>(List.of()),
                new ArrayList<Integer>(List.of()), new ArrayList<BedType>(List.of()), new ArrayList<Room.RoomSize>(List.of()),
                new double[] {0, 10000000000.99}, new Calendar[] {todayDate, todayDate}));
        assertFalse(searchedRooms.contains(r));

    }
    // Finding specific rooms
    @Test
    void tightCriteria() {
        List<Room> searchedRooms = sc.searchAvailableRooms(new RoomCriteria(new int[] {4, 4}, new int[] {10, 10}, new ArrayList<Boolean>(List.of(false)),
                new ArrayList<Integer>(List.of(300)), new ArrayList<BedType>(List.of(BedType.Queen, BedType.Twin)), new ArrayList<Room.RoomSize>(List.of(Room.RoomSize.Double)),
                new double[] {33.05, 33.05}, new Calendar[] {todayDate, todayDate}));
        assertTrue(searchedRooms.contains(RoomRepository.loadRoomOfRoomNumber(300)));
        assertEquals(1, searchedRooms.size());

        searchedRooms = sc.searchAvailableRooms(new RoomCriteria(new int[] {2, 2}, new int[] {1, 1}, new ArrayList<Boolean>(List.of(true)),
                new ArrayList<Integer>(List.of(100)), new ArrayList<BedType>(List.of(BedType.Queen)), new ArrayList<Room.RoomSize>(List.of(Room.RoomSize.Deluxe)),
                new double[] {16.94, 16.94}, new Calendar[] {todayDate, todayDate}));
        assertTrue(searchedRooms.contains(RoomRepository.loadRoomOfRoomNumber(199)));
        assertEquals(1, searchedRooms.size());
    }
    // Finding multiple rooms
    @Test
    void looseCriteria() {
        List<Room> searchedRooms = sc.searchAvailableRooms(new RoomCriteria(new int[] {0, 10000000}, new int[] {0, 100000000}, new ArrayList<Boolean>(List.of()),
                new ArrayList<Integer>(List.of()), new ArrayList<BedType>(List.of()), new ArrayList<Room.RoomSize>(List.of(Room.RoomSize.Family)),
                new double[] {0, 10000000000.99}, new Calendar[] {todayDate, todayDate}));
        assertTrue(searchedRooms.contains(RoomRepository.loadRoomOfRoomNumber(114)));
        assertEquals(1, searchedRooms.size());

        searchedRooms = sc.searchAvailableRooms(new RoomCriteria(new int[] {0, 10000000}, new int[] {0, 100000000}, new ArrayList<Boolean>(List.of(true, false)),
                new ArrayList<Integer>(List.of(100, 200, 300)), new ArrayList<BedType>(List.of()),
                new ArrayList<Room.RoomSize>(List.of(Room.RoomSize.Deluxe)),
                new double[] {0, 10000000000.99}, new Calendar[] {todayDate, todayDate}));
        assertTrue(searchedRooms.contains(RoomRepository.loadRoomOfRoomNumber(301)));
        assertTrue(searchedRooms.contains(RoomRepository.loadRoomOfRoomNumber(199)));
        assertTrue(searchedRooms.contains(RoomRepository.loadRoomOfRoomNumber(299)));
        assertTrue(searchedRooms.contains(RoomRepository.loadRoomOfRoomNumber(113)));
        assertTrue(searchedRooms.contains(RoomRepository.loadRoomOfRoomNumber(145)));
        assertTrue(searchedRooms.contains(RoomRepository.loadRoomOfRoomNumber(230)));
        assertEquals(6, searchedRooms.size());
    }

    @AfterAll
    static void dropTables() {
        RoomRepository.dropTable();
        ReservationRepository.dropTable();
        UserRepository.dropTable();
    }
}
