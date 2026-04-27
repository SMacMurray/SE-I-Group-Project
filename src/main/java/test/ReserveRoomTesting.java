package test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import stay_and_shop_system.LoadCSV;
import stay_and_shop_system.occupancy.Reservation;
import stay_and_shop_system.occupancy.ReservationController;
import stay_and_shop_system.occupancy.Room;
import stay_and_shop_system.occupancy.RoomService;
import stay_and_shop_system.occupancy.database.ReservationRepository;
import stay_and_shop_system.occupancy.database.RoomRepository;
import stay_and_shop_system.user.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class ReserveRoomTesting {
    ReservationController rc = new ReservationController();
    RoomService rs = new RoomService();

    String email = "jollyJill@gmail.com";
    String name = "Jolly Jill";
    String password = "jollyGillyDons#love";
    String phoneNumber = "+1 909-909-9999";

    @BeforeAll
    static void beforeAll() {
//        LoadCSV.loadRooms();
    }
    @BeforeEach
    void beforeEach() {
        ReservationRepository.dropTable();
        ReservationRepository.createTable();

        UserRepository.dropTable();
        UserRepository.initAccountTable();

        RoomRepository.dropTable();
        RoomRepository.createTable();
        List<Room.BedType> bts = new ArrayList<>();
        bts.add(Room.BedType.Full);
        bts.add(Room.BedType.King);
        RoomRepository.addRoom(new Room(101, 100, 100, 101.01, true, bts, Room.QualityLevel.Executive, Room.RoomSize.Deluxe));
        System.out.println(RoomRepository.loadRoomOfRoomNumber(101));
        bts.add(Room.BedType.Queen);
        bts.add(Room.BedType.King);
        RoomRepository.addRoom(new Room(200, 13, 46, 203.99, false, bts, Room.QualityLevel.Comfort, Room.RoomSize.Double));
        System.out.println(RoomRepository.loadRoomOfRoomNumber(200));

        UserRepository.setUser(null);

    }
    @Test
    void nullOrEmptyInput() {
        Calendar expDate = Calendar.getInstance();
        SimpleDateFormat expFormatter = new SimpleDateFormat("MM/yyyy");
        try {
            expDate.setTime(expFormatter.parse("11/2027"));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        String creditCardNumber = "42";
        assertThrows(IllegalArgumentException.class, () -> {
            Object[] reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(101), null, null, 2, name, email, phoneNumber, creditCardNumber, "334", "1334 Firing My side at Drive", expDate);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Object[] reservationInfo = rc.reserveRoom(null, Calendar.getInstance(), null, 2, name, email, phoneNumber, creditCardNumber, "334", "1334 Firing My side at Drive", expDate);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Object[] reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(101), Calendar.getInstance(), Calendar.getInstance(), 0, "John", email, phoneNumber, creditCardNumber, "334", "1334 Firing My side at Drive", expDate);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Object[] reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(101), Calendar.getInstance(), Calendar.getInstance(), 2, "", email, phoneNumber, creditCardNumber, "334", "1334 Firing My side at Drive", expDate);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Object[] reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(101), Calendar.getInstance(), Calendar.getInstance(), 2, null, email, phoneNumber, creditCardNumber, "334", "1334 Firing My side at Drive", expDate);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Object[] reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(101), Calendar.getInstance(), Calendar.getInstance(), 2, name, "", phoneNumber, creditCardNumber, "334", "1334 Firing My side at Drive", expDate);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Object[] reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(101), Calendar.getInstance(), Calendar.getInstance(), 2, name, null, phoneNumber, creditCardNumber, "334", "1334 Firing My side at Drive", expDate);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Object[] reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(101), Calendar.getInstance(), Calendar.getInstance(), 2, name, email, "", creditCardNumber, "334", "1334 Firing My side at Drive", expDate);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Object[] reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(101), Calendar.getInstance(), Calendar.getInstance(), 2, name, email, null, creditCardNumber, "334", "1334 Firing My side at Drive", expDate);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Object[] reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(101), Calendar.getInstance(), Calendar.getInstance(), 2, name, email, phoneNumber, null, "334", "1334 Firing My side at Drive", expDate);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Object[] reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(101), Calendar.getInstance(), Calendar.getInstance(), 2, name, email, phoneNumber, "", "334", "1334 Firing My side at Drive", expDate);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Object[] reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(101), Calendar.getInstance(), Calendar.getInstance(), 2, name, email, phoneNumber, "42", "", "1334 Firing My side at Drive", expDate);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Object[] reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(101), Calendar.getInstance(), Calendar.getInstance(), 2, name, email, phoneNumber, "42", "334", "", expDate);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Object[] reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(101), Calendar.getInstance(), Calendar.getInstance(), 2, name, email, phoneNumber, "42", "334", null, expDate);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Object[] reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(101), Calendar.getInstance(), Calendar.getInstance(), 2, name, email, phoneNumber, "42", "334", "John", null);
        });
    }
    @Test
    void invalidInput() {
        Calendar laterThanEndDate = Calendar.getInstance();
        laterThanEndDate.set(Calendar.YEAR, 2999);
        Calendar beforeTodayDate = Calendar.getInstance();
        beforeTodayDate.set(Calendar.YEAR, 1990);
        Calendar invalidExpDate = Calendar.getInstance();
        int invalidGuestNum = 0;
        int outOfRangeGuestNum = 101; // More than the room max occupancy
        int outOfRangeGuestNum2 = -101; // More than the room max occupancy
        String invalidCreditCard = "3";
        String invalidCCV = "3";
        String invalidCCV2 = "33a";
        String invalidPhoneNumber = "909-999-9999";
        String invalidPhoneNumber2 = "+1 909-999-999";
        Calendar expDate = Calendar.getInstance();
        SimpleDateFormat expFormatter = new SimpleDateFormat("MM/yy");
        try {
            expDate.setTime(expFormatter.parse("11/27"));
            invalidExpDate.setTime(expFormatter.parse(expFormatter.format(Calendar.getInstance().getTime())));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        assertThrows(IllegalArgumentException.class, () -> {
            Object[] reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(101), laterThanEndDate, Calendar.getInstance(), 2, name, email, phoneNumber, "42", "334", "John", expDate);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Object[] reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(101), beforeTodayDate, beforeTodayDate, 2, name, email, phoneNumber, "42", "334", "John", expDate);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Object[] reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(101), Calendar.getInstance(), Calendar.getInstance(), 2, name, email, phoneNumber, "42", "334", "John", invalidExpDate);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Object[] reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(101), Calendar.getInstance(), Calendar.getInstance(), invalidGuestNum, name, email, phoneNumber, "42", "334", "John", expDate);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Object[] reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(101), Calendar.getInstance(), Calendar.getInstance(), outOfRangeGuestNum, name, email, phoneNumber, "42", "334", "John", expDate);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Object[] reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(101), Calendar.getInstance(), Calendar.getInstance(), outOfRangeGuestNum2, name, email, phoneNumber, "42", "334", "John", expDate);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Object[] reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(101), Calendar.getInstance(), Calendar.getInstance(), 2, name, email, phoneNumber, invalidCreditCard, "334", "John", expDate);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Object[] reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(101), Calendar.getInstance(), Calendar.getInstance(), 2, name, email, phoneNumber, "42", invalidCCV, "John", expDate);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Object[] reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(101), Calendar.getInstance(), Calendar.getInstance(), 2, name, email, phoneNumber, "42", invalidCCV2, "John", expDate);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Object[] reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(101), Calendar.getInstance(), Calendar.getInstance(), 2, name, email, invalidPhoneNumber, "42", "334", "John", expDate);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Object[] reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(101), Calendar.getInstance(), Calendar.getInstance(), 2, name, email, invalidPhoneNumber2, "42", "334", "John", expDate);
        });

    }
    @Test
    void newGuestMakesReservation() {
        Calendar start = Calendar.getInstance();
        start.set(Calendar.DAY_OF_MONTH, start.get(Calendar.DAY_OF_MONTH) + 1);
        Calendar end = Calendar.getInstance();
        end.set(Calendar.DAY_OF_MONTH, end.get(Calendar.DAY_OF_MONTH) + 1);
        Calendar end2 = Calendar.getInstance();
        end2.set(Calendar.DAY_OF_MONTH, end2.get(Calendar.DAY_OF_MONTH) + 3);
        Calendar expDate = Calendar.getInstance();
        SimpleDateFormat expFormatter = new SimpleDateFormat("MM/yyyy");
        String guestEmail = "jollyJill@gmail.com";

        try {
            expDate.setTime(expFormatter.parse("11/2027"));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        for (Reservation r : ReservationRepository.loadReservations()) {
            System.out.println(r);
        }
        Object[] reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(101), start, end, 2, "Jolly Jill", guestEmail, "+1 909-909-9099", "4242 4242 4242 4242", "334", "1334 Firing My side at Drive", expDate);
        for (Reservation r : ReservationRepository.loadReservations()) {
            System.out.println(r);
        }
        int guestId = (int)reservationInfo[0];
        double total = (double)reservationInfo[1];

        assertEquals(guestId, Math.abs(Objects.hash(guestEmail)));
        assertEquals(total, RoomRepository.loadRoomOfRoomNumber(101).getDailyRate());

        // This now considers that the User has a session account assigned.
        reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(200), start, end2, 2, "Jolly Jill", guestEmail, "+1 909-909-9099", "4242 4242 4242 4242", "334", "1334 Firing My side at Drive", expDate);

        guestId = (int)reservationInfo[0];
        total = (double)reservationInfo[1];

        assertEquals(guestId, Math.abs(Objects.hash(guestEmail)));
        assertEquals(RoomRepository.loadRoomOfRoomNumber(200).getDailyRate() * 3, total);

        assertTrue(UserRepository.findUser(guestEmail));


    }
    @Test
    void adminToGuestAdmin() throws Exception {
        AccountController ac = new AccountController();


        Admin admin = new Admin(email, name, Objects.hash(password), phoneNumber);
        UserRepository.createEmployeeAccount(email, name, Objects.hash(password), phoneNumber, 0);
        // UserRepository.setUser(admin);

        Calendar start = Calendar.getInstance();
        start.set(Calendar.DAY_OF_MONTH, start.get(Calendar.DAY_OF_MONTH) + 1);
        Calendar end = Calendar.getInstance();
        end.set(Calendar.DAY_OF_MONTH, end.get(Calendar.DAY_OF_MONTH) + 1);
        Calendar end2 = Calendar.getInstance();
        end2.set(Calendar.DAY_OF_MONTH, end2.get(Calendar.DAY_OF_MONTH) + 3);
        Calendar expDate = Calendar.getInstance();
        SimpleDateFormat expFormatter = new SimpleDateFormat("MM/yyyy");
        try {
            expDate.setTime(expFormatter.parse("11/2027"));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        for (Reservation r : ReservationRepository.loadReservations()) {
            System.out.println(r);
        }
        Object[] reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(101), start, end, 2, name, email, phoneNumber, "4242 4242 4242 4242", "334", "1334 Firing My side at Drive", expDate);
        for (Reservation r : ReservationRepository.loadReservations()) {
            System.out.println(r);
        }


        int guestId = (int)reservationInfo[0];
        double total = (double)reservationInfo[1];

        assertEquals(guestId, Math.abs(Objects.hash(email)));


        assertTrue(UserRepository.getSessionAccount().getTypeId().equals(User.UserType.GUEST_ADMIN));

        reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(200), start, end2, 2, name, email, phoneNumber, "4242 4242 4242 4242", "334", "1334 Firing My side at Drive", expDate);

        System.out.println("");
        for (Reservation r : ReservationRepository.loadReservations()) {
            System.out.println(r);
        }

        guestId = (int)reservationInfo[0];
        total = (double)reservationInfo[1];

        assertEquals(guestId, Math.abs(Objects.hash(email)));
        assertEquals(RoomRepository.loadRoomOfRoomNumber(200).getDailyRate() * 3, total);

        assertTrue(UserRepository.findUser(email));

        assertTrue(UserRepository.getSessionAccount().getTypeId().equals(User.UserType.GUEST_ADMIN));

        assertTrue(UserRepository.findAccount(email));
