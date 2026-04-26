package test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import stay_and_shop_system.occupancy.Room;
import stay_and_shop_system.occupancy.database.ReservationRepository;
import stay_and_shop_system.occupancy.database.RoomRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CancelReservationTesting {
    int guestNum = 1;
    String email = "jollyJill@gmail.com";
    String name = "Jolly Jill";
    String password = "jollyGillyDons#love";
    String phoneNumber = "+1 909-909-9999";
    String creditCardNumber = "4242 4242 4242 4242";
    String billingAddr = "1047 Treee Freee drive";
    String ccv = "444";
    static Calendar expDate = Calendar.getInstance();
    static SimpleDateFormat expFormatter = new SimpleDateFormat("MM/yy");

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
    }
    @Test
    void nullOrEmptyInput() {

    }
    @Test
    void invalidInput() {

    }
    @Test
    void searchReservationById() {

    }
    @Test
    void deleteReservation() {

    }

    @AfterAll
    static void dropTables() {
        RoomRepository.dropTable();
        ReservationRepository.dropTable();
    }
}
