package test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stay_and_shop_system.occupancy.Reservation;
import stay_and_shop_system.occupancy.ReservationController;
import stay_and_shop_system.occupancy.Room;
import stay_and_shop_system.occupancy.SearchController;
import stay_and_shop_system.occupancy.database.ReservationRepository;
import stay_and_shop_system.occupancy.database.RoomRepository;
import stay_and_shop_system.user.UserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// Finding a room that does not overlap with a reservation uses SearchAvailableRoom and ReserveRoom, so that part depends
// on those two.
public class ModifyReservationTesting {
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
    int roomNumber = 100;
    static Calendar expDate = Calendar.getInstance();
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
        RoomRepository.addRoom(new Room(145, 30, 15, 150.95, false,
                new ArrayList<>(List.of(Room.BedType.King)),
                Room.QualityLevel.Executive, Room.RoomSize.Deluxe));
        RoomRepository.addRoom(new Room(113, 2, 1, 10.49, true,
                new ArrayList<>(List.of(Room.BedType.Full)),
                Room.QualityLevel.Executive, Room.RoomSize.Deluxe));
        RoomRepository.addRoom(new Room(299, 10, 10, 5.99, true,
                new ArrayList<>(List.of(Room.BedType.Full)),
                Room.QualityLevel.Executive, Room.RoomSize.Deluxe));
        RoomRepository.addRoom(new Room(114, 13, 13, 3.00, false,
                new ArrayList<>(List.of(Room.BedType.Full)),
                Room.QualityLevel.Economy, Room.RoomSize.Family));
        RoomRepository.addRoom(new Room(301, 35, 30, 299.99, false,
                new ArrayList<>(List.of(Room.BedType.King, Room.BedType.Queen)),
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
        int guestId = (int)(rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(roomNumber), todayDate, todayDate, guestNum, name, email, phoneNumber, creditCardNumber, ccv, billingAddr,
                expDate))[0];
        Reservation r = ReservationRepository.loadReservationsOfGuestId(guestId).get(0);

        assertThrows(IllegalArgumentException.class, () -> {
            rc.modifyReservation(null, roomNumber, guestNum, name, email, creditCardNumber);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            rc.modifyReservation(r, roomNumber, guestNum, null, email, creditCardNumber);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            rc.modifyReservation(r, roomNumber, guestNum, "", email, creditCardNumber);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            rc.modifyReservation(r, roomNumber, guestNum, name, null, creditCardNumber);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            rc.modifyReservation(r, roomNumber, guestNum, name, "", creditCardNumber);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            rc.modifyReservation(r, roomNumber, guestNum, name,  email, null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            rc.modifyReservation(r, roomNumber, guestNum, name,  email, "");
        });
    }
    @Test
    void invalidInput() {
        int guestId = (int)(rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(roomNumber), todayDate, todayDate, guestNum, name, email, phoneNumber, creditCardNumber, ccv, billingAddr,
                expDate))[0];
        Reservation r = ReservationRepository.loadReservationsOfGuestId(guestId).get(0);

        int nonExistentRoomNumber = 0;
        int invalidGuestNumber = 0;
        int outOfRangeGuestNumber = 1000000000;
        int outOfRangeGuestNumber2 = -1000000000;
        String invalidEmail = "joel.com";
        String invalidEmail2 = "1@a.com";
        String invalidEmail3 = "j@a.";
        String invalidCreditCardNumber = "j@a.";

        assertThrows(IllegalArgumentException.class, () -> {
            rc.modifyReservation(r, nonExistentRoomNumber, guestNum, name, email, creditCardNumber);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            rc.modifyReservation(r, roomNumber, invalidGuestNumber, name, email, creditCardNumber);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            rc.modifyReservation(r, roomNumber, outOfRangeGuestNumber, name, email, creditCardNumber);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            rc.modifyReservation(r, roomNumber, outOfRangeGuestNumber2, name, email, creditCardNumber);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            rc.modifyReservation(r, roomNumber, guestNum, name, invalidEmail, creditCardNumber);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            rc.modifyReservation(r, roomNumber, guestNum, name, invalidEmail2, creditCardNumber);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            rc.modifyReservation(r, roomNumber, guestNum, name, invalidEmail3, creditCardNumber);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            rc.modifyReservation(r, roomNumber, guestNum, name, email, invalidCreditCardNumber);
        });
    }
    @Test
    void modifyWholeReservation() {
        int guestId = (int)(rc.reserveRoom(RoomRepository.loadRoomOfRoomNumber(roomNumber), todayDate, todayDate, guestNum, name, email, phoneNumber, creditCardNumber, ccv, billingAddr,
                expDate))[0];
        Reservation r = ReservationRepository.loadReservationsOfGuestId(guestId).get(0);

        // Modify reservation with the same values.
        rc.modifyReservation(r, roomNumber, guestNum, name, email, creditCardNumber);
        Reservation repositoryReservation = ReservationRepository.loadReservationsOfGuestId(r.getGuestId()).get(0);
        assertEquals(repositoryReservation.getReservationId(), r.getReservationId());
        r = ReservationRepository.loadReservationsOfGuestId(r.getGuestId()).get(0);
        assertEquals(Math.abs(Objects.hash(email)), r.getGuestId());
        assertEquals(roomNumber, r.getRoom().getNumber() );
        assertEquals(name, r.getGuestName() );
        assertEquals(guestNum, r.getGuestNumber() );
        assertEquals(email, r.getGuestEmail() );
        assertEquals(creditCardNumber, r.getCreditCardNumber() );

        int newRoomNumber = 200;
        int newGuestNum = 3;
        String newGuestName = "Johnny Somali";
        String newGuestEmail = "gta5@gmail.com";
        String newCreditCardNumber = "4242";

        rc.modifyReservation(r, newRoomNumber, newGuestNum, newGuestName, newGuestEmail, newCreditCardNumber);
        r = ReservationRepository.loadReservationsOfGuestId(r.getGuestId()).get(0);
        assertEquals(Objects.hash(newRoomNumber + newGuestName + r.getFormattedEndDate()), r.getReservationId());
        assertEquals(Math.abs(Objects.hash(newGuestEmail)), r.getGuestId());
        assertEquals(newRoomNumber, r.getRoom().getNumber() );
        assertEquals(newGuestName, r.getGuestName() );
        assertEquals(newGuestNum, r.getGuestNumber() );
        assertEquals(newGuestEmail, r.getGuestEmail() );
        assertEquals(newCreditCardNumber, r.getCreditCardNumber() );

    }
    @AfterAll
    static void deleteTables() {
        ReservationRepository.dropTable();
        RoomRepository.dropTable();
        UserRepository.dropTable();
    }
}