//
        GuestAdmin gA = (GuestAdmin) UserRepository.getSessionAccount();

        assertEquals(admin.getEmail(), gA.getEmail());
        assertEquals(admin.getName(), gA.getName());
        assertEquals(admin.getPassword(), gA.getPassword());
        assertEquals(admin.getPhoneNumber(), gA.getPhoneNumber());



    }
    @Test
    void clerkToGuestClerk() {
        AccountController ac = new AccountController();

        Clerk clerk = new Clerk(email, name, Objects.hash(password), phoneNumber);
        UserRepository.createEmployeeAccount(email, name, Objects.hash(password), phoneNumber, 1);
        // UserRepository.setUser(admin);

        Calendar start = Calendar.getInstance();
        start.set(Calendar.DAY_OF_MONTH, start.get(Calendar.DAY_OF_MONTH) + 1);
        Calendar end = Calendar.getInstance();
        end.set(Calendar.DAY_OF_MONTH, end.get(Calendar.DAY_OF_MONTH) + 1);
        Calendar end2 = Calendar.getInstance();
        end2.set(Calendar.DAY_OF_MONTH, end2.get(Calendar.DAY_OF_MONTH) + 3);
        Calendar expDate = Calendar.getInstance();
        SimpleDateFormat expFormatter = new SimpleDateFormat("MM/yyyy");
        try {
            expDate.setTime(expFormatter.parse("11/2027"));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        for (Reservation r : ReservationRepository.loadReservations()) {
            System.out.println(r);
        }
        Object[] reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(101), start, end, 2, name, email, phoneNumber, "4242 4242 4242 4242", "334", "1334 Firing My side at Drive", expDate);
        for (Reservation r : ReservationRepository.loadReservations()) {
            System.out.println(r);
        }


        int guestId = (int)reservationInfo[0];
        double total = (double)reservationInfo[1];

        assertEquals(guestId, Math.abs(Objects.hash(email)));


        assertTrue(UserRepository.getSessionAccount().getTypeId().equals(User.UserType.GUEST_CLERK));

        reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(200), start, end2, 2, name, email, phoneNumber, "4242 4242 4242 4242", "334", "1334 Firing My side at Drive", expDate);

        System.out.println("");
        for (Reservation r : ReservationRepository.loadReservations()) {
            System.out.println(r);
        }

        guestId = (int)reservationInfo[0];
        total = (double)reservationInfo[1];

        assertEquals(guestId, Math.abs(Objects.hash(email)));
        assertEquals(RoomRepository.loadRoomOfRoomNumber(200).getDailyRate() * 3, total);

        assertTrue(UserRepository.findUser(email));

        assertTrue(UserRepository.getSessionAccount().getTypeId().equals(User.UserType.GUEST_CLERK));

        assertTrue(UserRepository.findAccount(email));
