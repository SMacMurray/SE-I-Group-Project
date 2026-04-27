package test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stay_and_shop_system.occupancy.Reservation;
import stay_and_shop_system.occupancy.ReservationController;
import stay_and_shop_system.occupancy.Room;
import stay_and_shop_system.occupancy.database.ReservationRepository;
import stay_and_shop_system.occupancy.database.RoomRepository;
import stay_and_shop_system.user.AccountController;
import stay_and_shop_system.user.GuestInterface;
import stay_and_shop_system.user.User;
import stay_and_shop_system.user.UserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class CancelReservationTesting {
    ReservationController rc = new ReservationController();

    int guestNum = 1;
    String email = "jollyJill@gmail.com";
    String name = "Jolly Jill";
    String password = "jollyGillyDons#love";
    String phoneNumber = "+1 909-909-9999";
    String creditCardNumber = "4242 4242 4242 4242";
    String billingAddr = "1047 Treee Freee drive";
    String ccv = "444";
    static Calendar expDate = Calendar.getInstance();
    static Calendar todayDate;
    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
    static SimpleDateFormat expFormatter = new SimpleDateFormat("MM/yyyy");

    @BeforeAll
    static void init() {
        RoomRepository.dropTable();
        RoomRepository.createTable();

        RoomRepository.addRoom(new Room(100, 5, 5, 10.05, true,
                new ArrayList<>(List.of(Room.BedType.Full)),
                Room.QualityLevel.Comfort, Room.RoomSize.Double));
        RoomRepository.addRoom(new Room(200, 10, 4, 20.05, false,
                new ArrayList<>(List.of(Room.BedType.Full, Room.BedType.King)),
                Room.QualityLevel.Comfort, Room.RoomSize.Double));
        RoomRepository.addRoom(new Room(300, 10, 4, 30.05, false,
                new ArrayList<>(List.of(Room.BedType.Twin, Room.BedType.Queen)),
                Room.QualityLevel.Comfort, Room.RoomSize.Double));
        RoomRepository.addRoom(new Room(230, 2, 4, 1.05, true,
                new ArrayList<>(List.of(Room.BedType.Queen)),
                Room.QualityLevel.Comfort, Room.RoomSize.Deluxe));
        RoomRepository.addRoom(new Room(199, 1, 2, 5.95, true,
                new ArrayList<>(List.of(Room.BedType.Queen)),
                Room.QualityLevel.Executive, Room.RoomSize.Deluxe));


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

        UserRepository.dropTable();
        UserRepository.initAccountTable();
    }
    @Test
    void nullOrEmptyInput() {
        AccountController.createAccount(email, name, password, phoneNumber);
        assertEquals(Math.abs(Objects.hash(email)), ((GuestInterface)UserRepository.getSessionAccount()).getGuestId());
        assertThrows(NullPointerException.class, () -> ReservationRepository.deleteReservation(null));

        //
        // loadreservation by guest id;
        // getGuest Id
        // delete Reservation
    }
    @Test
    void searchReservationById() {
        AccountController.createAccount(email, name, password, phoneNumber);
        GuestInterface guest = ((GuestInterface)UserRepository.getSessionAccount());


        for (Room r : RoomRepository.loadRooms()) {
            rc.reserveRoom(r, todayDate, todayDate, guestNum, name, email, phoneNumber, creditCardNumber, ccv, billingAddr,
                    expDate);
        }
        assertTrue(() -> {
            for (Room r : RoomRepository.loadRooms()) {
                boolean containsR = false;
                for (Reservation re : ReservationRepository.loadReservationsOfGuestId(guest.getGuestId())) {
                    if (re.getRoom().equals(r)) {
                        containsR = true;
                        break;
                    }
                }
                if (!containsR) {
                    return false;
                }
            }
            return true;
        });

        // Empty reservation Search
        assertTrue(ReservationRepository.loadReservationsOfGuestId(0).isEmpty());
    }
    @Test
    void deleteReservation() {
        AccountController.createAccount(email, name, password, phoneNumber);
        GuestInterface guest = ((GuestInterface)UserRepository.getSessionAccount());

        for (Room r : RoomRepository.loadRooms()) {
            rc.reserveRoom(r, todayDate, todayDate, guestNum, name, email, phoneNumber, creditCardNumber, ccv, billingAddr,
                    expDate);
        }
        for (Reservation r : ReservationRepository.loadReservations()) {
            ReservationRepository.deleteReservation(r);
        }
        assertEquals(0, ReservationRepository.loadReservations().size());

    }

    @AfterAll
    static void dropTables() {
        RoomRepository.dropTable();
        ReservationRepository.dropTable();
    }
}