//
        GuestClerk gC = (GuestClerk) UserRepository.getSessionAccount();

        assertEquals(clerk.getEmail(), gC.getEmail());
        assertEquals(clerk.getName(), gC.getName());
        assertEquals(clerk.getPassword(), gC.getPassword());
        assertEquals(clerk.getPhoneNumber(), gC.getPhoneNumber());



    }
    @Test
    void guestWithPasswordMakesReservation() {
        Guest guest = new Guest(email, name, Objects.hash(password), phoneNumber);
        UserRepository.createEmployeeAccount(email, name, Objects.hash(password), phoneNumber, 4);

        Calendar start = Calendar.getInstance();
        start.set(Calendar.DAY_OF_MONTH, start.get(Calendar.DAY_OF_MONTH) + 1);
        Calendar end = Calendar.getInstance();
        end.set(Calendar.DAY_OF_MONTH, end.get(Calendar.DAY_OF_MONTH) + 1);
        Calendar end2 = Calendar.getInstance();
        end2.set(Calendar.DAY_OF_MONTH, end2.get(Calendar.DAY_OF_MONTH) + 3);
        Calendar expDate = Calendar.getInstance();
        SimpleDateFormat expFormatter = new SimpleDateFormat("MM/yyyy");
        try {
            expDate.setTime(expFormatter.parse("11/2027"));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        for (Reservation r : ReservationRepository.loadReservations()) {
            System.out.println(r);
        }
        Object[] reservationInfo = rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(101), start, end, 2, name, email, phoneNumber, "4242 4242 4242 4242", "334", "1334 Firing My side at Drive", expDate);
        for (Reservation r : ReservationRepository.loadReservations()) {
            System.out.println(r);
        }


        int guestId = (int)reservationInfo[0];
        double total = (double)reservationInfo[1];

        assertEquals(guestId, Math.abs(Objects.hash(email)));


        assertTrue(UserRepository.getSessionAccount().getTypeId().equals(User.UserType.GUEST));

        Guest g = (Guest) UserRepository.getSessionAccount();

        assertEquals(guest.getEmail(), g.getEmail());
        assertEquals(guest.getName(), g.getName());
        assertEquals(guest.getPassword(), g.getPassword());
        assertEquals(guest.getPhoneNumber(), g.getPhoneNumber());

    }

    @AfterAll
    static void dropTables() {
        RoomRepository.dropTable();
        ReservationRepository.dropTable();
        UserRepository.dropTable();
    }
//AccountSystem => UserRepository
//Made JUnit Testing for ReserveRoom combined with UserRepository
//Almost or Fullly Finished(there may be edge cases beside null cases) ReserveRoom.
}
